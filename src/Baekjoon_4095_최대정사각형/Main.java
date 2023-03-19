/*
 Author : Ruel
 Problem : Baekjoon 4095번 최대 정사각형
 Problem address : https://www.acmicpc.net/problem/4095
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
 */

package Baekjoon_4095_최대정사각형;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1과 0으로 이루어진 NxM크기의 행렬이 주어졌을 때,
        // 1로만 이루어진 가장 큰 정사각형 부분 행렬 찾는 프로그램을 작성하시오.
        //
        // DP문제
        // (i, j)를 오른쪽 아래 끝 점으로 갖는 크기 2인 정사각형이 되기 위해서는
        // (i-1, j-1), (i-1, j), (i, j-1)도 모두 1이여야한다.
        // 따라서 이를 확장해서 생각해본다면
        // (i, j)를 오른쪽 아래 끝 점으로 갖는 크기 n인 정사각형이 되기 위해서는
        // (i-1, j-1), (i-1, j), (i, j-1)을 끝점으로 갖는 사각형들의 크기가 n-1이 되어야함을 의미한다.
        // 이를 이용하여 행렬 내의 가장 큰 사각형의 크기를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            // 크기가 0, 0일 경우 종료
            if (n + m == 0)
                break;
            
            // 입력으로 주어지는 행렬
            int[][] matrix = new int[n][];
            for (int i = 0; i < matrix.length; i++)
                matrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            
            // DP 계산
            int[][] dp = new int[n][m];
            int max = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    // 현재 칸이 0이라면 건너뛴다.
                    if (matrix[i][j] == 0)
                        continue;
                    
                    // 현재 칸이 값이 1이므로 최소 크기는 1
                    dp[i][j] = 1;
                    // i와 j 모두 0보다 크다면
                    // (i-1, j-1), (i-1, j), (i, j-1) 위치의 DP를 살펴보고
                    // 가장 작은 크기의 사각형 + 1한 크기가 현재 위치에서 찾은 최대 사격형의 크기이다.
                    if (i > 0 && j > 0)
                        dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    // 찾은 사각형의 크기가 최댓값을 갱신하는지 확인
                    max = Math.max(max, dp[i][j]);
                }
            }
            // 이번 테스트케이스의 답안 기록
            sb.append(max).append("\n");
            st = new StringTokenizer(br.readLine());
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}