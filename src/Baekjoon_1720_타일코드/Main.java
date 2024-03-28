/*
 Author : Ruel
 Problem : Baekjoon 1720번 타일 코드
 Problem address : https://www.acmicpc.net/problem/1720
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1720_타일코드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2 * n크기의 넓은 판을 1 * 2, 2 * 1, 2 * 2 크기의 타일들로 채우려고 한다.
        // 넓은 판을 교환하다보니 좌우를 뒤집으면 같은 경우가 발생한다.
        // 위 경우를 한가지로만 센다고 할 때
        // 판을 채우는 경우의 수를 출력하라
        //
        // DP 문제
        // dp[타일의 길이] = 경우의 수로 세운다.
        // 길이 i의 판을 채우는 경우는
        // 길이 i-1의 판에 가로 1짜리 타일을 하나 덧붙이는 경우와
        // 길이 i-1의 판에 가로 2짜리 타일 두개를 덧붙이는 경우 + 2 * 2짜리 타일을 붙이는 경우이다.
        // 최종적으로 좌우를 뒤집었을 때 같은 경우는 하나로만 세야하므로
        // 원래 하나만 세어진 경우를 제외하고 나머지를 1/2배 해주면 된다.
        // 원래 하나만 세어진 경우는 좌우가 대칭인 경우이다.
        // 길이 i인 판에서 좌우가 대칭인 경우는
        // 짝수 일 때는, 길이가 i/2인 같은 판을 두개 대칭으로 잇거나,
        // (i-2)/2인 판 두개 사이에 가로 2인 타일을 두개 덧붙이거나 2 * 2의 타일을 배치한 경우
        // 홀수 일 때는 (i-1)/2의 판을 좌우대칭으로 붙이고 사이에 가로 1짜리 타일을 둔 경우이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이가 n인 판
        int n = Integer.parseInt(br.readLine());

        // dp
        int[] dp = new int[n + 1];
        // 길이가 0, 1인 경우의 수는 1
        dp[0] = dp[1] = 1;
        // 길이 i인 판을 타일로 채우는 경우는
        // 길이 i-1인 판에 가로 1인 타일을 붙이는 경우와
        // 길이 i-2인 판에 가로 2인 타일 2개를 위아래로 붙인 경우 혹은 2 * 2 타일을 붙인 경우.
        for (int i = 2; i < dp.length; i++)
            dp[i] += dp[i - 1] + dp[i - 2] * 2;
        
        int answer = dp[n];
        int singularity;
        // n 짝수일 때
        // 좌우대칭은 경우의 수는
        // n/2짜리 판을 좌우 대칭으로 붙이는 경우와
        // (n-2)/2 짜리 판을 좌우대칭으로 붙이고, 가운데에 가로 2짜리 타일을 위아래로 붙이거나, 2 * 2 타일을 사용한 경우.
        if (n % 2 == 0)
            singularity = dp[n / 2] + dp[(n - 2) / 2] * 2;
        else        // 홀수일 때는 (n-1)/2짜리 타일을 좌우대칭으로 붙이고 가운데 가로 1짜리 타일을 붙인 경우.
            singularity = dp[n / 2];
        // 답에서, 좌우대칭은 경우를 제외하고 나머지를 1/2배 한다
        answer = (answer - singularity) / 2 + singularity;
        // 답안 출력
        System.out.println(answer);
    }
}