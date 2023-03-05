/*
 Author : Ruel
 Problem : Baekjoon 17208번 카우버거 알바생
 Problem address : https://www.acmicpc.net/problem/17208
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17208_카우버거알바생;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m개의 치즈 버거, k개의 감자 튀김이 있다.
        // 이 때 n개의 주문이 들어왔고, 주문은 치즈버거와 감자 튀김으로만 이루어져있다.
        // 남아있는 치즈 버거와 감자 튀김으로 최대 몇 개의 주문을 처리할 수 있는가?
        //
        // DP로 푸는 배낭 문제
        // 치즈 버거와 감자 튀김, 변수가 2개이므로 3차원 배열 내지 역순으로 계산한다면 2차원 배열로 처리할 수 있다.
        // 3차원 배열일 경우에는 dp[주문][치즈버거][감자튀김]이고, 2차원 배열일 경우 dp[치즈버거][감자튀김]으로 정의한다
        // 3차원 배열의 경우에는 주문 별로 위치가 구분이 되기 때문에, 이번 주문과 비교하여 잔여 재고량에서 현재 주문이 가능한지 여부를 살펴보며 채우고
        // 2차원 배열에서는 현재 위치에서 이전 주문만 처리된 것인지, 현재 주문도 반영된 것인지 구분이 되지 않기 때문에
        // 큰 값에서부터 줄여나가면서 이전 주문에서의 값만 참고하여 현재 주문에 반영한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 주문, 치즈 버거, 감자 튀김의 개수
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각각의 주문
        int[][] orders = new int[n][];
        for (int i = 0; i < orders.length; i++)
            orders[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 2차원 배열을 통해 DP로 해결한다
        // dp[치즈버거][감자튀김]
        int[][] dp = new int[m + 1][k + 1];
        // 각각의 주문에 대해
        for (int[] order : orders) {
            // 치즈버거는 m개부터 현재 주문을 처리할 수 있는 잔여량까지
            for (int i = dp.length - 1; i - order[0] >= 0; i--) {
                // 감자 튀김도 마찬가지로 k개부터 현재 주문을 처리할 수 있는 잔여량까지
                // i개의 치즈 버거, j개의 감자 튀김으로 처리할 수 있는 최대 주문량은
                // 현재 기록되어있는 dp[i][j] 값과
                // 현재 주문을 반영하기 전인 i - order[0]개의 치즈 버거, j - order[1] 개의 감자 튀김을 사용해서 처리한 최대 주문량
                // 에 현재 주문을 +1 한 값과 비교하여 더 큰 값을 기록한다.
                for (int j = dp[i].length - 1; j - order[1] >= 0; j--)
                    dp[i][j] = Math.max(dp[i][j], dp[i - order[0]][j - order[1]] + 1);
            }
        }

        // m개의 치즈 버거, k개의 감자 튀김으로 처리할 수 있는 최대 주문의 개수를 출력한다.
        System.out.println(dp[m][k]);
    }
}