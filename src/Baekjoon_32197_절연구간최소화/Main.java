/*
 Author : Ruel
 Problem : Baekjoon 32197번 절연 구간 최소화
 Problem address : https://www.acmicpc.net/problem/32197
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32197_절연구간최소화;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 지하철은 전기를 공급 받는 방식이 직류와 교류 두가지이다.
        // 이는 구간마다 정해져있으며, 급전 방식이 바뀔 때 잠시 전력 공급이 중단되는데 이를 절연이라고 부른다.
        // 절연이 적게 발생할수록 만족도가 높아진다.
        // n개의 역, m개의 선로가 주어진다.
        // 선로는 s e t로 주어지며 s와 e 역을 연결하며, 전력 공급 방식인 t는 0 또는 1로 주어진다.
        // a역에서 출발하여 b역에 도달하고자 할 때
        // 절연이 최소로 일어나는 경우는?
        //
        // 최단 경로, dijkstra
        // 최단 경로로 풀되, 기준이 되는 것은 거리가 아니라 절연 횟수이다.
        // 절연 횟수가 최소로 a에서 b로 도달하게끔 계산하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 역, m개의 노선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 노선 정보
        List<List<int[]>> routes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            routes.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            routes.get(s).add(new int[]{e, t});
            routes.get(e).add(new int[]{s, t});
        }

        st = new StringTokenizer(br.readLine());
        // 출발지와 도착지 a, b
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // a에서 출발하여 각 정거장에 도달하는 최소 절연 횟수
        // 전력 공급 방식에 따라 2가지 경우로 도달할 수 있다.
        int[][] minChanges = new int[n + 1][2];
        for (int[] mc : minChanges)
            Arrays.fill(mc, Integer.MAX_VALUE);
        minChanges[a][0] = minChanges[a][1] = 0;

        // dijkstra
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        // 처음 시작
        priorityQueue.offer(new int[]{a, 0, 0});
        priorityQueue.offer(new int[]{a, 1, 0});
        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            // 더 적은 절연으로 현재 정거장에 도달할 수 있다면 건너뜀.
            if (minChanges[current[0]][current[1]] < current[2])
                continue;
            
            // current -> next
            for (int[] next : routes.get(current[0])) {
                // 절연 횟수
                int change = current[2] + Math.abs(next[1] - current[1]);
                // 절연 횟수가 최소값을 갱신한다면
                // minChanges에 값 기록 후 우선순위큐에 추가
                if (minChanges[next[0]][next[1]] > change) {
                    minChanges[next[0]][next[1]] = change;
                    priorityQueue.offer(new int[]{next[0], next[1], change});
                }
            }
        }
        // b역에 두가지 급전 방식 중 더 적은 절연 횟수로 도달한 경우를 출력
        System.out.println(Math.min(minChanges[b][0], minChanges[b][1]));
    }
}