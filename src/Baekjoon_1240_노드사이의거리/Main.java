/*
 Author : Ruel
 Problem : Baekjoon 1240번 노드사이의 거리
 Problem address : https://www.acmicpc.net/problem/1240
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1240_노드사이의거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] matrix;
    static int[][][] sparseArray;
    static int[] depths;

    public static void main(String[] args) throws IOException {
        // n개의 노드와 n-1개의 간선과 간선의 거리가 주어진다.
        // 그리고 m개의 노드 쌍이 주어질 때
        // 노드 쌍의 거리를 출력하라.
        //
        // 트리와 희소배열을 이용한 문제
        // 문제 자체는 시간이 넉넉한 편이라 희소 배열을 사용하지 않아도 괜찮지만
        // 시간 상 많은 이점이 있는 희소 배열을 사용하지 않을 이유는 없다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 간선의 정보 저장.
        matrix = new int[n + 1][n + 1];
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            matrix[a][b] = matrix[b][a] = c;
        }

        // 희소배열
        // n개의 노드가 있다면 최대 깊이가 n까지의 편향트리로 주어질 수도 있다.
        // 따라서 그 때의 최대 크기로 선언해주자.
        sparseArray = new int[n + 1][(int) Math.ceil(Math.log(n) / Math.log(2)) + 1][2];
        Queue<Integer> queue = new LinkedList<>();
        // 1은 항상 주어지므로, 1을 루트로 가정하고 풀자.
        queue.offer(1);
        boolean[] visited = new boolean[n + 1];
        depths = new int[n + 1];
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 방문 체크
            visited[current] = true;
            // 부모의 깊이보다 하나 더 깊다.
            depths[current] = depths[sparseArray[current][0][0]] + 1;
            // 깊이에 따라 희소 배열을 채워나간다.
            int ancestors = (int) Math.ceil(Math.log(depths[current] - 1) / Math.log(2));
            for (int i = 1; i <= ancestors; i++) {
                // current의 2^i번째 조상은 current의 2^(i-1)번째 조상의 2^(i-1)번째 조상과 같다.
                sparseArray[current][i][0] = sparseArray[sparseArray[current][i - 1][0]][i - 1][0];
                // current -> 2^(i-1)번째 조상 -> 2^i번째 까지의 조상까지의 거리를 구한다.
                sparseArray[current][i][1] = sparseArray[current][i - 1][1] + sparseArray[sparseArray[current][i - 1][0]][i - 1][1];
            }

            for (int i = 1; i < matrix[current].length; i++) {
                // current와 i 사이에 간선이 없거나 이미 방문한 적이 있다면 건너뛴다.
                if (matrix[current][i] == 0 || visited[i])
                    continue;

                // i의 조상으로 current 기록
                sparseArray[i][0][0] = current;
                // 그 때의 거리 기록.
                sparseArray[i][0][1] = matrix[current][i];
                // 큐에 i 삽입.
                queue.offer(i);
            }
        }

        // m개의 쿼리를 처리한다.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            sb.append(findMinCost(a, b)).append("\n");
        }
        // 최종 답안 출력.
        System.out.print(sb);
    }

    // 희소 배열을 통해 a, b 노드 사이의 거리를 구한다.
    static int findMinCost(int a, int b) {
        int costSum = 0;
        // 서로 깊이가 다르다면
        while (depths[a] != depths[b]) {
            // 희소배열을 통해 2^x만큼 조상을 거슬러 올라간다.
            int diff = Math.abs(depths[a] - depths[b]);
            // 차이의 log2값을 취해 2의 몇제곱 조상으로 거슬러 올라갈지 정한다.
            int jump = (int) (Math.log(diff) / Math.log(2));

            // 깊이가 더 큰 쪽을 조상으로 올려보낸다.
            // 올라가는 쪽의 거리를 더한다.
            if (depths[a] < depths[b]) {
                costSum += sparseArray[b][jump][1];
                b = sparseArray[b][jump][0];
            } else {
                costSum += sparseArray[a][jump][1];
                a = sparseArray[a][jump][0];
            }
        }

        // 위 과정이 끝났다면 깊이는 같아졌다.
        // 따라서 a, b 노드가 같아질 때까지 하나씩 조상을 거슬러 올라간다.
        // 그러면서 비용도 더한다.
        while (a != b) {
            costSum += sparseArray[a][0][1];
            costSum += sparseArray[b][0][1];
            a = sparseArray[a][0][0];
            b = sparseArray[b][0][0];
        }
        // 최종 거리합을 반환한다.
        return costSum;
    }
}