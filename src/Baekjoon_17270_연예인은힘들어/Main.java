/*
 Author : Ruel
 Problem : Baekjoon 17270번 연예인은 힘들어
 Problem address : https://www.acmicpc.net/problem/17270
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17270_연예인은힘들어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 지헌과 성하가 있는 위치가 주어진다.
        // 두 사람은 한 장소에서 만나려고 한다.
        // 1. 두 사람의 출발지는 약속 장소 후보에서 제외된다.
        // 2. 두 사람의 이동 거리 합이 최소가 되는 곳 중 하나를 약속 장소로 하려한다.
        // 3. 지헌이 성하보다 늦게 도착하는 곳은 약속 장소가 될 수 없다.
        // 4. 조건을 만족하는 곳이 여러 곳이라면 지헌이 더 일찍 도착하는 곳,
        // 그러한 곳도 여러 곳이라면 장소 번호가 더 이른 곳을 약속 장소로 한다.
        //
        // 플로이드 와셜 문제
        // 조건의 순서가 조금 웃겼는데, 거리 합이 최소인 장소들 중에서 나머지 조건을 만족하는지 살펴야했다.
        // 만약 거리합이 최소인 곳이 다른 조건을 만족하지 않아 약속후보에 들어가지 않는다면 답은 -1이었다.
        // 철저하게 조건 순서대로 풀어야만 했던 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 장소의 수와 도로의 수
        int v = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 인접 행렬
        int[][] adjMatrix = new int[v + 1][v + 1];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if (adjMatrix[a][b] > c)
                adjMatrix[a][b] = adjMatrix[b][a] = c;
        }
        
        // 플로이드 와셜
        for (int via = 1; via < adjMatrix.length; via++) {
            for (int start = 1; start < adjMatrix.length; start++) {
                if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                    continue;

                for (int end = 1; end < adjMatrix.length; end++) {
                    if (start == end || via == end || adjMatrix[via][end] == Integer.MAX_VALUE)
                        continue;

                    adjMatrix[start][end] = Math.min(adjMatrix[start][end], adjMatrix[start][via] + adjMatrix[via][end]);
                }
            }
        }

        st = new StringTokenizer(br.readLine());
        // 지헌과 성하의 위치
        int j = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        Queue<Integer> queue = new LinkedList<>();
        int distanceSum = Integer.MAX_VALUE;
        for (int i = 1; i < adjMatrix.length; i++) {
            // 두 사람의 출발지는 제외
            if (i == s || i == j)
                continue;
            // 이전 최소 거리와 같다면 후보지로 큐에 추가
            else if (adjMatrix[i][j] + adjMatrix[i][s] == distanceSum)
                queue.offer(i);
            // 최소 거리를 갱신한다면 큐를 새로 만들고 추가.
            else if (adjMatrix[i][j] + adjMatrix[i][s] < distanceSum) {
                distanceSum = adjMatrix[i][j] + adjMatrix[i][s];
                queue = new LinkedList<>();
                queue.offer(i);
            }
        }

        int answer = -1;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 지헌이 성하보다 늦게 도착하는 곳은 제외된다.
            if (adjMatrix[current][j] > adjMatrix[current][s])
                continue;

            // 만약 아직 answer가 0이거나
            // current가 answer보다 지헌이 더 먼저 도착한다면 답안에 등록한다.
            if (answer == -1 || adjMatrix[current][j] < adjMatrix[answer][j])
                answer = current;
        }
        
        // 답안 출력
        System.out.println(answer);
    }
}