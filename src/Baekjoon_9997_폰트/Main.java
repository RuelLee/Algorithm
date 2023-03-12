/*
 Author : Ruel
 Problem : Baekjoon 9997번 폰트
 Problem address : https://www.acmicpc.net/problem/9997
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9997_폰트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    // 모든 알파벳이 사용되어을 때의 비트값
    static final int fullBitmask = (int) Math.pow(2, 26) - 1;

    public static void main(String[] args) throws IOException {
        // n개의 단어가 주어진다.
        // 각 단어들을 이용하여 26개의 알파벳이 모두 사용된 문장을 만들고자 한다.
        // 만들 수 있는 문장의 개수는 몇 개인가?
        //
        // 비트마스킹 브루트포스 문제
        // 알파벳이 총 26개이므로, int 타입으로 비트마스킹이 가능하다.
        // 각 단어를 비트마스킹을 통해 포함된 알파벳들을 표시하고
        // 각 단어를 선택했을 때와 선택하지 않았을 때, 모두를 계산해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 단어
        int n = Integer.parseInt(br.readLine());
        String[] words = new String[n];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();

        // 각 단어를 비트마스킹을 통해 하나의 값으로 표현한다.
        int[] bitmasks = new int[n];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++)
                bitmasks[i] |= 1 << (words[i].charAt(j) - 'a');
        }

        // countFullAlphabets를 통해 브루트포스로 모든 경우의 수를 따져
        // 가능한 경우의 수를 출력한다.
        System.out.println(countFullAlphabets(0, 0, bitmasks));
    }

    // 브루트포스를 사용하여 n개의 단어를 조합하여
    // 모든 알파벳이 사용된 문장의 개수를 찾는다.
    static int countFullAlphabets(int idx, int bitmask, int[] bitmasks) {
        // 모든 단어를 살펴보았다면
        // bitmask 값을 살펴보고, 모든 알파벳이 사용되었다면 1, 그렇지 않다면 0을 반환한다.
        if (idx >= bitmasks.length)
            return bitmask == fullBitmask ? 1 : 0;

        // idx단어에 대해 선택할 수 있는 가짓수는 2가지이다.
        // 해당 단어를 선택하거나 선택하지 않거나
        // 선택한 경우에는 매개 변수로 건네는 bitmask에 현재 단어의 비트값을 | 연산을 취하고
        // 그렇지 않은 경우에는 그대로 건넨다.
        // 해당 경우의 수를 더한 값을 반환한다.
        return countFullAlphabets(idx + 1, bitmask | bitmasks[idx], bitmasks) +
                countFullAlphabets(idx + 1, bitmask, bitmasks);
    }
}