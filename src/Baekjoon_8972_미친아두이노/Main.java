/*
 Author : Ruel
 Problem : Baekjoon 8972번 미친 아두이노
 Problem address : https://www.acmicpc.net/problem/8972
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8972_미친아두이노;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int r, c;
    static int[] dr = {0, 1, 1, 1, 0, 0, 0, -1, -1, -1};
    static int[] dc = {0, -1, 0, 1, -1, 0, 1, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        // r * c 크기의 격자 안에 플레이어 i와 미친 아두이노 R이 주어진다.
        // 다음과 같이 게임이 진행된다.
        // 1. 플레이어가 8가지 방향으로 이동하거나 가만히 있는다.
        // 2. 플레이어가 미친 아두이노의 칸으로 이동하는 경우, 게임이 종료된다.
        // 3. 미친 아두이노들은 8가지 방향 중, 플레이어에게 가장 가까워지는 방향으로 한 칸 이동한다.
        // 4. 미친 아두이노가 종수가 있는 칸으로 이동한 경우, 게임은 끝난다.
        // 5. 2개 이상의 미친 아두이노들이 한 칸에 모이는 경우, 그 칸의 모든 아두이노들은 파괴된다.
        // 플레이어가 움직이는 방향이 주어질 때
        // 게임이 끝나지 않는다면, 그러한 상황을 출력하고, 게임이 끝났다면 몇 번째 턴에 끝났는지를 출력하라
        //
        // 구현, 시뮬레이션 문제
        // 주어진 조건에 따라 충실히 구현하면 되는 문제
        // 다만 모든 아두이노들이 동시에 움직이므로, 아두이노들끼리 만났을 때
        // 지금 만난 것이 이전 턴의 아두이노인지, 현재 턴의 아두이노인지 구분해야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c크기의 판
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 2개의 판을 만들어, 번갈아가며 이용한다.
        char[][][] map = new char[2][r][c];
        for (int i = 0; i < map[0].length; i++)
            map[0][i] = br.readLine().toCharArray();
        
        // 아두이노들의 위치
        Queue<Integer>[] enemies = new Queue[2];
        for (int i = 0; i < enemies.length; i++)
            enemies[i] = new LinkedList<>();
        // 플레이어
        int player = 0;
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map[0][i].length; j++) {
                if (map[0][i][j] == 'R')
                    enemies[0].offer(i * c + j);
                else if (map[0][i][j] == 'I')
                    player = i * c + j;
            }
        }

        String directions = br.readLine();
        // 끝났는지 여부
        int finished = -1;
        // 아두이노들이 모여 터졌는지 여부
        boolean[][] boom = new boolean[r][c];
        for (int i = 0; i < directions.length() && finished == -1; i++) {
            // boom과 다음 턴의 map 초기화
            for (boolean[] b : boom)
                Arrays.fill(b, false);
            for (char[] ma : map[(i + 1) % 2])
                Arrays.fill(ma, '.');
            
            // 현재 플레이어가 움직이는 방향
            int direction = directions.charAt(i) - '0';
            
            // 위치
            int nextR = player / c + dr[direction];
            int nextC = player % c + dc[direction];
            // 미친 아두이노의 위치라면 게임 종료
            if (map[i % 2][nextR][nextC] == 'R') {
                finished = i + 1;
                break;
            } else {
                // 그 외의 다음 턴의 플레이어 위치 기록
                map[(i + 1) % 2][nextR][nextC] = 'I';
                player = nextR * c + nextC;
            }

            // 아두이노들을 움직임
            while (!enemies[i % 2].isEmpty()) {
                // 현재 위치
                int enemy = enemies[i % 2].poll();

                // 플레이어와 가장 가까워지는 방향을 찾는다.
                int minDistance = Integer.MAX_VALUE;
                direction = 5;
                for (int d = 1; d < dr.length; d++) {
                    nextR = enemy / c + dr[d];
                    nextC = enemy % c + dc[d];

                    if (checkArea(nextR, nextC) && calcDistance(player, nextR * c + nextC) < minDistance) {
                        minDistance = calcDistance(player, nextR * c + nextC);
                        direction = d;
                    }
                }
                nextR = enemy / c + dr[direction];
                nextC = enemy % c + dc[direction];
                
                // 다음 칸에 플레이어가 있다면 게임 종료
                if (map[(i + 1) % 2][nextR][nextC] == 'I') {
                    finished = i + 1;
                    break;
                } else if (map[(i + 1) % 2][nextR][nextC] == 'R')       // 아두이노가 있다면 터짐 예약
                    boom[nextR][nextC] = true;
                else {
                    // 그 외의 경우 이동
                    map[(i + 1) % 2][nextR][nextC] = 'R';
                    enemies[(i + 1) % 2].offer(nextR * c + nextC);
                }
            }
            
            // 판을 돌아보며, 아두이노들끼리 모여 터지는 곳이 있는지 체크
            for (int j = 0; j < map[(i + 1) % 2].length; j++) {
                for (int k = 0; k < map[(i + 1) % 2][j].length; k++) {
                    if (boom[j][k]) {
                        // 있다면 큐에서 해당 아두이노를 지우고,
                        // 맵에서도 치운다.
                        enemies[(i + 1) % 2].remove(j * c + k);
                        map[(i + 1) % 2][j][k] = '.';
                    }
                }
            }
        }
        
        // 플레이어가 패배했다면 kraj와 해당 턴 출력
        if (finished != -1)
            System.out.println("kraj " + finished);
        else {
            // 그 외의 경우는 맵 상태 출력
            StringBuilder sb = new StringBuilder();
            for (char[] m : map[directions.length() % 2]) {
                for (char p : m)
                    sb.append(p);
                sb.append("\n");
            }
            System.out.print(sb);
        }
    }
    
    // play와 enemy의 거리 계산
    static int calcDistance(int player, int enemy) {
        return Math.abs(player / c - enemy / c) + Math.abs(player % c - enemy % c);
    }
    
    // 맵 범위 체크
    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}