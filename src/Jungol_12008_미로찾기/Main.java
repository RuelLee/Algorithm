/*
 Author : Ruel
 Problem : Jungol 12008번 미로찾기
 Problem address : https://jungol.co.kr/problem/12008
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12008_미로찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int h, w;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // h * w 크기의 격자가 주어진다.
        // #은 벽, .은 길, 출발점 S, 도착점 G로 주어진다. 상하좌우 인접한 곳으로 움직일 수 있다.
        // 출발점에서 도착점에 가는 최소 거리는?
        //
        // BFS 문제
        // 시작점에서부터 시작하여, BFS 사방탐색으로 출발점까지의 거리를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // h * w 크기의 미로
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        // 미로
        char[][] map = new char[h][w];
        // 출발점과 도착점
        int[] points = new int[2];
        for (int i = 0; i < h; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < w; j++) {
                if (map[i][j] == 'S')
                    points[0] = i * w + j;
                else if (map[i][j] == 'G')
                    points[1] = i * w + j;
            }
        }

        // 각 격자에 다다르는 최소 거리
        int[][] minDistances = new int[h][w];
        for (int[] md : minDistances)
            Arrays.fill(md, Integer.MAX_VALUE);
        // 출발점
        minDistances[points[0] / w][points[0] % w] = 0;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(points[0]);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 위치
            int row = current / w;
            int col = current % w;

            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 인접한 지나오지 않은 길인 경우
                if (checkArea(nextR, nextC) && map[nextR][nextC] != '#' && minDistances[nextR][nextC] > minDistances[row][col] + 1) {
                    minDistances[nextR][nextC] = minDistances[row][col] + 1;
                    queue.offer(nextR * w + nextC);
                }
            }
        }
        // 도착점에서의 거리 출력
        System.out.println(minDistances[points[1] / w][points[1] % w]);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}