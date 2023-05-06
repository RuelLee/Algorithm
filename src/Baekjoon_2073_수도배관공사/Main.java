/*
 Author : Ruel
 Problem : Baekjoon 2073번 수도배관공사
 Problem address : https://www.acmicpc.net/problem/2073
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2073_수도배관공사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // D만큼 떨어진 곳에서 물을 끌어오려 한다.
        // 파이프를 이어 정확히 D 길이를 만들며, 이 때의 용량은 연이은 파이프들 중 최소 용량이
        // 연이은 파이프의 용량이 된다고 한다.
        // p개 파이프의 길이 L, 용량 C가 주어질 때
        // 최대 용량으로 파이프를 잇고자 할 때 그 용량은?
        //
        // 배낭 문제
        // 모든 파이프들을 하나씩 살펴보며
        // 만들 수 있는 길이에 대해 최대 용량을 하나씩 따져가며
        // D길이를 만들 때, 최대 용량을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 수원지까지의 거리 d
        int d = Integer.parseInt(st.nextToken());
        // 파이프들의 종류
        int p = Integer.parseInt(st.nextToken());
        
        // 파이프들의 정보
        int[][] pipes = new int[p][];
        for (int i = 0; i < pipes.length; i++)
            pipes[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp[n]
        // n길이를 만들 때, 최대 용량
        int[] dp = new int[d + 1];
        // 수원지에서의 용량은 무한대.
        dp[0] = Integer.MAX_VALUE;

        // 모든 파이프들을 살펴본다.
        for (int[] pipe : pipes) {
            // 이번 파이프가 중복되어 계산되는 것을 막기 위해
            // 내림차순으로 살펴본다.
            // 현재 기록된 길이 i일 때 최대 용량과
            // i - (이번 파이프의 길이)의 길이에 이번 파이프를 연이었을 때, 만들어지는 최대 용량을 비교해
            // 더 큰 값을 기록해둔다.
            for (int i = dp.length - 1; i - pipe[0] >= 0; i--)
                dp[i] = Math.max(dp[i], Math.min(dp[i - pipe[0]], pipe[1]));
        }

        // 길이 D를 만들 때의 최대 용량을 출력한다.
        System.out.println(dp[d]);
    }
}