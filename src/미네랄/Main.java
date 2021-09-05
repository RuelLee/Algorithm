/*
 Author : Ruel
 Problem : Baekjoon 2933번 미네랄
 Problem address : https://www.acmicpc.net/problem/2933
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 미네랄;

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
    static int[] dr = {0, 0, 1, -1};
    static int[] dc = {1, -1, 0, 0};

    public static void main(String[] args) {
        // 시뮬레이션 문제는 차분하게 하나씩 하나씩 고려하며 구현해야한다.
        // 
        Scanner sc = new Scanner(System.in);

        int r = sc.nextInt();
        int c = sc.nextInt();

        sc.nextLine();
        char[][] map = new char[r][];
        for (int i = 0; i < r; i++)
            map[i] = sc.nextLine().toCharArray();

        int n = sc.nextInt();
        for (int i = 0; i < n; i++)
            throwStick(sc.nextInt(), (i & 1) == 0, map);

        StringBuilder sb = new StringBuilder();
        for (char[] ma : map)
            sb.append(String.valueOf(ma)).append("\n");
        System.out.println(sb);
    }

    static void throwStick(int height, boolean fromLeft, char[][] map) {        // 막대를 던지는 경우
        int r = map.length - height;
        int c = fromLeft ? 0 : map[r].length - 1;
        while (c >= 0 && c < map[r].length) {
            if (map[r][c] == 'x') {         // 돌을 만났다면
                map[r][c] = '.';            // 해당 돌을 부숴주고,
                for (int d = 0; d < 4; d++) {       // 4방을 둘러봐서
                    int nextR = r + dr[d];
                    int nextC = c + dc[d];

                    if (checkArea(nextR, nextC, map) && map[nextR][nextC] == 'x')       // 각 위치에 돌이 존재한다면 별개의 클러스터로 판단하고 떨어뜨려준다.
                        moveCluster(nextR, nextC, map);
                }
                break;
            }

            if (fromLeft)
                c++;
            else
                c--;
        }
    }

    static void moveCluster(int r, int c, char[][] map) {
        Queue<Point> queue = new LinkedList<>();        // 연결되어있는 클러스터를 탐색할 큐
        List<Point> cluster = new ArrayList<>();        // 클러스터 요소들을 기록할 리스트
        queue.offer(new Point(r, c));
        cluster.add(new Point(r, c));
        map[r][c] = '.';        // 현재 위치는 지워준다

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int d = 0; d < 4; d++) {       // 4방을 둘러보며
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC, map) && map[nextR][nextC] == 'x') {     // 돌이 있다면
                    queue.offer(new Point(nextR, nextC));       // 다음 순서에 탐색하기 위해 큐에 삽입
                    cluster.add(new Point(nextR, nextC));       // 전체 클러스터에 표시하기 위해 리스트에 추가
                    map[nextR][nextC] = '.';            // 현재 위치는 빈 공간으로 만들어준다.
                }
            }
        }
        int minDistanceFromFloor = cluster.isEmpty() ? 0 : Integer.MAX_VALUE;

        for (Point p : cluster)     // 클러스트 요소들을 탐색하며, 각 요소들 중 가장 작은 하락 높이를 계산해준다.
            minDistanceFromFloor = Math.min(minDistanceFromFloor, calcFromFloor(p.r, p.c, map));

        for (Point p : cluster)         // 클러스트 내 모든 요소들에게 가장 작은 하락 높이만큼 떨어뜨려준다.
            map[p.r + minDistanceFromFloor][p.c] = 'x';
    }

    static int calcFromFloor(int r, int c, char[][] map) {      // 다른 돌이나, 지면으로부터의 높이를 구해준다.
        int height = 0;
        while (r + height + 1 < map.length && map[r + height + 1][c] == '.')
            height++;
        return height;
    }

    static boolean checkArea(int r, int c, char[][] map) {      // 범위 밖으로 벗어나는지 체크
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}