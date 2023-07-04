/*
 Author : Ruel
 Problem : Baekjoon 12786번 INHA SUIT
 Problem address : https://www.acmicpc.net/problem/12786
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12786_INHASUIT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 나무와 각 나무에는 구멍이 뚫려있다.
        // 현재 주인공은 위치 0에 높이 1에 서있다.
        // 오른쪽으로 강풍이 불어, 오른쪽으로 이동하며, 각 나무를 통과하기 전에는
        // 한 번만 슈트의 기동이 가능하다.
        // 슈트의 기동은
        // O -> 높이 유지
        // A -> 위로 1 이동
        // B -> 현재 높이만큼 위로 이동(현재 높이가 10 이상이라면 항상 20으로 이동)
        // C -> 아래로 1 이동
        // T -> 위 아래 어느 위치든 순간이동
        // T 기동은 무리가 가기 때문에 k번 제한이 있으며 최소한으로 사용하고자 한다.
        // 마지막 나무까지 통과하는데 필요한 최소한의 T 기동의 수
        // 혹은 불가능하다면 -1을 출력한다.
        //
        // DP 문제
        // 현재 위치에서 이동할 수 있는 다음 위치를 계산하고
        // 다음 위치에서는 구멍에 도달한 경우만 살펴보며 이동시킨다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 나무와 t 기동의 제한 횟수 k
        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        
        // 각 나무에 뚫린 구멍
        int[][] holes = new int[n + 1][];
        // 0번 위치에는 높이 1에서 시작
        holes[0] = new int[]{1};
        for (int i = 1; i < holes.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            holes[i] = new int[Integer.parseInt(st.nextToken())];
            for (int j = 0; j < holes[i].length; j++)
                holes[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 각 위치에 도달하는데 필요한 최소한의 T 기동 횟수
        int[][] dp = new int[n + 1][21];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 마찬가지로 0, 높이 1에서는 0회
        dp[0][1] = 0;
        // 순차적으로 0번 위치부터 n번 나무까지 모두 살펴본다.
        for (int i = 0; i < dp.length - 1; i++) {
            // 살펴볼 때는 현재 나무의 구멍 위치에서만 계산한다.
            for (int current : holes[i]) {
                // 만약 현재 구멍에 도달하는 경우가 없다면 건너뛴다.
                if (dp[i][current] == Integer.MAX_VALUE)
                    continue;
                
                // O 기동
                dp[i + 1][current] = Math.min(dp[i + 1][current], dp[i][current]);
                
                // A 기동
                if (current + 1 < dp[i + 1].length)
                    dp[i + 1][current + 1] = Math.min(dp[i + 1][current + 1], dp[i][current]);
                
                // B 기동
                dp[i + 1][Math.min(current * 2, 20)] = Math.min(dp[i + 1][Math.min(current * 2, 20)], dp[i][current]);
                
                // C 기동
                if (current - 1 > 0)
                    dp[i + 1][current - 1] = Math.min(dp[i + 1][current - 1], dp[i][current]);
                
                // T 기동
                for (int next : holes[i + 1])
                    dp[i + 1][next] = Math.min(dp[i + 1][next], dp[i][current] + 1);
            }
        }

        // 마지막 나무의 구멍 위치들에 도달한 최소 T기동 횟수들을 살펴보고
        // 최소값을 찾는다.
        int min = Integer.MAX_VALUE;
        for (int hole : holes[n])
            min = Math.min(min, dp[n][hole]);

        // 그 값이 k보다 같거나 작다면 가능한 경우이므로 min 출력
        // 그렇지 않다면 불가능한 경우이므로 -1을 출력한다.
        System.out.println(min <= k ? min : -1);
    }
}