/*
 Author : Ruel
 Problem : Baekjoon 28070번 유니의 편지 쓰기
 Problem address : https://www.acmicpc.net/problem/28070
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28070_유니의편지쓰기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 친구의 복역 기간이 주어진다.
        // 이 중 하나의 달을 정해 친구들에게 편지를 써주려고할 때
        // 가장 많은 친구들에게 편지를 써주는 연도와 달은?
        // 그러한 달이 여러개라면 가장 연도와 달을 출력한다.
        //
        // 스위핑 문제
        // 수가 아닌 문자열로 주어지지만
        // 이 또한 정렬을 통해 스위핑으로 풀 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 친구
        int n = Integer.parseInt(br.readLine());
        
        // 복역 기간
        String[][] days = new String[n][2];
        for (int i = 0; i < days.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < days[i].length; j++)
                days[i][j] = st.nextToken();
        }
        // 입대 시기에 맞춰 배열을 정렬한다.
        Arrays.sort(days, Comparator.comparing(o -> o[0]));

        // 전역 시기를 기준으로 최소힙 우선순위큐를 만든다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparing(o -> days[o][1]));
        // 최대 인원
        int maxSize = 0;
        // 그 때의 시기
        String s = null;
        for (int i = 0; i < days.length; i++) {
            // i번째 친구보다 이른 날 전역하는 친구들은 모두 우선순위큐에서 제거한다.
            while (!priorityQueue.isEmpty() && days[priorityQueue.peek()][1].compareTo(days[i][0]) < 0)
                priorityQueue.poll();
            
            // i번째 친구 추가
            priorityQueue.offer(i);
            // 현재 우선순위큐의 크기가 가장 컸다면
            if (priorityQueue.size() > maxSize) {
                // 현재 크기 기록
                maxSize = priorityQueue.size();
                // i번째 친구의 입대 시기 기록
                s = days[i][0];
            }
        }

        // 기록된 s를 출력한다.
        System.out.println(s);
    }
}