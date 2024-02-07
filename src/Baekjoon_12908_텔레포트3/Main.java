/*
 Author : Ruel
 Problem : Baekjoon 12908번 텔레포트 3
 Problem address : https://www.acmicpc.net/problem/12908
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12908_텔레포트3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 시작점, 도착점 그리고 3개의 텔레포트 위치가 주어진다.
        // 텔레포트는 두 점을 이동하는데 10초가 걸린다.
        // 시작점에서 도착점에 도달하는데 걸리는 최소 시간은?
        //
        // 플로이드 워셜 문제
        // 위치가 시작점, 도착점, 3개의 텔레포트 두 위치 총 8개가 주어진다.
        // 따라서 모든 점에 대해 서로 이동 시간을 구하고, 플로이드 워셜을 돌려 최소 이동 거리를 구한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 8개의 위치가 주어진다.
        int[][] points = new int[8][2];
        // 시작점과 도착저
        for (int i = 0; i < 2; i++)
            points[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 텔레포트
        for (int i = 2; i < 8; i += 2) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++)
                    points[i + j][k] = Integer.parseInt(st.nextToken());
            }
        }

        // 각 위치들의 이동 시간을 모두 직접 구한다.
        int[][] adjMatrix = new int[8][8];
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++)
                adjMatrix[i][j] = adjMatrix[j][i] = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
        }
        // 텔레포트로 이동 가능한 경우,
        // 직접 이동과 텔레포트 중 더 적은 시간을 구한다.
        for (int i = 2; i < 8; i += 2)
            adjMatrix[i][i + 1] = adjMatrix[i + 1][i] = Math.min(adjMatrix[i][i + 1], 10);
        
        // 플로이드 워셜
        for (int via = 0; via < points.length; via++) {
            for (int start = 0; start < points.length; start++) {
                if (start == via)
                    continue;

                for (int end = 0; end < points.length; end++) {
                    if (end == via || end == start)
                        continue;

                    if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }

        // 시작점에서 도착점에 도달하는 최소 시간을 출력한다.
        System.out.println(adjMatrix[0][1]);
    }
}