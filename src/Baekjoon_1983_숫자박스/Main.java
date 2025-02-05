/*
 Author : Ruel
 Problem : Baekjoon 1983번 숫자 박스
 Problem address : https://www.acmicpc.net/problem/1983
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1983_숫자박스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 개의 행과 n열로 이루어진 숫자박스가 주어진다.
        // 전체 칸은 2 * n개의 칸으로 이루어져있고,
        // 각 칸에는 -9 ~ +9까지의 수가 들어있으며, 0은 빈칸은 나타낸다.
        // 숫자 박스의 값은
        // 각 행의 동일한 열의 수를 곱해 더하되, 빈 칸의 경우 열의 위치를 바꿀 수 있다.
        // 가령
        // -3 -1 -2  0  5 -1  0
        //  0 -3  2  4  0  5  2 가 같이 주어진 숫자박스를
        // 
        // -3 -1 -2  0  0  5 -1
        // -3  0  0  2  4  5 -2 와 같이 바꿔 값을 구할 수도 있다.
        // 값을 최대화하여 구하고자할 때 그 값은?
        //
        // DP 문제
        // n이 최대 400으로 주어진다.
        // 처음에 4차원 DP로 구하려했으나 그러면 최대 크기가
        // 200^4으로 억단위를 넘어간다.
        // 따라서 차원을 줄여야했다.
        // dp[열][첫번째행에서사용한0의개수][두번째행에서사용한0의개수] = 최대값
        // 으로 DP를 세우면 시간상, 공간상 범위내에서 문제를 풀 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 열
        int n = Integer.parseInt(br.readLine());
        // 각 숫자박스
        List<List<Integer>> numBoxes = new ArrayList<>();
        for (int i = 0; i < 2; i++)
            numBoxes.add(new ArrayList<>());
        for (int i = 0; i < 2; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int num = Integer.parseInt(st.nextToken());
                // 0이 아닌 경우에만 담는다.
                if (num != 0)
                    numBoxes.get(i).add(num);
            }
        }

        // dp[열][첫번째행에서사용한0의개수][두번째행에서사용한0의개수] = 최대값
        // 각 숫자박스에서 사용할 수 있는 만큼만 배열로 할당
        int[][][] dp = new int[n + 1][n - numBoxes.get(0).size() + 1][n - numBoxes.get(1).size() + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++)
                Arrays.fill(dp[i][j], Integer.MIN_VALUE);
        }
        dp[0][0][0] = 0;

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    // 값이 초기값이라면 건너뛴다.
                    if (dp[i][j][k] == Integer.MIN_VALUE)
                        continue;
                    
                    // 두 열 모두 아직 남은 수가 있다면
                    // 해당 두 수를 꺼내 곱한 결과값 반영
                    if (i - j < numBoxes.get(0).size() && i - k < numBoxes.get(1).size())
                        dp[i + 1][j][k] = Math.max(dp[i + 1][j][k], dp[i][j][k] + numBoxes.get(0).get(i - j) * numBoxes.get(1).get(i - k));
                    
                    // 첫번째 행에서 아직 안 사용한 0이 남아있다면
                    if (j + 1 < dp[i].length)
                        dp[i + 1][j + 1][k] = Math.max(dp[i + 1][j + 1][k], dp[i][j][k]);
                    
                    // 두번째 행에서 아직 안 사용한 0이 남아있다면
                    if (k + 1 < dp[i][j].length)
                        dp[i + 1][j][k + 1] = Math.max(dp[i + 1][j][k + 1], dp[i][j][k]);
                    
                    // 두 행 모두 아직 사용하지 않은 0이 남아있다면
                    if (j + 1 < dp[i].length && k + 1 < dp[i][j].length)
                        dp[i + 1][j + 1][k + 1] = Math.max(dp[i + 1][j + 1][k + 1], dp[i][j][k]);
                }
            }
        }
        // 모두 계산할 결과값 출력
        System.out.println(dp[n][n - numBoxes.get(0).size()][n - numBoxes.get(1).size()]);
    }
}