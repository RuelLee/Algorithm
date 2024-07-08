/*
 Author : Ruel
 Problem : Baekjoon 29704번 벼락치기
 Problem address : https://www.acmicpc.net/problem/29704
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_29704_벼락치기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문제에 대해 소요 시일과 벌금이 주어진다.
        // 앞으로 t일 이내에 풀지 못한 문제만큼 벌금을 내게 된다.
        // 내야하는 벌금을 최소화하고자 할 때, 벌금의 금액은?
        //
        // 배낭 문제
        // 정해진 시간 내에 벌금 합이 가장 크도록 문제들을 고르면 되는 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 문제, 문제를 풀 수 있는 t일
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 문제들
        int[][] problems = new int[n][];
        // 벌금 총합
        int total = 0;
        for (int i = 0; i < problems.length; i++) {
            problems[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            total += problems[i][1];
        }

        // t일 동안 해결할 수 있는 문제들의 벌금 합을 구한다.
        int[] dp = new int[t + 1];
        // 문제마다 살펴본다.
        for (int[] p : problems) {
            // 같은 문제를 중복해서 푸는 것은 안되므로
            // 끝나는 날부터 내림차순으로 계산.
            for (int j = t; j - p[0] >= 0; j--)
                dp[j] = Math.max(dp[j], dp[j - p[0]] + p[1]);
        }
        // 전체 문제 벌금 합에서 t일까지 가능한 푼 문제의 벌금 합을 뺀 값을 출력한다.
        System.out.println(total - dp[t]);
    }
}