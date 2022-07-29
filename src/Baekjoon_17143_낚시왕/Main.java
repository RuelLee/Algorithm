/*
 Author : Ruel
 Problem : Baekjoon 17143번 낚시왕
 Problem address : https://www.acmicpc.net/problem/17143
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17143_낚시왕;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Shark {
    int r;
    int c;
    int speed;
    int direction;
    int size;
    boolean fished;

    public Shark(int r, int c, int speed, int direction, int size, boolean fished) {
        this.r = r;
        this.c = c;
        this.speed = speed;
        this.direction = direction;
        this.size = size;
        this.fished = fished;
    }
}

public class Main {
    static int[] dr = {0, -1, 1, 0, 0};
    static int[] dc = {0, 0, 0, 1, -1};
    static Shark[] sharks;
    static int[][] fishing;

    public static void main(String[] args) throws IOException {
        // 낚시터가 r, c의 크기로 주어지고, m마리의 상어가 위치, 속력, 방향, 크기가 주어진다.
        // 한 칸에는 한 마리의 상어만 있을 수 있고, 중복된 상어가 존재한다면 큰 상어가 작은 상어를 잡아먹는다.
        // 매 턴마다 주인공은 0에서부터
        // 1. 오른쪽으로 한 칸 이동.
        // 2. 열에 있는 상어들 중 가장 가까운 상어를 포획.
        // 3. 상어가 속력과 방향에 따라 이동.
        // 이 이루어진다.
        // 주인공이 모든 열을 거쳤을 때, 얻을 수 있는 상어들의 크기 합은?
        //
        // 시뮬레이션 문제
        // 침착하게 문제에서 요구하는 사항들을 구현해나가면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 몇 번 상어가 존재하는지 표시한다.
        fishing = new int[r][c];
        // 각 상어들의 정보.
        sharks = new Shark[m + 1];
        for (int i = 1; i < sharks.length; i++) {
            st = new StringTokenizer(br.readLine());
            // 1, 1이 아니라 0, 0부터 사용할 것이므로, row, col을 각각 하나씩 빼준다.
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            int speed = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());
            // 속도가 낚시터의 크기보다 많이 크다면, 원래 위치에 원래 방향으로 돌아오는 만큼의 속도는 고려할 필요가 없다.
            // 만약 상어의 방향이 세로라면 낚시터의 세로의 크기 * 2 - 2의 속도는 의미가 없다.
            // 따라서 해당 값 만큼 속도를 모듈러 연산해서 유의미한 속도만 남겨둔다.
            // 가로도 마찬가지.
            if (direction <= 2)
                speed %= (r * 2 - 2);
            else
                speed %= (c * 2 - 2);
            int size = Integer.parseInt(st.nextToken());

            // row, col에 i번 상어가 존재한다고 표시.
            fishing[row][col] = i;
            // 상어의 정보로도 넣어주자.
            sharks[i] = new Shark(row, col, speed, direction, size, false);
        }

        // 포획한 상어들의 크기 합
        int sum = 0;
        for (int col = 0; col < c; col++) {
            // 매턴 주인공이 낚시를 하고,
            sum += fishingShark(col);
            // 상어가 이동하는 것을 반복한다.
            moveSharks();
        }
        // 최종적으로 잡은 상어의 크기 반환
        System.out.println(sum);
    }

    // 상어를 이동시킨다.
    static void moveSharks() {
        // 낚시터를 새로 선언해주고,
        fishing = new int[fishing.length][fishing[0].length];
        for (int i = 1; i < sharks.length; i++) {
            // 이미 포획되거나 먹힌 상어라면 건너뛴다.
            if (sharks[i].fished)
                continue;

            // 이동해야할 칸 수는 상어의 속도만큼.
            int move = sharks[i].speed;
            // i 상어의 r과 c
            int r = sharks[i].r;
            int c = sharks[i].c;
            while (move > 0) {
                // 현재 방향으로 이동한다면 다음 위치.
                int nextR = r + dr[sharks[i].direction];
                int nextC = c + dc[sharks[i].direction];
                // 혹시 다음 위치가 낚시터를 벗어난다면
                if (nextR < 0 || nextR >= fishing.length ||
                        nextC < 0 || nextC >= fishing[nextR].length) {
                    // 방향이 위, 오른쪽인 경우는 하나를 증가시켜
                    // 아래, 왼쪽로 만들어주고
                    if (sharks[i].direction % 2 == 1)
                        sharks[i].direction++;
                    // 반대인 경우는 하나를 감소시켜 역방향으로 만들어주자.
                    else
                        sharks[i].direction--;
                    // 방향만 변경했으므로 move는 감소시킬 필요없다.
                    continue;
                }
                // 이동한 위치를 r과 c에 반영
                r = nextR;
                c = nextC;
                move--;
            }
            // 이번 턴에 상어의 최종 위치.
            sharks[i].r = r;
            sharks[i].c = c;

            // 만약 이미 같은 곳에 이번 턴에 이동한 상어가 존재한다면
            if (fishing[r][c] != 0) {
                // 서로의 크기를 비교해 큰 상어를 남겨둔다.
                if (sharks[i].size > sharks[fishing[r][c]].size) {
                    sharks[fishing[r][c]].fished = true;
                    fishing[r][c] = i;
                } else
                    sharks[i].fished = true;
                // 그렇지 않다면, i번 상어가 해당 r, c에 위치.
            } else
                fishing[r][c] = i;
        }
    }

    // 상어를 잡는다.
    static int fishingShark(int col) {
        // col이 주어지면, 해당 col에서 가장 가까운 상어를 잡는다.
        for (int row = 0; row < fishing.length; row++) {
            int sharkNum = fishing[row][col];
            // 상어가 존재한다면,
            if (sharkNum != 0) {
                // 낚시터에서 제거
                fishing[row][col] = 0;
                // sharkNum 상어를 포획했다 남겨두기
                sharks[sharkNum].fished = true;
                // sharkNum의 상어 크기 반환.
                return sharks[sharkNum].size;
            }
        }
        // 상어를 잡지 못했다면 0 반환.
        return 0;
    }
}