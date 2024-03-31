/*
 Author : Ruel
 Problem : Baekjoon 21941번 문자열 제거
 Problem address : https://www.acmicpc.net/problem/21941
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21941_문자열제거;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Word {
    String word;
    int score;

    public Word(String word, int score) {
        this.word = word;
        this.score = score;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 문자열 S와 지울 수 있는 m개의 A1, A2, ..., Am 문자열이 주어진다.
        // 그리고 각 Ai에 대해서는 지울 때의 점수가 주어진다.
        // S의 부분문자열 중 Ai가 존재한다면 해당하는 점수를 얻으며 해당 부분문자열을 지울 수 있다.
        // 문자 하나를 그냥 지운다면 1점을 얻는다
        // 문자열 S를 전부 지우고자할 때 얻을 수 있는 최대 점수는?
        //
        // DP문제
        // dp[시작][끝] = 얻을 수 있는 최대 점수
        // 로 dp를 세우고 푼다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 문자열 S
        String s = br.readLine();
        // 지울 수 있는 문자열 m개
        int m = Integer.parseInt(br.readLine());

        // 지울 수 있는 문자열을 길이에 따라 분류한다.
        HashMap<Integer, List<Word>> words = new HashMap<>();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            int x = Integer.parseInt(st.nextToken());

            if (!words.containsKey(a.length()))
                words.put(a.length(), new ArrayList<>());
            words.get(a.length()).add(new Word(a, x));
        }

        // DP
        // 하나의 문자열을 지울 때는 기본적으로 1점.
        int[][] dp = new int[s.length()][s.length()];
        for (int i = 0; i < dp.length; i++)
            dp[i][i] = 1;

        // 지울 수 있는 문자열에 길이 1짜리 문자가 있었다면 해당 사항도 반영해야하므로
        // diff가 0일 때부터 시작.
        for (int diff = 0; diff < s.length(); diff++) {
            // 살펴보고자하는 문자열의 시작 idx start
            for (int start = 0; start + diff < s.length(); start++) {
                // start ~ end까지의 부분문자열을 살펴본다.
                int end = start + diff;
                
                // 만약 start ~ end에 해당하는 길의 지울 수 있는 문자열이 있는지 살펴보고
                if (words.containsKey(diff + 1)) {
                    for (Word word : words.get(diff + 1)) {
                        boolean possible = true;
                        for (int i = 0; i < word.word.length(); i++) {
                            if (word.word.charAt(i) != s.charAt(start + i)) {
                                possible = false;
                                break;
                            }
                        }
                        // 존재한다면 삭제했을 때의 점수가
                        // 최대 점수인지 확인
                        if (possible)
                            dp[start][end] = Math.max(dp[start][end], word.score);
                    }
                }

                // start ~ mid, mid +1 ~ end까지의 최대 점수 합이
                // start ~ end에 반영된다.
                for (int mid = start; mid < end; mid++)
                    dp[start][end] = Math.max(dp[start][end], dp[start][mid] + dp[mid + 1][end]);
            }
        }
        // S 전체를 지웠을 때의 최대 점수를 출력한다.
        System.out.println(dp[0][s.length() - 1]);
    }
}