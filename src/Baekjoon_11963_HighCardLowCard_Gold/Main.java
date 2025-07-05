/*
 Author : Ruel
 Problem : Baekjoon 11963번 High Card Low Card (Gold)
 Problem address : https://www.acmicpc.net/problem/11963
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11963_HighCardLowCard_Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ 2n이 적혀있는 카드를 bessie와 elsie가 n개씩 나눠 갖는다.
        // 총 n번의 게임을 하는데, n/2번째까지는 더 큰 수를 낸 사람이, n/2 + 1 ~ n번째까지는 더 작은 수를 낸 사람이 승리한다.
        // elsie가 내는 카드가 모두 주어질 때, bessie가 이길 수 있는 최대 횟수는?
        //
        // 그리디, 정렬 문제
        // 전후반에 따라 게임 승리 조건이 달라지기 때문에
        // 전반에는 최대한 이기면서, 큰 수의 카드들을 최대한 많이 처리해야하고
        // 후반에는 elsie가 내는 카드보단 작으면서 가장 큰 카드를 내 이겨야한다.
        // 따라서 전반에 내는 카드들을 정렬하여, bessie가 가진 카드 중 가장 큰 카드를 우선적으로 내며 이겨나간다.
        // 후반에는 elsie보다 작지만 가장 큰 카드들을 내며 이겨나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n번의 게임
        int n = Integer.parseInt(br.readLine());

        // elsie가 가져가는 카드들을 표시해둔다.
        boolean[] selected = new boolean[2 * n + 1];
        // 전반
        int[] firstHalf = new int[n / 2];
        for (int i = 0; i < firstHalf.length; i++)
            selected[firstHalf[i] = Integer.parseInt(br.readLine())] = true;
        Arrays.sort(firstHalf);
        // 후반
        int[] secondHalf = new int[n / 2];
        for (int i = 0; i < secondHalf.length; i++)
            selected[secondHalf[i] = Integer.parseInt(br.readLine())] = true;
        Arrays.sort(secondHalf);

        // elsie가 갖지 않은 카드들을 bessie가 가져가며
        // 큰 순서대로 살펴보기 위해 우선순위큐를 사용한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 2 * n; i > 0; i--) {
            if (!selected[i])
                priorityQueue.add(i);
        }

        int count = 0;
        // 전반에는 elsie가 내는 카드들을 내림차순으로 살펴보며
        // 가장 큰 수를 내며 이겨나간다.
        for (int i = firstHalf.length - 1; i >= 0; i--) {
            if (priorityQueue.peek() > firstHalf[i]) {
                priorityQueue.poll();
                count++;
            }
        }

        // 후반에는 elsie가 내는 카드보다는 작지만 가장 큰 카드를 내며 이겨나간다.
        for (int i = secondHalf.length - 1; i >= 0; i--) {
            while (!priorityQueue.isEmpty() && priorityQueue.peek() > secondHalf[i])
                priorityQueue.poll();

            // 카드가 다 떨어졌다면 그만.
            if (priorityQueue.isEmpty())
                break;

            priorityQueue.poll();
            count++;
        }

        // 총 이긴 횟수 출력
        System.out.println(count);
    }
}