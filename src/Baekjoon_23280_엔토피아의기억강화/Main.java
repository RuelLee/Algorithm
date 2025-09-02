/*
 Author : Ruel
 Problem : Baekjoon 23280번 엔토피아의 기억강화
 Problem address : https://www.acmicpc.net/problem/23280
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23280_엔토피아의기억강화;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // 3 * 4 크기의 정수가 그려진 게임판이 있다
    //  1  2  3
    //  4  5  6
    //  7  8  9
    // 10 11 12
    // 게임판을 누를 때는 왼손 엄지나, 오른손 엄지로 누른다.
    // 처음 두 손가락은 1번과 3번에 올려져있다.
    // 손가락을 이동시킬 때는 두 칸 사이의 거리만큼 체력을 사용한다.
    // 두 칸 사이의 거리는 동서방향 거리 + 남북방향 거리로 정의한다.
    // 또한 왼손 엄지로 누를 때는 a만큼 오른손 엄지로 누를 때는 b만큼 체력을 사용한다.
    // n개의 눌러야되는 수가 주어질 때
    // 모든 수를 누르는데 드는 최소 체력은?
    //
    // DP 문제
    // dp[누른번호의수][왼손엄지의위치][오른손엄지의위치] = 사용한 체력
    // 으로 두고 DP를 채워나간다.
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 눌러야하는 수의 개수 n, 왼손 엄지로 누를 때 소모하는 체력 a, 오른손 엄지로 누를 때 소모하는 체력 b
        int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 눌러야되는 수들
        int[] order = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < order.length; i++)
            order[i] = Integer.parseInt(st.nextToken());

        // dp[누른번호의수][왼손엄지의위치][오른손엄지의위치] = 사용한 체력
        int[][][] dp = new int[n + 1][13][13];
        // 초기값
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++)
                    dp[i][j][k] = Integer.MAX_VALUE;
            }
        }
        dp[0][1][3] = 0;
        
        // 모든 수를 살펴보며
        for (int i = 0; i < order.length; i++) {
            // 왼손 엄지의 위치
            for (int j = 0; j < dp[i].length; j++) {
                // 오른손 엄지의 위치
                for (int k = 0; k < dp[i][j].length; k++) {
                    // 초기값이라면 불가능한 경우이므로 건너뛴다.
                    if (dp[i][j][k] == Integer.MAX_VALUE)
                        continue;
                    
                    // 왼손 엄지로 order[i]를 누를 때
                    dp[i + 1][order[i]][k] = Math.min(dp[i + 1][order[i]][k], dp[i][j][k] + calcDistance(j, order[i]) + a);
                    // 오른손 엄지로 order[i]를 누를 때
                    dp[i + 1][j][order[i]] = Math.min(dp[i + 1][j][order[i]], dp[i][j][k] + calcDistance(k, order[i]) + b);
                }
            }
        }
        
        // 모든 수를 다 누른 경우.
        // 엄지들의 모든 위치를 고려하여, 최소값을 찾는다.
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < dp[n].length; i++) {
            for (int j = 0; j < dp[n][i].length; j++)
                min = Math.min(min, dp[n][i][j]);
        }
        // 답 출력
        System.out.println(min);
    }

    static int calcDistance(int a, int b) {
        return Math.abs((a - 1) / 3 - (b - 1) / 3)
                + Math.abs((a - 1) % 3 - (b - 1) % 3);
    }
}