/*
 Author : Ruel
 Problem : Baekjoon 23031번 으어어… 에이쁠 주세요..
 Problem address : https://www.acmicpc.net/problem/23031
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23031_으어어에이쁠주세요;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Zombie {
    int r;
    int c;
    int direction;

    public Zombie(int r, int c, int direction) {
        this.r = r;
        this.c = c;
        this.direction = direction;
    }
}

public class Main {
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};
    static char[][] map;
    static List<Zombie> zombies;
    static boolean[][] lighted, switches;
    static int[][] zombieCounter;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 공간이 주어지며 공간에는 O 빈 공간, S 스위치, Z 좀비를 나타낸다.
        // 주인공과 좀비 모두 처음에는 아래 방향을 바라보고 있다
        // 주어진 문자열 A가 주어지며 주인공은 이에 따라 (1, 1)에서부터 이동한다.
        // 도중에 벽을 만나 이동할 수 없는 경우, 이동을 무시한다.
        // 좀비는 자신의 방향대로 한 칸씩 이동하며, 벽에 부딪친 경우, 뒤로 돌아선다.
        // A는 F, L, R로 구성되어있으며, F는 앞으로 전진, L은 왼쪽으로 90도 방향 전환, R은 오른쪽으로 90도 방향 전환을 나타낸다.
        // 불이 꺼진 상태에서 좀비를 만나면 기절을 하게 되고,
        // 불이 켜진 상태에서 좀비를 만나면, 학생임을 알 수 있기 때문에 기절하지 않는다.
        // 스위치를 만난 경우, 좀비보다 빠르게 스위치에 접근해 스위치가 있는 칸과 인접한 8칸의 불을 밝힌다.
        // 주어진 문자열 A대로 움직일 때, 기절하는지 여부를 출력하라
        //
        // 시뮬레이션, 구현 문제
        // n이 그리 크지 않기 때문에, 직접 시뮬레이션 해보며 상황을 계산해나가면 된다.
        // 주의할 점으로는 좀비와 스위치가 같이 있을 수 있다는 점과 그 때 주인공이 해당 칸에 도착하면
        // 좀비를 보기보다 먼저 스위치를 켠다는 점이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 맵
        int n = Integer.parseInt(br.readLine());
        char[] a = br.readLine().toCharArray();

        map = new char[n][];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();
        
        // 불이 켜졌는지 여부
        lighted = new boolean[n][n];
        // 스위치의 위치
        switches = new boolean[n][n];
        // 해당 위치의 좀비의 수
        zombieCounter = new int[n][n];

        // 리스트로 좀비를 뽑아내고, 스위치의 위치 표시
        zombies = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'Z') {
                    zombies.add(new Zombie(i, j, 4));
                    zombieCounter[i][j]++;
                } else if (map[i][j] == 'S')
                    switches[i][j] = true;
            }
        }
        
        // 현재 위치의 방향
        int currentR = 0;
        int currentC = 0;
        int direction = 4;
        boolean meetZombie = false;
        for (int i = 0; i < a.length && !meetZombie; i++) {
            // 불이 꺼진 위치에서 좀비를 만났다면 기절
            if (!lighted[currentR][currentC] && zombieCounter[currentR][currentC] > 0) {
                meetZombie = true;
                break;
            }
            
            // 순서에 따라 A의 명령 수행
            switch (a[i]) {
                case 'F' -> {
                    int nextR = currentR + dr[direction];
                    int nextC = currentC + dc[direction];

                    if (checkArea(nextR, nextC)) {
                        currentR = nextR;
                        currentC = nextC;
                    }
                }
                case 'L' -> direction = (direction + 6) % 8;
                case 'R' -> direction = (direction + 2) % 8;
            }

            // 스위치를 만난 경우 불을 켠다.
            if (switches[currentR][currentC])
                lightOn(currentR, currentC);
            
            // 해당 위치에 불이 켜지지 않았는데, 좀비가 존재한다면 기절
            if (!lighted[currentR][currentC] && zombieCounter[currentR][currentC] > 0)
                meetZombie = true;
            // 좀비가 이동을 한다.
            zombieTurn();
        }
        // 최종적으로 기절 여부를 출력
        System.out.println(meetZombie ? "Aaaaaah!" : "Phew...");
    }

    // (r, c)와 인접한 8칸의 불을 밝힌다.
    static void lightOn(int r, int c) {
        lighted[r][c] = true;
        for (int d = 0; d < 8; d++) {
            int nearR = r + dr[d];
            int nearC = c + dc[d];

            if (checkArea(nearR, nearC))
                lighted[nearR][nearC] = true;
        }
    }

    // 모든 좀비를 방향대로 이동시키거나,
    // 벽에 부딪친 경우, 뒤돌아서게 한다.
    static void zombieTurn() {
        for (Zombie z : zombies) {
            int nextR = z.r + dr[z.direction];
            int nextC = z.c + dc[z.direction];

            if (checkArea(nextR, nextC)) {
                zombieCounter[z.r][z.c]--;
                zombieCounter[z.r = nextR][z.c = nextC]++;
            } else
                z.direction = (z.direction + 4) % 8;
        }
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}