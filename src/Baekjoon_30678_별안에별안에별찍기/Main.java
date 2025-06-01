/*
 Author : Ruel
 Problem : Baekjoon 30678번 별 안에 별 안에 별 찍기
 Problem address : https://www.acmicpc.net/problem/30678
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30678_별안에별안에별찍기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static boolean[][] star = {
            {false, false, true, false, false},
            {false, false, true, false, false},
            {true, true, true, true, true},
            {false, true, true, true, false},
            {false, true, false, true, false},
    };
    static int[] pows;

    public static void main(String[] args) throws IOException {
        // star0는 별이 하나만 있는 패턴이고
        // *
        // 양의 정수 i에 대해 stari의 패턴은 다음과 같다
        //                 stari-1
        //                 stari-1
        // stari-1 stari-1 stari-1 stari-1 stari-1
        //         stari-1 stari-1 stari-1
        //         stari-1         stari-1
        // n이 주어질 때, startn을 출력하라
        //
        // 분할 정복 문제
        // 주어지는 크기 내에서 별이 찍히는 자리 여부를 재귀 혹은 분할 정복을 통해 풀어나가면 된다.
        // i가 하나 증가할 때마다, 가로 세로가 5배가 된다.
        // 주어진 n의 크기에서 별에 해당하는 범위를 구하고, 해당 범위에 속하는지 여부를 먼저 찾고
        // 크기를 줄여나가며 최종적으로 별이 해당하는 위치에만 별을 찍는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 별의 크기
        int n = Integer.parseInt(br.readLine());

        // 5의 제곱을 많이 쓰기 때문에 미리 구해둔다.
        pows = new int[n + 1];
        pows[0] = 1;
        for (int i = 1; i <= n; i++)
            pows[i] = pows[i - 1] * 5;

        StringBuilder sb = new StringBuilder();
        // 크기가 n일 때, 가로, 세로의 길이는 5^n
        for (int i = 0; i < pows[n]; i++) {
            for (int j = 0; j < pows[n]; j++)
                sb.append(isStar(i, j, n) ? '*' : ' ');
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
    
    // 크기와 r, c가 주어질 때, 해당 지점에 별을 찍는지 여부
    static boolean isStar(int r, int c, int size) {
        // size가 0보다 큰 동안
        while (size > 0) {
            // 가로 세로를 5칸의 범위로 나눈다.
            // 각 변의 길이가 5^size 이므로
            // 가로 세로를 5^(size-1)로 나눈 값을 사용한다.
            // 빈 공간에 해당할 경우, false 반환
            if (!star[r / pows[size - 1]][c / pows[size - 1]])
                return false;

            // 별에 해당할 경우, size가 0일 때까지 별에 해당하는지 확인해야한다.
            // size의 한 칸이 size-1에서의 전체 범위를 의미한다.
            // size에서 (r, c) 값을 size-1에서의 (r, c)로 보정해줘야한다.
            // 해당 값은 5^(size-1)의 나머지 연산을 통해 구할 수 있다.
            r %= pows[size - 1];
            c %= pows[size - 1];
            // 사이즈 감소
            size--;
        }
        // 계속 줄여나가는 동안 별이 찍히는 공간이었고,
        // size = 0까지 도달했다면 최종적으로 별을 찍는다.
        return true;
    }
}