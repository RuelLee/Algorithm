/*
 Author : Ruel
 Problem : Baekjoon 15817번 배수 공사
 Problem address : https://www.acmicpc.net/problem/15817
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15817_배수공사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n종류 파이프의 길이와 개수가 주어진다.
        // 파이프들을 합쳐 길이 x를 만드는 경우의 수를 구하라
        // 파이프를 합치는 순서는 고려하지 않는다.
        //
        // 배낭 문제
        // 같은 길이의 파이프가 여러개 주어지고, 그 순서를 고려하지 않는다는 점을 빼면
        // 일반적인 배낭문제나 다름없다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 파이프 n종류, 원하는 길이 x
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        // 파이프 정보
        int[][] pipes = new int[n][];
        for (int i = 0; i < n; i++)
            pipes[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 길이에 도달하는 경우의 수
        int[] dp = new int[x + 1];
        dp[0] = 1;
        // 순서대로 파이프를 살펴본다.
        for (int[] pipe : pipes) {
            // x부터 내림차순으로
            // 현재 파이프를 최대 pipe[1]개만큼 이었을 때 i 길이를 만드는 경우의 수를 구한다.
            for (int i = x; i - pipe[0] >= 0; i--) {
                // 최대 1개부터, pipe[1]개 만큼 사용할 수 있고
                // 그 길이 합이 i를 넘어서는 안된다.
                for (int j = 1; j <= pipe[1] && i - pipe[0] * j >= 0; j++)
                    dp[i] += dp[i - pipe[0] * j];
            }
        }
        // 길이 x를 만드는 경우의 수 출력
        System.out.println(dp[x]);
    }
}