/*
 Author : Ruel
 Problem : Baekjoon 13019번 A를 B로
 Problem address : https://www.acmicpc.net/problem/13019
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13019_A를B로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 같은 두 문자열 A, B가 주어진다.
        // 하나의 문자열을 골라 맨 앞으로 보낼 수 있다.
        // 해당 연산을 최소한으로 하여, A 문자열을 B 문자열로 만들고 싶다.
        // 그 횟수는? 불가능하다면 -1을 출력한다.
        //
        // 그리디 문제
        // 먼저 A 문자열을 B 문자열로 만들기 위해서는 포함된 문자의 개수가 같아야한다.
        // 그리고 문자열을 맨 앞으로만 보낼 수 있으므로 B 문자열의 뒷 부분부터 순서가 일치하는 것만 남길 수 있다.
        // 그 외에는 모드 연산을 통해 앞을 보내되, 언제 보낼지 순서의 차이만 발생한다.
        // A와 B 문자열을 뒤에서부터 살펴보며, B의 문자와 A의 문자가 일치할 때만 B의 idx를 감소시킨다.
        // 최종적으로 idx의 +1이 남은 문자들의 길이이므로 해당 수를 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = new String[2];

        // 문자의 개수
        int[][] counts = new int[2][26];
        for (int i = 0; i < 2; i++) {
            inputs[i] = br.readLine();
            for (int j = 0; j < inputs[i].length(); j++)
                counts[i][inputs[i].charAt(j) - 'A']++;

        }

        // 개수 비교
        boolean possible = true;
        for (int i = 0; i < 26; i++) {
            if (counts[0][i] != counts[1][i]) {
                possible = false;
                break;
            }
        }

        // B 문자열의 idx
        int idx = inputs[1].length() - 1;
        // A 문자열의 뒷부분부터 살펴보며, 문자가 일치할 때만 idx를 감소
        for (int i = inputs[0].length() - 1; i >= 0; i--) {
            if (inputs[0].charAt(i) == inputs[1].charAt(idx))
                idx--;
        }

        // 각 문자의 개수가 일치하고, idx가 앞으로 진행을 했다면
        // idx + 1을 출력
        // 그 외의 경우는 불가능하므로 -1을 출력
        System.out.println((!possible || idx == inputs[1].length() - 1) ? -1 : idx + 1);
    }
}