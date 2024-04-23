/*
 Author : Ruel
 Problem : Baekjoon 12888번 포화 이진 트리 도로 네트워크
 Problem address : https://www.acmicpc.net/problem/12888
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12888_포화이진트리도로네트워크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 높이 n의 포화 이진 트리가 주어질 때
        // 한 도시에서 다른 도시로 차를 보내는데,
        // 각 도시에 방문한 차가 1대만 되도록 하고자 한다.
        // 총 보내야하는 차는 최소 몇 대인가?
        //
        // DP 문제
        // 조금만 그림을 그려 직접 해보면
        // 차를 최소로 보내는 방법은 외곽을 따라 먼저 한대를 보내고
        // 나머지 장소들을 처리하는 방법이다.
        // 예를 들어 높이가 2인 포화 이진 트리일 경우
        // 외곽 둘러 차를 보내면 내부에 높이 0인 포화 이진 트리 2개가 남는다.
        // 높이가 3인 경우에는
        // 0인 포화 이진 트리 2개와 1인 포화 이진 트리 2개가 남는다.
        // 이런식으로
        // 높이 n인 경우
        // 0 ~ n - 2인 이진 트리가 2개씩 남는다.
        // 따라서 점화식을 세우고 DP를 통해 문제를 풀어주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 높이 n
        int n = Integer.parseInt(br.readLine());

        long[] dp = new long[Math.max(2, n + 1)];
        // 높이가 0, 1인 경우는 1대
        dp[0] = dp[1] = 1;
        // dp[0] + ... + dp[i-2]까지를 sum으로 나타낸다.
        long sum = 0;
        for (int i = 2; i < dp.length; i++) {
            // sum에 (i-2)번째 포화이진트리 값을 더해준다.
            sum += dp[i - 2];
            // i번째 트리는
            // 0 ~ (i-2) 높이 트리의 대수 * 2 + 외곽을 지나는 1대
            dp[i] = sum * 2 + 1;
        }

        // 높이가 n인 경우의 답을 출력한다.
        System.out.println(dp[n]);
    }
}