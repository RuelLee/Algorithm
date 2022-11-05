/*
 Author : Ruel
 Problem : Baekjoon 14925번 목장 건설하기
 Problem address : https://www.acmicpc.net/problem/14925
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14925_목장건설하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m, n 크기의 땅이 주어지고 치울 수 없는 나무와 바위들이 주어진다.
        // 최대 크기로 정사각형 모양의 목장을 지으려고 할 때, 한 변의 길이는 얼마인가?
        //
        // DP로 최대 정사각형 크기를 재는 문제
        // 각 DP는 자신으로부터 왼쪽, 위로 만들 수 있는 최대 크기의 정사각형을 의미한다
        // r, c의 위치에서, r-1, c-1 과 r-1, c 그리고 r, c-1 의 값을 비교하면 r, c의 값을 계산할 수 있다.
        // DP를 채워나가며 최대 크기의 정사각형을 구하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        // 입력으로 주어지는 땅
        int[][] lands = new int[m][];
        for (int i = 0; i < lands.length; i++)
            lands[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // r, c로부터 왼쪽 위로 만들 수 있는 최대 정사각형 모양 목장의 한 변 길이를
        // DP에 기록한다.
        int[][] dp = new int[m][n];
        int maxSize = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 나무나 바위라면 건너 뛴다.
                if (lands[i][j] != 0)
                    continue;

                // i - 1 < 0 이거나 j - 1 < 0인 경우
                // 범위를 벗어난다. 이번 칸이 0인 점만 반영하여 1로 기록.
                if (i == 0 || j == 0)
                    dp[i][j] = 1;
                // (i-1, j-1), (i-1, j), (i, j-1) 위치의 값을 비교하여 가장 작은 값을 찾고
                // 그 값에 (i, j) 땅을 포함하여 +1 한 값이 dp[i][j]의 값이 된다.
                else
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                // 전체 땅에서 최대 정사각형 목장의 한 변 길이를 기록해나간다.
                maxSize = Math.max(maxSize, dp[i][j]);
            }
        }

        // 전체 땅에서의 답을 출력한다.
        System.out.println(maxSize);
    }
}