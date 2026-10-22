/*
 Author : Ruel
 Problem : Jungol 1929번 책꽂이 만들기
 Problem address : https://jungol.co.kr/problem/1929
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1929_책꽂이만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 l1, l2, ..., ln이 주어진다.
        // 총 길이가 l1 ~ ln까지의 합인 하나의 널빤지를 잘라, l1, l2, ... , ln 길이의 널빤지들로 나누려고 한다.
        // 한 번 나눌 때마다, 현재 널빤지의 길이만큼 비용이 소모된다.
        // 지불해야하는 최소 비용은?
        //
        // 우선순위큐 문제
        // 널빤지를 하나에서부터 n개로 나눠가려고 하면, 나눠지는 두 널빤지의 길이 중 긴 것이 짧을 수록 좋다.
        // 다시 말해 최대 두 개가 비슷하게 나눠야한다. 이렇게 하려면.. 복잡하고 연산도 많다.
        // 반대로 n개의 널빤지를 하나로 이어붙인다고 생각해보면 쉬워진다.
        // 널빤지를 두 개를 이어붙이고, 그 비용은 두 널빤지 길이의 합이라고 한다면
        // 가장 짧은 널빤지 두 개를 계속해서 이어붙이면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 조각으로 널빤지가 나뉘어있고, 이를 이어붙인다고 생각하자.
        int n = Integer.parseInt(br.readLine());
        // 우선순위큐에 담아, 가장 작은 두 널빤지를 구한다.
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
            priorityQueue.offer(Long.parseLong(br.readLine()));

        long sum = 0;
        // 하나의 널빤지가 될 때까지
        // 가장 작은 두 널빤지를 계속 이어붙인다.
        while (priorityQueue.size() > 1) {
            long a = priorityQueue.poll();
            long b = priorityQueue.poll();
            sum += (a + b);
            priorityQueue.offer(a + b);
        }
        // 답 출력
        System.out.println(sum);
    }
}