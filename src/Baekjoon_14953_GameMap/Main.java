/*
 Author : Ruel
 Problem : Baekjoon 14953번 Game Map
 Problem address : https://www.acmicpc.net/problem/14953
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14953_GameMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] connected, dp;
    static List<List<Integer>> roads;

    public static void main(String[] args) throws IOException {
        // n개의 도시가 m개의 도로로 연결되어있다.
        // 한 플레이어가 이 도시를 정복하는데,
        // 다음 정복할 도시는 이전에 정복한 도시보다 더 많은 도시와 도로로 연결되어있어야한다.
        // 이 때, 정복할 수 있는 가장 많은 수의 도시는?
        //
        // DFS, DP 문제
        // 한 도시부터 시작하여 정복할 수 있는 최대 도시의 수는 정해져있다.
        // 따라서 dp[도시] = 정복할 수 있는 최대 도시의 수로 정한다.
        // 그 후, 다른 도시에서, dp값이 있는 도시로 왔을 때, 탐색하지 않고, 값을 참조하여 빠르게 계산을 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 도시마다 연결된 도시의 수
        connected = new int[n];
        dp = new int[n];
        roads = new ArrayList<>();
        for (int i = 0; i < n; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            roads.get(a).add(b);
            roads.get(b).add(a);
            connected[a]++;
            connected[b]++;
        }

        int answer = 0;
        // 0번 도시부터 n-1번 도시까지 DFS
        // BFS를 하며 정복할 수 있는 도시의 최댓값을 answer에 기록
        for (int i = 0; i < dp.length; i++)
            answer = Math.max(answer, dfs(i));
        // 답 출력
        System.out.println(answer);
    }
    
    // DFS로 탐색
    static int dfs(int node) {
        // dp값이 존재한다면 바로 값 참조
        if (dp[node] != 0)
            return dp[node];

        // 없다면 현재 node에서 진행할 경우
        // 정복할 수 있는 최대 도시의 수를 구한다.
        int max = 0;
        for (int next : roads.get(node)) {
            // 연결된 도시가 더 많을 경우에만 진행
            if (connected[next] > connected[node])
                max = Math.max(max, dfs(next));
        }
        // 찾은 max값에 현재 도시 node를 포함하여 +1한 값을
        // dp[node]에 기록하고 값 반환
        return dp[node] = max + 1;
    }
}