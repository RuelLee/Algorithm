/*
 Author : Ruel
 Problem : Baekjoon 1520번 내리막 길
 Problem address : https://www.acmicpc.net/problem/1520
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 내리막길;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 일반적인 BFS 문제구나! -> 시간초과
        // DP를 활용하여 오른쪽 아래로 값을 채워나가며 진행해보자 -> 값이 왼쪽이나 위로 진행될 땐 수행불가.
        // 큰 숫자에서 작은 숫자로 단방향으로만 진행되니, 그럼 우선순위큐에 각 지점의 값을 담고, 값이 큰 순서부터 DP를 채워보자 -> 정답
        // 어차피 높이가 높은 곳에서 낮은 곳으로밖에 이동을 할 수 없다.
        // 따라서 값이 큰 순서대로 해당 지점에 도달할 수 있는 경우의 수를 구하면 된다!
        // 최종 값은 맨 오른쪽 아래의 값.
        // 다른 사람들의 답안을 보니 DFS와 DP를 이용하여 풀은 경우가 많은 것 같다
        // DFS로 깊이우선으로 들어가되, 맨오른쪽 아래거나 이미 계산이 된 지점까지 파고들어, 해당 지점의 가짓수를 더해나가는 방법이었다
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = sc.nextInt();


        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2[2], o1[2]));
        int[][] map = new int[m][n];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = sc.nextInt();       // 값을 저장하고
                priorityQueue.offer(new int[]{i, j, map[i][j]});        // 우선순위큐에 넣어 크기순대로 내림차순으로 뽑아낼 수 있도록 하자.
            }
        }
        int[][] dp = new int[m][n];
        dp[0][0] = 1;   // 시작지점에서의 가짓수는 1가지.

        while (!priorityQueue.isEmpty()) {      // 우선순위큐가 빌 때까지
            int[] current = priorityQueue.poll();

            for (int d = 0; d < 4; d++) {       // 4방을 둘러보며
                int postR = current[0] + dr[d];
                int postC = current[1] + dc[d];

                if (checkArea(postR, postC, map) && map[postR][postC] > map[current[0]][current[1]])        // 자신보다 큰 값이 있다면
                    dp[current[0]][current[1]] += dp[postR][postC];     // 해당 지점에 도달하는 가짓수를 자신의 가짓수에 더한다.
            }
        }
        System.out.println(dp[dp.length - 1][dp[0].length - 1]);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}