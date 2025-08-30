/*
 Author : Ruel
 Problem : Baekjoon 1532번 동전 교환
 Problem address : https://www.acmicpc.net/problem/1532
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1532_동전교환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 현재 금화 g1, 은화 s1, 동화 b1개를 갖고 있는데
        // 금화 g2, 은화 s2, 동화 b2개가 필요하다.
        // 각 동전들은
        // 금화 -> 은화 * 9, 은화 11 -> 금화 1
        // 은화 -> 동화 * 9, 동화 11 -> 은화 1 로 교환할 수 있다고 한다.
        // 최소 몇 번의 교환을 통해서 원하는 개수의 동전 수를 맞출 수 있는가?
        // 불가능하다면 -1을 출력한다.
        //
        // 그리디 문제
        // 금화 -> 은화 -> 동화 혹은 동화 -> 은화 -> 금화 순으로 부족한 개수를 교환하다보면
        // 은화가 부족하여 금화 -> 동화 혹은 동화 -> 금화로 변환해야하는 경우가 생겨서 귀찮다.
        // 금화 -> 동화 -> 은화 혹은 동화 -> 금화 -> 은화 순으로 은화를 마지막으로 두면
        // 은화는 금화, 동화 양측으로 변환이 가능하기 때문에 편하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 현재 동전의 개수와 필요한 개수
        int[][] coins = new int[2][3];
        StringTokenizer st;
        for (int i = 0; i < coins.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < coins[i].length; j++)
                coins[i][j] = Integer.parseInt(st.nextToken());
        }

        int count = 0;
        // 금화가 부족한 경우
        // 필요한 만큼을 은화에서 교환한다.
        if (coins[0][0] < coins[1][0]) {
            int need = coins[1][0] - coins[0][0];
            count += need;
            coins[0][0] += need;
            coins[0][1] -= need * 11;
        }

        // 동화가 부족한 경우
        // 마찬가지로 부족한 만큼 은화에서 교환한다.
        if (coins[0][2] < coins[1][2]) {
            int need = coins[1][2] - coins[0][2];
            int exchange = (need + 8) / 9;
            count += exchange;
            coins[0][1] -= exchange;
            coins[0][2] += exchange * 9;
        }

        // 마지막으로 은화를 살펴본다.
        if (coins[0][1] < coins[1][1]) {
            // 부족한 양
            int need = coins[1][1] - coins[0][1];
            // 금화에 여분이 있다면
            // 가능한 만큼 교환
            if (coins[0][0] > coins[1][0]) {
                int exchange = Math.min((need + 8) / 9, coins[0][0] - coins[1][0]);
                count += exchange;
                coins[0][0] -= exchange;
                coins[0][1] += exchange * 9;
                need -= exchange * 9;
            }

            // 그래도 부족한데, 동화에 여분이 있다면
            // 가능한 만큼 교환
            if (need > 0 && coins[0][2] > coins[1][2]) {
                int exchange = Math.min(need, (coins[0][2] - coins[1][2]) / 11);
                count += exchange;
                coins[0][1] += exchange;
                coins[0][2] -= exchange * 11;

            }
        }
        // 최종적으로 은화의 개수가 필요한 개수보다 많은지만 살펴보면 된다.
        System.out.println(coins[0][1] >= coins[1][1] ? count : -1);
    }
}