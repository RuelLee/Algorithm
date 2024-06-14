/*
 Author : Ruel
 Problem : Baekjoon 12429번 생존자 (Small)
 Problem address : https://www.acmicpc.net/problem/12429
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12429_생존자_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 음식에 대해 남아있는 유통기한과 허기를 참을 수 있는 정도가 주어진다.
        // 당장 음식을 먹기 시작하며 허기가 오기 전까지 다른 음식을 먹지 않는다.
        // 허기가 오면 바로 음식을 먹어야하며, 유통기한이 지난 음식은 폐기한다.
        // 최대한 생존할 수 있는 시간을 구하라
        //
        // 비트마스킹, DP 문제
        // n이 최대 10
        // 유통기한과 허기를 참을 수 있는 기간이 최대 100까지 주어지므로
        // DP와 비트마스킹을 통해 풀 수 있다
        // dp[시간][현재먹은음식의비트마스킹] = 생존여부
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // n개의 음식에 대한 정보
            int n = Integer.parseInt(br.readLine());
            int[][] foods = new int[n][];
            for (int i = 0; i < foods.length; i++)
                foods[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            
            // 최대 200분까지 생존할 수 있으며, 그 때의 음식 상태
            boolean[][] dp = new boolean[201][1 << n];
            dp[0][0] = true;
            // 최대 생존한 시간
            int max = 0;
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[i].length; j++) {
                    // 만약 i시간까지 j상태로 생존한 기록이 없다면 건너뜀.
                    if (!dp[i][j])
                        continue;
                    
                    // 존재한다면 생존 최대시간 갱신
                    max = Math.max(max, i);
                    // 현재 먹을 수 있는 음식의 상태를 살펴본다.
                    for (int k = 0; k < n; k++) {
                        // 유통기한이 지난 음식은 건너뜀.
                        if (foods[k][0] < i)
                            continue;
                        // 유통 기한이 지나지 않았고, 아직 먹지 않은 음식이라면
                        // dp에 해당 사항 표시
                        else if ((j & (1 << k)) == 0)
                            dp[i + foods[k][1]][j | (1 << k)] = true;
                    }
                }
            }
            // 최종적으로 생존한 시간을 기록
            sb.append("Case #").append(testCase + 1).append(": ").append(max).append("\n");
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }
}