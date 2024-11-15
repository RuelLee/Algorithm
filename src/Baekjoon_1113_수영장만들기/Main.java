/*
 Author : Ruel
 Problem : Baekjoon 1113번 수영장 만들기
 Problem address : https://www.acmicpc.net/problem/1113
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1113_수영장만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int n, m;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어지고 각 칸에는 높이가 주어진다.
        // 격자 안에 물을 부어, 수영장을 만들고자 한다.
        // 격자 밖의 높이는 0이고, 물은 인접한 곳의 낮은 곳으로 흐른다.
        // 격자 내에 담아둘 수 있는 물의 최대량은?
        //
        // BFS 문제
        // 격자 내에서 주위의 높이를 따져 낮은 곳에 물을 채우고자 한다면 어려운 문제가 된다.
        // 격자 외부에서 순차적으로 물을 부어, 외부의 높이보다 
        // 낮은 격자 내부를 발견하면 해당 곳의 개수를 세어주는 방식으로 구현한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 해당 격자의 높이
        int[][] pool = new int[n + 2][m + 2];
        for (int i = 1; i < pool.length - 1; i++) {
            String row = br.readLine();
            for (int j = 1; j < pool[i].length - 1; j++)
                pool[i][j] = row.charAt(j - 1) - '0';
        }

        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            // 격자 밖의 공간을 큐에 담는다.
            queue.offer(0);

            // 큐가 빌 때까지
            while (!queue.isEmpty()) {
                // 현재 위치
                int row = queue.peek() / (m + 2);
                int col = queue.poll() % (m + 2);

                // 사방 탐색하여, 현재 붓는 물의 높이보다 낮은 곳엔 모두 물이 흘러 해당 높이가 된다.
                for (int d = 0; d < 4; d++) {
                    int nextR = row + dr[d];
                    int nextC = col + dc[d];

                    if (checkArea(nextR, nextC) && pool[nextR][nextC] < i) {
                        queue.offer(nextR * (m + 2) + nextC);
                        pool[nextR][nextC] = i;
                    }
                }
            }

            // 원래 주어졌던 격자 내부에
            // 현재 물의 높이 i보다 낮은 곳이 있는지 센다.
            for (int j = 1; j < pool.length - 1; j++) {
                for (int k = 1; k < pool[j].length - 1; k++) {
                    if (pool[j][k] < i)
                        count++;
                }
            }
        }
        // 답 출력
        System.out.println(count);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n + 2 && c >= 0 && c < m + 2;
    }
}