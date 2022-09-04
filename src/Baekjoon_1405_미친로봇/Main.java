/*
 Author : Ruel
 Problem : Baekjoon 1405번 미친 로봇
 Problem address : https://www.acmicpc.net/problem/1405
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1405_미친로봇;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static double[] percents;
    static double simpleRoutePercent = 0;
    static int[] diff;

    public static void main(String[] args) throws IOException {
        // 로봇이 n번 동서남북으로 이동한다.
        // 각 행동을 취할 확률이 주어졌을 때, 이동하는 장소가 모두 처음 방문한 장소일 확률을 구하라
        //
        // dfs + 백트래킹을 활용한 문제
        // 이전에 방문했던 장소들을 기록하면서, 다음 장소가 이전에 방문했던 장소인지 확인한다.
        // 방문하지 않았다면 dfs로 n번까지 탐색한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 행동 횟수
        n = Integer.parseInt(st.nextToken());
        // 동서남북으로 이동할 확률
        percents = new double[4];
        for (int i = 0; i < 4; i++)
            percents[i] = Integer.parseInt(st.nextToken()) / (double) 100;
        // 각 위치를 int값 하나로 나타낸다.
        // 동쪽은 +1, 서쪽은 -1, 남쪽은 2 * n + 1 북쪽은 -(2 * n + 1) 에 해당하는 값을 더하며
        // 시작점 0으로 해당 값을 통해 로봇이 어디에 위치하는지 확인할 수 있다.
        diff = new int[]{1, -1, 2 * n + 1, -(2 * n + 1)};
        // 방문했던 장소들을 기억할 int 배열
        int[] visited = new int[n + 1];
        // 0번째 턴, 0위치에서 100% 확률로 시작.
        findAnswer(0, visited, 1);
        // 최종적으로 구한 확률을 출력한다.
        System.out.printf("%.10f\n", simpleRoutePercent);
    }

    static void findAnswer(int turn, int[] visited, double percent) {
        if (turn == n) {
            // n번까지 무사히 도달했다면 해당 확률을
            // simpleRoutePercent에 더한다.
            simpleRoutePercent += percent;
            return;
        }

        // 동서남북에 대해 각각 따져본다.
        for (int d = 0; d < 4; d++) {
            // d값에 따라
            // 0은 동, 1은 서, 2는 남, 3은 북
            // 토대로 다음 위치 값을 구한다.
            int nextLoc = visited[turn] + diff[d];

            // 해당 위치를 방문한 적 있는지 확인한다.
            boolean simpleRoute = true;
            // nextLoc이 currentLoc과 같을 수는 없으므로 visited[turn]까지 확인을 안해도 무방.
            for (int i = 0; i < turn; i++) {
                if (nextLoc == visited[i]) {
                    simpleRoute = false;
                    break;
                }
            }
            // 방문한 적이 있다면 다음 방향으로 넘어간다.
            if (!simpleRoute)
                continue;

            // 방문한 적이 없다면 다음 위치를 방문 표시를 한 후
            // dfs로 계속해서 탐색한다.
            visited[turn + 1] = nextLoc;
            findAnswer(turn + 1, visited, percent * percents[d]);
        }
    }
}