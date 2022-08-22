/*
 Author : Ruel
 Problem : Baekjoon 2206번 벽 부수고 이동하기
 Problem address : https://www.acmicpc.net/problem/2206
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2206_벽부수고이동하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class State {
    int r;
    int c;
    int breaks;

    public State(int r, int c, int breaks) {
        this.r = r;
        this.c = c;
        this.breaks = breaks;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n, m 크기의 맵이 주어진다
        // 0은 비어있는 공간, 1은 벽이다.
        // 1, 1에서 n, m으로 이동하고자 한다. (1, 1)은 항상 0이다.
        // 가는 동안 벽을 한 번 부술 수 있다고 한다
        // n, m에 도달하는 최단 거리는 얼마인가?
        //
        // BFS 문제
        // 단 각 위치에 도달하는 최단거리를 기록하되, 벽을 부순 횟수에 따라 각각 저장한다
        // 따라서 이미 벽을 부순 경우와, 아직 벽을 부수지 않은 경우를 따로 계산해둔다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 입력으로 주어지는 맵
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 각 지점에 이르는 최소 거리를 벽을 부쉈는지 여부에 따라 별개로 저장한다.
        int[][][] minDistances = new int[n][m][2];
        for (int[][] col : minDistances) {
            for (int[] breaks : col)
                Arrays.fill(breaks, Integer.MAX_VALUE);
        }
        // 시작 위치 초기값
        minDistances[0][0][0] = minDistances[0][0][1] = 1;

        // 큐에 시작 상태를 담는다.
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(0, 0, 0));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 일단 맵 범위를 벗어나지 않으며
                if (checkArea(nextR, nextC, n, m)) {
                    // 다음 지점이 빈 지점일 때, 최소 거리 갱신이 되는지 확인.
                    if (map[nextR][nextC] == '0' && minDistances[nextR][nextC][current.breaks] > minDistances[current.r][current.c][current.breaks] + 1) {
                        minDistances[nextR][nextC][current.breaks] = minDistances[current.r][current.c][current.breaks] + 1;
                        queue.offer(new State(nextR, nextC, current.breaks));
                    // 현재 상태가 아직 벽을 부순 적이 없으며, 다음 지점이 벽이고, 이 벽을 부숨으로써 해당 지점에 이르는 최소 거리가 갱신된다면
                    } else if (current.breaks == 0 && map[nextR][nextC] == '1' && minDistances[nextR][nextC][1] > minDistances[current.r][current.c][0] + 1) {
                        minDistances[nextR][nextC][1] = minDistances[current.r][current.c][0] + 1;
                        queue.offer(new State(nextR, nextC, 1));
                    }
                }
            }
        }

        // 최종 지점에서의 벽을 부쉈을 때, 부수지 않았을 때, 두 경우 모두 살펴보고 작은 값을 가져온다.
        int minDistance = Arrays.stream(minDistances[n - 1][m - 1]).min().getAsInt();
        // 만약 초기값 그대로라면 n, m에 도달하는 방법이 없는 경우. -1 출력
        // 그렇지 않고 값이 있다면, 그 때의 최소값을 출력한다.
        System.out.println(minDistance == Integer.MAX_VALUE ? -1 : minDistance);
    }

    static boolean checkArea(int r, int c, int n, int m) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}