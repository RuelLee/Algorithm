/*
 Author : Ruel
 Problem : Baekjoon 14948번 군대탈출하기
 Problem address : https://www.acmicpc.net/problem/14948
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14948_군대탈출하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class State {
    int r;
    int c;
    int levelRequired;
    int jump;

    public State(int r, int c, int levelRequired, int jump) {
        this.r = r;
        this.c = c;
        this.levelRequired = levelRequired;
        this.jump = jump;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n*m 크기의 방이 주어지고 (0, 0) 위치에서 (n-1, m-1)으로 이동하려한다.
        // 각 방에는 레벨 요구치가 있으며, 해당 레벨보다 같거나 높아야만 지나갈 수 있다.
        // 그리고 도중 단 한번 점프를 사용할 수 있으며, 진행방향으로 한칸을 건너뛴다.
        // 중간에 있는 타일의 레벨 요구치는 무시할 수 있으며, 방 밖으로 나갈 수는 없다.
        // 목적지까지 도달하는데 필요한 최소 레벨 요구치는?
        //
        // BFS 문제
        // 각 방에서 우리가 레벨 요구치를 계산하는데 있어 필요한 정보들은
        // 각 방의 좌표와 점프를 사용했는지 여부이다.
        // 따라서 dp[row][col][jump]로 3차원 배열을 세워 각 방에 필요한 레벨 요구치를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 방
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 방의 필요한 레벨 요구치
        int[][] barrack = new int[n][];
        for (int i = 0; i < barrack.length; i++)
            barrack[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // (0, 0)부터 출발할 때, 해당 방에 도달하기 위한 최소 레벨 요구치
        int[][][] minLevels = new int[n][m][2];
        // 큰 값으로 초기화
        for (int[][] row : minLevels) {
            for (int[] col : row)
                Arrays.fill(col, Integer.MAX_VALUE);
        }

        // 시작점은 (0, 0)방의 요구치와 같다.
        minLevels[0][0][0] = barrack[0][0];
        // 레벨 요구치가 낮은 방을 우선적으로 탐색
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparing(o -> o.levelRequired));
        priorityQueue.offer(new State(0, 0, minLevels[0][0][0], 0));
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            // 만약 current의 레벨 요구치가 기록된 값보다 크다면
            // 더 적은 레벨 요구치로 해당 위치에서 계산이 되었으므로 건너뛴다.
            if (current.levelRequired > minLevels[current.r][current.c][current.jump])
                continue;

            // 사방 탐색
            for (int d = 0; d < 4; d++) {
                // 점프 사용 유무에 따라 탐색하는 공간이 4개 혹은 8개가 될 수 있다.
                // multi가 1일 때는 점프를 사용하지 않는 경우
                // 2인 경우는 점프를 사용하는 경우.
                // 당연히 current가 점프를 사용한 결과라면 이번에는 사용해선 안된다.
                for (int multi = 1; multi < 3 - current.jump; multi++) {
                    // 다음 좌표
                    int nextR = current.r + dr[d] * multi;
                    int nextC = current.c + dc[d] * multi;
                    
                    // 다음 좌표가 방을 벗어나지 않고
                    if (checkArea(nextR, nextC, barrack)) {
                        // 레벨 요구치
                        int levelRequired = Math.max(current.levelRequired, barrack[nextR][nextC]);
                        // 레벨 요구치가 최소값을 갱신한다면
                        if (minLevels[nextR][nextC][current.jump + multi - 1] > levelRequired) {
                            // 값 갱신
                            minLevels[nextR][nextC][current.jump + multi - 1] = levelRequired;
                            // 큐 추가
                            priorityQueue.offer(new State(nextR, nextC, levelRequired, current.jump + multi - 1));
                        }
                    }
                }
            }
        }
        // 최종적으로 목적지에 필요한 레벨 요구치에 대한 두 값
        // 점프를 사용하지 않은 값과 사용한 값, 두 값 중 더 적은 값을 출력한다.
        System.out.println(Arrays.stream(minLevels[n - 1][m - 1]).min().getAsInt());
    }
    
    // 방의 범위를 벗어나는지 체크
    static boolean checkArea(int r, int c, int[][] barrack) {
        return r >= 0 && r < barrack.length && c >= 0 && c < barrack[r].length;
    }
}