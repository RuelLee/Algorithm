/*
 Author : Ruel
 Problem : Baekjoon 1107번 리모컨
 Problem address : https://www.acmicpc.net/problem/1107
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1107_리모컨;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class State {
    int channel;
    int pressed;
    int identifier;

    public State(int channel, int pressed, int identifier) {
        this.channel = channel;
        this.pressed = pressed;
        this.identifier = identifier;
    }
}

public class Main {
    static int[] delta = {-1, 1};

    public static void main(String[] args) throws IOException {
        // 0 ~ 9까지의 숫자버튼과 +, - 버튼이 있는 리모콘이 주어진다.
        // 이 중 m 개의 숫자버튼이 고장났다.
        // 현재 100번 채널에서 n번 채널로 이동하고자할 때, 눌러야하는 버튼의 최소 수는?
        //
        // 완전 탐색 문제
        // 채널을 바꾸는 방법은 + , - 버튼을 사용하여 하나씩 이동하거나
        // 숫자버튼을 이용하여 채널을 바꾸는 방법이 있다.
        // 위 두 방법으로 각 채널에 도달하는 최소 버튼 수를 계산해나가자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 가고자 하는 채널
        int n = Integer.parseInt(br.readLine());

        // 고장난 버튼의 수
        int m = Integer.parseInt(br.readLine());
        StringTokenizer st = null;
        if (m != 0)
            st = new StringTokenizer(br.readLine());
        boolean[] brokenButtons = new boolean[10];
        for (int i = 0; i < m; i++)
            brokenButtons[Integer.parseInt(st.nextToken())] = true;

        // 각 채널에 도달하는 최소 버튼의 수
        // dp[n][0] = 숫자버튼을 통해 n번 채널에 도달할 때 최소 버튼 수
        // dp[n][1] = + , - 버튼을 통해 n번 채널에 도달한 최소 버튼 수
        // 숫자 버튼을 통해 이동한 경우를 분리한 이유는, 추가적인 숫자 버튼을 누름을 통해 다른 채널로 이동할 수 있기 때문.
        int[][] dp = new int[Math.max(101, n * 2 + 1)][2];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);

        // 처음 시작은 100번 채널.
        dp[100][1] = 0;
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.pressed));
        priorityQueue.offer(new State(100, 0, 1));
        // 고장나지 않은 숫자 버튼을 한 번 누름으로써 해당 채널로 이동이 가능하다.
        for (int i = 0; i < brokenButtons.length; i++) {
            if (!brokenButtons[i]) {
                dp[i][0] = 1;
                priorityQueue.offer(new State(i, 1, 0));
            }
        }

        // 큐가 빌 때까지
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            // 원하는 채널에 도달했다면 종료
            if (current.channel == n)
                break;
                // 이미 더 적은 버튼 수로 계산이 끝난 채널이라면 건너 뛴다.
            else if (dp[current.channel][current.identifier] < current.pressed)
                continue;

            // 숫자 버튼을 통해 current 채널로 온 경우
            // (숫자버튼을 추가로 누름으로써 다음 채널로 갈 수 있는 경우)
            if (current.identifier == 0) {
                // 현재 채널 * 10
                int next = current.channel * 10;
                for (int i = 0; i < brokenButtons.length; i++) {
                    // 고장나지 않은 버튼을 누른다.
                    // 해당 채널이 범위 안에 있고, 최소 누름 수를 갱신한다면
                    if (!brokenButtons[i] && checkChannel(next + i, dp) &&
                            dp[next + i][0] > current.pressed + 1) {
                        // dp값 갱신.
                        dp[next + i][0] = current.pressed + 1;
                        // 큐 추가
                        priorityQueue.offer(new State(next + i, dp[next + i][0], 0));
                    }
                }
            }

            // currnet 채널에 도착한 모든 경우
            // + , - 버튼을 통해 채널 이동이 가능하다.
            for (int d = 0; d < delta.length; d++) {
                int next = current.channel + delta[d];
                // 숫자 버튼으로 이동했던, + , - 버튼으로 이동했던 버튼 누름 수 중 더 적은 값
                int pressed = Math.min(dp[current.channel][0], dp[current.channel][1]);
                // 다음 채널이 있고, 최소 버튼 수를 갱신한다면
                if (checkChannel(next, dp) && dp[next][1] > pressed + 1) {
                    // 값 갱신
                    dp[next][1] = pressed + 1;
                    // 큐 추가
                    priorityQueue.offer(new State(next, dp[next][1], 1));
                }
            }
        }
        // n번 채널에 도달하는 최소 버튼 수 출력.
        System.out.println(Math.min(dp[n][0], dp[n][1]));
    }

    static boolean checkChannel(int n, int[][] dp) {
        return n >= 0 && n < dp.length;
    }
}