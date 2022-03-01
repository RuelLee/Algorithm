/*
 Author : Ruel
 Problem : Baekjoon 2449번 전구
 Problem address : https://www.acmicpc.net/problem/2449
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2449_전구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 전구가 연이어 있으며, 전구들의 m개의 색으로 빛날 수 있다
        // 연속한 전구의 색이 같을 경우, 한번에 연속한 전구들의 색을 한번에 바꿀 수 있다고 한다
        // 전체 전구들을 한 색으로 통일하는데 총 몇 번의 변화가 필요한가
        // dp문제!
        // dp[i][j][k]를 i번 전구부터 j번 전구를 k색으로 통일하는데 필요한 최소 변경횟수라고 하자
        // i ~ j까지의 전구를 k색으로 통일하는데는 두가지 방법이 있다.
        // 1. i부터 j 사이의 mid값을 정해, 두 파츠로 나누고, i ~ mid까지를 k색으로 변경하는데 드는 횟수와 mid + 1부터 j까지를 각각 k색으로 변경하는 횟수를 합치는 경우
        // 2. 두 색을 통일 했을 때 가장 적은 횟수를 사용하는 색으로 바꾸고 두 파츠를 한번에 k색으로 바꾸는 경우.
        // 위 두가지를 고려해서 짜보자!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[][][] dp = new int[n][n][m + 1];
        // MAX값으로 초기값 세팅
        for (int[][] dy : dp) {
            for (int[] d : dy)
                Arrays.fill(d, Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; i++) {
            // 하나의 전구일 때는, 해당 전구의 주어진 색일 때는 변경 횟수 0
            // 그 외의 색은 1
            Arrays.fill(dp[i][i], 1);
            dp[i][i][Integer.parseInt(st.nextToken())] = 0;
        }

        for (int diff = 1; diff < n; diff++) {      // 시작 전구부터 끝나는 전구까지의 차이. 1부터 시작해서 점점 늘려간다(=범위를 늘려간다)
            for (int start = 0; start + diff < n; start++) {        // 0번 전구부터, 마지막 전구가 n-1번일 때까지
                for (int mid = start; mid < start + diff; mid++) {      // mid는 start부터 start + diff 보단 작은 동안.
                    int minChanges = Integer.MAX_VALUE;     // 특정 색으로 전구들을 색 변경을 할 때 최소 변경 횟수를 저장한다.
                    for (int color = 1; color < m + 1; color++) {       // 1번 색부터 m번 색까지
                        // start ~ start + diff 까지의 전구를 1번 방법을 통해 바꾸는 최소 횟수는
                        // start ~ mid까지의 전구를 k색으로 바꾸는 횟수와 mid + 1 ~ start + diff까지의 전구를 k색으로 바꾸는 횟수의 합
                        dp[start][start + diff][color] = Math.min(dp[start][start + diff][color], dp[start][mid][color] + dp[mid + 1][start + diff][color]);
                        // 전체 색의 최소 변경 횟수가 갱신되는지 확인.
                        minChanges = Math.min(minChanges, dp[start][start + diff][color]);
                    }
                    // 2번 방법으로 전구들의 색을 바꾸는 경우에 대해 생각한다.
                    // start ~ start + diff까지의 전구들을 minChanges로 저장된 횟수에 해당하는 색으로 변경하고, 두 파츠를 한번에 color 색으로 바꾸는 경우.
                    for (int color = 1; color < m + 1; color++)
                        dp[start][start + diff][color] = Math.min(dp[start][start + diff][color], minChanges + 1);
                }
            }
        }

        // 최종적으로 0번부터 n-1번까지의 전구에 대해 저장된 dp[0][n -1]을 모든 색에 대해 살펴보며
        // 그 변경 횟수가 가장 적은 값이 정답.
        int answer = dp[0][n - 1][0];
        for (int i = 1; i < m + 1; i++)
            answer = Math.min(answer, dp[0][n - 1][i]);
        System.out.println(answer);
    }
}