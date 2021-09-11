/*
 Author : Ruel
 Problem : Baekjoon 2931번 가스관
 Problem address : https://www.acmicpc.net/problem/2931
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 가스관;

import java.util.Scanner;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 시뮬레이션 문제
        // 진행중인 방향과, 현재 블럭으로 다음 방향을 계산해내, 다음 블럭으로 이동하면 된다
        // 그러다 아무것도 없는 공간을 만났다면, 현재 진행방향을 제외한 다른 4방향 중, 현재 블럭 방향으로 뚫려있는 파이프를 찾는다
        // 만약 복수의 파이프가 현재 블럭 방향으로 뚫려있다면 이는 + 블럭이 놓여야한다
        Scanner sc = new Scanner(System.in);

        int r = sc.nextInt();
        int c = sc.nextInt();

        char[][] map = new char[r][c];
        sc.nextLine();

        int startR = 0;
        int startC = 0;
        for (int i = 0; i < map.length; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = line.charAt(j);

                if (map[i][j] == 'M') {
                    startR = i;
                    startC = j;
                }
            }
        }

        int direction = 0;
        for (int d = 0; d < 4; d++) {       // 시작점으로부터 네방향을 살펴보고,
            int nextR = startR + dr[d];
            int nextC = startC + dc[d];

            if (checkArea(nextR, nextC, map) && map[nextR][nextC] != '.' && nextDirection(map[nextR][nextC], d + 1) != -1) {        // 연결된 파이프가 있다면
                startR = nextR;     // 해당 파이프로 이동하고
                startC = nextC;
                direction = d + 1;      // 진행 방향을 입력해준다.
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        char answer = '.';
        while (map[startR][startC] != 'Z') {        // Z에 도착할 때까지
            if (map[startR][startC] == '.') {       // 만약 파이프가 비어있다면
                sb.append((startR + 1)).append(" ").append((startC + 1)).append(" ");
                boolean connected = false;
                for (int d = 0; d < 4; d++) {
                    if ((direction + 1) % 4 == d)       // 4방 중 직전 방향은 생략
                        continue;

                    int nextR = startR + dr[d];
                    int nextC = startC + dc[d];

                    if (checkArea(nextR, nextC, map) && nextDirection(map[nextR][nextC], d + 1) != -1) {        // 현재 블럭쪽으로 뚫려있다면
                        if (connected) {        // 이미 한번 파이프를 놓은 적이 있다면(= 복수의 파이프가 현재 블럭으로 뚫려있다면)
                            map[startR][startC] = '+';      // 블럭을 +로 교체
                            answer = map[startR][startC];
                            break;
                        }
                        map[startR][startC] = rightBlock(direction, d + 1);     // 이전 위치와 다음 위치를 고려하여 현재 위치에 블럭을 놓는다.
                        answer = map[startR][startC];
                        connected = true;       // 한번 블럭을 놓았다고 표시. connected가 true인데, 또 다른 파이프를 찾았다면 +로 교체해줄 것이다.
                    }
                }
                sb.append(answer);
            }
            int delta = nextDirection(map[startR][startC], direction) - 1;      // 현재 방향에 맞는 delta 값으로 startR과 startC를 변경.
            startR += dr[delta];
            startC += dc[delta];
            direction = delta + 1;      // 방향도 변경해준다.
        }
        System.out.println(sb);
    }

    static char rightBlock(int preDirection, int nextDirection) {       // 이전 블럭에서 현재 블럭으로 온 방향과 현재 블럭에서 다음 블럭으로 이동할 방향을 입력하면 그에 올바른 파이프 모양을 리턴한다.
        // 1 -> UP, 2 -> RIGHT, 3 -> DOWN, 4 -> LEFT
        if (preDirection == 3) {        // 방향이 DOWN이라면(= 이전 블럭이 위에 있다면)
            if (nextDirection == 3)     // 방향이 유지되려면 | 블럭
                return '|';
            else if (nextDirection == 4)        // 방향이 left로 바뀌려면 ┘ 블럭
                return '3';
            else if (nextDirection == 2)        // 방향이 right로 바뀌려면 └ 블럭
                return '2';
        } else if (preDirection == 2) {
            if (nextDirection == 2)
                return '-';
            else if (nextDirection == 1)
                return '3';
            else if (nextDirection == 3)
                return '4';
        } else if (preDirection == 1) {
            if (nextDirection == 1)
                return '|';
            else if (nextDirection == 2)
                return '1';
            else if (nextDirection == 4)
                return '4';
        } else if (preDirection == 4) {
            if (nextDirection == 4)
                return '-';
            else if (nextDirection == 3)
                return '1';
            else if (nextDirection == 1)
                return '2';
        }
        return '.';
    }

    static int nextDirection(char c, int currentDirection) {        // 현재 블럭과 진입 방향을 알려주면, 탈출 방향을 알려준다.
        // 1 -> UP, 2 -> RIGHT, 3 -> DOWN, 4 -> LEFT
        if (currentDirection == 1) {
            if (c == '|' || c == '+')
                return 1;
            else if (c == '1')
                return 2;
            else if (c == '4')
                return 4;
        } else if (currentDirection == 2) {
            if (c == '-' || c == '+')
                return 2;
            else if (c == '3')
                return 1;
            else if (c == '4')
                return 3;
        } else if (currentDirection == 3) {
            if (c == '|' || c == '+')
                return 3;
            else if (c == '2')
                return 2;
            else if (c == '3')
                return 4;
        } else if (currentDirection == 4) {
            if (c == '-' || c == '+')
                return 4;
            else if (c == '1')
                return 3;
            else if (c == '2')
                return 1;
        }
        return -1;
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}