/*
 Author : Ruel
 Problem : Baekjoon 12104번 순환 순열
 Problem address : https://www.acmicpc.net/problem/12104
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 순환순열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // A와 B 두 바이너리 문자열이 주어진다.
        // 순환 순열이란 수를 왼쪽으로 한칸씩 순환 이동 시킨 순열이다. 110 -> 101(1번), 011(2번)
        // A와 XOR 했을 때 0이 나오는 B의 순환 순열의 개수를 구하라.
        // XOR 연산 -> 같을 각 자리의 값이 같을 때만 0이 나온다.
        // 따라서 B의 순환 순열 중 A와 일치할 경우에만 XOR 연산시 0이 나온다
        // 각 순환 순열을 모두 구해 개별적으로 연산하는 건 비효율적이다.
        // KMP 알고리즘을 적용해서 풀어보자
        // A에 대해서 pi배열을 구해, A 문자열 내에서 반복되는 구간을 구해, 특정 문자가 달랐을 때 처음으로 돌아가는 것이 아닌 반복되는 구간으로 돌아간다.(세이브 포인트 같은 느낌)
        // B의 순환 순열에 대해서는 길이가 n이라 했을 때,
        // 원래 문자열은 0, ..., (n - 1)
        // 첫번째 순환 순열은 1, ..., (n -1), 0
        // 중복되지 않는 마지막 순환 순열은, (n - 1), ..., (n - 2) 형태를 띈다.
        // 따라서 i를 0값부터, n * 2 - 1값까지 진행시키며 이를 n값으로 모듈러 연산한 값을 인덱스로 사용한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] a = br.readLine().toCharArray();
        char[] b = br.readLine().toCharArray();

        int[] pi = getPi(a);        // pi배열을 구하고
        int j = 0;
        int count = 0;
        // i는 0부터 b의 길이 * 2 - 1까지 진행시키며,
        for (int i = 0; i < b.length * 2 - 1; i++) {
            // a의 j번째 글자와 b의 i % b.length번째 글자가 일치하지 않는다면
            // 일치하는 구간으로 돌아간다.
            while (j > 0 && a[j] != b[i % b.length])
                j = pi[j - 1];
            // a의 j번째 글자와 b의 i % b.length번째 글자가 일치한다면, j값을 하나 증가.
            if (a[j] == b[i % b.length])
                j++;
            // j가 a의 마지막 글자까지 도달했다면, XOR 연산해서 0이 나오는 경우를 하나 찾았다.
            // 하나 카운트하고, 한 구간 전으로 돌아가준다.
            if (j >= a.length) {
                count++;
                j = pi[j - 1];
            }
        }
        System.out.println(count);
    }

    static int[] getPi(char[] c) {      // pi 배열을 구하는 메소드
        int[] pi = new int[c.length];       // c와 같은 길이로 생성.
        int j = 0;
        for (int i = 1; i < pi.length; i++) {       // 1부터 시작
            // i번째 값과 j번째 값이 일치하지 않는다면
            // 일치했던 구간으로 돌아간다.
            while (j > 0 && c[i] != c[j])
                j = pi[j - 1];
            // 일치한다면, pi배열에 j값을 하나 증가시켜 기록해주자.
            if (c[i] == c[j])
                pi[i] = ++j;
        }
        // 최종적으로 완성된 pi 배열을 리턴.
        return pi;
    }
}