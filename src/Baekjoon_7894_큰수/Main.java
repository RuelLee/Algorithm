/*
 Author : Ruel
 Problem : Baekjoon 7894번 큰 수
 Problem address : https://www.acmicpc.net/problem/7894
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7894_큰수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스 마다 m이 주어진다.
        // m! 의 자릿수를 구하라
        //
        // 수학 문제
        // m이 최대 10^7으로 주어지기 때문에 당연히 직접 계산하진 못한다.
        // log10을 생각해보면 이 값이 십진수의 자릿수와 관련있다.
        // log1 + ... + logm까지 모두 누적시켜둔 후, 테스트케이스가 주어질 때마다 값을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스의 수
        int testCase = Integer.parseInt(br.readLine());

        // 미리 10^7개의 값을 누적시켜 구해둠.
        double[] sums = new double[10000000 + 1];
        for (int i = 1; i < sums.length; i++)
            sums[i] = sums[i - 1] + Math.log(i) / Math.log(10);

        StringBuilder sb = new StringBuilder();
        // 테스트케이스마다 해당하는 값을 기록
        for (int t = 0; t < testCase; t++) {
            int m = Integer.parseInt(br.readLine());
            sb.append((int) (sums[m] + 1)).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}