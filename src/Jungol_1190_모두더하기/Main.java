/*
 Author : Ruel
 Problem : Jungol 1190번 모두더하기
 Problem address : https://jungol.co.kr/problem/1190
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1190_모두더하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수로 이루어진 집합이 주어진다.
        // 이 중 개의 수를 더해 ret를 누적시키고, 더한 값은 다시 집합에 넣는다.
        // 집합에 한 수만 남을 때까지 해당 행동을 반복할 때
        // ret의 최소값은?
        //
        // 그리디. 우선순위큐 문제
        // ret에는 더하는 수가 계속 누적된다.
        // 큰 수일수록 덜 누적되는 것이 좋다. 따라서 가장 작은 수 두개를 골라 합치는 과정을 반복한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 우선순위큐로 정렬
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
            priorityQueue.add(Integer.parseInt(st.nextToken()));

        long sum = 0;
        // 우선순위큐에 2개 이상의 수가 담겨있는 동안
        while (priorityQueue.size() > 1) {
            // 두 수를 뽑아
            int a = priorityQueue.poll();
            int b = priorityQueue.poll();

            // 합친 값을 누적시키고
            sum += a + b;
            // 다시 집합에 넣는다.
            priorityQueue.add(a + b);
        }
        // 답 출력
        System.out.println(sum);
    }
}