/*
 Author : Ruel
 Problem : Baekjoon 17528번 Two Machines
 Problem address : https://www.acmicpc.net/problem/17528
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package TwoMachines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] tasks;
    static int[] total;

    public static void main(String[] args) throws IOException {
        // 상당히 어려웠다.
        // 일을 한쪽 기계가 모두 한다고 생각하고, 여기서 시간을 줄이는 방법은 A가 맡은 일들 중 일부를 B에게 넘기는 것이다.
        // 그럼 이제 일을 하나 넘기는 경우를 생각해보자
        // A가 모든 일을 맡았을 때 N시간이 걸리고, A기계로는 x, B기계로는 y 시간 걸리는 일을 넘겨준다고 생각해보자
        // 그러면 A의 총 시간은 N - x시간, B의 총시간은 y 시간이 된다
        // 두 시간의 차이는 N에서 N - x - y가 되었다
        // 따라서 한 작업을 다른 기계로 넘겨줬을 때, x + y 만큼의 두 기계 사이 시간 차가 줄어들게 된다.
        // DP의 행은 작업의 번호, 열은 총 시간으로 두자
        // 그리고 x + y가 한 기계가 모든 일을 맡았을 때의 시간을 넘지 않는 선에서의 x값(A기계에서 B기계로 넘겨 줄어드는 시간)을 구하자
        // (= 배낭 문제와 유사해진다. 제한은 시간이 되고, 가치는 x가 된다)
        // 모든 일을 맡았던 기계가 넘겨받는 쪽보다는 시간이 크다는 조건이 있으므로,
        // 전체 시간 - 최종적으로 구해지는 x 값이 두 기계가 적당히 일을 분배했을 때 얻는 두 기계의 작동 시간 중 큰 값이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        tasks = new int[2][n + 1];
        total = new int[2];
        StringTokenizer st;
        for (int i = 1; i < tasks[0].length; i++) {
            st = new StringTokenizer(br.readLine());
            total[0] += tasks[0][i] = Integer.parseInt(st.nextToken());
            total[1] += tasks[1][i] = Integer.parseInt(st.nextToken());
        }
        // 0번 기계가 모든 일을 맡았을 경우, 1번 기계가 모두 맡았을 경우를 계산 후 작은 값을 가져온다.
        System.out.println(Math.min(findMinTime(0), findMinTime(1)));
    }

    static int findMinTime(int machine) {
        // 행은 작업을 의미, 열은 A가 맡은 총 작업의 시간이다.
        int[][] dp = new int[tasks[machine].length][total[machine] + 1];

        // 작업을 순서대로 살펴보며
        for (int i = 1; i < dp.length; i++) {
            // A가 맡은 작업의 시간을 점차 늘려간다.
            for (int j = 1; j < dp[i].length; j++) {
                // 초기값은 이전 작업에서 동일 작업시간일 때의 값과, 동일 작업에서 하나 적은 시간일 때의 값 중 큰 값으로 세팅해준다.
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                // 줄일 수 있는 차이는 작업이 각 기계에 할당되는 시간의 합이다.
                int diff = tasks[0][i] + tasks[1][i];

                // 만약 이번 작업으로 줄어드는 차이가 현재 시간보다 크다면 처음에 정했던 A기계가 작업하는 시간이 B보다 더 크다는 점을 위반하게 된다. 위 경우는 계산 X
                // j값보다 diff가 작을 때만, 현재 작업을 B로 옮기기 전 A 기계가 최대로 줄일 수 있는 시간이 기억된 곳은 dp[i - 1][j - diff] 이다
                // 현재 dp값과 dp[i - 1][j - diff] + 이번 작업으로 줄어드는 시간을 더한 값 중 더 큰 값을 남겨둔다.
                if (j >= diff)
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - diff] + tasks[machine][i]);
            }
        }
        // 최종적으로 dp 가장 끝에 남겨진 값이 A기계의 작업을 B기계로 나눠주었지만, 아직 A기계가 더 많이 일을 하면서도 가장 작업시간이 적은 경우, A 기계의 줄어든 작업 시간이다.
        // A가 모두 맡았을 때의 시간에서 최종 DP값을 빼주면 두 기계가 적절히 일을 나눴을 때 작업 시간이 더 많은 쪽의 시간이다.
        return total[machine] - dp[dp.length - 1][dp[dp.length - 1].length - 1];
    }
}