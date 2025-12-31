/*
 Author : Ruel
 Problem : Baekjoon 20302번 민트 초코
 Problem address : https://www.acmicpc.net/problem/20302
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20302_민트초코;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] counts;

    public static void main(String[] args) throws IOException {
        // 곱셈과 나눗셈으로만 이루어진 수식이 주어진다.
        // 수식에는 n개의 수가 주어진다.
        // 해당 수식이 정수로 표현된다면 mint chocolate, 정수가 아닌 유리수라면 toothpaste를 출력한다.
        //
        // 소인수분해 문제
        // 수가 최대 10만까지 주어지므로, 최대 소수 99991까지 표현되는 배열을 선언한다.
        // 그 후, 들어오는 수에 대해 소인수분해하여, 연산자가 곱셈일 경우는 누적
        // 나눗셈일 경우는 차감하여 전체 인수들의 수가 양수를 인지 확인한다.
        // 만약 중간에 0으로 곱한다면 무조건 정수가 되므로 이 또한 체크해준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());

        // 인수들
        counts = new int[99992];
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어진 수들을 소인수분해하여
        // 곱셈은 경우 누적, 나눗셈인 경우 차감해준다.
        factorization(Integer.parseInt(st.nextToken()), true);
        for (int i = 0; i < n - 1; i++) {
            char c = st.nextToken().charAt(0);
            int num = Integer.parseInt(st.nextToken());
            factorization(num, c == '*');
        }

        boolean mint = true;
        // 0의 개수가 0개인 경우에는 다른 인수들을 살펴보며, 개수가 양수인지 체크한다.
        // 하나라도 음수가 있다면 정수가 아닌 유리수로 표현된다.
        if (counts[0] == 0) {
            for (int i = 2; i < counts.length; i++) {
                if (counts[i] < 0) {
                    mint = false;
                    break;
                }
            }
        }
        // 답 출력
        System.out.println(mint ? "mint chocolate" : "toothpaste");
    }

    // 소인수분해
    static void factorization(int n, boolean multiple) {
        // 음수이든 양수이든 상관없으므로 절대값 처리
        n = Math.abs(n);
        // 2부터 n의 제곱근까지 나누어떨어지는지 체크
        for (int i = 2; i * i <= n; i++) {
            while (n % i == 0) {
                counts[i] += (multiple ? 1 : -1);
                n /= i;
            }
        }

        // n이 0이라면 0이 주어진 경우이므로, 0을 누적
        if (n == 0)
            counts[0]++;
        // 그 외에 1보다 큰 경우, 해당 n의 개수 누적
        else if (n > 1)
            counts[n] += (multiple ? 1 : -1);
    }
}