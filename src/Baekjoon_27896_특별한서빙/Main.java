/*
 Author : Ruel
 Problem : Baekjoon 27896번 특별한 서빙
 Problem address : https://www.acmicpc.net/problem/27896
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27896_특별한서빙;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생이 줄서 있으며, 각 학생은 원하지 않는 메뉴를 받는다면
        // xi만큼의 불만족도를 쌓고, 원하는 메뉴를 받는다면 xi만큼의 불만족도를 감소시켜준다고 한다.
        // 순서대로 서빙을 한다고 할 때
        // 불만족도 합이 m이 넘지 않으며 모든 학생들에게 메뉴를 서빙하려면 최소 몇 개의
        // 원하는 메뉴를 준비해야할까?
        //
        // 그리디 문제
        // 순서대로 서빙을 하며 불만족도가 m보다 같거나 커져서는 안된다.
        // 따라서 순서대로 불만족도를 쌓되, 원하는 메뉴는 여태까지 서빙했던 학생들 중
        // 가장 많은 불만족도를 낮출수 있는 학생에게로 바꿔가며 모든 학생들에게 서빙하도록 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, 불만족도 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 학생의 불만족도
        int[] discontents = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 불만족도 합
        long sum = 0;
        // 준비해야하는 원하는 메뉴의 수
        int count = 0;
        // 우선순위큐를 통해 여태 서빙했던 학생들의 불만족도를
        // 내림차순 정렬하여 큰 값부터 우선적으로 취급한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int discontent : discontents) {
            // 이번 학생에게 원하지 않는 메뉴를 우선 지급한다.
            sum += discontent;
            // 우선순위큐에 추가
            priorityQueue.offer(discontent);
            // 만약 불만족도가 m보다 같거나 커졌다면
            // 여태 음식을 지급한 학생들 중 불만족도를 가장 많이 낮출 수 있는 학생에게
            // 원하는 메뉴로 교체해주며 불만족도를 낮춘다.
            while (!priorityQueue.isEmpty() && sum >= m) {
                sum -= priorityQueue.poll() * 2;
                count++;
            }
        }

        // 최종적으로 메뉴를 교체해준 횟수를 출력한다.
        System.out.println(count);
    }
}