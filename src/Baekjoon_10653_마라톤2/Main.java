/*
 Author : Ruel
 Problem : Baekjoon 10653번 마라톤 2
 Problem address : https://www.acmicpc.net/problem/10653
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10653_마라톤2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 마라톤을 하는데 n개의 체크포인트가 주어진다.
        // 이 중 k개의 체크포인트를 건너뛰고 마라톤을 최소 거리로 마치고 싶다.
        // 첫번째와 마지막 체크포인트는 건너뛰지 못한다고 할 때
        // 마라톤을 마치는 최소 거리는?
        //
        // DP문제
        // dp[i][j]로 i에 도달하는데, j개를 건너뛰었을 때의 최소 거리로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 체크포인트와 k개의 건너뛸 수 있는 개수
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 체크포인트의 위치
        int[][] points = new int[n][];
        for (int i = 0; i < points.length; i++)
            points[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[][] dp = new int[n][k + 1];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 첫번째 포인트에서의 거리는 0
        dp[0][0] = 0;

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 만약 dp[i][j]에 초기값이라면 해당 방법으로 도달하는 방법이 없는 경우
                // j개를 건너뛰어 i로 도달하는 방법이 존재하지 않는다면, j이상의 값에 대해서도 마찬가지이므로
                // j 이상의 탐색은 하지 않는다.
                if (dp[i][j] == Integer.MAX_VALUE)
                    break;

                // 체크포인트를 건너뛰지 않는 경우.
                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + calcDistance(i, i + 1, points));

                // 건너뛰는 경우
                // jump가 1일 경우, i+1을 건너뛰고, i -> i + 2로 간다.
                // 따라서 i + jump + 1 값이 n보다 작아야하며
                // 이전에 뛴 값 j 와 jump의 합이 k보다 같거나 작아야한다.
                // 이전에 기록된 값과 비교하여 더 작아질 경우 갱신.
                for (int jump = 1; j + jump <= k && i + jump + 1 < n; jump++)
                    dp[i + jump + 1][j + jump] = Math.min(dp[i + jump + 1][j + jump],
                            dp[i][j] + calcDistance(i, i + jump + 1, points));
            }
        }

        // n번 지점에 k번 건너뛰어 도달할 때의 최소 거리를 출력한다.
        System.out.println(dp[n - 1][k]);
    }

    // start와 end 지점 사이의 거리를 계산한다.
    static int calcDistance(int start, int end, int[][] points) {
        return Math.abs(points[start][0] - points[end][0]) +
                Math.abs(points[start][1] - points[end][1]);
    }
}