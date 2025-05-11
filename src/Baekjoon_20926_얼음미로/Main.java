/*
 Author : Ruel
 Problem : Baekjoon 20926번 얼음 미로
 Problem address : https://www.acmicpc.net/problem/20926
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20926_얼음미로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int w, h;

    public static void main(String[] args) throws IOException {
        // 가로 w, 세로 h 크기의 얼음 미로가 주어진다.
        // 얼음 미로에서는 한 방향으로 이동 시 미끄러져 계속 이동한다.
        // 그 때 칸에 적힌 수만큼 이동 시간이 증가한다.
        // 칸에는 수 말고도 'T' -> 주인공, 'R' -> 바위, 'H' -> 구멍, 'E' -> 출구 가 적혀있으며
        // T의 위치는 이동 시간이 0이다.
        // 바위에 부딪친 경우 그 앞에서 멈추고, 구멍이나 맵 밖으로 나간 경우, 절벽이기 때문에 탈출할 수 없다.
        // 최종적으로 가장 적은 시간에 E로 도달해야한다.
        // 그 때의 탈출 시간을 출력하라
        //
        // 시뮬레이션, 최단 경로 문제
        // 현재 위치로부터 각 턴마다 한 방향으로 읻오하여 바위 앞 혹은 출구로만 나갈 수 있다.
        // 각 자리에 도달하는 어떤 방법으로 오느냐에 따라 다를 수 있으므로 각 지점에 도달하는 최소 시간을 기록하고
        // 우선순위큐를 통해 도달 시간이 빠른 지점부터 처리해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 가로 w, 세로 h 크기의 얼음 미로
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        
        char[][] map = new char[h][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 각 지점에 도달하는 최소 시간
        // T의 위치는 0, 나머지는 큰 값
        int[][] dp = new int[h][w];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> dp[o / w][o % w]));
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 시작 위치를 한 번 찾았다면, 우선 순위 큐에 담고
                // 그 값을 0으로 대체해주자.
                if (map[i][j] == 'T') {
                    dp[i][j] = 0;
                    priorityQueue.offer(i * w + j);
                    map[i][j] = '0';
                } else
                    dp[i][j] = Integer.MAX_VALUE;
            }
        }
        
        // 출구에 도달한 최소 시간
        int answer = Integer.MAX_VALUE;
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            // 현재 위치
            int row = current / w;
            int col = current % w;

            // 4방향 중 한 방향으로 계속 이동해야한다.
            for (int d = 0; d < 4; d++) {
                // 다음 위치
                int nextR = row;
                int nextC = col;
                // 총 이동 시간
                int sum = dp[row][col];
                // 더 이동할 수 있는가
                boolean remain = true;
                // 맵을 벗어나지 않고, 아직 더 이동할 수 있는 동안
                // 한 방향으로 계속 이동한다.
                while (checkArea(nextR + dr[d], nextC + dc[d]) && remain) {
                    switch (map[nextR + dr[d]][nextC + dc[d]]) {
                        // 구멍이라면 해당 방향으로 이동해선 안된다.
                        // 그대로 종료
                        case 'H' -> remain = false;
                        // 바위에 부딪친 경우
                        // 바위 앞인 현재 위치에서 멈춘 후, 이동 시간을 비교한 후
                        // 최솟값을 갱신한 경우, 우선순위큐에 추가
                        case 'R' -> {
                            if (dp[nextR][nextC] > sum) {
                                dp[nextR][nextC] = sum;
                                priorityQueue.offer(nextR * w + nextC);
                            }
                            remain = false;
                        }
                        // 탈출한 경우
                        // 탈출 최소 시간을 갱신하는지 확인
                        case 'E' -> {
                            if (answer > sum)
                                answer = sum;
                            remain = false;
                        }
                        // 그 외의 수가 적힌 칸인 경우
                        // 이동 시간을 누적.
                        default -> {
                            nextR += dr[d];
                            nextC += dc[d];
                            sum += map[nextR][nextC] - '0';
                        }
                    }
                }
            }
        }
        // answer가 초기값이라면 탈출이 불가능한 경우이므로 -1을 출력
        // 그 외의 경우 최소 탈출 시간 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
    
    // 맵의 범위를 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}