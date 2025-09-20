/*
 Author : Ruel
 Problem : Baekjoon 30869번 빨리 기다리기
 Problem address : https://www.acmicpc.net/problem/30869
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30869_빨리기다리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정류소, m개의 노선이 주어진다.
        // i번 노선은 si번 정류소에서 출발해 ei 정류장에 도착하며, 배차 간격은 gi로 0시에 첫출발한 뒤, 매 gi시간마다 운행을 다시 시작한다.
        // 빨리 기다리기를 사용할 경우, 해당 정류소에서 출발하는 하나의 버스를 바로 출발시킬 수 있다.
        // k번 이내로 빨리 기다리기를 사용하여, 1번 정류소에서 n번 정류소에 도달하는 최소 시간을 출력하라
        //
        // 최단 경로, dijkstra 문제
        // minTimes[정류소][빨리기다리기사용횟수] = 최소 도착 시간
        // 으로 정하고, 다익스트라를 활용하여 각 정류소에 빨리 기다리기를 사용한 횟수 별 최소 도착 시간을 계산해나간다.
        // 도착 후, 즉시 다른 버스를 타고 이동하는 것이 아닌, 버스 마다 배차 간격에 따른 출발 시간이 있음에 유의한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정류소, m개의 노선, 빨리 기다리기 최대 사용 횟수 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 노선들
        List<List<int[]>> routes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            routes.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            routes.get(Integer.parseInt(st.nextToken())).add(new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }

        // 각 정류소 별 최소 도착 시간
        int[][] minTimes = new int[n + 1][k + 1];
        for (int[] mt : minTimes)
            Arrays.fill(mt, Integer.MAX_VALUE);

        // 0 시각에 모든 버스가 동시에 출발하므로
        // 1 정류소에 있는 주인공 또한 바로 버스를 타고 이동이 가능.
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        for (int[] r : routes.get(1)) {
            minTimes[r[0]][0] = r[1];
            priorityQueue.offer(new int[]{r[0], 0, r[1]});
        }
        // dijkstra
        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            // 현재 값보다 더 이른 시각에 도달하는 것이 가능하다면 건너뛰기
            if (minTimes[current[0]][current[1]] < current[2])
                continue;

            // 현재 정류소에서 출발하는 노선들을 살펴본다.
            for (int[] next : routes.get(current[0])) {
                // 빨리 기다리기를 사용하지 않는 경우
                // 해당 버스의 다음 출발 시간
                int nextStartTime = (current[2] - 1) / next[2] * next[2] + next[2];
                // 다음 정류소에 도달하는 시각이 최소값을 갱신한다면
                if (minTimes[next[0]][current[1]] > nextStartTime + next[1]) {
                    // currnet[1] ~ k까지의 빨리 기다리기를 사용한 경우들 중
                    // 현재 도착 시각보다 더 늦은 값을 갖는 경우는 의미가 없다.
                    // 따라서 해당 값들로 채움
                    for (int i = current[1]; i < minTimes[next[0]].length; i++) {
                        if (minTimes[next[0]][i] < nextStartTime + next[1])
                            break;
                        minTimes[next[0]][i] = nextStartTime + next[1];
                    }
                    // 우선순위큐에 추가
                    priorityQueue.offer(new int[]{next[0], current[1], minTimes[next[0]][current[1]]});
                }

                // 빨리 기다리기를 사용하는 경우
                if (current[1] + 1 < minTimes[next[0]].length &&
                        minTimes[next[0]][current[1] + 1] > current[2] + next[1]) {
                    // 마찬가지로 current[1] + 1 ~ k 빨리 기다리기를 사용한 경우들 중
                    // 더 늦은 도착 시각을 갖는 경우는 의미가 없으므로
                    // current[2] + next[1]로 채운다.
                    for (int i = current[1] + 1; i < minTimes[next[0]].length; i++) {
                        if (minTimes[next[0]][i] < current[2] + next[1])
                            break;
                        minTimes[next[0]][i] = current[2] + next[1];
                    }
                    // 우선순위큐에도 추가
                    priorityQueue.offer(new int[]{next[0], current[1] + 1, minTimes[next[0]][current[1] + 1]});
                }
            }
        }

        // 몇 번 빨리 기다리기를 사용했건, 가장 빠른 시각을 찾아
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < minTimes[n].length; i++)
            min = Math.min(min, minTimes[n][i]);
        // 답 출력
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }
}