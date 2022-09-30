/*
 Author : Ruel
 Problem : Baekjoon 2461번 대표 선수
 Problem address : https://www.acmicpc.net/problem/2461
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2461_대표선수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 학급에는 각각 m명의 학생이 있으며, 각각의 능력치를 갖고 있다.
        // 각 학급에서 대표를 선정해 경기를 하려하는데, 이 때의 각 학급 대표의 능력치 차이를 최소한으로 하고 싶다.
        // 이 때 최소 능력치 차이 값은?
        //
        // 우선순위큐를 이용해 풀이하였다.
        // n개의 학급이 주어지나 결국 차이를 결정 짓는 것은 각 대표들의 능력치의 최소값과 최대값이다.
        // 따라서 우선 순위큐를 이용하여, 각 학급을 오름차순으로 살펴보며
        // 이 때 대표의 능력치가 가장 낮은 학급을 다음 학생으로 하나씩 올려가는 작업을 반복하며
        // 능력치 차이를 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n개의 학급, m명의 학생.
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 우선순위큐를 이용해 학급의 대표들 중 가장 능력치가 낮은 학급을 우선적으로 살펴본다.
        PriorityQueue<PriorityQueue<Integer>> classes = new PriorityQueue<>(Comparator.comparingInt(pq -> pq.peek()));
        // 대표들 중 가장 능력치가 높은 값.
        int max = 0;
        // n개의 학급에 대해 주어진 입력 처리.
        for (int i = 0; i < n; i++) {
            // 학생 또한 최소힙 우선순위큐로 저장한다.
            PriorityQueue<Integer> students = new PriorityQueue<>();
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                students.offer(Integer.parseInt(st.nextToken()));
            // 학급의 입력이 끝났고, 이 학급의 대표 능력치가 대표들 중 가장 높은지 확인한다.
            max = Math.max(students.peek(), max);
            // 해당 학급에 대한 우선순위큐도 우선순위큐에 담는다.
            classes.offer(students);
        }

        // 능력치 차이의 최소값.
        // 현재 각 학급의 대표는 능력치가 가장 낮은 학생으로 되어있고, 이를 통해 차이값을 초기화한다.
        int minDiff = max - classes.peek().peek();
        // 능력치가 가장 낮은 대표의 학급에 해당 대표보다 능력치가 더 높은 학생가 있을 때만.
        while (!classes.isEmpty() && classes.peek().size() > 1) {
            // 해당 학급을 꺼낸다.
            PriorityQueue<Integer> minPlayerClass = classes.poll();
            // 다음 이번 학생을 꺼내 다음 학생을 대표로 만든다.
            minPlayerClass.poll();
            // 이번 대표가 대표들 중 가장 능력치가 높은지 확인한다.
            max = Math.max(minPlayerClass.peek(), max);
            // 학급을 다시 우선순위큐에 담는다.
            classes.offer(minPlayerClass);
            // 차이는 대표들 중 가장 높은 능력치인 max값과
            // 대표들 중 가장 낮은 능력치인 class.peek() 학급의 대표 class.peek().peek()의 차이로 구한다.
            minDiff = Math.min(max - classes.peek().peek(), minDiff);
        }
        // 구한 최소 차이를 출력한다.
        System.out.println(minDiff);
    }
}