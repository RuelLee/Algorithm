/*
 Author : Ruel
 Problem : Baekjoon 3933번 라그랑주의 네 제곱수 정리
 Problem address : https://www.acmicpc.net/problem/3933
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3933_라그랑주의네제곱수정리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] dp = new int[1 << 15];

    public static void main(String[] args) throws IOException {
        // 양의 정수는 많아야 4개의 제곱수로 표현할 수 있다고 한다. 이를 라그랑주의 네 제곱수 정리라고 한다.
        // 여러개의 n이 주어질 때 해당 수를 라그랑주의 네 제곱수로 표현할 수 있는 가짓수를 출력하라.
        // 경우의 수를 구할 때 제곱수의 순서가 바뀌는 경우는 같은 경우로 생각한다. 3^2 + 4^2 = 4^2 + 3^2
        //
        // 브루트포스, DP문제
        // 메모이제이션을 활용한 bottom- up 방식으로 풀려고 했으나
        // 각 경우에 따른 마지막 제곱수가 무엇인지도 표시해야해서 구현이 어려웠다.
        // 그냥 브루트포스를 활용하여 모든 경우의 수를 직접 계산하더라도 시간과 메모리 내에서 작동할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 1부터 범위를 넘지 않는 제곱수들로 첫 제곱수를 정하며 라그랑주의 네 제곱수를 모두 구한다.
        for (int i = 1; Math.pow(i, 2) < dp.length; i++)
            fillDP(1, i, (int) Math.pow(i, 2));

        String input = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (input != null) {
            int num = Integer.parseInt(input);
            // 0이 들어올 때까지 반복하며 라그랑주의 네 제곱수가 가능한 경우의 수를 기록해나간다.
            if (num == 0)
                break;
            // num의 경우의 수를 StringBuilder에 기록한다.
            sb.append(dp[num]).append("\n");
            // 다음 수를 입력받는다.
            input = br.readLine();
        }
        // 전체 답안 출력.
        System.out.println(sb);
    }

    // 지금 몇번째 제곱수인지, 이전 수는 무엇인지, 합은 얼마인지를 매개변수로 넘겨주며
    // 재귀적으로 메소드를 호출하며 라그랑주의 네 제곱수들을 구한다.
    static void fillDP(int idx, int preNum, int sum) {
        // 제곱수들의 합이 sum인 수를 하나 구했다.
        // sum의 경우의 수 하나 추가.
        dp[sum]++;

        // 만약 이번이 4번째 제곱수라면 더 이상 진행해선 안된다.
        // 종료.
        if (idx == 4)
            return;

        // 이전 수보다 같거나 크면서, 범위를 넘지 않는 수를 선택해 재귀적으로 메소드를 호출한다.
        // 이전 수보다 같거나 큰 조건은, 제곱 수의 순서가 바뀌는 경우는 한 가지 경우로 세기로 했으므로
        // 오름차순 정렬되어있는 수들로 구하기 위함이다.
        for (int i = preNum; Math.pow(i, 2) + sum < dp.length; i++)
            fillDP(idx + 1, i, sum + (int) Math.pow(i, 2));
    }
}