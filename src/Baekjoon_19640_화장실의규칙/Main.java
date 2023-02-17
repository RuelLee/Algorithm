/*
 Author : Ruel
 Problem : Baekjoon 19640번 화장실의 규칙
 Problem address : https://www.acmicpc.net/short/status/19640/1002/1
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19640_화장실의규칙;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명이 화장실을 기다리고 있다.
        // 이를 m개의 줄로 나눠서며, 1번은 1번 줄에, 2번은 2번 줄에, ..., m+1번은 다시 1번 줄에 선다
        // 화장실은 가장 선두에 있는 사람들 중 한명이 들어가게 되는데 그 우선순위는
        // 1. 근무일 D가 높은 사람
        // 2. D가 같다면 급한 정도 H가 높은 사람
        // 3. D, H가 같다면 가장 낮은 번호의 줄에 선 사람이 우선적으로 이용한다.
        // 원래 줄에서 k + 1번째 사람이 화장실을 이용하려면, 몇 명의 앞사람이 이용하게 될까?
        //
        // 우선순위큐 문제
        // 우선순위큐를 통해 조건에 따라 정렬을 해서 k + 1번째 사람이 이용하는 순서만 계산해내면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 사람의 수 n, 줄의 수 m, 순서를 구하고자 하는 사람 앞에 서있는 사람의 수 k
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 사람들의 근무일과 급한 정도
        int[][] employees = new int[n][];
        for (int i = 0; i < employees.length; i++)
            employees[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 우선순위큐를 통해 조건에 따른 순서를 구한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            // 근무일이 같고
            if (employees[o1][0] == employees[o2][0]) {
                // 급한 정도도 같다면
                // 선 줄의 번호가 낮은 것을 우선적으로
                if (employees[o1][1] == employees[o2][1])
                    return Integer.compare(o1 % m, o2 % m);
                // 근무일만 같다면 급한 정도가 높은 순서대로
                return Integer.compare(employees[o2][1], employees[o1][1]);
            }
            // 근무일이 같지않다면 근무일이 높은 순서대로
            // 우선순위큐에서 뽑아낸다.
            return Integer.compare(employees[o2][0], employees[o1][0]);
        });
        // 전체 인원과, 줄의 수 중 더 적은 값까지 우선순위큐에 담는다.
        for (int i = 0; i < Math.min(n, m); i++)
            priorityQueue.offer(i);
        
        // 순서 표시
        int count = 0;
        // 우선순위큐가 빌 때까지
        while (!priorityQueue.isEmpty()) {
            // 현재 사람
            int current = priorityQueue.poll();
            
            // 만약 현재 번호가 k라면 (0부터 시작했으므로 우리가 순서를 구하려는 사람)
            // 반복문 종료.
            if (current == k)
                break;
            
            // 순서 증가
            count++;
            // 현재 사람 뒤에 다음 사람이 있는지 확인하고
            // 있다면 우선순위큐에 담는다.
            if (current + m < employees.length)
                priorityQueue.offer(current + m);
        }

        // 순서를 구하려던 사람이 화장실을 이용한 순서를 출력한다.
        System.out.println(count);
    }
}