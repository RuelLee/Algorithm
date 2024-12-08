/*
 Author : Ruel
 Problem : Baekjoon 5444번 시리얼 넘버
 Problem address : https://www.acmicpc.net/problem/5444
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5444_시리얼넘버;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 고유한 번호를 갖고 있는 n개의 기타들이 주어진다.
        // 주인공 기타들의 고유 번호 합은 m의 배수를 이룬다고 한다.
        // 주인공의 기타 개수로 가능한 최댓값은 얼마인가?
        //
        // 배낭 문제
        // 고유 번호들의 합이 m의 배수를 이룬다.
        // 메모리 공간을 낭비할 필요는 없으므로, 나머지값에 따른 처리를 한다.
        // dp[처리한 기타 순서][시리얼 번호 합의 나머지 값] = 기타의 수

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트 케이스
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int t = 0; t < testCase; t++) {
            st = new StringTokenizer(br.readLine());
            // 기타의 수, 배수 m
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            // 기타의 시리얼 번호
            int[] guitars = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < guitars.length; i++)
                guitars[i] = Integer.parseInt(st.nextToken());

            // 이전 값만 체크하면 되므로
            // dp[2][시리얼 번호의 합] = 기타의 수
            int[][] dp = new int[2][m];
            for (int[] d : dp)
                Arrays.fill(d, -1);
            dp[0][0] = 0;

            for (int i = 0; i < guitars.length; i++) {
                for (int j = 0; j < m; j++) {
                    // 값이 없는 경우 건너뜀.
                    if (dp[i % 2][j] == -1)
                        continue;
                    
                    // 나머지 j를 만드는 것이 가능한 경우.
                    
                    // 현재 사용하는 dp 배열
                    int current = i % 2;
                    // 다음 값을 기록할 dp 배열
                    int next = (i + 1) % 2;
                    
                    // 현재 기타를 포함하여 만들어지는 나머지 값
                    int sum = (j + guitars[i]) % m;
                    dp[next][sum] = Math.max(dp[next][sum], dp[current][j] + 1);
                    
                    // 현재 기타를 넣지 않았을 때의 값도 기록
                    dp[next][j] = Math.max(dp[next][j], dp[current][j]);
                }
            }
            // 최종적으로 모든 기타를 살펴본 후
            // 나머지가 0인 경우가 기타 고유 번호들이 합이 m의 배수인 경우이므로
            // 해당 값을 기록한다.
            sb.append(dp[n % 2][0]).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}