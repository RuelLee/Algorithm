/*
 Author : Ruel
 Problem : Baekjoon 16236번 아기 상어
 Problem address : https://www.acmicpc.net/problem/16236
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16236_아기상어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] map;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 공간에 빈 공간 0, 물고기 1 ~ 6, 아기 상어 9가 주어진다
        // 아기 상어의 처음 크기는 2이며, 자신보다 작은 물고기만 먹을 수 있고,
        // 자신의 크기와 같은 수의 물고기를 먹게 되면 크기가 1만큼 성장한다.
        // 아기 상어는 먹을 수 있는 물고기들 중 가장 가까운 물고기에게 다가가고, 가까운 물고기가 여러 마리라면
        // 물고기들 중 가장 위에 있는, 또 그러한 물고기가 여러 마리라면 가장 왼쪽에 있는 물고기를 먹는다.
        // 아기 상어는 자신보다 큰 물고기가 있는 칸은 지나갈 수 없지만, 자기보다 작은 물고기가 있는 칸은 지나갈 수 있다.
        // 아기 상어가 먹을 수 있는 마지막 물고기를 먹는 데까지 걸리는 시간은?
        //
        // 구현 문제
        // 구현이 어렵진 않지만 많아 조금 성가셨다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        map = new int[n][];
        for (int i = 0; i < n; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 우선순위큐를 통해 최소힙으로 물고기들을 받자.
        PriorityQueue<Integer> fishes = new PriorityQueue<>(Comparator.comparingInt(l -> map[l / n][l % n]));
        // 아기 상어의 위치.
        int sharkLoc = -1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 9)
                    sharkLoc = i * n + j;
                else if (map[i][j] != 0)
                    fishes.offer(i * n + j);
            }
        }
        // 상어의 위치를 알아냈다면 맵에선 지워주자.
        map[sharkLoc / n][sharkLoc % n] = 0;

        // 초기 상어의 크기.
        int sharkSize = 2;
        // 먹은 물고기의 수.
        int eatenFishes = 0;
        // 걸린 시간.
        int time = 0;
        // 현재 먹을 수 있는 물고기들을 리스트에 모아주자.
        List<Integer> fishCanEaten = new LinkedList<>();
        while (!fishes.isEmpty() && map[fishes.peek() / n][fishes.peek() % n] < sharkSize)
            fishCanEaten.add(fishes.poll());

        // 먹을 수 있는 물고기가 있는 한 계속 반복한다.
        while (!fishCanEaten.isEmpty()) {
            // 현재 상어의 위치로부터 먹을 수 있는 물고기들까지의 거리를 구한다.
            int[][] minDistances = new int[n][n];
            for (int[] md : minDistances)
                Arrays.fill(md, Integer.MAX_VALUE);
            // 현재 상어의 위치에서는 거리가 0.
            minDistances[sharkLoc / n][sharkLoc % n] = 0;
            // BFS로 탐색한다.
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(sharkLoc);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                // 사방 탐색.
                for (int d = 0; d < 4; d++) {
                    int nextR = current / n + dr[d];
                    int nextC = current % n + dc[d];
                    // nextR, nextC가 맵 안에 있으며, 상어의 크기보단 같거나 작아야하고
                    // current에서 nextR, nextC로 가는 경우가 최소 거리를 갱신하는 경우에만.
                    if (checkArea(nextR, nextC) && map[nextR][nextC] <= sharkSize
                            && minDistances[nextR][nextC] > minDistances[current / n][current % n] + 1) {
                        // 최소 거리 갱신.
                        minDistances[nextR][nextC] = minDistances[current / n][current % n] + 1;
                        // 큐 삽입.
                        queue.offer(nextR * n + nextC);
                    }
                }
            }

            // 위에 구한 최소거리를 토대로 조건에 맞는 물고기를 선정한다.
            // 먼저 가장 가까운 물고기여야하며, 그러한 물고기가 여러마리라면 가장 위, 가장 왼쪽 물고기를 선택해야한다.
            int minDistance = Integer.MAX_VALUE;
            int loc = -1;
            for (int fish : fishCanEaten) {
                // 최소 거리가 낮으면 선택 or
                // 최소 거리가 같지만, idx가 더 작아도 선택(idx가 작다 == 이전 물고기보다 행이 적거나, 같으면서 열이 더 적다.)
                if (minDistance > minDistances[fish / n][fish % n] ||
                        minDistance == minDistances[fish / n][fish % n] && loc > fish) {
                    minDistance = minDistances[fish / n][fish % n];
                    loc = fish;
                }
            }

            // 만약 그러한 물고기로 가는 경로가 없다면 최소 거리가 갱신되지 않는다.
            // 반복문 종료.
            if (loc == -1)
                break;
            // 그렇지 않다면 아기 상어는 loc으로 이동하고
            sharkLoc = loc;
            // 이동 거리만큼 시간이 흐르며
            time += minDistances[loc / n][loc % n];
            // 해당 위치에 물고기는 먹힌다.
            map[loc / n][loc % n] = 0;
            // 그리고 먹을 수 있는 물고기 리스트에서 해당 물고기 삭제.
            fishCanEaten.remove(Integer.valueOf(loc));

            // 먹은 물고기 마릿수 증가.
            // 상어 크기와 같은 수의 물고기를 먹은 거라면
            if (++eatenFishes == sharkSize) {
                // 상어 크기 증가
                sharkSize++;
                // 먹은 물고기 마릿수 초기화.
                eatenFishes = 0;
            }
            // 물고기들 중 상어의 크기가 증가함에 따라 먹을 수 있는 물고기들이 더 생겨났는지 확인하고 추가한다.
            while (!fishes.isEmpty() && sharkSize > map[fishes.peek() / n][fishes.peek() % n])
                fishCanEaten.add(fishes.poll());
        }
        // 최종적으로 걸린 시간을 출력한다.
        System.out.println(time);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}