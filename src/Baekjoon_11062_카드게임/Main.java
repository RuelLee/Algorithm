/*
 Author : Ruel
 Problem : Baekjoon 11062번 카드 게임
 Problem address : https://www.acmicpc.net/problem/11062
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11062_카드게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] cards;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // n개의 카드가 주어지고, a, b 두 플레이어는 각각 최좌측 혹은 최우측 카드를 가져갈 수 있다
        // 각 플레이어는 자신의 점수를 최대로 만들기 위한 최선의 전략으로 게임을 한다고 한다
        // a(먼저 턴을 진행하는) 플레이어가 얻는 가장 큰 점수는?
        // 
        // 게임 이론과 관련된 문제였다
        // 단순히 자신이 고를 수 있는 두 카드 중 더 높은 카드를 선택하는 것이 아니라,
        // 카드를 선택하면 그 안쪽 카드를 선택할 수 있게 되는데, 값이 큰 카드를 상대방이 못 가져가게 선택하는 것도 포함된다
        // 따라서 dp를 통해 start카드 부터 end카드 까지의 범위에 대해 a가 얻을 수 있는 최대 값을 bottom-up 방식으로 구해보자
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            cards = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            dp = new int[n][n];
            sb.append(findAnswer(0, n - 1, 0)).append("\n");
        }
        System.out.print(sb);
    }

    // startCard부터 endCard까지 차례에 따른 a플레이어가 얻을 수 있는 최대값 반환
    static int findAnswer(int startCard, int endCard, int turn) {
        // 이미 dp 결과가 있다면 바로 참고
        if (dp[startCard][endCard] != 0)
            return dp[startCard][endCard];
        
        // 만약 한 장의 카드만 남은 경우
        // a플레이어의 차례(0)일 경우에는 해당 카드의 값 반환
        // b를레이어의 차례(1)일 경우에는 a가 얻는 점수가 없으므로 0 반환
        if (startCard == endCard)
            return (turn == 0 ? cards[startCard] : 0);

        // a플레이어의 차례일 경우
        // 단순히 지금 얻는 카드의 큰 값이 잠재적으로 따졌을 때는 낮은 총합을 가질 수있다
        // 따라서 왼쪽 카드를 선택했을 때와 오른쪽 카드를 선택했을 때, 모두 계산해서 더 큰값을 dp에 남기고 반환한다.
        if (turn == 0)
            return dp[startCard][endCard] = Math.max(findAnswer(startCard + 1, endCard, 1) + cards[startCard],
                    findAnswer(startCard, endCard - 1, 1) + cards[endCard]);
        // b플레이어의 차례일 경우
        // 어차피 a플레이어가 얻는 값은 0이지만, b플레이어가 어떤 카드를 선택하느냐에 따라 a플레이어가 다음에 선택할 수 있는 카드가 달라진다
        // dp에는 a플레이어의 기준으로 값이 기록되어있으므로, b플레이어는 자신이 최대값을 얻는 방법인, 더 작은 dp를 가지는 값을 가져와
        // dp에 기록하고 리턴한다.
        return dp[startCard][endCard] = Math.min(findAnswer(startCard + 1, endCard, 0),
                findAnswer(startCard, endCard - 1, 0));
    }
}