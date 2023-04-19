/*
 Author : Ruel
 Problem : Baekjoon 24230번 트리 색칠하기
 Problem address : https://www.acmicpc.net/problem/24230
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24230_트리색칠하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 정점이 n인 트리가 주어진다.
        // 각 정점을 원하는 색으로 칠하고 싶다. 현재 정점들은 모두 0번 색으로 칠해져있다.
        // 정점을 색으로 칠할 때, 해당하는 서브트리가 모두 같은 색으로 칠해진다고 할 때
        // 몇 번을 칠해야 모든 정점들을 원하는 색으로 칠할 수 있는가?
        //
        // BFS 문제
        // 루트 노드부터, 자식 노드들을 탐색해나가며, 부모 노드와 자식 노드가 색이 다르다면
        // 자식 노드에 무조건 색을 칠해야한다.
        // 해당하는 경우의 수를 깊이 우선 탐색으로 처리하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 정점의 수
        int n = Integer.parseInt(br.readLine());
        // 정점들에 칠하고자 하는 색
        int[] colors = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 트리의 간선
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n; i++)
            child.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            child.get(a).add(b);
            child.get(b).add(a);
        }

        // 루트 노드가 0번 색이라면 0번 노드에서 색을 칠할 필요는 없다.
        // 하지만 0번 색이 아니라면 다른 색으로 한번 칠해야한다.
        int answer = findAnswer(0, new boolean[n], child, colors) + (colors[0] == 0 ? 0 : 1);
        System.out.println(answer);
    }

    // BFS 탐색
    static int findAnswer(int n, boolean[] visited, List<List<Integer>> child, int[] colors) {
        // 방문 체크
        visited[n] = true;
        
        // 자신을 루트로 갖는 서브 트리에서 색을 칠해야하는 횟수
        int sum = 0;
        for (int c : child.get(n)) {
            // 미방문 노드라면(=자식노드라면)
            // 재귀적으로 메소드를 호출하여 자식의 서브 트리에 칠해야하는 색의 횟수를 계산한다.
            // 지금 n 노드와 c노드의 색이 다르면, c 노드에 색을 한번 칠해야한다.
            // 따라서 두 값의 합을 sum에 더해주자.
            if (!visited[c])
                sum += findAnswer(c, visited, child, colors) + (colors[n] == colors[c] ? 0 : 1);
        }
        // 최종적으로 자신의 자식 노드를 루트로 갖는 서브 트리들에서 색을 칠해야하는 횟수가
        // sum에 계산되었다.
        // sum 반환
        return sum;
    }
}
