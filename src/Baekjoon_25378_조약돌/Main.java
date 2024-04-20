/*
 Author : Ruel
 Problem : Baekjoon 25378번 조약돌
 Problem address : https://www.acmicpc.net/problem/25378
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25378_조약돌;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 장소에 조약돌들이 놓여있다.
        // 다음 두 가지 행동
        // 1. 인접한 장소의 조약돌을 같은 개수만큼 줍기
        // 2. 한 장소의 조약돌을 모두 줍기
        // 을 통해 모든 조약돌을 줍고자할 때
        // 행해야하는 최소 행동의 수는?
        //
        // DP 문제
        // 최악의 경우, 조약돌을 모두 2번을 행할 경우 n번을 통해 모든 조약돌을 주울 수 있다.
        // 행동의 수가 줄어드는 경우는 1번 행동을 통해 인접한 장소의 조약돌까지 모두 주워지는 경우이다.
        // 따라서 dp[i] = i 장소까지 조약돌을 줍는데 줄어든 행동의 최대 횟수 라고 정의하고 문제를 풀자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 장소
        int n = Integer.parseInt(br.readLine());
        
        // 각 장소에 놓여있는 조약돌
        int[] stones = new int[n + 2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < stones.length - 1; i++)
            stones[i] = Integer.parseInt(st.nextToken());

        int[] dp = new int[n + 2];
        dp[0] = 0;
        // i번 장소부터 돌을 줍기 시작한다.
        for (int i = 1; i < dp.length - 1; i++) {
            // i-1까지의 줄어든 최대 행동 수를 가져온다.
            dp[i] = Math.max(dp[i], dp[i - 1]);
            // 처음 놓여있는 돌의 수
            int stone = stones[i];
            // i ~ j 까지 1번 행동을 통해 주울 수 있는 최대 위치까지 간다.
            for (int j = i; j < dp.length - 1; j++) {
                // 만약 돌이 0개 이하가 된다면
                if (stone <= 0) {
                    // 0개라면 수가 딱 맞아떨어져 1번 행동을 통해
                    // 행동이 하나 줄어든 경우
                    // 따라서 dp[i-1]까지의 줄어든 행동의 수 +
                    // i ~ j까지 줄어든 행동 1회를 더한 값과 원래 값을 비교하여
                    // 더 큰 값을 남겨둔다.
                    if (stone == 0)
                        dp[j] = Math.max(dp[j], dp[i - 1] + 1);
                    // 남은 돌이 0개 이하가 되었으므로
                    // 더 이상 찾는 것을 중지.
                    break;
                }
                // 돌이 남아있다면
                // 이번에 stone의 돌을 j위치와 j+1 위치에서 줍는다.
                // 남는 돌은 stones[j+1] - stone 개
                stone = stones[j + 1] - stone;
            }
        }
        // n번 행동에서 최대 dp[n]번 행동이 줄어들었다.
        // 해당 값 출력
        System.out.println(n - dp[n]);
    }
}