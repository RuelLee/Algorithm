/*
 Author : Ruel
 Problem : Baekjoon 1565번 수학
 Problem address : https://www.acmicpc.net/problem/1565
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1565_수학;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 배열 d와 배열 m이 주어졌을 때
        // d의 모든 수의 배수이고, m의 모든 수의 약수인 수의 개수를 구하라
        //
        // 수학 문제
        // d의 모든 수의 배수이다 -> 최소공배수의 배수이다
        // m의 모든 수의 약수이다 -> 최대공약수의 약수이다.
        // d의 최소공배수, m의 최대공약수를 구한 후
        // m의 최대공약수를 d의 최소공배수로 나눈 후, 
        // 해당 수의 약수의 개수를 구하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각 배열
        int[] sizes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] d = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] m = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // d의 최소공배수
        long lcm = 1;
        for (int i = 0; i < d.length; i++)
            lcm = getLCM(lcm, d[i]);
        
        // m의 최대공약수
        long gcd = m[0];
        for (int i = 1; i < m.length; i++)
            gcd = getGCD(gcd, m[i]);

        // 최소공배수가 최대공약수보다 작거나, 최대공약수가 최소공배수로 나누어떨어지지 않는다면
        // 답은 0
        if (gcd < lcm || gcd % lcm != 0)
            System.out.println(0);
        else {
            // 최대공약수를 최소공배수로 나눈 후
            // 약수의 개수를 센다.
            gcd /= lcm;
            int count = 0;
            int i = 1;
            for (; i * i < gcd; i++) {
                if (gcd % i == 0)
                    count += 2;
            }
            if (i * i == gcd)
                count++;
            // 약수의 개수 출력
            System.out.println(count);
        }
    }
    
    // 최소공배수 찾기
    static long getLCM(long a, long b) {
        return a / getGCD(a, b) * b;
    }
    
    // 최대공약수 찾기
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