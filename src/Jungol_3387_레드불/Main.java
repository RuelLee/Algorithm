/*
 Author : Ruel
 Problem : Jungol 3387번 레드불
 Problem address : https://jungol.co.kr/problem/3387
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3387_레드불;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 0부터 n위치를 걷거나 날아서 가려고 한다.
        // 걷기 : 고도가 1이상일 경우, 0까지 떨어진 후, 옆으로 걸어서 이동한다.
        // 날기 : 고도를 1 상승시키며 옆으로 이동한다.
        // 최대 고도를 k 미만으로 유지하며 n인 위치에 고도 0으로 도달하고자 한다.
        // 가능한 경우의 수를 1_000_000_007로 나눈 나머지 값으로 출력하라
        //
        // 누적합 문제
        // dp[i] = i 위치에 고도 0인 상태로 도달하는 경우의 수라고 정의 하자
        // i에 고도 0으로 도달하는 경우들은
        // i-1에서 걸어오는 경우
        // i - k ~ i - 1까지 날고, i-1에서 떨어져서 걷는 경우
        // i - k + 1 ~ i - 1
        // ..
        // dp[i] = dp[i-1] + (dp[i-2] + ... + dp[i - k])
        // 구간의 합을 많이 사용하므로 누적합 처리하여 계산하자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 0 ~ n까지의 위치, 최대 고도 k 미만
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 누적합 처리
        long[] psums = new long[n + 1];
        // 0 위치인 경우의 수 1
        psums[0] = 1;
        // psums[i]는
        // psums[i-1]의 값 + i에 0 고도로 도달하는 경우의 수
        // i에 0 고도로 도달하는 경우의 수 = (psums[i-1] - psums[i-k-1])
        // 나머지값으로 처리하기 때문에, - 연산의 경우 음수가 될 수 있다는 점을 고려해, LIMIT을 더해주고 mod LIMIT을 한다.
        for (int i = 1; i <= n; i++)
            psums[i] = (psums[i - 1] + psums[i - 1] + LIMIT - (i - k - 1 < 0 ? 0 : psums[i - k - 1])) % LIMIT;
        // 0 고도로 도달하는 경우의 수만 구하므로
        // 해당값을 계산하여 출력
        System.out.println((psums[n] - psums[n - 1] + LIMIT) % LIMIT);
    }
}