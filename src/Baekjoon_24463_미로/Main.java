/*
 Author : Ruel
 Problem : Baekjoon 24463번 미로
 Problem address : https://www.acmicpc.net/problem/24463
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24463_미로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 미로가 주어진다.
        // . 길, + 막힌 벽을 의미한다.
        // 미로 가장자리에는 .이 항상 두 개만 주어지고, 하나에서 시작하여, 다른 하나로 나간다.
        // 이용하지 않은 길은 @로 표시한다.
        //
        // BFS 문제
        // BFS를 통해, 시작 지점으로부터 종료 지점까지 최단 경로를 찾으며
        // 경로를 찾은 후엔, 각 지점이 거리를 통해, 종료 지점부터 거꾸로 찾아가며 사용한 길을 표시한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 미로
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 시작 지점과 종료 지점
        int start = -1;
        int end = -1;
        for (int i = 0; i < map.length && end == -1; i++) {
            for (int j = 0; j < map[i].length && end == -1; j++) {
                if ((i == 0 || j == 0 || i == map.length - 1 || j == map[i].length - 1) && map[i][j] == '.') {
                    if (start == -1)
                        start = i * m + j;
                    else
                        end = i * m + j;
                }
            }
        }
        
        // BFS를 통해 시작 지점부터 종료 지점까지 최단 경로 계산
        int[][] dp = new int[n][m];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[start / m][start % m] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / m;
            int col = current % m;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC) && map[nextR][nextC] == '.' && dp[nextR][nextC] > dp[row][col] + 1) {
                    dp[nextR][nextC] = dp[row][col] + 1;
                    queue.offer(nextR * m + nextC);
                }
            }
        }
        
        // 종료 지점의 거리를 통해, 역추적하여 시작 지점까지 찾아간다.
        // 그러면서 사용한 길을 표시
        boolean[][] used = new boolean[n][m];
        used[end / m][end % m] = true;
        int distance = dp[end / m][end % m];
        int loc = end;
        while (distance > 0) {
            for (int d = 0; d < 4; d++) {
                int nextR = loc / m + dr[d];
                int nextC = loc % m + dc[d];

                if (checkArea(nextR, nextC) && dp[nextR][nextC] == distance - 1) {
                    used[nextR][nextC] = true;
                    distance--;
                    loc = nextR * m + nextC;
                }
            }
        }
        
        // 벽은 벽으로 표시하고, 이용한 길은 .
        // 이용하지 않은 길은 @로 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '+' || (map[i][j] == '.' && used[i][j]))
                    sb.append(map[i][j]);
                else
                    sb.append('@');
            }
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}