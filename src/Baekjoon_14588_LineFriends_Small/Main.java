/*
 Author : Ruel
 Problem : Baekjoon 14588번 Line Friends (Small)
 Problem address : https://www.acmicpc.net/problem/14588
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14588_LineFriends_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n 선분이 주어지고, 서로 겹치는 부분이 존재하는 선분들의 경우 서로 친구를 맺기로 했다.
        // 친구 관계가 1일 경우, 친구의 친구는 2, 친구의 친구의 친구는 3... 같이 주어진다고 할 때
        // q개의 관계에 대해 출력하라
        //
        // 스위핑, 플로이드 워셜, 정렬 문제
        // 먼저 친구 관계를 파악하기 위해
        // 겹치는 선분들에 대해 관계를 1씩 설정해준 후, 플로이드 워셜로 다른 관계들의 최소 값들을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 선분
        int n = Integer.parseInt(br.readLine());

        int[][] lines = new int[n][2];
        // 선분의 시작점이 이른 순서대로 살펴본다.
        PriorityQueue<Integer> startPoints = new PriorityQueue<>(Comparator.comparingInt(o -> lines[o][0]));
        for (int i = 0; i < lines.length; i++) {
            lines[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            startPoints.offer(i);
        }

        int[][] adjMatrix = new int[n][n];
        PriorityQueue<Integer> endPoints = new PriorityQueue<>(Comparator.comparingInt(o -> lines[o][1]));
        // 시작점이 이른 순서대로 살펴보면서
        while (!startPoints.isEmpty()) {
            // 끝점이 현재 시작점보다 작은 선분들은 친구 관계가 아니므로 제거한다.
            while (!endPoints.isEmpty() &&
                    lines[endPoints.peek()][1] < lines[startPoints.peek()][0])
                endPoints.poll();
            
            // 끝점이 현재 시작점보다 멀리있는 경우, 모두 친구 관계
            for (int friend : endPoints)
                adjMatrix[startPoints.peek()][friend] = adjMatrix[friend][startPoints.peek()] = 1;
            
            // 현재의 선분을 endPoints에 추가
            endPoints.offer(startPoints.poll());
        }
        
        // 플로이드 워셜
        for (int via = 0; via < n; via++) {
            for (int start = 0; start < n; start++) {
                if (via == start || adjMatrix[start][via] == 0)
                    continue;

                for (int end = 0; end < n; end++) {
                    if (end == via || end == start || adjMatrix[via][end] == 0)
                        continue;

                    if (adjMatrix[start][end] == 0 || adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }
        
        // q개의 쿼리 처리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            // adjMatrix[a][b]가 0이라면 아무 관계가 아니므로 -1
            // 값이 있다면 해당 값 출력.
            sb.append(adjMatrix[a][b] == 0 ? -1 : adjMatrix[a][b]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}