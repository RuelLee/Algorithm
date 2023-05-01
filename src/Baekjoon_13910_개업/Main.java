/*
 Author : Ruel
 Problem : Baekjoon 13910번 개업
 Problem address : https://www.acmicpc.net/problem/13910
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13910_개업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 요리사는 양손에 하나씩 웍을 쥐고서 요리를 할 수 있다. (= 최대 두 웍으로 요리하는 것이 가능하다)
        // n인분의 짜장면을 m 종류의 웍으로 요리하고자할 때, 최소 요리 횟수는?
        //
        // DP, 배낭 문제
        // 두 웍으로 한번에 요리하는 것이 가능하므로 한 번에 요리 가능한 그릇을 모두 계산한다.
        // 그리고 최소한의 요리 횟수로 n인분을 만드는 방법을 DP를 통해 해결한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 만들고자하는 짜장면의 개수
        int n = Integer.parseInt(st.nextToken());
        // 웍의 종류
        int m = Integer.parseInt(st.nextToken());
        
        // 웍들의 크기
        int[] woks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 한번에 요리할 수 있는 양
        HashSet<Integer> cookedOnetime = new HashSet<>();
        for (int i = 0; i < woks.length; i++) {
            cookedOnetime.add(woks[i]);
            for (int j = i + 1; j < woks.length; j++)
                cookedOnetime.add(woks[i] + woks[j]);
        }

        // n인분을 만든다.
        int[] dp = new int[n + 1];
        // 초기값은 큰 값
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 0인분을 만드는데는 횟수 소모가 없다.
        dp[0] = 0;
        // 한번에 만들 수 있는 양을 모두 살펴보며
        for (int onetime : cookedOnetime) {
            for (int i = 0; i + onetime < dp.length; i++) {
                // i인분을 만드는 방법이 없다면 건너뛴다.
                if (dp[i] == Integer.MAX_VALUE)
                    continue;

                // i 그릇을 만드는데 dp[i]회 요리를 하면 된다.
                // i + onetime 그릇을 만드는데는 dp[i] + 1 회 요리를 하면 가능하다.
                // 따라서 기존에 계산된 값과 비교하여 더 적은 값을 남겨둔다.
                dp[i + onetime] = Math.min(dp[i + onetime], dp[i] + 1);
            }
        }

        // n 그릇을 만드는데 필요한 요리 횟수가 초기값이라면 불가능한 경우이므로 -1을 출력
        // 그렇지 않다면 계산된 결과값을 출력한다.
        System.out.println(dp[n] == Integer.MAX_VALUE ? -1 : dp[n]);
    }
}