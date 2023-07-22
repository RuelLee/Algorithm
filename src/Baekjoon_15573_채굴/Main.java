/*
 Author : Ruel
 Problem : Baekjoon 15573번 채굴
 Problem address : https://www.acmicpc.net/problem/15573
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15573_채굴;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] mineral;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 세로 n, 가로 m 크기의 광물이 주어진다.
        // 채굴기는 공기와 맞닿아있는 광물을 하나 캘 수 있다.
        // 각 광물의 강도와 채굴기의 성능이 주어질 때
        // 강도 <= 성능인 경우만 캘 수 있다.
        // 원하는 광물을 k개 이상 채굴할 수 있는 최소 성능 d를 구하라
        //
        // 이분 탐색, BFS 문제
        // 이분 탐색을 통해 1 ~ 1000000 까지 범위에서 성능을 찾는다.
        // 각 성능으로 캘 수 있는 광물의 수는 BFS를 통해 센다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 광물과 캐야하는 최소 개수 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 공기 중을 표현할 공간을 가로 한 줄과 세로 두 줄을 추가한다.
        mineral = new int[n + 1][m + 2];
        for (int i = 1; i < mineral.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < mineral[i].length - 1; j++)
                mineral[i][j] = Integer.parseInt(st.nextToken());
        }

        // d를 1 ~ 1000000 범위에서 찾는다.
        int start = 1;
        int end = 1000000;
        while (start <= end) {
            int mid = (start + end) / 2;
            // mid의 강도로 채굴할 때 k개 미만이 채굴된다면
            // start의 범위를 mid + 1로 높여준다.
            if (mining(mid) < k)
                start = mid + 1;
            // 만약 k개 이상이 채굴됐다면 end를 mid - 1로 낮춰준다.
            else
                end = mid - 1;
        }
        // 찾은 최소 값 출력
        System.out.println(start);
    }
    
    // BFS
    static int mining(int strength) {
        // 방문 체크
        boolean[][] visited = new boolean[mineral.length][mineral[0].length];
        
        // 캔 미네랄의 수
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0][0] = true;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 위치
            int row = current / mineral[0].length;
            int col = current % mineral[0].length;
            
            // 4방 탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];
                
                // 맵 범위를 벗어나지 않고, 방문하지 않았고
                // 강도가 성능이하라면 캘 수 있다.
                if (checkArea(nextR, nextC) && !visited[nextR][nextC] && mineral[nextR][nextC] <= strength) {
                    // 방문 체크
                    visited[nextR][nextC] = true;
                    // 큐 추가
                    queue.offer(nextR * mineral[0].length + nextC);
                    // 만약 미네랄이 맞다면(= 공기가 아니라면)
                    // count 증가
                    if (mineral[nextR][nextC] > 0)
                        count++;
                }
            }
        }
        return count;
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < mineral.length && c >= 0 && c < mineral[r].length;
    }
}