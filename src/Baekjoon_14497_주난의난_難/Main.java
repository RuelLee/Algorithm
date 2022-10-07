/*
 Author : Ruel
 Problem : Baekjoon 14497번 주난의 난(難)
 Problem address : https://www.acmicpc.net/problem/14497
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14497_주난의난_難;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n, m 크기의 맵이 주어진다.
        // 맵은 빈 공간, 친구, 주인공, 범인이 주어진다.
        // 주인공은 매 점프마다 파동으로 친구들을 쓰러뜨릴 수 있다.
        // 범인을 쓰러뜨리기 위한 최소의 점프는?
        //
        // 그래프 탐색 문제
        // 우선순위가 있는 탐색 문제.
        // 파동의 형태이기 때문에 빈 공간에는 한 번의 점프로 여러 칸을 지나갈 수 있다.
        // 따라서 우선순위큐를 통해 점프 횟수가 적은 공간을 우선적으로 탐색해나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 주어지는 입력.
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 주인공과 범인의 위치.
        st = new StringTokenizer(br.readLine());
        int x1 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken());
        int x2 = Integer.parseInt(st.nextToken());
        int y2 = Integer.parseInt(st.nextToken());
        
        // 맵 정보
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 각 공간에 도달할 수 있는 최소 점프.
        int[][] minJumps = new int[n][m];
        for (int[] row : minJumps)
            Arrays.fill(row, Integer.MAX_VALUE);
        minJumps[x1 - 1][y1 - 1] = 0;

        // 우선순위큐로 점프 횟수가 적은 공간을 우선적으로 탐색한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(v -> minJumps[v / m][v % m]));
        priorityQueue.offer((x1 - 1) * m + (y1 - 1));
        while (!priorityQueue.isEmpty()) {
            // 현재 공간의 row, col
            int row = priorityQueue.peek() / m;
            int col = priorityQueue.poll() % m;
            
            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 맵을 벗어나지 않으며
                if (checkArea(nextR, nextC, map)) {
                    // 다음 칸이 0이라면 한번의 점프로 연속해서 지나갈 수 있으므로
                    // row, col과 같은 점프 횟수를 갖을 수 있다.
                    if (map[nextR][nextC] == '0' && minJumps[nextR][nextC] > minJumps[row][col]) {
                        minJumps[nextR][nextC] = minJumps[row][col];
                        priorityQueue.offer(nextR * m + nextC);
                        // 다음 칸이 0이 아니라면 파동을 통해 쓰러뜨리고, 막히게 된다.
                        // row, col보다 하나 더 많은 점프 횟수를 갖을 수 있다.
                    } else if (minJumps[nextR][nextC] > minJumps[row][col] + 1) {
                        minJumps[nextR][nextC] = minJumps[row][col] + 1;
                        priorityQueue.offer(nextR * m + nextC);
                    }
                }
            }
        }
        // 최종적으로 범인이 있는 위치에 최소 점프 횟수를 출력한다.
        System.out.println(minJumps[x2 - 1][y2 - 1]);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}