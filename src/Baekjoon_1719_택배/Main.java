/*
 Author : Ruel
 Problem : Baekjoon 1719번 택배
 Problem address : https://www.acmicpc.net/problem/1719
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1719_택배;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 집하장과 m개의 집하장 간 경로가 시작지점, 도착지점, 소요시간으로 주어진다
        // 각 집하장에서 다른 집하장으로 도달하는 최소 시간의 경로로 이동할 때, 첫 집하장에서 이동해야하는 두번째 집하장의 번호를 출력하라.
        //
        // 기본적으로 모든 집하장 간에 최소 시간을 계산해야하므로 플로이드-와샬로 계산한다
        // 각 지점에 도달하기 위해 이동하는 경로를 모두 저장할 수는 없으므로
        // 각 경로마다 두번째 집하장의 번호를 기록해둔다. 그리고 최소 시간을 계산하며 값이 갱신될 때마다 이 두번째 집하장의 번호도 같이 가져간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] adjMatrix = new int[n][n];
        int[][] secondPoints = new int[n][n];
        // 인접 행렬의 초기값으로는 최대값으로 세팅해준다.
        for (int[] am : adjMatrix)
            Arrays.fill(am, 1001 * (n + 1));

        // 경로들
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 시작지점
            int a = Integer.parseInt(st.nextToken()) - 1;
            // 도착지점
            int b = Integer.parseInt(st.nextToken()) - 1;
            // 소요 시간
            int cost = Integer.parseInt(st.nextToken());

            // 더 적은 소요 시간을 갖는 경로가 들어왔을 때

            if (cost < adjMatrix[a][b]) {
                // 양방향 경로를 갱신해준다.
                adjMatrix[a][b] = adjMatrix[b][a] = cost;
                // 처음 주어지는 경로에서는 도착 지점이 두번째 집하장.
                secondPoints[a][b] = b + 1;
                secondPoints[b][a] = a + 1;
            }
        }

        for (int via = 0; via < adjMatrix.length; via++) {
            for (int start = 0; start < adjMatrix.length; start++) {
                // 경유지와 시작지가 같을 때는 건너 뛴다.
                if (start == via)
                    continue;
                for (int end = 0; end < adjMatrix.length; end++) {
                    // 도착지와 시작지가 같거나, 도착지와 경유지가 같은 경우 건너 뜀.
                    if (end == start || end == via)
                        continue;

                    // 최소 경로가 갱신되는 경우.
                    if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end]) {
                        // 소요 시간을 갱신해주고
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                        // 시작점 -> 경유지에 기록된 두번째 집하장의 번호를 새로 갱신되는 경로에 반영해준다.
                        secondPoints[start][end] = secondPoints[start][via];
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // 최종적으로 secondPoints에 각 집하장 -> 집하장의 최소 경로에 따른 두번째 이동 집하장이 기록되어있다.
        // 해당 집하장들을 출력.
        for (int[] sp : secondPoints) {
            for (int i : sp)
                sb.append(i == 0 ? "-" : i).append(" ");
            sb.append("\n");
        }
        System.out.print(sb);
    }
}