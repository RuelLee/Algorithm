/*
 Author : Ruel
 Problem : Baekjoon 2248번 이진수 찾기
 Problem address : https://www.acmicpc.net/problem/2248
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2248_이진수찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n(최대 31)자리의 이진수 중 L개 이하의 비트가 1인 것을 크기 순으로 나열했을 때,
        // I번째 이진수를 구하는 프로그램을 작성하시오.
        //
        // DP문제
        // dp를 통해 dp[i][j] = i자리의 이진수 중 j개 이하의 비트가 1인 수의 개수
        // 로 정하고 값을 채워나간다
        // dp[i][j]는 i - 1자리의 이진수 중 j개가 1인 것(0 + j개의 1)과
        // i - 1자리의 이진수 중 j - 1개가 1이고, 앞에 1을 붙이는 것(1 + (j - 1)개의 1)의 개수와 같다.
        // 그 후, DP를 통해 이진수를 직접 만들어간다.
        // 만약 i번째 비트가 1인지 0인지 판별하는 방법은
        // (i - 1)개의 비트로 만들 수 있는 수의 개수가 I보다 크다면 0,
        // (i - 1)개의 브트로 만들 수 있는 수의 개수가 I보다 작다면 1을 붙인 후, I에서 그 개수만큼을 빼준다.
        // 이런 식으로 각 자리마다 1인지 0인지를 판별하며 수를 만들어 나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        // 최대 31개의 비트인데, 0이 1번째로 들어가므로 최대 2^31 + 1번째 수가 올 수 있다.
        // 이는 int 범위를 벗어나므로 long으로 받아주자.
        long I = Long.parseLong(st.nextToken());

        // i개의 비트 중 j개가 1인 경우의 수를 셀 DP.
        long[][] dp = new long[N][L + 1];
        for (int i = 0; i < dp.length; i++)
            dp[i][0] = 1;

        for (int i = 1; i < dp.length; i++) {
            // i개의 비트이므로 1이 i개를 넘을 수 없고, 문제 주어지는 제한이 L도 넘을 수 없다.
            int limit = Math.min(i + 1, dp[i].length);
            // i - 1개의 비트에서 j개가 1인 경우 + i - 1개의 비트에서 j - 1개가 1인 경우.
            for (int j = 1; j < limit; j++)
                dp[i][j] = dp[i - 1][j] + dp[i - 1][j - 1];
        }

        // 수를 만들어 간다.
        StringBuilder sb = new StringBuilder();
        // 사용한 1의 개수만큼 다음 자리의 경우의 수가 줄어든다.
        // 따라서 남은 사용할 수 있는 1의 개수를 지속적으로 고려해야한다.
        int remainOne = L;
        // N자리의 비트이므로, N-1개의 비트로 만들 수 있는 이진수의 개수부터 체크해나간다.
        for (int i = N - 1; i >= 0; i--) {
            int sum = 0;
            // i자리에서 remainOne의 1로 만들 수 있는 이진수 경우의 수.
            for (int j = 0; j <= remainOne; j++)
                sum += dp[i][j];

            // 만약 I가 sum보다 클 경우,
            if (sum < I) {
                // 1을 붙이고
                sb.append(1);
                // I에서 sum만큼을 빼준다.
                I -= sum;
                // 1의 개수도 하나 줄여준다.
                remainOne--;
            } else {
                // 그렇지 않을 경우
                // 0을 붙이고,
                // I, remainOne을 유지한다.
                sb.append(0);
            }
        }
        // 완성된 수를 출력한다.
        System.out.println(sb);
    }
}