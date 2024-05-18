/*
 Author : Ruel
 Problem : Baekjoon 20120번 호반우와 리듬게임
 Problem address : https://www.acmicpc.net/problem/20120
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20120_호반우와리듬게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 리듬게임을 진행한다.
        // 총 n개의 노트가 주어지고
        // 각 노트의 점수는 음수일 수도 있다.
        // 연속하여 노트를 처리할 경우, 노트의 점수 * 콤보의 수로 점수가 누적된다.
        // 3개의 노트를 연속하여 놓칠 경우, 0점이 되고, 더 이상 점수를 얻을 수 없다.
        // 모든 노트는 순서대로 처리한다.
        // 얻을 수 있는 최대 점수는?
        //
        // DP 문제
        // dp문제이나 조금 생각해야하는 문제
        // 각 노트를 처리할 때, 노트를 맞추거나, 실패하는 경우 두 가지가 될 수 있으며
        // 성공하는 경우는 콤보가 몇번째 콤보인지에 따라 점수 계산이 다르며
        // 실패하는 경우 역시, 몇 번 연속하여 실패하였는지가 중요하게 작용한다.
        // 따라서 dp로
        // dp[현재 노드][콤보] = 점수
        // dp[i][j]에 대해 살펴보면
        // i-1번째에서 j-1번째 콤보가 성립되야만, dp[i][j]가 가능하다.
        // 따라서 dp[i][j] = dp[i-1][j-1] + 노트의 점수 * j로 계산된다.
        // dp[i][0]인 현재 노트를 실패하는 경우에 대해서는
        // i번째 혹은 i-1번째 노드를 1이상 콤보로 성공한 경우에 대해서 실패하는 것이 허락된다.
        // 3번 이상 노트를 놓치면 점수가 0이되고 더 이상 점수를 얻을 수 없으므로.
        // 위 사항들을 유의하며 문제를 푼다.
        // 추가로 3번 이상 연속하여 실패할 경우 점수가 0점이 되는데
        // 점수의 범위가 -가 될 수 있으므로, 3번 연속하여 실패하는 경우도
        // 최대 점수가 될 수 있음에 유의한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노트
        int n = Integer.parseInt(br.readLine());
        int[] notes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp
        long[][] dp = new long[n + 1][n + 1];
        // 초기값 세팅
        for (int i = 2; i < dp.length; i++)
            Arrays.fill(dp[i], Long.MIN_VALUE);
        // 해당 노트를 1콤보 이상 성공한 경우에 대해
        // 최대 점수를 계산해둔다.
        long[] max = new long[n + 1];
        // 초기값
        Arrays.fill(max, Long.MIN_VALUE);
        max[0] = 0;
        
        // 첫번째 노트를 성공한 경우
        dp[1][1] = max[1] = notes[0];
        // 두번째 노트부터 반복문으로 처리
        for (int i = 1; i < notes.length; i++) {
            // 이번 노트를 실패할 경우
            // i번째 혹은 i-1번째를 1콤보 이상 성공한 경우에 대해서
            // 최대 점수를 가져온다.
            dp[i + 1][0] = Math.max(max[i], max[i - 1]);

            // 이번 노트를 성공하는 경우
            for (int j = 0; j <= i; j++) {
                // 현재 점수 + 노트 * 콤보의 값으로 계산된다.
                dp[i + 1][j + 1] = dp[i][j] + notes[i] * (j + 1);
                // i+1번째 노트를 1콤보 이상 성공한 경우에 대해
                // 최대값을 미리 계산해둔다.
                max[i + 1] = Math.max(max[i + 1], dp[i + 1][j + 1]);
            }
        }

        // 함정 같은 부분이다.
        // 3번 이상 연속하여 실패하면 점수가 0점으로 고정되는데
        // 노트를 맞추는 경우 점수가 음수가 될 수도 있으므로
        // 해당 경우를 유용하게 사용할 수도 있다.
        // 따라서 최소 점수는 0점 이상으로 계산한다.
        long answer = 0;
        // dp값에서 0보다 큰 가장 높은 점수 값을 가져온다.
        for (int i = 0; i < dp[n].length; i++)
            answer = Math.max(answer, dp[n][i]);
        // 최대 점수 출력
        System.out.println(answer);
    }
}