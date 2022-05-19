/*
 Author : Ruel
 Problem : Baekjoon 15824번 너 봄에는 캡사이신이 맛있단다
 Problem address : https://www.acmicpc.net/problem/15824
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15824_너봄에는캡사이신이맛있단다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int MAX = 1_000_000_007;
    static int[] pow;

    public static void main(String[] args) throws IOException {
        // n개의 메뉴가 주어지고, 각 메뉴의 스코빌 지수가 주어진다
        // 그리고 메뉴들을 조합(2메뉴 이상)으로 먹었을 때, 가장 낮은 스코빌 지수와, 가장 높은 스코빌 지수의 차이만큼 매운 맛을 느낀다고 한다.
        // 메뉴들에 모든 조합에 대하여 느낄 수 있는 매운 맛의 합은 얼마인가
        //
        // 생각보다 재밌는 문제
        // 모든 조합을 구해서 그 때의 매운맛을 더하려면 2^n만큼의 연산이 필요하다
        // n이 30만까지 주어지는 경우에는 당연히 불가능.
        // 각 메뉴에 촛점을 맞춰, 현재 메뉴가 조합에서 가장 낮은 스코빌 지수가 될 경우의 수와
        // 가장 높은 스코빌 지수가 되는 경우를 따져본다
        // 2 5 8 세개의 메뉴가 주어진다면
        // 2가 가장 낮은 메뉴가 되는 경우 -> 3가지, 가장 높은 메뉴 되는 경우 -> 0가지
        // 5가 가장 낮은 메뉴가 되는 경우 -> 1가지, 가장 높은 메뉴가 되는 경우 -> 1가지
        // 8이 가장 낮은 메뉴가 되는 경우 -> 0가지, 가장 높은 메뉴가 되는 경우 -> 3가지
        // 메뉴들을 정렬을 한 뒤,
        // i번째 메뉴가 매운 맛이 가장 낮은 메뉴로 조합에 들어가는 경우는 i번 이후의 메뉴들과 조합을 짜는 경우이고
        // i번째 메뉴가 매운 맛이 가장 높은 메뉴로 조합에 들어가는 경우는 i번 이전의 메뉴들과 조합을 짜는 경우이다.
        // 위와 같이 계산하면 선형적인 계산이 가능하다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] dishes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 음식이 최대 30만까지 주어지므로 그 때의 제곱값들을 미리 계산하여 모듈러 연산해두자.
        pow = new int[n];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = (int) (((long) pow[i - 1] * 2) % MAX);

        // 음식들을 정렬
        Arrays.sort(dishes);
        long sum = 0;
        for (int i = 0; i < dishes.length; i++) {
            // i번째 메뉴가 가장 높은 스코빌 지수로 조합에 포함되는 경우.
            // i개의 메뉴들 중 최소 한 개 이상 포함되는 경우를 계산한다.
            sum += ((long) dishes[i] * (pow[i] - 1)) % MAX;
            sum %= MAX;

            // i번째 메뉴가 가장 낮은 스코빌 지수로 조합에 포함되는 경우.
            // n - i - 1개의 메뉴들 중 최소 한 개이상 포함되는 조합을 계산한다.
            sum -= ((long) dishes[i] * (pow[n - i - 1] - 1)) % MAX;
            sum = (sum + MAX) % MAX;

        }
        System.out.println(sum);
    }
}