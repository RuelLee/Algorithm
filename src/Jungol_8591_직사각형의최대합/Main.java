/*
 Author : Ruel
 Problem : Jungol 8591번 직사각형의 최대 합
 Problem address : https://jungol.co.kr/problem/8591
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8591_직사각형의최대합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기 격자 칸 안에 값들이 주어진다.
        // 직시각형 범위의 합을 최대로 하고자할 때, 그 값은?
        //
        // 누적합 문제
        // n과 m이 800까지로 주어져, 2차원 누적합으로 두 점을 찍어 범위 내의 누적합을 구하는 방식으로는 불가능했다.
        // 카데인 알고리즘이라는 걸 사용했는데
        // 먼저, 열을 기준으로 누적합을 구한다.
        // 그러고 가능한 모든 행 쌍을 구해, 해당 행들에 해당하는 누적합들을 1차원 배열로 구한다.
        // 그리고 누적합을 열을 따라 비교하며, 이전까지의 값 + 현재값이 더 큰지, 현재값이 더 큰지를 비교하며 답을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 주어지는 값들
        int[][] array = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                array[i][j] = Integer.parseInt(st.nextToken());
        }

        // 열에 따른 누적합
        long[][] psums = new long[n + 1][m + 1];
        for (int i = 1; i < psums.length; i++) {
            for (int j = 1; j < psums[i].length; j++)
                psums[i][j] = psums[i - 1][j] + array[i - 1][j - 1];
        }

        // 답
        long answer = Long.MIN_VALUE;
        // 선택한 행들로 1차원 배열로 구한다.
        long[] temp = new long[m + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = i; j < n + 1; j++) {
                for (int k = 1; k < m + 1; k++)
                    temp[k] = psums[j][k] - psums[i - 1][k];

                // 여태까지의 합
                long psum = Long.MIN_VALUE;
                for (int k = 1; k < m + 1; k++) {
                    // psum + k번째 값이 k번째 값 단일보다 더 큰 경우
                    // 누적
                    if (0 >= psum)
                        psum = temp[k];
                    else        // 아닌 경우, k번째 값만 선택
                        psum += temp[k];
                    // 값이 최대인지 비교
                    answer = Math.max(answer, psum);
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}