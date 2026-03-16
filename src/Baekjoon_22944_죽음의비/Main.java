/*
 Author : Ruel
 Problem : Baekjoon 22944번 죽음의 비
 Problem address : https://www.acmicpc.net/problem/22944
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22944_죽음의비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다. 체력 h, 우산의 내구도 d가 주어진다.
        // 각 격자는 우산 U, 시작 위치 S, 안전 지대 E, 빈칸 .으로만 주어진다.
        // 시작 위치와 안전 지대를 제외한 곳에는 죽음의 비가 내리며
        // 비를 맞을 때마다 우산이 있는 경우, 우산의 내구도가 1 감소하거나 우산이 없는 경우 체력이 1 감소한다.
        // 우산이 있는 곳은 최대 10곳 이하로 주어지며, 해당 장소에 도달할 경우, 들고 있는 우산을 즉시 버리며, 새로운 우산을 든다.
        // 이동은 좌우상하 한 칸씩 가능하다.
        // 시작 위치에서 안전 지대로 최소 이동으로 도달하고자할 때, 그 이동 횟수는?
        //
        // BFS 문제
        // 먼저 맵에서 이동이 불가능한 곳은 없다.
        // 따라서 각 지점들의 위치를 안다면 각 거리는 BFS를 할 필요없이 맨해튼 거리로 계산할 수 있다.
        // 그 후, 각 지점 S, U, E에서 다른 지점으로 이동이 가능한지, 가능하다면 우산의 내구도만 차감하는지 혹은 체력도 감소하는지 여부를 따진다.
        // 최종적으로 E에 도달가능한지 체크한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 격자, 체력 h, 우산의 내구도 d
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        // 맵
        char[][] map = new char[n][];
        // 우산의 개수
        int k = 0;
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'U')
                    k++;
            }
        }

        // 각 지점들을 배열로 정리
        // points[0]는 시작 지점, points[k+1]은 안전 지대, 나머지는 우산
        int[][] points = new int[k + 2][2];
        int cnt = 1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case 'S' -> {
                        points[0][0] = i;
                        points[0][1] = j;
                    }
                    case 'U' -> {
                        points[cnt][0] = i;
                        points[cnt++][1] = j;
                    }
                    case 'E' -> {
                        points[k + 1][0] = i;
                        points[k + 1][1] = j;
                    }
                }
            }
        }

        // 맨해튼 거리 계산
        int[][] distances = new int[k + 2][k + 2];
        for (int i = 0; i < distances.length; i++) {
            for (int j = i + 1; j < distances[i].length; j++)
                distances[i][j] = distances[j][i] = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
        }

        Queue<int[]> queue = new LinkedList<>();
        int[][] minDistances = new int[k + 2][h + 1];
        for (int[] md : minDistances)
            Arrays.fill(md, Integer.MAX_VALUE);
        minDistances[0][h] = 0;
        for (int i = 1; i < k + 2; i++) {
            // 시작 지점에서는 비가 내리지 않으므로 최대 h와 동일한 거리까지 이동 가능. 이 때의 체력은 1이 된다.
            if (distances[0][i] <= h) {
                minDistances[i][h - distances[0][i] + 1] = distances[0][i];
                queue.offer(new int[]{i, h - distances[0][i] + 1, minDistances[i][h - distances[0][i] + 1]});
            }
        }
        // bfs
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (minDistances[cur[0]][cur[1]] < cur[2])
                continue;

            // 다음 지점
            for (int next = 1; next < k + 2; next++) {
                // 우산의 내구도 차감만으로도 이동 가능한 경우
                if (distances[cur[0]][next] <= d) {
                    if (minDistances[next][cur[1]] > minDistances[cur[0]][cur[1]] + distances[cur[0]][next]) {
                        minDistances[next][cur[1]] = minDistances[cur[0]][cur[1]] + distances[cur[0]][next];
                        queue.offer(new int[]{next, cur[1], minDistances[next][cur[1]]});
                    }
                } else if (distances[cur[0]][next] < d + cur[1]) {      // 체력까지 소모해야 이동 가능한 경우
                    if (minDistances[next][d + cur[1] - distances[cur[0]][next]] > cur[2] + distances[cur[0]][next]) {
                        minDistances[next][d + cur[1] - distances[cur[0]][next]] = cur[2] + distances[cur[0]][next];
                        queue.offer(new int[]{next, d + cur[1] - distances[cur[0]][next], minDistances[next][d + cur[1] - distances[cur[0]][next]]});
                    }
                }
            }
        }

        // 남은 체력에 상관 없이 최소 이동 횟수로 안전 지대에 도달한 경우 계산
        int answer = Integer.MAX_VALUE;
        for (int i = 1; i <= h; i++)
            answer = Math.min(answer, minDistances[k + 1][i]);
        // 답 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}