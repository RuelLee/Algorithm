/*
 Author : Ruel
 Problem : Baekjoon 20005번 보스몬스터 전리품
 Problem address : https://www.acmicpc.net/problem/20005
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20005_보스몬스터전리품;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 맵이 주어진다.
        // 맵에 플레이어는 소문자, 보스는 B, 빈 공간은 ., 막힌 공간은 X로 주어진다
        // 각 플레이어는 최소 거리로 보스에게 이동하며,
        // 이동 후엔 자신의 dps만큼 매 초 공격을 가한다
        // 플레이어의 dps들과 보스의 hp 또한 주어진다.
        // 이 때 최대 몇 명의 플레이어가 보스에게 공격을 가할 수 있는가?
        //
        // 그래프 탐색 문제
        // 각 플레이어가 보스에게 도달하기 위한 최소거리는
        // 보스의 위치로부터 시작하여 BFS 탐색으로 각 거리를 구해준다.
        // 그 후, 거리가 가까운 사람부터 보스에 도달하여 데미지를 가한다.
        // 모든 플레이어는 동시에 공격을 가하므로, 같은 거리에 있는 인원에 대해서는
        // 동시에 공격을 가함을 유의하자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 지도의 크기와 플레이어의 수
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        char[][] map = new char[m][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 각 플레이어의 dps
        int[] players = new int[26];
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            players[st.nextToken().charAt(0) - 'a'] = Integer.parseInt(st.nextToken());
        }
        
        // 각 플레이어가 보스로부터 떨어진 거리
        int[] distances = new int[26];
        int[][] distancesFromBoss = new int[m][n];
        for (int[] dfb : distancesFromBoss)
            Arrays.fill(dfb, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'B') {
                    distancesFromBoss[i][j] = 0;
                    queue.offer(i * n + j);
                    break;
                }
            }
            if (!queue.isEmpty())
                break;
        }
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / n;
            int col = current % n;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC, map) && map[nextR][nextC] != 'X'
                        && distancesFromBoss[nextR][nextC] > distancesFromBoss[row][col] + 1) {
                    distancesFromBoss[nextR][nextC] = distancesFromBoss[row][col] + 1;
                    queue.offer(nextR * n + nextC);
                    if (map[nextR][nextC] >= 'a' && map[nextR][nextC] <= 'z')
                        distances[map[nextR][nextC] - 'a'] = distancesFromBoss[nextR][nextC];
                }
            }
        }
        // 보스로부터 각 플레이어의 거리 계산 끝

        // 최소힙 우선순위큐를 통해 플레이어를 가까운 순서부터 체크한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> distances[value]));
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] > 0)
                priorityQueue.offer(i);
        }

        // 보스의 hp
        int hp = Integer.parseInt(br.readLine());
        
        // 이전 플레이어의 보스 도달 시간
        int prePlayerArrivedTime = 0;
        // 현재 공격을 가하는 사람들의 dps 합
        int dpsSum = 0;
        // 누적 데미지
        int damages = 0;
        // 아직 보스를 쓰러뜨리지 못했고, 플레이어가 남아있다면
        while (damages < hp && !priorityQueue.isEmpty()) {
            // 이번 플레이어
            int current = priorityQueue.poll();
            // 만약 직전 플레이어와 도착 시간이 같다면
            // dps합만 증가시킨 후, 나중에 한번에 공격을 가한다.
            if (prePlayerArrivedTime == distances[current])
                dpsSum += players[current];
            else {
                // 만약 직전 플레이어와 도착 시간이 다르다면
                // 직전 플레이어들에 대한 공격을 계산한다.
                damages += dpsSum * (distances[current] - prePlayerArrivedTime);
                // 계산을 가한 후, 보스가 쓰러졌다면 current 플레이어는 공격을 가하지 못한다.
                // 우선순위큐에 다시 넣어주고 반복문을 종료한다.
                if (damages >= hp) {
                    priorityQueue.offer(current);
                    break;
                }
                // 그렇지 않고 보스 hp가 남아있다면
                // current와 동시에 도달하는 다른 플레이어가 있을 수 있으므로
                // 현재 도착 시간을 기록하고
                // dps 합에 현재 플레이어의 dps를 더해준다.
                prePlayerArrivedTime = distances[current];
                dpsSum += players[current];
            }
        }

        // 반복문이 종료된 후 누적 데미지가 보스의 hp를 넘어섰다면 보스가 쓰러진 경우
        // 전체 플레이어에서 우선순위큐에 남아있는 인원을 제외한 만큼이 데미지를 가했다.
        // 만약 아직 hp가 남아있다면 모든 인원이 보스를 때릴 수 있는 경우이다.
        System.out.println(damages >= hp ? p - priorityQueue.size() : p);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}