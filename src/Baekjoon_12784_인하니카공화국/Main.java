/*
 Author : Ruel
 Problem : Baekjoon 12784번 인하니카 공화국
 Problem address : https://www.acmicpc.net/problem/12784
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12784_인하니카공화국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] bridges;
    static final int MAX = 20 * 1000 + 1;

    public static void main(String[] args) throws IOException {
        // n개의 섬과 m개의 다리와 각 다리를 폭파하는데 필요한 다이너마이트가 주어진다
        // 1번 섬을 제외한 다리가 하나 밖에 없는 섬 중 하나에서 1번섬으로 가고자 한다
        // 이를 막기 위해 다리가 하나 밖에 없는 모든 섬에서 1번 섬으로 가는 경로를 차단하고자 한다.
        // 이 때의 최소 다이너마이트 사용 개수를 구하라
        //
        // 잘 생각해보면 트리 형태임을 알 수 있다
        // 그리고 다리가 하나 밖에 없는 섬은 단말 노드임을 알 수 있다.
        // 따라서 1번부터 bottom-up 방식으로 단말 연결을 끊어가는 경우를 계산해 나가면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            // 섬 간의 연결된 다리를 폭파시키는데 필요한 다이너마이트의 개수
            bridges = new int[n + 1][n + 1];
            for (int[] b : bridges)
                Arrays.fill(b, MAX);
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());

                bridges[a][b] = bridges[b][a] = Math.min(bridges[b][a], d);
            }

            // 1번 노드에서 자식 노드들을 끊는 경우 중 최소 다이너마이트 사용 개수를 구한다.
            int answer = findMinDynamite(1, 0, new boolean[n + 1]);
            // 1번 섬만 하나 있는 경우에는 끊을 다리가 없다. 0을 리턴해야한다.
            // 그 외의 경우에는 구해진 answer이 정답.
            System.out.println(answer == MAX ? 0 : answer);
        }
    }

    // 최소 다이너마이트의 개수를 구한다.
    static int findMinDynamite(int node, int parent, boolean[] visited) {
        // node 방문 체크.
        visited[node] = true;

        // 자식 노드들을 끊는데 필요한 다이너마이트 개수를 구한다.
        int sum = 0;
        for (int i = 1; i < bridges[node].length; i++) {
            // 방문한 적이 있거나(=부모노드) 연결된 다리가 없다면 건너 뛴다.
            if (visited[i] || bridges[node][i] == MAX)
                continue;

            // 연결된 다리가 있다면 다이너마이트의 개수를 더한다.
            sum += findMinDynamite(i, node, visited);
        }
        // 만약 sum이 0인 경우는 단말 노드의 경우. 위의 경우에는 항상 parent와 node 간의 연결을 끊는 다이너마이트의 개수를 리턴
        // 그렇지 않은 경우에는, node에서 자식 노드들을 끊는 다이너마이트의 개수 sum과
        // parent와 node를 끊는 다이너마이트의 개수 중 더 작은 값을 리턴한다.
        return sum == 0 ? bridges[parent][node] : Math.min(bridges[parent][node], sum);
    }
}