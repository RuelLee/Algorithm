/*
 Author : Ruel
 Problem : Jungol 4314번 장난꾸러기
 Problem address : https://jungol.co.kr/problem/4314
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4314_장난꾸러기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // q개의 l과 r이 주어진다.
        // A[i] = i이고, 이 i가 10보다 큰 경우, 각 자릿수의 합으로 지속적으로 바꿔나간다.
        // 예를 들어 197인 경우 197 -> 1 + 9 + 7 = 17 -> 1 + 7 = 8
        // A[i] + ... + A[r]의 값은?
        //
        // 수학
        // 디지털 루트 문제
        // 1 ~ 9까지의 값을 주기로 갖으며 반복되는 수열이다.
        // 먼저 r까지의 디지털 루트 합을 구하고, l-1까지의 디지털 루트 합을 구해 빼주면 된다.
        // 디지털 루트의 합은 9로 나누면 해당하는 주기의 개수가 나오므로 * 45를 해주고
        // 나머지 값은 9 미만이므로 해당 값에 대해서는 등차 수열의 합으로 구해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // q개의 테스트 케이스
        int q = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < q; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 범위
            long l = Long.parseLong(st.nextToken());
            long r = Long.parseLong(st.nextToken());

            // r까지의 디지털 루트 합에서 r-1까지의 디지털 루트 합을 뺀 값을 기록
            sb.append(getSumToN(r) - getSumToN(l - 1)).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // n까지의 디지털 루트의 합
    static long getSumToN(long n) {
        long sum = 0;
        // 해당하는 온전한 주기의 개수
        sum += (n / 9) * 45;
        // 남은 개수에 대해서는 등차수열의 합으로 구함
        sum += (n % 9) * (n % 9 + 1) / 2;
        return sum;
    }
}