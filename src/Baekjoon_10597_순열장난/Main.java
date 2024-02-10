/*
 Author : Ruel
 Problem : Baekjoon 10597번 순열장난
 Problem address : https://www.acmicpc.net/problem/10597
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10597_순열장난;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 수로 이루어진 순열이 모두 이어써져있다.
        // 이를 공백으로 분리한 순열로 복구하라
        //
        // 백트래킹 문제
        // 주어진 s의 길이를 통해 1 ~ n의 n을 구할 수 있다.
        // 백트래킹을 통해 가능한 수들을 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어진 이어붙여쓴 순열 s
        String s = br.readLine();

        // s의 길이를 통해 n을 구한다.
        int maxNum = s.length() < 10 ? s.length() : (s.length() - 9) / 2 + 9;
        // 데크를 통해 순열을 찾는다.
        Deque<Integer> deque = new LinkedList<>();
        findAnswer(0, s, new boolean[maxNum + 1], deque);

        StringBuilder sb = new StringBuilder();
        // 데크에 있는 값을 앞에서부터 순서대로 기록한다.
        while (!deque.isEmpty())
            sb.append(deque.pollFirst()).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력
        System.out.println(sb);
    }

    // 백트래킹을 활용하여 순열을 찾는다.
    static boolean findAnswer(int idx, String s, boolean[] pick, Deque<Integer> deque) {
        // 모든 글을 살펴봤다면 수의 배치가 끝난 경우.
        // true 반환
        if (idx == s.length())
            return true;
        
        // idx번째 한 자리의 수
        int num = s.charAt(idx) - '0';
        // 0이 아니고
        if (num > 0) {
            // 아직 선택되지 않은 수라면
            if (!pick[num]) {
                // 데크에 추가
                deque.offerLast(num);
                // 선택 표시
                pick[num] = true;
                // idx를 num으로 처리했을 때, 답을 찾을 수 있는지 확인한다.
                // 찾는다면 true 반환
                if (findAnswer(idx + 1, s, pick, deque))
                    return true;
                // 선택 표시 해제
                pick[num] = false;
                // 데크에서 제거
                deque.pollLast();
            }
            
            // idx와 idx + 1 수로 두자리 수로 계산할 경우.
            if (idx + 1 < s.length()) {
                num *= 10;
                num += s.charAt(idx + 1) - '0';
                if (num < pick.length && !pick[num]) {
                    deque.offerLast(num);
                    pick[num] = true;
                    if (findAnswer(idx + 2, s, pick, deque))
                        return true;
                    pick[num] = false;
                    deque.pollLast();
                }
            }
        }
        // 위의 경우가 불가능하다면 false 반환.
        return false;
    }
}