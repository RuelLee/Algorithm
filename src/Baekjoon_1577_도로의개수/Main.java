/*
 Author : Ruel
 Problem : Baekjoon 1577번 도로의 개수
 Problem address : https://www.acmicpc.net/problem/1577
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1577_도로의개수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 집(0, 0)에서 학교(n, m)로 이동하려고 한다
        // 각 도로들은 격자형태로 이루어져있고, 집에서 학교로 최단 거리로 도달하려고 한다
        // 그런데 k개의 공사중인 도로가 있으며, 해당 도로는 이용하지 못한다고 한다
        // 집에서 학교로 이동하는 최단 거리 방법의 개수를 구하여라
        //
        // DP문제
        // 격자 형태이므로 최단거리로 학교로 도달하는 방법은 가로로 한칸 전진하거나 세로로 한칸 전진하는 수밖에 없다
        // 따라서 DP로 각 지점에 도달하는 방법의 가지수를 계산해나가면 된다
        // 공사중인 도로가 (x1, y1), (x2, y2) 형태로 주어지므로
        // 이를 하나의 수로 만들기 위해 (x, y) -> x + y * width를 사용했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int width = n + 1;
        int height = m + 1;

        // 공사중인 도로의 수
        int k = Integer.parseInt(br.readLine());
        // 공사중인 도로를 저장할 공간.
        HashMap<Integer, HashSet<Integer>> underConstruction = new HashMap<>();
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            // x, y -> x + y * width로 만들어 idx로 만든다.
            int aIdx = a + b * width;
            int bIdx = c + d * width;

            if (!underConstruction.containsKey(aIdx))
                underConstruction.put(aIdx, new HashSet<>());
            if (!underConstruction.containsKey(bIdx))
                underConstruction.put(bIdx, new HashSet<>());

            // 건설중인 도로에 추가.
            underConstruction.get(aIdx).add(bIdx);
            underConstruction.get(bIdx).add(aIdx);
        }

        long[][] dp = new long[height][width];
        // 0, 0 지점에서 출발.
        dp[0][0] = 1;
        for (int row = 0; row < dp.length; row++) {
            for (int col = 0; col < dp[row].length; col++) {
                // 현재 위치의 idx
                int currentIdx = row * width + col;

                // 만약 현재 위치보다 하나 적은 행이 존재한다면
                if (row - 1 >= 0) {
                    // 해당 위치의 idx를 구하고
                    int preIdx = (row - 1) * width + col;
                    // (row - 1, col ) -> (row, col)의 도로가 공사중이 아닐 때만 이전 경우의 수를 더해준다.
                    if (!underConstruction.containsKey(preIdx) ||
                            !underConstruction.get(preIdx).contains(currentIdx))
                        dp[row][col] += dp[row - 1][col];
                }

                // 만약 현재 위치보다 하나 적은 열이 존재한다면
                if (col - 1 >= 0) {
                    // 이전 위치의 idx를 구하고
                    int preIdx = row * width + col - 1;
                    // (row, col -1) -> (row, col)의 도로가 공사중이 아닐 때만 해당 경우의 수를 더해준다.
                    if (!underConstruction.containsKey(preIdx) ||
                            !underConstruction.get(preIdx).contains(currentIdx))
                        dp[row][col] += dp[row][col - 1];
                }
            }
        }
        // 최종적으로 dp[m][n]에 계산된 수가 (0, 0)에서 (m, n)으로 도달하는 최단 거리 우의 수.
        System.out.println(dp[m][n]);
    }
}