/*
 Author : Ruel
 Problem : Baekjoon 13255번 동전 뒤집기
 Problem address : https://www.acmicpc.net/problem/13255
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13255_동전뒤집기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 동전이 탁자에 놓여있으며 모두 앞면이 위를 향하고 있다.
        // k개의 정수 a[i]가 주어지며, 각 턴마다 a[i]개의 동전을 랜덤하게 골라 뒤집는다.
        // k번의 턴이 지난 후, 동전이 모두 앞면을 향할 확률을 구하라
        //
        // 기댓값의 선형성, DP
        // 기댓값의 선형성이라는 개념을 이용한다.
        // 모든 동전에 대해 계산하는 것이 아니라, 동전 하나가 k번 턴이 지났을 때, 앞면을 향할 기댓값을 구하고
        // 이를 n개의 동전에 적용시킨다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 동전
        int n = Integer.parseInt(br.readLine());
        // k개 턴
        int k = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 턴마다 뒤집을 동전의 개수
        int[] a = new int[k];
        for (int i = 0; i < k; i++)
            a[i] = Integer.parseInt(st.nextToken());

        // 하나의 동전이 앞면을 향할 확률
        double rate = 1;
        // 각 턴마다 확률은
        // 앞면인 동전 * (뒤집는 동전들에 속하지 않을 확률)
        // + 뒷면인 동전 * (뒤집는 동전에 속할 확률)
        // 이를 k번 반복하면 1개의 동전이 k번 뒤에 앞면일 기댓값이 나온다.
        for (int i = 0; i < a.length; i++)
            rate = rate * (1 - (double) a[i] / n) + (1 - rate) * ((double) a[i] / n);

        // 이를 n개의 동전만큼 곱하면 된다.
        // 답 출력
        System.out.println(rate * n);
    }
}