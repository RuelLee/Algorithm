/*
 Author : Ruel
 Problem : Baekjoon 24548번 도로 정보
 Problem address : https://www.acmicpc.net/problem/24548
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24548_도로정보;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 도로 정보가 주어진다.
        // 도로 정보는 나무 T, 잔디 G, 울타리 F, 사람 P로 주어진다.
        // 흥미로운 구간이란 구간 내에 포함되는 물체의 배수가 3의 배수인 경우이다.
        // 흥미로운 구간의 개수를 출력하라
        //
        // 누적합, 조합 문제
        // 3의 배수로 떨어지느냐 문제이므로
        // 누적합을 구하면서 현재까지의 속한 물체들의 mod 3 값의 갯수를 계산한다.
        // 어느 두 지점 a, b에서 a의 누적 나무 수가 1개, b의 누적 나무 수가 7개라 한다면
        // 둘 다 두 지점을 구간으로 선택했을 때, 나무의 개수가 6개로 3의 배수가 된다.
        // 이처럼 mod 3 값이 같은 두 지점을 선택하면 해당 지점에 속한 각각 물체의 개수 합이 3의 배수가 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 도로
        int n = Integer.parseInt(br.readLine());
        // 도로 정보
        String s = br.readLine();
        
        // 누적합
        int[] sums = new int[4];
        int[][][][] dp = new int[3][3][3][3];
        // 초기값.
        // 어떠한 물체도 속하지 않은 시작점
        dp[0][0][0][0]++;
        for (int i = 0; i < s.length(); i++) {
            // 해당하는 물체의 개수 증가
            switch (s.charAt(i)) {
                case 'T' -> sums[0]++;
                case 'G' -> sums[1]++;
                case 'F' -> sums[2]++;
                case 'P' -> sums[3]++;
            }
            // 각각을 mod 3한 값으로 해당 지점을 표현하고
            // 그 지점의 개수 증가.
            dp[sums[0] % 3][sums[1] % 3][sums[2] % 3][sums[3] % 3]++;
        }

        int sum = 0;
        // 모든 경우의 수를 살펴보며
        // 해당 경우에서 속한 지점들 중에 두 지점을 뽑는 경우의 수 계산
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    for (int l = 0; l < dp[i][j][0].length; l++)
                        sum += dp[i][j][k][l] * (dp[i][j][k][l] - 1) / 2;
                }
            }
        }
        // 답 출력
        System.out.println(sum);
    }
}