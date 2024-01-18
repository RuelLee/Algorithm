/*
 Author : Ruel
 Problem : Baekjoon 2737번 연속 합
 Problem address : https://www.acmicpc.net/problem/2737
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2737_연속합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 대부분의 양수는 적어도 2개 이상의 연속된 자연수의 합으로 나타낼 수 있다.
        // 6 = 1 + 2  + 3
        // T개의 n이 주어질 때
        // n을 2개 이상의 연속된 자연수의 합으로 나타낼 수 있는 경우의 수를 출력하라
        //
        // 수학 문제
        // n을 t개의 연속된 수로 나타낼 수 있다면 아래와 같이 쓰는 것이 허용된다.
        // n = (x + 0) + (x + 1) + ... + (x + t-1)
        // 따라서 n에서 0 ~ t-1까지의 합을 뺀 후, 그 값이 t개로 나누어떨어지는지 확인하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 주어지는 수 n
            int n = Integer.parseInt(br.readLine());

            // 연속된 길이가 2일 때부터 시작.
            // 그 때의 합은 0 + 1
            int sum = 1;
            int continuousLength = 2;
            // 경우의 수
            int count = 0;
            // n- sum이 0보다 큰 동안만 진행.
            while (n - sum > 0) {
                // n - sum이 continuousLength로 나누어떨어지는지 확인.
                // 그렇다면 경우의 수 추가
                if ((n - sum) % continuousLength == 0)
                    count++;
                // 다음 경우를 위해
                // sum과 continuousLength 증가
                sum += continuousLength++;
            }
            // testCase의 답 기록
            sb.append(count).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}