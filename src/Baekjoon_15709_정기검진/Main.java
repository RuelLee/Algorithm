/*
 Author : Ruel
 Problem : Baekjoon 15709번 정기검진
 Problem address : https://www.acmicpc.net/problem/15709
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15709_정기검진;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    long time;

    public Road(int end, long time) {
        this.end = end;
        this.time = time;
    }
}

public class Main {
    static List<List<Road>> roads = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // n개의 집, m개의 병원, b개의 다리, k개의 도로가 주어진다.
        // 집이 있는 구역과 병원이 있는 구역은 강으로 분리가 되어있으며, b의 다리를 건너가야한다.
        // 다리는 0초만에 건널 수 있다.
        // 도로는 집 혹은 병원 혹은 다리를 잇는 도로가 k개 존재하며, 다리와 다리를 잇는 도로도 존재한다.
        // 도로는 a b k 형태로 주어지고, a지점과 b지점 사이에 시간 k가 소요되는 도로가 존재한다는 의미다.
        // q개의 질문에 대해
        // s번 집에서 e번 병원까지 도달하는 최소 시간을 구하라
        // n과 m은 최대 10만, b는 최대 100, k는 최대 2 * 10^4, q는 최대 10만으로 주어진다.
        //
        // 다익스트라
        // 다익스트라 문제인데 조금 생각을 틀어야하는 문제
        // 보면, n과 m은 최대 10만개로 꽤나 수가 크다.
        // 따라서 집에서 병원으로 가는 경우를 모두 세면 시간 초과가 난다.
        // 다리의 개수가 100개로 상당히 적은데 이를 바탕으로
        // 다리에서 집, 다리에서 병원으로 가는 최소 시간을 구하고
        // 질문이 주어졌을 때, 모든 다리에 대해 거쳐가는 시간이 가장 적은 다리를 찾으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 집, m개의 병원, b개의 다리, k개의 도로의 수, q개의 질문
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 도로 입력 처리
        roads = new ArrayList<>();
        for (int i = 0; i < n + m + b + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            roads.get(x).add(new Road(y, z));
            roads.get(y).add(new Road(x, z));
        }

        // 각 다리를 기준으로 각 지점들에 이르는 최소 시간을 구한다.
        long[][] minTimes = new long[b][n + m + b + 1];
        for (int i = 0; i < minTimes.length; i++) {
            // i+1번째 다리에 대해 처리.
            Arrays.fill(minTimes[i], Long.MAX_VALUE);
            minTimes[i][n + m + 1 + i] = 0;
            // 방문 체크용 boolean 배열
            boolean[] visited = new boolean[n + m + b + 1];
            // 시작 지점은 i+1번 다리
            PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.time));
            priorityQueue.offer(new Road(n + m + 1 + i, 0));
            // Dijkstra
            while (!priorityQueue.isEmpty()) {
                Road current = priorityQueue.poll();
                if (visited[current.end])
                    continue;

                for (Road next : roads.get(current.end)) {
                    if (!visited[next.end] && minTimes[i][next.end] > minTimes[i][current.end] + next.time) {
                        minTimes[i][next.end] = minTimes[i][current.end] + next.time;
                        priorityQueue.offer(new Road(next.end, minTimes[i][next.end]));
                    }
                }
                visited[current.end] = true;
            }
        }
        
        // q개의 질문 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 시작 집 s, 도착 병원 e
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            long answer = Long.MAX_VALUE;
            // 각 다리를 거쳐 병원으로 가는 경우를 모두 비교
            for (int j = 0; j < minTimes.length; j++) {
                if (minTimes[j][s] != Long.MAX_VALUE && minTimes[j][e] != Long.MAX_VALUE)
                    answer = Math.min(answer, minTimes[j][s] + minTimes[j][e]);
            }
            // 다리를 통해 병원을 갈 수 있는 경우, 최대 시간
            // 불가능한 경우 -1을 기록
            sb.append(answer == Long.MAX_VALUE ? -1 : answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}