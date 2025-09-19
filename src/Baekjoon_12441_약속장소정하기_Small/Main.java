/*
 Author : Ruel
 Problem : Baekjoon 12441번 약속장소 정하기 (Small)
 Problem address : https://www.acmicpc.net/problem/12441
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12441_약속장소정하기_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 각 테스트케이스마다 n개의 도시, p명의 친구, m개의 도로가 주어진다.
        // 각 친구는 현재 친구가 존재하는 도시 x, 거리 1을 움직이는데 걸리는 시간 v가 주어진다.
        // 각 도로는 도로가 지나가는 도시들 사이의 거리 d, 연결된 도시의 수 l, 연속한 도시 c가 l개 주어진다.
        // 각 친구들이 한 도시에 모일 때, 가장 적게 걸리는 시간을 구하라
        //
        // 최단 경로, dijkstra 문제
        // minDistances[도시][친구] = 최소 도달 시간 으로 정하고
        // 다익스트라를 통해 풀 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        // 테스트케이스마다 반복하여 사용하는 변수들을 선언해준 뒤
        // 각 테스트케이스마다 초기화하거나 덮어써 사용한다.
        StringTokenizer st;
        int[][] friends = new int[10][2];
        List<List<int[]>> roads = new ArrayList<>();
        int[][] minDistances = new int[111][10];
        for (int i = 0; i < 111; i++)
            roads.add(new ArrayList<>());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // n개의 도시, p명의 친구, m개의 도로
            int n = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            // 각 친구들의 시작 도시와 거리 1을 이동하는데 걸리는 시간
            for (int i = 0; i < p; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < friends[i].length; j++)
                    friends[i][j] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < n + 1; i++)
                roads.get(i).clear();
            
            // 도로 정보
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int d = Integer.parseInt(st.nextToken());
                int l = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                for (int j = 0; j < l - 1; j++) {
                    int nextC = Integer.parseInt(st.nextToken());
                    roads.get(c).add(new int[]{nextC, d});
                    roads.get(nextC).add(new int[]{c, d});
                    c = nextC;
                }
            }
            
            // minDistances[도시][친구] = 최소 도달 시간
            for (int[] md : minDistances)
                Arrays.fill(md, Integer.MAX_VALUE);
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
            for (int i = 0; i < friends.length; i++) {
                // 각 친구들의 시작 위치 세팅 및 우선순위큐에 추가
                minDistances[friends[i][0]][i] = 0;
                priorityQueue.offer(new int[]{i, friends[i][0], 0});
            }

            while (!priorityQueue.isEmpty()) {
                int[] current = priorityQueue.poll();
                // 이미 더 이른 시간에 해당 친구가 해당 지점을 거쳐갔다면 건너뜀.
                if (minDistances[current[1]][current[0]] < current[2])
                    continue;
                
                // 다음 이동 가능 도시
                for (int[] next : roads.get(current[1])) {
                    // 다음 도시로 가는데 소요 시간이, 기록된 것보다 더 적게 걸린다면 갱신
                    if (minDistances[next[0]][current[0]] > current[2] + next[1] * friends[current[0]][1]) {
                        minDistances[next[0]][current[0]] = current[2] + next[1] * friends[current[0]][1];
                        priorityQueue.offer(new int[]{current[0], next[0], minDistances[next[0]][current[0]]});
                    }
                }
            }

            int answer = Integer.MAX_VALUE;
            // 모든 도시들에 대해
            for (int i = 1; i < n + 1; i++) {
                int max = 0;
                // 가장 늦은 친구가 도달하는 시간을 구한다.
                for (int j = 0; j < p; j++)
                    max = Math.max(max, minDistances[i][j]);
                // 해당 시간이 가장 이른 곳이 답
                answer = Math.min(answer, max);
            }
            // 답 기록
            sb.append("Case #").append((testCase + 1)).append(": ").append(answer == Integer.MAX_VALUE ? -1 : answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}