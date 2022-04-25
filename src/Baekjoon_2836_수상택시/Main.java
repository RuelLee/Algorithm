/*
 Author : Ruel
 Problem : Baekjoon 2836번 수상 택시
 Problem address : https://www.acmicpc.net/problem/2836
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2836_수상택시;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Person {
    int start;
    int end;

    public Person(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 0번부터 m번까지 집이 있고, 각 집들 사이의 거리는 1이다
        // 주인공은 0번부터 m번 집에 가는데, 중간에 있는 승객들을 실어, 원하는 위치에 내려준다
        // 이 때 모든 승객들을 데려다주고, 주인공이 m번 집에 가는데 이동해야하는 최소 거리는?
        //
        // 스위핑 문제
        // 주인공이 m번 집까지는 가는 거리는 m이다.
        // 만약 승객들 또한 정방향으로 타고 간다면, 그냥 m번 집까지 가면서 도중에 실어내려주면 된다.
        // 문제가 되는 점은 역방향으로 가는 승객들
        // 역방향으로 갈 때, 그 거리를 최소화하기 위해 최대한 서로 중복되는 역방향 승객들을 최대한 많이 실어야한다
        // 만약 7 -> 3, 8 -> 5로 가는 승객들이 있다고 하자
        // 이 두 승객을 최소한의 역방향 이동으로 데려다주는 방법은
        // 8 위치 -> 7위치 -> 5위치 -> 3위치. 즉 8 -> 3으로 5의 이동거리로 데려다주는 방법이다
        // 역방향 승객들만 구해, 서로 간의 중복되지 않는 역방향 거리를 모두 더하면 최소한의 역방향으로 승객들을 데려다 줄 수 있다
        // 따라서 답은 m + 역방향의 거리 * 2 (역방향으로 갔다가, 다시 돌아와야하므로) 이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 하차 위치가 0에 가까운 순으로 살펴보자.
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.end));
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            // 역방향 승객들만 우선순위큐에 담는다.
            if (end < start)
                priorityQueue.offer(new Person(start, end));
        }

        long sum = 0;       // 중복되지 않은 역방향 거리의 합.
        int lastStartLoc = 0;       // 현재까지 살펴본 승객들 중 가장 멀리서 타는 손님의 위치.
        while (!priorityQueue.isEmpty()) {
            Person p = priorityQueue.poll();

            // 만약 lastStartLoc보다 현재 승객의 목적지가 더 멀다면
            // 중복되는 거리가 없다.
            if (p.end > lastStartLoc) {
                // lastStartLoc을 갱신하고
                lastStartLoc = p.start;
                // 현재 승객의 역방향 거리를 모두 더해준다.
                sum += p.start - p.end;
            } else if (p.start > lastStartLoc) {
                // 현재 승객의 목적지가 여태까지 왔던 손님들의 탑승지보다 가깝고,
                // 현재 승객의 탑승지가 lastStartLoc보단 멀다면
                // 현재 승객의 역방향 거리 중 일부가 중복된다.
                // 중복되지 않는 거리인 p.start - lastStartLoc을 더해주고
                sum += p.start - lastStartLoc;
                // 최대 탑승 위치는 p.start로 갱신해준다.
                lastStartLoc = p.start;
            }
        }
        System.out.println(m + 2 * sum);
    }
}