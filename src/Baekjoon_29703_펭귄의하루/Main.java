/*
 Author : Ruel
 Problem : Baekjoon 29703번 펭귄의 하루
 Problem address : https://www.acmicpc.net/problem/29703
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_29703_펭귄의하루;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Seek {
    int r;
    int c;
    int state;

    public Seek(int r, int c, int state) {
        this.r = r;
        this.c = c;
        this.state = state;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기로 표현되는 펭귄 마을이 있다.
        // S는 시작점, H는 집, E는 안전 지역, D는 위험 지역, F는 물고기 서식지이다.
        // S에서 출발하여 F를 들려 H로 돌아가는데, D는 거치지 않고서 가고자 한다.
        // 펭귄은 상하좌우로 1초에 한 칸 움직일 수 있으며, 마을 밖으로 이동할 수 없다.
        // 이 때 집에 도달하는 최소 시간을 구하라
        //
        // BFS 문제
        // S -> F, F -> H 두 가지 경로를 구해야한다.
        // 만약 둘 중 하나만 최소가 되는 값을 구한다면 그 합이 최소가 아닐 수 있다.
        // 따라서 S -> F, H -> F 처럼 S와 H에서 F로 도달하는 경로로 생각하여 구하고
        // 두 경로의 합이 최소인 경우를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 맵
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] map = new char[n][];
        
        // minDistances[i][j][k]
        // i, j에 도달하는데 걸리는 최소 시간
        // k == 0 일 땐 S -> F, 1일 땐 H -> F
        int[][][] minDistances = new int[n][m][2];
        for (int[][] row : minDistances) {
            for (int[] sta : row)
                Arrays.fill(sta, Integer.MAX_VALUE);
        }
        Queue<Seek> queue = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {     // 시작 지점
                    queue.offer(new Seek(i, j, 0));
                    minDistances[i][j][0] = 0;
                } else if (map[i][j] == 'H') {      // 집
                    queue.offer(new Seek(i, j, 1));
                    minDistances[i][j][1] = 0;
                }
            }
        }
        
        // BFS 탐색
        while (!queue.isEmpty()) {
            Seek current = queue.poll();
            
            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];
                
                // 맵 범위를 벗어나지 않고, 위험지역이 아니며
                // 최소 시간을 갱신할 경우
                if (checkArea(nextR, nextC, map) && map[nextR][nextC] != 'D' &&
                        minDistances[nextR][nextC][current.state] > minDistances[current.r][current.c][current.state] + 1) {
                    minDistances[nextR][nextC][current.state] = minDistances[current.r][current.c][current.state] + 1;
                    queue.offer(new Seek(nextR, nextC, current.state));
                }
            }
        }

        // 최소 시간
        int answer = Integer.MAX_VALUE;
        // 모든 지점을 돌며, F이며 두 경로의 합이 최소인 곳을 찾는다.
        for (int i = 0; i < minDistances.length; i++) {
            for (int j = 0; j < minDistances[i].length; j++) {
                if (map[i][j] == 'F' && minDistances[i][j][0] != Integer.MAX_VALUE &&
                        minDistances[i][j][1] != Integer.MAX_VALUE)
                    answer = Math.min(answer, minDistances[i][j][0] + minDistances[i][j][1]);
            }
        }
        // 만약 answer이 초기값이라면
        // 두 경로 중 불가능한 경로가 있는 경우. -1 출력
        // 그 외의 경우 answer 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    // 맵을 벗어나는지 확인한다.
    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}