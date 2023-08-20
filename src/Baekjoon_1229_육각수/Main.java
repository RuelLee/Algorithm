/*
 Author : Ruel
 Problem : Baekjoon 1229번 육각수
 Problem address : https://www.acmicpc.net/problem/1229
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1229_육각수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 육각수는 육각형을 이용해 정의할 수 있다.
        // hn은 한 변에 점 1, 2, ..., n개가 있는 육각형을 점 하나만 겹치게 그렸을 때
        // 존재하는 서로 다른 점의 개수이다.
        // n이 주어질 때, n을 육각수들의 최소 개수 합으로 만들고자 할 때, 최소 개수는?
        //
        // DP 문제
        // 먼저 육각수들을 모두 구한다.
        // 그 후, 특정한 수 i에 대해
        // (i - 육각수) 를 만드는 최소 개수 + 1(육각수이므로 1번) 개수를 비교하며 최소값을 모두 갱신해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 최소 육각수의 합을 구할 n
        int n = Integer.parseInt(br.readLine());

        // 육각수
        // 문제에서 n이 최대 100만까지 주어지므로
        // 100만 이하의 육각수는 707개가 있다.
        int[] hexagonalNumbers = new int[707];
        // 첫번째 육각수는 1
        hexagonalNumbers[0] = 1;
        // 육각수 찾기.
        for (int i = 1; i < hexagonalNumbers.length; i++)
            hexagonalNumbers[i] = hexagonalNumbers[i - 1] + i * 6 - (i * 2 - 1);
        
        // dp[i] = 최소 육각수의 합
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // 1 이상의 수에 대해
        for (int i = 1; i < dp.length; i++) {
            // i보다 작은 육각수를 모두 대입해본다.
            for (int hexagonalNum : hexagonalNumbers) {
                if (hexagonalNum > i)
                    break;
                
                // (i - hexagonalNum)를 만드는데 드는 최소 육각수의 개수 합 + 1이
                // i를 만드는 최소 육각수의 합인지 비교하고 값 갱신
                dp[i] = Math.min(dp[i], dp[i - hexagonalNum] + 1);
            }
        }
        // n을 만드는 최소 육각수의 합 출력
        System.out.println(dp[n]);
    }
}