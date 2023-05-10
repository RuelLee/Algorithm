/*
 Author : Ruel
 Problem : Baekjoon 9527번 1의 개수 세기
 Problem address : https://www.acmicpc.net/problem/9527
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9527_1의개수세기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 수 A, B가 주어졌을 때
        // A <= x <= B를 만족하는 모든 x에 대해 x를 이진수로 표현했을 때 1의 합을 구하는 프로그램을 작성하라
        //
        // 누적합 문제
        // 이진수로 표현했을 때 1의 개수를 세는 문제
        // 따라서 누적합을 2의 제곱으로 구분했다
        // ones[1]은 0 ~ 2^1까지의 1의 개수
        // ones[2]는 0 ~ 2^2 까지의 2의 개수
        // 그 후, B에 해당하는 수까지 1의 개수 합과
        // A-1에 해당하는 수까지의 1의 개수 합을 빼주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // A, B
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        // B가 최대 2의 몇 제곱까지 필요한지 계산하고, 배열을 선언한다.
        long[] ones = new long[(int) Math.ceil(Math.log(b) / Math.log(2)) + 1];
        // 2^0은 1
        ones[0] = 1;
        // ones를 채울 때는 비트를 통해 생각했다.
        // ones[1]은 2이므로, 10 이다.
        // 이는 한자리 비트 0, 1에서, 두자리 비트로 넘어가며 1이 하나 추가되었다.
        // ...
        // ones[3]는 8이므로 1000 이다.
        // 이는 000 부터 011까지 두자리 비트가 채워지는 경우 + 110부터 111까지 두 자리 비트가 채워지는 경우
        // 해당 개수는 ones[1] - 1개이며, 맨 앞자리가 0인 경우, 1인 경우 두 경우이므로 * 2
        // 그 후, 맨 앞자리에 1이 들어간 개수는 2^3 - 2^2 로 셀 수 있다.
        // 그 후, 마지막 1000에 1이 하나 추가된다.
        // 따라서 점화식으로써
        // An = (An-1 - 1) * 2 + 2^(n) - 2^(n-1) + 1을 세울 수 있고 채워나가면 된다.
        for (int i = 1; i < ones.length; i++)
            ones[i] = (ones[i - 1] - 1) * 2 + (long) (Math.pow(2, i) - Math.pow(2, i - 1)) + 1;

        // A ~ B까지의 1의 개수를 출력한다.
        System.out.println(calcOnes(b, ones) - calcOnes(a - 1, ones));
    }

    // 0 ~ n까지의 1의 개수를 센다.
    static long calcOnes(long n, long[] ones) {
        // n이 0이라면 더 셀 것도 없이 0
        if (n == 0)
            return 0;

        // n이 최대 몇 제곱 + 나머지로 표현되는지 센다.
        int pow = 0;
        while (n >= Math.pow(2, pow + 1))
            pow++;

        // 2 ^ pow까지의 1의 개수는 처리가 되었으므로
        // 나머지가 남는다면 나머지를 처리한다.
        // ones[pow]까지의 1의 개수와
        // 2^pow ~ n까지 맨 앞자리에 붙은 1의 개수 처리
        // 맨 앞자리를 제외한 1의 개수는 calcOnes를 나머지 수에 대한 호출을 함으로써 센다.
        if (n - Math.pow(2, pow) > 0)
            return ones[pow] + n - (long) Math.pow(2, pow) + calcOnes(n - (long) Math.pow(2, pow), ones);

        // 만약 딱 떨어진다면 그대로 1의 개수 반환.
        return ones[pow];
    }
}