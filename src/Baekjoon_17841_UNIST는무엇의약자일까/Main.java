/*
 Author : Ruel
 Problem : Baekjoon 17841번 UNIST는 무엇의 약자일까?
 Problem address : https://www.acmicpc.net/problem/17841
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17841_UNIST는무엇의약자일까;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[] chars = {'U', 'N', 'I', 'S', 'T'};
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 단어가 주어진다.
        // 단어 a의 길이를 len(a)라 할 때
        // 각 단어를 순서대로 앞의 len(a)개 이하의 단어를 따
        // UNIST를 만들 수 있는 경우의 수를 구하라
        //
        // DP 문제
        // 주의할 점은 단어들을 무작위로 선택해서 배열하는 것이 아닌
        // 순서대로 살펴보며 앞글자들을 따 UNIST를 만드는 것이었다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 단어
        int n = Integer.parseInt(br.readLine());
        String[] words = new String[n];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();

        // dp[idx] = 단어들로 idx - 1 길이를 완성한 경우의 수
        int[] dp = new int[6];
        // 초기값은 1
        dp[0] = 1;

        // 단어들을 순서대로 살펴봐야한다.
        for (String word : words) {
            // UNIST에서 한글자씩 해당 글자부터 시작하는 경우를 본다.
            for (int i = 0; i < 5; i++) {
                // word를 첫글자부터 일치하는지 확인.
                // j는 글자의 길이와 남은 UNIST의 길이 중 더 짧은 쪽까지
                for (int j = 0; j < Math.min(word.length(), 5 - i); j++) {
                    // UNIST의 (i + j)번째와 word의 j번째 글자가 일치하는지 확인
                    // 그렇지 않다면 종료하고 다음 단어로 넘어간다.
                    if (chars[i + j] != word.charAt(j))
                        break;

                    // 일치할 경우,
                    // dp[i + j + 1]에 해당 경우의 수만큼을 더해준다.
                    dp[i + j + 1] += dp[i];
                    dp[i + j + 1] %= LIMIT;
                }
            }
        }

        // 최종 5개의 글자가 모두 일치하는 경우는
        // dp[5]에 계산되어있으니 해당 값 출력.
        System.out.println(dp[5]);
    }
}