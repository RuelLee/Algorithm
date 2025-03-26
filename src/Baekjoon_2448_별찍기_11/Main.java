/*
 Author : Ruel
 Problem : Baekjoon 2448번 별 찍기 - 11
 Problem address : https://www.acmicpc.net/problem/2448
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2448_별찍기_11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static boolean[][] factor;

    public static void main(String[] args) throws IOException {
        // n이 3 * 2^k 값으로 주어질 때
        // 해당하는 형태로 별을 출력하라
        //
        // 분할정복
        // n이 3일 때는
        //   *
        //  * *
        // *****
        // 6일 때는
        //      *     
        //     * *    
        //    *****   
        //   *     *  
        //  * *   * * 
        // ***** *****
        // 과 같은 모양으로 찍힌다.
        // 기본적인 모양인 n = 3일 때의 형태는 기억해야하고
        // 그 외의 경우는 해당 패턴 안에 패턴이 반복되는 형태로 표현 가능하다.
        // r이 n / 2보다 작은 지점에는 n / 2의 형태의 별이 n / 2칸 떨어진 곳에서 찍히고 있다.
        // c값에서 n / 2칸씩을 보정해준다.
        // r이 n / 2보다 같거나 큰 지점들에서는
        // n / 2의 형태의 별이 두개가 연이어있되, 가운데 한 칸을 띄고 있다.
        // 이 점을 고려하여 계산해준다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 n
        int n = Integer.parseInt(br.readLine());

        // 가장 기본적인 크기의 별찍기
        factor = new boolean[3][5];
        factor[0][2] = factor[1][1] = factor[1][3] = true;
        Arrays.fill(factor[2], true);

        StringBuilder sb = new StringBuilder();
        // n의 크기에 따라 너비는 최대 n * 2 - 1,
        // 높이는 n 크기에 대해 별을 찍어야한다.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n * 2 - 1; j++)
                sb.append(isStar(i, j, n) ? '*' : ' ');
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // 해당 지점이 별인지 판별한다.
    static boolean isStar(int r, int c, int length) {
        // 단위 크기가 될 때까지 분할한다.
        while (length > 3) {
            // r이 length의 반보다 작다면
            if (r < length / 2) {
                // c에 대해 좌우 length / 2만큼씩은 빈 공간이다. 해당 공간에 해당될 경우
                // false 반환
                if (c < length / 2 || c >= length * 2 - 1 - length / 2)
                    return false;
                // 그 외의 경우 length / 2만큼씩을 c에 대해 보정해주면 된다.
                c -= length / 2;
                // 그리고 length / 2로 줄인 크기에서 탐색한다.
                length /= 2;
            } else {        // r이 length / 2보다 같거나 큰 경우
                // 두 개의 length / 2 크기의 모양을 두개 이어 그린다.
                // c가 가운데 두 모양의 띄는 지점에 위치한다면 공백이다.
                if (c == length - 1)
                    return false;
                // 그 외의 경우 
                // c값에 대해서는 length를 기준으로 양쪽으로 분할하므로
                // 해당 mod length 값을
                c %= length;
                length /= 2;
                // r은 length / 2을 기준으로 위아래로 분할되므로
                // mod length / 2 값을 해준다.
                // 그리고 크기를 반으로 줄인다.
                // 코드에선 length를 먼저 1/2배 해줘서, 그냥 mod length를 해줬다.
                r %= length;
            }
        }
        // length가 3인 지점까지 왔다면
        // 저장해둔 최소 단위 크기에서의 별 위치 값을 반환한다.
        return factor[r][c];
    }
}