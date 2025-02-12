/*
 Author : Ruel
 Problem : Baekjoon 15906번 변신 이동 게임
 Problem address : https://www.acmicpc.net/problem/15906
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15906_변신이동게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class State {
    int r;
    int c;
    int trans;
    int turn;

    public State(int r, int c, int trans, int turn) {
        this.r = r;
        this.c = c;
        this.trans = trans;
        this.turn = turn;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다.
        // 각 칸은 '.' 혹은 '#' 으로 주어지는데
        // .은 빈칸, #은 텔레포트가 있음을 의미한다.
        // 캐릭터는 한 턴에 상하좌우 한 칸 움직일 수 있으며, 변신을 할 수 있는데 이 때 t턴이 소모된다.
        // 변신을 한 경우, 상하좌우마다 각 가장 인접한 텔레포트로 1턴에 이동할 수 있다.
        // 변신을 해제하는데는 시간이 소모되지 않는다.
        // 캐릭터는 처음엔 (1, 1)에 위치하며 목표 지점은 (r, c)이다.
        // 목표 지점에 이르는 최소 턴의 수는?
        //
        // dijkstra
        // 와 유사한 문제
        // 먼저 각 지점에 이르는 최소 턴을 기록할 때
        // 해당 지점에 도착한 상태가 변신 상태인지, 일반 상태인지 기록해야한다.
        // 변신 상태로 이동해왔는데, 또 다시 이동할 때 텔레포트를 이용한다면, 변신하는데 시간이 추가로 소모되지 않으므로.
        // minTurns[i][j][k] = (i, j)에 k(0 = 일반, 1 = 변신) 상태로 도착할 때 최소 턴의 수

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 격자, 변신 소모 턴 t, 도착 지점 (r, c)
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 격자의 정보
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 각 지점에서 이용할 수 있는 텔레포트 정보.
        List<List<Integer>> teleport = new ArrayList<>();
        for (int i = 0; i < n * n; i++)
            teleport.add(new ArrayList<>());

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 맵에서 텔레포트를 발견하면
                if (map[i][j] == '#') {
                    // 네 방향에 대해
                    for (int d = 0; d < 4; d++) {
                        // 일직선으로 다른 텔레포트를 만날 때까지 만나는 격자들에 대해
                        // 해당 지점에 텔레포트를 이용할 수 있음을 기록
                        for (int k = 1; i + dr[d] * k >= 0 && i + dr[d] * k < n &&
                                j + dc[d] * k >= 0 && j + dc[d] * k < n; k++) {
                            int nextR = i + dr[d] * k;
                            int nextC = j + dc[d] * k;
                            teleport.get(nextR * n + nextC).add(i * n + j);
                            // 다른 텔레포트를 만났다면 종료.
                            if (map[nextR][nextC] == '#')
                                break;
                        }
                    }
                }
            }
        }
        
        // 각 지점, 각 상태에 따른 최소 도달 턴
        int[][][] minTurns = new int[n][n][2];
        // 초기화
        for (int[][] col : minTurns) {
            for (int[] trans : col)
                Arrays.fill(trans, Integer.MAX_VALUE);
        }
        // 시작 지점
        minTurns[0][0][0] = 0;
        minTurns[0][0][1] = t;
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.turn));
        priorityQueue.offer(new State(0, 0, 0, 0));
        priorityQueue.offer(new State(0, 0, 1, t));
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            // 이미 더 빠른 턴 수로 도달한 기록이 있다면
            // current는 버린다.
            if (minTurns[current.r][current.c][current.trans] < current.turn)
                continue;

            // 네 방향에 대해 일반 상태로 걸어가는 경우.
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (nextR >= 0 && nextR < n && nextC >= 0 && nextC < n &&
                        minTurns[nextR][nextC][0] > minTurns[current.r][current.c][current.trans] + 1) {
                    minTurns[nextR][nextC][0] = minTurns[current.r][current.c][current.trans] + 1;
                    priorityQueue.offer(new State(nextR, nextC, 0, minTurns[nextR][nextC][0]));
                }
            }
            // 이용할 수 있는 텔레포트에 대해 탐색
            for (int next : teleport.get(current.r * n + current.c)) {
                int nextR = next / n;
                int nextC = next % n;
                
                // 만약 현재 변신 상태라면 추가 변신 턴을 소모하지 않음에 유의
                if (minTurns[nextR][nextC][1] > minTurns[current.r][current.c][current.trans] + 1 + (current.trans == 1 ? 0 : t)) {
                    minTurns[nextR][nextC][1] = minTurns[current.r][current.c][current.trans] + 1 + (current.trans == 1 ? 0 : t);
                    priorityQueue.offer(new State(nextR, nextC, 1, minTurns[nextR][nextC][1]));
                }
            }
        }
        // 최종 목표 지점에 도달하는 최소 턴을 출력
        // 변신 혹은 일반 상태 어느 것이든 상관 없음.
        System.out.println(Math.min(minTurns[r - 1][c - 1][0], minTurns[r - 1][c - 1][1]));
    }
}