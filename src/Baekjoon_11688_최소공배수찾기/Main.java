/*
 Author : Ruel
 Problem : Baekjoon 11688번 최소공배수 찾기
 Problem address : https://www.acmicpc.net/problem/11688
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11688_최소공배수찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 세 정수 a, b, l이 주어질 때, LCM(a, b, c) = l을 만족하는 가장 작은 c를 찾아라
        //
        // 유클리드 호제법, 소수 판정 문제
        // 세 수의 최소공배수가 l이 되도록 만들어야한다.
        // 먼저 a와 b의 최소공배수 lcm을 찾는다.
        // 그 후, lcm과 c의 최소공배수가 l이 되면 된다.
        // l을 소인수분해 해나가면서 각 인수의 전체가 lcm에 포함되어있다면
        // c에는 포함될 필요가 없고, 해당 인수 전체가 lcm에 포함되지 않는다면 c에는 반드시 포함되어야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // a와 b, 그리고 c의 최소공배수가 l이 되어야한다.
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        long l = Long.parseLong(st.nextToken());
        
        // a와 b의 최소공배수
        long LCM = (long) a / getGCD(a, b) * b;
        // 만약 l이 lcm의 배수가 아니라면
        // 불가능한 경우이므로 -1을 출력
        if (l % LCM != 0)
            System.out.println(-1);
        else {
            // 이 경우에는 answer가 c가 된다.
            long answer = 1;
            // l에 대해 소인수분해를 진행한다.
            for (long i = 2; i * i <= l; i++) {
                // l의 인수로 i가 포함된다면
                if (l % i == 0) {
                    long num = i;
                    // l에 포함되는 i의 최대 제곱을 찾는다.
                    while (l % (num * i) == 0)
                        num *= i;
                    
                    // l에서 num은 나눠 없어주고
                    l /= num;
                    // a와 b의 최소공배수에 num이 포함되지 않는다면
                    // c를 이루는 인수가 되어야하므로 answer에 num을 곱한다.
                    if (LCM % num != 0)
                        answer *= num;
                }
            }
            // l에 대해 제곱근까지의 범위에 대해서는 인수분해를 진행하였고
            // 현재 l은 1이거나 소수일 것이다.
            // 해당 값이 lcm에 포함되지 않는다면 마찬가지로 answer에 곱해준다.
            if (LCM % l != 0)
                answer *= l;
            // 해당 answer 출력
            System.out.println(answer);
        }
    }

    // a와 b의 최소공배수를 계산한다.
    static int getGCD(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);

        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}