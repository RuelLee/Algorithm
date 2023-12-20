/*
 Author : Ruel
 Problem : Baekjoon 2829번 아름다운 행렬
 Problem address : https://www.acmicpc.net/problem/2829
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2829_아름다운행렬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 정사각행렬에서 가장 왼쪽 위에서 시작하는 대각선 성분의 합을 A
        // 가장 왼쪽 아래에서 시작하는 대각선 성분의 합을 B라고 하자.
        // A - B가 최대가 되는 부분 행렬의 값은?
        //
        // 누적합 문제
        // A를 구하기 위해서, 자신보다 왼쪽 위에 있는 성분을 계속해서 누적하여 누적합을 구하고
        // B를 구하기 위해서 자신보다 왼쪽 아래에 있는 성분을 계속하여 누적합을 구한다.
        // 그 후 특정 좌표에서 가능한 정사각행렬에서 모든 부분 행렬을 구하여 A - B의 최대값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 정사각행렬의 크기
        int n = Integer.parseInt(br.readLine().trim());
        // 정사각행렬
        int[][] matrix = new int[n][];
        for (int i = 0; i < matrix.length; i++)
            matrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[][] upDown = new int[n + 2][n + 2];
        int[][] downUp = new int[n + 2][n + 2];
        // 초기값 세팅
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                upDown[i + 1][j + 1] = matrix[i][j];
                downUp[i + 1][j + 1] = matrix[i][j];
            }
        }
        // A에 해당하는 누적합을 구한다.
        for (int i = 1; i < upDown.length - 1; i++) {
            for (int j = 1; j < upDown[i].length - 1; j++)
                upDown[i][j] += upDown[i - 1][j - 1];
        }
        // B에 해당하는 누적합을 구한다.
        for (int i = downUp.length - 2; i > 0; i--) {
            for (int j = 1; j < downUp[i].length - 1; j++)
                downUp[i][j] += downUp[i + 1][j - 1];
        }
        
        // 최대값 계산
        int max = 0;
        // i, j에서
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // size 크기의 정사각행렬의 A - B 값을 구한다.
                for (int size = 1; Math.max(i, j) + size < n; size++) {
                    // A와 B의 값
                    int a = upDown[i + size + 1][j + size + 1] - upDown[i][j];
                    int b = downUp[i + 1][j + size + 1] - downUp[i + size + 2][j];
                    
                    // 최대값을 갱신하는지 확인.
                    max = Math.max(max, a - b);
                }
            }
        }
        // 최대값 출력
        System.out.println(max);
    }
}