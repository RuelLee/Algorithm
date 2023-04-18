/*
 Author : Ruel
 Problem : Baekjoon 24513번 좀비 바이러스
 Problem address : https://www.acmicpc.net/problem/24513
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24513_좀비바이러스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class State {
    int row;
    int col;
    int type;

    public State(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 격자 모양의 마을이 있다.
        // 어느 날 좀비 바이러스가 창궐하여 퍼져나간다.
        // 바이러스는 1번과 2번 두 종류가 있으며, 1시간 마다 상하좌우 네 방향으로 퍼져나간다
        // 만약 한 마을에 바이러스가 완전히 퍼지기 전, 두 바이러스가 만난다면 3번 바이러스로 변이하며, 더 이상 퍼지지는 않는다.
        // 치료제를 갖고 있는 마을은 감염시킬 수 없다.
        // 1번과 2번 바이러스에 감염된 마을이 각각 하나씩 나왔을 때
        // 1, 2, 3번 바이러스가 퍼진 마을의 개수를 각각 구하라
        //
        // BFS 문제
        // 치료제가 있음에 유의하고, 각 위치에 바이러스가 퍼진 시간과 타입을 기록한다.
        // 그리고 두 바이러스가 동시에 같은 마을에 진입했다면 3번 바이러스로 변이시키고 더 이상 진행시키지 않는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 주어지는 초기 상태
        int[][] map = new int[n][];
        for (int i = 0; i < n; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 1, 2번 바이러스의 위치
        int[] viruses = new int[2];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] > 0)
                    viruses[map[i][j] - 1] = i * m + j;
            }
        }

        // 바이러스 진행 상황
        int[][][] virusMap = new int[n][m][2];
        // 값 초기화
        for (int[][] row : virusMap) {
            for (int[] col : row)
                Arrays.fill(col, Integer.MAX_VALUE);
        }

        // 첫 바이러스 시간과 타입 추가.
        // 큐에 바이러스 위치와 타입 추가.
        Queue<State> queue = new LinkedList<>();
        for (int i = 0; i < viruses.length; i++) {
            int row = viruses[i] / m;
            int col = viruses[i] % m;
            virusMap[row][col][0] = 0;
            virusMap[row][col][1] = i;
            queue.offer(new State(row, col, i));
        }
        
        // BFS 탐색
        while (!queue.isEmpty()) {
            State current = queue.poll();
            // 만약 3번 타입이라면 더 이상 진행하지 않는다.
            if (virusMap[current.row][current.col][1] == 2)
                continue;
            
            // 사방 탐색
            for (int d = 0; d < 4; d++) {
                int nextR = current.row + dr[d];
                int nextC = current.col + dc[d];

                // 다음 위치가 지도 범위를 벗어나지 않으며 치료제가 있는 마을이 아니고
                if (checkArea(nextR, nextC, map) && map[nextR][nextC] != -1) {
                    // 다른 바이러스보다 먼저 도착한다면 
                    if (virusMap[nextR][nextC][0] > virusMap[current.row][current.col][0] + 1) {
                        // 바이러스 진입 시간과 타입 기록
                        virusMap[nextR][nextC][0] = virusMap[current.row][current.col][0] + 1;
                        virusMap[nextR][nextC][1] = current.type;
                        // 큐에 추가
                        queue.offer(new State(nextR, nextC, current.type));
                    } else if (virusMap[nextR][nextC][0] == virusMap[current.row][current.col][0] + 1 &&
                            virusMap[nextR][nextC][1] + virusMap[current.row][current.col][1] == 1) {
                        // 만약 서로 다른 타입의 바이러스가 같은 시간에 진입했다면
                        // 바이러스 타입만 변화.
                        virusMap[nextR][nextC][1] = 2;
                    }
                }
            }
        }
        // 탐색 종료

        // 결과를 살펴보며 각 바이러스 감염된 마을 수 계산.
        int[] counts = new int[3];
        for (int i = 0; i < virusMap.length; i++) {
            for (int j = 0; j < virusMap[i].length; j++) {
                if (virusMap[i][j][0] == Integer.MAX_VALUE)
                    continue;

                counts[virusMap[i][j][1]]++;
            }
        }
        
        // 답안 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < counts.length; i++)
            sb.append(counts[i]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }

    // 사방 탐색 시, 범위를 벗어나는지 확인.
    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}