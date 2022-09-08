/*
 Author : Ruel
 Problem : Baekjoon 13424번 비밀 모임
 Problem address : https://www.acmicpc.net/problem/13424
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13424_비밀모임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스
        // n개의 방, m개의 통로가 주어진다
        // m개의 줄에 a b c 형태로 a와 b를 잇는 거리 c의 양방향 통로가 주어진다.
        // 그리고 k명의 친구와 그 위치가 주어질 때
        // 친구들이 모이는데 필요한 거리의 총합이 최소인 방을 구하라
        //
        // 모든 방에 대해 탐색해야하므로, 플로이드-와샬 알고리즘을 적용해보자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            // 인접 행렬로 각 방에 이르는 통로의 거리를 저장해둔다.
            // 큰 값으로 초기화.
            int[][] adjMatrix = new int[n][n];
            for (int i = 0; i < adjMatrix.length; i++) {
                Arrays.fill(adjMatrix[i], Integer.MAX_VALUE);
                adjMatrix[i][i] = 0;
            }

            // 두 방을 잇는 통로를 인접 행렬에 저장.
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;
                int c = Integer.parseInt(st.nextToken());

                adjMatrix[a][b] = adjMatrix[b][a] = Math.min(adjMatrix[a][b], c);
            }

            // 플로이드 와샬
            for (int via = 0; via < adjMatrix.length; via++) {
                for (int start = 0; start < adjMatrix.length; start++) {
                    if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                        continue;

                    for (int end = 0; end < adjMatrix.length; end++) {
                        if (end == via || end == start ||
                                adjMatrix[via][end] == Integer.MAX_VALUE)
                            continue;

                        if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                            adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                    }
                }
            }

            // 모여야하는 k명의 친구들.
            int k = Integer.parseInt(br.readLine());
            int[] friends = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // 모든 방을 살펴보며, 각 방에 모든 친구가 모이는 최소 거리가 작은 방을 찾는다.
            int room = -1;
            int distance = Integer.MAX_VALUE;
            for (int i = 0; i < adjMatrix.length; i++) {
                // i방에 모이는 친구들 거리의 합 sum.
                int sum = 0;
                // 각 친구들이 i방에 모이는 거리를 더한다.
                for (int friend : friends)
                    sum += adjMatrix[i][friend - 1];
                // i방에 모이는 최소 거리 합이 방들 중 최소인지 확인한다.
                if (distance > sum) {
                    room = i + 1;
                    distance = sum;
                }
            }
            // 최종적으로 모이는 거리가 최소인 room을 출력한다.
            sb.append(room).append("\n");
        }
        System.out.print(sb);
    }
}