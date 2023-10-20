/*
 Author : Ruel
 Problem : Baekjoon 1099번 알 수 없는 문장
 Problem address : https://www.acmicpc.net/problem/1099
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1099_알수없는문장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문장에 n개의 단어가 이어붙여있으며
        // 각 단어는 알파벳의 위치가 섞여있을 수 있다.
        // 예를 들어 abc라는 단어는 acb, bac 같이 섞여있으며 제 자리에 있지 않은 알파벳 개수만큼 비용이 소모된다.
        // 위의 경우는 2이고, bca는 3인 식이다.
        // 문장과 단어의 개수, 단어들이 주어질 때
        // 최소 비용으로 해당 문장을 해석하는 방법은?
        //
        // DP 문제
        // 먼저 단어들에 대해 알파벳 개수를 세어둔다.
        // 그 후, 문장에서 단어들과 비교해나가며, 해당 위치에 해당하는 단어와
        // 해당 단어를 그 위치에 적용했을 때의 비용을 계산해나간다.
        // 최종 위치까지 모두 계산이되었다면 그 값을, 그렇지 않다면 -1을 출력.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 문자
        String sentence = br.readLine();

        // 단어의 개수
        int n = Integer.parseInt(br.readLine());
        // 단어
        String[] words = new String[n];
        // 단어에 속한 알파벳 개수를 센다.
        int[][] alphabetCounts = new int[n][26];
        for (int i = 0; i < words.length; i++) {
            words[i] = br.readLine();
            for (int j = 0; j < words[i].length(); j++)
                alphabetCounts[i][words[i].charAt(j) - 'a']++;
        }

        // 해당 위치 전까지의 문장을 해석하는데 드는 비용을 계산한다.
        int[] dp = new int[sentence.length() + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 처음 위치는 0
        dp[0] = 0;
        for (int i = 0; i < sentence.length(); i++) {
            // 만약 초기값이라면 정확히 i에 도달하는 해석방법이 없다는 뜻.
            // 이후 i에 대해서는 없다는 뜻이 아니므로 continue
            if (dp[i] == Integer.MAX_VALUE)
                continue;

            // i위치부터 해당하는 단어가 있는지 확인
            for (int j = 0; j < words.length; j++) {
                // j번째 단어랑 비교할 때, 길이가 범위를 넘어가는지 확인.
                if (sentence.length() - i < words[j].length())
                    continue;

                // j번째 단어에 대해 미리 세어둔 알파벳 개수를 가져온다.
                int[] counts = alphabetCounts[j].clone();
                boolean found = true;
                for (int k = 0; k < words[j].length(); k++) {
                    // 문장의 i번째부터 단어와 알파벳을 비교하여
                    // 해당하지 않거나, 더 많은 알파벳이 등장한다면 중단.
                    if (--counts[sentence.charAt(i + k) - 'a'] < 0) {
                        found = false;
                        break;
                    }
                }

                // 알파벳의 종류와 그 수가 일치한다면
                if (found) {
                    // 단어와 알파벳의 정확한 위치를 비교하여 비용을 계산한 후
                    int cost = 0;
                    for (int k = 0; k < words[j].length(); k++) {
                        if (sentence.charAt(i + k) != words[j].charAt(k))
                            cost++;
                    }
                    // 문장에서 단어가 끝나는 위치의 dp에 최소값을 비교.
                    dp[i + words[j].length()] = Math.min(dp[i + words[j].length()], dp[i] + cost);
                }
            }
        }
        // 최종 위치에서의 값이 초기값이라면 -1
        // 그렇지 않다면 해당 값 출력
        System.out.println(dp[dp.length - 1] == Integer.MAX_VALUE ? -1 : dp[dp.length - 1]);
    }
}