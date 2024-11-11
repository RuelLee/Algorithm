/*
 Author : Ruel
 Problem : Baekjoon 2922번 즐거운 단어
 Problem address : https://www.acmicpc.net/problem/2922
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2922_즐거운단어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] vowels = {'A', 'E', 'I', 'O', 'U'};

    public static void main(String[] args) throws IOException {
        // 단어를 만들고자 한다.
        // 기본틀이 주어진다.
        // 기본틀에는 알파벳 혹은 _가 들어있다.
        // _에는 임의의 알파벳을 적을 수 있다.
        // 단 단어는 3번 연속하여 모음이 나오거나, 3번 연속하여 자음이 나와서는 안된다.
        // 또한 L이 반드시 포함되어야한다.
        // 만들 수 있는 단어의 가짓수를 구하라
        //
        // DP 문제
        // dp[문자길이][L포함여부][연속한자음의수][연속한모음의수] = 가짓수
        // 로 정하고 DP를 채우면 되는 문제.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        
        // dp[문자길이][L포함여부][연속한자음의수][연속한모음의수] = 가짓수
        long[][][][] dp = new long[input.length()][2][3][3];
        if (input.charAt(0) == '_') {
            // 첫 글자가 _인 경우
            // 모음을 넣는 경우 5가지
            dp[0][0][1][0] = 5;
            // 자음을 넣는 경우 20가지
            dp[0][0][0][1] = 20;
            // L을 넣는 경우 한가지
            dp[0][1][0][1] = 1;
        } else if (isVowel(input.charAt(0)))        // 모음인 경우
            dp[0][0][1][0] = 1;
        else if (input.charAt(0) == 'L')        // L인 경우
            dp[0][1][0][1] = 1;
        else        // 자음인 경우
            dp[0][0][0][1] = 1;

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    for (int l = 0; l < dp[i][j][k].length; l++) {
                        // 경우의 수가 없다면 건너뜀.
                        if (dp[i][j][k][l] == 0)
                            continue;
                        
                        // 다음 글자가 _라면
                        if (input.charAt(i + 1) == '_') {
                            // k가 2 미만인 경우, 모음을 넣을 수 있다.
                            if (k < 2)
                                dp[i + 1][j][k + 1][0] += dp[i][j][k][l] * 5;
                            // l이 2미만인 경우, 자음을 넣을 수 있다.
                            if (l < 2) {
                                // l을 넣는 경우
                                dp[i + 1][1][0][l + 1] += dp[i][j][k][l];
                                // 그 외의 자음을 넣는 경우
                                dp[i + 1][j][0][l + 1] += dp[i][j][k][l] * 20;
                            }
                        } else if (isVowel(input.charAt(i + 1))) {      // 다음 글자가 모음인 경우
                            // 연속한 모음이 2 미만일 때만 가능.
                            if (k < 2)
                                dp[i + 1][j][k + 1][0] += dp[i][j][k][l];
                        } else {        // 다음 글자가 자음인 경우
                            if (l < 2)      // 연속한 자음이 2미만인 경우에만 가능. 다음 글자가 L인지 여부 확인 필요.
                                dp[i + 1][input.charAt(i + 1) == 'L' ? 1 : j][0][l + 1] += dp[i][j][k][l];
                        }
                    }
                }
            }
        }

        // 마지막까지 완성된 경우의 수들 중
        // L이 포함된 경우들을 모두 센다.
        long sum = 0;
        for (int i = 0; i < dp[dp.length - 1][1].length; i++) {
            for (int j = 0; j < dp[dp.length - 1][1][i].length; j++)
                sum += dp[dp.length - 1][1][i][j];
        }
        // 답 출력
        System.out.println(sum);
    }

    // 모음 판별
    static boolean isVowel(char c) {
        for (int i = 0; i < vowels.length; i++)
            if (c == vowels[i]) return true;
        return false;
    }
}