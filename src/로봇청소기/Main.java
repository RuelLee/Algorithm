/*
 Author : Ruel
 Problem : Baekjoon 4991번 로봇 청소기
 Problem address : https://www.acmicpc.net/problem/4991
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 로봇청소기;

import java.util.*;

class Point {
    int r;
    int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

class State {
    int currentPoint;
    int removedDirt;
    int bitmask;
    int sum;

    public State(int currentPoint, int removedDirt, int bitmask, int sum) {
        this.currentPoint = currentPoint;
        this.removedDirt = removedDirt;
        this.bitmask = bitmask;
        this.sum = sum;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] matrix;
    static List<Point> points;
    static char[][] room;
    static final int MAX = 100000;

    public static void main(String[] args) {
        // 시뮬레이션 문제
        // 먼저 각 지점에서 다른 쓰레기까지의 거리를 구해 저장한다
        // 그 후, 로봇청소기에서 시작하여, 각각의 쓰레기를 순차적으로 방문하는 여러가지의 방법들 중 최소 거리를 구한다!
        Scanner sc = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();
        while (true) {
            int w = sc.nextInt();
            int h = sc.nextInt();

            if (w == 0 && h == 0)
                break;

            room = new char[h][w];
            points = new ArrayList<>();
            sc.nextLine();
            for (int i = 0; i < room.length; i++) {
                String input = sc.nextLine();
                for (int j = 0; j < input.length(); j++) {
                    room[i][j] = input.charAt(j);

                    if (room[i][j] == 'o')      // 로봇 청소기는 0번 위치에
                        points.add(0, new Point(i, j));
                    else if (room[i][j] == '*')     // 쓰레기는 그냥 추가.
                        points.add(new Point(i, j));
                }
            }
            matrix = new int[points.size()][points.size()];
            for (int[] mt : matrix)
                Arrays.fill(mt, MAX);
            int[][] minDistance = new int[h][w];
            for (int i = 0; i < points.size(); i++) {
                for (int[] md : minDistance)        // md 배열 초기화
                    Arrays.fill(md, MAX);
                minDistance[points.get(i).r][points.get(i).c] = 0;      // 시작점은 거리가 0
                bfs(points.get(i).r, points.get(i).c, i, minDistance);      // start로부터 다른 쓰레기까지의 거리를 구한 후, matrix에 저장해준다.
            }

            Queue<State> queue = new LinkedList<>();
            queue.offer(new State(0, 0, 1, 0));     // 시작 점은 로봇의 위치

            int distanceSum = MAX;      // 최종적인 최소 이동거리
            while (!queue.isEmpty()) {
                State current = queue.poll();

                if (current.removedDirt == points.size() - 1) {     // 현재 제거한 쓰레기가 총 쓰레기의 수와 같다면
                    distanceSum = Math.min(distanceSum, current.sum);
                    continue;
                }

                for (int i = 1; i < points.size(); i++) {       // 쓰레기들을 살펴보며
                    if ((current.bitmask & 1 << i) == 0)            // current에서 줍지 않은 쓰레기라면
                        queue.offer(new State(i, current.removedDirt + 1, current.bitmask | 1 << i, current.sum + matrix[current.currentPoint][i]));
                }
            }
            sb.append(distanceSum == MAX ? -1 : distanceSum).append("\n");
        }
        System.out.println(sb);
    }

    static void bfs(int startR, int startC, int start, int[][] minDistance) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(startR, startC));
        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (room[current.r][current.c] == '*' && !(current.r == startR && current.c == startC)) {       // 시작 곳이 아닌 쓰레기의 위치에 도달했다면
                for (int i = 1; i < points.size(); i++) {
                    if (current.r == points.get(i).r && current.c == points.get(i).c) {
                        matrix[start][i] = Math.min(matrix[start][i], minDistance[current.r][current.c]);   // 몇번째 쓰레기인지 찾아 거리값을 갱신
                        break;
                    }
                }
            }

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 다음 위치가 막혀있지 않고, 최소거리가 갱신된다면
                if (checkArea(nextR, nextC) && room[nextR][nextC] != 'x' && minDistance[nextR][nextC] > minDistance[current.r][current.c] + 1) {
                    minDistance[nextR][nextC] = minDistance[current.r][current.c] + 1;
                    queue.offer(new Point(nextR, nextC));
                }
            }
        }
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < room.length && c >= 0 && c < room[r].length;
    }
}