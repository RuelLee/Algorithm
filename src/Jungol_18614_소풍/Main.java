/*
 Author : Ruel
 Problem : Jungol 18614번 소풍
 Problem address : https://jungol.co.kr/problem/18614
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_18614_소풍;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시가 전위 순회 순서로 주어진다.
        // 각 도시는 매력도 V와 노드 자식의 수 C가 주어진다.
        // 한 도시를 중복 방문하지 않으며 최소 한 개 이상의 도시를 방문할 때, 매력도 합의 최댓값은?
        //
        // 트리에서 DP 문제, DFS 문제
        // 도시들이 주어지는대로 전위 순회를 하며
        // 1. 현재 노드만 방문하는 경우
        // 2. 한 자식 노드에서 시작하여, 자신을 거쳐, 다른 자식 노드로 가는 경우
        // 3. 자신이 경로가 되어, 부모 노드를 거치는 경우. 세 가지를 따지면 된다.
        // dp[i] = i로부터 한 자식 노드들을 재귀적으로 방문하여 얻을 수 있는 매력도의 최대합

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 도스
        int n = Integer.parseInt(br.readLine());
        // 노드 정도
        int[][] nodes = new int[n][2];
        // dp
        long[] dp = new long[n];
        // 깊이가 최대 990이므로 스택으로 방문
        Stack<Integer> stack = new Stack<>();
        // 답
        long ans = Long.MIN_VALUE;
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            // 노드 정보
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                nodes[i][j] = Integer.parseInt(st.nextToken());
            // 1번 경우 현재 노드만 단일 방문했을 때의 매력도
            ans = Math.max(ans, dp[i] = nodes[i][0]);
            // 스택에 추가
            stack.push(i);

            // 더 이상 자식 노드가 없는 경우
            while (!stack.isEmpty() && nodes[stack.peek()][1] == 0) {
                // 현재 노드를 뽑고.
                int cur = stack.pop();
                // 부모 노드가 존재한다면
                if (!stack.isEmpty()) {
                    // 현재 dp[부모노드]에는 다른 자식 노드 -> 부모 노드에 이르는 현재까지의 매력도 최대합이 계산되어있다.
                    // 2번 경우인,  dp[cur] + dp[부모노드]를 할 경우. 다른 자식 노드 -> 부모 노드 -> 나를 거쳐 다른 자식으로 퍼져나가는 경우의 최대 매력도 합을 구할 수 있다.
                    ans = Math.max(ans, dp[stack.peek()] + dp[cur]);
                    // 3번 경우인 cur을 거쳐 부모노드로 이르는 최대 dp값 갱신
                    dp[stack.peek()] = Math.max(dp[stack.peek()], dp[cur] + nodes[stack.peek()][0]);
                    // 자식 노드의 수 차감
                    nodes[stack.peek()][1]--;
                }
            }
        }
        // 답 출력
        System.out.println(ans);
    }
}