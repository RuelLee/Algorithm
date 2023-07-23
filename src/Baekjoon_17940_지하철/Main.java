/*
 Author : Ruel
 Problem : Baekjoon 17940번 지하철
 Problem address : https://www.acmicpc.net/problem/17940
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17940_지하철;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class State {
    int station;
    int transfer;
    int time;

    public State(int station, int transfer, int time) {
        this.station = station;
        this.transfer = transfer;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지하철 역과 목적역 m이 주어진다.
        // 각 지하철 역은 각각의 회사가 운영하고 있으며
        // 서로 다른 회사가 운영하는 역으로 이동할 때 많은 환승 요금이 부과된다고 한다.
        // 각 역을 운영하는 회사와 역 간의 연결 정보와 이동 시간이 주어질 때
        // 최저 환승과 최저 시간으로 목적지에 도달하는 방법을 구하고
        // 그 때의 환승 횟수와 소요 시간을 출력한다.
        //
        // dijkstra 문제
        // 먼저 시간보다는 환승 횟수가 우선이다.
        // 따라서 먼저 환승 횟수가 더 적어지는지 확인하고
        // 환승 횟수가 같다면 더 적은 시간을 갖는 경우를 우선하는 방법으로
        // 다익스트라 알고리즘을 돌리면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 역과 목적역 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 역을 운영하는 회사
        int[] operation = new int[n];
        for (int i = 0; i < n; i++)
            operation[i] = Integer.parseInt(br.readLine());

        // 역 간의 연결 정보와 소요 시간.
        int[][] adjMatrix = new int[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 최소 환승 횟수
        int[] minTransfers = new int[n];
        Arrays.fill(minTransfers, Integer.MAX_VALUE);
        minTransfers[0] = 0;
        // 소요 시간.
        int[] minTimes = new int[n];
        Arrays.fill(minTimes, Integer.MAX_VALUE);
        minTimes[0] = 0;
        
        // 우선순위큐
        // 환승 횟수가 적은 것을 우선하고, 같다면 소요 시간이 더 적은 순
        PriorityQueue<State> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.transfer == o2.transfer)
                return Integer.compare(o1.time, o2.time);
            return Integer.compare(o1.transfer, o2.transfer);
        });
        priorityQueue.offer(new State(0, 0, 0));
        
        while (!priorityQueue.isEmpty()) {
            // 현재 상태
            State current = priorityQueue.poll();
            // 만약 기록된 환승 횟수가 더 적거나
            // 같은데 더 적은 시간인 경우가 있다면 current는 건너뛴다.
            if (minTransfers[current.station] < current.transfer ||
                    (minTransfers[current.station] == current.transfer && minTimes[current.station] < current.time))
                continue;

            // current.statin에서 갈 수 있는 다음 역을 살펴본다.
            for (int next = 0; next < adjMatrix[current.station].length; next++) {
                // 같거나 경로가 없는 경우는 건너뛴다.
                if (current.station == next || adjMatrix[current.station][next] == 0)
                    continue;
                
                // current.station -> next로 갈 때 최소 환승 횟수
                int currentTransfer = minTransfers[current.station] + (operation[current.station] + operation[next]) % 2;
                // 최소 환승횟수가 더 적거나
                // 같은데 소요 시간이 더 적다면
                if (minTransfers[next] > currentTransfer ||
                        (minTransfers[next] == currentTransfer && minTimes[next] > minTimes[current.station] + adjMatrix[current.station][next])) {
                    // 환승 횟수, 소요 시간을 갱신하고 우선순위큐에 추가한다.
                    minTransfers[next] = currentTransfer;
                    minTimes[next] = minTimes[current.station] + adjMatrix[current.station][next];
                    priorityQueue.offer(new State(next, minTransfers[next], minTimes[next]));
                }
            }
        }

        // 최종적으로 목적지에 도달하는 최소 환승 횟수와 최소 소요 시간을 출력한다.
        System.out.println(minTransfers[m] + " " + minTimes[m]);
    }
}