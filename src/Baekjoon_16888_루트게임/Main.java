/*
 Author : Ruel
 Problem : Baekjoon 16888번 루트 게임
 Problem address : https://www.acmicpc.net/problem/16888
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16888_루트게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] memo;

    public static void main(String[] args) throws IOException {
        // 처음 정수 n이 주어진다.
        // 이 정수로부터 두 사람이 번갈아가며 완전 제곱수 x를  골라 n에서 x를 뺀다.
        // 위 과정을 진행하여 먼저 0을 만드는 사람이 이긴다.
        // 두 사람 모두 완벽하게 게임을 진행한다고 할 때, 이기는 사람은?
        //
        // DP 문제, 게임 이론
        // 메모이제이션을 통해 해당 수에 턴을 받은 사람이 이기는가 지는가에 대해 저장해둔다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // n은 최대 100만까지.
        memo = new int[1_000_001];
        // 0을 만드는 것이 아닌 0에 턴을 받은 사람은 진다.
        memo[0] = 2;
        for (int testCase = 0; testCase < t; testCase++) {
            int n = Integer.parseInt(br.readLine());
            // n으로 시작한 사람이 이긴다면 koosaga
            // 진다면 cubelover 기록
            sb.append(findAnswer(n) == 1 ? "koosaga" : "cubelover").append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    static int findAnswer(int idx) {
        // 아직 계산하지 않았다면
        if (memo[idx] == 0) {
            // 초기값으로 도달한 사람이 진다고 가정.
            memo[idx] = 2;
            // 제곱수를 큰 값에서부터 1까지 계산한다.
            // 만약 작은 값부터 시작하며 idx가 100만이라면, 1까지 호출을 100만번해야한다.
            for (int i = (int) Math.sqrt(idx); i > 0; i--) {
                // idx - i * i 값들 중에 상대방이 지는 경우가 있다면
                if (findAnswer(idx - i * i) == 2) {
                    // idx에 먼저 도달하는 사람이 이김을 표시하고 반복문 종료
                    memo[idx] = 1;
                    break;
                }
            }
        }
        // 값 반환.
        return memo[idx];
    }
}