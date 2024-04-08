/*
 Author : Ruel
 Problem : Baekjoon 27212번 미팅
 Problem address : https://www.acmicpc.net/problem/27212
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27212_미팅;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // A 대학의 학생 N명, B 대학의 학생 M명에 대한 성격이 수로 주어진다.
        // 그리고 각 성격별로 악수를 할 때, 얻을 수 있는 만족도 W가 행렬로 주어진다.
        // 학생들은 최대 한 사람과만 악수할 수 있으며
        // A대학의 xi번째, B대학의 yi번째 학생이 악수를 했다면
        // 다른 쌍에 대해
        // i < j,  xi < xj, yi < yj를 만족해야한다.
        // 얻을 수 있는 만족도 합의 최대값은?
        //
        // DP 문제
        // dp[A대학의 i번째 학생까지][B대학의 j번째 학생까지] = 만족도의 최대값
        // 으로 정하고 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // A대학의 학생 n명, B대학의 학생 m명, 성격의 종류 c
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 성격에 따른 악수에 대한 만족도 값
        int[][] w = new int[c + 1][c + 1];
        for (int i = 1; i < w.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < w[i].length; j++)
                w[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // A대학의 학생들
        int[] a = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < a.length; i++)
            a[i] = Integer.parseInt(st.nextToken());
        
        // B대학의 학생들
        int[] b = new int[m + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < b.length; i++)
            b[i] = Integer.parseInt(st.nextToken());

        // DP
        long[][] dp = new long[n + 1][m + 1];
        // A대학의 i번째 학생
        for (int i = 1; i < dp.length; i++) {
            // B대학의 j번째 학생
            // dp[i][j]는
            // dp[i-1][j] = A의 i-1번째 학생까지, B대학의 j번째 학생까지의 최대 만족도값과
            // dp[i][j-1] = A의 i번째 학생까지, B대학의 j-1번째 학생까지의 최대 만족도값 중 더 큰 값
            // 그리고 dp[i-1][j-1] + w[a[i]][b[j]] = A의 i-1번째 학생까지, B의 j-1번째 학생까지의
            // 만족도 최대값 + A의 i번째 학생과 B의 j번째 학생의 악수할 때의 만족도
            // 이 세 값을 비교하여 가장 큰 값을 저장한다.
            for (int j = 1; j < dp[i].length; j++)
                dp[i][j] = Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1] + w[a[i]][b[j]]);
        }

        // 전체 학생들이 악수했을 때 얻을 수 있는 최대 만족도 합을 출력
        System.out.println(dp[n][m]);
    }
}