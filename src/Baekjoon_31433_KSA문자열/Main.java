/*
 Author : Ruel
 Problem : Baekjoon 31433번 KSA 문자열
 Problem address : https://www.acmicpc.net/problem/31433
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31433_KSA문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[] ksa = {'K', 'S', 'A'};

    public static void main(String[] args) throws IOException {
        // x라는 문자열이 주어진다.
        // ksa를 사랑하기 때문에 다음 조건에 해당하는 문자열로 바꾸고자 한다.
        // i를 3나눈 나머지에 따라
        // 1이면 K, 2면 S, 3이면 A가 되어야 한다.
        // 이 때 사용할 수 있는 연산은
        // 1. 아무 문자 하나를 지운다.
        // 2. 맨 앞에 문자 하나를 추가한다.
        // 3. 맨 뒤에 문자 하나를 추가한다.
        // 처음 주어지는 문자열과 길이가 같으며, 위 조건을 만족하는 문자열로 바꾸고자할 때
        // 최소 연산 횟수는?
        //
        // 그리디 문제
        // 그냥 문자열의 문자를 순서대로 살펴보며, KSA 순서에 해당하는 문자들 세고
        // 해당하지 않는 문자를 모두 지우고 뒤에 KSA를 연속하여 붙이면 된다.
        // 다만, 맨 앞에 문자도 추가할 수 있으므로
        // 처음에 맨 앞에 아무것도 안 붙이는 경우
        // K를 붙이고 시작하는 경우, KS를 붙이고 시작하는 경우를 따져본다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어진 문자열
        String x = br.readLine();
        
        // 답
        int answer = Integer.MAX_VALUE;
        // 맨 앞에 문자를 붙이는 경우를 따져본다.
        // start가 0이면 아무것도 붙이지 않는 경우
        // 1이면 K를 앞에 붙인 경우, 2이면 KS를 앞에 붙인 경우이다.
        for (int start = 0; start < 3; start++) {
            int count = 0;
            // KSA를 반복해서 만족하는 개수를 센다.
            for (int i = 0; i < x.length(); i++) {
                if (x.charAt(i) == ksa[(start + count) % 3])
                    count++;
            }
            // 만족하는 않는 문자 만큼을 지워야한다.
            int deleted = x.length() - count;
            // 만약 지운 문자가 앞에 붙인 문자의 수보다 같거나 큰 경우
            // 앞에 붙인 문자의 수(start)+ 지운 문자(deleted) + 남은 길이(deleted - start)
            // = deleted * 2
            if (deleted >= start)
                answer = Math.min(answer, deleted * 2);
            // 앞에 붙인 문자의 수가 지운 문자의 수보다 더 많은 경우
            // 앞에 붙은 문자의 수(start) + 지운 문자의 수(deleted) + 넘치는 길이(start - deleted)
            // = start * 2
            else
                answer = Math.min(answer, start * 2);
        }
        // 최종 답안 출력
        System.out.println(answer);
    }
}