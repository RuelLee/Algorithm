/*
 Author : Ruel
 Problem : Baekjoon 31633번 첨단 가지 농장
 Problem address : https://www.acmicpc.net/problem/31633
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31633_첨단가지농장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Seek {
    int r;
    int c;
    int order;

    public Seek(int r, int c, int order) {
        this.r = r;
        this.c = c;
        this.order = order;
    }
}

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 칸의 농장이 주어진다.
        // 처음 각 칸의 가지는 물을 많이 받을 수록 더 빨리 자란다.
        // 모든 가지의 크기를 균일하게 만들기 위해 인접한 칸의 높이 차를 주어 물을 더 많이 받도록 하고자 한다.
        // 인접한 칸의 가지 크기가 다르다면 높이 차가 반드시 있어야하며
        // 크기가 같다면 높이 차가 없어야한다.
        // 처음 모든 칸의 높이는 0일 때, 높이 차를 최소한으로 주어 가지 크기를 균일하게 만들고자 할 때
        // 각 칸의 높이를 출력하라
        //
        // 우선순위큐, 최단 거리
        // 분류를 분리 집합, 위상 정렬이 있었지만.
        // 우선순위큐를 통해 크기가 작은 칸, 크기가 같다면 높이가 높은 칸부터 인접한 칸을 탐색하며
        // 현재 칸의 크기보다 인접 칸의 크기가 더 크다면 높이를 반드시 +1 더 높게 가져가도록 했다.
        // 인접한 같이라면 같도록 하였다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 칸
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 각 칸의 가지 크기
        int[][] farm = new int[n][m];
        for (int i = 0; i < farm.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < farm[i].length; j++)
                farm[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 높이
        int[][] order = new int[n][m];
        // 모든 칸을 일단 우선순위큐에 담는다.
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (farm[o1.r][o1.c] == farm[o2.r][o2.c])
                return Integer.compare(o2.order, o1.order);
            return Integer.compare(farm[o1.r][o1.c], farm[o2.r][o2.c]);
        });
        for (int i = 0; i < farm.length; i++) {
            for (int j = 0; j < farm[i].length; j++)
                priorityQueue.offer(new Seek(i, j, order[i][j]));
        }

        while (!priorityQueue.isEmpty()) {
            // 현재 칸
            Seek current = priorityQueue.poll();
            if (current.order < order[current.r][current.c])
                continue;
            
            // 인접 칸
            for (int d = 0; d < 4; d++) {
                int nearR = current.r + dr[d];
                int nearC = current.c + dc[d];

                // 범위를 벗어나지 않고,
                // 현재 칸의 가지보다 크기가 같거나 크고
                // 크기가 같다면 높이차가 같은지, 더 크다면 높이가 더 높은지 확인.
                if (checkArea(nearR, nearC) && farm[nearR][nearC] >= farm[current.r][current.c] &&
                        order[nearR][nearC] < order[current.r][current.c] + (farm[nearR][nearC] == farm[current.r][current.c] ? 0 : 1)) {
                    order[nearR][nearC] = order[current.r][current.c] + (farm[nearR][nearC] == farm[current.r][current.c] ? 0 : 1);
                    priorityQueue.offer(new Seek(nearR, nearC, order[nearR][nearC]));
                }
            }
            
            // 높이 값이 갱신되어 쓸모없는 값이 우선순위큐의 최상단에 있을 경우 제거
            while (!priorityQueue.isEmpty() && order[priorityQueue.peek().r][priorityQueue.peek().c] > priorityQueue.peek().order)
                priorityQueue.poll();
        }
        
        // 답 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < order[i].length; j++)
                sb.append(order[i][j]).append(" ");
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}