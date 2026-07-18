/*
 Author : Ruel
 Problem : Jungol 3249번 색종이 만들기3(압축해제)
 Problem address : https://jungol.co.kr/problem/3249
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3249_색종이만들기3_압축해제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] map;
    static String code;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다. n은 2의 제곱수이다.
        // 각 칸에는 0 혹은 1이 들어있다. 이를 X, 0, 1로 표현한 코드가 주어진다.
        // 현재 범위에 1과 0이 섞여있다면 X가 주어지고, 그 다음 현재 범위를 4등분하여 좌상, 우상, 좌하, 우하의 상태가
        // 다시 X 0 1로 주어진다.
        // 주어진 코드로 격자의 상태를 복구하여 출력하라
        //
        // 재귀 문제
        // 문제에 따라 충실히, X가 주어진 경우, 4등분한 범위를 순차적으로 탐색하고
        // 0이나 1이 주어진 경우, 해당 범위를 해당 값으로 채우면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        // 코드
        code = br.readLine();

        // 전체 범위에 대해 탐색
        findAnswer(0, 0, n - 1, 0, n - 1);
        // 답 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            sb.append(map[i][0]);
            for (int j = 1; j < map[i].length; j++)
                sb.append(" ").append(map[i][j]);
            sb.append("\n");
        }
        // 출력
        System.out.println(n);
        System.out.print(sb);
    }

    // 재귀를 통해 격자의 상태를 복구한다.
    // 현재 코드의 순서는 idx이며, 범위는 rowS부터 rowE까지, colS부터 colE까지
    static int findAnswer(int idx, int rowS, int rowE, int colS, int colE) {
        // X가 주어진 경우
        if (code.charAt(idx) == 'X') {
            // 4등분하여 탐색
            // 좌상
            idx = findAnswer(idx + 1, rowS, (rowS + rowE) / 2, colS, (colS + colE) / 2);
            // 우상
            idx = findAnswer(idx + 1, rowS, (rowS + rowE) / 2, (colS + colE) / 2 + 1, colE);
            // 좌하
            idx = findAnswer(idx + 1, (rowS + rowE) / 2 + 1, rowE, colS, (colS + colE) / 2);
            // 우하
            idx = findAnswer(idx + 1, (rowS + rowE) / 2 + 1, rowE, (colS + colE) / 2 + 1, colE);
        } else if (code.charAt(idx) == '1') {       // 1인 경우 범위를 1로 채움
            for (int i = rowS; i <= rowE; i++)
                Arrays.fill(map[i], colS, colE + 1, 1);
        }   // 0인 경우는 딱히 하지 않아도 이미 0이 차있다.
        // 현재 진행된 idx를 반환
        return idx;
    }
}