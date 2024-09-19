/*
 Author : Ruel
 Problem : Baekjoon 3165번 5
 Problem address : https://www.acmicpc.net/problem/3165
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3165_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n과 k가 주어질 때
        // n보다 크면서 5가 적어도 k번 포함되는 가장 작은 수를 구하라
        //
        // 그리디 문제
        // 그리디하게 일의 자리부터 5를 하나씩 만들어가며 전체 수에 5가 포함된 개수를 센다.
        // 단 해당 자리의 수가 5보다 커서, 다음 5가 포함되는 수가 다음 자릿수의 영향을 미치는 경우도 생각해야한다.
        // 예를 들어 45의 경우, 일의 자리만 고려하면 55가 되지만 이러면 안되고, 50 또한 고려해야함을 잊지말자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n보다 크며, 적어도 5가 k개 포함되어야한다.
        long n = Long.parseLong(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 만약 이미 조건을 만족한 경우
        // n보다는 커야하므로 1 증가시킨다
        if (count5(n) >= k)
            n++;

        // 최소 k개의 5가 포함되어야하므로
        // 최대 k-1번째 자리까지 살펴보면 된다.
        for (int i = 0; i < k; i++) {
            // 현재 수에 포함된 5의 개수가 k개 이상이라면 반복문 종료
            if (count5(n) >= k)
                break;
            
            // 현재 살펴보는 자릿수
            long target = (n / (long) Math.pow(10, i)) % 10;
            // 해당 수의 진짜 값
            long num = target * (long) Math.pow(10, i);

            // 만약 살펴보는 자리의 수가 5보다 작다면 해당 값을 5로 만든다.
            if (target < 5)
                n = n - num + 5 * (long) Math.pow(10, i);
            else {      // 만약 5보다 크다면 일단은 i+1번째 자릿수 값을 1 증가시킨다.
                n = n - num + (long) Math.pow(10, i + 1);
                // 그리고 5의 개수를 세어봐서 k이상이라면 반복문 종료
                if (count5(n) >= k)
                    break;
                // 그렇지 않다면 i번째 수도 5로 만든다.
                n += (long) (Math.pow(10, i) * 5);
            }
        }
        System.out.println(n);
    }

    // n에 포함된 5의 개수를 센다.
    static int count5(long n) {
        int count = 0;
        while (n > 0) {
            if (n % 10 == 5)
                count++;
            n /= 10;
        }
        return count;
    }
}