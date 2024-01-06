/*
 Author : Ruel
 Problem : Baekjoon 5550번 헌책방
 Problem address : https://www.acmicpc.net/problem/5550
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5550_헌책방;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 헌책방에서 책을 매입한다.
        // 책의 종류는 10종류가 존재하고, 같은 종류의 책을 한번에 많이 판매할수록 고가로 매입한다.
        // 같은 장르의 책을 T권 매입할 때, 책 한 권 당 매입 가격이 기준 가격보다 T-1원 높아진다.
        // 에를 들어 같은 종류의 100, 120, 150인 책을 한번에 판매한다면 102, 122, 152원으로 매입한다.
        // 전체 n권 중 k권을 팔려고할 때 얻을 수 있는 최대 금액은?
        //
        // DP, 누적합 문제
        // 각 카테고리의 책을 비싼 책을 우선적으로 팔수록 유리하다.
        // 따라서 카테고리 별로 i권을 팔 때의 가격을 누적합으로 구한다.
        // 그 후, DP[a][b]를 통해, a번째 카테고리까지 b권을 팔 때 가장 비싼 매입 가격으로 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 전체 n권의 책 중 k권을 판다.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 각 책을 우선순위큐를 통해 가격이 높은 순으로 판매한다.
        List<PriorityQueue<Integer>> books = new ArrayList<>();
        for (int i = 0; i < 11; i++)
            books.add(new PriorityQueue<>(Comparator.reverseOrder()));
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            books.get(g).add(c);
        }

        // 같은 종류의 책을 한번에 판매할 때, 얻을 수 있는 최대 누적합.
        int[][] psums = new int[11][];
        for (int i = 0; i < psums.length; i++) {
            psums[i] = new int[books.get(i).size() + 1];
            // 여태까지 판매한 책의 수.
            // j번째 누적합은
            // 1 ~ j-1번째 책들의 가격은 j-1번째 누적합에 비해 1원씩 높아지고
            // j번째 책의 가격은 원래 가격 + j-1원이 된다.
            for (int j = 1; j < psums[i].length; j++)
                psums[i][j] = psums[i][j - 1] + j * 2 - 2 + books.get(i).poll();
        }

        int[][] dp = new int[11][k + 1];
        // i번째 카테고리까지 고려했을 때
        for (int i = 1; i < dp.length; i++) {
            // 총 j권의 책을 팔 때의 최대 가격
            for (int j = 1; j < dp[i].length; j++) {
                // j권의 책을 j-1번째 카테고리까지 고려한 값과
                // j-1권을 책을 j번째 카테고리까지 고려한 값 중 더 큰 값을
                // dp[i][j]에 대한 초기값으로 설정한다.
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

                // j권의 책들 중 i번째 카테고리 책을 l권 판매했을 때의 값을 계산하여
                // 최대값을 갱신하는지 확인한다.
                for (int l = 1; l < Math.min(j + 1, psums[i].length); l++)
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - l] + psums[i][l]);
            }
        }
        System.out.println(dp[10][k]);
    }
}