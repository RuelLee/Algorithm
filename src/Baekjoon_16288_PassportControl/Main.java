/*
 Author : Ruel
 Problem : Baekjoon 16288번 Passport Control
 Problem address : https://www.acmicpc.net/problem/16288
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16288_PassportControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 여행객들이 k개의 창구에서 여권 심사를 받는다.
        // 여행객들은 1 ~ n 까지 순서대로 줄을 서 있다.
        // 하지만 창구에서 나오는 순서는 다를 수 있다.
        // 가령 3명이 2개의 창구를 통해 심사를 받는다면
        // 123, 132, 213, 231, 312 등의 순서는 가능하지만 321의 순서는 불가능하다.
        // n, k, 순서가 주어질 때 가능한 경우인지 판별하라
        //
        // 그리디문제
        // 만약 처음 나오는 사람이 n이라면 나머지 k - 1개의 창구에서
        // 1 ~ n - 1까지의 사람들이 모두 나와야한다.
        // n이 마지막 순서인데 가장 먼저 나왔다는 것은, 자신이 하나의 창구에서 처음으로 줄을 섰고, 가장 먼저 나왔다는 뜻이므로.
        // 따라서 k개의 창구들을 통과한 가장 마지막 여행객들을 표시하며, 다음 창구 이용자는 이전 여행객보다 무조건 큰 번호를 갖고 있어야한다.
        // 이전 창구 이용자보다 작은 번호는 무조건 다른 창구에서 처리되어야하므로.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 여행객들
        int[] passengers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // k개의 창구.
        int[] ports = new int[k];
        boolean possible = true;
        // 여행객들의 순서대로 처리한다.
        for (int i = 0; i < passengers.length; i++) {
            int idx = -1;
            // j개의 창구를 살펴보며
            // passengers[i] 보다 작은 번호를 갖지만 그 중 가장 큰 번호를 갖는 여행객이 이용한 창구를 찾는다.
            for (int j = 0; j < ports.length; j++) {
                if (ports[j] < passengers[i] &&
                        (idx == -1 || ports[idx] < ports[j]))
                    idx = j;
            }
            // 찾는 것이 실패했을 경우, 불가능한 경우이다.
            if (idx == -1) {
                possible = false;
                break;
            }
            // 찾는 것이 성공했다면 해당 창구로 i번째 여행자가 지나가면 된다.
            ports[idx] = passengers[i];
        }
        // 가불가 여부를 출력한다.
        System.out.println(possible ? "YES" : "NO");
    }
}