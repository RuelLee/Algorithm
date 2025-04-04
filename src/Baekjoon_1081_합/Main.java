/*
 Author : Ruel
 Problem : Baekjoon 1081번 합
 Problem address : https://www.acmicpc.net/problem/1081
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1081_합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] pow;

    public static void main(String[] args) throws IOException {
        // L보다 크거나 같고, U보다 작거나 같은 모든 정수의 각 자리의 합을 구하라
        //
        // 수학 문제
        // 각자리에 1 ~ 9가 총 몇 번씩 나타나는지를 계산하면 되는 문제
        // 123이라는 수가 주어진다면
        // 1의 자리에서는 1 ~ 3이 (12 + 1)번 등장한다.
        // 10의 자리에서는 1이 총 (1 + 1) * 10번 등장하며, 3 ~ 9는 (1) * 10번 등장한다.
        // 여기서 2는 (1) * 10  + (3 + 1)번 등장한다.
        // 이에 대한 규칙성을 나타내면
        // abc라는 수가 주어질 때
        // 10의 자리에서 b보다 작은 정수에 대해서는 (a + 1) * 10번, b보다 큰 수는 (a) * 10번
        // b에 대해서는 a * 10 + ( c+ 1)이라는 규칙성을 갖는다.
        // 이를 토대로 각 자리의 합을 구하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // l <= x <= u 에 대한 x의 각 자리수의 합을 모두 더한다.
        int l = Integer.parseInt(st.nextToken());
        int u = Integer.parseInt(st.nextToken());

        // 10의 제곱이 많이 필요하므로 미리 계산.
        pow = new long[11];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = pow[i - 1] * 10;

        System.out.println(sumZeroToN(u) - sumZeroToN(Math.max(l - 1, 0)));
    }

    // 0 ~ n까지의 수에 대해 각 자리의 합을 구한다.
    static long sumZeroToN(int n) {
        // 총 합
        long sum = 0;
        // 자릿수
        int count = 0;
        // 1의 자리부터 계산해나간다.
        while (n / pow[count] > 0) {
            // 현재 자릿수의 수
            long num = (n % pow[count + 1]) / pow[count];
            // 1부터 ~ 9까지의 수가 해당 자리에서 몇 번 등장하는지 계산한다.
            // num보다 작은 수에 대해서는 (n / pow[count + 1] + 1) * pow[count]번 등장하며
            // num 이상인 수에 대해서는 (n / pow[count + 1]) * pow[count]번 등장한다
            for (int i = 1; i < 10; i++)
                sum += (n / pow[count + 1] + (i < num ? 1 : 0)) * i * pow[count];
            // 정확히 num인 경우에는 추가로 (n % pow[count] + 1)번 등장한다.
            sum += num * (n % pow[count] + 1);
            // 다음 자릿수로 넘어간다.
            count++;
        }
        // 계산한 합 반환.
        return sum;
    }
}