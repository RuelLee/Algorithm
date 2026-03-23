/*
 Author : Ruel
 Problem : Baekjoon 16890번 창업
 Problem address : https://www.acmicpc.net/problem/16890
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16890_창업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 구사과와 큐브러버 둘이 각각 n개의 문자를 고른다.
        // 둘이 같이 길이 n의 문자열 하나를 만드는데
        // 구사과는 사전순으로 가장 빠른, 큐브러버는 가장 느린 문자열을 만들려고 한다.
        // 만들어진 문자열을 출력하라
        //
        // 정렬 문제
        // 가장 기본이 되는 것은 구사과는 자신이 가진 문자 중 가장 빠른 것을 가장 앞에
        // 큐브러버는 가진 문자 중 가장 느린 것을 가장 앞에 배치하려할 것이다.
        // 구사과부터 번갈아가면서 해당 원칙에 따라 배치한다.
        // 그러다 규칙이 깨지는 순간이 발생한다.
        // 구사과가 가진 가장 이른 문자가 큐브러버가 가진 가장 느린 문자보다도 느리다면?
        // 그러면 구사과는 자신이 가진 문자를 문자열 끝에 배치할수록 유리해진다.
        // 마찬가지로 큐브러버 또한 자신이 가진 문자를 끝 쪽에 배치할수록 유리하다.
        // 따라서 해당 순간부터는 구사과는 자신이 가진 문자 중 가장 느린 문자를 가장 뒤쪽에
        // 큐브러버는 자신이 가진 문자 중 가장 빠른 문자를 가장 뒤쪽에 배치하기 시작한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각자가 선택한 문자들
        char[][] inputs = new char[2][];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = br.readLine().toCharArray();
            // 정렬
            Arrays.sort(inputs[i]);
        }

        // 데크를 통해 구사과는 (n + 1) / 2개의 문자를 빠른 순으로
        // 큐브러버는 n / 2개의 문자를 느린 순으로 담는다.
        // n이 홀수일 경우, 구사과가 하나 더 많은 문자를 선택하기 때문
        Deque<Character>[] deques = new Deque[2];
        for (int i = 0; i < deques.length; i++)
            deques[i] = new LinkedList<>();
        for (int i = 0; i < (inputs[0].length + 1) / 2; i++)
            deques[0].offerLast(inputs[0][i]);
        for (int i = 0; i < (inputs[1].length) / 2; i++)
            deques[1].offerLast(inputs[1][inputs[1].length - 1 - i]);

        char[] answer = new char[inputs[0].length];
        // 아직 안 채워진 문자의 앞 뒤 인덱스
        int[] answerIdx = new int[2];
        answerIdx[1] = answer.length - 1;
        // 차례
        int turn = 0;
        while (answerIdx[0] <= answerIdx[1]) {
            // 두 데크 중 하나가 빈 경우
            // 마지막 문자를 채우는 경우
            // 현재 턴인 사람의 문자를 담는다.
            if (deques[0].isEmpty() || deques[1].isEmpty()) {
                answer[answerIdx[0]] = deques[turn].pollFirst();
                answerIdx[0]++;
            } else if (deques[0].peekFirst() < deques[1].peekFirst()) {
                // 구사과의 가장 이른 문자가 큐브러버의 가장 느린 문자보다 빠른 경우
                // 정석적으로 가장 앞에서부터, 자신이 원하는 문자를 배치한다.
                answer[answerIdx[0]++] = deques[turn].pollFirst();
                turn = (turn + 1) % 2;
            } else {
                // 구사과의 가장 이른 문자가 큐브러버의 가장 느린 문자보다 느려진 경우
                // 뒤에서부터, 구사과는 자신이 가진 가장 느린 문자
                // 큐브러버는 자신이 가진 가장 빠른 문자를 배치한다.
                answer[answerIdx[1]--] = deques[turn].pollLast();
                turn = (turn + 1) % 2;
            }
        }

        // 답 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answer.length; i++)
            sb.append(answer[i]);
        // 출력
        System.out.println(sb);
    }
}