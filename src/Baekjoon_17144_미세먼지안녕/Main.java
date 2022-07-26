/*
 Author : Ruel
 Problem : Baekjoon 17144번 미세먼지 안녕!
 Problem address : https://www.acmicpc.net/problem/17144
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17144_미세먼지안녕;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Grid {
    int r;
    int c;
    int dust;

    public Grid(int r, int c, int dust) {
        this.r = r;
        this.c = c;
        this.dust = dust;
    }
}

public class Main {
    static int airCleaner;
    static int[][] room;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 세로r, 가로 c 크기의 격자 모양 방에 대한 먼지들과 t 턴 수가 주어진다.
        // 가장 왼쪽 세로줄 2칸에는 공기청정기가 주어진다.
        // 1. 각 격자 안의 미세먼지는 네 방향으로 확산하며, 확산되는 양은 원래 먼지의 1/5이다.
        // 네 방향 중 공기청정기가 있거나 칸이 없다면 그 방향으로 확산은 일어나지 않는다.
        // 남은 미세먼지는 확산한 양을 제외한 만큼이다.
        // 2. 공기 위쪽 공기청정기는 테두리를 따라 반시계로 공기를 순환시킨다.
        // 아래쪽 공기청정기는 테두리를 따라 시계방향으로 공기를 순환시킨다.
        // 공기청정기에 들어온 먼지는 사라진다
        // ex)
        // ↓←←←←←←
        // ↓         ↑
        // -1→→→→→↑
        // -1→→→→→↓
        // ↑         ↓
        // ↑←←←←←←
        // t 만큼 시간이 흘렀을 때 방에 남은 미세먼지의 총합을 구하라.
        //
        // 시뮬레이션 문제
        // 먼지가 확산할 때, 이중 for문을 사용하려했지만, 그럴 경우, 이번에 확산된 먼지가 재확산될 수 있다.
        // 따라서 현재 턴에 있는 먼지들의 위치와 양을 큐에 담아, 현재 있는 먼지만으로 확산시키도록 하였다.
        // 공기 순환의 경우 for문을 4개 이용하여 먼지들을 이동시켜주었다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 방에 있는 먼지들.
        room = new int[r][c];
        for (int i = 0; i < room.length; i++)
            room[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 공기청정기의 윗부분에 해당하는 row를 가져온다.
        // col은 어차피 0 고정이므로 필요x
        // 아래쪽 부분도 역시 해당하는 row + 1이므로 필요x
        for (int i = 0; i < room.length; i++) {
            if (room[i][0] == -1) {
                airCleaner = i;
                break;
            }
        }

        // t만큼 진행시켜준다.
        for (int turn = 0; turn < t; turn++) {
            diffusion();
            airCirculation();
        }

        // room에 현재 있는 모든 먼지의 총합을 구한다.
        int dustSum = 0;
        for (int[] rm : room)
            dustSum += Arrays.stream(rm).sum();

        // 공기청정기가 -1로 두칸을 차지하고 있으므로 해당 값을 보정해준다.
        dustSum += 2;
        System.out.println(dustSum);
    }

    // 공기 순환
    static void airCirculation() {
        // 공기청정기의 윗 부분. 반시계방향으로 순환.
        // col == 0인 부분.
        for (int r = airCleaner - 1; r > 0; r--)
            room[r][0] = room[r - 1][0];
        // row == 0인 부분.
        for (int c = 0; c < room[0].length - 1; c++)
            room[0][c] = room[0][c + 1];
        // col == c - 1인 부분.
        for (int r = 0; r < airCleaner; r++)
            room[r][room[r].length - 1] = room[r + 1][room[r].length - 1];
        // row == airCleaner인 부분.
        for (int c = room[airCleaner].length - 1; c > 1; c--)
            room[airCleaner][c] = room[airCleaner][c - 1];
        // 공기청정기에서 나온 공기는 먼지가 0.
        room[airCleaner][1] = 0;

        // 공기청정기의 아랫부분. 시계방향으로 순환.
        // col == 0인 부분
        for (int r = airCleaner + 2; r < room.length - 1; r++)
            room[r][0] = room[r + 1][0];
        // row == r - 1인 부분
        for (int c = 0; c < room[room.length - 1].length - 1; c++)
            room[room.length - 1][c] = room[room.length - 1][c + 1];
        // col == c - 1인 부분
        for (int r = room.length - 1; r > airCleaner + 1; r--)
            room[r][room[r].length - 1] = room[r - 1][room[r].length - 1];
        // row == airClear + 1인 부분
        for (int c = room[airCleaner + 1].length - 1; c > 1; c--)
            room[airCleaner + 1][c] = room[airCleaner + 1][c - 1];
        // 공기청정기에서 나온 공기는 먼지가 0
        room[airCleaner + 1][1] = 0;
    }
    
    // 먼지 확산
    static void diffusion() {
        Queue<Grid> queue = new LinkedList<>();
        // 현재 먼지들의 위치와 양을 큐에 담는다.
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                if (room[i][j] != 0 && room[i][j] != -1)
                    queue.offer(new Grid(i, j, room[i][j]));
            }
        }

        // 큐가 빌 때까지.
        while (!queue.isEmpty()) {
            Grid current = queue.poll();

            // 1/5 몫 만큼 인접한 방향에 확산시킨다.
            int quota = current.dust / 5;
            for (int d = 0; d < 4; d++) {
                // 확산시킬 좌표.
                int nearR = current.r + dr[d];
                int nearC = current.c + dc[d];
                
                // 해당 좌표가 맵 안에 있고, 공기청정기가 아니라면
                if (checkArea(nearR, nearC) && room[nearR][nearC] != -1) {
                    // 인근 지점에 1/5만큼 확산시키고
                    room[nearR][nearC] += quota;
                    // 현재 지점에서는 해당 분만큼 빼준다.
                    room[current.r][current.c] -= quota;
                }
            }
        }
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < room.length && c >= 0 && c < room[r].length;
    }
}