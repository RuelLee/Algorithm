/*
 Author : Ruel
 Problem : Baekjoon 3197번 백조의 호수
 Problem address : https://www.acmicpc.net/problem/3197
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 백조의호수;

import java.util.*;

class Point {
    int r;
    int c;
    int cost;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public Point(int r, int c, int cost) {
        this.r = r;
        this.c = c;
        this.cost = cost;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 시뮬레이션 문제
        // 날짜를 직접 진행시켜가며 얼음을 녹일 경우 많은 시간이 소요될 것이다.
        // 각 얼음이 녹을 때까지 필요한 날짜를 적어주고 백조 끼리의 길을 찾을 때 참고하도록 하자
        // 먼저 물의 좌표를 구해 인접한 얼음에는 1로 적어주자
        // 위 작업과 동시에 1인 좌표들도 저장해뒀다가, 다음 차례에 인접한 얼음 중 아직 녹지 않은 얼음에는 2로 표시해주자 -> 반복
        // 위와 같이 작업을 했다면 각 얼음이 녹을 때까지의 시간이 적혀있을 것이다
        // 백조1로부터 백조2까지의 길을 찾아나가되, 동일한 좌표라 하더라도 어디서 왔느냐에 따라 소요되는 시일이 다를 수 있다.
        // 따라서 또 길을 찾는 과정 중에 도착하는 해당 좌표에 최소 도착 시일을 기록해두록 하자(같은 시일이 소요되거나 더 많은 시일이 소요된다면 무시할 수 있도록)
        // 위 사항을 따라 길을 찾고, 그 중 거쳐왔던 얼음의 최댓값이 가장 적은 값을 구하면 된다.
        Scanner sc = new Scanner(System.in);

        int r = sc.nextInt();
        int c = sc.nextInt();
        char[][] lake = new char[r][c];

        List<Point> birds = new ArrayList<>();
        Queue<Point> water = new LinkedList<>();
        sc.nextLine();
        for (int i = 0; i < lake.length; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < lake[i].length; j++) {
                lake[i][j] = line.charAt(j);

                if (lake[i][j] == 'L') {        // 백조의 좌표
                    birds.add(new Point(i, j));
                    water.add(new Point(i, j));
                } else if (lake[i][j] == '.')   // 물의 좌표
                    water.offer(new Point(i, j));
            }
        }
        int[][] daysToWater = new int[r][c];    // 얼음이 녹을 때까지의 날짜를 적어둘 이차원배열
        for (int[] dtw : daysToWater)
            Arrays.fill(dtw, 10000);

        for (Point p : water)
            daysToWater[p.r][p.c] = 0;

        while (!water.isEmpty()) {
            Point current = water.poll();

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 해당 얼음이 현재 기입된 값보다 녹는 데 더 적은 일이 소요된다면 값을 갱신해주자.
                if (checkArea(nextR, nextC, lake) && lake[nextR][nextC] == 'X' && daysToWater[current.r][current.c] + 1 < daysToWater[nextR][nextC]) {
                    daysToWater[nextR][nextC] = daysToWater[current.r][current.c] + 1;
                    water.offer(new Point(nextR, nextC));
                }
            }
        }
        // -- 여기까지 얼음이 녹는 일 기입 끝 --

        // 이제 길을 찾자.
        // 시작점은 첫번째 백조의 위치
        PriorityQueue<Point> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        priorityQueue.offer(new Point(birds.get(0).r, birds.get(0).c, 0));

        int[][] minCost = new int[r][c];
        for (int[] mc : minCost)
            Arrays.fill(mc, 10000);
        int answer = 0;
        while (!priorityQueue.isEmpty()) {
            Point current = priorityQueue.poll();
            // 우선순위큐이므로 도착하자마자 종료시키면 된다.
            if (current.r == birds.get(1).r && current.c == birds.get(1).c) {
                answer = current.cost;
                break;
            }

            //
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 다음 이동 공간에 도달하는 날짜가
                // 1. Point current까지 도달하는데 소요된 일 = current.cost
                // 2. nextR, nextC의 좌표의 얼음이 녹는데 소요되는 일 = daysToWater[nextR][nextC] 중 큰 값보다 더 적은 일을 소요한다면
                // minCost 값을 갱신해주고, 우선순위큐에 넣어주자.
                if (checkArea(nextR, nextC, lake) && minCost[nextR][nextC] > Math.max(current.cost, daysToWater[nextR][nextC])) {
                    minCost[nextR][nextC] = Math.max(current.cost, daysToWater[nextR][nextC]);
                    priorityQueue.offer(new Point(nextR, nextC, Math.max(current.cost, daysToWater[nextR][nextC])));
                }
            }
        }
        System.out.println(answer);
    }

    static boolean checkArea(int r, int c, char[][] lake) {
        return r >= 0 && r < lake.length && c >= 0 && c < lake[r].length;
    }
}