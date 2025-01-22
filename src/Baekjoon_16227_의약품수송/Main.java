/*
 Author : Ruel
 Problem : Baekjoon 16227번 의약품 수송
 Problem address : https://www.acmicpc.net/problem/16227
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16227_의약품수송;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int time;

    public Road(int end, int time) {
        this.end = end;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 사막에 두 개척지가 있고, 그 사이에 있는
        // n개의 특수 장비가 놓여있는 장소, 도로의 개수 k가 주어진다.
        // 도로는 특수 장비가 놓여있는 두 장소를 연결하며, 그 때 걸리는 시간이 주어진다.
        // 사막에서 차를 달리는데, 최대 100분간 달릴 수 있고, 달리는 동안 모래가 쌓여
        // 특수 장비가 있는 곳에서 5분 동안 모래를 씻어내야 다시 달릴 수 있다.
        // 0번 개척지에서 n + 1번 개척지로 가는데 걸리는 최소 시간은?
        //
        // BFS, 다익스트라 문제
        // 특수 장비가 놓여있는 장소 내지 개척지에 도달할 때
        // 남은 잔여 주행 시간을 구분하여 도착 시간을 계산해나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 특수 장비가 놓여있는 장소, 도로의 개수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 도로 입력 정보
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 2; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            roads.get(u).add(new Road(v, t));
            roads.get(v).add(new Road(u, t));
        }
        
        // 각 장소에 잔여 주행 시간 별로 이르는 시간
        int[][] minTimes = new int[n + 2][101];
        for (int[] mt : minTimes)
            Arrays.fill(mt, Integer.MAX_VALUE);
        // 처음에는 0번 위치, 잔여 주행 시간 100
        minTimes[0][100] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0 * 101 + 100);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 위치
            int loc = current / 101;
            // 현재 남은 잔여 주행 시간
            int remainTime = current % 101;
            // 목적지에 도달했다면 건너뜀.
            if (loc == n + 1)
                continue;

            // loc에서 갈 수 있는 다른 장소들을 살펴본다.
            for (Road next : roads.get(loc)) {
                // 모래를 씻지 않고, 다음 장소로 갈 수 있으며
                // 그 때의 도착 시간이 최소 시간을 갱신하는 경우
                if (next.time <= remainTime && minTimes[next.end][remainTime - next.time] > minTimes[loc][remainTime] + next.time) {
                    minTimes[next.end][remainTime - next.time] = minTimes[loc][remainTime] + next.time;
                    queue.offer(next.end * 101 + remainTime - next.time);
                }

                // 모래를 씻고 나서, 다음 장소에 갈 수 있으며
                // 그 때의 도착 시간이 최소 시간을 갱신하는 경우.
                if (next.time <= 100 && minTimes[next.end][100 - next.time] > minTimes[loc][remainTime] + 5 + next.time) {
                    minTimes[next.end][100 - next.time] = minTimes[loc][remainTime] + 5 + next.time;
                    queue.offer(next.end * 101 + 100 - next.time);
                }
            }
        }

        // 잔여 주행 시간에 상관없이
        // n + 1 지점에 도달한 최소 시간을 구한다.
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < minTimes[n + 1].length; i++)
            answer = Math.min(answer, minTimes[n + 1][i]);
        // 답 출력
        System.out.println(answer);
    }
}
