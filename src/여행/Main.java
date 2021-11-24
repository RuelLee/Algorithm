/*
 Author : Ruel
 Problem : Baekjoon 2157번 여행
 Problem address : https://www.acmicpc.net/problem/2157
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Route {
    int end;
    int score;

    public Route(int end, int score) {
        this.end = end;
        this.score = score;
    }
}

public class Main {
    static List<List<Route>> airplanes;

    public static void main(String[] args) throws IOException {
        // 1 ~ n번까지의 도시가 주어지고, 최대 이동할 수 있는 도시 개수 m, 그리고 항공편들의 개수 k가 주어진다
        // 도시 번호가 커지는 방향으로만 이동하며, 그 때 먹는 기내식 점수들의 총합을 최대할 때, 최대 점수를 구하여라
        // 그 때 현재 도시의 번호와 거쳐온 도시의 수를 DP로 나타내자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        airplanes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            airplanes.add(new ArrayList<>());

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int score = Integer.parseInt(st.nextToken());
            if (start < end)        // 커지는 방향의 항공편만 기억하자
                airplanes.get(start).add(new Route(end, score));
        }

        int[][] dp = new int[n + 1][m + 1];
        // 시작 도시는 반드시 1이다. 1로부터 먼저 시작시켜 주자.
        // 중복된 항공편이 있을 수 있으므로, max 값으로 저장하자.
        for (Route next : airplanes.get(1))
            dp[next.end][2] = Math.max(dp[next.end][2], next.score);

        // 1번에서 출발한 도시들은 이미 표시되었으므로, 2부터 시작하되
        for (int start = 2; start < dp.length; start++) {
            // 2번 이상의 도시들은 1로부터 출발한 최저 2개의 도시를 거친 도시들 뿐이다.
            for (int turn = 2; turn < dp[start].length; turn++) {
                // 값이 0이라면 도착하는 경우가 없는 경우. turn이 m보다 커지면 더 이상 이동할 수 없다.
                if (dp[start][turn] == 0 || turn >= m)
                    continue;

                // start 도시에서 갈 수 있는 도시들을 표시하자
                for (Route next : airplanes.get(start))
                    dp[next.end][turn + 1] = Math.max(dp[next.end][turn + 1], dp[start][turn] + next.score);
            }
        }

        // 도착 지점은 n이므로, dp[n]에 저장된 값들 중 최대값을 출력하자.
        int maxScore = 0;
        for (int i = 2; i < dp[n].length; i++)
            maxScore = Math.max(maxScore, dp[n][i]);
        System.out.println(maxScore);
    }
}