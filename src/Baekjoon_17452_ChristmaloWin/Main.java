/*
 Author : Ruel
 Problem : Baekjoon 17452번 Christmalo.win
 Problem address : https://www.acmicpc.net/problem/17452
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17452_ChristmaloWin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 단어 중 공통된 알파벳을 선택하여 한 단어로 묶는다.
        // 예를 들어 christmas와 halloween을 선택했다면, 공통된 알파벳 a를 선택하여
        // christma와 alloween을 만들고 이 둘을 합쳐서 christmalloween이 된다.
        // 이 때 선택한 두 단어에서 제외되는 알파벳들을 최소화하고자할 때, 제외되는 알파벳의 수는?
        //
        // DP 문제
        // 각 단어, 각 자리의 알파벳 위치에서 왼쪽에, 오른쪽에 위치한 알파벳들의 수를 세고
        // 그 개수가 적은 것을 계속해서 DP에 남겨둔다.
        // 그리고 각 단어들을 이전 알파벳의 기록 DP를 살펴보며 자신에 속한 알파벳과 왼쪽, 오른쪽에 단어를 붙였을 때
        // 가장 적게 제외되는 알파벳의 수를 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 단어의 수
        int n = Integer.parseInt(br.readLine());

        // 이전에 등장한 단어들 중 왼쪽에서부터 셌을 때
        // 제외되는 알파벳이 가장 적은 개수를 기록한다.
        int[] fromLeft = new int[26];
        Arrays.fill(fromLeft, Integer.MAX_VALUE);
        // 오른쪽도 마찬가지로 계산해준다.
        int[] fromRight = new int[26];
        Arrays.fill(fromRight, Integer.MAX_VALUE);
        // 첫단어는 바로 DP에 반영
        reflectWord(br.readLine(), fromLeft, fromRight);
        
        // 가장 제외되는 수가 적은 알파벳의 수
        int answer = Integer.MAX_VALUE;
        // n - 1개의 단어들에 대해 진행.
        for (int i = 1; i < n; i++) {
            // 단어
            String word = br.readLine();
            // 단어의 모든 알파벳을 살펴봄
            for (int j = 0; j < word.length(); j++) {
                // 왼쪽에서 j번째 알파벳
                int leftCharIdx = word.charAt(j) - 'a';
                // DP를 살펴보고, word의 j번째 알파벳을 기준으로 오른쪽에 붙일 수 있다면(= word의 j-1이하 알파벳들을 버리고)
                // 그 때의 제외되는 단어를 answer에 반영
                if (fromRight[leftCharIdx] != Integer.MAX_VALUE)
                    answer = Math.min(answer, j + fromRight[leftCharIdx]);

                // 오른쪽에서 j번째 알파벳
                int rightCharIdx = word.charAt(word.length() - 1 - j) - 'a';
                // 마찬가지로 word를 오른쪽에서 j번째 알파벳을 기준으로 (= rightCharIdx보다 오른쪽에 있는 알파벳들을 버리고)
                // 왼쪽에 붙일 수 있는지 확인하고 answer에 값을 반영한다.
                if (fromLeft[rightCharIdx] != Integer.MAX_VALUE)
                    answer = Math.min(answer, j + fromLeft[rightCharIdx]);
            }
            // word를 dp에 반영하여 다음 단어에 활용할 수 있도록 한다.
            reflectWord(word, fromLeft, fromRight);
        }
        // 답안 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    // word를 dp에 계산해 넣는다.
    static void reflectWord(String word, int[] fromLeft, int[] fromRight) {
        for (int i = 0; i < word.length(); i++) {
            // 왼쪽에서 i번째 단어의 idx
            int leftCharIdx = word.charAt(i) - 'a';
            // 제외되는 최소 알파벳의 수 갱신
            fromLeft[leftCharIdx] = Math.min(fromLeft[leftCharIdx], i);

            // 오른쪽도 마찬가지로 계산.
            int rightCharIdx = word.charAt(word.length() - 1 - i) - 'a';
            fromRight[rightCharIdx] = Math.min(fromRight[rightCharIdx], i);
        }
    }
}