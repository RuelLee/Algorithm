/*
 Author : Ruel
 Problem : Baekjoon 15311번 약 팔기
 Problem address : https://www.acmicpc.net/problem/15311
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15311_약팔기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // k(최대 2000)개의 약봉지에 약을 나눠 담는다.
        // 각 약봉지에는 1 ~ 100만 알의 약을 담을 수 있다.
        // 연속한 약봉지들을 골라, n개의 약을 담고자 할 때
        // k개의 약봉지에 담아야하는 알약의 수를 각각 출력하라
        //
        // 애드 혹 문제
        // 사실 입력 n이 100만으로 고정이기에, 100만일 때만 고려해도 된다.
        // 허나 좀더 일반적으로 풀어보고 싶었다.
        // k가 최대 2000까지 가능하므로, 1000 + 1000으로 나눌 수 있고
        // 100만은 1000 ^ 2으로 표현할 수 있다.
        // 따라서 1이 1000개, 1000이 999개로 늘어선다면
        // 1000과 인근한 부근부터 1을 세어간다면 최대 1000개까지 담을 수 있고
        // 1과 인근한 부근부터 1000을 세어간다면, 최대 99만9천까지 담을 수 있다. 물론 모두 담으면 100만이 된다.
        // 좀 더 일반화하면
        // n이 sqrt * sqrt로 표현된다면
        // sqrt개 만큼의 1, (sqrt - 1)개 만큼의 sqrt가 있다면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 주어지는 n
        int n = Integer.parseInt(br.readLine());
        // 제곱했을 때, n보다 같거나 큰 최소값 sqrt
        int sqrt = 1;
        while (sqrt * sqrt < n)
            sqrt++;

        StringBuilder sb = new StringBuilder();
        // n이 sqrt * (sqrt -1)보다 같거나 작다면
        // sqrt가 sqrt-2개 있어도 괜찮다.
        int num = (n > sqrt * (sqrt - 1)) ? sqrt - 1 : sqrt - 2;
        // 필요한 약봉지의 수, 1짜리 sqrt개, sqrt짜리 num개
        sb.append(sqrt + num).append("\n");
        // 1을 먼저 배치
        for (int i = 0; i < sqrt; i++)
            sb.append(1).append(" ");
        
        // 그 후 num개의 sqrt를 배치
        for (int i = 0; i < num - 1; i++)
            sb.append(sqrt).append(" ");
        sb.append(sqrt);
        // 출력
        System.out.println(sb);
    }
}