/*
 Author : Ruel
 Problem : Baekjoon 2151번 거울 설치
 Problem address : https://www.acmicpc.net/problem/2151
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 거울설치;

import java.util.*;

class Pos {
    int r;
    int c;
    boolean isVertical;

    public Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public Pos(int r, int c, boolean isVertical) {
        this.r = r;
        this.c = c;
        this.isVertical = isVertical;
    }
}

public class Main {
    static int[][][] minMirror;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 시뮬레이션 문제.
        // 각 상태가 현재 위치와 빛의 방향도 갖고 있어야한다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        char[][] house = new char[n][n];

        List<Pos> exits = new ArrayList<>();
        sc.nextLine();
        for (int i = 0; i < house.length; i++) {
            String row = sc.nextLine();
            for (int j = 0; j < house[i].length; j++) {
                house[i][j] = row.charAt(j);

                if (house[i][j] == '#')
                    exits.add(new Pos(i, j));
            }
        }

        // 해당 지점에 도착했을 때 거친 최소 거울의 개수를 저장할 공간
        // minMirror[r][c][0]은 r, c지점에서 현재 빛의 상태가 가로인 경우
        // minMirror[r][c][1]은 r, c지점에서 현재 빛의 상태가 세로인 경우
        minMirror = new int[n][n][2];
        for (int[][] mMirror : minMirror) {
            for (int[] mM : mMirror)
                Arrays.fill(mM, 2500);
        }
        minMirror[exits.get(0).r][exits.get(0).c][0] = minMirror[exits.get(0).r][exits.get(0).c][1] = 0;    // 시작 지점에서 사용 거울의 개수는 0

        Queue<Pos> queue = new LinkedList<>();
        // 출발 지점에서 어떤 상황인지 모르므로, 세로와 가로 모두 넣어 시작하자.
        queue.add(new Pos(exits.get(0).r, exits.get(0).c, true));
        queue.add(new Pos(exits.get(0).r, exits.get(0).c, false));
        while (!queue.isEmpty()) {
            Pos current = queue.poll();
            if (current.r == exits.get(1).r && current.c == exits.get(1).c)     // 도착 지점에 도달했다면 이번 연산은 넘겨주고, queue가 빌 때까지 계산을 계속하자.
                continue;

            if (current.isVertical) {   // 현재 수직일 경우
                for (int d = 0; d < 2; d++) {
                    int nextR = current.r + dr[2 * d];  // 다음 위치도 수직으로 위 아래만 뽑는다.
                    int nextC = current.c + dc[2 * d];

                    if (checkArea(nextR, nextC, house)) {       // 범위 안에 있는지와 막혀있지 않은지 여부를 확인하고
                        if (minMirror[current.r][current.c][1] < minMirror[nextR][nextC][1]) {      // 수직으로 진행했을 때 nextR, nextC에서 최소 거울 개수가 갱신된다면
                            minMirror[nextR][nextC][1] = minMirror[current.r][current.c][1];        // current.r, current.c의 최소 거울 개수를 nextR, nextC에 넘겨주고,
                            queue.add(new Pos(nextR, nextC, true));                         // queue에도 넣어 다음번에 계산을 계속하자.
                        }

                        // 만약 nextR, nextC 지점이 거울이 설치가 가능한 공간이고, 그랬을 때 도달한 최소 거울 개수가 갱신된다면
                        if (house[nextR][nextC] == '!' && minMirror[current.r][current.c][1] + 1 < minMirror[nextR][nextC][0]) {
                            minMirror[nextR][nextC][0] = minMirror[current.r][current.c][1] + 1;    // 최소 거울 개수를 갱신하고
                            queue.add(new Pos(nextR, nextC, false));            // queue에 넣어 연산을 계속하자
                        }
                    }
                }
            } else {    // 현재 방향에 수평일 경우( = 수직인 경우와 동일)
                for (int d = 0; d < 2; d++) {
                    int nextR = current.r + dr[2 * d + 1];
                    int nextC = current.c + dc[2 * d + 1];

                    if (checkArea(nextR, nextC, house)) {
                        if (minMirror[current.r][current.c][0] < minMirror[nextR][nextC][0]) {
                            minMirror[nextR][nextC][0] = minMirror[current.r][current.c][0];
                            queue.add(new Pos(nextR, nextC, false));
                        }

                        if (house[nextR][nextC] == '!' && minMirror[current.r][current.c][0] + 1 < minMirror[nextR][nextC][1]) {
                            minMirror[nextR][nextC][1] = minMirror[current.r][current.c][0] + 1;
                            queue.add(new Pos(nextR, nextC, true));
                        }
                    }
                }
            }
        }

        // 답은 최종 도달지점에서 세로든 가로든 도달한 최소 거울의 개수.
        int answer = Math.min(minMirror[exits.get(1).r][exits.get(1).c][0], minMirror[exits.get(1).r][exits.get(1).c][1]);
        System.out.println(answer);
    }

    static boolean checkArea(int r, int c, char[][] house) {
        return r >= 0 && r < house.length && c >= 0 && c < house[r].length && house[r][c] != '*';
    }
}