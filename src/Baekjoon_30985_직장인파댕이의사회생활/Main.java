/*
 Author : Ruel
 Problem : Baekjoon 30985번 직장인 파댕이의 사회생활
 Problem address : https://www.acmicpc.net/problem/30985
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30985_직장인파댕이의사회생활;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Corridor {
    int end;
    int time;

    public Corridor(int end, int time) {
        this.end = end;
        this.time = time;
    }
}

class State {
    int elevator;
    int room;
    long time;

    public State(int elevator, int room, long time) {
        this.elevator = elevator;
        this.room = room;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 건물이 주어진다.
        // 한 층에는 n개의 방이 있고, 한 층의 방들을 연결하는 m개의 복도가 주어지며, 총 층은 k개이다.
        // 각 방에는 윗층으로 연결되는 엘리베이터가 있거나 없을 수 있다.
        // 엘리베이터가 한 층을 이동하는데 걸리는 시간은 각각 다르다.
        // 현재 1층 1번 방에서, k층 n번 방에 가고자할 때, 걸리는 최소시간은?
        //
        // 다익스트라 문제
        // n이 최대 10만, k개 최대 20만으로 주어지므로
        // 모든 층, 모든 방에 대해 계산을 하면 당연히 시간과 메모리 초과.
        // 어차피 윗층으로 이동하는 방법은 엘리베이터 뿐이므로
        // 한 번에 1층에서 k층으로 가장 운송 시간이 적은 엘리베이터를 통해 이동한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 방, m개의 복도, k개의 층
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 방을 연결하는 복도들의 정보
        List<List<Corridor>> corridors = new ArrayList<>();
        for (int i = 0; i < n; i++)
            corridors.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());

            corridors.get(u).add(new Corridor(v, c));
            corridors.get(v).add(new Corridor(u, c));
        }
        
        // 각 방에 연결된 엘리베이터
        int[] elevators = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        long[][] minTimes = new long[2][n];
        for (long[] mt : minTimes)
            Arrays.fill(mt, Long.MAX_VALUE);
        // 1층의 1번 방에서 출발
        minTimes[0][0] = 0;
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.time));
        priorityQueue.offer(new State(0, 0, 0));
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            // 더 적은 시간으로 해당 층의 방에 도착한 적이 있다면 해당 current는 건너뛴다.
            if (minTimes[current.elevator][current.room] > current.time)
                continue;

            // 현재 방에서 엘리베이터를 이동하는 경우.
            if (current.elevator == 0 && elevators[current.room] != -1 &&
                    minTimes[1][current.room] > minTimes[0][current.room] + (long) elevators[current.room] * (k - 1)) {
                minTimes[1][current.room] = minTimes[0][current.room] + (long) elevators[current.room] * (k - 1);
                priorityQueue.offer(new State(1, current.room, minTimes[1][current.room]));
            }

            // 현재 방에서 복도를 이용하여 다른 방으로 이동하는 경우.
            for (Corridor next : corridors.get(current.room)) {
                if (minTimes[current.elevator][next.end] > minTimes[current.elevator][current.room] + next.time) {
                    minTimes[current.elevator][next.end] = minTimes[current.elevator][current.room] + next.time;
                    priorityQueue.offer(new State(current.elevator, next.end, minTimes[current.elevator][next.end]));
                }
            }
        }
        
        // k층 n번 방에 도달하는 시간이 초기값이 아니라면
        // 가능한 경우이므로 해당 값 출력
        // 초기값이라면 불가능한 경우이므로 -1 출력
        System.out.println(minTimes[1][n - 1] == Long.MAX_VALUE ? -1 : minTimes[1][n - 1]);
    }
}