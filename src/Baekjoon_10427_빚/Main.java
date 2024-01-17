/*
 Author : Ruel
 Problem : Baekjoon 10427번 빚
 Problem address : https://www.acmicpc.net/problem/10427
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10427_빚;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n번의 돈을 빌렸다.
        // 부채를 상환하는 방식이 독특한데
        // m개의 부채를 상환하는 방법은 n개 중 m개의 부채를 골라
        // 그 중 최대값 * m 만큼의 돈을 갚아야한다.
        // 이 때 빌린 금액과 상환금의 차이를 Sm이라고 하자.
        // S1 + ... + Sn의 최소값을 구하라
        //
        // 누적합, 정렬 문제
        // 상환금을 최소로 하고자한다면 m개의 값들의 차이를 최소로해야한다.
        // 따라서 빚을 금액에 따라 정렬한 후, 순차적으로 m개의 값들에 대해서 계산해나간다.
        // 구간에 대한 접근이 많으므로 누적합을 통해 연산을 줄이자.
                
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스 개수
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 돈을 빌린 횟수 n
            int n = Integer.parseInt(st.nextToken());
            // 빚
            long[] psums = new long[n + 1];
            for (int i = 1; i < psums.length; i++)
                psums[i] = Integer.parseInt(st.nextToken());
            // 정렬
            Arrays.sort(psums);
            // 누적합
            for (int i = 1; i < psums.length; i++)
                psums[i] += psums[i - 1];

            long sum = 0;
            // m이 1인 경우에는 무조건 0이다.
            // 따라서 m이 2인 경우부터 n까지 계산한다.
            for (int i = 2; i <= n; i++) {
                // 후보값
                long candidate = Long.MAX_VALUE;
                // 1 ~ i까지의 범위, 2 ~ i+1까지의 범위, ... , n-i ~ n까지의 범위에 대해 계산한다.
                for (int j = i; j < psums.length; j++)
                    candidate = Math.min(candidate, (psums[j] - psums[j - 1]) * i - (psums[j] - psums[j - i]));
                // 찾은 값을 더한다.
                sum += candidate;
            }
            // 답 기록
            sb.append(sum).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}