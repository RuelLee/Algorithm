/*
 Author : Ruel
 Problem : Jungol 10391번 달과 우산
 Problem address : https://jungol.co.kr/problem/10391
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_10391_달과우산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        // C 혹은 J로 이루어진 단어가 주어진다.
        // CJ가 등장할 때마다 X, JC가 등장할 때마다 Y원의 비용이 추가된다.
        // 중간중간 ?로 비어있는 공간이 있고, 임의의 문자로 채울 수 있다.
        // 완성된 단어의 최소 비용은?
        //
        // DP 문제
        // dp[i][0] = i번째에 C일 때의 최소 비용
        // dp[i][1] = i번째에 J일 때의 최소 비용

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // CJ일 때의 비용 X
            int x = Integer.parseInt(st.nextToken());
            // JC일 때의 비용 Y
            int y = Integer.parseInt(st.nextToken());
            // 주어지는 단어
            char[] s = st.nextToken().toCharArray();

            // dp[i][0] = i번째에 C일 때의 최소 비용
            // dp[i][1] = i번째에 J일 때의 최소 비용
            long[][] dp = new long[s.length][2];
            // 초기 값 설정
            for (long[] d : dp)
                Arrays.fill(d, Integer.MAX_VALUE);
            if (s[0] == 'C')
                dp[0][0] = 0;
            else if (s[0] == 'J')
                dp[0][1] = 0;
            else
                dp[0][0] = dp[0][1] = 0;

            // dp를 채워나간다.
            for (int i = 1; i < s.length; i++) {
                // i번째 글자가 C인 경우
                if (s[i] == 'C')
                    dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1] + y);
                else if (s[i] == 'J')       // J인 경우
                    dp[i][1] = Math.min(dp[i - 1][0] + x, dp[i - 1][1]);
                else {      // ?인 경우
                    dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1] + y);
                    dp[i][1] = Math.min(dp[i - 1][0] + x, dp[i - 1][1]);
                }
            }
            // 마지막 글자까지 모두 처리했을 때의 최소 비용 기록
            sb.append("Case #").append(testCase + 1).append(": ").append(Math.min(dp[dp.length - 1][0], dp[dp.length - 1][1])).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}