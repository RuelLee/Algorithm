/*
 Author : Ruel
 Problem : Baekjoon 16472번 고냥이
 Problem address : https://www.acmicpc.net/problem/16472
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16472_고냥이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 동시에 인식할 수 있는 알파벳의 종류 n이 주어진다.
        // 소문자로 이루어진 문자열이 주어질 때,
        // 최대 n가지 종류로 이루어진 부분문자열 중 최대 길이는?
        //
        // 두 포인터 문제
        // 하나는 앞쪽, 하나는 뒤쪽의 포인터를 갖고서 n가지의 종류로 이루어진
        // 부분문자열의 최대 길이를 찾는다.
        // 해당 포인터들 사이에 어떠한 알파벳이 있는지 세어가며 종류를 n이하로 유지한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        int n = Integer.parseInt(br.readLine());
        String input = br.readLine();

        // 두 포인터들 사이의 알파벳들을 종류에 따라 센다.
        int[] alphabets = new int[26];

        // 초기 상태로 첫 글자를 추가시켜두자.
        // 종류는 1
        int kinds = 1;
        // 길이는 1
        int maxLength = 1;
        // j가 뒤쪽 포인터.
        int j = 0;
        // 첫 알파벳의 개수 증가.
        alphabets[input.charAt(0) - 'a']++;
        // i가 앞쪽 포인터.
        for (int i = 0; i < input.length(); i++) {
            // i가 0보다 클 때만
            // 이전 알파벳을 하나씩 제외해 나간다.
            // 이전 알파벳을 하나 제외하여, 0개가 된다면 종류를 하나 감소.
            if (i > 0 && --alphabets[input.charAt(i - 1) - 'a'] == 0)
                kinds--;

            // j + 1이 문자열 범위 내이며
            // 종류가 n개 미만이라면 무조건 추가 가능
            // 종류가 n이면서, j + 1번째 알파벳이 두 포인터들 사이에 포함된 알파벳이라면 j 증가 가능.
            while (j + 1 < input.length() &&
                    (kinds < n || (kinds == n && alphabets[input.charAt(j + 1) - 'a'] > 0))) {
                // j+1번째 알파벳이 두 포인터들 사이에 없는 알파벳이라면 종류가 증가해야한다.
                if (alphabets[input.charAt(j + 1) - 'a'] == 0)
                    kinds++;
                // j + 1번째 알파벳의 개수 증가.
                alphabets[input.charAt(++j) - 'a']++;
            }
            // i ~ j까지의 길이가 최대 길이를 갱신하는지 확인
            maxLength = Math.max(maxLength, j - i + 1);
        }

        // 최대 길이 출력.
        System.out.println(maxLength);
    }
}