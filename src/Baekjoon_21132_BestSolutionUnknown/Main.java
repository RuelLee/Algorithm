/*
 Author : Ruel
 Problem : Baekjoon 21132번 Best Solution Unknown
 Problem address : https://www.acmicpc.net/problem/21132
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21132_BestSolutionUnknown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 참가자와 참가자의 실력이 주어진다
        // 각 참가자는 인접한 다른 참가자와 겨룰 수 있고, 이 때 실력이 같거나 우수한 사람이 승리하며, 실력이 하나 증가한다
        // 우승할 가능성이 있는 참가자들을 모두 출력하라
        // 상당히 어려웠던 문제
        // 작은 집합을 점차 옆 집합과 합쳐가며 전체 집합에 대한 답을 구해야한다.
        // 실력이 작은 참가자부터 오름차순으로 살펴보며,
        // 1. 자신보다 왼쪽 집합이 있을 경우, 해당 집합에서 참가자까지 도달할 수 있는 집합원을 구해야한다
        // 각 집합원은 최대 집합의 범위 -1 만큼의 실력을 추가로 이기면서 얻을 수 있다.
        // 만약 이 값이 참가자보다 작다면 참가자가 포함된 집합에서 해당 집합원은 포함될 수 없다.
        // 예를 들어 1 1 1 3에, {1, 1, 1}이 포함된 집합이 있고, 3을 추가시켜한다 하자
        // 각 1은 모두 3까지 실력을 증가시킬 수 있으므로, {1, 1, 1, 3}이 새로운 집합으로 만들어진다.
        // 만약 1 2 1 3 같은 경우에는 어떨까? {1, 2, 1}이라는 집합 자체가 탄생할 수 없다. 1은 2를 이길 수 없으므로, {2}에 3을 추가시키는 경우가 될 것이다.
        // 이 때 집합의 범위는 1 ~ 3이므로 2는 최대 4까지 실력을 키울 수 있어, {2, 3} 범위는 1 ~ 4가 될 것이다.
        // 또한 참가자는 집합보다 나중에 추가되므로(오름차순으로 체크하므로) 항상 집합의 최대값보다 큰 값을 갖고 있어 항상 추가시킬 수 있다.
        // 2. 자신보다 오른쪽 집합이 있을 경우에도 같은 계산을 하며 집합의 점점 키워나간다
        // 3. 최종적으로 하나의 집합이 탄생하고 이 집합이 전체 인원에 대한 우승 가능성이 있는 참가자들이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int k = Integer.parseInt(br.readLine());
        int[] participants = new int[k + 2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 참가자들의 우선순위큐에 담아 오름차순으로 처리한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> participants[o]));
        for (int i = 1; i < k + 1; i++) {
            participants[i] = Integer.parseInt(st.nextToken());
            priorityQueue.offer(i);
        }

        int[] start = new int[k + 2];       // 각 참가자가 속한 집합의 시작을 저장
        int[] end = new int[k + 2];     // 각 참가자가 속한 집합의 마지막을 저장.
        // 집합에 포함된 참가자들을 저장해줄 것이다.
        // 우선순위큐에 저장해 역시 실력 오름차순으로 정렬한다면, 집합을 합칠 때 탈락되는 참가자들을 빨리 구할 수 있다.
        PriorityQueue<Integer>[] indexes = new PriorityQueue[k + 2];
        while (!priorityQueue.isEmpty()) {
            // 오름차순으로 참가자를 꺼내
            int current = priorityQueue.poll();
            // 아직 집합에 속하지 않았다면
            if (start[current] == 0) {
                // current에 해당하는 집합을 만들고, 포함.
                indexes[current] = new PriorityQueue<>(Comparator.comparingInt(o -> participants[o]));
                indexes[current].offer(current);
                // 범위를 지정
                start[current] = end[current] = current;
            }

            // 참가자보다 왼쪽 집합이 존재한다면
            if (start[current - 1] != 0) {
                // 해당 집합에서 현재 참가자를 포함했을 때 우승 가능성이 없는 참가자들을 제외한다.
                // 집합은 start[current - 1] ~ current - 1의 범위를 갖는다
                // 따라서 current - 1 - start[current - 1]만큼의 실력을 이김으로 얻을 수 있다
                // 그 값이 현재 참가자의 실력보다 작다면 우승 가능성이 없는 경우. 제외해준다.
                while (!indexes[start[current - 1]].isEmpty() &&
                        participants[indexes[start[current - 1]].peek()] + current - 1 - start[current - 1] < participants[current])
                    indexes[start[current - 1]].poll();
                // 그 후 current와 우선순위큐 집합을 합쳐준다.
                if (indexes[start[current - 1]].size() > indexes[current].size()) {
                    while (!indexes[current].isEmpty())
                        indexes[start[current - 1]].offer(indexes[current].poll());
                } else {
                    while (!indexes[start[current - 1]].isEmpty())
                        indexes[current].offer(indexes[start[current - 1]].poll());
                    indexes[start[current - 1]] = indexes[current];
                }
                // 범위를 다시 재지정.
                start[current] = start[current - 1];
                end[start[current]] = current;
            }

            // 오른쪽 집합에 대해서도 체크해준다.
            if (start[current + 1] != 0) {
                while (!indexes[start[current + 1]].isEmpty() &&
                        participants[indexes[start[current + 1]].peek()] + end[current + 1] - (current + 1) < participants[current])
                    indexes[start[current + 1]].poll();

                if (indexes[start[current]].size() < indexes[current + 1].size()) {
                    while (!indexes[start[current]].isEmpty())
                        indexes[current + 1].offer(indexes[start[current]].poll());
                    indexes[start[current]] = indexes[current + 1];
                } else {
                    while (!indexes[current + 1].isEmpty())
                        indexes[start[current]].offer(indexes[current + 1].poll());
                }
                start[end[current + 1]] = start[start[current]];
                end[start[start[current]]] = end[current + 1];
            }
        }
        // 참가자를 오름차순 출력해야하므로 다시 우선순위큐에 index 들을 담아준다.
        PriorityQueue<Integer> answer = new PriorityQueue<>();
        while (!indexes[1].isEmpty())
            answer.offer(indexes[1].poll());
        StringBuilder sb = new StringBuilder();
        sb.append(answer.size()).append("\n");
        while (!answer.isEmpty())
            sb.append(answer.poll()).append(" ");
        System.out.println(sb);
    }
}