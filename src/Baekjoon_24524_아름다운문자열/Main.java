/*
 Author : Ruel
 Problem : Baekjoon 24524번 아름다운 문자열
 Problem address : https://www.acmicpc.net/problem/24524
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24524_아름다운문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문자열 S와 T가 주어진다.
        // 문자열 S에서 순서대로 문자들을 떼와 T를 여러개 만들고자 한다.
        // 최대 만들 수 있는 T의 개수는?
        //
        // 그리디 문제
        // '순서대로' 떼온다는 점을 유의한다.
        // 가령 S가 abba이고 T가 ab라면
        // 첫번째 a와 두번째 혹은 세번째 b로 ab 하나만 만들 수 있다.
        // 따라서 S에 문자열을 순차적으로 살펴보며
        // T는 부분 완성된 문자열의 수를 센다.
        // 그 후, 가장 많이 완성이 된 문자열에 우선적으로 살펴보며 완성시켜 나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 문자열 S와 T
        String s = br.readLine();
        String t = br.readLine();

        // 부분 완성된 T의 완성 정도에 따른 개수를 센다.
        int[] counts = new int[t.length()];
        // s는 순차적으로
        for (int i = 0; i < s.length(); i++) {
            // 많이 완성된 부분 문자열을 먼저 완성 시키기 위해서
            // t는 거꾸로 세나간다.
            for (int j = t.length() - 1; j >= 0; j--) {
                // i와 j가 일치한다면
                if (s.charAt(i) == t.charAt(j)) {
                    // j가 첫번째 문자열일 경우에는 개수 증가.
                    if (j == 0)
                        counts[0]++;
                    else if (counts[j - 1] > 0) {
                        // 0이 아닐 때는 j-1까지 완성된 부분 문자열이 있는지 확인해야한다.
                        // 존재한다면 j-1을 하나 감소시키고, j를 하나 증가시킨다.
                        counts[j - 1]--;
                        counts[j]++;
                    }
                }
            }
        }
        // 완성된 t문자열의 개수를 출력한다.
        System.out.println(counts[t.length() - 1]);
    }
}