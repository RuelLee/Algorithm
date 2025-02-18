/*
 Author : Ruel
 Problem : Baekjoon 20956번 아이스크림 도둑 지호
 Problem address : https://www.acmicpc.net/problem/20956
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20956_아이스크림도둑지호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 아이스크림이 1번부터 n번까지 순서대로 배치되어있다.
        // 이중 m개의 아이스크림을 먹는데, 가장 양이 많은 아이스크림부터 먹는다.
        // 아이스크림의 양이 7의 배수라면 민트초코맛이라고 판단하고, 아이스크림의 순서를 뒤집는다.
        // 먹은 아이스크림의 번호를 구하라.
        //
        // 데크, 정렬 문제
        // 가만 생각해보면, 아이스크림을 먹는 가장 큰 순서는
        // 양이 많은 아이스크림부터 먹는다.
        // 이는 순서를 뒤집더라도 상관이 없다.
        // 양이 같은 아이스크림이 주어진다면, 이는 순서를 뒤집을 경우, 순서가 바뀐다.
        // 따라서 양이 같은 아이스크림을 데크에 순서대로 저장하고
        // 이를 정순으로 먹는지 역순으로 먹는지를 고려해여 코딩하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 아이스크림 중 m개를 먹는다.
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 아이스크림 양 별로 데크에 구분하여 담는다.
        HashMap<Integer, Deque<Integer>> hashMap = new HashMap<>();
        // 우선순위큐로 양에 따라 내림차순으로 살펴볼 수 있게 한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            // 이번 아이스크림의 양
            int num = Integer.parseInt(st.nextToken());
            // 처음 보는 양이라면
            // 해쉬맵과 우선순위큐에 추가
            if (!hashMap.containsKey(num)) {
                hashMap.put(num, new LinkedList<>());
                priorityQueue.offer(num);
            }
            hashMap.get(num).addLast(i + 1);
        }

        StringBuilder sb = new StringBuilder();
        // 순서
        boolean naturalOrder = true;
        // m개의 아이스크림을 먹는다.
        for (int i = 0; i < m; i++) {
            // 우선순위큐가 비지 않았고, 데크에 아이스크림이 더 이상 담겨 있지 않다면
            // 다음 양에 해당하는 아이스크림으로 넘어간다.
            while (!priorityQueue.isEmpty() && hashMap.get(priorityQueue.peek()).isEmpty())
                priorityQueue.poll();
            
            // 현재 살펴볼 양
            int current = priorityQueue.peek();
            // 정순이라면 앞에서 꺼내고, 역순이라면 뒤에서 꺼낸다.
            sb.append(naturalOrder ? hashMap.get(current).pollFirst() : hashMap.get(current).pollLast()).append("\n");
            // current가 7의 배수라면 순서를 뒤집는다.
            if (current % 7 == 0)
                naturalOrder = !naturalOrder;
        }
        // 답 출력
        System.out.print(sb);
    }
}