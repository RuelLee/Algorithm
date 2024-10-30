/*
 Author : Ruel
 Problem : Baekjoon 24270번 미니 버킷 리스트
 Problem address : https://www.acmicpc.net/problem/24270
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24270_미니버킷리스트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 할 일과 소요되는 연속 시간
        // 그리고 k개의 단위 시간이 주어진다.
        // k개의 시간 동안, n개의 일을 모두 처리하는 방법의 가짓수를 구하라
        //
        // 경우의 수, 중복 조합, 수학 문제
        // 먼저, n개의 할 일의 순서는 n! 가지가 된다.
        // 할 일을 모두 하고 남는 시간을 r이라 할 때,
        // 쉬는 시간을 할일의 전후, n+1 가지의 곳에 r개 배치할 수 있다.
        // 이는 중복조합으로 n+1 H r이고, n+r C r로 쓸 수 있다.
        // 따라서 답은 n ! * n+r C r 이고
        // n! * (n+r)! / n ! / r!로 쓸 수 있다. n! 끼리 사라지고
        // (n+r)! / r!로 구할 수 있다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 할 일과 k개의 단위 시간
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] times = new int[n];
        st = new StringTokenizer(br.readLine());
        int sum = 0;
        for (int i = 0; i < times.length; i++)
            sum += (times[i] = Integer.parseInt(st.nextToken()));

        long answer = 1;
        // 답은 (n+r)! / r! 로
        // r+1부터 n+r까지의 곱으로 계산할 수 있다.
        for (int i = k - sum + 1; i <= n + k - sum; i++)
            answer = (answer * i) % LIMIT;
        // 답 출력
        System.out.println(answer);
    }
}