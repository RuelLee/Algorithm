/*
 Author : Ruel
 Problem : Baekjoon 1071번 소트
 Problem address : https://www.acmicpc.net/problem/1071
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1071_소트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수들이 주어질 때, 사전순으로 가장 이른순으로 정렬하되, 뒤에 오는 숫자가 앞의 숫자 + 1이 되지 않게 한다
        // 기본적으로 정렬하여 작은 순으로 꺼내되,
        // 만약 이번 순서의 수가 이전 순서의 수보다 1 큰 값이라면 이번 순서를 건너뛰고, 다음 순서의 값을 살펴본다
        // 만약 다음 순서의 값이 없다면, 이전 값을 취소하고, 이번 순서의 값을 집어 넣는다.
        // 하지만 같은 수가 여러번 주어져 이미 앞에도 같은 수들로 이루어진 경우가 발생할 수도 있다 ex) 1 1 2 의 경우.
        // 따라서 위와 같은 경우를 방지하기 위해, 순서에 남은 종류의 숫자가 2개인데, 그 수들이 서로 인접한 숫자라면 큰 수를 먼저 사용하고, 작은 수를 사용하도록 한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 중복되는 수가 들어올 수 있다
        // 불필요한 계산을 줄이기 위해, 우선순위큐에는 종류만 집어넣고, 개수를 따로 세주도록 하자.
        // 최대가 1000까지의 수들.
        int[] nums = new int[1001];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            if (nums[num] == 0) // 이전에 들어온 적이 없는 수라면
                priorityQueue.offer(num);       // 우선순위큐에 추가.
            nums[num]++;        // nums에 개수 추가.
        }

        Queue<Integer> cannotLocated = new LinkedList<>();      // 사용할 수 없는 수.
        Deque<Integer> sorted = new LinkedList<>();     // 정렬이 된 수.
        while (!priorityQueue.isEmpty()) {
            // 만약 남은 종류의 수가 2개이고, 서로 인접한 수라면 or
            // 정렬된 수의 마지막 수가 이번 차례의 수보다 1작은 수라면
            // 이번 순서의 수는 쓸 수 없다. cannotLocated로 넘겨준다.
            if ((priorityQueue.size() == 2 && nums[priorityQueue.peek() + 1] > 0) ||
                    !sorted.isEmpty() && sorted.peekLast() + 1 == priorityQueue.peek())
                cannotLocated.offer(priorityQueue.poll());

            // 가능한 가장 작은 수를 우선순위큐에서 뽑아,
            // sorted 데크 마지막에 넣어준다.
            sorted.offerLast(priorityQueue.peek());
            nums[priorityQueue.peek()]--;
            // 현재 순서 수의 개수를 모두 사용했다면 우선순위큐에서 제거해준다.
            if (nums[priorityQueue.peek()] == 0)
                priorityQueue.poll();

            // cannotLocated에 사용되지 않은 수들을 다시 우선순위큐에 넣어준다.
            while (!cannotLocated.isEmpty())
                priorityQueue.offer(cannotLocated.poll());
        }
        StringBuilder sb = new StringBuilder();
        // sorted에 정렬이 끝났다. 선입선출로 들어온 순서대로 답안을 만들자.
        while (!sorted.isEmpty())
            sb.append(sorted.pollFirst()).append(" ");
        System.out.println(sb);
    }
}