/*
 Author : Ruel
 Problem : Baekjoon 10982번 행운쿠키 제작소
 Problem address : https://www.acmicpc.net/problem/10982
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 행운쿠키제작소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] cookies;
    static int[] ovenTotalTime;

    public static void main(String[] args) throws IOException {
        // 이전에 푼 Two Machines(https://www.acmicpc.net/problem/17528)와 유사한 문제
        // A라는 오븐과 B라는 오븐에서 구울 때 굽는 시간이 서로 다른 반죽들이 주어질 때
        // 모든 쿠키들을 굽는데 필요한 최소 시간을 구하는 문제
        // 기본적인 개념은 같다
        // 한쪽 오븐에 모든 반죽을 할당한 후, 다른 한 오븐에 적당히 분배하여 두 오븐 중 굽는 시간이 오래 걸리는 쪽의 시간을 최소화해야한다.
        // A라는 오븐에 모두 맡길 경우, 20이라는 시간이 걸린다고 치자
        // 그렇다면 A는 20, B는 0인 상태이다
        // 이 때 5 / 10 인 반죽을 B로 옮긴다고 치면 A는 15, B는 10이 된다
        // 차이가 20에서 5로 줄어들었다. A에서는 5가 줄어들었고, B에서는 10이 증가했기 때문이다
        // 따라서 반죽이 각 오븐에서 굽는데 걸리는 시간의 합은 한쪽 기계에서 다른 한쪽 기계로 옮길 때 줄어드는 시간의 차이다.
        // 그리고 이 때의 A 오븐에서 줄어든 시간을 구하면 A 기계가 걸리는 시간이 나온다.
        // A 오븐이 모든 일을 맡았을 때부터, 점차 시간이 줄어들어, B 오븐보다 더 적은 경우로도 이어지지만, 계산이 복잡해지므로
        // A 오븐이 모든 일을 맡았을 때부터 -> A 오븐이 B 오븐보단 더 많은 시간이 걸리지만 최소 시간인 경우와
        // B 오븐이 모든 일을 맡았을 때부터 -> B 오븐이 A 오븐보단 더 많은 시간이 걸리지만 최소 시간인 경우로 나눠서 풀자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());

            cookies = new int[n][2];
            StringTokenizer st;
            ovenTotalTime = new int[2];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                ovenTotalTime[0] += cookies[i][0] = Integer.parseInt(st.nextToken());
                ovenTotalTime[1] += cookies[i][1] = Integer.parseInt(st.nextToken());
            }
            sb.append(Math.min(findMinTime(0), findMinTime(1))).append("\n");
        }
        System.out.println(sb);
    }

    static int findMinTime(int fromOven) {      // 처음에 모든 일을 맡은 오븐은 fromOven
        // 쉽게 말해 가치가 cookies[i][fromOven]이고 제한이 ovenTotalTime[fromOven]+1인 가방문제와 같다.
        // 1000개의 쿠키가 각각 최대 100시간 총 10만시간이 걸리므로 모두 행을 쿠키로 모두 나타내면 메모리를 많이 잡아먹는다
        // 항상 이전 DP만 참고한다는 점을 고려하여 행을 2줄로 하여 서로 번갈아가면서 참고하도록 하면 된다.
        int[][] dp = new int[2][ovenTotalTime[fromOven] + 1];
        for (int i = 0; i < cookies.length; i++) {
            int pre = i % 2 == 0 ? 0 : 1;       // i가 짝수라면 이전이 0번, 아니라면 이전이 1번
            int cur = i % 2 == 0 ? 1 : 0;       // i가 짝수라면 이번은 1번, 아니라면 이번은 0번

            for (int j = 1; j < dp[cur].length; j++) {
                dp[cur][j] = Math.max(dp[cur][j - 1], dp[pre][j]);      // 이전 시간에 줄일 수 있는 시간과, 이전 쿠키 동일 시간에 줄일 수있는 시간 중 최대값을 가져온다

                int timeDiffCanBeReduced = cookies[i][0] + cookies[i][1];
                // 이번 쿠키가 줄일 수 있는 시간보다 현재 두 기계의 차이 시간이 더 크다면
                // 이번 쿠키를 다른 기계로 옮겼을 때, fromOven 에서 최대로 줄일 수 있는 시간이 늘어나는지 확인하고 갱신한다.
                if (j >= timeDiffCanBeReduced)
                    dp[cur][j] = Math.max(dp[cur][j], dp[pre][j - timeDiffCanBeReduced] + cookies[i][fromOven]);
            }
        }
        // 마지막 DP에 fromOven에서 최대로 줄일 수 있는 시간이 기록되어있다.
        // 이를 ovenTotalTime[fromOven]에서 빼주면 fromOven 이 모든 반죽를 맡은 상태에서 적절히 다른 오븐으로 반죽을 분배하여
        // 다른 오븐보단 더 많은 시간이 걸리지만 최대로 줄인 시간이 된다.
        return ovenTotalTime[fromOven] - dp[dp.length - 1][dp[dp.length - 1].length - 1];
    }
}