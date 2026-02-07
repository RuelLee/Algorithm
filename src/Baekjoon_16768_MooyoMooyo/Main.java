/*
 Author : Ruel
 Problem : Baekjoon 16768번 Mooyo Mooyo
 Problem address : https://www.acmicpc.net/problem/16768
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16768_MooyoMooyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, k;
    static int[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * 10 크기의 판이 주어진다.
        // 각 칸에는 0 ~ 9까지의 값이 들어있고, 0은 빈 칸이다.
        // 같은 값의 칸 k개가 서로 인접해있다면, 터진다.
        // 터지고 나서 빈 칸의 위치에는 위에 있는 칸들이 떨어진다.
        // 마지막 상태를 출력하라.
        //
        // BFS, 시뮬레이션 문제
        // 먼저 두 가지 동작이 존재한다.
        // 하나는 k개의 인접해있는 칸들이 터지는 동작이고
        // 다른 하나는 중력이 작용해, 빈 칸의 위치로 위의 칸들이 떨어지는 것이다.
        // 두 동작을 구현하고, 더 이상 터지지 않을 때까지 두 동작을 반복한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * 10 크기의 칸, 같은 값 k개의 칸이 모이면 터진다.
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 보드
        map = new int[n][10];
        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < 10; j++)
                map[i][j] = row.charAt(j) - '0';
        }

        // 터지는 칸의 개수가 0보다 크다면 계속 터뜨리고
        // 떨어뜨리고를 반복한다.
        while (bomb() > 0)
            drop();

        // 최종 상태 출력
        StringBuilder sb = new StringBuilder();
        for (int[] m : map) {
            for (int j = 0; j < 10; j++)
                sb.append(m[j]);
            sb.append("\n");
        }
        System.out.print(sb);
    }

    // 인접한 k개의 칸을 터뜨린다.
    static int bomb() {
        // 방문 체크
        boolean[][] visited = new boolean[n][10];
        // 터진 칸의 개수
        int sum = 0;
        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 10; j++) {
                // 0이 아니고, 미방문인 경우
                if (map[i][j] != 0 && !visited[i][j]) {
                    queue.clear();
                    hashSet.clear();

                    // BFS 탐색으로 인접한 같은 값 칸의 개수를 센다.
                    queue.offer(i * 10 + j);
                    visited[i][j] = true;
                    hashSet.add(i * 10 + j);
                    while (!queue.isEmpty()) {
                        int current = queue.poll();

                        for (int d = 0; d < 4; d++) {
                            int nextR = current / 10 + dr[d];
                            int nextC = current % 10 + dc[d];

                            if (checkArea(nextR, nextC) && !visited[nextR][nextC] && map[nextR][nextC] == map[current / 10][current % 10]) {
                                queue.offer(nextR * 10 + nextC);
                                visited[nextR][nextC] = true;
                                hashSet.add(nextR * 10 + nextC);
                            }
                        }
                    }

                    // 같은 값 인접한 칸의 개수가 k개 이상인 경우
                    // 모두 0으로 갑승ㄹ 바꾼다.
                    if (hashSet.size() >= k) {
                        sum += hashSet.size();
                        for (int idx : hashSet)
                            map[idx / 10][idx % 10] = 0;
                    }
                }
            }
        }
        // 총 터진 칸의 개수 반환
        return sum;
    }

    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < 10;
    }

    // 중력이 작용해 빈 칸으로 값이 떨어진다.
    static void drop() {
        for (int i = 0; i < 10; i++) {
            int from = n - 2;
            for (int to = n - 1; to > 0 && from >= 0; to--) {
                if (map[to][i] == 0) {
                    from = Math.min(from, to - 1);
                    while (from >= 0 && map[from][i] == 0)
                        from--;

                    if (from >= 0) {
                        map[to][i] = map[from][i];
                        map[from][i] = 0;
                    }
                }
            }
        }
    }
}