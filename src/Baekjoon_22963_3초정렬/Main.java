/*
 Author : Ruel
 Problem : Baekjoon 22963번 3초 정렬
 Problem address : https://www.acmicpc.net/problem/22963
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22963_3초정렬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 최대 3번 수를 바꿀 수 있을 때, 수들을 비내림차순으로 만들고자 한다.
        // 가능하다면 YES와 바꿔야하는 수의 위치와 값을 출력하라
        // 불가능하다면 NO를 출력한다.
        //
        // DP 문제
        // 난이도 기여 항목을 보니 최장증가수열에, 역추적에 사람들이 복잡하게 풀었지만
        // 그냥 간단하게
        // dp[idx][교체횟수] = 마지막 값(가장 큰 값)으로 풀 수 있는 문제
        // 역추적할 때도 A[i]와 dp값이 달라지는 지점을 체크하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < a.length; i++)
            a[i] = Integer.parseInt(st.nextToken());
        
        // dp[idx][교체횟수] = 마지막 값
        int[][] dp = new int[n][4];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 한 개의 수를 여러번 바꿀 수도 있다.
        // 첫번째의 경우 1로 가득 채운 뒤
        Arrays.fill(dp[0], 1);
        // 교체 횟수가 0인 경우만 a[0]으로 교체
        dp[0][0] = a[0];
        // 0 ~ n-2번째 수까지 살펴본다.
        // i = idx
        for (int i = 0; i < dp.length - 1; i++) {
            // j = 교체 횟수
            for (int j = 0; j < dp[i].length; j++) {
                // 값이 초기값이라면 불가능한 경우이므로 건너뛴다.
                if (dp[i][j] == Integer.MAX_VALUE)
                    continue;
                
                // 만약 i+1번째 수가 dp[i][j]보다 같거나 크다면
                // i+1번째 수를 교체하지 않고, 그냥 진행할 수 있다.
                if (a[i + 1] >= dp[i][j])
                    dp[i + 1][j] = Math.min(dp[i + 1][j], a[i + 1]);
                
                // j가 3보다 작은 경우
                // i+1번째 수 대신, 교체 횟수를 한번 사용하여 dp[i][j]로 교체해, 더 낮은 값을 갖도록 할수 있다.
                if (j < 3)
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j]);
            }
        }
        
        // 마지막 idx에 교체횟수와 상관없이 초기값이 아닌 값이 있는지 찾는다.
        // 그 때의 교체 횟수를 idx에 저장
        int idx = -1;
        for (int i = 0; i < dp[n - 1].length; i++) {
            if (dp[n - 1][i] != Integer.MAX_VALUE) {
                idx = i;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        // 모든 수가 초기값이라면 불가능한 경우
        // NO 기록
        if (idx == -1)
            sb.append("NO").append("\n");
        else {
            // 가능한 경우
            // YES 기록 후, 교체 횟수 기록
            sb.append("YES").append("\n").append(idx).append("\n");
            
            // 이제 스택으로 역추적하며, 변경 내역을 살펴본다.
            Stack<int[]> stack = new Stack<>();
            for (int i = n - 1; i >= 0; i--) {
                // dp[i][idx]가 a[i]와 다를 경우
                // 해당 순서에서 값 변경이 일어났다.
                // i와 현재 변경된 값 dp[i][idx]을 스택에 담고, 변경 횟수 차감
                if (dp[i][idx] != a[i])
                    stack.push(new int[]{i, dp[i][idx--]});
            }
            // 값을 꺼내며 답 기록
            while (!stack.isEmpty())
                sb.append(stack.peek()[0] + 1).append(" ").append(stack.pop()[1]).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}