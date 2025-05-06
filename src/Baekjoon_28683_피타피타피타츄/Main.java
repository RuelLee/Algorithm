/*
 Author : Ruel
 Problem : Baekjoon 28683번 피타! 피타! 피타츄!
 Problem address : https://www.acmicpc.net/problem/28683
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28683_피타피타피타츄;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // 직각삼각형의 한 변의 길이의 제곱이 n일 때
        // 다른 두 변의 길이가 모두 정수인 직각삼각형의 개수는 몇 개인가?
        // 무한하다면 -1을 출력한다
        //
        // 수학, 피타고라스의 정리
        // * 먼저, n이 제곱수로 주어진다면, n 또한 정수이므로,
        // 다른 하나의 변을 임의의 정수로 정하더라도, 나머지 하나의 변의 값을 대응해서 맞출 수 있으므로 무한한 개수가 존재한다.
        // * n이 제곱수가 아닌 경우
        // n이 빗변의 길이의 제곱에 해당하는 경우
        // 다른 두 변의 제곱의 합이 n이 되어야한다.
        // n보다 같거나 작은 제곱수를 모두 구해 두 포인터로 개수를 센다.
        // n이 빗변의 길이 제곱이 아닌 경우
        // 빗변을 b, 다른 하나를 a라 했을 때
        // n + a^2 = b^2
        // n = b^2 - a^2 = (b + a)(b - a) 인데
        // b + a = x, b - a = y라 두면
        // 2b = x + y, 2a = x - y가 된다.
        // 결국 (b + a)와 (b - a)의 합과 차가 짝수가 되어야하는데, 이는 두 개가 같은 홀짝성을 갖는다.
        // 이를 이용하여 개수를 마저 세어준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 직각삼각형의 한 변의 길이의 제곱 n
        long n = Long.parseLong(br.readLine());
        
        // n이 제곱수인 경우, 무한
        if ((long) Math.sqrt(n) * (long) Math.sqrt(n) == n)
            System.out.println(-1);
        else {
            int count = 0;
            
            // n이 빗변 길이의 제곱인 경우
            // n보다 작은 제곱수들을 미리 구하여
            List<Long> pows = new ArrayList<>();
            for (long i = 1; i * i < n; i++)
                pows.add(i * i);

            int j = pows.size() - 1;
            // 두 제곱수의 합이 정확히 n이 되는 경우의 개수를 센다.
            for (int i = 0; i <= j; i++) {
                while (i < j && pows.get(i) + pows.get(j) > n)
                    j--;
                if (pows.get(i) + pows.get(j) == n)
                    count++;
            }
            
            // n이 빗변이 아닌 변의 길이의 제곱인 경우
            // 홀짝성을 이용하여
            // n에 나누어떨어지는 i와 n / i를 구하고,
            // 두 수의 홀짝성 일치하는지 확인.
            for (long i = 1; i * i < n; i++) {
                if (n % i == 0 && (i + (n / i)) % 2 == 0)
                    count++;
            }
            // 개수 출력
            System.out.println(count);
        }
    }
}