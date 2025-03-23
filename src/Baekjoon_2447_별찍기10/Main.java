/*
 Author : Ruel
 Problem : Baekjoon 2447번 별 찍기 - 10
 Problem address : https://www.acmicpc.net/problem/2447
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2447_별찍기10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 재귀적인 패턴으로 별을 찍어보자.
        // n이 3의 거듭제곱으로 주어지고, n패턴은 n * n 정사각형 모양의 패턴이다.
        // 크기 3인 패턴은
        // ***
        // * *
        // *** 으로 주어지고, n이 3보다 클 경우 각각의 별이 (n / 3) * (n / 3) 크기의 패턴으로 채워진 형태이다.
        // 크기가 9인 경우
        // *********
        // * ** ** *
        // *********
        // ***   ***
        // * *   * *
        // ***   ***
        // *********
        // * ** ** *
        // ********* 와 같이 된다.
        //
        // 분할 정복, 재귀 문제
        // 최소 크기의 패턴을 보면
        // 현재 공간을 9개로 나누어, 그 중 가운데는 비어있는 형태이다.
        // 따라서 n이 주어질 경우, 현재 n에서 공간을 9개로 나누어 가운데가 아닌 영역에 대해 (n / 3) * (n / 3) 크기의 패턴을 찍는다.
        // 다시 그 이후 n / 3의 공간 내에서 다시 가운데를 제외한 나머지 8개의 공간에 대해 (n / 9) * (n / 9) 크기의 패턴을 채운다.
        // 재귀적으로 최소 크기가 될 때까지 가운데 영역인지 판별하며, 한 번이라도 가운데 영역인 경우는 별을 찍지 않으면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기 n
        int n = Integer.parseInt(br.readLine());
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 현재 좌표에 별을 찍어야하는지 여부 판별
                if (isStar(i, j, n))
                    sb.append('*');
                else
                    sb.append(' ');
            }
            sb.append("\n");
        }
        // 답 출력
        System.out.println(sb);
    }

    // 현재 위치에 별을 찍어야하는지 계산한다.
    static boolean isStar(int r, int c, int size) {
        // size가 3보다 같거나 큰 동안 판별한다.
        while (size >= 3) {
            // 현재 size를 1/3로 줄이고
            size /= 3;
            // 현재의 r이 가운데 영역의 범위이고
            // c 또한 가운데 영역의 범위일 경우
            // 빈 공간이므로 false를 반환.
            if (r >= size && r < size * 2 && c >= size && c < size * 2)
                return false;

            // 그렇지 않을 경우
            // 현재 1/3된 size에 대해서 해당 사항을 다시 계산해야한다.
            // 이 때의 크기에 맞게 r과 c를 조정해준다.
            // r과 c에 대해 각각 mod size를 한다.
            r %= size;
            c %= size;
        }
        // 한번도 가운데 영역이 아니였다면 true를 반환하여 별이 찍히는 영역임을 알린다.
        return true;
    }
}