/*
 Author : Ruel
 Problem : Baekjoon 2600번 구슬게임
 Problem address : https://www.acmicpc.net/problem/2600
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2600_구슬게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 사람 A, B가 두 개의 구슬 통에서 구슬을 꺼내는 게임을 한다.
        // 한 사람이 한 번에 꺼낼 수 있는 구슬의 개수는 세가지다.
        // 게임은 A가 먼저하고, 그 후에 B 순으로 번갈아가면서 진행된다.
        // 구슬을 꺼낼 수 없게 되는 사람이 패배한다.
        // 서로 최선의 방식으로 게임을 한다고할 때, 승자는?
        //
        // 메모이제이션, 게임 이론,  문제.
        // 남은 구슬의 수를 바탕으로, 현재 구슬을 꺼내는 사람이 이기는지, 지는지에 대해 메모이제이션을 통해 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 한번에 꺼낼 수 있는 구슬의 개수
        int[] removableBids = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(removableBids);

        StringBuilder sb = new StringBuilder();
        // 총 5번의 게임을 진행한다.
        for (int t = 0; t < 5; t++) {
            // 현재 구슬 통에 들어있는 구슬의 개수
            int[] bidsInBucket = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // dp[a][b] 각각의 통에 남은 구슬의 양.
            int[][] dp = new int[bidsInBucket[0] + 1][bidsInBucket[1] + 1];
            for (int[] d : dp)
                Arrays.fill(d, -1);
            // 0값이 리턴된다면, A가 승리, 1이 반환된다면 B가 승리
            sb.append(findAnswer(bidsInBucket, removableBids, dp) == 0 ? 'A' : 'B').append("\n");
        }

        System.out.print(sb);
    }

    // 메모이제이션을 통해 해당 턴(= 해당 구슬의 개수)에 진입한 사람이 이길 수 있는지 계산한다.
    static int findAnswer(int[] bidsInBucket, int[] removableBids, int[][] dp) {
        // 이미 결과가 있다면 해당 결과 반환.
        if (dp[bidsInBucket[0]][bidsInBucket[1]] != -1)
            return dp[bidsInBucket[0]][bidsInBucket[1]];

        // dp[a][b] = 0은 구슬이 a, b개 남았을 때 해당 턴에 진입한 사람이 승리함을, 1의 경우 상대방이 승리함을 의미한다.
        int answer = 1;
        // 꺼낼 수 있는 구슬 remove개
        for (int remove : removableBids) {
            // 첫번째 구슬 통에 구슬이 remove보다 같거나 많이 있다면
            if (remove <= bidsInBucket[0]) {
                // remove개를 제거하고
                bidsInBucket[0] -= remove;
                // 진행했을 때, 현재 턴을 진행한 사람이 승리할 수 있는지 확인한다.
                // 재귀적으로 findAnswer 메소드를 호출했을 때 리턴값으로 1이 온다면, 현재 턴을 진행하는 사람이 이길 수 있는 경우.
                // 재귀적으로 호출한 턴에서는 상대방이 게임을 진행하는데, 지는 경우만 있다는 뜻이므로
                // 0이 이기는 경우, 1이 지는 경우이므로 더 작은 값만 남겨둔다.
                answer = Math.min(answer, (findAnswer(bidsInBucket, removableBids, dp) + 1) % 2);
                // 구슬 복구
                bidsInBucket[0] += remove;

                // 만약 현재 턴을 진행하는 사람이 승리한다면 더 이상 다른 경우는 살펴보지 않는다.
                if (answer == 0)
                    break;
            }

            // 마찬가지로 두번째 구슬 통에서 remove개의 구슬을 제거하는 경우.
            if (remove <= bidsInBucket[1]) {
                bidsInBucket[1] -= remove;
                answer = Math.min(answer, (findAnswer(bidsInBucket, removableBids, dp) + 1) % 2);
                bidsInBucket[1] += remove;

                if (answer == 0)
                    break;
            }
        }
        // 결과값을 저장하고 반환.
        return dp[bidsInBucket[0]][bidsInBucket[1]] = answer;
    }
}