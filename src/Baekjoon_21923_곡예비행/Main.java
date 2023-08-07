/*
 Author : Ruel
 Problem : Baekjoon 21923번 곡예 비행
 Problem address : https://www.acmicpc.net/problem/21923
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21923_곡예비행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 0, -1};
    static int[] dc = {0, 1, -1, 0};

    public static void main(String[] args) throws IOException {
        // 모형 비행기 조종 대회가 열린다.
        // 각 비행은 상승 비행과 하강 비행이 있으며
        // 상승 비행에는 오른쪽 or 윗칸으로만 이동할 수 있고
        // 하강 비행에는 오른쪽 or 아랫칸으로만 이동할 수 있다.
        // 각 지점을 지날 때의 점수들이 n * m 으로 주어질 때,
        // 비행을 마친 후 얻을 수 있는 최대 점수를 구하라
        //
        // DP 문제
        // 상승 비행일 때의 점수를 위해
        // n-1, 0에서 시작해서 각 지점에 도달하는 최대 점수를 구한다.
        // 하강 비행일 때는 반대로
        // 도착 지점 n-1, m-1에서 각 지점에 도달하는 최대 점수를 구한다.
        // 그 후 두 합이 최대가 되는 지점의 점수가 비행에서 얻을 수 있는 최대 점수가 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n * m 크기의 격자
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 지점을 지날 때의 점수
        int[][] scores = new int[n][];
        for (int i = 0; i < scores.length; i++)
            scores[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 상승 비행일 때.
        // 점수가 음수도 있다는 점과 값의 크기를 고려하여
        // long타입으로 지정 후, Long.MIN_VALUE로 채워준다.
        long[][] upDP = new long[n][m];
        for (long[] up : upDP)
            Arrays.fill(up, Long.MIN_VALUE);
        // 출발 지점의 점수 초기화
        upDP[n - 1][0] = scores[n - 1][0];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < upDP[i].length; j++) {
                for (int d = 0; d < 2; d++) {
                    int nextR = i + dr[d];
                    int nextC = j + dc[d];

                    if (checkArea(nextR, nextC, upDP))
                        upDP[nextR][nextC] = Math.max(upDP[nextR][nextC], upDP[i][j] + scores[nextR][nextC]);
                }
            }
        }
        
        // 하강 비행
        // 상승 비행과 마찬가지로 초기화
        long[][] downDP = new long[n][m];
        for (long[] dp : downDP)
            Arrays.fill(dp, Long.MIN_VALUE);
        downDP[n - 1][m - 1] = scores[n - 1][m - 1];
        // 다만 하강 비행은 도착지점에서부터 각 지점에 이르는 점수를 구한다.
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                for (int d = 2; d < 4; d++) {
                    int nextR = i + dr[d];
                    int nextC = j + dc[d];

                    if (checkArea(nextR, nextC, downDP))
                        downDP[nextR][nextC] = Math.max(downDP[nextR][nextC], downDP[i][j] + scores[nextR][nextC]);
                }
            }
        }

        long answer = Long.MIN_VALUE;
        // 모든 지점을 살펴보며
        // 상승 비행과 하강 비행을 통해 각 지점에 도달할 때의 점수를 구하고
        // 최대값을 찾는다.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                answer = Math.max(answer, upDP[i][j] + downDP[i][j]);
        }
        // 답안 출력
        System.out.println(answer);
    }

    static boolean checkArea(int r, int c, long[][] dp) {
        return r >= 0 && r < dp.length && c >= 0 && c < dp[r].length;
    }
}