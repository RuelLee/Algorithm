/*
 Author : Ruel
 Problem : Baekjoon 1445번 일요일 아침의 데이트
 Problem address : https://www.acmicpc.net/problem/1445
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1445_일요일아침의데이트;

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
        // n * m 크기의 맵이 주어진다.
        // S는 시작 지점, F는 도착 지점, .은 빈 공간, g는 쓰레기가 있는 곳이다.
        // S에서 출발하여 F로 도달하는데 가능한 쓰레기는 통과하지 않으며, 쓰레기 근처도 지나가지 않으려고 한다.
        // 만약 쓰레기를 통과하는 경우가 여러개라면 쓰레기 옆을 지나가는 칸의 개수를 최소로 지나려고 한다.
        // 이 때 두 값을 출력하라
        //
        // 완전 탐색 문제
        // BFS 문제와 동일.
        // 쓰레기를 통과하는 횟수와 더불어 쓰레기 근처를 밟는 경우를 같이 고려해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 맵을 살펴보며 시작 지점과
        int start = -1;
        // 도착 지점
        int end = -1;
        // 근처에 쓰레기 유무를 체크한다
        boolean[][] nearbyTrash = new boolean[n][m];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 시작 지점
                if (map[i][j] == 'S')
                    start = i * m + j;
                // 도착 지점
                else if (map[i][j] == 'F')
                    end = i * m + j;
                // 현 위치가 쓰레기라면
                else if (map[i][j] == 'g') {
                    for (int d = 0; d < 4; d++) {
                        int nearR = i + dr[d];
                        int nearC = j + dc[d];

                        // 사방에 쓰레기 근처라고 표시한다.
                        // 단 S, F는 세지 않는다고 했으므로, F나 S는 쓰레기 근처더라도 표시 하지 않는다.
                        if (checkArea(nearR, nearC, map) && map[nearR][nearC] != 'F' && map[nearR][nearC] != 'S')
                            nearbyTrash[nearR][nearC] = true;
                    }
                }
            }
        }

        // 쓰레기 통과 횟수와 쓰레기 근처를 지나는 횟수를 센다.
        int[][][] minTrashes = new int[n][m][2];
        // 값 초기화
        for (int[][] row : minTrashes) {
            for (int[] trashes : row)
                Arrays.fill(trashes, Integer.MAX_VALUE);
        }
        minTrashes[start / m][start % m][0] = minTrashes[start / m][start % m][1] = 0;

        // BFS 탐색
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int r = current / m;
            int c = current % m;

            for (int d = 0; d < 4; d++) {
                int nextR = r + dr[d];
                int nextC = c + dc[d];

                // 맵 안에 있고
                if (checkArea(nextR, nextC, map)) {
                    // 다음 위치가 쓰레기라면
                    if (map[nextR][nextC] == 'g') {
                        // r, c에서 nextR, nextC로 갈 때의 값과 기록되어있는 값을 비교하여 최소값을 갱신한다면 표시 후 큐에 추가.
                        if (minTrashes[nextR][nextC][0] > minTrashes[r][c][0] + 1 ||
                                (minTrashes[nextR][nextC][0] == minTrashes[r][c][0] + 1 && minTrashes[nextR][nextC][1] > minTrashes[r][c][1])) {
                            minTrashes[nextR][nextC][0] = minTrashes[r][c][0] + 1;
                            minTrashes[nextR][nextC][1] = minTrashes[r][c][1];
                            queue.offer(nextR * m + nextC);
                        }
                        // 다음 위치가 쓰레기가 아니며 해당 지점에 도달하는 값이 최소값을 갱신한다면
                        // (쓰레기 위치는 아니나 쓰레기의 근처일 수도 있다. 해당 사항 유의)
                    } else if (minTrashes[nextR][nextC][0] > minTrashes[r][c][0] ||
                            (minTrashes[nextR][nextC][0] == minTrashes[r][c][0] && minTrashes[nextR][nextC][1] > minTrashes[r][c][1] + (nearbyTrash[nextR][nextC] ? 1 : 0))) {
                        minTrashes[nextR][nextC][0] = minTrashes[r][c][0];
                        minTrashes[nextR][nextC][1] = minTrashes[r][c][1] + (nearbyTrash[nextR][nextC] ? 1 : 0);
                        queue.offer(nextR * m + nextC);
                    }
                }
            }
        }

        // 도착 지점에 도달했을 때의 최소값을 출력한다.
        System.out.println(minTrashes[end / m][end % m][0] + " " + minTrashes[end / m][end % m][1]);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}