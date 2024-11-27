/*
 Author : Ruel
 Problem : Baekjoon 6135번 Cow Hurdles
 Problem address : https://www.acmicpc.net/problem/6135
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6135_CowHurdles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지점과 두 지점을 잇는 m개의 경로가 주어진다.
        // 경로 사이에는 높이가 존재한다.
        // t개의 출발지와 도착지가 주어진다.
        // 출발지에서 도착지로 도달할 때까지 오르는 최대 높이를 최소화하고자할 때
        // 각각의 최대 높이를 출력하라
        //
        // 플로이드 와셜 문제
        // 인접 행렬에 경로의 높이를 입력 받고
        // start -> end까지의 최대 높이를
        // start -> via, via -> end 중 높은 높이로 결정된다.
        // 그 후 start -> end까지의 최대 높이들 중 가장 작은 값을 구해나가는 작업을 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지점, m개의 경로, t개의 이동
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 인접 행렬
        int[][] adjMatrix = new int[n + 1][n + 1];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            
            adjMatrix[s][e] = h;
        }
        
        // 플로이드 워셜
        // 경유지
        for (int via = 1; via <= n; via++) {
            // 출발지
            for (int start = 1; start <= n; start++) {
                if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                    continue;
                
                // 도착지
                for (int end = 1; end <= n; end++) {
                    if (end == start || end == via || adjMatrix[via][end] == Integer.MAX_VALUE)
                        continue;

                    // 출발지 -> 도착지까지의 최대 높이는
                    // 출발지 -> 경유지, 경유지 -> 도착지 높이들 중 큰 값이다.
                    // 이번 최대 높이가 여태까지 중의 최대 높이들 중 가장 작은 값인지 확인.
                    adjMatrix[start][end] = Math.min(adjMatrix[start][end], Math.max(adjMatrix[start][via], adjMatrix[via][end]));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // t개의 이동 처리
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // a에서 b로 이동하는데 초기값 그대로라면 경로가 존재하지 않아, 이동할 수 없는 경우.
            // -1 기록
            // 그 외의 경우, 계산된 최대 높이 기록
            sb.append(adjMatrix[a][b] == Integer.MAX_VALUE ? -1 : adjMatrix[a][b]).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}