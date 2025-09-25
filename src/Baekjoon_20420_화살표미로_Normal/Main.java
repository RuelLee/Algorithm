/*
 Author : Ruel
 Problem : Baekjoon 20420번 화살표 미로 (Normal)
 Problem address : https://www.acmicpc.net/problem/20420
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20420_화살표미로_Normal;

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
    static char[] direction = {'U', 'R', 'D', 'L'};
    static int r, c;

    public static void main(String[] args) throws IOException {
        // r * c 크기의 미로가 주어진다.
        // 미로의 각 칸에는 방향이 주어져있고, 해당 방향으로만 이동이 가능하다.
        // (1, 1)에서 출발하여 (r, c)에 도달하는 것이 목적이다.
        // 스크롤은 시계 방향과 반시계 방향 두개가 있고, 해당 방향으로 90도 회전시키는 것이 가능하다.
        // 한 자리에서 여러 스크롤을 사용할 수도 있다.
        // 스크롤은 시계 방향과 반시계 방향 1개씩 1세트로 총 k세트가 주어진다.
        // 목적지에 도달할 수 있는지를 출력하라
        //
        // BFS 문제
        // dp[row][col][반시계사용횟수][시계사용횟수] 로 정하고 해당 지역에 도달하는 최소 스크롤 횟수를 기록해나간다.
        // 최종적으로 정해진 사용 횟수 내에 (r, c)에 도달할 수 있는가를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 미로의 크기 r * c, 스크롤 세트의 개수 k
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 미로
        char[][] map = new char[r][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // dp[row][col][반시계사용횟수][시계사용횟수]
        boolean[][][][] dp = new boolean[r][c][k + 1][k + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int a = 0; a < dp[i][j].length; a++)
                    Arrays.fill(dp[i][j][a], false);
            }
        }
        // 처음 시작 위치 초기화
        for (int i = 0; i < dp[0][0].length; i++)
            Arrays.fill(dp[0][0][i], true);

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 0, 0});
        while (!queue.isEmpty()) {
            // 현재 위치
            int[] current = queue.poll();
            
            // 현재 미로에서 향하는 방향
            int head = -1;
            while (head < 3) {
                if (map[current[0]][current[1]] == direction[++head])
                    break;
            }

            // 현재 방향에서 0, 90, 180, 270도를 살펴본다.
            for (int d = 0; d < 4; d++) {
                int nextR = current[0] + dr[(head + d) % 4];
                int nextC = current[1] + dc[(head + d) % 4];

                // 다음 칸이 미로 내이고
                if (checkArea(nextR, nextC)) {
                    // 다음 칸을 가기 위해선 시계방향으로 d번 스크롤을 사용하던가
                    // 반시계 방향으로 4 - d번 스크롤을 사용해야한다.
                    // 먼저 시계 방향
                    // 스크롤의 개수가 남아있고, 현재 이동하는 경우가 최소 스크롤 사용 횟수인 경우
                    if (current[3] + d <= k && !dp[nextR][nextC][current[2]][current[3] + d]) {
                        // dp[nextR][nextC][현재 사용 반시계 스크롤 개수 이상][현재 사용 시계 스크롤 개수 이상]을 모두 true로 채운다.
                        // 더 많은 스크롤을 사용해서 해당 위치에 가는 것은 의미가 없기 때문
                        for (int i = current[2]; i < dp[nextR][nextC].length; i++) {
                            for (int j = current[3] + d; j < dp[nextR][nextC][i].length; j++) {
                                if (dp[nextR][nextC][i][j])
                                    break;
                                dp[nextR][nextC][i][j] = true;
                            }
                        }
                        // 그리고 큐에 추가
                        queue.offer(new int[]{nextR, nextC, current[2], current[3] + d});
                    }
                    
                    // 반시계 방향으로도 마찬가지
                    if (current[2] + 4 - d <= k && !dp[nextR][nextC][current[2] + 4 - d][current[3]]) {
                        for (int i = current[2] + 4 - d; i < dp[nextR][nextC].length; i++) {
                            for (int j = current[3]; j < dp[nextR][nextC][i].length; j++) {
                                if (dp[nextR][nextC][i][j])
                                    break;
                                dp[nextR][nextC][i][j] = true;
                            }
                        }
                        queue.offer(new int[]{nextR, nextC, current[2] + 4 - d, current[3]});
                    }
                }
            }
        }
        
        // 최종적으로 (r, c)에 양 스크롤을 k개 이하씩 사용하여 도달할 수 있는가를 출력
        // dp[r-1][c-1][k][k]을 출력
        System.out.println(dp[r - 1][c - 1][k][k] ? "Yes" : "No");
    }

    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}