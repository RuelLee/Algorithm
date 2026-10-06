/*
 Author : Ruel
 Problem : Jungol 1517번 여행
 Problem address : https://jungol.co.kr/problem/1517
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1517_여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 섬, m개의 비행 운항 정보, 세관이 있는 섬의 수 k, 여행 요청 q가 주어진다.
        // 비행 경로는 p요금으로 d섬에서 a섬으로 가는 편도 여행이다.
        // 여행 경로 내에 반드시 세관을 거쳐야하며, 세관은 1 ~ k번 섬에 있다.
        // q개의 출발섬과 도착섬이 주어질 때, 가능한 여행 수와 최소 비용의 합을 구하라
        //
        // 플로이드 워셜 문제
        // q개의 요청에 대해 답해야하므로, 미리 모든 경로에 대해 답을 모두 구해두면 좋다.
        // 세관을 거친 경로와 안 거친 경로도 구분해야한다.
        // 이번 플로이드 워셜은 세관을 거친 경로, 안 거친 경로 또한 구분이 되므로 총 두 번 반복하여 돌려준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 섬, m개의 비행 운항 정보, 세관이 있는 섬의 수 k, 여행 요청 q
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 인접행렬 초기화
        int[][][] adjMatrix = new int[n + 1][n + 1][2];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++)
                adjMatrix[i][j][0] = adjMatrix[i][j][1] = Integer.MAX_VALUE;
        }

        // 비행 운항 정보
        // adjMatrix[i][j][세관통과여부] = 비용
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            adjMatrix[d][a][(d <= k || a <= k) ? 1 : 0] = p;
        }

        // 플로이드 워셜을 총 두번 돌려준다.
        for (int i = 0; i < 2; i++) {
            // 경유 via
            for (int via = 1; via <= n; via++) {
                // 시작 start
                for (int start = 1; start <= n; start++) {
                    if (start == via || Math.min(adjMatrix[start][via][0], adjMatrix[start][via][1]) == Integer.MAX_VALUE)
                        continue;

                    // 도착 end
                    for (int end = 1; end <= n; end++) {
                        if (end == start || end == via || Math.min(adjMatrix[via][end][0], adjMatrix[via][end][1]) == Integer.MAX_VALUE)
                            continue;

                        // 도중에 세관을 들렸는지 여부
                        // start, via, end 중 하나라도 k이하의 섬이 있는지
                        // 선택된 경로가 세관을 거친 경로인지.
                        int customs = (via <= k || start <= k || end <= k || adjMatrix[start][via][1] <= adjMatrix[start][via][0] ||
                                adjMatrix[via][end][1] <= adjMatrix[via][end][0]) ? 1 : 0;

                        // 최소 비용의 합
                        adjMatrix[start][end][customs] = Math.min(adjMatrix[start][end][customs],
                                Math.min(adjMatrix[start][via][0], adjMatrix[start][via][1]) + Math.min(adjMatrix[via][end][0], adjMatrix[via][end][1]));
                    }
                }
            }
        }

        // q개의 질의 체크
        int cnt = 0;
        long sum = 0;
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            if (adjMatrix[s][e][1] != Integer.MAX_VALUE) {
                cnt++;
                sum += adjMatrix[s][e][1];
            }
        }
        // 답 출력
        System.out.println(cnt);
        System.out.println(sum);
    }
}