/*
 Author : Ruel
 Problem : Baekjoon 10649번 프리스비
 Problem address : https://www.acmicpc.net/problem/10649
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10649_프리스비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[][] cows;

    public static void main(String[] args) throws IOException {
        // h 높이로 프리스비를 던진다.
        // n마리의 소들을 쌓아, h 높이 이상이 되면, 해당 프리스비를 잡을 수 있다.
        // 각 소의 키 무게 힘이 주어진다.
        // 소들은 자신의 힘이 위에 쌓인 소들의 무게보다 같거나 커야한다.
        // 안정도는 현재 상태에서 추가로 더 올릴 수 있는 무게를 뜻한다.
        // 프리스비를 잡을 수 있는 상태 중 안정도가 가장 높은 값은?
        //
        // 비스마스킹, dp, BFS 문제
        // 생각해보면 쌓는 소들을 정했다면, 높이는 같고, 어떻게 배치하느냐에 따라 안정도는 달라진다.
        // 따라서 dp[비트] = 최대 안정도로 구하며, 점차 쌓이는 소의 수가 늘어나므로 BFS 탐색을 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 수, 높이 h
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        // 소들의 정보
        cows = new int[n][3];
        // dp[비트] = 최대 안정도
        long[] dp = new long[1 << n];
        boolean[] enqueued = new boolean[1 << n];
        Queue<Integer> queue = new LinkedList<>();
        // 소들의 입력을 받으며, 해당 소를 가장 아래 둘 때의 상태으로 dp에도 값 추가
        // 탐색을 위해 큐에도 추가
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                cows[i][j] = Integer.parseInt(st.nextToken());
            int bit = 1 << i;
            dp[bit] = cows[i][2];
            queue.offer(bit);
            enqueued[bit] = true;
        }

        // 큐가 빌 때까지
        while (!queue.isEmpty()) {
            // 현재 상태
            int current = queue.poll();

            for (int i = 0; i < n; i++) {
                // i번 소가 새로운 소이며, current 상태 위에 더 얹을 수 있는 경우
                if ((current & (1 << i)) != 0 || dp[current] < cows[i][1])
                    continue;

                // 다음 비트
                int next = (current | (1 << i));
                // 안정도 최댓값 갱신
                dp[next] = Math.max(dp[next], Math.min(cows[i][2], dp[current] - cows[i][1]));
                // 큐에 없는 경우 추가
                if (!enqueued[next]) {
                    queue.offer(next);
                    enqueued[next] = true;
                }
            }
        }

        long answer = -1;
        // 기록된 상태들 중 키가 h보다 같거나 큰 것들 중, 최대 안정도를 계산
        for (int i = 1; i < enqueued.length; i++) {
            if (enqueued[i] && calcHeight(i) >= h)
                answer = Math.max(answer, dp[i]);
        }
        // answer가 초기값이라면, 그러한 경우가 없으므로 Mark is too tall 을 출력
        // 그 외의 경우 최대 안정도 출력
        System.out.println(answer == -1 ? "Mark is too tall" : answer);
    }

    // bit를 바탕으로 현재 높이 계산
    static long calcHeight(int bit) {
        long sum = 0;
        for (int i = 0; i < cows.length; i++) {
            if ((bit & (1 << i)) != 0)
                sum += cows[i][0];
        }
        return sum;
    }
}