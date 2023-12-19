/*
 Author : Ruel
 Problem : Baekjoon 14222번 배열과 연산
 Problem address : https://www.acmicpc.net/problem/14222
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14222_배열과연산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수와 k가 주어진다.
        // 수 중 하나를 골라 k를 더하는 연산을 무한히 할 수 있다.
        // n개의 수를 1 ~ n까지의 수가 모두 하나씩 있는 배열로 만들 수 있는가?
        //
        // 그리디 문제
        // 1 ~ n까지의 수가 모두 등장해야하므로
        // 1부터 모든 수를 순서대로 살펴보며
        // 우선순위큐에 배열을 담아 오름차순으로 같이 살펴본다
        // 차례인 수보다 더 작은 수라면 k를 더해 다시 우선순위큐에 담고
        // 같다면 다음 차례의 수로 넘긴다.
        // 크다면 수를 줄일 방법은 없으므로 불가능한 경우이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수
        int n = Integer.parseInt(st.nextToken());
        // 한 번 더할 때 더할 수 있는 수 k
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        // 우선순위큐에 수들을 담는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
            priorityQueue.offer(Integer.parseInt(st.nextToken()));

        // 1부터 n까지의 수를 만들어야한다.
        int num = 1;
        while (!priorityQueue.isEmpty()) {
            // 현재 가장 작은 수
            int current = priorityQueue.poll();
            // num보다 작다면 k를 더해 다시 우선순위큐에 담는다.
            if (current < num)
                priorityQueue.offer(current + k);
            // 같다면 순서인 수를 하나 증가시킨다.
            else if (current == num)
                num++;
            // 크다면 줄일 방법은 없으므로 종료.
            else
                break;
        }

        // num이 n+1까지 증가했다면 1 ~ n까지의 수를 모두 만든 경우이므로 1 출력
        // 그렇지 않은 경우엔 불가능한 경우이므로 0 출력
        System.out.println(num == n + 1 ? 1 : 0);
    }
}