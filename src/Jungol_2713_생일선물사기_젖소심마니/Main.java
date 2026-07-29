/*
 Author : Ruel
 Problem : Jungol 2713번 생일선물사기(젖소 심마니)
 Problem address : https://jungol.co.kr/problem/2713
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2713_생일선물사기_젖소심마니;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int w, h;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // h * w 크기의 격자가 주어진다.
        // 0은 길, 1은 막힌 곳, 2는 서연의 위치, 3은 은비와의 약속 장소, 4 상점
        // 서연은 상점에서 물건을 사 약속 장소로 가고자 한다.
        // 물건을 사기 전엔 3번 장소를 통과할 수 없다.
        // 최소 거리를 구하라
        //
        // BFS 그래프 탐색 문제
        // 서연 -> 상점 -> 약속 장소로 이동하지만
        // 서연 -> 상점 은 한 장소에서 시작해서 여러 장소로 갈라지고
        // 상점 -> 약속 장소는 여러 장소에서 시작해서 한 장소로 모인다.
        // 따라서 서연 -> 상점 <- 약속과 같이
        // 서연에서 상점까지의 거리, 약속 장소에서 상점까지의 거리를 구하면 한 장소 -> 여러 장소로 탐색이 가능하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 맵의 크기
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        int[][] map = new int[h][w];
        // 서연의 위치, 약속 장소
        int[] points = new int[2];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

                if (map[i][j] == 2)
                    points[0] = i * w + j;
                else if (map[i][j] == 3)
                    points[1] = i * w + j;
            }
        }

        // 서연 -> 상점
        int[][] minDistanceFrom2 = new int[h][w];
        for (int[] md : minDistanceFrom2)
            Arrays.fill(md, Integer.MAX_VALUE);
        minDistanceFrom2[points[0] / w][points[0] % w] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(points[0]);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int r = current / w;
            int c = current % w;

            for (int d = 0; d < 4; d++) {
                int nextR = dr[d] + r;
                int nextC = dc[d] + c;

                if (checkArea(nextR, nextC) && map[nextR][nextC] != 1 && map[nextR][nextC] != 3 &&
                        minDistanceFrom2[nextR][nextC] > minDistanceFrom2[r][c] + 1) {
                    minDistanceFrom2[nextR][nextC] = minDistanceFrom2[r][c] + 1;
                    queue.offer(nextR * w + nextC);
                }
            }
        }

        // 약속 장소 -> 상점
        int[][] minDistanceFrom3 = new int[h][w];
        for (int[] md : minDistanceFrom3)
            Arrays.fill(md, Integer.MAX_VALUE);
        minDistanceFrom3[points[1] / w][points[1] % w] = 0;
        queue.offer(points[1]);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int r = current / w;
            int c = current % w;

            for (int d = 0; d < 4; d++) {
                int nextR = r + dr[d];
                int nextC = c + dc[d];

                if (checkArea(nextR, nextC) && map[nextR][nextC] != 1 && map[nextR][nextC] != 2 &&
                        minDistanceFrom3[nextR][nextC] > minDistanceFrom3[r][c] + 1) {
                    minDistanceFrom3[nextR][nextC] = minDistanceFrom3[r][c] + 1;
                    queue.offer(nextR * w + nextC);
                }
            }
        }

        // 상점에서 모이는 거리들 중 최소 값을 찾는다.
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (minDistanceFrom2[i][j] == Integer.MAX_VALUE ||
                        minDistanceFrom3[i][j] == Integer.MAX_VALUE)
                    continue;
                else if (map[i][j] == 4)
                    answer = Math.min(answer, minDistanceFrom2[i][j] + minDistanceFrom3[i][j]);
            }
        }
        // 답 출력
        System.out.println(answer);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}