/*
 Author : Ruel
 Problem : Baekjoon 14868번 문명
 Problem address : https://www.acmicpc.net/problem/14868
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14868_문명;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parents;
    static int[] ranks;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n, n의 맵이 주어지고, 문명의 발상지가 주어진다
        // 매해 문명은 사방으로 넓혀나가며, 문명 간에 서로 인접할 경우, 하나의 문명으로 합쳐진다고 한다
        // 하나의 문명으로 합쳐지는데 소요되는 시간은 얼마인가?
        //
        // 분리 집합과 BFS를 같이 쓰는 문제
        // BFS를 통해 문명을 매해 사방으로 확장해 나가며
        // 서로 다른 문명과 인접하게 됐을 경우, 분리집합을 사용하여 하나의 문명으로 합쳐준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        // 문명의 수만큼 집합이 필요하다.
        parents = new int[k + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[k + 1];

        int[][] map = new int[n][n];
        // 문명의 발상지를 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= k; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;

            map[row][col] = i;
            queue.offer(row * n + col);
        }

        int turn = 0;
        Queue<Integer> nextQueue = new LinkedList<>();
        // 최초 문명의 수
        int numOfCivilizations = k;
        // 매해 문명마다 사방으로 확장해나가며 다른 문명과 인접하는지 확인한다.
        // 전체 문명의 수가 1보다 큰 경우에 반복한다.
        while (numOfCivilizations > 1) {
            // 인접한 문명이 있는지 확인한다.
            for (int point : queue) {
                int row = point / n;
                int col = point % n;
                for (int d = 0; d < 4; d++) {
                    int nearR = row + dr[d];
                    int nearC = col + dc[d];

                    // 인접 칸에 다른 문명이 존재한다면 하나의 집합으로 묶고, 전체 문명의 수를 하나 감소시킨다.
                    if (checkArea(nearR, nearC, map) && map[nearR][nearC] != 0 &&
                            findParent(map[row][col]) != findParent(map[nearR][nearC])) {
                        union(map[row][col], map[nearR][nearC]);
                        numOfCivilizations--;
                    }
                }
            }

            // 전체 문명의 수가 1이 됐다면 종료.
            if (numOfCivilizations == 1)
                break;

            // 큐가 빌 때까지 문명을 사방으로 확장한다.
            while (!queue.isEmpty()) {
                int current = queue.poll();
                int row = current / n;
                int col = current % n;

                for (int d = 0; d < 4; d++) {
                    int nextR = row + dr[d];
                    int nextC = col + dc[d];

                    // 다음 칸이 맵 안에 있으며
                    if (checkArea(nextR, nextC, map)) {
                        // 0일 경우 확장하고, 다음 해에 탐색하기 위해 nextQueue에 삽입한다.
                        if (map[nextR][nextC] == 0) {
                            map[nextR][nextC] = findParent(map[row][col]);
                            nextQueue.offer(nextR * n + nextC);
                            // 다른 문명일 경우, 하나의 문명으로 합치고 전체 문명의 수를 감소시킨다.
                        } else if (findParent(map[row][col]) != findParent(map[nextR][nextC])) {
                            union(map[row][col], map[nextR][nextC]);
                            numOfCivilizations--;
                        }
                    }
                }
            }
            // nextQueue에 있는 지점들을 다음 해에 탐색하며
            queue = nextQueue;
            // nextQueue는 새로 선언하여 다다음해에 탐색할 포인트들을 위해 준비하자.
            nextQueue = new LinkedList<>();
            // 해 증가.
            turn++;
        }
        // 최종 turn이 전체 문명이 하나의 문명이 되는데 소요된 햇수
        System.out.println(turn);
    }

    // 두 집합을 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // 집합의 대표를 반환한다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}