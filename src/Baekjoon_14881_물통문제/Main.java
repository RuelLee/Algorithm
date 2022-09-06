/*
 Author : Ruel
 Problem : Baekjoon 14881번 물통 문제
 Problem address : https://www.acmicpc.net/problem/14881
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14881_물통문제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 물통 a, b가 주어진다. 무한한 물을 통해 c리터를 정확히 만들 수 있는지 출력하라
        // t개의 테스트 케이스가 주어진다.
        //
        // 최대공약수 문제
        // 물통 문제의 경우 두 물통의 최대공약수의 배수만큼의 물을 만들 수 있다.
        // 따라서 c가 a, b물통보다 작고, 최대공약수의 배수라면 c리터의 물을 만들어낼 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        // 테스트케이스
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            // a, b 물통과 목표인 c
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 유클리드 호제법을 통해 a, b의 최대공약수를 계산한다.
            int max = Math.max(a, b);
            int min = Math.min(a, b);
            while (min > 0) {
                int temp = max % min;
                max = min;
                min = temp;
            }
            // c가 a, b보다 작고
            // c가 최대공약수의 배수라면 "YES"를 출력. 그 외의 경우 "NO" 를 출력한다.
            sb.append(c > Math.max(a, b) ? "NO" : (c % max == 0 ? "YES" : "NO")).append("\n");
        }
        System.out.print(sb);
    }
}