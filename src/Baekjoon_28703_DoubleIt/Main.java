/*
 Author : Ruel
 Problem : Baekjoon 28703번 Double It
 Problem address : https://www.acmicpc.net/problem/28703
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28703_DoubleIt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 원하는 수를 하나 곱해 2를 곱하는 과정을 반복한다.
        // 그러한 과정 중 최대값과 최소값의 차이를 최소화하고자 할 때 그 차이를 출력하라
        //
        // 그리디 문제
        // 우선순위큐를 통해 최소값을 관리하면서
        // 지속적으로 최소값을 2배로 하며 최대값과 최소값의 차이를 계산한다.
        // 문제는 언제까지 해당 작업을 반복하느냐인데
        // 값의 범위가 1 ~ 10억까지 주어지므로
        // 당연히 처음에 주어지는 수열의 최대값보다 더 적은 값이 최소 차이가 된다.
        // 가장 작은 수를 2배로 해주는 연산을 해주다가 
        // 결국 처음에 최대값이었던 수를 2배로 해야하는 시점이 오는데
        // 그 이전까지 해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 우선순위큐를 통해 최소값을 관리한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 원래 최대값
        int originalMax = 0;
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            originalMax = Math.max(originalMax, num);
            priorityQueue.offer(num);
        }
        
        // 차이의 최소값을 계산
        int diff = originalMax - priorityQueue.peek();
        // 현재 최대값
        int currentMax = originalMax;
        // 2배가 될 수가 처음 최대값보다 작은 값인 동안에만 반복한다.
        while (priorityQueue.peek() < originalMax) {
            int doubled = priorityQueue.poll() * 2;
            priorityQueue.offer(doubled);
            
            // 현재 최대값 계산
            currentMax = Math.max(currentMax, doubled);
            // 현재 차이를 계산하고, 차이의 최소값을 갱신하는지 확인
            diff = Math.min(diff, currentMax - priorityQueue.peek());
        }
        
        // 답안 출력
        System.out.println(diff);
    }
}