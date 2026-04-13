/*
 Author : Ruel
 Problem : Baekjoon 10504번 덧셈
 Problem address : https://www.acmicpc.net/problem/10504
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10504_덧셈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 n이 주어진다.
        // 각 n이 두 개 이상의 연속된 자연수의 합으로 표현된다면 그러한 경우를 출력한다.
        // 여러개라면 가장 적은 개수의 수 합으로 표현되는 경우를 출력한다.
        //
        // 수학, 브루트포스 문제
        // 연속된 자연수의 합은 개수 * ( 시작 항 + 마지막 항) / 2 로 나타낼 수 있다.
        // 따라서
        // 개수의 값 i를 2에서부터 점점 늘려간다. 제한은 1 ~ i 까지의 합이 n보다 같거나 작은 시점까지
        // i개의 합으로 n을 표현한다면, 시작항은 n / i - (i - 1) / 2가 되어야한다.
        // 또한 2 * n은 i의 배수가 되어야한다.
        // 해당 조건에 만족하는 i를 찾을 수 있다면 값을 기록한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 주어지는 n
            int n = Integer.parseInt(br.readLine());

            // 시작항과 끝 항을 기록한다.
            int start = -1;
            int end = -1;
            // i를 2부터, 1 ~ i의 합이 n이하일 때까지 반복
            for (int i = 2; i * (i + 1) / 2 <= n && start == -1; i++) {
                // 조건에 만족하는 i를 찾는다.
                if ((2 * n) % i == 0) {
                    int temp = n / i - (i - 1) / 2;
                    // 계산된 시작항으로부터 i개의 항 합이 n이 맞는지 확인
                    if (i * (temp + temp + i - 1) == n * 2) {
                        // 찾았다면 기록
                        start = temp;
                        end = temp + i - 1;
                    }
                }
            }

            // start가 초기값이라면 불가능한 경우
            // IMPOSSIBLE 기록
            if (start == -1)
                sb.append("IMPOSSIBLE");
            else {
                // 시작항과 마지막항을 토대로 답 작성
                sb.append(n).append(" = ").append(start);
                for (int i = start + 1; i <= end; i++)
                    sb.append(" + ").append(i);
            }
            sb.append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}