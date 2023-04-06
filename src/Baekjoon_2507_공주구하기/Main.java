/*
 Author : Ruel
 Problem : Baekjoon 2507번 공주 구하기
 Problem address : https://www.acmicpc.net/problem/2507
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2507_공주구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 섬이 주어진다.
        // 1번 섬부터 n번 섬까지 갔다가 다시 되돌아오려하며,
        // 각 섬에는 발판의 스프링이 있어 스프링의 세기에 따라 다음에 갈 수 있는 섬이 범위가 달라진다.
        // 한 번 밟은 발판은 망가져 두 번 사용할 수 없다.
        // 또한 어떤 섬들의 스프링은 되돌아 올 때는 사용할 수 없다.
        // 1번 섬에서 n번 섬에 갔다 되돌아오는 경로의 수를 1000으로 나눈 나머지는?
        //
        // DP문제
        // 일반적인 DP일 경우 갈 수 있는 경우의 수를 모두 구하면 되지만,
        // 되돌아올 때는 지나지 않은 섬만 들려야한다.
        // 되돌아온다 생각하지 말고, 동시에 두 명을 n번 섬으로 보내되, 들리는 섬을 겹치지 않게 한다고 생각하자
        // dp[i][j] = 첫번째 사람이 현재 있는 섬 i, 두번째 사람이 현재 있는 섬 j
        // 첫번째 사람은 i섬에 따라서 다음에 갈 수 있는 섬이 달라지고
        // 두번째 사람은 다음 섬의 발판에 따라 j에서 갈 수 있는지가 결정됨에 유의하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 섬의 정보들
        // 섬의 위치, 발판의 세기, 되돌아올 때 이용할 수 있는지 여부
        int[][] islands = new int[n][];
        for (int i = 0; i < islands.length; i++)
            islands[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 첫번째 사람과 두번째 사람이 들린 마지막 섬의 번호
        int[][] dp = new int[n][n];
        dp[0][0] = 1;
        
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] %= 1000;
                
                // 현재 첫번째 사람의 위치 i
                // 두번째 사람의 위치 j
                // 다음 섬은 항상 i, j보다 더 큰 번호의 섬으로만 간다.
                for (int k = Math.max(i, j) + 1; k < n; k++) {
                    // i에서 발판을 밟고 k 섬으로 갈 수 있는 경우
                    if (islands[k][0] <= islands[i][0] + islands[i][1])
                        dp[k][j] += dp[i][j];

                    // j에서 k로 갈 수 있는 경우
                    // 사실은 k에서 j로 오는 경우이기 때문에 k 섬의 발판을 사용한다.
                    if (islands[j][2] == 1 && islands[k][2] == 1 && islands[k][0] <= islands[j][0] + islands[k][1])
                        dp[i][k] += dp[i][j];
                }
            }
        }

        // 마지막 섬은 n번 섬으로 두 사람 모두 도착해야한다.
        // 도착지가 같기 때문에 dp[i][n-1]과 dp[n-1][j]를 모두 계산하여 dp[n-1][n-1]을 구할 경우
        // 중복되는 경로가 생긴다.
        // 따라서 둘 중 하나의 경우만 계산한다.
        int sum = 0;
        for (int i = 0; i < dp.length; i++) {
            if (islands[i][0] + islands[i][1] >= islands[n - 1][0])
                sum += dp[i][n - 1];
        }
        sum %= 1000;
        // 최종 답안 출력.
        System.out.println(sum);
    }
}