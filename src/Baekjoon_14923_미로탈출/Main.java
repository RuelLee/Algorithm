/*
 Author : Ruel
 Problem : Baekjoon 14923번 미로 탈출
 Problem address : https://www.acmicpc.net/problem/14923
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14923_미로탈출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 미로가 주어진다.
        // 미로는 벽과 빈 공간으로 이루어져있다.
        // 주인공은 지팡이가 단 한 번 벽을 부술 수 있다.
        // 시작 지점과 도착 지점에 주어질 때,
        // 도착 지점에 도달하는 최소 거리는?
        //
        // BFS 문제
        // 지팡이 사용 여부를 최소 거리에 포함시켜 너비 우선 탐색을 통해
        // 시작 지점에서 도착 지점에 도달하는 최소 거리를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 미로의 크기
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 시작 지점과 도착 지점
        int[][] points = new int[2][2];
        for (int i = 0; i < points.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < points[i].length; j++)
                points[i][j] = Integer.parseInt(st.nextToken()) - 1;
        }

        // 미로의 정보
        int[][] map = new int[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 지점에 도달하는 최소 거리
        int[][][] dp = new int[2][n][m];
        for (int[][] b : dp) {
            for (int[] d : b)
                Arrays.fill(d, Integer.MAX_VALUE);
        }
        // 시작 지점은 거리가 0
        dp[0][points[0][0]][points[0][1]] = 0;

        // 시작 지점에서부터 너비 우선 탐색을 시작한다.
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(points[0][0] * m + points[0][1]);
        while (!queue.isEmpty()) {
            // 지팡이 사용 여부
            int wand = queue.peek() / (n * m);
            // 현재의 행
            int row = queue.peek() % (n * m) / m;
            // 현재의 열
            int col = queue.poll() % (n * m) % m;

            // 사방 탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 다음 위치가 맵을 벗어나지 않으며
                if (checkArea(nextR, nextC, map)) {
                    // 빈 공간이라면
                    if (map[nextR][nextC] == 0) {
                        // 지팡이 정보를 그대로 가져가며
                        // (row, col)에서 (nextR, nextC)로 이동하는 거리가 최소인지 확인하고
                        // 그렇다면 값을 갱신하고 큐에 다음 위치를 넣는다.
                        if (dp[wand][nextR][nextC] > dp[wand][row][col] + 1) {
                            dp[wand][nextR][nextC] = dp[wand][row][col] + 1;
                            queue.offer(wand * m * n + nextR * m + nextC);
                        }
                    }
                    // 만약 다음 위치가 벽이지만 지팡이를 아직 사용 하지 않았고
                    // 다음 위치로 이동하는 거리가 최소라면
                    // 값을 갱신하고 큐에 추가한다.
                    else if (wand == 0 && dp[1][nextR][nextC] > dp[0][row][col] + 1) {
                        // 지팡이를 사용하므로 dp[1]에 기록
                        dp[1][nextR][nextC] = dp[0][row][col] + 1;
                        queue.offer(n * m + nextR * m + nextC);
                    }
                }
            }
        }

        // 지팡이를 사용했건 사용하지 않았건, 도착 지점에서의 최소 거리를 확인한다.
        int answer = Math.min(dp[0][points[1][0]][points[1][1]], dp[1][points[1][0]][points[1][1]]);
        // 만약 answer이 초기값 그대로라면 도착 지점에 도달할 수 없는 경우. -1 출력
        // 그렇지 않다면 answer를 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    // (r, c)가 map을 벗어나는지 확인한다.
    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}