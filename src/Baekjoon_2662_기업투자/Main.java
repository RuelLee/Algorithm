/*
 Author : Ruel
 Problem : Baekjoon 2662번 기업투자
 Problem address : https://www.acmicpc.net/problem/2662
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2662_기업투자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 기업에 투자할 수 있는 돈 n, 투자 가능 기업 m이 주어진다
        // 각 기업마다 1 ~ n까지 투자했을 때의 얻을 수 있는 이익들이 각각 주어진다.
        // n을 투자하여 얻을 수 있는 최대 이익은?
        //
        // DP문제!
        // 문제를 해석하는데 시간이 더 오래걸렸다
        // 만약 A라는 기업에 3을 투자할 때 1 + 1 + 1 or 1 + 2 이런 식으로 투자할 수 있는 것이 아니라
        // 무조건 3에 해당하는 이익이 돌아온다.
        // 적은 금액을 중복해서 투자할 수 없으므로, DP 계산 순서를 작은 값에서 시작하는 것이 아니라
        // 큰 값에서부터 줄여나가야한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 금액을 기업에 투자했을 때의 이익.
        int[][] invest = new int[n + 1][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int money = Integer.parseInt(st.nextToken());
            for (int j = 0; j < invest[i].length; j++)
                invest[money][j] = Integer.parseInt(st.nextToken());
        }

        // 금액을 투자했을 때, 각 기업에 투자한 금액들
        int[][] dp = new int[n + 1][m];
        // 그 때의 최대 이익.
        int[] maxReturn = new int[n + 1];

        // 한 기업씩 살펴본다.
        for (int company = 0; company < m; company++) {
            // 전체 투자 금액은 n부터 1까지 내림차순으로 내려간다
            // 분할하여 중복 투자가 불가능하므로.
            for (int totalInvestMoney = n; totalInvestMoney > 0; totalInvestMoney--) {
                // 이번에 계산할 투자 금액
                for (int currentInvestMoney = 1; currentInvestMoney < invest.length; currentInvestMoney++) {
                    // 전체 투자 금액보다 현재 투자 금액이 큰 경우는 없으므로 중단.
                    if (totalInvestMoney < currentInvestMoney)
                        break;
                    
                    // 현재 계산되어있는 totalInvestMoney에 해당하는 이익보다
                    // currentInvestMoney만큼을 대신 company에 투자했을 때의 이익이 더 크다면
                    if (maxReturn[totalInvestMoney] < maxReturn[totalInvestMoney - currentInvestMoney] + invest[currentInvestMoney][company]) {
                        // 최대 이익 갱신
                        maxReturn[totalInvestMoney] = maxReturn[totalInvestMoney - currentInvestMoney] + invest[currentInvestMoney][company];
                        // totalInvestMoney - currentInvestMoney 금액에 해당하는 투자 금액들을 가져오고,
                        for (int i = 0; i < m; i++)
                            dp[totalInvestMoney][i] = dp[totalInvestMoney - currentInvestMoney][i];
                        // currentInvestMoney 만큼을 company에 투자한 걸로 더해준다.
                        dp[totalInvestMoney][company] += currentInvestMoney;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // n만큼 투자했을 때의 최대 이익
        sb.append(maxReturn[n]).append("\n");
        // 그 때의 각각 기업에 투자한 금액
        for (int i = 0; i < dp[n].length; i++)
            sb.append(dp[n][i]).append(" ");
        System.out.println(sb);
    }
}