/*
 Author : Ruel
 Problem : Baekjoon 13302번 리조트
 Problem address : https://www.acmicpc.net/problem/13302
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13302_리조트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static int[] days = {1, 3, 5};
    static int[] tickets = {10000, 25000, 37000};
    static int[] coupons = {0, 1, 2};

    public static void main(String[] args) throws IOException {
        // n일 중에 리조트에 방문할 수 없는 m개의 날짜가 주어진다.
        // 1일 이용권은 1만원에 쿠폰 0
        // 3일 이용권은 2.5만원에 쿠폰 1장
        // 5일 이용권은 3.7만원에 쿠폰 2장을 주며, 쿠폰 3장으로 1일을 무료로 이용할 수 있다고 한다.
        // m일을 제외한 모든 날에 리조트를 최소 비용으로 이용하려할 떄, 비용은?
        //
        // DP문제
        // 각 날짜에 대해 1일권, 3일권 5일권을 사용했을 때의 최소비용을 DP를 사용해서 계산해나가보자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 리조트를 이용할 수 없는 m개의 일
        HashSet<Integer> hashSet = new HashSet<>();
        if (m > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < m; i++)
                hashSet.add(Integer.parseInt(st.nextToken()));
        }

        // minCosts[i][j] = i일까지 j장의 쿠폰을 갖고 있으면서 리조트를 이용하는 최소 비용.
        int[][] minCosts = new int[n + 1][(int) Math.ceil((double) n / 5) * 2 + 1];
        for (int[] mc : minCosts)
            Arrays.fill(mc, Integer.MAX_VALUE);
        // 초기값.
        minCosts[0][0] = 0;

        // 모든 일에 대해
        for (int i = 0; i < minCosts.length - 1; i++) {
            // 모든 쿠폰 장수에 대해 계산한다.
            for (int j = 0; j < minCosts[i].length; j++) {
                // 만약 초기값이라면 불가능한 경우. 건너뛴다.
                if (minCosts[i][j] == Integer.MAX_VALUE)
                    continue;
                // 만약 i + 1일이 방문하지 못하는 날이라면 계산하지 않고,
                // i일 j쿠폰에 비용과 비교하여 더 적은 값을 i + 1에 계산해둔다.
                else if (hashSet.contains(i + 1)) {
                    minCosts[i + 1][j] = Math.min(minCosts[i + 1][j], minCosts[i][j]);
                    continue;
                }

                // 1일권, 3일권, 5일권을 구매하는 경우.
                for (int d = 0; d < days.length; d++) {
                    // 만약 이용권이 전체 일수를 초과하는 경우, 쿠폰 또한 버리는 경우이므로
                    // 1일권보다 유리할 수가 없다. 해당 경우는 반복문을 끝낸다.
                    if (i + days[d] >= minCosts.length)
                        break;
                    // d번 이용권을 이용하는 것이 기존에 계산됐던 결과보다
                    // 더 적은 비용이 소모될 경우 해당 값을 기록해준다.
                    else if (minCosts[i + days[d]][j + coupons[d]] > minCosts[i][j] + tickets[d])
                        minCosts[i + days[d]][j + coupons[d]] = minCosts[i][j] + tickets[d];
                }

                // 쿠폰이 3장 이상 있어, 사용할 수 있는 경우.
                if (j >= 3 && minCosts[i + 1][j - 3] > minCosts[i][j])
                    minCosts[i + 1][j - 3] = minCosts[i][j];
            }
        }

        // 최종적으로 n일에 남은 쿠폰 수에 상관 없이 가장 적은 비용을 출력한다.
        System.out.println(Arrays.stream(minCosts[n]).min().getAsInt());
    }
}