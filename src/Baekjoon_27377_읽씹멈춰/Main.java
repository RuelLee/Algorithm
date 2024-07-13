/*
 Author : Ruel
 Problem : Baekjoon 27377번 읽씹 멈춰!
 Problem address : https://www.acmicpc.net/problem/27377
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27377_읽씹멈춰;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 말을 n번 반복하여 적고자 한다.
        // 한 번 말을 적는데 s의 시간이 걸리고,
        // 현재 적은 만큼을 복사하여 붙여넣는데 t만큼의 시간이 걸린다.
        // 정확히 n만큼 말을 반복하는데 걸리는 최소 시간을 출력하라
        //
        // 그리디 문제
        // n이 홀수라면 무조건 한 번 직접 적어야하고,
        // 짝수라면 n/2만큼을 직접 적는 것과 t와 비교하여 더 적은 쪽을 취한다.
        // 사실 문제 풀이 자체는 간단한데, 값의 범위가 매우 커 이를 처리할 방법이 문제였다.
        // 무한대의 범위를 취급할 수 있는 BigInteger를 통하여 처리했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // T개의 테스트 케이스
        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < T; testCase++) {
            long n = Long.parseLong(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 글을 적는데 걸리는 시간 s
            BigInteger s = new BigInteger(st.nextToken());
            // 복붙하는데 걸리는 시간 t
            BigInteger t = new BigInteger(st.nextToken());

            BigInteger sum = BigInteger.ZERO;
            while (n > 0) {
                // 홀수라면
                if (n % 2 == 1) {
                    // 값 1 차감 후, sum에 s 추가
                    n--;
                    sum = sum.add(s);
                } else {
                    // 짝수라면
                    // 값을 반으로 줄이고
                    n /= 2;
                    // 줄어든 만큼 직접 쓰는 것과 t를 비교하여 더 적은 쪽을 누산.
                    sum = sum.add(s.multiply(BigInteger.valueOf(n)).min(t));
                }
            }
            // 답 기록
            sb.append(sum).append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }
}