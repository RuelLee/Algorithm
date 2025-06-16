/*
 Author : Ruel
 Problem : Baekjoon 2917번 늑대 사냥꾼
 Problem address : https://www.acmicpc.net/problem/2917
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2917_늑대사냥꾼;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자에 빈 칸, 나무, 출발지, 도착지가 주어진다.
        // 현재 (r, c), 나무가 (a, b)라면 두 거리는 |r - a| + |c - b|로 계산한다.
        // 출발지에서 도착지로 도달하되, 나무로부터 최대한 멀리 떨어져서 도달하고자 한다.
        // 도착지에 도달할 때까지, 나무와 거리의 최솟값이 가장 큰 값은?
        //
        // BFS, 최단 경로
        // 먼저 나무가 하나가 아닐 수 있으므로, 각 나무들로 부터 BFS를 통해
        // 각 격자와 나무의 거리를 모두 계산한다.
        // 그 후, 출발지부터 도착지까지 도달할 때까지 현재까지 거쳐온 경로에서의 나무와 거리의 최솟값과
        // 다음 격자의 나무와 거리를 비교해가며 계산한다.
        // 큐로도 가능하지만 우선순위큐를 사용하여 거리가 먼 것부터 계산하면 연산을 줄일 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 지도
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();
        
        // 각 격자와 나무 사이의 거리
        int[][] fromTree = new int[n][m];
        for (int[] ft : fromTree)
            Arrays.fill(ft, Integer.MAX_VALUE);
        // 나무들을 큐에 담아 BFS를 한다.
        Queue<Integer> queue = new LinkedList<>();
        // 시작점과 도착점
        int start = -1;
        int end = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == '+') {
                    queue.offer(i * m + j);
                    fromTree[i][j] = 0;
                } else if (map[i][j] == 'V')
                    start = i * m + j;
                else if (map[i][j] == 'J')
                    end = i * m + j;
            }
        }

        // BFS로 계산
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / m;
            int col = current % m;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC) && fromTree[nextR][nextC] > fromTree[row][col] + 1) {
                    fromTree[nextR][nextC] = fromTree[row][col] + 1;
                    queue.offer(nextR * m + nextC);
                }
            }
        }
        
        // 각 격자에 도달할 때까지 거친 경로에서의
        // 나무와 거리의 최솟값
        int[][] minDistances = new int[n][m];
        for (int[] md : minDistances)
            Arrays.fill(md, -1);
        // 시작점
        minDistances[start / m][start % m] = fromTree[start / m][start % m];
        // 우선순위큐로 거리가 먼 것부터 계산
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(minDistances[o2 / m][o2 % m], minDistances[o1 / m][o1 % m]));
        priorityQueue.offer(start);
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            int row = current / m;
            int col = current % m;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];
                
                // (row, col)에서의 값과 (nextR, nextC)에서 나무와의 거리를 비교
                if (checkArea(nextR, nextC) && minDistances[nextR][nextC] < Math.min(minDistances[row][col], fromTree[nextR][nextC])) {
                    minDistances[nextR][nextC] = Math.min(minDistances[row][col], fromTree[nextR][nextC]);
                    priorityQueue.offer(nextR * m + nextC);
                }
            }
        }
        // 도착점에서의 값 출력
        System.out.println(minDistances[end / m][end % m]);
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}