/*
 Author : Ruel
 Problem : Baekjoon 1520번 내리막 길
 Problem address : https://www.acmicpc.net/problem/1520
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 내리막길;

import java.util.Scanner;

public class Main2 {
    static int[][] map;
    static int[][] dp;
    static boolean[][] visited;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // DFS + DP로 풀어보았다.
        // 도착 지점에서 도착 지점에 도달하는 방법은 1가지이므르 그 값을 담아준다.
        // 출발지점에서부터 시작하여, 각 지점에서부터 도착 지점까지 도달할 수 있는 방법의 가짓수를 DP에 담아줄 것이다
        // 현 지점에 처음 도착했다면, 4방을 DFS를 통해 해당 지점에서 도착 지점까지 도달할 수 있는 방법의 가짓수를 가져와야하고,
        // 현 지점이 이미 방문한 적이 있는 지점이라면 DP에 현 지점으로부터 도착지점까지 도달할 수 있는 방법의 가짓수가 DP에 저장되어있으니, 해당 값을 리턴하면 된다.
        // 최종적으로는 dp[0][0]에 출발지점으로부터 도착지점까지 도달할 수 있는 경우의 수가 저장될 것이다.
        // EX) 아래와 같은 값이 있다고 하자
        // 10 10  7
        //  9  8  5
        //  3  2  1
        // 그렇다면 DP는 아래와 같이 채워질 것이다.
        //  3  0  0
        //  3  2  1
        //  1  1  1
        //  2, 3, 5는 도착지점까지 도달할 수 있는 가짓수가 1가지이다.
        //  8은 5에서 1값을 가져오고, 2에서 1값을 가져와 2가 되었으며
        //  9는 8에서 2값을 가져오고, 3에서 1값을 가져와 3이 되었다.
        // 마찬가지로 시작지점인 10은 9에서 3을 가져와 답은 3가지가 된다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        map = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = sc.nextInt();
        }
        dp = new int[n][m];
        dp[dp.length - 1][dp[dp.length - 1].length - 1] = 1;
        visited = new boolean[n][m];
        visited[visited.length - 1][visited[visited.length - 1].length - 1] = true;     // 도달 지점은 1가지로 계산이 되어있으므로, 이미 방문(=계산)되었다고 생각해주자.

        dfs(0, 0);          // 0, 0에서 시작
        System.out.println(dp[0][0]);
    }

    static int dfs(int r, int c) {
        if (visited[r][c])      // 이미 방문한 적이 있다면
            return dp[r][c];        // 해당 지점에서 도달지점까지 도착할 수 있는 가짓수를 리턴해주자.

        int count = 0;      // 그렇지 않다면 4방을 탐색하여 4방으로부터 도착지점까지 도달할 수 있는 가짓수를 더해주자. 0값으로 초기화가 되었으니, 도달할 수 없다면 0값이 리턴될 것이다.
        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            if (checkArea(nextR, nextC) && map[nextR][nextC] < map[r][c])       // 해당 방향이 맵을 넘지 않으며, 자신보다 작은 값을 갖고 있을 때
                count += dfs(nextR, nextC);     // 해당 방향으로부터 계산되는 도착지점까지의 가짓수를 더해준다.
        }
        visited[r][c] = true;       // 현재 지점에 방문체크를 해주고,
        return dp[r][c] = count;        // dp에 현재 지점으로부터 도착지점까지 도달할 수 있는 방법의 가짓수를 남겨주고, 리턴해준다.
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}