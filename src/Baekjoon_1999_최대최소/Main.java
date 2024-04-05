/*
 Author : Ruel
 Problem : Baekjoon 1999번 최대최소
 Problem address : https://www.acmicpc.net/problem/1999
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1999_최대최소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 행렬이 주어진다. n은 최대 250
        // 최대 10만개의 k개의 쿼리가 주어진다.
        // 쿼리는 i j 로 이루어지며
        // i행 j열부터 i+b행 j+b열까지의 부분행렬에 대해 최대값과 최소값의 차이를 묻는다.
        // 쿼리에 대해 답하라
        //
        // DP 문제
        // b값은 정해져있으므로
        // dp를 통해 dp[i][j] = i행 j ~ j + b -1까지의 최대값, 최소값
        // 으로 정하고 각각 구해둔다.
        // 그 후, 쿼리에 대해서는 i행부터 ~ i + b -1까지 돌며 해당하는 부분 행렬의
        // 최대 최소값을 구해 차를 구한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 행렬, 부분 행렬의 가로 세로 길이 b, 쿼리의 개수 k
        int n = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 전체 행렬
        int[][] matrix = new int[n][];
        for (int i = 0; i < matrix.length; i++)
            matrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp[i][j] = i행의 j ~ j+b-1까지의 최대 최소값
        int[][] max = new int[n][n - b + 1];
        int[][] min = new int[n][n - b + 1];
        for (int i = 0; i < n; i++) {
            int row = i;
            PriorityQueue<Integer> maxValue = new PriorityQueue<>((o1, o2) -> Integer.compare(matrix[row][o2], matrix[row][o1]));
            PriorityQueue<Integer> minValue = new PriorityQueue<>(Comparator.comparingInt(o -> matrix[row][o]));
            for (int j = 0; j < b - 1; j++) {
                maxValue.offer(j);
                minValue.offer(j);
            }

            for (int j = 0; j < n - b + 1; j++) {
                maxValue.offer(j + b - 1);
                minValue.offer(j + b - 1);

                while (!maxValue.isEmpty() && maxValue.peek() < j)
                    maxValue.poll();
                while (!minValue.isEmpty() && minValue.peek() < j)
                    minValue.poll();

                max[i][j] = matrix[i][maxValue.peek()];
                min[i][j] = matrix[i][minValue.peek()];
            }
        }
        
        // 쿼리 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            
            // 최대 최소값
            int maxValue = Integer.MIN_VALUE;
            int minValue = Integer.MAX_VALUE;
            // r행부터, r + b -1행까지 돌며 계산
            for (int j = r; j < r + b; j++) {
                maxValue = Math.max(maxValue, max[j][c]);
                minValue = Math.min(minValue, min[j][c]);
            }
            // 최대, 최소값의 차이 기록
            sb.append(maxValue - minValue).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}