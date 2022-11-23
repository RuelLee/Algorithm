/*
 Author : Ruel
 Problem : Baekjoon 23288번 주사위 굴리기 2
 Problem address : https://www.acmicpc.net/problem/23288
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23288_주사위굴리기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 주사위
class Dice {
    // 주어지는 전개도에 따른 초기값
    int top = 1;
    int bottom = 6;
    int east = 3;
    int west = 4;
    int north = 2;
    int south = 5;
    int direction = 0;

    // 각 동서남북으로 이동할 때 값의 변화.
    void goEast() {
        int temp = top;
        top = west;
        west = bottom;
        bottom = east;
        east = temp;
    }

    void goWest() {
        int temp = top;
        top = east;
        east = bottom;
        bottom = west;
        west = temp;
    }

    void goSouth() {
        int temp = top;
        top = north;
        north = bottom;
        bottom = south;
        south = temp;
    }

    void goNorth() {
        int temp = top;
        top = south;
        south = bottom;
        bottom = north;
        north = temp;
    }
}

public class Main {
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 지도와 주사위의 전개도가 주어진다.
        // 주사위는 위 1, 아래 6, 서4, 동 3, 북 2, 남 5의 눈을 처음 갖고 있고, 이동 방향은 동쪽이다.
        // 주사위의 이동은
        // 1. 이동 방향으로 한 칸 구른다. 만약 칸이 없다면 반대방향으로 구른다.
        // 2. 해당 칸의 점수를 획득한다.
        // 3. 주사위의 아랫면과 칸의 값을 비교하여
        //   1) 주사위 > 칸 -> 시계 방향 90도 방향으로 이동 방향 회전
        //   2) 칸 > 주사위 -> 반시계  방향 90도로 이동 방향 회전
        //   3) 같을 경우 변화 없음
        // 해당 칸의 점수는 해당 칸의 값 * (연속해서 인접해있는 같은 값의 칸의 수)이다
        // 주사위의 이동 횟수가 주어질 때, 얻는 점수를 출력하라
        //
        // 구현 + 그래프 탐색 문제
        // 먼저 각 칸의 점수를 구해야한다.
        // 연속해서 인접해 있는 칸의 수 * 해당 칸의 값이므로, 너비우선탐색으로
        // 방문하지 않았던 칸에 도착한다면 인접한 모든 같은 칸의 개수를 찾고
        // 해당 칸의 개수 * 칸의 값을 찾은 모든 칸의 점수로 기록해준다.
        //
        // 그리고 이제 주사위를 굴려야한다.
        // 0, 0에서 처음 방향은 동쪽임을 기억하고
        // 각 주사위가 동서남북으로 굴러갈 때 주사위의 값이 어떻게 바뀌는지 전개도를 고려하여 값을 변경시켜주자
        // 그리고 각 이동마다 칸이 존재하는지, 해당 칸의 점수, 주사위 아랫면과 칸의 값을 비교하여 방향을 지정해주는 작업을 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 입력으로 주어지는 칸의 값
        int[][] map = new int[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 칸의 점수를 구한다.
        int[][] scores = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 이미 계산된 결과가 있다면 건너뛴다.
                if (scores[i][j] != 0)
                    continue;

                // 해쉬셋이 인접한 칸들을 기록한다.
                HashSet<Integer> hashSet = new HashSet<>();
                hashSet.add(i * m + j);
                // 큐를 통해 너비우선탐색
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i * m + j);
                // 큐가 빌 때까지
                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    int row = current / m;
                    int col = current % m;

                    // 사방탐색을 한다.
                    for (int d = 0; d < 4; d++) {
                        int nextR = row + dr[d];
                        int nextC = col + dc[d];

                        // 범위를 벗어나지 않으며
                        // 이미 찾았던 칸이 아니고
                        // 같은 값을 갖고 있는 새로운 칸을 찾는다면
                        if (checkArea(nextR, nextC, map) &&
                                !hashSet.contains(nextR * m + nextC) &&
                                map[row][col] == map[nextR][nextC]) {
                            // 해쉬셋이 기록하고, 큐에 삽입한다.
                            hashSet.add(nextR * m + nextC);
                            queue.offer(nextR * m + nextC);
                        }
                    }
                }
                // 찾아진 모든 연속한 인접 칸들에 대해서
                for (int idx : hashSet) {
                    int row = idx / m;
                    int col = idx % m;

                    // 해당 칸의 값 * 개수로 점수를 기록해준다.
                    scores[row][col] = map[row][col] * hashSet.size();
                }
            }

        }

        // 주사위의 현재 위치.
        int r = 0;
        int c = 0;
        Dice dice = new Dice();
        // 점수 총합
        int totalScore = 0;
        // 이동 횟수만큼 이동한다.
        while (k > 0) {
            // 이동방향에 따른 다음 위치를 계산한다.
            int nextR = r + dr[dice.direction];
            int nextC = c + dc[dice.direction];

            // 만약 칸을 벗어난다면 이동방향을 바꾸고
            // 이동횟수 차감 없이 다시 계산한다.
            if (!checkArea(nextR, nextC, map)) {
                dice.direction = (dice.direction + 2) % 4;
                continue;
            }
            
            // 범위를 벗어나지 않았다면
            // 이동 방향에 따른 주사위 값들을 변경시켜주고
            switch (dice.direction) {
                case 0 -> dice.goEast();
                case 1 -> dice.goSouth();
                case 2 -> dice.goWest();
                case 3 -> dice.goNorth();
            }
            // 위치 변경
            r = nextR;
            c = nextC;

            // 주사위 아랫면과 칸의 값을 비교하여 방향을 변경시켜준다.
            if (dice.bottom > map[r][c])
                dice.direction = (dice.direction + 1) % 4;
            else if (dice.bottom < map[r][c])
                dice.direction = (dice.direction + 3) % 4;

            // 이번 칸에서 얻은 점수를 더해주고
            totalScore += scores[r][c];
            // 이동횟수를 하나 차감한다.
            k--;
        }
        // 점수 총합을 출력.
        System.out.println(totalScore);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}