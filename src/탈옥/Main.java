/*
 Author : Ruel
 Problem : Baekjoon 9376번 탈옥
 Problem address : https://www.acmicpc.net/problem/9376
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 탈옥;

import java.util.*;

class Point {
    int r;
    int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static char[][] prison;
    static int[][][] minKeys;
    static final int MAX = 10_000;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 개념 생각이 너무 어렵다 ㅠㅠ
        // 감옥 안에 죄수 두명과 밖에서 이를 빼내려는 사람 한 명이 있다.
        // 열쇠가 필요한 문을 #으로 표시할 때, 두 죄수를 모두 밖으로 꺼내는데 필요한 최소한의 열쇠의 개수를 구하기 문제
        // 각각의 시점에서 BFS를 돌려 모든 지점에 방문하기 위한 최소한의 열쇠의 개수를 구한다
        // 구해진 값을 토대로 필요한 열쇠의 개수를 산출한다.
        // 1. 세 명이 한 지점에 만난 경우
        //  -> 각각의 열쇠 개수를 더해서 필요한 열쇠의 개수를 구하되, 만난 지점이 #인 문이었다면, 세 명 모두 열쇠를 사용하여 총 3개가 사용됐다. 하지만 원래 한 명만 열면 되는 것이니, 2개를 빼준다.
        // 2. 두 명의 죄수가 각각 밖으로 나가는 경우
        //  -> 죄수끼리는 만나지 않고, 밖으로 나가는 경우다. 밖에 있는 사람과 각각의 죄수 간에 최소한의 열쇠의 개수로 만나는 경우를 체크하고, 마찬가지로 문에서 만났다면 2명이 2개의 열쇠를 사용했으므로, 하나를 빼준다.
        // 총 필요한 개수는 두 죄수가 사용한 열쇠의 합
        // 1번과 2번 중 더 작은 값을 사용한 경우가 최소 열쇠의 개수
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int h = sc.nextInt();
            int w = sc.nextInt();

            // 밖으로 통하는 지점을 최외곽이 .이나 #인 경우를 세서 해도 되지만, 가로, 세로로 두칸을 늘려주고 .으로 채워준다면 밖에서 최외곽 아무 곳에서 BFS를 시작해도 같은 값이 채워진다.
            prison = new char[h + 2][w + 2];
            minKeys = new int[h + 2][w + 2][3];
            init();

            sc.nextLine();
            List<Point> prisoner = new ArrayList<>();
            for (int i = 1; i < prison.length - 1; i++) {
                String line = sc.nextLine();
                for (int j = 1; j < prison[i].length - 1; j++) {
                    prison[i][j] = line.charAt(j - 1);
                    if (prison[i][j] == '$')
                        prisoner.add(new Point(i, j));
                }
            }


            for (int i = 0; i < prisoner.size(); i++) {
                minKeys[prisoner.get(i).r][prisoner.get(i).c][i] = 0;       // 죄수의 위치의 필요 열쇠의 개수는 0
                Queue<Point> queue = new LinkedList<>();
                queue.offer(new Point(prisoner.get(i).r, prisoner.get(i).c));   // 큐에 죄수의 위치를 넣고
                fillMinKeys(queue, i);       // bfs로 계산!
            }
            minKeys[0][0][2] = 0;       // 밖에서 들어가는 경우의 계산
            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(0, 0));
            fillMinKeys(queue, 2);

            int KeysUsedPrisonersMeet = MAX;            // 3명이 한 곳에서 만나는 경우 계산
            for (int i = 0; i < minKeys.length; i++) {
                for (int j = 0; j < minKeys[i].length; j++) {
                    int keySums = minKeys[i][j][0] + minKeys[i][j][1] + minKeys[i][j][2];
                    if (prison[i][j] == '#')        // 문에서 만났다면 필요한 열쇠의 개수는 3이 아닌 1이므로 2개를 빼준다.
                        keySums -= 2;
                    if (KeysUsedPrisonersMeet > keySums)
                        KeysUsedPrisonersMeet = keySums;
                }
            }

            // 죄수가 각각 별도의 루트로 탈출하는 경우
            int aPrisonerMinKeys = MAX;
            int bPrisonerMinKeys = MAX;
            for (int i = 0; i < minKeys.length; i++) {
                for (int j = 0; j < minKeys[i].length; j++) {
                    int aPrisoner = minKeys[i][j][0] + minKeys[i][j][2];
                    int bPrisoner = minKeys[i][j][1] + minKeys[i][j][2];

                    if (prison[i][j] == '#') {      // 죄수와 조력자가 문에서 만난 경우, 필요 열쇠의 개수는 2개가 아닌 1개 이므로 하나를 빼준다.
                        aPrisoner--;
                        bPrisoner--;
                    }
                    if (aPrisoner < aPrisonerMinKeys)
                        aPrisonerMinKeys = aPrisoner;
                    if (bPrisoner < bPrisonerMinKeys)
                        bPrisonerMinKeys = bPrisoner;
                }
            }
            // 세명이 한 곳에서 만난 경우의 열쇠의 개수와 각각 별도의 루트로 탈출한 경우 중 작은 값이 답.
            sb.append(KeysUsedPrisonersMeet < aPrisonerMinKeys + bPrisonerMinKeys ? KeysUsedPrisonersMeet : aPrisonerMinKeys + bPrisonerMinKeys).append("\n");
        }
        System.out.println(sb);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < prison.length && c >= 0 && c < prison[r].length;
    }

    static void fillMinKeys(Queue<Point> queue, int identifier) {
        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC) && prison[nextR][nextC] != '*') {
                    if (prison[nextR][nextC] == '#' && minKeys[nextR][nextC][identifier] > minKeys[current.r][current.c][identifier] + 1) {
                        minKeys[nextR][nextC][identifier] = minKeys[current.r][current.c][identifier] + 1;
                        queue.offer(new Point(nextR, nextC));
                    } else if ((prison[nextR][nextC] == '.' || prison[nextR][nextC] == '$') && minKeys[nextR][nextC][identifier] > minKeys[current.r][current.c][identifier]) {
                        minKeys[nextR][nextC][identifier] = minKeys[current.r][current.c][identifier];
                        queue.offer(new Point(nextR, nextC));

                    }
                }
            }
        }
    }

    static void init() {
        for (int i = 0; i < prison.length; i++) {
            if (i == 0 || i == prison.length - 1)
                Arrays.fill(prison[i], '.');
            else
                prison[i][0] = prison[i][prison[i].length - 1] = '.';
        }

        for (int[][] mk : minKeys) {
            for (int[] m : mk)
                Arrays.fill(m, MAX);
        }
    }
}