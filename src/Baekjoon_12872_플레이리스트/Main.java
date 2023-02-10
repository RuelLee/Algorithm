/*
 Author : Ruel
 Problem : Baekjoon 12872번 플레이리스트
 Problem address : https://www.acmicpc.net/problem/12872
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12872_플레이리스트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 음악이 있다.
        // 같은 음악은 두 곡 사이에 최소 m개의 음악이 들어있어야한다.
        // 모든 음악을 들으며, p개의 음악을 들으려할 때, 플레이리스트의 경우의 수를 구하여라
        // 값이 커질 수 있으므로, 1,000,000,007로 나눈 나머지를 출력한다.
        //
        // DP문제
        // 처음 떠올릴 때는 최근 들은 m개의 노래에 대해 겹치는 노래가 다음에 나와선 안되므로
        // 이를 비트마스킹을 통해 처리해야하나? 라는 생각을 했지만
        // n과 m이 최대 100으로 주어지므로, 이를 비트마스킹으로 해결하기엔 공간을 너무 차지한다.
        // 생각해보면 m개의 노래에 대해 각각을 생각할 필요는 없이 묶음으로 처리해도 된다.
        // dp[i][j]를 길이 i의 리스트에 대해, j개의 노래들이 선택되었다고 생각하자.
        // 여기서 i + 1의 길이를 만드는 방법은
        // 1. 새로운 노래를 마지막에 추가하는 방법이다. 이 때 새로운 노래는 n - j 개가 있다.
        // dp[i + 1][j + 1] += dp[i][j] * (n - j)
        // 2. 한 번 등장했던 노래를 다시 한번 선택하는 방법이 있다.
        // 이는 최근 등장했던 m개의 노래와는 중복되지 않는 노래를 선택하는 방법이 있다.
        // 이 때 선택할 수 있는 노래의 가짓수는 선택한 노래 j개에서 가장 최근에 등장한 m개를 제외한 값이다.
        // 플레이리스트의 길이는 늘어나지만 선택된 음악의 종류 수는 늘어나지 않는다.
        // dp[i + 1][j] += dp[i][j] * (j - m)
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 입력으로 주어지는 n, m, p
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        
        // 값이 커질 수 있으므로 long 타입으로 선언.
        // dp[i][j], i는 플레이리스트의 길이, j는 선택된 노래의 종류
        long[][] dp = new long[p + 1][n + 1];
        // 1개의 음악을 선택하는 방법은 전체 음악 종류만큼 있다.
        dp[1][1] = n;
        // 다음 것을 계산하므로, p - 1까지 계산
        for (int i = 1; i < dp.length - 1; i++) {
            // 선택한 노래의 종류 수를 모두 살펴본다.
            for (int j = 1; j < dp[i].length; j++) {
                // 값이 0이라면 건너뛴다.
                if (dp[i][j] == 0)
                    continue;
                
                // 아직 선택되지 않은 음악의 종류
                int notSelected = n - j;
                // 가 하나 이상 있다면
                // 새로운 노래를 추가하는 경우의 수를 계산한다.
                if (notSelected > 0) {
                    dp[i + 1][j + 1] += dp[i][j] * notSelected;
                    dp[i + 1][j + 1] %= LIMIT;
                }

                // 선택한 노래의 종류가 m개 이상이라면
                // 가장 최근에 등장한 m개의 노래를 제외한 나머지 노래들을 추가해도 된다.
                if (j > m) {
                    dp[i + 1][j] += dp[i][j] * (j - m);
                    dp[i + 1][j] %= LIMIT;
                }
            }
        }

        // 플레이리스트의 길이가 p이고,
        // 모든 노래를 선택한 경우의 수인 dp[p][n]을 출력한다.
        System.out.println(dp[p][n]);
    }
}