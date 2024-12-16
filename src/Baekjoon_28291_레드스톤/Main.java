/*
 Author : Ruel
 Problem : Baekjoon 28291번 레드스톤
 Problem address : https://www.acmicpc.net/problem/28291
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28291_레드스톤;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int w, h;

    public static void main(String[] args) throws IOException {
        // w * h 크기의 맵을 만들었다.
        // 블록에는 레드스톤 가루, 레드스톤 블록, 레드스톤 램프가 있다.
        // 레드스톤 가루 : 상하좌우로 인접한 회로에 매초마다 전기 신호를 전달한다.
        // 목적지에 더 큰 전기신호가 있을 경우, 전달하지 않으며, 전기 신호의 세기는 1 감소한다.
        // 레드스톤 블록 : 상하좌우 인접한 블록에 15만큼의 전기 신호를 전달한다
        // 레드스톤 램프 : 1 이상의 전기 신호를 받은 경우, 불이 켜진다.
        // 맵의 정보가 주어질 때
        // 맵에 존재하는 모든 램프들이 켜지는지 확인하라
        //
        // BFS 문제
        // 주어진대로, 맵을 구현한 후, 블록에서부터 BFS를 통해 탐색하여
        // 전기 신호가 사라지기 전에 램프에 닿을 수 있는지 확인한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // w * h 크기의 맵
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        
        // 맵
        char[][] map = new char[w][h];
        int n = Integer.parseInt(br.readLine());
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String type = st.nextToken();
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            switch (type.charAt(type.length() - 1)) {
                // 레드스톤 블록인 경우
                case 'k' -> {
                    map[x][y] = 'b';
                    queue.offer(x * h + y);
                }
                // 레드스톤 가루인 경우
                case 't' -> map[x][y] = 'd';
                // 레드스톤 램프인 경우
                case 'p' -> map[x][y] = 'l';
            }
        }

        int[][] dp = new int[w][h];
        // 레드스톤 블록은 상하좌우로 15만큼 전달하므로,
        // 자기 블록은 16을 갖고 있다고 생각한다.
        for (int start : queue)
            dp[start / h][start % h] = 16;
        while (!queue.isEmpty()) {
            // 현재 위치
            int current = queue.poll();
            int x = current / h;
            int y = current % h;
            // 만약 현재 값이 1이라면 더 이상 전달할 수 없으므로
            // 건너뛴다.
            if (dp[x][y] == 1)
                continue;
            
            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextX = x + dx[d];
                int nextY = y + dy[d];
                
                // 맵 범위를 벗어나지 않으며
                // 레드스톤 더스트 혹은 레드스톤 램프이며,
                // 갖고 있는 전기 신호가 (x, y)에서 전달하는 것이 더 강할 경우
                if (checkArea(nextX, nextY) && (map[nextX][nextY] == 'd' || map[nextX][nextY] == 'l' ) &&
                        dp[x][y] - 1 > dp[nextX][nextY]) {
                    // 해당 값으로 전기 신호값 수정
                    dp[nextX][nextY] = dp[x][y] - 1;
                    // 램프는 전달할 수 없으므로
                    // 레드스톤 가루일 경우에만 큐에 추가
                    if (map[nextX][nextY] == 'd' )
                        queue.offer(nextX * h + nextY);
                }
            }
        }

        // 맵을 전부 살펴보며
        // 모든 램프에 전달된 전기 신호값이 1이상인지 확인한다.
        boolean success = true;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (map[i][j] == 'l' && dp[i][j] == 0) {
                    success = false;
                    break;
                }
            }
        }
        // 답 출력
        System.out.println(success ? "success" : "failed");
    }

    static boolean checkArea(int x, int y) {
        return x >= 0 && x < w && y >= 0 && y < h;
    }
}