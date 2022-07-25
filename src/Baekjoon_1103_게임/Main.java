/*
 Author : Ruel
 Problem : Baekjoon 1103번 게임
 Problem address : https://www.acmicpc.net/problem/1103
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1103_게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] map;
    static int[][] dp;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n, m의 보드가 주어지가 각 칸에는 1 ~ 9의 수가 주어진다
        // 맨왼쪽, 맨 위의 칸에서 시작하여, 사방으로 현재 칸의 수만큼 움직일 수 있다.
        // H에 빠지거나 보드를 나가면 게임은 종료된다.
        // 오랫동안 게임을 지속하고 싶을 때, 가장 오랫동안 게임할 수 있는 횟수를 출력하라.
        // 무한히 진행할 수 있다면 -1을 출력한다.
        //
        // DP 문제
        // bottom-up 방식으로 각 칸에서 최대한 진행할 수 있는 턴을 기록해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 칸의 수를 map에 기록한다.
        map = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            String row = br.readLine();
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = row.charAt(j) - '0';
        }

        // 각 칸에서 최대 이동할 수 있는 횟수를 기록한다.
        dp = new int[n][m];
        // 0, 0에서 최대 이동할 수 있는 횟수를 계산한다.
        int answer = findAnswer(0, 0, new boolean[n][m]);
        // 답이 만약 Integer.MAX_VALUE이 돌아왔다면 무한히 진행할 수 있는 경우. -1 출력
        // 그렇지 않다면 answer 값 출력.
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    // 답을 찾는다.
    static int findAnswer(int r, int c, boolean[][] visited) {
        // 만약 map[r][c]가 H에 해당하는 값이라면
        // 더 이상 이동할 수 없으므로 0을 출력한다.
        if (map[r][c] == 'H' - '0')
            return 0;

        // DFS로 탐색하는데 이번 칸이 연속적으로 방문할 수 있는 곳이라면
        // 사이클이 형성돼, 무한히 게임을 할 수 있는 경우.
        // dp[r][c]에 Integer.MAX_VALUE 값을 기록하고, 리턴하자.
        if (visited[r][c])
            return dp[r][c] = Integer.MAX_VALUE;
        // 그렇지 않고 혹시 dp가 기록이 되어있다면
        // (= 연속적으로 방문할 수는 없지만 이전에 방문해서 계산한 적이 있다면)
        // 계산 없이 값을 가져온다.
        else if (dp[r][c] != 0)
            return dp[r][c];

        // r, c에 대해 방문 체크.
        visited[r][c] = true;
        // 현재 칸에서 맵 밖으로 나가더라도 최소 1번은 이동이 보장되어있다.
        int maxMove = 1;
        // 사방탐색을 하되, 이동 칸수는 map[r][c]이다.
        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d] * map[r][c];
            int nextC = c + dc[d] * map[r][c];
            
            // 맵 범위 안이고,
            if (checkArea(nextR, nextC)) {
                int move = findAnswer(nextR, nextC, visited);
                // 만약 반환된 값이 Integer.MAX_VALUE라면 무한히 이동이 가능한 경우.
                // DP에 Integer.MAX_VALUE 기록하고 리턴.
                if (move == Integer.MAX_VALUE)
                    return dp[r][c] = Integer.MAX_VALUE;
                // 그렇지 않다면, move에 (r, c) -> (nextR, nextC)로 가는 횟수인 1을 더해
                // maxMove값과 비교하여 더 큰 값을 남겨둔다.
                maxMove = Math.max(maxMove, move + 1);
            }
        }
        // (r, c)로부터 방문할 수 있는 곳에 체크가 끝났으므로 방문 체크 해제.
        visited[r][c] = false;
        // dp에는 maxMove 값을 기록해준다.
        return dp[r][c] = maxMove;
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}