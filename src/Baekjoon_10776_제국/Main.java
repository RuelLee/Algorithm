/*
 Author : Ruel
 Problem : Baekjoon 10776번 제국
 Problem address : https://www.acmicpc.net/problem/10776
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10776_제국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int t;
    int h;

    public Road(int end, int t, int h) {
        this.end = end;
        this.t = t;
        this.h = h;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // a항구에서 출발하여 무인도 b에 뗏목을 타고 도착하고자 한다.
        // 뗏목의 두께는 k이다.
        // n개의 섬이 있고, m개의 바닷길이 있다.
        // 각 바닷길은 출발지 도착지 소요시간 뗏목의 소모되는 두께가 주어진다.
        // 목적지까지 도달할 때까지 뗏목의 두께는 0이 되어서는 안된다.
        // 목적지에 도달하는 최소 시간은?
        //
        // 다익스트라 문제
        // 최단 경로 문제인데, 뗏목의 내구도가 들어갔을 뿐이다.
        // 따라서 각 지점에 도달할 때, 시간 뿐만 아니라 뗏목의 두께 상태도 나타내야한다.
        // minTimes[지점][뗏목의두께] = 시간 으로 정하여, 목적지까지 뗏목의 두께가 0이 아닌 상태로 도달할 때의 시간을 구한다.
        // 어느 지점에 더 적은 두께로 더 많은 시간이 소요되어 도달하는 경우도 있을지 모르나, 해당 경우는 불필요하다.
        // 따라서 지점에 이르는 최소 시간을 갱신할 때, 도달한 두께보다 더 적은 값에 현재 도달 시간과 비교하여 더 적은 값을 넣어두면
        // 불필요한 연산을 막을 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 두께 k, n개의 섬, m개의 바닷길
        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        
        // 바닷길 정보
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());

            roads.get(s).add(new Road(e, t, h));
            roads.get(e).add(new Road(s, t, h));
        }

        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // minTimes[지점][뗏목의두께] = 최소 소요 시간
        int[][] minTimes = new int[n + 1][k + 1];
        for (int[] mt : minTimes)
            Arrays.fill(mt, Integer.MAX_VALUE);
        // 시작 지점
        minTimes[a][k] = 0;
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>((Comparator.comparingInt(o -> o.t)));
        priorityQueue.offer(new Road(a, 0, k));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 더 적은 값이 들어와있다면 계산할 필요 없다.
            if (minTimes[current.end][current.h] < current.t)
                continue;

            // current.end에서 next로 가는 경우
            for (Road next : roads.get(current.end)) {
                // 전체 소요 시간
                int t = current.t + next.t;
                // 남은 뗏목의 두께
                int h = current.h - next.h;
                // h가 0보다 커야하고, 최소 도달 시간을 갱신하는 경우에
                if (h > 0 && minTimes[next.end][current.h - next.h] > t) {
                    // h보다 같거나 작은 두께로 도달하는 경우
                    // t보다 더 적은 소요 시간을 갖어야 의미가 있다.
                    // 따라서 h보다 같거나 적은 두께에는 모두 t와 비교하여 더 적은 값을 넣는다.
                    for (int i = h; i > 0; i--) {
                        // 더 적은 값이 들어와있는 경우.
                        // 이미 해당 값으로 앞부분이 채워져있을 것이므로 반복문 종료
                        if (minTimes[next.end][i] < t)
                            break;
                        minTimes[next.end][i] = Math.min(minTimes[next.end][i], t);
                    }
                    // 해당 상태 우선순위큐에 추가
                    priorityQueue.offer(new Road(next.end, t, h));
                }
            }
        }
        // b 지점에 뗏목의 두께가 1이상으로 도달할 때의 시간이 초기값이라면 불가능한 경우이므로 -1 출력
        // 그 외의 경우, 해당 시간 출력
        System.out.println(minTimes[b][1] == Integer.MAX_VALUE ? -1 : minTimes[b][1]);
    }
}