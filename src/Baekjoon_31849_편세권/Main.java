/*
 Author : Ruel
 Problem : Baekjoon 31849번 편세권
 Problem address : https://www.acmicpc.net/problem/31849
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31849_편세권;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기 위치의 지도에 r개의 방, c개의 편의점이 주어진다.
        // 각 방의 편세권 점수는 가장 가까운 편의점까지의 맨해튼 거리 * 월세로 주어진다.
        // 가장 낮은 편세권 점수를 갖는 방의 점수는?
        //
        // BFS 문제
        // 먼저 각 편의점부터의 거리를 BFS를 통해 구한다.
        // 그 후 각 방에 대해 거리 * 월세를 통해 편세권 점수를 구하고 그 중 최소값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 지도
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 방의 개수 r, 편의점의 개수 c
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 거리
        int[][] distances = new int[n][m];
        for (int[] d : distances)
            Arrays.fill(d, Integer.MAX_VALUE);

        // 방 정보
        int[][] rooms = new int[r][];
        for (int i = 0; i < rooms.length; i++)
            rooms[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        Queue<Integer> queue = new LinkedList<>();
        // 편의점 정보
        for (int i = 0; i < c; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            queue.offer(a * m + b);
            distances[a][b] = 0;
        }

        // bfs
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / m;
            int col = current % m;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC, distances) && distances[nextR][nextC] > distances[row][col] + 1) {
                    distances[nextR][nextC] = distances[row][col] + 1;
                    queue.offer(nextR * m + nextC);
                }
            }
        }
        
        // 거리 * 월세로 편세권 점수 계산
        int answer = Integer.MAX_VALUE;
        for (int[] room : rooms)
            answer = Math.min(answer, distances[room[0] - 1][room[1] - 1] * room[2]);
        // 답 출력
        System.out.println(answer);

    }

    static boolean checkArea(int r, int c, int[][] distances) {
        return r >= 0 && r < distances.length && c >= 0 && c < distances[r].length;
    }
}