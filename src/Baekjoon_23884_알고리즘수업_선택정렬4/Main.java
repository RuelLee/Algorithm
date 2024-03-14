/*
 Author : Ruel
 Problem : Baekjoon 23884번 알고리즘 수업 - 선택 정렬 4
 Problem address : https://www.acmicpc.net/problem/23884
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23884_알고리즘수업_선택정렬4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class State {
    int idx;
    int value;

    public State(int idx, int value) {
        this.idx = idx;
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 배열의 크기 n, 교환 횟수 k가 주어진다.
        // 선택 정렬을 시행하여 교환이 k번 일어났을 때의
        // 수열 상태를 출력하라
        // 교환이 k번 일어나지 않는다면 -1을 출력한다
        //
        // 우선순위큐
        // 우선순위큐를 사용하여 현재 교환이 일어나지 않은 가장 큰 수를 찾아
        // 위치할 수 있는 가장 마지막 순서의 수와 교환하는 작업을 반복한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 수열의 크기 n, 교환 횟수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        PriorityQueue<State> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.value, o1.value));
        // 현재 수의 순서와 값을 우선순위큐에 추가
        for (int i = 0; i < nums.length; i++)
            priorityQueue.offer(new State(i, nums[i]));
        
        // 현재 교환횟수
        int count = 0;
        // 교환이 일어나지 않은 가장 마지막 수의 idx
        int lastIdx = nums.length - 1;
        while (!priorityQueue.isEmpty() && count < k) {
            // 현재 교환을 할 수
            State current = priorityQueue.poll();
            // 이미 현재 자리에 없는 수라면 건너뛴다.
            if (nums[current.idx] != current.value)
                continue;

            // 현재 교환이 일어날 수가 가장 마지막 idx가 아니라면
            // 해당 수와 교환을 한다.
            if (current.idx != lastIdx) {
                nums[current.idx] = nums[lastIdx];
                nums[lastIdx] = current.value;

                // current.idx에는 lastIdx에 있던 수가 위치.
                current.value = nums[current.idx];
                // 다시 우선순위큐에 추가
                priorityQueue.offer(current);
                // 교환 카운트
                count++;
            }
            // lastIdx까지 정렬이 일어났으므로
            // lastIdx 값을 다음 순서인 lastIdx - 1로 넘긴다.
            lastIdx--;
        }
        
        // 교환이 k회 일어나지 않았다면 -1 출력
        if (count < k)
            System.out.println(-1);
        else {      // 일어났을 경우, 현재 수열의 상태 출력
            StringBuilder sb = new StringBuilder();
            for (int num : nums)
                sb.append(num).append(" ");
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(sb);
        }
    }
}