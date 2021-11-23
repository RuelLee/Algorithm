/*
 Author : Ruel
 Problem : Baekjoon 2281번 데스노트
 Problem address : https://www.acmicpc.net/problem/2281
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 데스노트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] dp;
    static final int MAX = 1000 * 1000 * 1000;

    public static void main(String[] args) throws IOException {
        // 이름의 길이들과 한 줄에 적을 수 있는 글자수가 주어질 때 (이름끼리는 서로 공백으로 구분해야한다)
        // 마지막 줄을 제외한 나머지 줄들의 공백 길이의 제곱의 합이 최소가 되게 한다
        // 이 때 제곱의 합의 값을 구하라
        // 이름을 순서대로 연속적으로 적기 때문에 DP로 풀 수 있는 문제다
        // DP[i][j]를 i는 i번째 이름을 적을 순서를 뜻하고, j는 현재 행에서 남아있는 문자 수를 뜻한다고 하자
        // 이름을 적는 방법은 이름의 길이가 현재 남은 길이 + 1보다 같거나 작다면 현재 행에 적는 방법
        // 이름이 현재 남은 칸수보다 길거나 혹은 다음 줄에 적는 것이 제곱의 합에 더 유리하기 때문에 다음 줄에 적는 경우.
        // 두 가지를 DP로 풀면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        dp = new int[n + 1][m + 1];
        int[] nameLength = new int[n];      // 이름의 길이
        for (int i = 0; i < nameLength.length; i++)
            nameLength[i] = Integer.parseInt(br.readLine());

        for (int[] d : dp)      // MAX값으로 DP를 초기화시켜둔다.
            Arrays.fill(d, MAX);

        dp[0][m] = 0;       // 첫번째 이름을 적을 시작이 되는 위치. 비어있으므로 남은 칸수 m, 제곱들의 합 0
        for (int i = 0; i < nameLength.length; i++) {
            int Length = nameLength[i];         // 현재 선택된 이름의 길이

            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j] == MAX)        // 초기화되어있다면 현재까지 오는 방법이 없는 경우. 건너뛴다.
                    continue;

                if (j == m)     // 만약 j값이 m이라면 = 현재 행의 첫 이름을 적는 경우
                    dp[i + 1][j - Length] = dp[i][j];       // 이름만 적고 다음 이름으로 순서를 넘긴다.
                else if (j > Length)        // 남은 칸이 이름을 적기에 충분한 경우(첫번째 이름이 아닌 경우)
                    dp[i + 1][j - (Length + 1)] = dp[i][j];     // 공백을 고려하여 이름의 길이 + 1칸을 줄여주고 다음 이름으로 순서를 넘긴다.

                // 첫 이름이 아닌 경우( 무조건 행을 넘겨 다음 이름을 적는 것도 가능하다)
                // 다만 행이 고려되지 않은 DP이기 때문에 다른 행이지만 남은 칸수가 같아 중복된 위치가 참고될 수 있다. 따라서 제곱의 합이 더 작은 값만 남겨둔다.
                // 다음 행에 첫 이름으로 적히는 것이기 때문에 m - Length 위치에 현재 제곱의 합 + 현재 위치에서 남은 칸 수의 제곱을 더한 값을 다음 DP 위치의 값과 비교한다.
                if (j != m)
                    dp[i + 1][m - Length] = Math.min(dp[i + 1][m - Length], dp[i][j] + (int) Math.pow(j, 2));
            }
        }

        // 이제 마지막 줄을 제외한 남은 칸 수의 제곱의 합 중 최소값을 구할 차례다.
        // 이 값은 dp[n-1] 배열에 담겨있는 값들 중 최소값이다. 이 값을 구해 출력해주자.
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < dp[dp.length - 1].length; i++)
            min = Math.min(min, dp[dp.length - 1][i]);
        System.out.println(min);
    }
}