/*
 Author : Ruel
 Problem : Jungol 12013번 짝 맞추기
 Problem address : https://jungol.co.kr/problem/12013
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12013_짝맞추기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 카드의 형태가 문자열로 주어지며 카드의 종류는 a b c이다.
        // 앞부터 살펴보며 두 장 연속인 카드가 a일 경우 두 장의 카드를 b 한장으로
        // b일 경우 c 한 장으로, c일 경우 a 한 장으로 바꾼다.
        // 마지막에 남은 카드를 출력하라
        //
        // 시뮬레이션 문제
        // 데크 두 개를 통해 주어진 조건대로 카드를 교체해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        // 뒷 부분과 앞 부분의 데크
        Deque<Character> second = new LinkedList<>();
        for (char c : input.toCharArray())
            second.offerLast(c);
        Deque<Character> first = new LinkedList<>();

        // 뒤의 데크가 빌 때까지
        while (!second.isEmpty()) {
            // 앞 데크 비었다면 앞 데크로 하나 이동
            if (first.isEmpty())
                first.offerLast(second.poll());
            // 아닌 경우, 앞 데크의 마지막과 뒷 데크의 처음을 비교하여 같다면
            // 하나의 문자로 바꿔 뒷 데크의 처음으로 교환
            else if (first.peekLast() == second.peekFirst()) {
                first.pollLast();
                switch (second.pollFirst()) {
                    case 'a' -> second.offerFirst('b');
                    case 'b' -> second.offerFirst('c');
                    case 'c' -> second.offerFirst('a');
                }
            } else      // 서로 다른 경우, 그냥 앞데크에 추가
                first.offerLast(second.pollFirst());
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        while (!first.isEmpty())
            sb.append(first.pollFirst());
        // 출력
        System.out.println(sb);
    }
}