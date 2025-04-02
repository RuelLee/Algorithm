/*
 Author : Ruel
 Problem : Baekjoon 10993번 별 찍기 - 18
 Problem address : https://www.acmicpc.net/problem/10993
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10993_별찍기18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] pow;
    static int n;

    public static void main(String[] args) throws IOException {
        // 예제를 보고 규칙을 유추한 뒤 별을 출력하라
        // 1
        // *
        // 2
        // *****
        //  ***
        //   *
        // 3
        //       *
        //      * *
        //     *   *
        //    *******
        //   *  ***  *
        //  *    *    *
        // *************
        //
        // 분할 정복 문제
        // 먼저 가장 큰 외각 삼각형에 대해, 주어지는 n의 짝홀수 여부에 따라 모양이 뒤집힌다.
        // 홀수일 때 삼각형, 역수일 땐 역삼각형
        // 그 때의 높이는 2^n - 1, 너비는 (2^n -1) * 2 -1
        // 위와 같은 조건으로 외각 삼각형을 찍어내며
        // 이제 내부의 삼각형에 대해 생각해보자
        // 내부의 삼각형은
        // 홀수일 때는 r이 높이의 반 이상부터 생기며
        // 짝수일 때는 r이 높이의 반 이하일 때까지 생긴다.
        // 그 때의 r과 c를 보정하여 내부의 삼각형도 찍어내자.

        // 제곱을 많이 쓰므로 미리 계산
        pow = new int[11];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = pow[i - 1] * 2;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        // n일 때 높이는 pow[n] - 1까지
        for (int i = 0; i < pow[n] - 1; i++) {
            // 너비는 짝홀수 여부에 따라, 외각선을 넘어가면 더 이상 공백을 찍지않고
            // 줄을 바꾼다.
            for (int j = 0; j < (n % 2 == 0 ? (pow[n] - 1) * 2 - 1 - i : pow[n] - 1 + i); j++)
                sb.append(isStar(i, j) ? '*' : ' ');
            sb.append("\n");
        }
        System.out.print(sb);
    }

    // 해당 좌표의 별을 찍어아하는지 계산.
    static boolean isStar(int r, int c) {
        int size = n;
        // size가 1보다 큰 동안
        while (size > 1) {
            // size가 짝수일 때
            if (size % 2 == 0) {
                // 빗변
                int diagonalDiff = Math.abs(pow[size] - 2 - c) - (pow[size] - 2 - r);
                // r이 0이거나 빗변에 해당할 경우
                // 별을 찍는다.
                if (r == 0 || diagonalDiff == 0)
                    return true;
                // 빗변보다 바깥쪽이거나 r이 반 이상일 경우 별을 찍지 않는다.
                else if (diagonalDiff > 0 || r >= pow[size - 1])
                    return false;
                
                // 그 외의 경우
                // 빗변 안쪽이며, r이 반 미만일 때
                // 내부 삼각형을 고려해야한다.
                // 그 때의 r과 c값을 보정
                r--;
                c -= pow[size - 1];
            } else {
                // size가 홀수일 때
                // 빗변 경계선
                int diagonalDiff = Math.abs(pow[size] - 2 - c) - r;
                // 가장 마지막 r이거나 빗변일 경우 별을 출력
                if (r == pow[size] - 2 || diagonalDiff == 0)
                    return true;
                // 빗변보다 바깥쪽이거나 r이 반 미만일 때는 별을 찍지 않는다.
                else if (diagonalDiff > 0 || r < pow[size - 1] - 1)
                    return false;

                // 그 외의 경우
                // 빗변 안쪽이며 r이 반 이상일 때
                r -= pow[size - 1] - 1;
                c -= pow[size - 1];
            }
            // 사이즈는 하나 줄어든다.
            size--;
        }
        // size가 1이 된 경우
        // 무조건 별을 하나 찍는다.
        return true;
    }
}