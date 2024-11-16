/*
 Author : Ruel
 Problem : Baekjoon 2878번 캔디캔디
 Problem address : https://www.acmicpc.net/problem/2878
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2878_캔디캔디;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Friend {
    int candy;
    int count;

    public Friend(int candy, int count) {
        this.candy = candy;
        this.count = count;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // m개의 사탕을 n명의 친구들과 나누고자 한다.
        // n명의 친구는 각자 받고 싶은 개수의 사탕 수가 있다.
        // 원하는 만큼에서 받지 못한 개수의 제곱 만큼 분노 게이지가 쌓인다고 한다.
        // 분노 합을 최소화하고자 할 때, 그 값을 2^64 으로 나눈 나머지를 출력하라
        //
        // 그리디 문제
        // 가장 많이 받고 싶은 친구부터, 사탕을 나눠주어
        // 각 학생의 받지 못한 사탕의 개수를 큰 값부터 줄여나가는 작업을 한다.
        // m이 크기 때문에 하나씩 계산해서는 안되고, 같은 요구량을 갖는 친구들을 한번에 처리하도록 하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // m개의 사탕, n명의 친구들
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 각 친구들의 사탕 요구량을 우선순위큐에 담는다.
        // 요구량이 많은 친구들부터 볼 수 있게 최대 힙 우선순위큐 사용
        PriorityQueue<Friend> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.candy, o1.candy));
        for (int i = 0; i < n; i++)
            priorityQueue.offer(new Friend(Integer.parseInt(br.readLine()), 1));

        // 우선순위큐가 비지 않았고, m이 아직 남아있는 동안
        while (!priorityQueue.isEmpty() && m > 0) {
            Friend current = priorityQueue.poll();
            // current와 같은 사탕 요구량을 갖는 친구들을 count로 누적시킨다.
            while (!priorityQueue.isEmpty() && priorityQueue.peek().candy == current.candy)
                current.count += priorityQueue.poll().count;
            // 만약 current 요구량이 0이라면 건너 뛴다.
            if (current.candy == 0)
                continue;
            
            // 우선순위큐 최상단에 있는 친구의 요구량 혹은 우선순위큐가 비어있다면 0
            int next = !priorityQueue.isEmpty() ? priorityQueue.peek().candy : 0;
            // m이 충분하여 current의 요구량을 모두 next로 만들 수 있다면
            if (m >= (long) (current.candy - next) * current.count) {
                m -= (current.candy - next) * current.count;
                current.candy = next;
                priorityQueue.offer(current);
            } else if (m >= current.count) {
                // m이 current의 요구량을 전부 next로 바꿀수는 없지만
                // current의 인원수보다는 많아, 전체 인원을 일정량 만큼 요구량을 낮출 수 있는 경우
                
                // 낮출 수 있는 요구량
                int multi = m / current.count;
                current.candy -= multi;
                m -= multi * current.count;
                priorityQueue.offer(current);
            } else {
                // m이 current의 인원수보다 적어, 일부의 인원만 요구량을 낮출 수 있는 경우
                priorityQueue.offer(new Friend(current.candy - 1, m));
                current.count -= m;
                m = 0;
                priorityQueue.offer(current);
            }
        }

        // 우선순위큐에 남은 인원들을 바탕으로
        // 분노 게이지 합을 계산한다.
        // 값이 long 범위를 벗어날 수 있음으로 BigInteger 타입을 사용한다.
        BigInteger answer = BigInteger.ZERO;
        BigInteger limit = BigInteger.TWO.pow(64);
        while (!priorityQueue.isEmpty()) {
            Friend current = priorityQueue.poll();
            BigInteger add = BigInteger.valueOf(current.candy).pow(2).multiply(BigInteger.valueOf(current.count));
            answer = answer.add(add);
            answer = answer.mod(limit);
        }
        // 답 출력
        System.out.println(answer);
    }
}