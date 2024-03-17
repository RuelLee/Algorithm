/*
 Author : Ruel
 Problem : Baekjoon 16500번 문자열 판별
 Problem address : https://www.acmicpc.net/problem/16500
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16500_문자열판별;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문자열 s와 단어 목록 A가 주어졌을 때
        // s를 A에 포함된 문자열을 한 개 이상 공백없이 붙여서 만들 수 있는지 판별하라
        //
        // DP 문제
        // dp[idx] = idx 이하의 문자열에 대해 A의 단어들을 이어붙여 만들 수 있는지에 대한 여부
        // 로 dp를 세우고 문제를 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 문자열 s
        String s = br.readLine();
        int n = Integer.parseInt(br.readLine());
        // 주어지는 단어목록
        String[] words = new String[n];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();
        
        // dp[idx] = idx까지 단어들을 이어붙여 만들 수 있는가
        boolean[] dp = new boolean[s.length() + 1];
        // 초기값 0은 true
        dp[0] = true;
        for (int i = 0; i < dp.length; i++) {
            // i까지 이어붙이는 것이 불가능하다면 여기서 더 이어붙이는 것도 불가능하므로
            // 건너뛴다.
            if (!dp[i])
                continue;

            // 가능하다면 단어들을 살펴본다.
            for (String word : words) {
                // i + word가 s의 길이를 넘거나,
                // 해당 위치에 이미 도달하는 것이 가능하다고 계산이 되었다면 건너뛴다.
                if (i + word.length() >= dp.length ||
                        dp[i + word.length()])
                    continue;

                // 그렇지 않다면, i ~ i + word.length() -1에 해당하는 구간에서
                // 모든 문자가 일치하는지 살펴본다.
                boolean same = true;
                for (int j = 0; j < word.length(); j++) {
                    if (s.charAt(i + j) != word.charAt(j)) {
                        same = false;
                        break;
                    }
                }
                // 가능하다면 i + word.length()에 true 표시를 하고
                // 다음 위치에서 다시 단어를 이어붙이는 작업을 한다.
                if (same)
                    dp[i + word.length()] = true;
            }
        }
        
        // 최종적으로 문자열 마지막 위치에 true가 표시되어있다면
        // 가능한 경우, 그렇지 않다면 불가능한 경우
        // 그에 대한 답안 출력
        System.out.println(dp[dp.length - 1] ? 1 : 0);
    }
}