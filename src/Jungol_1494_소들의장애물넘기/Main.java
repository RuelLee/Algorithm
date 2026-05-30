/*
 Author : Ruel
 Problem : Jungol 1494번 소들의 장애물 넘기
 Problem address : https://jungol.co.kr/problem/1494
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1494_소들의장애물넘기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지점과 지점들을 잇는 m개의 경로가 주어진다.
        // 각 경로에는 언덕이 주어진다.
        // t개의 시작점과 도착점이 주어질 때
        // 지나가는 경로들 중 최고 언덕의 높이를 최소화화고자 한다.
        // 그 때, 경로 내의 제일 높은 언덕의 높이를 출력하라
        //
        // 플로이드 워셜 문제
        // 시작점과 도착점이 t개로 꽤 많으므로
        // 플로이드 워셜로 모든 경로에 대해 계산한 후
        // t개의 질문에 답한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지점, m개의 경로, t개의 질의
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());

        // 인접 행렬
        int[][] adjMatrix = new int[N + 1][N + 1];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            adjMatrix[s][e] = h;
        }

        // 플로이드 워셜
        for (int v = 1; v <= N; v++) {
            for (int s = 1; s <= N; s++) {
                if (s == v || adjMatrix[s][v] == Integer.MAX_VALUE)
                    continue;

                for (int e = 1; e <= N; e++) {
                    if (e == s || e == v || adjMatrix[v][e] == Integer.MAX_VALUE)
                        continue;

                    // s -> v -> e일 때, 지나는 최고 언덕의 높이가
                    // 다른 경로들에 비해 최소인지 확인
                    adjMatrix[s][e] = Math.min(adjMatrix[s][e], Math.max(adjMatrix[s][v], adjMatrix[v][e]));
                }
            }
        }

        // t개의 질의 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            sb.append(adjMatrix[a][b] == Integer.MAX_VALUE ? -1 : adjMatrix[a][b]).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}