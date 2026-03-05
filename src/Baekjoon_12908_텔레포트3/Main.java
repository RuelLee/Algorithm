/*
 Author : Ruel
 Problem : Baekjoon 12908번 텔레포트 3
 Problem address : https://www.acmicpc.net/problem/12908
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12908_텔레포트3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] points;

    public static void main(String[] args) throws IOException {
        // 시작점과 도착점이 주어진다.
        // 세 곳의 텔레포트 가능 지점이 주어지고
        // 각 텔레포트는 두 지점을 10초로 이동할 수 있다.
        // 기본적인 이동으로 상하좌우를 1초에 이동할 수 있다.
        // 시작점에서 도착점에 가는데 필요한 최소 시간은?
        //
        // 플로이드 워셜 문제
        // 먼저 모든 지점에 대해 서로의 거리를 맨해튼 거리로 측정하여 계산한다.
        // 그리고 텔레포트의 양 지점에 대해서는 직접 이동하는 것이 빠른지
        // 텔레포트를 이용하여 10초만에 이동하는 것이 빠른지도 비교한다.
        // 그 후, 플로이드 워셜을 통해, 시작점에서 도착점까지의 최소 시간을 구하낟.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 각 지점들
        // 0번 시작점, 7번 도착점
        // 1 - 2, 3 - 4, 5 - 6은 서로 연결된 텔레포트
        points = new int[8][2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        points[0][0] = Integer.parseInt(st.nextToken());
        points[0][1] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        points[7][0] = Integer.parseInt(st.nextToken());
        points[7][1] = Integer.parseInt(st.nextToken());
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            points[i * 2 + 1][0] = Integer.parseInt(st.nextToken());
            points[i * 2 + 1][1] = Integer.parseInt(st.nextToken());
            points[i * 2 + 2][0] = Integer.parseInt(st.nextToken());
            points[i * 2 + 2][1] = Integer.parseInt(st.nextToken());
        }

        // 먼저 각 지점에 대해 맨해튼 거리 측정
        long[][] adjMatrix = new long[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++)
                adjMatrix[i][j] = adjMatrix[j][i] = calcDistance(i, j);
        }
        // 텔레포트 유효 여부 판단
        for (int i = 1; i < 7; i += 2)
            adjMatrix[i][i + 1] = adjMatrix[i + 1][i] = Math.min(adjMatrix[i][i + 1], 10);

        // 플로이드 워셜
        for (int via = 0; via < 8; via++) {
            for (int start = 0; start < 8; start++) {
                if (start == via)
                    continue;

                for (int end = 0; end < 8; end++) {
                    if (end == via || end == start)
                        continue;

                    adjMatrix[start][end] = Math.min(adjMatrix[start][end], adjMatrix[start][via] + adjMatrix[via][end]);
                }
            }
        }
        // 답 출력
        System.out.println(adjMatrix[0][7]);
    }

    // 맨해튼 거리 계산
    static int calcDistance(int a, int b) {
        return Math.abs(points[a][0] - points[b][0]) + Math.abs(points[a][1] - points[b][1]);
    }
}