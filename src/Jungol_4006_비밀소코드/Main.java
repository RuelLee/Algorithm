/*
 Author : Ruel
 Problem : Jungol 4006번 비밀 소 코드
 Problem address : https://jungol.co.kr/problem/4006
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4006_비밀소코드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 한 단어가 주어지면
        // 해당 단어 w 뒤에 w의 맨 뒷 글자를 앞으로 보낸 형태로 이어붙여 무한히 만든다.
        // 예를 들어
        // w가 cow라면 cow -> cowwco -> cowwcoocowwc -> ...
        // 이 때 n번째 글자를 출력하라
        //
        // 재귀, 분할 정복 문제
        // 먼저, 단어의 길이를 n보다 큰 범위까지 2배를 하며 늘려간다
        // 현재 형태는 w + w` 형태이다.
        // n이 현재 길이의 1/2 이하라면 그대로, 1/2 초과라면 w에서의 위치로 값을 보정한다.
        // 해당 작업을 길이가 원래 주어진 w의 길이가 될 때까지 반복하여 문자를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 원래 주어지는 문제
        String word = st.nextToken();
        // 원하는 순서
        long n = Long.parseLong(st.nextToken()) - 1;

        // length가 n보다 같거나 작은 동안 계속 2배를 해나간다.
        long length = word.length();
        while (length <= n)
            length *= 2;

        //
        while (length > word.length()) {
            // 현재 길이의 반을 먼저 구해두고
            length /= 2;
            // n이 현재 길이의 반보다 크다면
            if (n >= length) {
                // 먼저, length만큼을 빼, w`에서만의 순서를 찾고
                n -= length;
                // w`는 w에서 맨 뒤글자가 앞으로 온 형태이므로 순서를 보정
                n = (n + length - 1) % length;
            }
        }
        // 답 출력
        System.out.println(word.charAt((int) n));
    }
}