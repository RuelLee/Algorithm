/*
 Author : Ruel
 Problem : Baekjoon 14503번 로봇 청소기
 Problem address : https://www.acmicpc.net/problem/14503
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14503_로봇청소기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 청소기와 n * m 크기의 방이 주어진다.
        // 청소기는 위치와 방향이 주어진다. 방향은 0 = 북, 1 = 동, 2 = 남, 3 = 서로 주어진다.
        // 방의 격자는 0 혹은 1로 주어지며, 0은 청소가 되지 않은 구역, 1은 벽을 의미한다.
        // 방의 가장자리 테두리는 벽이 있음이 보장된다.
        // 청소는 다음과 같이 동작한다.
        // 1. 현재 칸이 청소되지 않은 경우, 청소한다.
        // 2. 주변의 4칸 중 청소되지 않은 칸이 없는 경우
        //  1) 바라보는 방향을 유지한 채, 뒤로 갈 수 있다면 뒤로 이동한다.
        //  2) 뒤쪽 칸이 벽이라 후진할 수 없다면 작동을 멈춘다.
        // 3. 주변의 4칸 중 청소되지 않은 칸이 있는 경우
        //  1) 반시계로 90도 회전한다
        //  2) 바라보는 칸이 청소되지 않은 경우 전진한다.
        //  3) 1번으로 돌아간다.
        // 위와 같은 과정을 반복할 때, 청소하는 칸의 개수는?
        //
        // 시뮬레이션, 구현 문제
        // 그냥 문제에 주어진대로 구현만 하면 된다.
        // 방향이 순서대로 북 동 남 서임에 유의하고, 청소기가 탐색할 땐 반시계임에 유의한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 방
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 청소기의 위치와 방향
        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        
        // 방 정보
        int[][] map = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 청소한 격자의 수
        int count = 0;
        while (true) {
            // 현재 칸 청소가 안된 경우
            if (map[r][c] == 0) {
                // count 증가 및 현재 칸을 0이 아닌 2로 바꾼다.
                count++;
                map[r][c] = 2;
            }

            // 네 칸 중 청소하지 않은 칸이 있는 경우
            // 를 따지고 나서, 2번과 3번으로 분기하는 경우 계산이 낭비된다.
            // 먼저 3번을 통해 반시계로 돌며 청소되지 않은 칸이 있는지 탐색한다.
            // 그 후, 청소되지 않은 칸이 없다면 2번으로 넘어간다.
            boolean found = false;
            for (int i = 1; i < 5; i++) {
                // 현재 방향이 북 동 남 서로 되어있으므로 반시계로 방향을 맞추기 위해서는
                // 이번 방향에 +3을 한 뒤 mod 4을 해주면 된다.
                // 위 과정을 4번 반복하여 결국 자신이 보던 방향까지 오게된다.
                int nextD = (d + (i * 3)) % 4;
                int nextR = r + dr[nextD];
                int nextC = c + dc[nextD];
                
                // 청소되지 않은 칸이라면
                if (map[nextR][nextC] == 0) {
                    // 해당 칸으로 이동
                    r = nextR;
                    c = nextC;
                    // 방향 변경ㄴ
                    d = nextD;
                    // 네 칸 중 청소되지 않은 칸이 발견됐음을 표시
                    found = true;
                    // 더 이상 사방 탐색을 하지 않고 반복문 종료
                    break;
                }
            }

            // 사방 탐색 동안 청소되지 않은 칸을 발견하지 못한 경우
            // 2번으로 넘어간다.
            if (!found) {
                // 가야하는 방향을 현재 방향의 뒤로
                // 따라서 +2한 뒤 mod 4한 방향
                int nextR = r + dr[(d + 2) % 4];
                int nextC = c + dc[(d + 2) % 4];
                
                // 해당 칸이 벽이 아닌 경우
                if (map[nextR][nextC] != 1) {
                    // 이동
                    r = nextR;
                    c = nextC;
                } else      // 벽인 경우 작동 종료
                    break;
            }
        }
        // 청소한 칸의 개수 출력
        System.out.println(count);
    }
}