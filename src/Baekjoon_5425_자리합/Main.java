/*
 Author : Ruel
 Problem : Baekjoon 5425번 자리합
 Problem address : https://www.acmicpc.net/problem/5425
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5425_자리합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] dp, pow;

    public static void main(String[] args) throws IOException {
        // 구간 [a, b]의 자리 합이란 구간에 포함되는 모든 숫자의 각 자리를 합한 것이다.
        // 예를 들어, [28,31]의 자리합은 아래와 같다.
        // 2+8 + 2+9 + 3+0 + 3+1 = 28
        // 두 수 a와 b가 주어질 때, [a, b]의 자리 합을 구하라
        //
        // dp, 수학 문제
        // abc라는 수가 주어진다면
        // a-1까지는 10^2번씩 각 수가 백의 자리에서 등장하고, a는 bc + 1번 등장한다.
        // 그 후, 십의 자리로 넘어가, b-1까지는 10번씩 등장하고, b는 c+1번 등장한다.
        // 이러한 반복성을 찾고 계산하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 십의 제곱을 미리 계산해둔다.
        pow = new long[17];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = pow[i - 1] * 10;

        // dp[i] = 10^i -1의 자리합
        dp = new long[17];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < 10; j++)
                dp[i] += j * pow[i - 1];
            dp[i] += dp[i - 1] * 10;
        }
        
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            
            // b까지의 자리합에서 a-1까지의 자리 합을 뺀 값이 이번 테스트케이스에서 원하는 값
            // 해당 값 기록
            sb.append(getSumToN(b) - getSumToN(a - 1)).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // n까지의 자리합을 구한다.
    static long getSumToN(long n) {
        // 가장 큰 자릿수까지 idx를 끌어올린다.
        int idx = 0;
        while (pow[idx + 1] <= n)
            idx++;

        long sum = 0;
        while (idx > 0) {
            // 10^idx 자리의 수
            int num = (int) ((n / pow[idx]) % 10);
            //  num보다 작은 수는 10^idx번만큼 등장한다.
            // 미리 구해둔 dp[idx](= 10^idx -1의 자리 합)를 활용하여,
            // num보다 작은 수에서 등장하는 10^idx 미만 자릿수들의 합을 누적시킨다.
            for (int i = 0; i < num; i++)
                sum += dp[idx] + i * pow[idx];
            // 마지막으로 num이 등장하는 횟수만큼 num을 더한다.
            sum += num * (n % pow[idx] + 1);
            // idx-1번째 자릿수로 이동
            idx--;
        }
        // 마지막으로 일의 자리수가 남았다.
        // 해당 수를 그냥 더해준다.
        for (int i = 1; i <= n % 10; i++)
            sum += i;
        // 답 반환
        return sum;
    }
}