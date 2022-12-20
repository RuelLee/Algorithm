/*
 Author : Ruel
 Problem : Baekjoon 6443번 애너그램
 Problem address : https://www.acmicpc.net/problem/6443
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6443_애너그램;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 애너그램을 만드는 프로그램을 작성하라
        // abc를 입력받았다면 abc acb bac bca cab cba를 출력해야한다.
        //
        // 백트래킹 문제
        // 일단 같은 알파벳이 여러개 주어졌을 때 결과로 중복된 결과가 나오는 경우를 제외해야한다.
        // aa가 주어진다면 첫번째 a가 앞일 때 aa와 뒤일 때 aa인 경우 2가지가 아니라 한 번만 출력해야한다.
        // 따라서 주어진 단어를 알파벳 개수로 분해하고, 백트래킹을 이용하여 조합하여 나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 전체 결과들
        StringBuilder answer = new StringBuilder();
        for (int t = 0; t < n; t++) {
            // 주어진 단어
            String input = br.readLine();
            int[] alphabets = new int[26];
            // 알파벳의 개수대로 분해
            for (int i = 0; i < input.length(); i++)
                alphabets[input.charAt(i) - 'a']++;

            // 애너그램을 만듦.
            makeAnagram(alphabets, input.length(), answer, new StringBuilder());
        }
        System.out.print(answer);
    }

    // 백트래킹을 활용해 애너그램을 만든다.
    static void makeAnagram(int[] alphabets, int remain, StringBuilder answer, StringBuilder word) {
        // 남은 알파벳이 0개일 경우 애너그램 완성
        if (remain == 0) {
            // 전체 결과에 추가.
            answer.append(word.toString()).append("\n");
            return;
        }

        // 남은 알파벳이 있을 경우.
        for (int i = 0; i < alphabets.length; i++) {
            // i번째 알파벳이 남은 경우.
            if (alphabets[i] > 0) {
                // 해당 알파벳을 현재 만들고 있는 애너그램 뒤에 추가.
                word.append((char) ('a' + i));
                // 개수 차감.
                alphabets[i]--;
                // 애너그램의 다음 글자를 만들기 위해 재귀.
                makeAnagram(alphabets, remain - 1, answer, word);

                // i번째 알파벳을 선택하지 않았을 때 경우로 복구
                // 종료 후 다시 i번째 알파벳 개수 증가.
                alphabets[i]++;
                // 애너그램 뒤에 붙어있는 마지막 알파벳 삭제.
                word.deleteCharAt(word.length() - 1);
            }
        }
    }
}