/*
 Author : Ruel
 Problem : Baekjoon 10835번 카드게임
 Problem address : https://www.acmicpc.net/problem/10835
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10835_카드게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n장씩 두 묶음의 카드셋이 주어진다
        // 각 카드셋의 맨 윗장만 보며
        // 왼쪽 카드셋의 맨 윗장만 버리거나, 두 카드셋의 맨 윗장들을 버릴 수 있다. 이 때의 점수는 0이다
        // 오른쪽 카드셋의 맨 윗장이 왼쪽 카드셋의 맨 윗장의 카드보다 작은 값을 갖고 있다면 오른쪽 카드만 버릴 수 있다. 이 때 점수는 오른쪽 카드의 숫자다
        // 두 카드셋 중 어느 쪽이든 모든 카드가 버려지는 순간 게임은 끝난다고 할 때, 얻을 수 있는 최대 점수는?
        //
        // DP문제.
        // DP문제는 생각할 것을 계속 던져주는 문제 같다.
        // dp[왼쪽카드 순서][오른쪽 카드 순서]로 정해주자
        // dp[0][0]의 각자 0번째 카드가 맨 위에 올라와있는 상태이다.
        // 위 상태에서 위의 3가지의 버리는 방법을 모두 고려해서 dp를 채워나가자
        // 해당 상태가 되는 것이 불가능한 경우가 있을 것(오른쪽 카드를 버리는 건 왼쪽 카드보다 작을 때 뿐만이므로)이다.
        // 이를 위해 각 DP를 -1로 채워두고, -1에서 변하지 않은 dp는 건너뛰도록 하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[][] cards = new int[2][];
        for (int i = 0; i < cards.length; i++)
            cards[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 카드가 모두 버려진 상태가 추가되어야하므로 n+1의 크기로 선언해주었다.
        int[][] dp = new int[n + 1][n + 1];
        // 각 상태에 도달 여부를 확인하기 위에 dp에 -1을 채운다.
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // dp[0][0]의 상태의 경우 점수는 0이다
        // 이를 통해 가능한 경우는 최소 0값이 채워질 것이다.
        dp[0][0] = 0;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length - 1; j++) {
                // 만약은 -1 값이라면 해당 상태가 불가능한 경우. 건너뛴다.
                if (dp[i][j] == -1)
                    continue;

                // 왼쪽 카드를 버리는 경우.
                dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j]);
                // 양쪽 카드를 버리는 경우.
                dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j]);

                // 오른쪽 카드를 버리는 경우.
                // 오른쪽 카드가 왼쪽 카드보다 값이 작아야한다.
                if (cards[0][i] > cards[1][j])
                    dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + cards[1][j]);
            }
        }

        // 두 카드셋 중 하나라도 모두 버려지면 게임이 끝나므로
        // 마지막 행과, 각 행의 마지막 열 값을 방문하며 최대값을 찾는다.
        int max = 0;
        for (int i = 0; i < n + 1; i++) {
            max = Math.max(max, dp[n][i]);
            max = Math.max(max, dp[i][n]);
        }
        System.out.println(max);
    }
}