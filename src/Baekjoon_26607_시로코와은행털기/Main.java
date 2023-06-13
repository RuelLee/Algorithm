/*
 Author : Ruel
 Problem : Baekjoon 26607번 시로코와 은행털기
 Problem address : https://www.acmicpc.net/problem/26607
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26607_시로코와은행털기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명 중 k명을 뽑아 팀을 이루고자 한다.
        // 팀의 능력치는 a능력의 합 * b 능력의 합이라고 한다.
        // 또한 각 인원의 능력치 합은 x라고 한다.
        // n명의 a, b 능력치가 주어질 때, 구할 수 있는 팀의 최대 능력치는?
        //
        // 배낭 문제
        // 한 사람당 능력치의 합이 x로 일정하므로, 하나의 능력치만 계산한 후
        // 나머지 능력치를 추정하는 것이 가능하다.
        // k명으로 가능한 a 능력치의 합을 모두 구하고,
        // 그 때의 팀 능력치 최대값을 구하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람, k명의 선발 인원, 각 인원의 능력치 합 x
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        boolean[][] dp = new boolean[k + 1][k * x + 1];
        // 0명일 때, 능력치 합 0
        dp[0][0] = true;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // a능력치만 갖고서 계산한다.
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // j명의 인원이 선발됐을 때, 가능한 a 능력치 합을 구한다.
            for (int j = Math.min(i, k - 1); j >= 0; j--) {
                for (int l = dp[j].length - 1; l >= 0; l--) {
                    // j명일 때 능력치 합이 l인 경우가 가능했다면
                    // j + 1명일 때 l + a인 경우가 가능하다.
                    if (dp[j][l])
                        dp[j + 1][l + a] = true;
                }
            }
        }
        
        int answer = 0;
        // k명을 선발해야하므로 k명일 때, 가능한 능력치 합을 살펴보며
        // 팀 능력치 최대값을 구한다.
        for (int i = 0; i < dp[k].length; i++) {
            if (dp[k][i])
                answer = Math.max(answer, i * (k * x - i));
        }
        // 답안 출력
        System.out.println(answer);
    }
}