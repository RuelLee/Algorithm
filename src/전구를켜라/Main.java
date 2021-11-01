/*
 Author : Ruel
 Problem : Baekjoon 2423번 전구를 켜라
 Problem address : https://www.acmicpc.net/problem/2423
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 전구를켜라;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static void main(String[] args) throws IOException {
        // 시뮬레이션 문제
        // 전선이 연결되는 것과 각 지점이 서로 다르므로 이것에 대해 유의하며 시뮬레이션한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] map = new char[n][m];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        int[][] minSwitch = new int[n + 1][m + 1];
        for (int[] ms : minSwitch)
            Arrays.fill(ms, Integer.MAX_VALUE);

        minSwitch[0][0] = 0;

        // 우선 순위큐에 담아 타일을 안 건들이거나 적게 옮겨도 연결되는 경우를 먼저 계산하도록 하자.
        PriorityQueue<Point> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(minSwitch[o1.r][o1.c], minSwitch[o2.r][o2.c]));
        queue.offer(new Point(0, 0));
        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (checkArea(current.r - 1, current.c + 1, minSwitch)) {       // 현재 지점에서 ↗으로 연결되는 경우
                if (map[current.r - 1][current.c] == '/' && minSwitch[current.r - 1][current.c + 1] > minSwitch[current.r][current.c]) {        // 경로를 안 건들여도 되는 경우
                    queue.offer(new Point(current.r - 1, current.c + 1));
                    minSwitch[current.r - 1][current.c + 1] = minSwitch[current.r][current.c];
                } else if (map[current.r - 1][current.c] == '\\' && minSwitch[current.r - 1][current.c + 1] > minSwitch[current.r][current.c] + 1) {        // 경로를 건들여야하는 경우
                    queue.offer(new Point(current.r - 1, current.c + 1));
                    minSwitch[current.r - 1][current.c + 1] = minSwitch[current.r][current.c] + 1;
                }
            }
            if (checkArea(current.r + 1, current.c + 1, minSwitch)) {       // 현재 지점에서 ↘으로 연결되는 경우
                if (map[current.r][current.c] == '\\' && minSwitch[current.r + 1][current.c + 1] > minSwitch[current.r][current.c]) {
                    queue.offer(new Point(current.r + 1, current.c + 1));
                    minSwitch[current.r + 1][current.c + 1] = minSwitch[current.r][current.c];
                } else if (map[current.r][current.c] == '/' && minSwitch[current.r + 1][current.c + 1] > minSwitch[current.r][current.c] + 1) {
                    queue.offer(new Point(current.r + 1, current.c + 1));
                    minSwitch[current.r + 1][current.c + 1] = minSwitch[current.r][current.c] + 1;
                }
            }
            if (checkArea(current.r + 1, current.c - 1, minSwitch)) {       // 현재 지점에서 ↙으로 연결되는 경우
                if (map[current.r][current.c - 1] == '/' && minSwitch[current.r + 1][current.c - 1] > minSwitch[current.r][current.c]) {
                    queue.offer(new Point(current.r + 1, current.c - 1));
                    minSwitch[current.r + 1][current.c - 1] = minSwitch[current.r][current.c];
                } else if (map[current.r][current.c - 1] == '\\' && minSwitch[current.r + 1][current.c - 1] > minSwitch[current.r][current.c] + 1) {
                    queue.offer(new Point(current.r + 1, current.c - 1));
                    minSwitch[current.r + 1][current.c - 1] = minSwitch[current.r][current.c] + 1;
                }
            }
            if (checkArea(current.r - 1, current.c - 1, minSwitch)) {       // 현재 지점에서 ↖으로 연결되는 경우
                if (map[current.r - 1][current.c - 1] == '\\' && minSwitch[current.r - 1][current.c - 1] > minSwitch[current.r][current.c]) {
                    queue.offer(new Point(current.r - 1, current.c - 1));
                    minSwitch[current.r - 1][current.c - 1] = minSwitch[current.r][current.c];
                } else if (map[current.r - 1][current.c - 1] == '/' && minSwitch[current.r - 1][current.c - 1] > minSwitch[current.r][current.c] + 1) {
                    queue.offer(new Point(current.r - 1, current.c - 1));
                    minSwitch[current.r - 1][current.c - 1] = minSwitch[current.r][current.c] + 1;
                }
            }
        }
        int answer = minSwitch[minSwitch.length - 1][minSwitch[minSwitch.length - 1].length - 1];
        // 맨오른쪽 아래 값이 Integer.MAX_VALUE로 바뀌지 않았다면 연결할 수 없는 경우이다.
        // 반대로 값이 바뀌었다면 연결된 경우.
        System.out.println(answer == Integer.MAX_VALUE ? "NO SOLUTION" : answer);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}