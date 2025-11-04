/*
 Author : Ruel
 Problem : Baekjoon 24042번 횡단보도
 Problem address : https://www.acmicpc.net/problem/24042
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24042_횡단보도;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int end;
    long time;

    public Seek(int end, long time) {
        this.end = end;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 교차로에 n개의 지역이 있고, m분의 주기로 신호가 반복된다.
        // 각 1분마다 횡단보도 중 하나가 초록불로 바뀌며, 이 시간 내에만 건널 수 있다.
        // m개의 횡단보도 신호가 주어진다.
        // 1번 지역에서 n번 지역으로 가는데 걸리는 최소 시간은?
        //
        // dijkstra 문제
        // 1번 -> n번 최단 시간. 다익스트라
        // 횡단 보도의 시간이 이번 주기에 다음 횡단보도를 갈 수있는지, 
        // 다음 신호 주기에 가야하는지를 잘 계산해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지역, m분의 주기
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 지역에서 다음 지역으로 갈 수 있는 신호
        // 
        List<List<int[]>> signals = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            signals.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // a < - > b 지역을 오갈 수 있는 신호가
            // m * x + i 때마다 켜진다.
            signals.get(a).add(new int[]{b, i});
            signals.get(b).add(new int[]{a, i});
        }
        
        // dijkstra
        // 각 지역에 도달하는 최소 시간
        long[] minTimes = new long[n + 1];
        Arrays.fill(minTimes, Long.MAX_VALUE);
        // 시작 위치
        minTimes[1] = 0;
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.time));
        priorityQueue.offer(new Seek(1, 0));
        while (!priorityQueue.isEmpty()) {
            // 현재 위치
            Seek current = priorityQueue.poll();
            // 현재 위치에 도달하는 시간보다 더 빠른 기록이 있다면
            // 이번 탐색은 건너뜀.
            if (minTimes[current.end] < current.time)
                continue;
            
            // 다음 지역
            for (int[] next : signals.get(current.end)) {
                // 이번 주기에 다음 지역에 도달하는 시간.
                long nextTime = minTimes[current.end] / m * m + next[1];
                // 만약 이미 지났다면 다음 주기에 가야한다.
                if (nextTime < minTimes[current.end])
                    nextTime += m;
                // 횡단보도를 건너는데 드는 시간
                nextTime++;
                
                // 최소 시간을 갱신한다면 값을 기록하고 우선순위큐에 추가
                if (minTimes[next[0]] > nextTime) {
                    minTimes[next[0]] = nextTime;
                    priorityQueue.offer(new Seek(next[0], nextTime));
                }
            }
        }
        // n번 지역에 도달하는 최소 시간 출력
        System.out.println(minTimes[n]);
    }
}