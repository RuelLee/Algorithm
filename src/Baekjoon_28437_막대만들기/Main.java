/*
 Author : Ruel
 Problem : Baekjoon 28437번 막대 만들기
 Problem address : https://www.acmicpc.net/problem/28437
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28437_막대만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n종류의 기본 막대의 길이들이 주어진다.
        // 이것들을 이용하여 길게 늘려 q종류의 막대들을 만들려고 한다.
        // 막대를 늘리는 방법에는 기본 막대에 k를 설정하여 k배로 만드는 방법이 있다.
        // 만들고자 하는 막대들을 만드는 방법의 가짓수는?
        //
        // dp 문제
        // 기본 막대와 목표 막대들 중 가장 길이가 긴 막대를 기준으로 dp를 만든다.
        // 그리고 작은 값부터 살펴가며, 각 막대기를 늘렸을 때 가능한 모든 경우를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 기본 막대
        int n = Integer.parseInt(br.readLine());
        int[] as = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // q개의 목표 막대 길이
        int q = Integer.parseInt(br.readLine());
        int[] ls = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp[a] = a 길이의 막대를 만드는 가짓수
        int[] dp = new int[Math.max(Arrays.stream(as).max().getAsInt(), Arrays.stream(ls).max().getAsInt()) + 1];
        // 기본 막대들에 대해서는 기본값이 1
        for (int a : as)
            dp[a] = 1;

        // 작은 막대들부터 살펴본다.
        for (int i = 0; i < dp.length; i++) {
            // i인 막대를 만드는 가짓수가 없다면 건너뛰고
            if (dp[i] == 0)
                continue;

            // 존재한다면 dp의 범위를 벗어나지 않는 한도내에서 배수를 증가시키며
            // 만들 수 있는 막대들을 계산한다.
            for (int j = 2; i * j < dp.length; j++)
                dp[i * j] += dp[i];
        }

        StringBuilder sb = new StringBuilder();
        // 목표한 막대들에 도달할 수 있는 가짓수를 기록한다.
        for (int l : ls)
            sb.append(dp[l]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답안 출력
        System.out.println(sb);
    }
}