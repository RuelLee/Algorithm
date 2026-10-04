/*
 Author : Ruel
 Problem : Jungol 12442번 초콜릿 포장
 Problem address : https://jungol.co.kr/problem/12442
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12442_초콜릿포장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] values = new int[]{4, 3, 7, 5};

    public static void main(String[] args) throws IOException {
        // n개의 초콜릿이 일렬로 주어진다. C는 정상, F는 불량을 뜻한다.
        // 각 연속한 초콜릿을 2개 혹은 3개를 묶어 판매한다.
        // 각 묶음에는 불량 초콜릿은 최대 1개만 들어갈 수 있다.
        // 초콜릿을 버릴 수 없으며, 순서를 임의로 바꿀 수도 없다.
        // 정상 초콜릿 2개는 4, 3개는 7달러에 판매하며
        // 불량이 하나 섞인 초콜릿 2개는 3, 3개는 5달러에 판매한다.
        // 모든 초콜릿을 묶음으로 판매할 때 얻는 최대 이익은? 불가능하다면 -1을 출력한다
        //
        // DP 문제
        // dp[i] = i번째 초콜릿을 묶음으로 팔았을 때 최대 이익으로 정하고 dp를 채워나간다.
        // 단, 초콜릿을 버리거나, 순서를 조정할 수 없기 때문에, 모두 나누는 것이 불가능한 경우도 생긴다.
        // 묶음은 두 개 혹은 세 개로 구성되기 때문에, dp[i]는 dp[i-2] 혹은 dp[i-3]을 참조하게 되는데
        // 이 때의 dp[i-2], dp[i-3]이 둘 다 불가능한 경우여서는 안된다.
        // 따라서 dp는 정상적인 경우 단조증가를 그리게 되는데, 최댓값을 계산하며, dp의 연속한 두 값이 최댓값보다 작아지는 시점이 발생하는 경우
        // 불가능한 경우로 처리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 초콜릿
        int n = Integer.parseInt(br.readLine());
        // 각 초콜릿
        char[] chocolates = br.readLine().toCharArray();
        // dp[i] = i번째 초콜릿을 묶음으로 팔았을 때 최대 이익
        int[] dp = new int[n + 1];

        // 현재 최댓값
        int max = 0;
        // 가능 여부
        boolean possible = true;
        for (int i = 2; i < n; i++) {
            // 현재 살펴보는 묶음에서의 불량품 개수
            int error = 0;
            for (int j = 0; j < 2; j++) {
                if (chocolates[i - j] == 'F')
                    error++;
            }

            // 만약 불량품이 연속하여 2개인 경우
            // i까지만으로는 불가능하므로 건너뜀
            if (error > 1)
                continue;
            // 그 외의 경우, i와 i-1로 2묶음의 초콜릿 값 반영
            dp[i + 1] = Math.max(dp[i + 1], dp[i - 1] + values[error]);

            // i-2번째 초콜릿이 불량품인 경우, error 증가
            if (chocolates[i - 2] == 'F')
                error++;
            // 불량품이 2개 이상이라면 불가능하므로 건너뜀
            if (error > 1)
                continue;
            // 3개 묶음의 초콜릿 계산
            dp[i + 1] = Math.max(dp[i + 1], dp[i - 2] + values[2 + error]);
            // 최댓값 반영
            max = Math.max(max, dp[i + 1]);

            // 혹시 연속한 dp의 값이 최댓값보다 작다면 전체 초콜릿을 묶음으로 나누는 것이
            // \불가능한 경우이므로 possible를 false 처리한 뒤, 반복문 종료
            if (dp[i + 1] < max && dp[i] < max) {
                possible = false;
                break;
            }
        }

        // 전체 초콜릿을 묶음으로 나누는 것이 불가능하다면 -1
        // 그 외의 경우 최대 이익을 출력
        System.out.println(!possible ? -1 : dp[n]);
    }
}