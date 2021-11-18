/*
 Author : Ruel
 Problem : Baekjoon 10165번 버스 노선
 Problem address : https://www.acmicpc.net/problem/10165
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 버스노선;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Bus {
    int num;
    int start;
    int end;

    public Bus(int num, int start, int end) {
        this.num = num;
        this.start = start;
        this.end = end;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 0 ~ n - 1까지 원형 모양의 정류장을 지나가는 버스 노선들이 주어진다
        // 버스 노선이 다른 버스 노선 안에 포함될 경우, 해당 노선을 취소하려고 한다
        // 취소되지 않는 노선들을 나타내는 문제
        // 일반적인 a-b(a < b)일 경우에는
        // a가 같을 경우에는 b에 대해서 내림차순, a가 다를 경우에는 a에 대해서 오름차순 정렬을 하게 되면
        // 먼저 나오는 경우가 항상 다음 나오는 것보다 범위가 크거나 겹치지 않는 경우가 주어진다.
        // 따라서 이전 것을 기억하되, 포함되는 경우에는 해당 노선은 버리고, 겹치지 않는 경우에는 이전 노선을 저장하고, 이번 노선을 이전 노선으로 기억해둔다.
        // 하지만 원형이라는 점이 문제이다
        // n이 10이 주어지고, 8-2, 8-9, 1-2 라는 노선이 주어진다면 이를 어떻게 처리해야할까에 대해 상객했다
        // 8-2라는 노선이 8-9라는 노선도 포함하고, 1-2라는 노선도 포함한다
        // 따라서 a-b(a > b)인 경우에는 ((a-n)-b), (a- (b+n)) 두 개의 노선으로 저장하기로 했다.
        // 그렇다면 위 두가지 경우 모두 판별할 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        // 버스는 시작점에 대해 오름차순 정렬하되, 같은 시작점을 가질 경우, 도착점에 대해 내림차순 정렬한다.
        PriorityQueue<Bus> priorityQueue = new PriorityQueue<>((o1, o2) -> o1.start == o2.start ? Integer.compare(o2.end, o1.end) : Integer.compare(o1.start, o2.start));
        StringTokenizer st;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (a < b)
                priorityQueue.offer(new Bus(i + 1, a, b));
            else {
                priorityQueue.offer(new Bus(i + 1, a - n, b));
                priorityQueue.offer(new Bus(i + 1, a, b + n));
            }
        }

        Bus pre = priorityQueue.poll();     // 첫 버스를 먼저 꺼내놓는다.
        PriorityQueue<Integer> answer = new PriorityQueue<>();
        HashSet<Integer> hashSet = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            Bus current = priorityQueue.poll();     // 이번 버스를 꺼내고
            if (current.end > pre.end) {        // 이번 버스가 이전 버스보다 도착점이 더 멀다면(= 범위가 겹치지 않는다면)
                if (!hashSet.contains(pre.num)) {       // 해쉬셋에 이전 버스가 기록되어있는지 확인하고
                    hashSet.add(pre.num);       // 해쉬셋에 추가
                    answer.offer(pre.num);      // 답을 오름차순 정렬해줄 우선순위큐에 삽입
                }
                pre = current;      // 비교할 이전 버스는 이번 버스로 대체한다.
            }
        }
        if (!hashSet.contains(pre.num))     // 마지막 버스가 해쉬셋에 포함되어있지 않다면
            answer.offer(pre.num);      // 우선순위큐에 넣어준다.
        StringBuilder sb = new StringBuilder();
        while (!answer.isEmpty())
            sb.append(answer.poll()).append(" ");
        System.out.println(sb);
    }
}