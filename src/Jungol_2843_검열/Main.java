/*
 Author : Ruel
 Problem : Jungol 2843번 검열
 Problem address : https://jungol.co.kr/problem/2843
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2843_검열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문자열 s와 t가 주어질 때
        // 문자열 s 내에서 t와 일치하는 부분을 삭제하고 나머지 부분을 이어붙인다.
        // 이어붙인 부분에서도 t가 발견된다면 계속 삭제한다.
        //
        // KMP
        // 오랜만에 kmp 알고리즘으로 문제를 풀어봤다
        // 문자열 일치 여부를 판단하는 알고리즘
        // 일치를 확인하는 문자열 내부에서 접두사와 접미사가 얼마나 일치하는지 여부를 계산해서
        // 하나의 문자열이 일치하지 않을 때, 기존에 구해둔 결과값에 의해서 처음부터 일치여부를 따지는게 아닌
        // 중간에 계산해둔 위치로 돌아가 연산을 줄이는 방법

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 문자열 s, t
        String s = br.readLine();
        String t = br.readLine();

        // t를 기반으로 pi 배열을 구한다.
        // 현재 위치에서 검사해야야할 다음 문자의 위치를 가르킨다.
        int[] pi = new int[t.length()];
        int j = 0;
        for (int i = 1; i < t.length(); i++) {
            // i, j번째 문자가 일치하지 않는다면, 이전 위치가 가르키는 위치로 돌아간다.
            while (j > 0 && t.charAt(i) != t.charAt(j))
                j = pi[j - 1];

            // 일치하는 경우, 한 칸 전진.
            if (t.charAt(i) == t.charAt(j))
                pi[i] = ++j;
        }

        // 스택으로 처리 가능하나, 추후에 답 작성 시를 위해 데크 사용
        Deque<int[]> deque = new LinkedList<>();
        j = 0;
        // s열 문자열을 살펴보며
        // j 포인터로 일치 여부를 계산해나간다.
        for (int i = 0; i < s.length(); i++) {
            // 문자가 일치 하지 않을 경우, 돌아감.
            while (j > 0 && s.charAt(i) != t.charAt(j))
                j = pi[j - 1];

            // 일치하는 경우
            if (s.charAt(i) == t.charAt(j)) {
                // 문자열을 발견한 경우.
                if (++j == t.length()) {
                    // 현재 마지막 문자를 넣지 않았으므로
                    // 데크에서 t의 길이 -1만큼의 문자를 제거
                    for (int k = 0; k < t.length() - 1; k++)
                        deque.pollLast();
                    // 데크의 최상단을 보고 j의 위치 결정
                    j = deque.isEmpty() ? 0 : deque.peekLast()[1];
                } else  // 그 외의 경우는 그냥 데크에 추가
                    deque.offerLast(new int[]{i, j});
            } else  // 일치 안하는 경우도 데크에 추가
                deque.offerLast(new int[]{i, j});
        }

        StringBuilder sb = new StringBuilder();
        // 앞에서부터 차례대로 꺼내며 답 기록
        while (!deque.isEmpty())
            sb.append(s.charAt(deque.pollFirst()[0]));
        // 출력
        System.out.println(sb);
    }
}