/*
 Author : Ruel
 Problem : Baekjoon 23258번 밤편지
 Problem address : https://www.acmicpc.net/problem/23258
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23258_밤편지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n번의 집이 주어진다.
        // n개의 경로가 주어지며, 시작 집과 도착 집 그리고 소요 시간이 주어진다.
        // i번 집에는 2^i 방울의 이슬이 있다. 출발지와 도착지가 아닌 경유하는 경우 해당 이슬을 모두 마셔야한다.
        // 최대 2^c 방울의 이슬을 마실 수 있는 반딧불이 q마리가 각각 s에서 출발하여 e에 도달하고자 한다.
        // 이 때 도달하는 최소 소요 시간은?
        //
        // 플로이드 워셜 문제
        // n이 최대 300, q가 최대 50만으로 주어진다.
        // 따라서 일일이 주어질 때마다 계산하는 것보다 미리 모든 경우의 수를 계산해두고, 질의가 들어왔을 때 답을 바로 처리해주는 것이 좋다.
        // 먼저 최대 2^c의 방울을 마실 수 있다 라는 의미는 경유하는 집의 최대 번호가 c 미만이어야한다는 것을 뜻한다.
        // 플로이드 워셜의 큰 틀은
        // 경유지를 하나씩 늘려가며, 출발지 -> 경유지 -> 도착지의 모든 경우의 수를 계산한다.
        // 따라서 adjMatrix[start][end][maxVia] = 최소 시간으로 하여, 경유하는 최대 집의 번호가 dp에 반영되도록 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 집의 개수 n, 질의의 개수 q
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 인접 행렬
        int[][][] adjMatrix = new int[n + 1][n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                // (i + 1) -> (j + 1) 의 소요 시간
                int t = Integer.parseInt(st.nextToken());
                // 0이라면 경로가 없는 경우. t인 경우는 해당 시간을 기록
                adjMatrix[i + 1][j + 1][0] = (t == 0 ? Integer.MAX_VALUE : t);
            }
        }

        // 경유지
        for (int via = 1; via <= n; via++) {
            // 최대 via 집까지 방문하는 것이 허용된다.
            // 하지만 via 집을 굳이 들리지 않아도 최소 시간으로 도달할 수 있다.
            // 따라서 via - 1일 때의 값을 via에 모두 복사해둔다.
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++)
                    adjMatrix[i][j][via] = adjMatrix[i][j][via - 1];
            }

            // 출발지
            for (int start = 1; start <= n; start++) {
                if (start == via || adjMatrix[start][via][via] == Integer.MAX_VALUE)
                    continue;

                // 도착지
                for (int end = 1; end <= n; end++) {
                    if (end == via || end == start || adjMatrix[via][end][via] == Integer.MAX_VALUE)
                        continue;

                    // 기록되어있는 경우보다 start -> via -> end가 더 빠른 경우
                    // 값 갱신
                    adjMatrix[start][end][via] = Math.min(adjMatrix[start][end][via], adjMatrix[start][via][via] + adjMatrix[via][end][via]);
                }
            }
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 최대 2^c방울을 마실 수 있는 반딧불이가 s에서 e로 간다.
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            // 출발지와 도착지가 같은 경우
            // 이슬을 안 마셔도 됨
            if (s == e)
                sb.append(0);
            // 그 외의 경우
            // 초기값이라면 불가능한 경우이므로 -1, 그 외의 경우 해당 시간을 기록
            else
                sb.append(adjMatrix[s][e][c - 1] == Integer.MAX_VALUE ? -1 : adjMatrix[s][e][c - 1]);
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}