/*
 Author : Ruel
 Problem : Baekjoon 2320번 끝말잇기
 Problem address : https://www.acmicpc.net/problem/2320
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2320_끝말잇기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[] alphabets = new char[]{'A', 'E', 'I', 'O', 'U'};
    static String[] words;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // n개의 A, E, I, O, U로 이루어진 단어들이 주어진다
        // 앞 단어의 마지막 글자와 뒷 단어의 첫 글자가 같은 단어들을 이어 최대한 긴 길이의 단어를 만들고 싶다
        // 만들 수 있는 가장 긴 단어의 길이는?
        //
        // n이 16으로 작게 주어진다. -> 비트마스킹과 메모이제이션을 활용한다.
        // DP를 행과 열로 나누어, 행에는 비트마스크, 열에는 시작하는 글자로 설정하자.
        // 그리고 bottom-up 방식으로 값을 채우고, 이미 계산된 결과는 참고하여 연산을 줄인다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        words = new String[n];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();

        // 행은 비트마스크, 열은 앞 단어의 끝난 글자(= 뒷 단어의 시작 글자)
        dp = new int[1 << n][alphabets.length];
        int answer = 0;
        // 5개의 시작 글자에 대해 모두 시작해보고, 최대 단어의 길이를 answer에 저장한다.
        for (char alphabet : alphabets)
            answer = Math.max(answer, findAnswer(alphabet, 0));
        System.out.println(answer);
    }

    static int findAnswer(char startWith, int bitmask) {
        // 알파벳에 해당하는 int 값을 가져온다.
        int charIdx = alphaToInt(startWith);
        // 만약 계산된 결과가 없다면
        if (dp[bitmask][charIdx] == 0) {
            // 단어를 하나씩 비교해나가면서
            for (int i = 0; i < words.length; i++) {
                // 아직 사용되지 않았고, 시작 글자가 일치한다면
                // 해당 단어로 시작하는 최대 단어의 길이를 찾고 그 길이를 비교한다.
                if ((bitmask & (1 << i)) == 0 && words[i].charAt(0) == startWith)
                    dp[bitmask][charIdx] = Math.max(dp[bitmask][charIdx],
                            findAnswer(words[i].charAt(words[i].length() - 1), bitmask | (1 << i)) + words[i].length());
            }
        }
        // dp[bitmask][charIdx] 에는, 현재 상태에 만들 수 있는 가장 긴 단어의 길이가 저장되어있다.
        // 해당 값 리턴.
        return dp[bitmask][charIdx];
    }

    // 5개의 알파벳과 int 값을 매칭시켜준다.
    static int alphaToInt(char c) {
        for (int i = 0; i < alphabets.length; i++)
            if (alphabets[i] == c)
                return i;
        return -1;
    }
}