/*
 Author : Ruel
 Problem : Baekjoon 2253번 점프
 Problem address : https://www.acmicpc.net/problem/2253
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2253_점프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] delta = {-1, 0, 1};

    public static void main(String[] args) throws IOException {
        // n개의 돌이 같은 간격으로 놓여있다.
        // 1 ~ n번 돌이라 부른다.
        // 1. 이동은 앞으로만 할 수 있다.
        // 2. 제일 처음 점프를 할 때는 한 칸밖에 점프하지 못한다.
        // 그 이후로는 가속/감속 점프가 가능한데, 이전에 x칸을 뛰었다면
        // 다음에는 x-1, x, x+1 칸 점프가 가능하다.
        // 3. m개의 돌들은 너무 작아 밟을 수 없으며, 해당 돌을의 순서가 주어진다.
        // 1 -> n번째 돌까지 이동하는데 필요한 최소 점프 횟수는?
        //
        // DP 문제
        // DP를 통해 dp[현재위치][속도] = 최소 점프 횟수를 통해
        // '현재 위치'에 '속도'로 도달하는데 '최소 점프'가 필요함을 나타낸다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 목표 위치 n
        int n = Integer.parseInt(st.nextToken());
        // 밟지 못하는 m개의 돌
        int m = Integer.parseInt(st.nextToken());

        // 너무 작은 돌 표시
        boolean[] tooSmall = new boolean[n + 1];
        for (int i = 0; i < m; i++)
            tooSmall[Integer.parseInt(br.readLine())] = true;

        // dp[현재위치][속도] = 최소 점프 횟수
        int[][] dp = new int[n + 1][142];
        // 초기값 설정
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[1][0] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(142);
        while (!queue.isEmpty()) {
            // 현재 위치
            int loc = queue.peek() / 142;
            // 속도
            int speed = queue.poll() % 142;
            
            // 가능한 다음 속도
            for (int d = 0; d < delta.length; d++) {
                int nextSpeed = speed + delta[d];
                int nextLoc = loc + nextSpeed;
                // 다음 위치가 0보다 크고, 속도도 0보다 크며,
                // 다음 위치가 n을 벗어나지 않고, 작은 돌이 아니며
                // 최소 점프 횟수를 갱신하는 경우
                if (nextLoc > 0 && nextSpeed > 0 && nextLoc < dp.length &&
                        !tooSmall[nextLoc] && dp[nextLoc][nextSpeed] > dp[loc][speed] + 1) {
                    // 값 갱신 후 큐 추가
                    dp[nextLoc][nextSpeed] = dp[loc][speed] + 1;
                    queue.offer(nextLoc * 142 + nextSpeed);
                }
            }

        }

        // n에 도달하는 여러 속도들의 횟수들 중 가장 작은 값을 가져온다.
        int answer = Arrays.stream(dp[n]).min().getAsInt();
        // 그 값이 초기값이라면 불가능한 경우이므로 -1을 출력하고
        // 그렇지 않다면 해당하는 값을 출력한다.
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}