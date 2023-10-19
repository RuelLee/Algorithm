/*
 Author : Ruel
 Problem : Baekjoon 23048번 자연수 색칠하기
 Problem address : https://www.acmicpc.net/problem/23048
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23048_자연수색칠하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 n까지의 자연수를 색칠한다.
        // 서로소인 두 자연수는 다른 색으로 칠해야한다.
        // 최소한의 색으로 모든 자연수를 칠하는 방법을 찾아라
        //
        // 에라토스테네스의 체, 소수 판정 문제
        // 소수는 다른 모든 수와 다른 색을 갖고 있어야한다.
        // 그리고 배수는 서로 같은 색을 갖더라도 상관이 없다.
        // 따라서 소수를 찾으면 새로운 색을 할당하고, 소수의 배수들에는 같은 색을 할당한다.
        // 위 과정을 반복하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 자연수
        int n = Integer.parseInt(br.readLine());
        
        // 각각에 할당되는 색
        int[] colors = new int[n + 1];
        // 1은 다른 수와 구별되는 색을 할당
        colors[1] = 1;
        // 색은 2번부터 시작
        int counter = 2;
        // 번호도 2번부터 탐색 시작
        for (int i = 2; i < colors.length; i++) {
            // 이미 색이 할당되어있다면 다른 소수의 배수이므로
            // 더 생각할 것 없이 건너뛰기
            if (colors[i] != 0)
                continue;

            // 그렇지 않다면 소수이므로 소수의 배수들에 같은 수를 할당한다.
            for (int j = 1; j * i < colors.length; j++)
                colors[i * j] = counter;
            // 다음 색으로 넘긴다.
            counter++;
        }

        StringBuilder sb = new StringBuilder();
        // counter가 가르키는 수는 아직 할당되지 않은 색이므로
        // 하나 적은 수의 색을 사용했으며
        sb.append(counter - 1).append("\n");
        // 1부터 할당된 수들의 색을 기록한다.
        for (int i = 1; i < colors.length; i++)
            sb.append(colors[i]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력
        System.out.println(sb);
    }
}