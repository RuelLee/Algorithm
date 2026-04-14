/*
 Author : Ruel
 Problem : Baekjoon 6144번 Charm Bracelet
 Problem address : https://www.acmicpc.net/problem/6144
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6144_CharmBracelet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 장식이 주어진다. 각 장식은 무게와 선호도가 있다.
        // m개의 무게 내에 가장 많은 장식 선호도 합을 갖고 싶다.
        // 이 때의 선호도 합은?
        //
        // 간단한 배낭 문제
        // 무게 제한 m 내에 가능한 장식 조합의 선호도 최대 합을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 장식, m의 무게 제한
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 장식
        int[][] charms = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            charms[i][0] = Integer.parseInt(st.nextToken());
            charms[i][1] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[m + 1];
        // 모든 장식을 둘러본다.
        for (int[] charm : charms) {
            // 하나의 배열로 끝내기 위해, 역순으로 살펴본다.
            // (정순으로 살펴볼 경우, 동일한 장식이 여러번 쓰일 수 있다.)
            for (int j = m - charm[0]; j >= 0; j--)
                dp[j + charm[0]] = Math.max(dp[j + charm[0]], dp[j] + charm[1]);
        }
        // 답 출력
        System.out.println(dp[m]);
    }
}