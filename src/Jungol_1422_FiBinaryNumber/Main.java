/*
 Author : Ruel
 Problem : Jungol 1422번 fi-binary number
 Problem address : https://jungol.co.kr/problem/1422
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1422_FiBinaryNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0과 1로만 이루어진 수를 Fi-binary라고 부르자.
        // 앞에 0이 붙지 않으며, 연속된 1이 나타나지 않는다.
        // n번째 Fi-binary를 구하라
        //
        // 피보나치 수열. dp, 누적합 문제
        // 해당하는 수를 나열해보면
        // 1, 10, 100, 101, 1000, 1001, 1010, 10000, 10001, 10010, 10100, 10101
        // 하지만 0이 맨 앞에 올 수 없다는 조건에 의해 수열에는 등장하지 않았지만
        // 이후 등장하는 수들을 보면 각 길이의 파이바이너리에 가장 먼저 오는 수는 1...0이다.
        // 따라서 0 1 10 100 101 ... 로 나아가는 수열이 자연스럽다고 볼 수 있고, n번째가 아닌 n+1번째 수를 하는 방식으로 바꾼다.
        // 먼저 연속된 1이 올 수는 없다.
        // 따라서 길이 n인 파이바이너리는 길이 n-1인 파이바이너리 뒤에 0을 하나 붙인 경우와
        // 길이 n-2인 파이바이너리에 01을 붙인 경우의 합으로 볼 수 있다.
        // 00을 붙인 경우는 전자에서 계산되고, 11로 연속한 1이 올 수는 없다
        // 따라서 dp[n] = dp[n-1] + dp[n-2]로 계산된다.
        // 그 후, 누적합을 구해, 각 길이마다 마지막 수가 몇번째 파이바이너리인지 계산해둔다.
        // 그리고 순서 n보다 같거나 크지만 가장 작은 순서 idx를 찾아, 길이를 확정짓는다.
        // 그 길이가 1보다 크다면 1뒤에 1이 올 수 없으므로 10을 붙이고, 두 개의 자리를 내려간다.
        // 현재 idx길이의 순서가 n보다 크다면 0을 추가하며 내려간다.
        // idx가 1까지 내려온 경우는 길이 1인 상태의 첫번째 수는 1이므로 10이 아닌 1만 추가하고 마친다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 각 길이에 따른 파이바이너리 누적합
        int[] psums = new int[44];
        // 0, 1, 10
        psums[0] = 1;
        psums[1] = 1;
        psums[2] = 1;
        // 이후는 점화식으로 계산
        for (int i = 3; i < psums.length; i++)
            psums[i] = psums[i - 1] + psums[i - 2];
        // 누적합 계산
        for (int i = 1; i < psums.length; i++)
            psums[i] += psums[i - 1];

        // 테스트케이스의 수
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 0 1 10 .. 으로 앞에 0을 추가했으므로 n+1번째 수를 찾는다.
            int n = Integer.parseInt(br.readLine().trim()) + 1;
            // n보다 같거나 큰 가장 작은 순서를 찾는다.
            int idx = 0;
            while (psums[idx] < n)
                idx++;
            // n번째 수의 길이는 idx

            // idx가 0보다 큰 동안
            while (idx > 0) {
                // 만약 psums[idx-1]이 n보다 같거나 큰 경우
                // 0일 추가하며 0을 낮춘다.
                while (idx > 0 && psums[idx - 1] >= n) {
                    idx--;
                    sb.append(0);
                }

                // idx가 0보다 큰 경우, 1이 등장할 시점
                if (idx > 0) {
                    // idx가 1이라면 길이가 1인 상태이므로 1만 추가
                    // 그 외의 경우는 10일 추가
                    sb.append(idx == 1 ? 1 : 10);
                    // 해당하는 개수만큼 순서를 빼고
                    n -= psums[idx - 1];
                    // 맨 앞에 1뿐만 아니라 10을 추가했으므로 길이 idx를 2 감소시킨다.
                    idx -= 2;
                }
            }
            // 줄 바꿈
            sb.append("\n");
        }
        // 답 출력
        System.out.println(sb);
    }
}