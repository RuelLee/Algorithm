/*
 Author : Ruel
 Problem : Baekjoon 16935번 배열 돌리기 3
 Problem address : https://www.acmicpc.net/problem/16935
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16935_배열돌리기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][][] matrix;

    public static void main(String[] args) throws IOException {
        // 크기가 n * m인 배열이 주어진다.
        // 1번 연산은 상하반전, 2번 연산은 좌우 반전
        // 3번 연산은 시계방향으로 90도 회전, 4번 연산은 반시계방향으로 90도 회전
        // 배열을 
        // 1 2
        // 4 3 네 개의 구역으로 나누고
        // 5번 연산은 구역 단위로 시계방향으로 90도 회전
        // 6번 연산은 구역 단위로 반시계방향으로 90도 회전이다.
        // r개의 연산들을 처리한 후, 결과를 출력하라
        //
        // 배열, 구현 문제
        // 충실히 문제에 주어진대로 구현하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n * m 크기의 배열, r개의 연산
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        
        // 배열을 두 개 선언하여, 번갈아가면서 결과값을 입력
        // 90도 회전시에 행 열 크기가 바뀔 수 있으므로
        // 최대 크기로 선언
        int max = Math.max(n, m);
        matrix = new int[2][max][max];
        int rows = n;
        int cols = m;
        // 초기값
        for (int i = 0; i < rows; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cols; j++)
                matrix[0][i][j] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < r; i++) {
            // 주어진 연산 처리
            int o = Integer.parseInt(st.nextToken());
            switch (o) {
                case 1 -> operation1(i, rows, cols);
                case 2 -> operation2(i, rows, cols);
                case 3 -> {
                    operation3(i, rows, cols);
                    int temp = rows;
                    rows = cols;
                    cols = temp;
                }
                case 4 -> {
                    operation4(i, rows, cols);
                    int temp = rows;
                    rows = cols;
                    cols = temp;
                }
                case 5 -> operation5(i, rows, cols);
                case 6 -> operation6(i, rows, cols);
            }
        }
        
        // 결과 기록
        StringBuilder sb = new StringBuilder();
        int seq = r % 2;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++)
                sb.append(matrix[seq][i][j]).append(" ");
            sb.append(matrix[seq][i][cols - 1]).append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }
    
    // 상하 반전
    static void operation1(int seq, int rows, int cols) {
        int from = seq % 2;
        int to = (seq + 1) % 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                matrix[to][rows - 1 - i][j] = matrix[from][i][j];
        }
    }
    
    // 좌우 반전
    static void operation2(int seq, int rows, int cols) {
        int from = seq % 2;
        int to = (seq + 1) % 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                matrix[to][i][cols - 1 - j] = matrix[from][i][j];
        }
    }
    
    // 시계방향 90도 회전
    static void operation3(int seq, int rows, int cols) {
        int from = seq % 2;
        int to = (seq + 1) % 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                matrix[to][j][rows - 1 - i] = matrix[from][i][j];
        }
    }
    
    // 반시계방향 90도 회전
    static void operation4(int seq, int rows, int cols) {
        int from = seq % 2;
        int to = (seq + 1) % 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                matrix[to][cols - 1 - j][i] = matrix[from][i][j];
        }
    }
    
    // 구역 구분 후, 구역 별로 시계방향 회전
    static void operation5(int seq, int rows, int cols) {
        int from = seq % 2;
        int to = (seq + 1) % 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int section = area(i, j, rows, cols);
                matrix[to][((section % 2 == 1 ? 0 : rows / 2) + i) % rows][((section % 2 == 0 ? 0 : cols / 2) + j) % cols]
                        = matrix[from][i][j];
            }
        }
    }
    
    // 구역 별로 반시계방향 회전
    static void operation6(int seq, int rows, int cols) {
        int from = seq % 2;
        int to = (seq + 1) % 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int section = area(i, j, rows, cols);
                matrix[to][((section % 2 == 0 ? 0 : rows / 2) + i) % rows][((section % 2 == 1 ? 0 : cols / 2) + j) % cols]
                        = matrix[from][i][j];
            }
        }
    }
    
    // 구역 구분
    static int area(int row, int col, int rows, int cols) {
        if (row < rows / 2) {
            if (col < cols / 2)
                return 1;
            return 2;
        } else {
            if (col >= cols / 2)
                return 3;
            return 4;
        }
    }
}