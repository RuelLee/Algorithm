/*
 Author : Ruel
 Problem : Baekjoon 21738번 얼음깨기 펭귄
 Problem address : https://www.acmicpc.net/problem/21738
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21738_얼음깨기펭귄;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class State {
    int idx;
    int depth;

    public State(int idx, int depth) {
        this.idx = idx;
        this.depth = depth;
    }
}

public class Main {
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 얼음블록과 지지대 역할을 할 수 있는 1 ~ s까지의 얼음 블록, p 펭귄이 위치한 블록
        // 그리고 각 얼음블록의 연결 상태가 주어진다.
        // 지지대는 단독으로 떠있을 수 있으며, 그 외 다른 블록은 다른 블록과 최소 1개의 연결이 있어야한다.
        // 펭귄이 서있는 블록은 최소 2개 이상의 연결이 있어야한다.
        // 펭귄을 빠뜨리지 않고 최대한 많은 블록을 깨고자할 때
        // 총 몇 개의 블록을 깰 수 있는가?
        //
        // BFS 문제
        // 펭귄이 위치한 블록부터, 지지대 얼음 블록까지의 거리를 구한다.
        // 거리가 가장 적은 지지대 블록을 2개 구해, 해당 블록들까지의 거리와
        // 펭귄이 서있는 블록만 남겨두면 되므로
        // n - (d1 + d2 + 1)을 해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 얼음 블록, s개의 지지대 블록, 펭귄이 위치한 p
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        
        // 각 블록들 간의 연결
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            connections.get(b).add(a);
        }
        
        // BFS
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.depth));
        priorityQueue.offer(new State(p, 0));
        // 펭귄이 위치한 지점부터의 거리
        int[] distances = new int[n + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[p] = 0;
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            // 만약 지지대 블록이거나, 이전에 더 짧은 거리로 도달을 했다면 건너뛴다.
            if (current.idx <= s || distances[current.idx] < current.depth)
                continue;

            // 연결된 다음 블록을 살펴본다.
            for (int next : connections.get(current.idx)) {
                // current에서 next로 가는 것이 이전에 계산한 경우보다 더 짧은 경우라면
                if (distances[next] > distances[current.idx] + 1) {
                    // 값 갱신 후, 큐에 추가
                    distances[next] = distances[current.idx] + 1;
                    priorityQueue.offer(new State(next, distances[next]));
                }
            }
        }

        // 지지대 블록들 중 가장 거리가 짧은 두 블록을 찾는다.
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;
        for (int i = 1; i <= s; i++) {
            if (distances[i] < first) {
                second = first;
                first = distances[i];
            } else if (distances[i] < second)
                second = distances[i];
        }

        // 두 블록까지의 거리합과, 펭귄이 서있는 블록을 제외한
        // 남은 모든 블록을 깰 수 있다.
        System.out.println(n - (first + second + 1));
    }
}