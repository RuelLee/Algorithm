/*
 Author : Ruel
 Problem : Baekjoon 1464번 뒤집기 3
 Problem address : https://www.acmicpc.net/problem/1464
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1464_뒤집기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 문자열 S를 뒤집는다
        // 예를 들어 BCDAF를 길이 3에서 뒵는다면 BCD가 뒤집혀 DCBAF가 된다
        // 이 후 길이 4로 다시 뒤집으면 ABCDF가 된다
        // 문자열 S가 주어졌을 때, 뒤집어서 사전순으로 제일 앞서는 것을 출력하라.
        //
        // 그리디 알고리즘 + 데크
        // 앞에서부터 일정 만큼의 문자열을 뒤집는다 -> 순서대로 문자열을 하나씩 살펴보며
        // 해당 문자열을 이미 완성된 문자열의 앞이나 뒤에 붙일 수 있는 것과 같다.
        // 따라서 문자열의 문자를 순서대로 살펴보며 데크에 담는다.
        // 이 때 문자가 데크의 맨 앞 문자보다 같거나 작다면 앞에 붙이고
        // 그렇지 않은 경우는 무조건 뒤에 붙인다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        Deque<Character> deque = new LinkedList<>();
        // 첫 문자
        deque.addFirst(input.charAt(0));
        // 나머지 문자들을 살펴본다.
        for (int i = 1; i < input.length(); i++) {
            // i번째 문자가 데크의 맨 앞 문자보다 같거나 더 이른 문자라면
            // 데크 앞에 붙이고
            if (input.charAt(i) <= deque.peekFirst())
                deque.addFirst(input.charAt(i));
            // 그렇지 않다면 데크 뒤에 붙인다.
            else
                deque.addLast(input.charAt(i));
        }

        StringBuilder sb = new StringBuilder();
        // 최종 완성된 데크의 모든 문자들을 순서대로 출력한다.
        while (!deque.isEmpty())
            sb.append(deque.pollFirst());
        System.out.println(sb);
    }
}