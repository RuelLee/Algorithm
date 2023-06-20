/*
 Author : Ruel
 Problem : Baekjoon 9472번 알고리즘 기말고사
 Problem address : https://www.acmicpc.net/problem/9472
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9472_알고리즘기말고사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 기말고사에는 n개의 용어와 n개의 정의를 서로 연결하는 문제가 나온다고 한다.
        // S(N,k)를 위에서부터 k개의 문제는 반드시는 틀리는 경우의 수라고 할 때, 그 값을 출력하라
        //
        // DP, 비트마스킹, 조합 문제
        // 위에서 k개의 문제 대해서는 반드시 문제 번호와 일치하지 않는 답을 연결해야한다.
        // 따라서 비트마스킹을 활용하여 DP를 계산해나간다.
        // k개 이후의 나머지 문제들에 대해서는 정답이건 아니건 상관이 없기 때문에
        // (n - k)! 를 곱해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        // 테스트케이스
        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            st.nextToken();
            // 문제의 수
            int n = Integer.parseInt(st.nextToken());
            // 틀려야하만 하는 k개의 문제
            int k = Integer.parseInt(st.nextToken());

            // DP
            // k개의 문제만 반드시 틀리면 되므로, k개만 DP를 세운다.
            long[][] dp = new long[k + 1][1 << n];
            // 초기값
            dp[0][0] = 1;
            // i번 문제
            for (int i = 0; i < dp.length - 1; i++) {
                // 비트마스킹 값 j
                for (int j = 0; j < dp[i].length; j++) {
                    // 만약 해당 경우의 수가 없는 경우에는 건너뛴다.
                    if (dp[i][j] == 0)
                        continue;

                    // l번 답
                    for (int l = 0; l < n; l++) {
                        // 만약 i == ㅣ이 같은 경우에는 정답이 되므로 건너뛴다.
                        if (i + 1 == l + 1)
                            continue;

                        // 비트마스킹을 확인해 l번 답을 아직 선택하지 않았다면
                        // i번 문제와 l번 답을 서로 연결하는 경우의 수를 계산한다.
                        if ((j & (1 << l)) == 0)
                            dp[i + 1][j | (1 << l)] += dp[i][j];
                    }
                }
            }

            long answer = Arrays.stream(dp[k]).sum();
            // n개의 문제 중 k번째 문제까지 모두 틀리는 경우의 수를 구했다.
            // 이제 나머지 (n - k) 개의 문제에 대해서는 맞건 틀리건 상관이 없으므로
            // (n - k)! 를 곱해준다.
            for (int i = n - k; i > 0; i--)
                answer *= i;
            
            // 답안 기록
            sb.append(t + 1).append(" ").append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}