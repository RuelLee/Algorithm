/*
 Author : Ruel
 Problem : Baekjoon 9029번 정육면체
 Problem address : https://www.acmicpc.net/problem/9029
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9029_정육면체;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // w * l * h 크기의 직육면체가 주어진다.
        // 이들을 최소 개수의 정육면체들로 나누려고 한다.
        // 만들 수 있는 최소 정육면체의 수는?
        //
        // DP 문제
        // dp를 통해 dp[w][l][h]일 때, 만들 수 있는 정육면체의 수를 구한다.
        // 초기값으로 w == l == h일 때의 값은 모두 1이다.
        // 또한 정육면체는 위, 아래, 옆 구분이 없으므로
        // 사실 가로, 세로, 높이의 순서가 서로 뒤바뀌어도 같은 값을 갖게 된다.
        // 따라서 가로 <= 세로 <= 높이 순으로 고정하여 연산을 줄이도록 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 테스트케이스가 여러개이므로, DP를 통해 모든 값을 구하고
        // 해당하는 값을 바로 출력하도록 한다.
        int[][][] memo = new int[201][201][201];
        // i는 1부터 ~
        for (int i = 1; i < memo.length; i++) {
            // j는 i값 부터~
            for (int j = i; j < memo[i].length; j++) {
                // k는 j값 부터~
                for (int k = j; k < memo[i][j].length; k++) {
                    // 세 값이 모두 같다면 1.
                    // 그렇지 않다면 큰 값으로 초기화하고 값을 구한다.
                    memo[i][j][k] = (i == j && j == k ? 1 : Integer.MAX_VALUE);

                    // (i, j, k) 직육면체를 (l, j, k), (i - l, j, k)의 직육면체로 나누었을 때
                    for (int l = 1; l <= i / 2; l++)
                        memo[i][j][k] = Math.min(memo[i][j][k], memo[l][j][k] + memo[i - l][j][k]);

                    // (i, j, k) 직육면체를 (i, l, k), (i, j - l, k)의 직육면체로 나누었을 때
                    // l과 j - l이 i보다 작아질 수 있음에 유의한다.
                    for (int l = 1; l <= j / 2; l++)
                        memo[i][j][k] = Math.min(memo[i][j][k], (l < i ? memo[l][i][k] : memo[i][l][k]) + (j - l < i ? memo[j - l][i][k] : memo[i][j - l][k]));

                    // (i, j, k) 직육면체를 (i, j, l), (i, j, k - l)의 직육면체로 나누었을 때
                    // l과 k - l이 i와 j보다 작아질 수 있음에 유의한다.
                    for (int l = 1; l <= k / 2; l++)
                        memo[i][j][k] = Math.min(memo[i][j][k], (l < i ? memo[l][i][j] : (l < j ? memo[i][l][j] : memo[i][j][l]))
                                + (k - l < i ? memo[k - l][i][j] : (k - l < j ? memo[i][k - l][j] : memo[i][j][k - l])));
                }
            }
        }

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // 구하고자 하는 직육면체의 사이즈
            int[] size = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            Arrays.sort(size);
            // 가로 <= 세로 <= 높이인 형태로 구했으므로
            // 정렬하고, 해당하는 계산 결과를 기록한다.
            sb.append(memo[size[0]][size[1]][size[2]]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}