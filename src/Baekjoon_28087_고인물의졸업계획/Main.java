/*
 Author : Ruel
 Problem : Baekjoon 28087번 고인물의 졸업 계획
 Problem address : https://www.acmicpc.net/problem/28087
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28087_고인물의졸업계획;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // m개의 과목에 대한 학점이 주어진다.
        // 한 학기에는 최대 2 * n학점까지 들을 수 있고, 졸업하려면 n 학점을 더 채워야한다.
        // 졸업을 하려면 들어야하는 과목의 수와 해당 과목을 출력하라
        // 방법이 여러가지라면 그 중 아무거나 출력해도 된다.
        //
        // 그리디 문제
        // 모든 과목에 대해 정렬하여 처리하려하면 시간 초과가 난다.
        // 따라서 정렬을 하지 않고, 현재 수강하려고 하는 과목에 대해서만 우선순위큐를 통해 처리한다.
        // 학점이 그 자체로 2 * n을 넘어간다면 건너뛰고,
        // 현재 선택한 과목들의 학점 합과 이번 과목의 학점 합이 2 * n 을 넘어간다면
        // 선택한 과목들 중 작은 학점인 과목을 제하여, 2 * n이 넘지 않도록 조절한다.
        // 그 후, 해당 과목을 추가하여 학점 합이 n 이상인지 확인한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n학점, m개의 과목
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 과목에 따른 학점들
        int[] subjects = new int[m];
        for (int i = 0; i < subjects.length; i++)
            subjects[i] = Integer.parseInt(br.readLine());
        
        // 현재 선택한 과목들의 idx를 우선순위큐를 통해 학점 오름차순으로 처리
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> subjects[o]));
        // 선택한 과목 학점 합
        int sum = 0;
        for (int i = 0; i < subjects.length; i++) {
            // 과목 자체가 2 * n 을 넘어간다면 건너뜀.
            if (subjects[i] > n * 2)
                continue;

            // 선택한 과목이 1개 이상 있으며
            // 선택한 과목 합과 i번째 과목 학점 합이 2 * n을 넘어간다면
            // 학점이 낮은 과목들을 제외시켜간다.
            while (!priorityQueue.isEmpty() && sum + subjects[i] > 2 * n)
                sum -= subjects[priorityQueue.poll()];
            
            // i번째 과목을 선택하여
            // 학점 합과 우선순위큐에 반영
            sum += subjects[i];
            priorityQueue.offer(i);
            
            // 만약 학점 합이 n 이상이라면 반복문 종료
            if (sum >= n)
                break;
        }

        // 해당하는 경우가 반드시 존재한다고 했으므로
        // 우선순위큐에 들어있는 과목들을 바탕으로 답안을 작성한다.
        StringBuilder sb = new StringBuilder();
        // 우선순위큐의 크기
        sb.append(priorityQueue.size()).append("\n");
        // 개별 과목들의 idx
        while (!priorityQueue.isEmpty())
            sb.append(priorityQueue.poll() + 1).append("\n");
        // 답안 출력
        System.out.print(sb);
    }
}