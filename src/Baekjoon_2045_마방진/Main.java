/*
 Author : Ruel
 Problem : Baekjoon 2045번 마방진
 Problem address : https://www.acmicpc.net/problem/2045
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2045_마방진;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // 각 가로줄, 세로줄, 대각선을 idx로 처리
    static int[][] lines = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    static int[][] matrix;

    public static void main(String[] args) throws IOException {
        // 최대 3개의 칸이 비어있는 3 * 3의 마방진이 주어진다.
        // 빈 칸을 채워 출력하라
        //
        // 구현 문제
        // 먼저, 대각선 전체가 0인 경우가 있는지 살펴본다.
        // 이렇게 되면, 각 가로줄마다, 각 세로줄마다, 각 대각선마다 모두 빈 칸이 포함되게 된다.
        // 이 때는 빈 칸을 제외한 모든 수의 합 / 2가 대각선의 합인 점을 이용하여 한 줄의 합을 구한다.
        // 그 외의 경우는, 각 가로줄마다, 각 세로줄마다, 각 대각선마다의 합을 구하며 그 최댓값을 찾는다.
        // 그 후, 빈 칸을 모두 채울 때까지, 모든 줄을 반복적으로 살펴보며, 빈 칸이 하나인 경우, 그 빈칸을 채운다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;
        // 주어지는 마방진
        matrix = new int[3][3];
        // 빈 칸의 수
        int zeroCount = 0;
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                if ((matrix[i][j] = Integer.parseInt(st.nextToken())) == 0)
                    zeroCount++;
            }
        }

        int lineSum = 0;
        // 만약 대각선의 모든 칸이 0인 경우
        if (countZero(6) == 3 || countZero(7) == 3) {
            // 나머지 모든 수의 합 / 2가 대각선 칸들의 합임을 이용
            int sum = getSum(0) + getSum(1) + getSum(2);
            lineSum = sum / 2;
        } else {
            // 그 외의 경우
            // 각 줄을 순회하며 최댓값을 찾는다.
            for (int i = 0; i < lines.length; i++)
                lineSum = Math.max(lineSum, getSum(i));
        }

        int idx = 0;
        // 빈 칸이 남아있는 동안
        while (zeroCount > 0) {
            // 해당 줄에 빈 칸이 하나일 때
            if (countZero(idx % 8) == 1) {
                // 하나의 빈칸을 채운다.
                int currentSum = getSum(idx % 8);
                for (int i = 0; i < lines[idx % 8].length; i++) {
                    if (matrix[lines[idx % 8][i] / 3][lines[idx % 8][i] % 3] == 0) {
                        matrix[lines[idx % 8][i] / 3][lines[idx % 8][i] % 3] = lineSum - currentSum;
                        zeroCount--;
                        break;
                    }
                }
            }
            // 다음 줄을 살펴봄
            idx++;
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            sb.append(matrix[i][0]);
            for (int j = 1; j < matrix[i].length; j++)
                sb.append(" ").append(matrix[i][j]);
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // idx 줄의 합을 구함
    static int getSum(int idx) {
        int sum = 0;
        for (int i = 0; i < lines[idx].length; i++)
            sum += matrix[lines[idx][i] / 3][lines[idx][i] % 3];
        return sum;
    }

    // idx 줄의 빈 칸의 개수를 셈
    static int countZero(int idx) {
        int cnt = 0;
        for (int i = 0; i < lines[idx].length; i++) {
            if (matrix[lines[idx][i] / 3][lines[idx][i] % 3] == 0)
                cnt++;
        }
        return cnt;
    }
}