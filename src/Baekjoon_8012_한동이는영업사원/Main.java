/*
 Author : Ruel
 Problem : Baekjoon 8012번 한동이는 영업사원!
 Problem address : https://www.acmicpc.net/problem/8012
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8012_한동이는영업사원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> connections;
    static int[][] sparseArray;
    static int[] depths;

    public static void main(String[] args) throws IOException {
        // 한동이는 1번 도시에서부터 여러 도시를 방문한다
        // 1번 도시에서는 모든 도시에 방문할 수 있고, 도로끼리 사이클을 만들지 않는다.
        // n개의 도시, n-1개의 도로, m개의 방문할 도시들이 주어질 때, 모든 도시를 방문할 수 있는 최소 시간을 출력하라.
        //
        // 사이클을 만들지 않는다 -> 트리형태
        // 도시들이 도로를 통해 트리 형태로 연결되어있다.
        // 따라서 희소배열을 통해 최소 공통 조상을 찾고, 각 지점에서 최소 공통조상까지의 거리를 더해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 도시 간에 연결된 도로.
        connections = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            connections.get(b).add(a);
        }

        // 희소 배열과, 각 도시의 깊이 설정.
        int size = (int) Math.ceil(Math.log(n) / Math.log(2)) + 1;
        sparseArray = new int[n + 1][size];
        depths = new int[n + 1];
        // connections에서 자신의 자식 노드만 남기고, 희소배열을 채운다.
        leaveOnlyChildAndFindParent(1, 1);

        // m개의 방문 도시 목록.
        int m = Integer.parseInt(br.readLine());
        int sum = 0;
        // 시작은 항상 1번 도시.
        int current = 1;
        for (int i = 0; i < m; i++) {
            // 다음 도시.
            int next = Integer.parseInt(br.readLine());
            // 현재 위치와 다음 도시 간의 최소 공통 조상 찾기.
            int sameAncestor = findSameAncestor(current, next);
            // 그 후 current -> sameAncestor -> next로의 거리를 구해 더한다.
            sum += depths[current] + depths[next] - 2 * depths[sameAncestor];
            // 다음 현재 위치는 next.
            current = next;
        }
        System.out.println(sum);
    }

    // 공통 조상 찾기.
    static int findSameAncestor(int a, int b) {
        // 깊이가 다르다면 희소 배열을 통해 jump해준다.
        while (depths[a] != depths[b]) {
            // 차이보다 같거나 작은 수 중 같은 큰 수를 찾아 해당 수 만큼 점프한다.
            int jump = (int) (Math.log(Math.abs(depths[a] - depths[b])) / Math.log(2));
            // a가 더 깊다면 a를 올리고
            if (depths[a] > depths[b])
                a = sparseArray[a][jump];
            // b가 더 깊다면 b를 올린다.
            else
                b = sparseArray[b][jump];
        }

        // 깊이가 같아졌다면, a와 b가 같아질 때까지 올라간다.
        while (a != b) {
            a = sparseArray[a][0];
            b = sparseArray[b][0];
        }
        // a나 b가 최소 공통 조상이다.
        return a;
    }

    // connections에서 자식 노드만 남기고, 희소 배열을 채워간다.
    static void leaveOnlyChildAndFindParent(int n, int depth) {
        // 깊이 설정.
        depths[n] = depth;
        // 현재까지의 깊이를 바탕으로 희소 배열을 채운다.
        // 자신의 2^i번째 조상은 자신의 2^(i-1)번째 조상의, 2^(i-1)번째 조상에 해당한다.
        for (int i = 1; i < Math.log(depths[n]) / Math.log(2); i++)
            sparseArray[n][i] = sparseArray[sparseArray[n][i - 1]][i - 1];

        // connections를 역순으로 살펴본다.
        for (int i = connections.get(n).size() - 1; i >= 0; i--) {
            int next = connections.get(n).get(i);
            // 이미 방문한 적이 있는 노드라면, 자신보다 조상 노드이다.
            // connections에서 지워주자.
            if (depths[next] != 0)
                connections.get(n).remove(i);
            else {
                // 그렇지 않다면 next의 조상 노드에 n을 기록하고
                sparseArray[next][0] = n;
                // 재귀적으로 메소드를 호출한다.
                leaveOnlyChildAndFindParent(next, depth + 1);
            }
        }
    }
}