/*
 Author : Ruel
 Problem : Baekjoon 1498번 주기문
 Problem address : https://www.acmicpc.net/problem/1498
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1498_주기문;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문자열을 연달아 쓴 것을 (x)^n이라고 나타낸다. 예를 들어 abab 는 (ab)^n이다
        // 어떤 문자열 s가 주어질 때, 앞에서부터 i개의 문자열이 (x)^n의 형태로 표시될 수 있는지 확인하고
        // 그러한 경우가 여러가지라면 n이 최대가 되는 경우를 출력하라
        // ex) ababab일 경우 
        // 길이가 4일때, abab로 '4 2'     길이가 4이고, ab가 2번 반복됨
        // 길이가 6일 때, ababab로 '6 3'      길이가 6이고 ab가 3번 반복됨
        //
        // KMP 알고리즘 문제 pi배열을 자세히 살펴보면 코드량 자체는 간단하게 끝난다.
        // ababab일 경우 pi배열은
        // 0 0 1 2 3 4 의 형태로 채워진다
        // 여기서 마지막 4의 뜻은, 자신부터 앞의 4개의 문자들이, 처음부터 4개의 문자들과 일치한다는 걸 의미한다
        // 따라서 자신의 idx + 1에서 pi[idx]를 빼주면 반복되는 문자열의 길이가 나온다.
        // 그리고 자신의 pi 값이 반복되는 문자열의 배수인지 확인하고, 배수라면, 몫 + 1값이 반복되는 횟수가 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        int[] pi = new int[s.length()];
        int j = 0;
        // pi 배열 작성
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j))
                j = pi[j - 1];

            if (s.charAt(i) == s.charAt(j))
                pi[i] = ++j;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < s.length(); i++) {
            // pi 값이 0이라면 자신과 앞의 접두사가 일치하지 않는 경우. 건너 뛰자.
            if (pi[i] == 0)
                continue;

            // pi 값이 존재한다면, 자신의 순서에서 pi값을 빼주자
            // 그렇게 한다면 현재 자신이 일치하는 접두사의 길이가 나온다
            int cycleLength = (i + 1) - pi[i];
            // 그리고 현재 i번째까지의 문자열의 길이가 접두사의 배수가 되는지 확인한다.
            // abcab에서는 0 0 0 1 2의 형태로 작성될 텐데 배수가 되지 못한다
            // 배수가 된다면 현재 문자열의 길이 i + 1
            // 그리고 반복되는 횟수인 (pi[i] / cycleLength) + 1 을 기록해준다.
            if (pi[i] % cycleLength == 0)
                sb.append((i + 1)).append(" ").append((pi[i] / cycleLength) + 1).append("\n");
        }
        System.out.println(sb);
    }
}