/*
 Author : Ruel
 Problem : Baekjoon 11964번 Fruit Feast
 Problem address : https://www.acmicpc.net/problem/11964
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11964_FruitFeast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 주인공은 최대 t의 만족감을 갖고 있다.
        // 앞에는 무한한 오렌지와 레몬이 있다.
        // 오렌지는 a만큼의 만족도를 레몬은 b만큼의 만족도를 높인다.
        // 도중 한번 물을 마실 수 있고, 물을 마실 경우, 만족도가 반으로 줄어든다.
        // 최대로 만들 수 있는 만족도 값은?
        //
        // BFS 문제
        // 오렌지를 먹는 경우, 레몬을 먹는 경우, 물을 마시는 경우
        // 나눠 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 최대 만족도 t
        int t = Integer.parseInt(st.nextToken());
        // 오렌지를 먹을 때는 a, 레몬을 먹을 때는 b만큼의 만족도가 증가한다.
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 각 상태에 도달할 수 있는가
        // dp[물을 마셨는가][만족도] = 도달 여부
        boolean[][] dp = new boolean[2][t + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 물을 마셨는가
            int water = current / (t + 1);
            // 만족도
            int full = current % (t + 1);
            if (dp[water][full])
                continue;
            
            // 오렌지를 먹는 경우
            if (full + a < dp[water].length && !dp[water][full + a])
                queue.offer(water * (t + 1) + full + a);
            
            // 레몬을 먹는 경우
            if (full + b < dp[water].length && !dp[water][full + b])
                queue.offer(water * (t + 1) + full + b);

            // current가 물을 안 마셨다면, 물을 마시는 경우도 고려 가능.
            if (water == 0 && !dp[1][full / 2])
                queue.offer(1 * (t + 1) + full / 2);
            
            // 방문 체크
            dp[water][full] = true;
        }

        // 물을 마셨던 마시지 않았던
        // 만들 수 있는 최대 포만도 값을 찾는다.
        int answer = 0;
        for (int i = t; i >= 0; i--) {
            if (dp[0][i] || dp[1][i]) {
                answer = i;
                break;
            }
        }
        // 답안 출력
        System.out.println(answer);
    }
}