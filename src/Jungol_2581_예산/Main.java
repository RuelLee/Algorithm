/*
 Author : Ruel
 Problem : Jungol 2581번 예산
 Problem address : https://jungol.co.kr/problem/2581
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2581_예산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] budgets, psums;

    public static void main(String[] args) throws IOException {
        // n개의 예산 사용처와 금액이 주어진다.
        // 총 예산의 합이 m을 넘어서는 안된다.
        // 한 사용처 당 최대 사용 금액을 정해두고, 그 이하인 곳은 해당 예산을
        // 그 이상인 곳에는 제한 금액만큼을 지금할 때
        // 단일 사용처 중 최대 예산을 구하라
        //
        // 이분 탐색, 누적합 문제
        // 두 개의 이분탐색을 사용한다.
        // 먼저 제한 금액을 1 ~ 한 사용처의 최대 예산 범위 내에서 이분 탐색하여, 총합이 m이내인 최대 제한 금액을 구한다.
        // 이 때, 각 사용처의 예산을 이분탐색하여 제한 금액이하인 사용처의 idx를 구한다
        // 이를 통해 제한 금액 이하인 곳은 누적합을 통해 예산합을 구하고, 초과인 곳은 제한 금액 * 개수로 구한다.
        // 두 합이 m이하인 최대 금액을 찾고, 그 때의 한 사용처의 최대 사용 금액을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 예산 사용처
        n = Integer.parseInt(br.readLine());

        // 예산
        budgets = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            budgets[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(budgets);

        // 누적합
        psums = new int[n + 1];
        for (int i = 1; i <= n; i++)
            psums[i] = psums[i - 1] + budgets[i];

        m = Integer.parseInt(br.readLine());

        // 제한 금액을 1 ~ 단일 최대 예산의 범위 내에서 찾는다.
        int start = 1;
        int end = budgets[n];
        while (start <= end) {
            int mid = (start + end) / 2;
            // 제한 금액이 mid로 했을 때, 전체 예산이 m이하라면
            // start의 범위를 mid + 1로 축소
            if (possible(mid))
                start = mid + 1;
            else        // 불가능할 경우 end를 mid - 1로 축소
                end = mid - 1;
        }
        // 제한 금액이 단일 예산의 최대 사용 금액
        // 해당 값을 출력
        System.out.println(end);
    }

    // 제한 금액이 max일 때, 전체 예산이 m이하인지 확인
    static boolean possible(int max) {
        int idx = findIdx(max);
        int sum = psums[idx] + (n - idx) * max;
        return sum <= m;
    }

    // value 이하인 최대 최대 예산 사용처의 번호를 찾는다.
    static int findIdx(int value) {
        // 1 ~ n 범위에서
        int start = 1;
        int end = n;
        // 이분 탐색으로 해당 idx를 찾는다.
        while (start <= end) {
            int mid = (start + end) / 2;
            if (budgets[mid] >= value)
                end = mid - 1;
            else
                start = mid + 1;
        }
        return end;
    }
}