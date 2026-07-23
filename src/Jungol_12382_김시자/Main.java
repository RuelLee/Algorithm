/*
 Author : Ruel
 Problem : Jungol 12382번 감시자
 Problem address : https://jungol.co.kr/problem/12382
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12382_김시자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 방이 m개의 복도로 연결되어있다.
        // 각 방에는 감시병들이 있으며, 플레이어가 움적이기 전 0.5초부터 ai만큼 냉각을 거친 후, bi만큼 감시를 진행한다.
        // ai와 bi는 합이 최대 10으로 주어지고, 플레이어가 처음 있는 1번 방의 ai는 1보다 같거나 크다.
        // 복도를 통해 이동하는 경우, 1초 간 복도를 거쳐 도달한다.
        // 감시에 걸리지 않고 각 방에 도달하는 최소 시간을 출력하라
        //
        // 다익스트라 문제
        // 다익스트라를 통해 각 방에 도달하는 최소 시간을 구하되
        // 감시병들의 냉각과 감시 시간에 따라 진입할 수 있는 모든 경우를 고려해야한다.
        // 따라서 각 방에 도달하는 시간을 1 ~ 10까지의 최소공배수인 2520 가지로 나눠 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 방, m개의 복도
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 방들의 냉각, 감시 시간
        int[][] rooms = new int[n + 1][2];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            rooms[i][0] = Integer.parseInt(st.nextToken());
            rooms[i][1] = Integer.parseInt(st.nextToken());
        }

        // 복도 연결 정보
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            lists.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            lists.get(u).add(v);
            lists.get(v).add(u);
        }

        // 각 방에 도달하는 모든 경우에 따른 최소 시간
        int[][] minTimes = new int[n + 1][2520];
        for (int[] mt : minTimes)
            Arrays.fill(mt, Integer.MAX_VALUE);
        // 처음 시작
        minTimes[1][0] = 0;
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> minTimes[o[0]][o[1]]));
        priorityQueue.offer(new int[]{1, 0});
        while (!priorityQueue.isEmpty()) {
            // 현재 방의 위치와 도착 전체 주기
            int[] cur = priorityQueue.poll();
            int curTime = minTimes[cur[0]][cur[1]];
            // 현재 방의 냉각 감시 주기
            int cycle = rooms[cur[0]][0] + rooms[cur[0]][1];

            // 현재 방에서 머무는 경우
            for (int i = 1; (curTime + i) % cycle < rooms[cur[0]][0] && i < 10; i++) {
                if (minTimes[cur[0]][(curTime + i) % 2520] > curTime + i) {
                    minTimes[cur[0]][(curTime + i) % 2520] = curTime + i;
                    priorityQueue.offer(new int[]{cur[0], (curTime + i) % 2520});
                }
            }

            // 다음 방으로 이동하는 경우
            for (int next : lists.get(cur[0])) {
                int nextCycle = rooms[next][0] + rooms[next][1];
                if ((curTime + 1) % nextCycle < rooms[next][0] &&
                        minTimes[next][(curTime + 1) % 2520] > curTime + 1) {
                    minTimes[next][(curTime + 1) % 2520] = curTime + 1;
                    priorityQueue.offer(new int[]{next, (curTime + 1) % 2520});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // 첫번째 방에는 항상 0초에 존재한다.
        sb.append(0);
        // 각 방에 도달하는 모든 경우들 중 최소 시간을 기록한다.
        for (int i = 2; i <= n; i++) {
            int time = Integer.MAX_VALUE;
            for (int j = 0; j < minTimes[i].length; j++)
                time = Math.min(time, minTimes[i][j]);
            sb.append(" ").append(time == Integer.MAX_VALUE ? -1 : time);
        }
        // 답 출력
        System.out.println(sb);
    }
}