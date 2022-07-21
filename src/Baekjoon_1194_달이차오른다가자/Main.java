/*
 Author : Ruel
 Problem : Baekjoon 1194번 달이 차오른다, 가자.
 Problem address : https://www.acmicpc.net/problem/1194
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1194_달이차오른다가자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class State {
    int r;
    int c;
    int keyBitmask;

    public State(int r, int c, int keyBitmask) {
        this.r = r;
        this.c = c;
        this.keyBitmask = keyBitmask;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 세로 n, 가로 m 크기의 맵이 주어진다.
        // '.' 빈 공간, '#' 벽, a ~ f 키, A ~ F 문, '0' 현재 서 있는 곳, '1' 탈출구로 주어진다
        // 문을 지나가기 위해서는 대응하는 키가 있어야만 한다
        // 탈출구로 탈출하는 최소 턴 수를 구하라.
        //
        // BFS와 비트마스킹을 활용한 문제
        // 너비 우선 탐색을 하되, 키를 갖고 있는 상태를 비트마스킹을 활용하여 구분해주자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 맵의 상태
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 각 지점에 키 상태에 따라 도달하는 최소 턴 수.
        int[][][] minDistance = new int[n][m][1 << 7];
        for (int[][] minD : minDistance) {
            for (int[] md : minD)
                Arrays.fill(md, Integer.MAX_VALUE);
        }

        // 현재 서있는 지점 찾기.
        Queue<State> queue = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '0') {
                    // 큐에 삽입
                    queue.offer(new State(i, j, 0));
                    // 턴 수 0으로 초기화
                    minDistance[i][j][0] = 0;
                    break;
                }
            }
        }
        
        // BFS 탐색
        while (!queue.isEmpty()) {
            State current = queue.poll();
            // 탈출구 도달 시 종료.
            if (map[current.r][current.c] == '1')
                break;
            
            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 맵 안에 있고
                if (checkArea(nextR, nextC, map)) {
                    // 키가 있는 곳일 때
                    if (map[nextR][nextC] >= 'a' && map[nextR][nextC] <= 'f') {
                        int keyState = current.keyBitmask | 1 << (map[nextR][nextC] - 'a');
                        if (minDistance[nextR][nextC][keyState] > minDistance[current.r][current.c][current.keyBitmask] + 1) {
                            minDistance[nextR][nextC][keyState] = minDistance[current.r][current.c][current.keyBitmask] + 1;
                            queue.offer(new State(nextR, nextC, keyState));
                        }
                        // 문이 있는 곳
                    } else if (map[nextR][nextC] >= 'A' && map[nextR][nextC] <= 'F') {
                        boolean hasKey = (current.keyBitmask & 1 << (map[nextR][nextC] - 'A')) != 0;
                        if (hasKey && minDistance[nextR][nextC][current.keyBitmask] > minDistance[current.r][current.c][current.keyBitmask] + 1) {
                            minDistance[nextR][nextC][current.keyBitmask] = minDistance[current.r][current.c][current.keyBitmask] + 1;
                            queue.offer(new State(nextR, nextC, current.keyBitmask));
                        }
                        // 벽일 때
                    } else if (map[nextR][nextC] == '#')
                        continue;
                    // 처음에 서있던 곳이거나, 빈 곳이거나, 탈출구일 때
                    else if (minDistance[nextR][nextC][current.keyBitmask] > minDistance[current.r][current.c][current.keyBitmask] + 1) {
                        minDistance[nextR][nextC][current.keyBitmask] = minDistance[current.r][current.c][current.keyBitmask] + 1;
                        queue.offer(new State(nextR, nextC, current.keyBitmask));
                    }
                }
            }
        }

        // 탈출구에 도달하는 최소 턴 수.
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < minDistance.length; i++) {
            for (int j = 0; j < minDistance[i].length; j++) {
                // 탈출구 일 때, 최소값을 가져온다.
                if (map[i][j] == '1')
                    minValue = Math.min(minValue, Arrays.stream(minDistance[i][j]).min().getAsInt());
            }
        }
        
        // 최소값이 갱신되어있다면 탈출이 가능한 경우이므로 그 값 출력
        // 그렇지 않고 초기값이라면 탈출이 불가능한 경우이므로 -1 출력
        System.out.println(minValue == Integer.MAX_VALUE ? -1 : minValue);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}