/*
 Author : Ruel
 Problem : Jungol 5399번 대머리의 유전학
 Problem address : https://jungol.co.kr/problem/5399
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5399_대머리의유전학;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 대머리는 반드시 한 세대는 건너뛴다고 한다.
        // n명이 주어지며, 1번이 시조이며, 2번부터 각자의 부모가 주어진다고 할 때
        // 가능한 대머리의 최대 수는?
        //
        // 트리 DP 문제
        // 트리를 따라가며, 각자가 대머리거나 아닐 때, 자신을 포함한 하위 트리에서 대머리가 총 몇명인지를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 가계도의 인원 n
        int n = Integer.parseInt(br.readLine());

        // 각자의 자식 노드
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());

        // n이 1 초과인 경우에만 각각의 부모 노드 입력을 받음
        if (n > 1) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n - 1; i++)
                child.get(Integer.parseInt(st.nextToken())).add(i + 2);
        }

        // dp[idx][대머리 여부] = 자신을 포함한 하위 트리의 최대 대머리의 수
        int[][] dp = new int[n + 1][2];
        // 방문 여부
        boolean[] visited = new boolean[n + 1];
        // dfs로 탐색할 경우, 스택 오버 플로우가 날 수 있으므로 스택으로 탐색
        Stack<Integer> stack = new Stack<>();
        // 1번부터 탐색
        stack.push(1);
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            // 자식 노드들을 방문하고, cur로 돌아온 경우
            if (visited[cur]) {
                // 자신이 대머리인 경우.
                // 자신이 대머리에 포함되므로 초기값은 1
                dp[cur][1] = 1;
                for (int ch : child.get(cur)) {
                    // 자신이 대머리가 아닌 경우는, 자식이 대머리이든 아니든 더 많은 수의 값을 가져와 더하고
                    dp[cur][0] += Math.max(dp[ch][0], dp[ch][1]);
                    // 자신이 대머리인 경우는 자식이 대머리가 아닌 경우만 가져와 더한다.
                    dp[cur][1] += dp[ch][0];
                }
            } else if (child.get(cur).isEmpty())    // 자식 노드가 비어있는 경우. 단말 노드인 경우. 자신이 대머리인 경우만 체크
                dp[cur][1] = 1;
            else {
                // 처음 방문했고, 자식 노드가 있는 경우
                // 자신을 다시 담고
                stack.push(cur);
                // 자식 노드들을 담는다.
                for (int ch : child.get(cur))
                    stack.push(ch);
                // 그리고 방문 체크
                visited[cur] = true;
            }
        }
        // 시조가 대머리인 경우와 대머리가 아닌 경우, 두 경우 모두 중 더 많은 경우를 출력
        System.out.println(Math.max(dp[1][0], dp[1][1]));
    }
}