/*
 Author : Ruel
 Problem : Jungol 2607번 줄 세우기
 Problem address : https://jungol.co.kr/problem/2607
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2607_줄세우기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n개의 수로 이루어진 수열이 주어진다.
        // 각 수를 맨 앞, 혹은 맨 뒤로 보내 오름차순 정렬하고자 한다.
        // 필요한 최소 이동 수는?
        //
        // dp, 그리디 문제
        // 중요한 점은 이동하지 않는 수들을 구하는 것이다.
        // 이동하지 않기 위해서는 등장 순서대로 살펴봤을 때, 얼마나 많은 수들이 연이어진 오름차순 그대로 나오는가이다.
        // 따라서 dp[i] = 현재 살펴본 수들 사이에 i를 마지막에 붙였을 때, 얻을 수 있는 최대 연이은 수열의 길이
        // 로 정의한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] child = new int[n];
        for (int i = 0; i < child.length; i++)
            child[i] = Integer.parseInt(st.nextToken());

        // dp[i] = 현재 살펴본 수들 사이에 i를 마지막에 붙였을 때, 얻을 수 있는 최대 연이은 수열의 길이
        int[] dp = new int[n + 1];
        int max = 0;
        // 수열을 차례대로 채워가며, dp를 채운다.
        // i번째 수를 child[i]라 할 때
        // child[i] -1 까지의 길이를 보고 +1을 해 채운다.
        // 기본 값이 0이므로 child[i] - 1이 등장하지 않았다면 알아서 dp[child[i]] = 1이 채워진다.
        // 그러며 얻는 최대 길이를 구한다.
        for (int i = 0; i < child.length; i++)
            max = Math.max(max, dp[child[i]] = dp[child[i] - 1] + 1);
        // n에서 해당 길이를 제외한 나머지들을 이동시켜야한다.
        System.out.println(n - max);
    }
}