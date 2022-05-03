/*
 Author : Ruel
 Problem : Baekjoon 15678번 연세워터파크
 Problem address : https://www.acmicpc.net/problem/15678
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15678_연세워터파크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 징검 다리에 각각의 점수가 부여되어있다.
        // 그리고 건너뛸 수 있는 최대 거리 d가 주어진다.
        // 징검 다리는 한번씩만 밟을 수 있다 했을 때, 최대 얻을 수 있는 점수는?
        //
        // 우선순위큐를 이용한 간단한 문제.
        // 먼저 징검다리 각 지점에 대한 최대값을 처음 징검다리 점수로 세팅해준다
        // 그리고 한 방향으로 살펴보며, d 거리 이하의 이전 징검다리들 중 최대 점수 징검 다리에서, 현재 징검다리로 올 경우
        // 최대 점수가 갱신되는지 확인하고 갱신된다면 현재 최대값을 갱신시켜준다
        // 끝까지 반복한 후에, 최대값을 갖고 있는 징검 다리 점수를 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        // 각 지점에서 최대값을 저장해둘 것이다.
        long[] maxValues = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

        // 우선순위큐는 계속해서 값을 추가, 제거해나가며 현재 지점으로 이동 가능한 최대 점수 징검다리를 가르킬 것이다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Long.compare(maxValues[o2], maxValues[o1]));
        // 현재까지의 최대 점수.
        long maxScore = Long.MIN_VALUE;
        for (int i = 0; i < maxValues.length; i++) {
            // 우선순위큐에 담긴 다음 값이 d거리 보다 먼 징검다리라면 제거해준다.
            while (!priorityQueue.isEmpty() && (i - priorityQueue.peek()) > d)
                priorityQueue.poll();

            // 우선순위큐가 비어있지 않다면, i 징검다리로 올 수 있는 이전 징검다리가 있는 경우
            // 그 중에서도 최대 값을 갱신하는지를 확인하고, 그럴 경우에만 반영해준다.
            if (!priorityQueue.isEmpty())
                maxValues[i] = Math.max(maxValues[i], maxValues[i] + maxValues[priorityQueue.peek()]);
            // 다음 차례로 넘어가기 전에, 현재 징검다리도 우선순위큐에 추가해준다.
            priorityQueue.offer(i);
            // 전체 징검다리들 중 최대값을 계속해서 갱신해나간다.
            maxScore = Math.max(maxScore, maxValues[i]);
        }
        // 최종적으로 저장된 maxScore가 d이하의 거리들의 징검다리들을 연속적으로 밟아 만들 수 있는 최대 점수.
        System.out.println(maxScore);
    }
}