/*
 Author : Ruel
 Problem : Baekjoon 12956번 퍼레이드
 Problem address : https://www.acmicpc.net/problem/12956
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12956_퍼레이드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 교차로, m개의 거리로 이루어진 도시가 있다.
        // 도로는 잇는 두 교차로와 길이가 주어진다.
        // 모든 교차로 (x, y) 쌍마다 오가는 버스가 존재한다.
        // 도로들 중 하나를 지정하여 퍼레이드를 하고자한다.
        // 퍼레이드를 했을 때, 영향을 받는 버스 노선의 개수를 구하고자 한다.
        // 영향을 받는 버스는 해당 도로를 제외하고 최단 경로를 구했을 때, 그 길이가 영향을 받는 경우이다.
        //
        // 플로이드 워셜
        // 먼저 모든 교차로 쌍에 대해 최단 경로를 구해야한다. -> 플로이드 워셜
        // 추가적으로 해당 시작점과 도착점을 갖는 경로가 유일한지도 판별해야하므로 경로의 수도 세야한다.
        // 그 후, 도로마다 시작점과 도착점을 갖는 경로에 대해 해당 경로를 꼭 거치는지 여부를 판단하며 개수를 세어준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 교차로, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 인접 행렬
        long[][][] adjMatrix = new long[n][n][2];
        for (long[][] am : adjMatrix) {
            for (long[] road : am)
                Arrays.fill(road, Long.MAX_VALUE);
        }
        // 시작점과 도착점이 같은 경우, 거리는 0, 경로의 수는 1
        for (int i = 0; i < n; i++) {
            adjMatrix[i][i][0] = 0;
            adjMatrix[i][i][1] = 1;
        }
        
        // 각 도로의 정보
        int[][] roads = new int[m][3];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());
            roads[i][0] = from;
            roads[i][1] = to;
            roads[i][2] = time;

            if (adjMatrix[from][to][0] > time) {
                adjMatrix[from][to][0] = adjMatrix[to][from][0] = time;
                adjMatrix[from][to][1] = adjMatrix[to][from][1] = 1;
            }
        }
        
        // 플로이드 워셜
        for (int via = 0; via < n; via++) {
            for (int start = 0; start < n; start++) {
                if (start == via || adjMatrix[start][via][0] == Long.MAX_VALUE)
                    continue;

                for (int end = 0; end < n; end++) {
                    if (end == start || end == via || adjMatrix[via][end][0] == Long.MAX_VALUE)
                        continue;
                    
                    // start -> via -> end의 거리와 경로의 수
                    long distance = adjMatrix[start][via][0] + adjMatrix[via][end][0];
                    long counts = adjMatrix[start][via][1] * adjMatrix[via][end][1];
                    // 최단 경로를 갱신하는 경우. 거리와 경로의 수 갱신
                    if (adjMatrix[start][end][0] > distance) {
                        adjMatrix[start][end][0] = distance;
                        adjMatrix[start][end][1] = counts;
                    } else if (adjMatrix[start][end][0] == distance)        // 최단 경로와 거리가 같은 경우. 경로의 수 누적
                        adjMatrix[start][end][1] += counts;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // 모든 도로에 대해
        for (int[] r : roads) {
            int count = 0;
            // start -> end로 가는 버스가 도로 r을 거치는지 여부 확인
            for (int start = 0; start < n; start++) {
                for (int end = 0; end < n; end++) {
                    // 경로의 길이는 start -> r[0] + r의 길이 + r[1] -> end
                    // 경로의 수는 start -> r[0]의 경로의 수 * r[1] -> end의 경로의 수
                    // 두 값이 기록된 최단 경로의 길이와 경로의 수와 일치하는 경우
                    // r을 반드시 거쳐야하는 경우.
                    // count 증가
                    if (adjMatrix[start][r[0]][0] + r[2] + adjMatrix[r[1]][end][0] == adjMatrix[start][end][0] &&
                            adjMatrix[start][r[0]][1] * adjMatrix[r[1]][end][1] == adjMatrix[start][end][1]) {
                        count++;
                    }
                }
            }
            // 영향을 받는 노선의 수 기록
            sb.append(count).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}