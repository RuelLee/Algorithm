/*
 Author : Ruel
 Problem : Baekjoon 13703번 물벼룩의 생존확률
 Problem address : https://www.acmicpc.net/problem/13703
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13703_물벼룩의생존확률;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수심 k cm에 있는 물벼룩은 1초마다 1/2의 확률로 위 또는 아래로 1cm 이동한다.
        // 수면에 닿으면 물벼룩은 죽는다.
        // n초 후 생존할 확률이 s / 2^n일 때 s를 구하라
        //
        // dp 문제
        // s / 2^n 에서 s를 구하라는 말은 결국 생존하는 경우의 수를 구하라는 말과 같다.
        // 따라서 매 초마다 수심 x에 존재하는 경우의 수를 모두 구해
        // n초일 때 0이 아닌 곳에 있는 경우의 수를 모두 더하면 된다.
        // n이 최대 63까지 주어지므로 int 타입이 아닌 long 타입을 사용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 처음 위치 k
        int k = Integer.parseInt(st.nextToken());
        // n초 뒤 위치
        int n = Integer.parseInt(st.nextToken());
        
        // dp[초][수심]
        long[][] dp = new long[n + 1][k + n + 1];
        dp[0][k] = 1;
        
        // 매 초마다
        for (int i = 0; i < dp.length - 1; i++) {
            // 0의 위치에는 이미 죽은 물벼룩이므로, 1부터 시작
            for (int j = 1; j < dp[i].length; j++) {
                // 경우의 수가 0인 경우 세지 않는다.
                if (dp[i][j] == 0)
                    continue;

                // 위로 1cm 상승하거나
                dp[i + 1][j - 1] += dp[i][j];
                // 아래로 1cm 하강한다.
                if (j + 1 < dp[i + 1].length)
                    dp[i + 1][j + 1] += dp[i][j];
            }
        }

        // n초일 때 0이 아닌 위치에 대한 경우의 수를 모두 더한다.
        long sum = 0;
        for (int j = 1; j < dp[n].length; j++)
            sum += dp[n][j];
        // 결과 출력.
        System.out.println(sum);
    }
}