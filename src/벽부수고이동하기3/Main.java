/*
 Author : Ruel
 Problem : Baekjoon 16933번 벽 부수고 이동하기 3
 Problem address : https://www.acmicpc.net/problem/16933
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 벽부수고이동하기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pos {
    int r;
    int c;
    int k;
    int distance;

    public Pos(int r, int c, int k, int distance) {
        this.r = r;
        this.c = c;
        this.k = k;
        this.distance = distance;
    }
}

public class Main {
    static int[][][] minDistance;
    static char[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 지도가 주어진다
        // 0은 갈 수 있는 길, 1 벽이며, 벽을 부수고 갈 수 있는 횟수 k가 주어진다
        // 벽은 낮에만 부술 수 있으며, 처음 시작은 낮이다(한 칸 이동할 때마다 낮 밤이 바뀌며, 제 자리에서 이동하지 않고 시간을 보낼 수 있다)
        // 최좌측 최상단에서 최우측 최하단으로 이동할 수 있는 최소 거리를 구하라.
        // k 값에 따라 해당 지점에 도달할 수 있는 최소 거리가 다를 수 있으며 남은 기회에 따라 최종 지점에 도달할 수도 못할 수도 있으므로 값을 모두 살려야한다
        // 하지만 기회가 더 작게 남았음에도 더 큰 거리로 도착한 경우는 버려도 되는 값이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        map = new char[n][m];       // 지도 표시.
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        minDistance = new int[n][m][k + 1];     // k값에 따라 해당 지점에 도달할 수 있는 최소 거리를 저장한다.
        for (int[][] minD : minDistance) {
            for (int[] miD : minD)
                Arrays.fill(miD, Integer.MAX_VALUE);        // Integer.MAX_VALUE로 초기값 세팅.
        }
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(0, 0, k, 1));       // 0, 0에서 시작하며, 벽을 부술 수 있는 횟수 k, 시작 지점에서의 거리 1;
        minDistance[0][0][k] = 1;       // 첫 지점에서 최소 거리는 k 횟수가 모두 살아있고, 거리는 1
        while (!queue.isEmpty()) {
            Pos current = queue.poll();

            for (int d = 0; d < 4; d++) {       // 사방 탐색
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC)) {      // 맵을 벗어나지 않는 범위이며
                    // 다음 칸이 길이며, 최소 거리가 갱신된다면
                    if (map[nextR][nextC] == '0' && current.distance + 1 < getMinDistanceRemainMoreThanKBreak(nextR, nextC, current.k)) {
                        // 다음 칸에 k값을 그대로 가져가며 최소 거리를 갱신해주고
                        minDistance[nextR][nextC][current.k] = current.distance + 1;
                        // 큐에 담아준다.
                        queue.offer(new Pos(nextR, nextC, current.k, current.distance + 1));
                    }

                    // 다음 칸이 벽이며, 아직 벽을 뚫을 수 있는 횟수가 남아있다면
                    if (map[nextR][nextC] == '1' && current.k > 0) {
                        // 시작을 1 홀수로 시작했기 때문에, 홀수일 때는 낮, 짝수일 때는 밤이다
                        // 낮이며, 벽을 뚫을 수 있는 횟수가 k-1개 보다 같거나 많이 남았을 때 다음 칸에 도달할 수 있는 최소 거리보다 이번이 더 적다면
                        if (current.distance % 2 == 1 && current.distance + 1 < getMinDistanceRemainMoreThanKBreak(nextR, nextC, current.k - 1)) {
                            // 다음 칸, 벽을 뚫을 수 있는 횟수 k-1에 최소 거리 갱신.
                            minDistance[nextR][nextC][current.k - 1] = current.distance + 1;
                            // 큐에 담아준다.
                            queue.offer(new Pos(nextR, nextC, current.k - 1, current.distance + 1));
                        } else if (current.distance % 2 == 0)       // 만약 밤이라면 제 자리에서 낮이 되길 기다린다.
                            queue.offer(new Pos(current.r, current.c, current.k, current.distance + 1));
                    }
                }
            }
        }
        // 최종 위치에서 벽을 뚫을 수 있는 횟수가 0보다 크거나 같을 때의 최소 거리를 가져온다
        // 초기값 그대로라면 도달할 수 없는 경우, 아니라면 그 때의 최소 거리가 저장되어 있다.
        int answer = getMinDistanceRemainMoreThanKBreak(map.length - 1, map[map.length - 1].length - 1, 0);
        // 도달할 수 없다면 -1, 가능하다면 answer 출력.
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    // r, c에서 k 이상의 기회가 남은 채 도달할 수 경우들 중 최소 거리를 반환한다.
    static int getMinDistanceRemainMoreThanKBreak(int r, int c, int k) {
        int answer = minDistance[r][c][k];
        for (int i = k + 1; i < minDistance[r][c].length; i++)
            answer = Math.min(answer, minDistance[r][c][i]);
        return answer;
    }

    // 맵 범위를 벗어나지 않았는지 체크.
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}