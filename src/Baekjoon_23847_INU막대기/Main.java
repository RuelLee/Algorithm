/*
 Author : Ruel
 Problem : Baekjoon 23847번 INU 막대기
 Problem address : https://www.acmicpc.net/problem/23847
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23847_INU막대기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 막대가 있고, 각 막대의 양 끝에는 I, N, U중 하나의 글자가 적혀있다.
        // 몇 개의 막대기를 서로 잇되, 맞닿은 면에 적힌 글자가 같도록 하고자한다.
        // 원래 주어진 막대 혹은 이은 막대 중 가장 긴 길이는?
        //
        // 애드 혹 문제
        // 복잡한 문제인 듯 보이나, 결론은 간단한 문제
        // 먼저, 막대를 6종류로 나눈다. II, IN, NN, NU, UU, UI
        // II, NN, UU는 자신들끼리 이을 수 있다.
        // IN은 II와 NN, NU는 NN과 UU, UI는 UU와 II를 이어줄 수 있다.
        // 이렇게 다른 알파벳과 잇는 막대를 다리 막대, 같은 알파벳끼리 있는 막대를 루프 막대라 하자.
        // 다리 막대가 한 종류만 있다면, 양 루프 막대기만 이어줄 수 있다.
        // 다리 막대가 두 종류 이상 있다면, 모든 루프 막대기들을 이어줄 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 막대
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        // 막대들을 분류한다.
        int[] sticks = new int[6];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            char[] ends = st.nextToken().toCharArray();
            int min = Math.min(charToInt(ends[0]), charToInt(ends[1]));
            int max = Math.max(charToInt(ends[0]), charToInt(ends[1]));
            int t = Integer.parseInt(st.nextToken());

            int idx = -1;
            // II, IN, NN, NU, UU, UI 순서대로 0 ~ 5번
            // I는 0, N는 1, U는 2로 값을 할당했을 때
            // 결국 두 글자의 합과 같다.
            // UI만 예외로 5번이다.
            if (min == 0 && max == 2)
                idx = 5;
            else
                idx = min + max;
            // 길이 합산
            sticks[idx] += t;
        }

        int answer = 0;
        // 단일 종류 막대의 길이
        for (int i = 0; i < sticks.length; i++)
            answer = Math.max(answer, sticks[i]);

        // 다리 막대의 종류를 센다
        int cnt = 0;
        // 다리 막대를 통해 두 루프 막대를 이었을 때의 최대 길이
        for (int i = 1; i < sticks.length; i += 2) {
            if (sticks[i] > 0) {
                answer = Math.max(answer, sticks[(i + 5) % 6] + sticks[i] + sticks[(i + 7) % 6]);
                cnt++;
            }
        }

        // 다리 막대의 종류가 2개 이상이라면
        // 모든 루프 막대와 다리 막대를 이을 수 있다.
        if (cnt >= 2) {
            // 총합
            int sum = 0;
            for (int i = 0; i < sticks.length; i++)
                sum += sticks[i];
            answer = Math.max(answer, sum);
        }
        
        // 답 출력
        System.out.println(answer);
    }

    static int charToInt(char c) {
        if (c == 'I')
            return 0;
        else if (c == 'N')
            return 1;
        return 2;
    }
}