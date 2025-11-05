/*
 Author : Ruel
 Problem : Baekjoon 13140번 Hello World!
 Problem address : https://www.acmicpc.net/problem/13140
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13140_HelloWorld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int[] multiple;
    static char[] chars = {'d', 'e', 'h', 'l', 'o', 'r', 'w'};
    static String[] words = {"hello", "world"};

    public static void main(String[] args) throws IOException {
        // N이 주어질 때 hello + world = N을 만족하는 서로 다른 한 자리 자연수(0 포함) d, e, h, l, o, r, w를 구해서 아래 그림과 같은 형태로 출력하는 프로그램을 작성하여라. 단, h와 w는 0이 될 수 없다.
        //   h e l l o
        // + w o r l d
        // -----------
        //  □□□□□□
        //
        // 브루트포스, 백트래킹 문제
        // 간단한 브루트포스 문제
        // 문자의 개수가 많지 않으므로, 각 문자에 수를 할당하는 모든 경우의 수를 해보면 된다.
        // h와 w에는 0이 할당될 수 없음에 유의
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        
        // d e h l o r w가 갖는 value
        multiple = new int[]{1, 1000, 10000, 120, 1001, 100, 10000};

        Deque<Integer> deque = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        // 답을 찾았다면
        if (findAnswer(0, 0, new boolean[10], deque)) {
            // 해당 문자에 해당하는 수를 할당
            HashMap<Character, Integer> hashMap = new HashMap<>();
            for (int i = 0; i < chars.length; i++)
                hashMap.put(chars[i], deque.pollFirst());

            // h e l l o에 해당하는 수 기록
            sb.append("  ");
            for (int i = 0; i < words[0].length(); i++)
                sb.append(hashMap.get(words[0].charAt(i)));
            sb.append("\n");
            
            // w o r l d에 해당하는 수 기록
            sb.append("+ ");
            for (int i = 0; i < words[1].length(); i++)
                sb.append(hashMap.get(words[1].charAt(i)));
            sb.append("\n");

            sb.append("-------").append("\n");
            // n의 수 길이에 따른 공백 추가 후, n 기록
            sb.append(n >= 100000 ? " " : "  ").append(n);
        } else      // 만족하는 답을 찾을 수 없는 경우, No Answer 기록
            sb.append("No Answer");
        // 답 출력
        System.out.println(sb);
    }

    // 브루트 포스, 백 트래킹
    // idx번째 문자를 할당하고 있으며, 여태까지의 합 sum, 선택된 수 seleceted
    // 그리고 순서대로 선택된 수를 deque에 담는다.
    static boolean findAnswer(int idx, int sum, boolean[] selected, Deque<Integer> deque) {
        // 끝까지 수 할당을 마쳤다면, sum이 n과 같은지 확인
        if (idx >= multiple.length)
            return sum == n;
        else if (sum > n)   // 모든 수를 할당하지 않았으나, 이미 sum이 n을 넘어선다면 false 반환
            return false;

        // h와 w를 제외하고는 0부터 시작.
        for (int i = (idx == 2 || idx == 6 ? 1 : 0); i < selected.length; i++) {
            // 수 i가 아직 선택되지 않았다면
            if (!selected[i]) {
                // idx번째에 i를 할당해본다.
                selected[i] = true;
                deque.offerLast(i);
                // 그리고 해당 경우로 파생되는 경우들 중, 답을 만족하는 경우가 있다면 true 반환
                if (findAnswer(idx + 1, sum + multiple[idx] * i, selected, deque))
                    return true;
                
                // 그렇지 않은 경우 선택 표시 및 데크에서 제거하여 다른 수를 시도
                selected[i] = false;
                deque.pollLast();
            }
        }
        // 9까지 모두 시도 했으나 불가능한 경우
        // false 반환
        return false;
    }
}