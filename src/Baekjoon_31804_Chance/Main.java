/*
 Author : Ruel
 Problem : Baekjoon 31804번 Chance!
 Problem address : https://www.acmicpc.net/problem/31804
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31804_Chance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 시험에서 우찬이는 a점, 상훈이는 b점(a < b)을 받았다.
        // 우찬이는 마법을 사용하여, 상훈이와 동점이 되기로 했다.
        // 1. 물 주기 -> +1점이 된다
        // 2. 밥 주기 -> *2점이 된다
        // 3. chance! -> *10점이 된다
        // 3번은 최대 한 번만 사용할 수 있다
        // 동점이 되기 위해 사용해야하는 마법의 최소 횟수는?
        //
        // DP 문제
        // dp[점수][chance 사용여부] = 마법 사용 횟수
        // 로 a부터 b점까지 dp를 채워나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 우찬이와 상훈이의 점수
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 우찬이의 점수부터 상훈이의 점수까지
        int[][] dp = new int[b + 1][2];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);

        // 우찬이의 점수부터 시작
        dp[a][0] = 0;
        for (int i = a; i < dp.length; i++) {
            // 1, 2번 마법은 3번 마법 사용 여부와 상관없이 사용 가능
            for (int j = 0; j < dp[i].length; j++) {
                // 초기값이라면 건너뛴다.
                if (dp[i][j] == Integer.MAX_VALUE)
                    continue;
                
                // 1번 마법을 사용할 수 있을 때
                if (i + 1 < dp.length)
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);
                // 2번 마법을 사용할 수 있을 때
                if (i * 2 < dp.length)
                    dp[i * 2][j] = Math.min(dp[i * 2][j], dp[i][j] + 1);
            }
            
            // 3번 마법은 아직 마법을 사용하지 않았을 때만 사용 가능
            // 3번 마법을 사용할 수 있을 때
            if (i * 10 < dp.length)
                dp[i * 10][1] = Math.min(dp[i * 10][1], dp[i][0] + 1);
        }
        
        // 최종 상훈이의 점수일 때, 최소 마법 사용 횟수 출력
        System.out.println(Math.min(dp[b][0], dp[b][1]));
    }
}