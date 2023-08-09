/*
 Author : Ruel
 Problem : Baekjoon 14628번 입 챌린저
 Problem address : https://www.acmicpc.net/problem/14628
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14628_입챌린저;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 스킬이 주어진다.
        // 각 스킬은 소모 마나 사용량과 상대방의 체력을 깎는 수치가 주어진다.
        // 같은 스킬을 한 게임에서 여러번 사용하면, 원래 마나 사용량에서 +k 만큼씩 추가된다.
        // 상대방의 체력이 m일 때, 정확히 체력을 0으로 만들며, 마나 사용량을 최소화하고자 할 때
        // 마나 소모량은?
        //
        // DP 문제
        // 스킬을 중복 사용할 때마다 마나 소모량이 추가되므로
        // 적 체력에 따라 1회부터 ~ 최대 사용횟수까지 마나 소모량을 스킬 별로 모두 구한다.
        // 그리고 해당 체력을 깎는데 소모하는 최소 마나 소모량을 DP를 사용하여 차근차근 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 스킬의 개수 n, 적의 체력 m, 추가 마나 소모량 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // dp만큼의 체력을 깎는데 소모되는 최소 마나 소모량
        int[] dp = new int[m + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 0의 초기값은 0
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            // 이번 스킬
            int[] skill = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 해당 스킬을 1회 ~ 최대 사용 횟수까지 각 소모량을 모두 구한다.
            List<Integer> costs = new LinkedList<>();
            costs.add(0);
            for (int j = 1; j * skill[1] <= m; j++)
                costs.add(costs.get(j - 1) + skill[0] + (j - 1) * k);

            // j만큼의 체력을 깎는데 소모되는 최소 마나를 구한다.
            for (int j = dp.length - 1; j > 0; j--) {
                // j이하의 체력을 깎아야하므로 j / 마나 소모량 이하로 스킬을 사용 가능하다.
                for (int l = 1; l * skill[1] <= j; l++) {
                    // j - 데미지 * l 만큼의 누적 데미지에서
                    // 스킬을 l회 사용한다면 총 누적 데미지가 j가 된다.
                    // 따라서 dp[j - l * skill[1]] 이 초기값이 아니고
                    // 해당 비용 + 스킬 l회 사용 소모량이 최저 소모량을 갱신하는지 확인한다.
                    if (dp[j - l * skill[1]] != Integer.MAX_VALUE)
                        dp[j] = Math.min(dp[j], dp[j] = dp[j - l * skill[1]] + costs.get(l));
                }
            }
        }
        // 항상 스킬들은 체력을 정확히 0으로 만들 수 있다 했으므로
        // dp[m]을 출력한다.
        System.out.println(dp[m]);
    }
}