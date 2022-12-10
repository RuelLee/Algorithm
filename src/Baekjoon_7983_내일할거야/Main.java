/*
 Author : Ruel
 Problem : Baekjoon 7983번 내일 할거야
 Problem address : https://www.acmicpc.net/problem/7983
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7983_내일할거야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // d일이 소요되고, t일까지 제출해야하는 과제 n개가 주어진다
        // 최대한 과제들을 미뤄, 오늘부터 연속하여 과제를 안해도 되는 최대일을 구하라
        //
        // 간단한 그리디 문제
        // 제출일이 가장 늦은 과제부터 살펴보며 최대한 뒤쪽에 과제들을 배치하여 나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 과제의 개수
        int n = Integer.parseInt(br.readLine());
        // 최대힙 우선순위큐를 통해 과제 제출일이 늦은 과제들부터 살펴보자.
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2[1], o1[1]));
        for (int i = 0; i < n; i++)
            priorityQueue.offer(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray());
        
        // 첫날부터 연속하여 놀 수 있는 날의 최대일
        int maxDay = (int) Math.pow(10, 9);
        while (!priorityQueue.isEmpty()) {
            // 과제를 하나 꺼내
            int[] homework = priorityQueue.poll();
            // 과제 제출 기한과 maxDay를 비교한다.
            // 저번 과제를 시작하기 전 날(= 과제를 하고 있지 않은 가장 늦은 날)과
            // 이번 과제를 제출해야하는 기한과 비교하는 것.
            // 둘 중 적은 값을 취해 해당 날짜까지 이번 과제를 진행해야한다.
            maxDay = Math.min(maxDay, homework[1]);
            // maxDay에 마치기 위해서는 maxDay - homework[0] + 1일부터 진행하여, maxDay까지 진행한다.
            // 과제를 진행하지 않는 가장 늦은 날은 maxDay - homework[0]이다.
            maxDay -= homework[0];
        }

        // 모든 과제를 처리하고 나서, 0일부터 연속하여 놀 수 있는 가장 늦은 날을 출력한다.
        System.out.println(maxDay);
    }
}