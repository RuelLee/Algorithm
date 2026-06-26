/*
 Author : Ruel
 Problem : Jungol 2613번 토마토(고)
 Problem address : https://jungol.co.kr/problem/2613
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2613_토마토_고;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자 칸 안에 익은 토마토 혹은 익지 않은 토마토 혹은 비어있다.
        // 익은 토마토들은 상하좌우 칸의 익지 않은 토마토를 하루 동안 익게 만든다.
        // 모든 토마토가 익는 일을 구하라
        //
        // BFS 문제
        // 익은 토마토를 기준으로 상하좌우를 탐색하며 익는 날짜들을 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        // 각 칸의 토마토들
        int[][] tomatos = new int[n][m];
        // 익는 날짜
        int[][] ripeDays = new int[n][m];
        for (int[] rd : ripeDays)
            Arrays.fill(rd, Integer.MAX_VALUE);
        // BFS 탐색을 위한 큐
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                // 해당 칸이 익은 토마토라면
                // 큐에 추가하고 ripeDays를 0으로 설정
                if ((tomatos[i][j] = Integer.parseInt(st.nextToken())) == 1) {
                    queue.offer(i * m + j);
                    ripeDays[i][j] = 0;
                }
            }
        }

        // BFS 탐색
        int maxDay = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / m;
            int col = current % m;

            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 범위 내이고, 익지 않은 토마토이며, 최소 익는 날짜를 갱신하는 경우
                // 익는 날짜 갱신, 익은 토마토 표시, 큐 추가, 최대 익는 날짜 갱신
                if (checkArea(nextR, nextC) && tomatos[nextR][nextC] == 0 && ripeDays[nextR][nextC] > ripeDays[row][col] + 1) {
                    ripeDays[nextR][nextC] = ripeDays[row][col] + 1;
                    tomatos[nextR][nextC] = 1;
                    queue.offer(nextR * m + nextC);
                    maxDay = Math.max(maxDay, ripeDays[nextR][nextC]);
                }
            }
        }

        // 익지 않은 토마토가 있는지 확인
        boolean allRipe = true;
        for (int i = 0; i < n && allRipe; i++) {
            for (int j = 0; j < m && allRipe; j++) {
                if (tomatos[i][j] == 0)
                    allRipe = false;
            }
        }
        // 모두 익었다면 날짜를, 그렇지 않다면 -1을 출력
        System.out.println(allRipe ? maxDay : -1);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}