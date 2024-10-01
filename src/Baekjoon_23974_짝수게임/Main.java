/*
 Author : Ruel
 Problem : Baekjoon 23974번 짝수 게임
 Problem address : https://www.acmicpc.net/problem/23974
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23974_짝수게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 윤구와 한성이 게임을 한다.
        // 윤구는 처음 n개의 동전을 갖고 있으며, 게임판에는 k개의 동전이 있다.
        // 윤구의 차례부터 시작한다.
        // 턴마다 플레이어는 1 ~ 4개의 동전을 가져갈 수 있다.
        // 모든 동전을 가져간 후, 윤구의 동전 개수가 짝수라면 윤구가 승리한다.
        //
        // DP, 게임 이론 문제
        // dp[남은동전][턴][윤구가가진동전의홀짝] = 승리하는 사람
        // 으로 dp를 세우고 푼다.
        // dp 값을 직접 채우다보면 0 ~ 5까지의 결과가 반복됨을 알 수 있다.
        // 따라서 0 ~ 5까지의 결과만 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 윤구가 처음 갖고 있는 동전의 개수 n
        // 전체 동전의 수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 0 ~ 5까지의 결과만 계산한다.
        int[][][] dp = new int[6][2][2];
        // 남은 동전이 0개일 때, 누구 차례이든, 윤구가 가진 동전이 홀수개라면
        // 한성이가 승리한다.
        dp[0][0][1] = dp[0][1][1] = 1;
        
        // 게임판 동전의 개수
        for (int i = 1; i < dp.length; i++) {
            // 0 -> 윤구 차례, 1 -> 한성이 차례
            for (int turn = 0; turn < dp[i].length; turn++) {
                // yoon9가 가진 동전의 홀짝 여부
                // 0일 때 짝수, 1일 때 홀수
                for (int yoon9 = 0; yoon9 < dp[i][turn].length; yoon9++) {
                    // 초기값으로는 상대방이 이기는 걸로 표시
                    dp[i][turn][yoon9] = (turn + 1) % 2;
                    
                    // 가져가는 동전의 수
                    for (int coin = 1; coin <= Math.min(i, 4); coin++) {
                        // coin개의 동전을 가져가, turn에 해당하는 사람이 이기는 경우가 발생한다면
                        // turn에 해당하는 사람은 항상 그 경우를 택한다.
                        if (dp[i - coin][(turn + 1) % 2][(yoon9 + (turn == 0 ? coin : 0)) % 2] == turn) {
                            dp[i][turn][yoon9] = turn;
                            break;
                        }
                    }
                }
            }
        }
        // 0 ~ 5까지의 결과가 반복되므로
        // k의 결과값은 k % 6에 해당하는 결과와 같다
        // 처음은 윤구의 턴이며, 갖고 있는 동전은 n개이므로
        // dp[k % 6][0][n % 2]의 결과값에 따라 답을 출력해주면 된다.
        System.out.println(dp[k % 6][0][n % 2] == 0 ? "YG" : "HS");
    }
}