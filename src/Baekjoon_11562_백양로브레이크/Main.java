/*
 Author : Ruel
 Problem : Baekjoon 11562번 백양로 브레이크
 Problem address : https://www.acmicpc.net/problem/11562
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11562_백양로브레이크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX = 251;

    public static void main(String[] args) throws IOException {
        // n개의 건물, m개의 도로가 주어진다.
        // m개의 도로는 u v b의 형태로 주어지며
        // b가 0일 경우, u -> v로 가는 일방통행, b가 1일 경우, u <-> v인 양방향 길이다.
        // 그리고 k개의 질문이 s e의 형태로 주어지며,
        // s -> e로 가는데는 일반통행로를 최소 몇 개 양방향 길로 바꾸면 가능한지에 대한 물음이다.
        // 물음에 대한 답들을 출력하라.
        //
        // 플로이드-와샬 문제이되, 조금 다르게 생각해볼 수 있었다.
        // 문제의 조건 상, 일반통행로를 -> 양방향 길로 바꾸는 경우만 가능하며, 해당 도로의 변환 개수를 세어나가야한다
        // 도로의 상황을 인접행렬로 생각해보자.
        // 전체 건물의 수(n)보다 큰 수로 초기화 시켜준다.
        // 그리고 도로가 주어질 때마다, 단방향일 경우에는 u - > v의 경우 0, v -> u의 경우 1을 저장해주자.
        // u -> v로 가는데는 도로를 건설할 필요가 없지만, v -> u의 경우, 도로를 하나 양뱡향 길로 바꿔야한다는 의미이다.
        // 양방향일 경우에는 u -> v, v -> u 모두 0을 저장해주자.
        // 그리고 플로이드 와샬을 통해 모든 루트를 돌며, 초기화 값인 경우는 건너뛰며(도로 자체가 없어 고려가 불가능한 경우)
        // 최소값을 구해나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] adjMatrix = new int[n][n];
        // 큰 값으로 인접행렬을 초기화시켜준다.
        for (int[] am : adjMatrix)
            Arrays.fill(am, MAX);
        // n 건물에서 다시 n 건물로 가는 경우 필요한 도로는 0
        for (int i = 0; i < n; i++)
            adjMatrix[i][i] = 0;

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken());

            // 도로가 주어질 경우, u -> v로 가는 길 자체는 항상 있다.
            adjMatrix[u][v] = 0;
            // b값에 따라, 0일 경우, adjMatrix[v][u] = 1, 그렇지 않을 경우 0을 넣어준다.
            adjMatrix[v][u] = b == 0 ? 1 : 0;
        }

        // 플로이드 와샬.
        for (int via = 0; via < n; via++) {
            for (int start = 0; start < n; start++) {
                if (start == via)
                    continue;

                for (int end = 0; end < n; end++) {
                    if (end == via || end == start)
                        continue;

                    // start -> via, via -> end 까지 어떠한 형태로든 도로가 있어야한다.
                    if (adjMatrix[start][via] != MAX && adjMatrix[via][end] != MAX &&
                            adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }

        // k개의 쿼리에 대해 플로이드 와샬로 처리된 결과값들을 출력해준다.
        int k = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1;
            int e = Integer.parseInt(st.nextToken()) - 1;

            sb.append(adjMatrix[s][e]).append("\n");
        }
        System.out.print(sb);
    }
}