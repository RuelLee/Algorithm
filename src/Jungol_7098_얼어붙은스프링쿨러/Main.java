/*
 Author : Ruel
 Problem : Jungol 7098번 얼어붙은 스프링쿨러
 Problem address : https://jungol.co.kr/problem/7098
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_7098_얼어붙은스프링쿨러;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<int[]>> child;
    static int[] depth, dp;

    public static void main(String[] args) throws IOException {
        // n개의 노드가 주어지고, n-1개의 간선이 주어지며, 트리 형태이다.
        // 루트 노드는 c로 주어진다.
        // 각 간선은 두 노드와 가중치가 주어진다. 가중치는 해당 간선의 있는 밸브를 잠그는데 필요한 힘이다.
        // 단말 노드에 스프링쿨러가 연결되어 물을 배출한다.
        // 모든 스프링쿨러로 가는 물을 차단하기 위해, 잠궈야하는 밸브들의 힘의 합을 최소화하고자할 때, 그 값은?
        //
        // 트리, dp 문제
        // dp를 통해, 단말 노드로부터, 각 노드를 잠구기 위한 최소 힘을 구한다.
        // 각 노드는 자신과 부모 노드의 밸브를 잠구는 경우와 자신과 자식 노드들의 밸브를 잠구는 경우의 힘을 비교하여
        // 더 작은 값을 취하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 자식 노드들을 저장할 리스트
        child = new ArrayList<>();
        for (int i = 0; i < 1001; i++)
            child.add(new ArrayList<>());
        // 각 노드의 깊이
        depth = new int[1001];
        // 각 노드를 잠구는데 필요한 힘
        dp = new int[1001];
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 노드의 개수 n, 루트 노드 c
            int n = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 리스트, depth 배열 초기화
            for (int i = 1; i <= n; i++)
                child.get(i).clear();
            Arrays.fill(depth, 1, n + 1, Integer.MAX_VALUE);

            // 간선 입력
            for (int i = 0; i < n - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());

                child.get(u).add(new int[]{v, w});
                child.get(v).add(new int[]{u, w});
            }

            // 루트 노드의 깊이와 힘 초기 설정
            depth[c] = 0;
            dp[c] = Integer.MAX_VALUE;
            // 큐를 통해 각 노드의 깊이를 bfs로 계산
            queue.offer(c);
            // 스택을 통해 bfs 방문 순서를 역순으로 계산하며, 부모 <->자신 노드의 밸브와 자신 <->자식노드들의 밸브 힘 합을 비교하는데 사용한다.
            stack.push(c);
            while (!queue.isEmpty()) {
                // 현재 cur
                int cur = queue.poll();

                // 자식 노드들의 깊이, dp를 채우고
                // 스택과 큐에 추가
                for (int[] next : child.get(cur)) {
                    if (depth[next[0]] > depth[cur]) {
                        depth[next[0]] = depth[cur] + 1;
                        dp[next[0]] = next[1];
                        stack.push(next[0]);
                        queue.offer(next[0]);
                    }
                }
            }

            // 스택을 통해 역순으로 방문하며
            while (!stack.isEmpty()) {
                int cur = stack.pop();

                // 자식노드들의 밸브를 잠구는데 필요한 힘의 합
                int sum = 0;
                for (int[] ch : child.get(cur)) {
                    if (depth[ch[0]] > depth[cur])
                        sum += dp[ch[0]];
                }
                // 자식 노드들이 있고, 그 값이 부모 <-> 자신보다 작다면 그 값을 반영
                if (sum != 0)
                    dp[cur] = Math.min(dp[cur], sum);
            }
            // 루트 노드에서 계산된 결과를 기록
            sb.append(dp[c]).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}