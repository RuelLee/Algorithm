/*
 Author : Ruel
 Problem : Jungol 19142번 알파카의 트로피 미로
 Problem address : https://jungol.co.kr/problem/19142
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_19142_알파카의트로피미로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어진다. 각 칸의 정보가 주어진다.
        // S : 시작점, E : 도착점, # : 벽, . : 빈 공간, 0 ~ 9 : 순간 이동 컵
        // 상하좌우로 한 칸씩 1초에 이동할 수 있으며, 벽은 뚫지 못한다.
        // 순간 이동 컵은 같은 번호의 순간 이동 컵끼리 연결되어있으며 1초만에 다른 곳으로 이동할 수 있다.
        // S에서 출발하여 E에 도착하는데 걸리는 최소 시간은?
        //
        // 그래프 탐색, BFS 문제
        // BFS로 S에서 출발한다.
        // 그러다 순간 이동 컵을 만나면, 같은 번호의 순간 이동 컵들의 최소 시간을 따져보고 1초에 걸려 이동시킨다.
        // 한 번 사용한 순간 이동 컵의 번호는 다음에 이용하는 경우는, 무조건 이보다 같거나 더 늦은 시간이므로 처음 한 회만 사용하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 공간
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 맵
        char[][] map = new char[n][];

        // 시작점과 도착점
        int[] points = new int[2];
        // 순간 이동 컵들
        List<List<Integer>> cups = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            cups.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 'S')
                    points[0] = i * m + j;
                else if (map[i][j] == 'E')
                    points[1] = i * m + j;
                else if (map[i][j] >= '0' && map[i][j] <= '9') {
                    int idx = map[i][j] - '0';
                    cups.get(idx).add(i * m + j);
                }
            }
        }

        // 각 위치에 도달하는 최소 시간
        int[][] minDistances = new int[n][m];
        // 순간 이동 컵 사용 여부
        boolean[] portalUsed = new boolean[10];
        // 시작 위치 값 초기화
        for (int[] md : minDistances)
            Arrays.fill(md, Integer.MAX_VALUE);
        minDistances[points[0] / m][points[0] % m] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(points[0]);
        // BFS
        while (!queue.isEmpty()) {
            // 현재 위치
            int cur = queue.poll();
            int row = cur / m;
            int col = cur % m;
            // 순간 이동 컵이라면
            if (map[row][col] >= '0' && map[row][col] <= '9') {
                int idx = map[row][col] - '0';
                // 해당 순간 이동 컵을 처음 사용한다면
                if (!portalUsed[idx]) {
                    // 모든 다른 위치들을 체크
                    for (int next : cups.get(idx)) {
                        int nextR = next / m;
                        int nextC = next % m;

                        if (minDistances[nextR][nextC] > minDistances[row][col] + 1) {
                            minDistances[nextR][nextC] = minDistances[row][col] + 1;
                            queue.offer(nextR * m + nextC);
                        }
                    }
                    // idx번 순간 이동 컵 사용 여부 체크
                    portalUsed[idx] = true;
                }
            }

            // 상하좌우로 이동
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 벽이 아니고, 최소 거리를 갱신하는 경우에만 이동ㄴ
                if (checkArea(nextR, nextC) && map[nextR][nextC] != '#' && minDistances[nextR][nextC] > minDistances[row][col] + 1) {
                    minDistances[nextR][nextC] = minDistances[row][col] + 1;
                    queue.offer(nextR * m + nextC);
                }
            }
        }

        // 도착점의 이동 시간
        int ans = minDistances[points[1] / m][points[1] % m];
        // 초기값이라면 불가능한 경우이므로 -1을 출력. 그 외의 경우 해당 값을 출력
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}