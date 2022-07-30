/*
 Author : Ruel
 Problem : Baekjoon 1600번 말이 되고픈 원숭이
 Problem address : https://www.acmicpc.net/problem/1600
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1600_말이되고픈원숭이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class State {
    int r;
    int c;
    int k;

    public State(int r, int c, int k) {
        this.r = r;
        this.c = c;
        this.k = k;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0, -2, -1, 1, 2, 2, 1, -1, -2};
    static int[] dc = {0, 1, 0, -1, 1, 2, 2, 1, -1, -2, -2, -1};

    public static void main(String[] args) throws IOException {
        // h, w 크기의 맵이 주어진다. 0은 이동할 수 있는 위치이고, 1은 돌이 이동할 수 있다.
        // 원숭이는 사방으로 한칸씩 이동하거나, k번 이하로 체스의 나이트처럼 이동할 수 있다.
        // 원숭이가 (1, 1)에서 출발해서 , (h, w)에 도달하는 최소 이동 횟수를 구하라.
        //
        // BFS 문제.
        // 좌표와 k에 따라 각 위치에 도달하는 최소 턴 수를 기록해나가자. 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 나이트처럼 뛸 수 있는 횟수.
        int k = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 가로
        int w = Integer.parseInt(st.nextToken());
        // 세로
        int h = Integer.parseInt(st.nextToken());

        // 맵의 상황을 입력 받는다. 돌이 있는 곳은 1.
        int[][] map = new int[h][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 지점에 도달하는 최소 횟수.
        int[][][] dp = new int[h][w][k + 1];
        for (int[][] cols : dp) {
            for (int[] ks : cols)
                Arrays.fill(ks, Integer.MAX_VALUE);
        }
        // 처음 시작은 0턴.
        dp[0][0][k] = 0;

        // 큐에 시작위치와 k를 넣자.
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(0, 0, k));
        // 큐가 빌 때까지.
        while (!queue.isEmpty()) {
            // 현재 상태.
            State current = queue.poll();

            // 만약 k가 0보다 크다면 사방 + 나이트 이동
            // k가 0이라면 사방으로만 이동한다.
            int delta = current.k > 0 ? 12 : 4;
            for (int d = 0; d < delta; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 다음 위치가 맵 안에 있고, 돌이 아닌 위치라면
                if (checkArea(nextR, nextC, map) && map[nextR][nextC] != 1) {
                    // 사방 이동일 때는 k가 감소하지 않고, 턴만 하나 증가된 상태랑 비교한다.
                    if (d < 4 && dp[nextR][nextC][current.k] > dp[current.r][current.c][current.k] + 1) {
                        dp[nextR][nextC][current.k] = dp[current.r][current.c][current.k] + 1;
                        queue.offer(new State(nextR, nextC, current.k));
                        // 나이트 이동일 때는 k가 하나 감소하고, 턴이 하나 증가된 상태랑 비교한다.
                    } else if (d >= 4 && dp[nextR][nextC][current.k - 1] > dp[current.r][current.c][current.k] + 1) {
                        dp[nextR][nextC][current.k - 1] = dp[current.r][current.c][current.k] + 1;
                        queue.offer(new State(nextR, nextC, current.k - 1));
                    }
                }
            }
        }

        // 도착점의 위치에서 k값에 상관없이 가장 작은 값을 찾는다.
        int min = Arrays.stream(dp[h - 1][w - 1]).min().getAsInt();
        // 만약 초기값 그대로라면 도달이 불가능한 경우. -1 출력.
        // 아니라면 min 값을 출력한다.
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}