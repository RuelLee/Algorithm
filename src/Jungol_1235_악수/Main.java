/*
 Author : Ruel
 Problem : Jungol 1235번 악수
 Problem address : https://jungol.co.kr/problem/1235
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1235_악수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // p명의 사람이 원형으로 둘러 앉아 있다.
        // 각 사람은 반드시 누군가와 악수를 해야한다.
        // 모두 동시에 악수를 할 때, 팔이 교차하는 일은 없어야한다.
        // 이 때, 각각이 원하는 맥주 브랜드가 주어질 때
        // 가장 많은 쌍들이 서로 같은 맥주 브랜드를 좋아하도록 악수한다할 때
        // 쌍의 개수는?
        //
        // DP 문제
        // dp[i][j] = i ~ j까지의 범위 사람들이 악수할 때, 서로 같은 브랜드를 좋아하는 사람들의 쌍 개수
        // 라고 정의하고 dp를 채워나간다.
        // dp[i][j]는 dp[i+1][j-1]까지의 상태에 i와 j번 사람이 악수하는 경우
        // 그리고 i와 mid까지의 악수 결과 + (mid + 1) ~ j까지의 악수 결과를 합산하는 경우가 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // p명의 인원
        int p = Integer.parseInt(br.readLine().trim());
        // 각각의 선호 맥주
        int[] beers = new int[p];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < p; i++)
            beers[i] = Integer.parseInt(st.nextToken());

        //  dp[i][j] = i ~ j번까지 사람이 악수할 때
        // 같은 맥주를 좋아하는 쌍의 개수
        int[][] dp = new int[p][p];
        // 반드시 악수해야하므로, 맨 앞 사람과 맨 뒷 사람의 번호 차이는 홀수개가 나야한다.
        for (int diff = 1; diff < p; diff += 2) {
            // i+1 ~ i + diff -1번까지의 결과에 i와 i+diff번 사람이 악수하는 경우
            for (int i = 0; i + diff < p; i++) {
                dp[i][i + diff] = Math.max(dp[i][i + diff], dp[i + 1][i + diff - 1] + (beers[i] == beers[i + diff] ? 1 : 0));

                // i와 j까지의 결과 + (j+1) ~ (i + diff)까지의 결과
                for (int j = i + 1; j < i + diff; j++)
                    dp[i][i + diff] = Math.max(dp[i][i + diff], dp[i][j] + dp[j + 1][i + diff]);
            }
        }
        // 답 출력
        System.out.println(dp[0][p - 1]);
    }
}