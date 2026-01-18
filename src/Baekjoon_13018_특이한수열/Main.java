/*
 Author : Ruel
 Problem : Baekjoon 13018번 특이한 수열
 Problem address : https://www.acmicpc.net/problem/13018
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13018_특이한수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열의 길이는 n
        // n이하의 수가 한번씩 등장
        // 1 <= i <= n인 i에 대해 gcd(i, a[i]) > 1을 만족하는 i가 k개
        // 위 사항을 만족하는 수열을 구하라
        //
        // 애드혹
        // 최대공약수가 1보다 큰 수를 k개로 제한하는 문제다.
        // 이웃하는 수의 GCD는? 1이다.
        // n - k개의 수를 하나씩 밀어내서 옆 수로가 되게끔하고
        // k개의 수에 대해서는 자기 자신을 출력하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 n과 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        // n과 k가 동일한 경우에는 불가능하다.
        // 1에 대해 gcd(1, a[i]) > 0을 만족하는 경우가 어떠한 경우도 없기 때문.
        if (n == k)
            sb.append("Impossible");
        else {
            // 그 외의 경우
            // n - k개의 수에 대해서는 옆의 수를 가져오고
            for (int i = 1; i <= n - k; i++)
                sb.append((i) % (n - k) + 1).append(" ");
            // k개는 자기 자신을 출력
            for (int i = n - k + 1; i <= n; i++)
                sb.append(i).append(" ");
        }
        // 답 출력
        System.out.println(sb.toString().trim());
    }
}