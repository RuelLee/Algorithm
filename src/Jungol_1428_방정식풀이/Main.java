/*
 Author : Ruel
 Problem : Jungol 1428번 방정식 풀이
 Problem address : https://jungol.co.kr/problem/1428
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1428_방정식풀이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // A * x + B * y + C * z = W가 주어진다.
        // A와 B와 C, W 값이 주어질 때
        // 만족하는 x, y, z 중 세 값의 합이 최소인 합을 구하라
        //
        // 브루트포스 문제
        // a, b, c, w가 100이하로 매우 작게 주어지므로
        // a와 b에 각각 1 ~ 100까지의 값을 대입해보고, c값을 구하는 방법으로 처리한다.
        // 100 * 100, 최악의 경우도 1만번으로 가볍게 풀 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 a, b, c, w
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        // 세 값의 최소 합
        int answer = Integer.MAX_VALUE;
        // x에 i값을 시도
        for (int i = 0; i * a <= w; i++) {
            // y에 j값을 시도
            for (int j = 0; i * a + j * b <= w; j++) {
                // 남은 w 값을 계산하고
                int remain = w - i * a - j * b;
                // 이 값이 c로 나누어 떨어지는지 확인.
                // 그렇다면 z값을 바로 계산하여, x + y + z값이 최소인지 확인
                if (remain % c == 0)
                    answer = Math.min(answer, i + j + remain / c);
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}