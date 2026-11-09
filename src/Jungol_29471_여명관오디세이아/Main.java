/*
 Author : Ruel
 Problem : Jungol 29471번 여명관 오디세이아
 Problem address : https://jungol.co.kr/problem/29471
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_29471_여명관오디세이아;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n개의 방이 주어진다.
        // 각 방에서 다른 방으로 이동할 때의 거리가 주어진다. 이동할 수 없는 경우 -1로 주어진다.
        // 각 방의 위험도가 주어진다.
        // 첫 이동에는 원래 이동 거리로 이동하고
        // 두번째 이동부터는 현재 방이 아닌 이전에 있던 방의 위험도 * 현재 방 -> 다음 방의 거리로 이동거리가 측정된다.
        // 1 -> n으로 가는 최소 거리는?
        //
        // 최단 경로 문제
        // distanceSums[이전 방의 번호][현재 방의 번호] = 최소 누적 거리로 정하여
        // 현재 방에 도달할 경우, 직전에 어느 방에 들렀는지를 구분하여 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 방
        int n = Integer.parseInt(br.readLine());

        // 한 방에서 다른 방으로 이르는 거리
        int[][] distances = new int[n + 1][n + 1];
        StringTokenizer st;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++)
                distances[i][j] = Integer.parseInt(st.nextToken());
        }

        // 각 방의 위험도
        long[] risks = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            risks[i] = Integer.parseInt(st.nextToken());

        // distanceSums[이전 방의 번호][현재 방의 번호] = 최소 누적 거리
        long[][] distanceSums = new long[n + 1][n + 1];
        // 값 초기화
        for (long[] ds : distanceSums)
            Arrays.fill(ds, Long.MAX_VALUE);
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o[2]));
        // 첫번째 방에서 다른 방으로 이동하는 경우
        for (int i = 2; i <= n; i++) {
            if (distances[1][i] != -1) {
                distanceSums[1][i] = distances[1][i];
                pq.offer(new long[]{1, i, distances[1][i]});
            }
        }

        while (!pq.isEmpty()) {
            long[] state = pq.poll();
            // 직전 방, 현재 방
            int pre = (int) state[0];
            int cur = (int) state[1];
            // 더 적은 누적 거리로 해당 상태를 계산한 적이 있다면 건너뜀.
            if (state[2] > distanceSums[pre][cur])
                continue;

            // 다음 방의 위치
            for (int next = 1; next <= n; next++) {
                // 이동 가능하며, 최소 누적 거리를 갱신하는 경우
                if (distances[cur][next] != -1 &&
                        distanceSums[cur][next] > distanceSums[pre][cur] + risks[pre] * distances[cur][next]) {
                    distanceSums[cur][next] = distanceSums[pre][cur] + risks[pre] * distances[cur][next];
                    pq.offer(new long[]{cur, next, distanceSums[cur][next]});
                }
            }
        }

        // 직전에 어느 방을 거쳤건, n번째 방에 도달한 경우들을 살펴보며 최소 누적 거리를 찾음
        long ans = Long.MAX_VALUE;
        for (int i = 1; i <= n; i++)
            ans = Math.min(ans, distanceSums[i][n]);
        // 불가능한 경우는 -1, 그 외의 경우 거리를 출력
        System.out.println(ans == Long.MAX_VALUE ? -1 : ans);
    }
}