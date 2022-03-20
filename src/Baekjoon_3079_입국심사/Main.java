/*
 Author : Ruel
 Problem : Baekjoon 3079 입국심사
 Problem address : https://www.acmicpc.net/problem/3079
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3079_입국심사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 입국 심사대와 m명의 사람이 주어진다
        // 그리고 각 입국 심사대의 소요시간이 주어질 때, m명이 모두 입국 심사대를 통과하는 최소 시간을 구하라
        // 시뮬레이션으로 풀려고 하면 연산이 너무 많아 불가능하다.
        // 특정 시간 동안 입국 심사대를 통과하는 사람의 수는 한번의 나눗셈으로 구할 수 있다.
        // 따라서 이분 탐색을 통해 특정 시간 동안 통과할 수 있는 사람의 수를 구해, 이 수가 m보다 크거나 같은 최소 시간을 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] gates = new int[n];
        for (int i = 0; i < gates.length; i++)
            gates[i] = Integer.parseInt(br.readLine());

        long start = 0;
        // 최대 m은 10억, 입국 심사대의 최대 소요시간도 10억이므로, 최악의 경우, 10억 * 10억의 시간이 나올 수도 있다.
        long end = 1_000_000_000L * 1_000_000_000L;
        while (start < end) {
            long mid = (start + end) / 2;
            long sum = 0;
            for (int gate : gates) {
                // 테이스트 케이스로 알아낸 경우.. sum을 구하면 특정 시간에 통과하는 최대 인원이 나오는데
                // 이 인원이 long의 범위도 벗어날 수 있다.
                // m 이상이라면 더 이상 그만 더하자.
                if (sum > m)
                    break;
                sum += mid / gate;
            }
            if (sum < m)
                start = mid + 1;
            else
                end = mid;
        }
        // 최종적으로 end값이 m명이 통과하는데 걸리는 최소 시간.
        System.out.println(end);
    }
}