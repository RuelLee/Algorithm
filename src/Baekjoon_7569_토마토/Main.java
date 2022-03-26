/*
 Author : Ruel
 Problem : Baekjoon 7569번 토마토
 Problem address : https://www.acmicpc.net/problem/7569 
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7569_토마토;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pos {
    int f;
    int r;
    int c;

    public Pos(int f, int r, int c) {
        this.f = f;
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int[][][] box;
    static int[] dr = {-1, 0, 1, 0, 0, 0};
    static int[] dc = {0, 1, 0, -1, 0, 0};
    static int[] df = {0, 0, 0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        // 3차원 좌표로 토마토가 주어진다
        // 익은 토마토와 안 익은 토마토가 섞여있는데, 하루가 지날 때마다
        // 익은 토마토의 같은 층의 4방과 같은 좌표의 위아래 총 6방향의 안 익은 토마토는 익는다고 한다
        // 모든 토마토가 읻는데 걸리는 시간은?
        //
        // 시뮬레이션 문제
        // 2차원 문제하고 기본적으로 동일하게 풀면 된다!
        // 현재 익어있는 상태의 토마토들의 위치를 받고, 6방향에 위치한 안 익은 토마토가 위치한다면 해당 토마토들의 위치를 저장하고 익었다고 표시한다.
        // 날짜를 증가시키고 전날 전날에 새로 추가한 막 익은 토마토들로 부터 6방 탐색을 계속해나간다.
        // 큐가 비거나, 모든 토마토가 익는다면 종료!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        box = new int[h][n][m];
        int unripeTomatoes = 0;
        Queue<Pos> ripeTomatoes = new LinkedList<>();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < m; k++) {
                    box[i][j][k] = Integer.parseInt(st.nextToken());

                    if (box[i][j][k] == 1)      // 익은 토마토의 위치 queue에 담아둔다.
                        ripeTomatoes.offer(new Pos(i, j, k));
                    else if (box[i][j][k] == 0)     // 안 익은 토마토의 개수를 센다.
                        unripeTomatoes++;
                }
            }
        }

        int days = 0;
        while (!ripeTomatoes.isEmpty() && unripeTomatoes > 0) {     // 새로운 익은 토마토들이 있고, 아직 안 익은 토마토가 남았다면
            Queue<Pos> nextTurn = new LinkedList<>();
            while (!ripeTomatoes.isEmpty() && unripeTomatoes > 0) {
                Pos current = ripeTomatoes.poll();
                for (int d = 0; d < 6; d++) {       // 6방향 탐색
                    int nextF = current.f + df[d];
                    int nextR = current.r + dr[d];
                    int nextC = current.c + dc[d];

                    // 범위를 벗어나지 않고, 안익은 토마토가 인근에 위치한다면
                    if (checkArea(nextF, nextR, nextC) && box[nextF][nextR][nextC] == 0) {
                        box[nextF][nextR][nextC] = 1;       // 익은 토마토로 표시.
                        unripeTomatoes--;       // 안 익은 토마토 수 감소.
                        nextTurn.offer(new Pos(nextF, nextR, nextC));       // 큐에 저장
                    }
                }
            }
            // 막 인은 토마토들을 다음 턴에 살펴본다.
            ripeTomatoes = nextTurn;
            // 날짜 증가.
            days++;
        }
        // 큐가 비거나, 모든 토마토가 익었다면 여기까지 도달할 수 있다
        // 안 익은 토마토가 있다면, 모든 토마토를 익히는게 불가능한 경우. -1 출력
        // 모든 토마토가 익었다면 그 떄의 날짜인 days 출력.
        System.out.println(unripeTomatoes > 0 ? -1 : days);
    }

    static boolean checkArea(int f, int r, int c) {
        return f >= 0 && f < box.length && r >= 0 && r < box[f].length && c >= 0 && c < box[f][r].length;
    }
}