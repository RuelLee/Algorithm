/*
 Author : Ruel
 Problem : Baekjoon 1749번 점수따먹기
 Problem address : https://www.acmicpc.net/problem/1749
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1749_점수따먹기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 행렬이 주어진다.
        // 부분 행렬의 합 중 가장 큰 값을 출력한다.
        //
        // 2차원 누적합 문제
        // DP[i][j] = (0, 0), (i, j)를 대각선으로 갖는 부분 행렬의 합으로 한다.
        // ○○○○
        // ○●●○
        // ○●●○
        // ○○○○ 부분의 합을 구하려면
        //
        // ○○○○  ●●●○      ●●●○     ●○○○             ●○○○
        // ○●●○  ●●●○      ○○○○     ●○○○             ○○○○
        // ○●●○  ●●●○      ○○○○     ●○○○             ○○○○
        // ○○○○  ○○○○에서  ○○○○ 과  ○○○○ 부분을 빼고  ○○○○ 을 더해준다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 입력으로 주어지는 행렬
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        long[][] matrix = new long[n][];
        for (int i = 0; i < matrix.length; i++)
            matrix[i] = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        
        // 2차원 누적합
        long[][] psum = new long[n + 1][m + 1];
        // psum[a][b] = (0, 0) ~ (a, b)를 대각선으로 갖는 부분 행렬의 합
        for (int i = 1; i < psum.length; i++) {
            for (int j = 1; j < psum[i].length; j++)
                psum[i][j] = psum[i - 1][j] + psum[i][j - 1] + matrix[i - 1][j - 1] - psum[i - 1][j - 1];
        }

        long max = Integer.MIN_VALUE;
        // (i, j)를 왼쪽 위 끝점
        for (int i = 1; i < psum.length; i++) {
            for (int j = 1; j < psum[i].length; j++) {
                // k, l을 오른쪽 아래 끝점
                for (int k = i; k < psum.length; k++) {
                    // 으로 갖는 부분 행렬의 합이 최댓값을 갱신하는지 확인한다.
                    for (int l = j; l < psum[k].length; l++)
                        max = Math.max(max,
                                psum[k][l] - psum[i - 1][l] - psum[k][j - 1] + psum[i - 1][j - 1]);
                }
            }
        }
        // 구해진 답 출력
        System.out.println(max);
    }
}