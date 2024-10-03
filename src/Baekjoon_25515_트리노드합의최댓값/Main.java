/*
 Author : Ruel
 Problem : Baekjoon 25515번 트리 노드 합의 최댓값
 Problem address : https://www.acmicpc.net/problem/25515
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25515_트리노드합의최댓값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> connections;
    static long[] values;

    public static void main(String[] args) throws IOException {
        // n개의 노드와 n-1개의 간선으로 구성된 트리가 주어진다.
        // 각 노드에는 정해진 값이 있으며, 해당 값은 처음 방문했을 때 가산된다.
        // 0번인 루트 노드에서 시작하여, 가산되는 값을 최대화하고자할 때, 그 값은?
        //
        // 트리에서 DP 문제
        // 각 노드에서 시작하여, 자식 노드로 퍼져나가며
        // 자신보다 하위 노드를 방문했을 때, 얻을 수 있는 값이 양수인지 따져,
        // 하위 노드로 탐색하는 것이 이익인지, 불이익인지 따져가며 DP를 채운다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());

        // n-1개의 간선
        connections = new ArrayList<>();
        for (int i = 0; i < n; i++)
            connections.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            connections.get(Integer.parseInt(st.nextToken())).add(Integer.parseInt(st.nextToken()));
        }
        
        // 각 노드의 값
        values = Arrays.stream(br.readLine().split(" ")).mapToLong(Integer::parseInt).toArray();
        // 해당 노드를 방문할 때 얻을 수 있는 최대값
        long[] dp = new long[n];
        
        // 0번 노드는 루트 노드이므로 반드시 방문.
        // 그 때의 최대값을 구해 출력
        System.out.println(findAnswer(0, dp));
    }
    
    // BFS
    static long findAnswer(int idx, long[] dp) {
        // idx번째 노드를 방문했다면 해당 노드의 값으로
        // dp[idx]를 초기화
        dp[idx] = values[idx];
        
        // 자식 노드를 방문했을 때
        // 양수값이 돌아와 방문할 때, 이익이 되는지 따져, dp[idx] 값에 누적
        for (int child : connections.get(idx))
            dp[idx] = Math.max(dp[idx], dp[idx] + findAnswer(child, dp));
        // 구한 dp[idx]의 최대값 반환.
        return dp[idx];
    }
}