/*
 Author : Ruel
 Problem : Baekjoon 4913번 페르마의 크리스마스 정리
 Problem address : https://www.acmicpc.net/problem/4913
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4913_페르마의크리스마스정리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int size = 1_000_000;

    public static void main(String[] args) throws IOException {
        // p가 소수이고 p = 4c + 1이라면
        // p = a^2 + b^2으로 나타낼 수 있다.
        // 어떤 구간이 주어질 때, 해당 구간의 소수의 수와
        // 제곱 수의 합으로 표현할 수 있는 소수의 개수를 출력하라
        //
        // 에라토스테네스의 체, 누적합
        // 에라토스테네스의 체를 통해 소수 판별 후
        // 누적합을 통해, 구간에서의 소수의 개수와 제곱 수로 표현 가능한 소수의 개수를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 소수
        boolean[] primeNumbers = new boolean[size];
        Arrays.fill(primeNumbers, true);
        // 0과 1은 소수가 아님
        primeNumbers[0] = primeNumbers[1] = false;
        // 에라토스테네스의 체
        for (int i = 2; i < primeNumbers.length; i++) {
            if (!primeNumbers[i])
                continue;

            for (int j = 2; i * j < primeNumbers.length; j++)
                primeNumbers[i * j] = false;
        }

        // 누적합
        int[][] psums = new int[size][2];
        // 예외로 2는 소수이며, 제곱 수의 합으로 표현할 수 있다.
        psums[2][0] = psums[2][1] = 1;
        for (int i = 3; i < psums.length; i++) {
            // 소수의 개수 누적합
            psums[i][0] = psums[i - 1][0] + (primeNumbers[i] ? 1 : 0);
            // 제곱 수의 합으로 표현할 수 있는 소수 개수 누적합
            psums[i][1] = psums[i - 1][1] + (primeNumbers[i] && i % 4 == 1 ? 1 : 0);
        }

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        // 여러 개의 테스트케이스
        while (true) {
            st = new StringTokenizer(br.readLine());
            // 구간 l ~ r
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            // l과 r이 모두 -1이 들어오면 종료
            if (l == -1 && r == -1)
                break;

            // 답안 작성
            // l과 r이 음수의 값도 들어올 수 있으나, 음수의 값인 경우 소수이지도, 제곱수의 합으로도 표현되지 않는다.
            // 따라서 적당히 0 값으로 변환될 수 있도록 한다.
            sb.append(l).append(" ").append(r).append(" ").append(psums[Math.max(r, 0)][0] - psums[Math.max(l - 1, 0)][0]).
                    append(" ").append(psums[Math.max(r, 0)][1] - psums[Math.max(l - 1, 0)][1]).append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }
}