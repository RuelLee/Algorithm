/*
 Author : Ruel
 Problem : Baekjoon 22846번 인증된 쉬운 게임
 Problem address : https://www.acmicpc.net/problem/22846
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22846_인증된쉬운게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 사람과 모니터가 있으며 모니터에는 1이 쓰여있다.
        // 모니터에 쓰여있는 수의 약수 중 하나를 더해 k를 초과한 사람이 패배하게 된다.
        // 두 사람 모두 최선의 전략으로 플레이한다할 때 이기는 사람은?
        //
        // 게임 이론
        // 자신의 차례에 k를 만들어 상대방에게 넘기면 이기게 된다.
        // 두 사람 모두 가능한 k를 만들도록 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 k
        int k = Integer.parseInt(br.readLine());

        // whoWin[i] i에 도달하는 사람이 이긴다면 1, 진다면 2
        int[] whoWin = new int[k + 1];
        // k에 도달하는 사람은 항상 지므로 2
        whoWin[k] = 2;
        // 1에서 시작한 사람이 이길 수 있다면 Kail
        // 이길 수 없다면 Ringo를 출력
        System.out.println(findAnswer(1, k, whoWin) == 1 ? "Kali" : "Ringo");
    }

    // dp를 통해 답을 찾는다.
    static int findAnswer(int idx, int k, int[] whoWin) {
        // 이미 계산된 결과가 있다면 해당 값 바로 반환.
        if (whoWin[idx] > 0)
            return whoWin[idx];

        // idx에 도착한 사람이 이기는 방법이 있는지 찾는다.
        whoWin[idx] = 2;
        // i의 제곱이 idx보다 같거나 작은 경우에서만 찾고
        // idx / i 도 약수이므로 같이 계산한다.
        for (int i = (int) Math.sqrt(idx); i > 0; i--) {
            if (idx % i == 0) {     // i가 idx의 약수인 경우
                // idx + i가 k보다 같거나 작고 자신이 k를 만드는 방법이 있거나
                // idx + (idx / i)가 k보다 같거나 작고, 자신이 k를 만드는 방법이 있는 경우
                if ((idx + i <= k && findAnswer(idx + i, k, whoWin) == 2) ||
                        (idx + idx / i <= k && findAnswer(idx + idx / i, k, whoWin) == 2)) {
                    // idx에 도달하는 사람이 이기는 방법이 있음에 체크하고 반복문 종료
                    whoWin[idx] = 1;
                    break;
                }
            }
        }
        // 결과값 반환.
        return whoWin[idx];
    }
}