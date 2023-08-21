/*
 Author : Ruel
 Problem : Baekjoon 13258번 복권 + 은행
 Problem address : https://www.acmicpc.net/problem/13258
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13258_복권은행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 한 은행의 이자 지급 방식은
        // 주마다 각 사람들의 잔고 1원당 티켓을 하나씩 발행해,
        // 그 중 하나의 티켓에 j원을 추가시켜주는 방식이다.
        // n명의 사람에 대한 계좌가 주어진다면, c주가 지난 뒤
        // 첫번째 계좌에 남아있는 잔고의 기댓값은 얼마인가?
        //
        // DP 문제
        // 기댓값에 대한 문제이므로 현재 전체 계좌들의 잔액 합에서
        // 해당 계좌의 잔액 만큼의 비율로 j원을 입금 받을 확률이 생긴다.
        // 따라서 매 주, 기대값은 현재 계좌 잔액 + (현재 계좌의 잔액 / 전체 계좌의 잔액) * j 값이 된다.
        // 당연히 소수점 아래 자리가 생길 수 있으므로 double 형으로 선언후 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 각 계좌들의 잔액
        double[] accounts = Arrays.stream(br.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
        // 매주의 당첨금 j원
        int j = Integer.parseInt(br.readLine());
        // 기간 c주
        int c = Integer.parseInt(br.readLine());

        // c주 동안 반복한다.
        for (int i = 0; i < c; i++) {
            // 현재 전체 계좌의 잔액 합
            double totalSum = Arrays.stream(accounts).sum();
            // 각 계좌에 대해
            // 이번 주에, 해당하는 기댓값만큼을 더해준다.
            // 기댓값 = 현재 계좌의 잔액 / 전체 계좌의 잔액 * j
            for (int k = 0; k < accounts.length; k++)
                accounts[k] += accounts[k] / totalSum * j;
        }

        // c주 후 첫번째 계좌의 잔액을 출력한다.
        System.out.println(accounts[0]);
    }
}