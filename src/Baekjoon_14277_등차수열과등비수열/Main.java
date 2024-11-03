/*
 Author : Ruel
 Problem : Baekjoon 14277번 등차 수열과 등비 수열
 Problem address : https://www.acmicpc.net/problem/14277
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14277_등차수열과등비수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 등차수열 A의 일반항 a + b*x(x는 음이 아닌 정수)와
        // 등비수열 G의 일반항 c * d^y(y는 음이 아닌 정수)가 주어진다.
        // u가 주어질 때, 1부터 u까지의 수 중에서 A나 G에 포함되는 수의 개수를 구하라
        //
        // 수학 문제
        // 먼저 1 ~ u까지의 범위 내에서 등차 수열 a + b*x에 속하는 원소의 개수는 세기 쉽다.
        // 0 ~ u - a 까지의 범위에서 b의 배수가 몇 개 들어가느냐와 같기 때문
        // 이는 (u - a) / b + 1개와 같다.
        // 이제 등비 수열을 따져보자.
        // 공비가 1이 아닌 한, 2의 배수로 값이 증가하므로, 값 범위를 모두 따지더라도 그 수가 많지 않다.
        // 따라서 공비가 1인 경우는 예외로 따로 처리해주며
        // 그 외의 경우는 직접 등비 수열을 구하되, A에 포함되는 경우 세지 않으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 등차수열 a + b * x
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        // 등비 수열 c * d ^ y
        long c = Long.parseLong(st.nextToken());
        long d = Long.parseLong(st.nextToken());
        // 1 ~ u까지의 범위
        long u = Long.parseLong(st.nextToken());
        
        // 범위에 포함된 등차 수열 원소의 개수
        long count = a > u ? 0 : (u - a) / b + 1;

        // 등비수열의 첫 항이 범위에 포함되는지 확인.
        if (c <= u) {
            // 공비가 1인 경우
            // 원소의 개수는 하나이며 이는 c이다
            if (d == 1) {
                if (c < a || (c - a) % b != 0)
                    count++;
            } else {
                // 그 외의 경우, 직접 따져본다
                long num = c;
                // 범위 내에서
                while (num <= u) {
                    // 등차수열에 포함되지 않은 경우에만 개수 증가.
                    if (num < a || (num - a) % b != 0)
                        count++;
                    // 공비를 곱해 다음 항으로 이동
                    num *= d;
                }
            }
        }
        // 구한 원소의 총 개수를 출력
        System.out.println(count);
    }
}