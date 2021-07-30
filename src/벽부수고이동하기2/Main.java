/*
 Author : Ruel
 Problem : Baekjoon 14442번 벽 부수고 이동하기 2
 Problem address : https://www.acmicpc.net/problem/14442
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 벽부수고이동하기2;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

class Pos {
    int r;
    int c;
    int distance;
    int k;

    public Pos(int r, int c, int distance, int k) {
        this.r = r;
        this.c = c;
        this.distance = distance;
        this.k = k;
    }
}

public class Main {
    static int[][] map;
    static int[][][] minDistance;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 벽 부수고 이동하기 3 보다 더 어려울 줄 알았는데, 오히려 낮 밤 요소가 빠진 문제였다
        // 낮, 밤을 고려하지 않고 벽을 부수고 이동할 수 있는가, 벽을 n개 부수었을 때, 해당 지점에 도착하는 최소 거리는 얼마인가
        // 등을 따져 문제를 풀면 됐다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        map = new int[n][m];
        sc.nextLine();
        for (int i = 0; i < map.length; i++) {
            String row = sc.nextLine();
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(String.valueOf(row.charAt(j)));
        }
        minDistance = new int[n][m][k + 1];
        for (int[][] minDist : minDistance) {
            for (int[] md : minDist)
                Arrays.fill(md, Integer.MAX_VALUE);
        }

        PriorityQueue<Pos> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.distance, o2.distance));
        priorityQueue.add(new Pos(0, 0, 1, 0));
        int answer = Integer.MAX_VALUE;
        while (!priorityQueue.isEmpty()) {
            Pos current = priorityQueue.poll();

            if (current.r == n - 1 && current.c == m - 1) {
                answer = current.distance;
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC)) {
                    if (map[nextR][nextC] == 0 && minDistance[nextR][nextC][current.k] > current.distance + 1) {
                        priorityQueue.add(new Pos(nextR, nextC, current.distance + 1, current.k));
                        minDistance[nextR][nextC][current.k] = current.distance + 1;
                    } else if (map[nextR][nextC] == 1 && current.k < k && minDistance[nextR][nextC][current.k + 1] > current.distance + 1) {
                        priorityQueue.add(new Pos(nextR, nextC, current.distance + 1, current.k + 1));
                        minDistance[nextR][nextC][current.k + 1] = current.distance + 1;
                    }
                }
            }
        }
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}