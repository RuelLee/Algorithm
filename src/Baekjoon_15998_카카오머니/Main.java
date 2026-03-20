/*
 Author : Ruel
 Problem : Baekjoon 15998번 카카오머니
 Problem address : https://www.acmicpc.net/problem/15998
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15998_카카오머니;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 원하는 만큼의 현금을 충전하고 사용할 수 있는 카카오머니라는 서비스가 있다.
        // 충전할 때는 해당 금액만큼 잔액에 충전되고
        // 사용할 때는 부족 금액 만큼을 연결된 계좌에서 m원씩 출금한다.
        // 현금이 무한한 계좌에 카카오머니 서비스를 연결했다고 할 때
        // n개의 입출금 기록이 주어진다.
        // a b로 주어지며, a가 양수인 경우는 입금, 음수인 경우는 출금, 0인 경우는 주어지지 않는다.
        // b는 남은 잔액을 뜻한다.
        // 만족하는 m이 존재한다면 그 중 아무거나를
        // 존재하지 않는다면 -1을 출력한다.
        //
        // 유클리드 호제법
        // 먼저, 입금이 이루어질 때, 잔액이 맞는지 확인해야한다.
        // 출금이 이루어질 때, 잔액이 출금액보다 많은 경우, 잔액만 확인한다.
        // 잔액보다 출금액이 많은 경우, m원씩 계좌에서 출금을 한다.
        // 이 때, 현재 잔액과 이전 잔액을 그리고 출금액을 비교하여, 충전된 금액을 계산한다.
        // 충전된 금액의 약수들이 m의 후보이다.
        // 하나 더, m은 현재 잔액보다 작을 수 없다는 점을 유의하여 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;

        // 입출금 내역
        long[][] balances = new long[n + 1][2];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            balances[i][0] = Long.parseLong(st.nextToken());
            balances[i][1] = Long.parseLong(st.nextToken());
        }

        // 잔액보다 출금액이 큰 경우가 한 번도 존재하지 않는다면
        // m은 어떠한 값도 가능하다.
        // 그 때를 대비하여 1을 처음 넣어둔다.
        long gcd = 1;
        // m은 출금액이 잔액보다 많았을 때, 충전이 이루어진 후의 잔액보다 커야한다.
        long biggerThan = 0;
        // 처음으로 잔액보다 큰 출금액이 등장한 경우를 판별한다.
        boolean isFirst = true;
        for (int i = 1; i < balances.length; i++) {
            // 입금이거나, 잔액이 출금액보다 같거나 많은 경우는 잔액 값 비교
            if (balances[i][0] > 0 || balances[i - 1][1] + balances[i][0] >= 0) {
                if (balances[i - 1][1] + balances[i][0] != balances[i][1]) {
                    gcd = -1;
                    break;
                }
            }

            // 출금액이 잔액보다 큰 경우
            if (balances[i - 1][1] + balances[i][0] < 0) {
                // 충전 후, 잔액보다 m은 커야하며
                biggerThan = Math.max(biggerThan, balances[i][1]);

                // 충전액 계산
                long plus = balances[i][1] - balances[i - 1][1] - balances[i][0];
                // 처음으로 출금액이 잔액보다 큰 경우는
                // 해당 값을 그대로 gcd에 대입.
                if (isFirst) {
                    gcd = plus;
                    isFirst = false;
                }
                else        // 그 외의 경우는 gcd와 충전액의 최대공약수를 찾는다.
                    gcd = getGCD(gcd, plus);
            }
        }

        // 최종적으로 gcd는 biggerThan보다 큰 값을 갖고 있는 경우 그대로 값 출력
        // 만약 출금액이 잔액보다 큰 경우가 한번도 없었다면 초기값 1을 그대로 갖고 있을 테고
        // 그 값을 출력한다.
        if (gcd > biggerThan)
            System.out.println(gcd);
        else        // 그 외의 경우 -1을 출력
            System.out.println(-1);
    }

    // a와 b의 최대공약수를 계산한다.
    static long getGCD(long a, long b) {
        long max = Math.max(a, b);
        long min = Math.min(a, b);

        while (min > 0) {
            long temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}