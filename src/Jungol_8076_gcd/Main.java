/*
 Author : Ruel
 Problem : Jungol 8076번 gcd(x,y)=a, x+y=b
 Problem address : https://jungol.co.kr/problem/8076
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8076_gcd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 10^18이하 양의 정수 a, b가 주어질 때
        // gcd(x, y) = a이고, x + y = b를 만족하는 (x, y)의 존재 여부를 구하라
        //
        // 수학 문제
        // gcd(x, y) = a이므로, x와 y는 a의 배수. x = a * c, y = a * d로 나타낼 수 있다.
        // a * c + a * d = b
        // a * (c + d) = b
        // b 또한 a의 배수이여야 하며, c + d는 양의 정수이므로 2 이상이다.
        // 1. b가 a의 배수인지
        // 2. b / a가 2보다 같거나 큰 지를 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 쿼리
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 주어진 a, b
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());

            // 1, 2번 조건을 만족하는지 확인
            sb.append(b % a == 0 && b / a >= 2 ? 1 : 0).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}