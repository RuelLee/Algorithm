/*
 Author : Ruel
 Problem : Baekjoon 2860번 종이에 숫자 쓰기
 Problem address : https://www.acmicpc.net/problem/2860
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2860_종이에숫자쓰기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 하나의 종이에 1 ~ 5까지의 수 하나만 쓸 수 있고, 종이는 무한개로 있다.
        // 종이에 쓴 숫자들의 평균을 p로 만들고자할 때
        // 각각 써야하는 1 ~ 5까지의 수의 개수는?
        // p는 1보다 크거나 같고, 5보다 작거나 같으며, 소수점 1 ~ 9자리이다.
        //
        // 유클리드 호제법, 이분 탐색 문제
        // p의 값이 유리수로 주어지므로, 이를 정수로 바꾸는 작업이 필요하다.
        // 최대 소수점 9자리이므로, 10^9 까지 곱할 수 있고, 곱한 수 만큼이 적어야하는 종이의 수다.
        // 그 수가 너무 크므로, 두 수를 유클리드 호제법을 통해 최대공약수로 나눠 횟수를 줄이고
        // 남은 횟수와 수를 비교하여 이분 탐색으로 적절히 1 ~ 5까지의 수를 분배한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 평균으로 만들고자 하는 p
        double p = Double.parseDouble(br.readLine());
        // 적어야하는 종이의 수
        long num = 1;
        for (int i = 0; i < 10; i++) {
            // 10의 i제곱을 곱해, 소수점이 사라진다면
            // p와 i에 10의 i제곱을 곱한다.
            if (p * Math.pow(10, i) % 1 == 0) {
                num *= Math.pow(10, i);
                p *= Math.pow(10, i);
                break;
            }
        }
        
        // 남은 수
        long remain = (long) p;
        // 최대공약수
        long gcd = getGCD(remain, num);
        // remain과 num에 gcd를 나눠 서로소로 만든다.
        remain /= gcd;
        num /= gcd;

        long[] counts = new long[6];
        // 1 ~ 5의 수를 적절히 더하여 remain을 만들어야하고
        // 그 수의 개수는 총 num개 이다.
        // 5부터 살펴나간다.
        for (int i = 5; i > 0; i--) {
            long start = 0;
            long end = remain / i;
            // remain - i * 개수의 값이 num - 개수보다 같거나 커야한다.
            // 남은 수에 0을 채우는 방법은 없고 최소 1을 채워야하기 때문.
            // 따라서 이분 탐색을 통해
            // 써야하는 i의 개수를 찾는다.
            while (start <= end) {
                long mid = (start + end) / 2;
                if (remain - mid * i >= num - mid)
                    start = mid + 1;
                else
                    end = mid - 1;
            }
            // 해당 수 만큼 count[i]에 표시
            counts[i] = end;
            // 남은 수에서 count[i] * i만큼을 빼주고
            remain -= counts[i] * i;
            // num에서도 사용한 i의 개수만큼을 빼준다.
            num -= counts[i];
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < counts.length; i++)
            sb.append(counts[i]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }

    // a와 b의 최대공약수를 구한다.
    static long getGCD(long a, long b) {
        long max = Math.max(a, b);
        long min = Math.min(a, b);
        while (min > 0) {
            long temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}