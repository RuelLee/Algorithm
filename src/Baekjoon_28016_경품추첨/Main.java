/*
 Author : Ruel
 Problem : Baekjoon 28016번 경품 추첨
 Problem address : https://www.acmicpc.net/problem/28016
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28016_경품추첨;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 추첨판이 주어진다.
        // 처음 시작 공의 위치는 2, 못의 위치는 1로 주어진다.
        // 공이 떨어지기 시작하여 돌을 만나면 같은 확률로 왼쪽 혹은 오른쪽으로 떨어진다.
        // 이동하려는 칸 혹은 그 아랫칸이 막혀있다면, 공은 더 이상 진행하지 못하고 처음부터 다시 시작한다.
        // 가장 아랫칸들 중 가장 당첨 확률이 높은 위치는?
        //
        // 큰 수 계산 문제
        // 돌을 만날 때마다 확률이 반이 되고, n이 최대 100으로 주어지므로
        // 총 99번의 1/2 연산이 있을 수 있다.
        // 따라서 BigInteger로 처음 위치에서 2^(n-1)의 값을 가지고서
        // 돌을 만날 때마다 /2로 값을 가져간다면 가장 아랫 칸에서 최소 1이상의 값이 보장된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 추첨판
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        // 시작 위치
        int start = 0;
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++) {
                if ((map[i][j] = Integer.parseInt(st.nextToken())) == 2)
                    start = i * m + j;
            }
        }
        
        // BigInteger로 각 칸의 확률 계산
        BigInteger[][] dp = new BigInteger[n][m];
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++)
                dp[i][j] = BigInteger.ZERO;
        }
        
        // 처음 위치
        dp[start / m][start % m] = BigInteger.TWO.pow(n - 1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / m;
            int col = current % m;
            // 가장 아랫칸이라면 종료
            if (row + 1 == n)
                break;
            // 이미 계산된 칸이라면 건너뜀.
            else if (visited[row][col])
                continue;
            
            // 아랫칸이 비어있는 경우
            // 아래로 떨어짐
            if (map[row + 1][col] == 0) {
                dp[row + 1][col] = dp[row + 1][col].add(dp[row][col]);
                queue.offer((row + 1) * m + col);
            } else if (col - 1 >= 0 && map[row][col - 1] == 0 && map[row + 1][col - 1] == 0) {
                // 돌을 만났는데, 왼쪽으로도 진행할 수 있고
                // 오른쪽으로도 진행할 수 있다면
                // 양쪽으로 값을 반씩 나누어 진행
                if (col + 1 < m && map[row][col + 1] == 0 && map[row + 1][col + 1] == 0) {
                    dp[row + 1][col - 1] = dp[row + 1][col - 1].add(dp[row][col].divide(BigInteger.valueOf(2)));
                    dp[row + 1][col + 1] = dp[row + 1][col + 1].add(dp[row][col].divide(BigInteger.valueOf(2)));
                    queue.offer((row + 1) * m + col - 1);
                    queue.offer((row + 1) * m + col + 1);
                } else {    // 오른쪽으로는 진행할 수 없는 경우
                    dp[row + 1][col - 1] = dp[row + 1][col - 1].add(dp[row][col].divide(BigInteger.valueOf(2)));
                    queue.offer((row + 1) * m + col - 1);
                }
            } else if (col + 1 < m && map[row][col + 1] == 0 && map[row + 1][col + 1] == 0) {
                // 오른쪽으로만 진행이 가능한 경우
                dp[row + 1][col + 1] = dp[row + 1][col + 1].add(dp[row][col].divide(BigInteger.valueOf(2)));
                queue.offer((row + 1) * m + col + 1);
            }
            visited[row][col] = true;
        }

        // 가장 아랫 칸들 중 가장 값이 큰 idx를 찾는다.
        int idx = 0;
        for (int i = 1; i < m; i++) {
            if (dp[n - 1][i].compareTo(dp[n - 1][idx]) > 0)
                idx = i;
        }
        // idx 칸의 값이 0인 경우, 공이 가장 아랫 칸까지 도달하는 것이 불가능한 경우
        // -1 출력
        // 그 외의 경우, 찾은 idx를 출력
        System.out.println(dp[n - 1][idx].equals(BigInteger.ZERO) ? "-1" : idx);
    }
}