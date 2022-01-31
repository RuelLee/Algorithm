/*
 Author : Ruel
 Problem : Baekjoon 1726번 로봇
 Problem address : https://www.acmicpc.net/problem/1726
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 로봇;

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
    int direction;

    public State(int r, int c, int direction) {
        this.r = r;
        this.c = c;
        this.direction = direction;
    }
}

public class Main {
    static int[] dr = {0, 0, 0, 1, -1};
    static int[] dc = {0, 1, -1, 0, 0};
    static int[][] map;
    static int[][][] minOrders;

    public static void main(String[] args) throws IOException {
        // 한 로봇이 수행할 수 있는 명령은 방향을 90도 틀기와 앞으로 1 ~ 3칸 전진이 있다고 한다
        // 로봇이 이동할 수 있는 경로와 이동할 수 없는 곳이 주어지고, 시작점과 방향, 도착점과 방향이 주어질 때
        // 해당 지점에 도착할 수 있는 최소 명령의 수는?
        // 생각보다 조건이 많아 구현하기가 조금 까다로웠다.
        // 로봇에 방향이 있기 때문에 이에 따른 필요 명령이 있어야했고, 한번에 이동할 수 있는 칸 수도 1 ~ 3칸인데 중간에 이동할 수 없는 곳이 주어진다면 이후로는 당연히 갈 수 없다.
        // 기본적인 문제 풀이 자체는 BFS였다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        map = new int[m][n];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        State start = new State(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));
        st = new StringTokenizer(br.readLine());
        State end = new State(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));

        minOrders = new int[m][n][5];       // 각 칸에 방향마다 필요 명령의 갯를 저장한다.
        for (int[][] minO : minOrders) {
            for (int[] mo : minO)
                Arrays.fill(mo, Integer.MAX_VALUE);
        }

        minOrders[start.r][start.c][start.direction] = 0;       // 시작점과 방향의 필요 명령 수는 0
        Queue<State> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            State current = queue.poll();

            for (int d = 1; d < 5; d++) {       // 동서남북 4방을 탐색한다
                if (current.direction == 1 || current.direction == 2) {     // 현재 동이나 서쪽 방향을 바라보고 있고
                    if ((d == 1 || d == 2) && minOrders[current.r][current.c][d] > minOrders[current.r][current.c][current.direction] + 2)      // 남이나 북 방향으로 틀 때는 명령을 하나만 소모한다. 이 때 최소 명령 수가 갱신되는지 확인.
                        minOrders[current.r][current.c][d] = minOrders[current.r][current.c][current.direction] + 2;
                    else if ((d == 3 || d == 4) && minOrders[current.r][current.c][d] > minOrders[current.r][current.c][current.direction] + 1)     // 동 -> 서, 서 -> 동일 경우에는 2개의 명령을 소모.
                        minOrders[current.r][current.c][d] = minOrders[current.r][current.c][current.direction] + 1;
                } else {        // 현재 남이나 북을 바라보고 있고,
                    if ((d == 1 || d == 2) && minOrders[current.r][current.c][d] > minOrders[current.r][current.c][current.direction] + 1)      // 원하는 방향이 동이나 서라면 명령 1 소모.
                        minOrders[current.r][current.c][d] = minOrders[current.r][current.c][current.direction] + 1;
                    else if ((d == 3 || d == 4) && minOrders[current.r][current.c][d] > minOrders[current.r][current.c][current.direction] + 2)     // 남 -> 북, 북 -> 남일 경우 명령 2 소모.
                        minOrders[current.r][current.c][d] = minOrders[current.r][current.c][current.direction] + 2;
                }

                for (int multi = 1; multi < 4; multi++) {       // 전진 명령은 1칸부터 3칸까지 가능
                    int nextR = current.r + dr[d] * multi;
                    int nextC = current.c + dc[d] * multi;

                    if (checkArea(nextR, nextC)) {      // 만약 이동 불가 지역을 만났다면 거기서 스톱.
                        if (map[nextR][nextC] == 1)
                            break;

                        else if (minOrders[nextR][nextC][d] > minOrders[current.r][current.c][d] + 1) {     // 이동이 된다면, current 지점 d방향의 최소 명령수 +1로 nextR, nextC 지점에 도달할 수 있다.
                            minOrders[nextR][nextC][d] = minOrders[current.r][current.c][d] + 1;
                            queue.offer(new State(nextR, nextC, d));
                        }
                    }
                }
            }
        }
        System.out.println(minOrders[end.r][end.c][end.direction]);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}