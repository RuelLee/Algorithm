/*
 Author : Ruel
 Problem : Jungol 3944번 Switching on the Lights
 Problem address : https://jungol.co.kr/problem/3944
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3944_SwitchingOnTheLights;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자 칸이 주어지고, m개의 스위치에 대한 정보가 주어진다.
        // 각 방에는 전구가 달려있고, 밝은 방에만 갈 수 있으며, (1, 1)에는 처음부터 밝혀져있다.
        // 이동은 인접한 상하좌우칸으로 이동할 수 있다.
        // 스위치에 대한 정보가 x y a b로 주어지는데 (x, y)칸에 있는 스위치가 (a, b) 칸에 있는 방을 밝힌다는 듯이다.
        // 최대한 많은 방을 밝히고자 할 때, 그 개수는?
        //
        // bfs 문제
        // bfs인데, 방문 체크와 밝힌 방의 체크를 구별해서 해야한다.
        // 먼저, 방에 방문하면 스위치들을 모두 켜며, 스위치가 밝혀진 방에서 인접한 곳에 이미 방문한 칸이 있는지 확인한다.
        // 있다면, 이번에 스위치로 밝혀진 방에 접근이 가능하고, 해당 칸을 큐에 추가한다.
        // 그 후, 현재 칸에서 인접한 칸들을 살펴보고, 불이 밝혀져있고 미방문한 칸이 있다면 큐에 추가하여 방문한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 격자, m개의 스위친
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 스위치 정보
        List<int[]>[][] switches = new List[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                switches[i][j] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            switches[x][y].add(new int[]{a, b});
        }

        // 불이 밝혀진 방
        boolean[][] lighted = new boolean[N][N];
        lighted[0][0] = true;
        // 방문 여부
        boolean[][] visited = new boolean[N][N];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            // 현재 위치
            int[] current = queue.poll();
            // 방문 체크
            visited[current[0]][current[1]] = true;

            // 스위치를 켬
            for (int[] light : switches[current[0]][current[1]]) {
                if (!lighted[light[0]][light[1]]) {
                    // 스위치를 켜고
                    lighted[light[0]][light[1]] = true;
                    for (int d = 0; d < 4; d++) {
                        int nearR = light[0] + dr[d];
                        int nearC = light[1] + dc[d];

                        // 인접한 칸들을 살펴봐 방문한 칸이 있다면, 현재 light 칸에도 접근 가능
                        if (checkArea(nearR, nearC) && visited[nearR][nearC])
                            queue.offer(new int[]{light[0], light[1]});
                    }
                }
            }

            // 현재 칸에서 인접한 네 칸을 살펴봄
            for (int d = 0; d < 4; d++) {
                int nearR = current[0] + dr[d];
                int nearC = current[1] + dc[d];

                // 미방문이며 불이 밝혀진 칸이 있다면 방문 가능
                if (checkArea(nearR, nearC) && !visited[nearR][nearC] && lighted[nearR][nearC])
                    queue.offer(new int[]{nearR, nearC});
            }
        }

        // 불이 밝혀진 칸들을 셈
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (lighted[i][j])
                    cnt++;
            }
        }
        // 답 출력
        System.out.println(cnt);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}