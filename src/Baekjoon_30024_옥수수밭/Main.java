/*
 Author : Ruel
 Problem : Baekjoon 30024번 옥수수밭
 Problem address : https://www.acmicpc.net/problem/30024
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30024_옥수수밭;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 옥수수밭과 해당 칸의 수확량이 주어진다.
        // 옥수수를 수확할 때는 외곽에 있는 옥수수 혹은 수확한 옥수수의 위치에서 상하좌우로 수호가할 수 있다.
        // k 그루의 옥수수를 수확량이 많은 순서대로 수확하려고할 때, 수확하는 위치를 순서대로 출력하라.
        //
        // 우선순위큐 문제
        // 수확하는데 수확량이라는 우선순위가 주어진다.
        // 따라서 현재 수확 가능한 모든 위치를 수확량에 따라
        // 최대힙 우선순위큐에 담아 순차적으로 처리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 옥수수밭
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 위치의 수확량
        int[][] corns = new int[n][];
        for (int i = 0; i < corns.length; i++)
            corns[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 수확하고자 하는 그루 수
        int k = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        // 최대힙 우선순위큐
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(corns[o2 / m][o2 % m], corns[o1 / m][o1 % m]));
        boolean[][] enqueued = new boolean[n][m];
        // 최외곽에 해당하는 위치를 우선순위큐에 담는다.
        for (int i = 0; i < corns.length; i++) {
            for (int j = 0; j < corns[i].length; j++) {
                if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                    priorityQueue.offer(i * m + j);
                    enqueued[i][j] = true;
                }
            }
        }

        // k개의 옥수수를 처리한다.
        while (!priorityQueue.isEmpty() && k > 0) {
            int current = priorityQueue.poll();
            // currnet의 좌표
            int r = current / m;
            int c = current % m;
            
            // 해당 위치 기록
            sb.append(r + 1).append(" ").append(c + 1).append("\n");
            // k 감소
            k--;

            // 수확한 옥수수로부터 상하좌우를 살펴본다.
            for (int d = 0; d < 4; d++) {
                int nextR = r + dr[d];
                int nextC = c + dc[d];

                // 맵을 벗어나지 않으며
                // 아직 큐에 들어있지 않다면 추가한다.
                if (checkArea(nextR, nextC, corns) && !enqueued[nextR][nextC]) {
                    // 우선순위큐에 추가 및 큐에 들어있다고 표시
                    enqueued[nextR][nextC] = true;
                    priorityQueue.offer(nextR * m + nextC);
                }
            }
        }
        
        // 기록된 결과 출력.
        System.out.print(sb);
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c, int[][] corns) {
        return r >= 0 && r < corns.length && c >= 0 && c < corns[r].length;
    }
}