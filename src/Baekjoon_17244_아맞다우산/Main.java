/*
 Author : Ruel
 Problem : Baekjoon 17244번 아맞다우산
 Problem address : https://www.acmicpc.net/problem/17244
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17244_아맞다우산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class State {
    int r;
    int c;
    int bitmask;

    public State(int r, int c, int bitmask) {
        this.r = r;
        this.c = c;
        this.bitmask = bitmask;
    }
}

class Loc {
    int r;
    int c;

    public Loc(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // m, n 크기의 맵이 주어진다
        // 벽은 '#', 시작 위치는 'S', 물건 위치는 'X', 빈 공간은 '.', 도착 위치는 'E'로 주어진다
        // 시작 위치에서 시작하여 모든 물건을 회수한 뒤, E로 도착하는 최소 시간을 출력하라
        //
        // BFS, 비트마스킹 문제
        // 각 지점에 도달하는 최소 시간 뿐만 아니라, 이 때는 몇 개의 물건을 회수했는가 또한 포함되어야한다
        // 따라서 각 지점에 방문하는 최소 시간을 회수한 물건의 개수로 구분하여 계산해야한다.
        // 이 때 물건이 최대 5개 주어지므로, 비트마스킹을 통해 처리할 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 주어지는 입력
        char[][] map = new char[m][n];
        // 물건의 개수를 센다.
        int counter = 0;
        Loc start = null, end = null;
        for (int i = 0; i < map.length; i++) {
            String row = br.readLine();
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = row.charAt(j);
                // 물건이라면
                // X를 저장하는 0 ~ 해당하는 숫자로 바꿔 저장한다.
                if (map[i][j] == 'X')
                    map[i][j] = (char) ((counter++) + '0');
                // 시작 위치
                else if (map[i][j] == 'S')
                    start = new Loc(i, j);
                // 도착 위친
                else if (map[i][j] == 'E')
                    end = new Loc(i, j);
            }
        }

        // 위치와 회수한 물건에 따른 최소 시간
        // 큰 값으로 초기화.
        int[][][] minDistances = new int[m][n][1 << counter];
        for (int[][] row : minDistances) {
            for (int[] bit : row)
                Arrays.fill(bit, Integer.MAX_VALUE);
        }

        // 시작 위치에서는 회수한 물건이 없고, 시간은 0이다.
        minDistances[start.r][start.c][0] = 0;
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(start.r, start.c, 0));
        while (!queue.isEmpty()) {
            // 현재 상태.
            State current = queue.poll();

            // 사방 탐색
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];
                
                // 다음 위치가 맵을 벗어나지 않으며
                if (checkArea(nextR, nextC, n, m)) {
                    int bitmask = current.bitmask;
                    // 만약 벽이라면 건너뛰고,
                    if (map[nextR][nextC] == '#')
                        continue;
                    // 물건의 위치라면, 해당 물건을 얻음으로써 바뀌는 바트마스크를 계산한다.
                    else if (map[nextR][nextC] >= '0' && map[nextR][nextC] < '5')
                        bitmask |= 1 << (map[nextR][nextC] - '0');

                    // 그런 후, 다음 위치에 해당하는 물건들을 갖고 방문한 시간이 최소 시간인지 확인하고
                    // 맞다면 최소 값을 갱신해주고, 해당 상태를 큐에 넣어주자.
                    if (minDistances[nextR][nextC][bitmask] > minDistances[current.r][current.c][current.bitmask] + 1) {
                        minDistances[nextR][nextC][bitmask] = minDistances[current.r][current.c][current.bitmask] + 1;
                        queue.offer(new State(nextR, nextC, bitmask));
                    }
                }
            }
        }

        // 최종 위치에 모든 물건을 갖고 도착한 시간을 출력한다.
        System.out.println(minDistances[end.r][end.c][(1 << (counter)) - 1]);
    }

    // 맵을 벗어나는지 체크하는 메소드
    static boolean checkArea(int r, int c, int n, int m) {
        return r >= 0 && r < m && c >= 0 && c < n;
    }
}