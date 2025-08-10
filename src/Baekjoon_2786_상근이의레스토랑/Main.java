/*
 Author : Ruel
 Problem : Baekjoon 2786번 상근이의 레스토랑
 Problem address : https://www.acmicpc.net/problem/2786
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2786_상근이의레스토랑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 한 레스토랑에 n개의 메뉴가 있고
        // 각 메뉴는 처음으로 해당 메뉴를 시킬 때의 가격 Ai와 그렇지 않을 때의 가격 Bi가 존재한다.
        // 각 1, 2, ..., n개의 서로 다른 메뉴를 시킬 때, 최소 가격을 출력하라
        //
        // 그리디, 정렬 문제
        // i개의 메뉴를 시킬 때의 최소 가격은
        // i - 1개의 메뉴를 첫 주문이 아닌 가격으로 가장 싼 순으로 주문한 뒤
        // i번째 메뉴를 아직 주문하지 않은 메뉴 중 첫 주문 가격이 가장 싼 음식으로 주문하거나
        // 혹은 i번째 메뉴를 첫 주문이 아닌 가격으로 다음으로 싼 메뉴를 주문하고
        // i - 1개의 메뉴들 중 첫 주문과의 가격 차이가 가장 적은 메뉴를 첫 주문으로 바꾸는 방법 중 하나이다.
        // 두 경우를 비교하여 계산하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 메뉴
        int n = Integer.parseInt(br.readLine());
        int[][] dishes = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < dishes.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < dishes[i].length; j++)
                dishes[i][j] = Integer.parseInt(st.nextToken());
        }
        // 첫 주문이 아닐 때의 가격으로 오름차순 정렬
        Arrays.sort(dishes, Comparator.comparingInt(o -> o[1]));

        // 첫 주문이 가장 싼 메뉴들을 우선순위큐를 통해 꺼낸다.
        PriorityQueue<Integer> minFirst = new PriorityQueue<>(Comparator.comparingInt(o -> dishes[o][0]));
        for (int i = 0; i < dishes.length; i++)
            minFirst.offer(i);
        
        // 0 ~ i까지의 범위 중 첫 주문과 그렇지 않을 때의 차이가 가장 적은 음식
        int minDiffIdx = -1;
        StringBuilder sb = new StringBuilder();
        // 첫 주문이 아닌 음식들의 가격 합
        long secondSum = 0;
        for (int i = 0; i < dishes.length; i++) {
            // minFirst가 i의 범위 내에 들어갔다면 제거한다.
            while (!minFirst.isEmpty() && minFirst.peek() < i)
                minFirst.poll();
            
            // 0 ~ i-1번째 음식을 첫 주문이 아닐 때의 가격으로 가장 싼 음식들로 채우고
            // 해당 범위 밖에서 첫 주문이 가장 싼 음식을 주문할 때의 가격
            long newFirst = minFirst.isEmpty() ? Long.MAX_VALUE : (dishes[minFirst.peek()][0] + secondSum);
            if (minDiffIdx == -1 || dishes[minDiffIdx][0] - dishes[minDiffIdx][1] > dishes[i][0] - dishes[i][1])
                minDiffIdx = i;
            secondSum += dishes[i][1];
            // 0 ~ i번째 음식을 차례대로 주문하고, 그 중에서 첫 주문과 그렇지 않을 때의 가격 차이가 가장 적은 음식을
            // 첫 주문으로 바꿨을 때의 가격
            long secondToFirst = secondSum + dishes[minDiffIdx][0] - dishes[minDiffIdx][1];
            // 두 가격 중 싼 가격을 기록
            sb.append(Math.min(newFirst, secondToFirst)).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}