/*
 Author : Ruel
 Problem : Baekjoon 23354번 군탈체포조
 Problem address : https://www.acmicpc.net/problem/23354
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23354_군탈체포조;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다.
        // 군부대는 -1 값을 갖고 있으며, 탈영병들은 0의 위치에 위치하고 있다.
        // 부대와 탈영병이 존재하는 위치를 제외한 격자들은 방문할 때마다 값에 해당하는 만큼의 통행료를 낸다.
        // 모든 탈영병들을 잡은 후 복귀하고자할 때, 비용의 최소값은?
        //
        // dijkstra, 브루트포스, 순엻 문제
        // 다익스트라 알고리즘을 온전히 사용하는 것은 아니지만,
        // 부대와 탈영병 위치를 기록하고, 해당 위치로부터 다른 지점까지의 이동 비용을
        // 우선순위큐를 통해 구한다.
        // 그 후 순열을 통해 탈영병을 잡을 순서를 정해 순서대로 방문했을 때의 비용을 모두 구하고
        // 그 때의 최소값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 부대와 탈영병의 위치
        int[][] locs = new int[6][2];
        // 탈영병의 수
        int runaways = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == -1) {
                    map[i][j] = 0;
                    locs[0][0] = i;
                    locs[0][1] = j;
                } else if (map[i][j] == 0) {
                    locs[++runaways][0] = i;
                    locs[runaways][1] = j;
                }
            }
        }
        
        // 각 지점에서 다른 지점에 이르는 최소 비용
        int[][] minDistances = new int[runaways + 1][runaways + 1];

        // 유사 dijkstra를 사용하여 다른 지점에 이르는 최소 비용을 구한다.
        int[][] distances = new int[n][n];
        for (int i = 0; i < runaways; i++) {
            for (int[] d : distances)
                Arrays.fill(d, Integer.MAX_VALUE);
            // 현재 출발점
            distances[locs[i][0]][locs[i][1]] = 0;

            // 다른 지점까지 이르는 최소 비용을 구한다.
            Queue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> distances[o / n][o % n]));
            priorityQueue.offer(locs[i][0] * n + locs[i][1]);
            while (!priorityQueue.isEmpty()) {
                int current = priorityQueue.poll();
                int row = current / n;
                int col = current % n;

                for (int d = 0; d < 4; d++) {
                    int nextR = row + dr[d];
                    int nextC = col + dc[d];
                    if (checkArea(nextR, nextC, n) &&
                            distances[nextR][nextC] > distances[row][col] + map[nextR][nextC]) {
                        distances[nextR][nextC] = distances[row][col] + map[nextR][nextC];
                        priorityQueue.offer(nextR * n + nextC);
                    }
                }
            }
            // 찾은 값을 기록
            for (int j = 0; j <= runaways; j++)
                minDistances[i][j] = minDistances[j][i] = distances[locs[j][0]][locs[j][1]];
        }
        // 0번 위치(부대)에서 시작하여 탈영병들을 모두 잡았을 때의 비용을 순열을 통해 구한다.
        System.out.println(findMinCosts(0, 0, 0, new boolean[runaways + 1], minDistances));
    }

    // 탈영병들 순서를 나열하여 최소 비용을 찾는다.
    static int findMinCosts(int current, int arrest, int sum, boolean[] visited, int[][] minDistances) {
        // 모든 탈영병을 체포했다면 부대로 복귀한다.
        if (arrest == visited.length - 1)
            return sum + minDistances[current][0];
        
        // 현재 상태에서 계산할 수 있는 최소 비용
        int min = Integer.MAX_VALUE;
        // 다음 위치
        for (int next = 1; next < visited.length; next++) {
            // 이미 방문했다면 건너뛰고
            if (visited[next])
                continue;

            // 아니라면 다음 위치 방문 표시
            visited[next] = true;
            // 다음으로 진행했을 때 현재 위치는 next, 체포한 탈영병의 수 arrest + 1, 비용 합 sum + minDistances[current][next], 방문 여부 visted
            // 해당 상태로 진행했을 때 최소 돌아오는 비용과 min과 비교하여 더 적은 값을 기록
            min = Math.min(min, findMinCosts(next, arrest + 1, sum + minDistances[current][next], visited, minDistances));
            // 해당 상황 계산이 끝났으므로 방문 표시 해제
            visited[next] = false;
        }
        // 찾은 최소 비용을 반환.
        return min;
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}