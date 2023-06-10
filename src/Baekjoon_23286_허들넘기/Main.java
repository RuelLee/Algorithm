/*
 Author : Ruel
 Problem : Baekjoon 23286번 허들 넘기
 Problem address : https://www.acmicpc.net/problem/23286
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23286_허들넘기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 간선이 주어지며, 간선은 단방향이다.
        // 간선 위에는 허들이 놓여있고, 반드시 허들을 넘어야한다.
        // t번의 연습으로 각 출발지점 s에서 도착지점 e로 이동을 하며 연습을 한다.
        // 그 때, s -> e로 가는 경로에 놓여있는 최대 허들의 높이를 최소화하고자 한다.
        // 그 때 허들의 높이들은?
        //
        // 플로이드 워셜 문제
        // t가 최대 4만개로 꽤나 큰 값을 갖는다.
        // 따라서 다익스트라로 일일이 계산하기보다는 플로이드 워셜로 모든 경로에 대해
        // 최소 높이를 구하고, t개의 쿼리들을 처리해주자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n개의 정점, m개의 간선, t개의 연습
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 간선 값 초기화
        int[][] adjMatrix = new int[n + 1][n + 1];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            adjMatrix[u][v] = h;
        }

        // 플로이드 워셜
        for (int via = 1; via < adjMatrix.length; via++) {
            for (int start = 1; start < adjMatrix.length; start++) {
                // start와 via가 같거나 두 경로 사이에 간선이 없다면 건너뛴다.
                if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                    continue;

                for (int end = 1; end < adjMatrix.length; end++) {
                    // end와 start, end와 via가 같다면 건너뛰고
                    // 마찬가지로 via -> end로 가는 경로가 없어도 건너뛴다.
                    if (end == start || end == via || adjMatrix[via][end] == Integer.MAX_VALUE)
                        continue;

                    // start -> end로 갈 때 넘는 허들의 최대높이는
                    // 기존에 계산되어있던 값과
                    // start -> via -> end로 거쳐갈 때의 넘는 최대 높이의 허들과 비교하여
                    // 더 낮은 쪽을 기록해둔다.
                    adjMatrix[start][end] = Math.min(adjMatrix[start][end], Math.max(adjMatrix[start][via], adjMatrix[via][end]));
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        // t개의 쿼리 처리
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            // s -> e로 가는 경로가 존재하지 않을 경우에는 -1
            // 존재한다면 기록된 최대 허들의 높이를 기록한다.
            sb.append(adjMatrix[s][e] == Integer.MAX_VALUE ? -1 : adjMatrix[s][e]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}