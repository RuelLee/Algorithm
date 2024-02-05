/*
 Author : Ruel
 Problem : Baekjoon 20542번 받아쓰기
 Problem address : https://www.acmicpc.net/problem/20542
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20542_받아쓰기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 영어 받아쓰기를 진행한다.
        // 제출한 답안과 정답과의 수정 횟수만큼이 정답이 된다.
        // 수정에는 추가, 삭제, 변환이 있다.
        // piza / pizzaa에선 z, a가 추가되어 2점, pineapple / apple 에선 p, i, n, e가 삭제되어 4점
        // 예외로 휘갈겨 쓴 i는 i, j, l와, v는 v, w와 서로 매칭된다.
        // james / iames, warren / varren일 경우 0점이 된다.
        //
        // DP 문제
        // dp[제출답안][정답] = 수정 횟수로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 제출 답안과 정답의 길이
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 제출 답안
        String written = br.readLine();
        // 정답
        String answer = br.readLine();
        
        // dp[제출답안][정답] = 수정 횟수
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 시작값 0
        dp[0][0] = 0;
        // 모든 i, j를 살펴보되
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length - 1; j++) {
                // 초기값이라면 도달하는 방법이 없는 경우이므로 건너뛴다.
                if (dp[i][j] == Integer.MAX_VALUE)
                    continue;

                // 제출 답안의 i번째, 정답의 j번째 문자가 일치하거나
                // i번째 문자가 i이고, j번째 문자가 j 혹은 l
                // i번째 문자가 v이고, j번째 문자가 w라면
                // 수정횟수를 추가하지 않고 다음 문자로 넘어간다.
                if (written.charAt(i) == answer.charAt(j) ||
                        (written.charAt(i) == 'i' && (answer.charAt(j) == 'j' || answer.charAt(j) == 'l')) ||
                        (written.charAt(i) == 'v' && answer.charAt(j) == 'w'))
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j]);
                else {
                    // 문자가 서로 다른 경우
                    // 제출 답안에 j번째와 같은 문자를 추가하는 경우
                    dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);
                    // 제출 답안에 i번째 문자를 삭제하는 경우
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);
                    // 제출 답안에서 i번째 문자를 j번째 문자로 수정하는 경우.
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j] + 1);
                }
            }
        }
        // 제출 답안은 문자가 남았지만, 정답에 문자가 남지 않은 경우
        // 제출 답안의 문자를 하나씩 삭제하며 수정 횟수를 1씩 늘리는 것이 가능.
        for (int i = 0; i < dp.length - 1; i++) {
            if (dp[i][m] != Integer.MAX_VALUE)
                dp[i + 1][m] = Math.min(dp[i + 1][m], dp[i][m] + 1);
        }
        // 정답의 문자는 남았지만, 제출 답안의 문자는 모두 살펴본 경우
        // 제출 답안에 문자를 추가하여 수정 횟수를 1씩 늘리는 것이 가능.
        for (int i = 0; i < dp[n].length - 1; i++) {
            if (dp[n][i] != Integer.MAX_VALUE)
                dp[n][i + 1] = Math.min(dp[n][i + 1], dp[n][i] + 1);
        }

        // 최소 수정 횟수 출력
        System.out.println(dp[n][m]);
    }
}