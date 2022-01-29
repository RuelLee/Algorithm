/*
 Author : Ruel
 Problem : Baekjoon 7576번 토마토
 Problem address : https://www.acmicpc.net/problem/7576
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 토마토;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pos {
    int r;
    int c;

    public Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int[][] box;
    static int[][] minDays;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 토마토 상자가 주어진다
        // 안 익은 토마토는 0, 익은 토마토는 1, 빈 곳은 -1로 주어지며,
        // 익은 토마토의 상하좌우의 토마토들은 영향을 받아 하루가 지나면 익게 된다.
        // 모든 토마토가 익는데 소요되는 시일은 며칠인지 구하라(모두 익는게 불가능하다면 -1 출력)
        // 완전탐색문제. BFS를 통해 풀어주자!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int MAX = n * m;        // 소요 시일의 최대값

        box = new int[m][n];
        Queue<Pos> queue = new LinkedList<>();
        for (int i = 0; i < box.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < box[i].length; j++) {
                box[i][j] = Integer.parseInt(st.nextToken());
                if (box[i][j] == 1)     // 익은 토마토들이 있는 곳이 시작점.
                    queue.offer(new Pos(i, j));
            }
        }
        minDays = new int[m][n];
        for (int[] md : minDays)    // 초기값으로 세팅
            Arrays.fill(md, MAX);
        for (Pos p : queue)     // 이미 익은 토마토가 있는 곳은 소요 시일 0일.
            minDays[p.r][p.c] = 0;

        while (!queue.isEmpty()) {
            Pos current = queue.poll();     // 익은 토마토 위치를 꺼내,

            for (int d = 0; d < 4; d++) {       // 상하좌우 탐색.
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // box 범위를 벗어나지 않으며, 안 익은 토마토가 있으며, 숙성 시일이 current 위치의 토마토로 인해 더 적어질 경우
                if (checkArea(nextR, nextC) && box[nextR][nextC] == 0 && minDays[nextR][nextC] > minDays[current.r][current.c] + 1) {
                    // 숙성 시일 갱신.
                    minDays[nextR][nextC] = minDays[current.r][current.c] + 1;
                    // 큐에 넣어 다음 번에 탐색하도록 한다.
                    queue.offer(new Pos(nextR, nextC));
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < minDays.length; i++) {
            for (int j = 0; j < minDays[i].length; j++) {
                if (box[i][j] == 0) {       // 안 익은 토마토였던 곳이
                    if (minDays[i][j] == MAX) {     // 숙성되지 않은 채 남았다면 불가능한 경우.
                        answer = -1;
                        break;
                    } else      // 익었다면 소요 시일의 최대값을 갱신해나간다.
                        answer = Math.max(answer, minDays[i][j]);
                }
            }
            if (answer == -1)
                break;
        }
        // 최종적으로 남는 answer이 최대 소요 시일.
        System.out.println(answer);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < box.length && c >= 0 && c < box[r].length;
    }
}