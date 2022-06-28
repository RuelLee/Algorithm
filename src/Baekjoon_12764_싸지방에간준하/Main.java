/*
 Author : Ruel
 Problem : Baekjoon 12764번 싸지방에 간 준하
 Problem address : https://www.acmicpc.net/problem/12764
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12764_싸지방에간준하;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 각 손님들의 pc 이용시간이 주어진다
        // 각 손님들은 비어 있는 pc들 중 가장 작은 번호의 pc를 이용한다.
        // 모든 사람이 기다리지 않고 이용하는데 필요한 pc의 수와 그 때 pc의 이용객의 수를 출력하라.
        //
        // 우선순위큐를 이용한 문제
        // 먼저 손님들은 시작 시간에 따라 오름차순으로 정렬한다
        // 각 컴퓨터는 우선순위큐로 종료시간을 통해 최대힙으로 만든다.
        // 각 이용객들을 순서대로 살펴보며, 해당 이용객이 이용하려는 시점에 비어있는 컴퓨터들을 살펴보며
        // 가장 작은 번호의 컴퓨터를 이용하게끔하자.
        // 반복하며, 각 컴퓨터의 개수, 컴퓨터를 이용한 이용객의 수를 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 이용객의 이용 시작 시간, 종료 시간.
        int[][] users = new int[n][2];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            users[i][0] = Integer.parseInt(st.nextToken());
            users[i][1] = Integer.parseInt(st.nextToken());
        }
        // 시작 시간에 따라 오름차순 정렬한다.
        Arrays.sort(users, Comparator.comparing(arr -> arr[0]));

        // 컴퓨터들을 순서대로 담아둘 리스트.
        List<PriorityQueue<Integer>> list = new ArrayList<>(n);
        // 컴퓨터들 중 종료시간이 이른 컴퓨터들을 찾아줄 우선순위큐.
        PriorityQueue<Integer> indexes = new PriorityQueue<>(Comparator.comparing(idx -> list.get(idx).peek()));
        // 비어있는 컴퓨터들 중 번호가 작은 순으로 보여줄 우선순위큐.
        PriorityQueue<Integer> emptyComIdxes = new PriorityQueue<>();
        // 손님들을 차례대로 살펴본다.
        for (int[] user : users) {
            // 이번 손님의 시작 시간보다 이른 종료 시간을 갖고 있어 빈자리가 된 컴퓨터를 찾고
            // emptyComIdexes에 넣어준다.
            while (!indexes.isEmpty() && list.get(indexes.peek()).peek() < user[0])
                emptyComIdxes.offer(indexes.poll());

            // 만약 빈 컴퓨터가 없다면, 새로운 컴퓨터를 추가하고 빈 컴퓨터에 추가시켜준다.
            if (emptyComIdxes.isEmpty()) {
                PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
                list.add(priorityQueue);
                emptyComIdxes.offer(list.size() - 1);
            }

            // 빈 컴퓨터들 중 가장 작은 컴퓨터를 골라내, 현재 손님의 종료 시간을 집어넣는다.
            list.get(emptyComIdxes.peek()).offer(user[1]);
            // 그 후 다시 해당 번호는 emptyComIdexes에서 꺼내, indexes에 넣어준다.
            indexes.offer(emptyComIdxes.poll());
        }

        StringBuilder sb = new StringBuilder();
        // 최종적으로 list의 size가 필요한 최소 컴퓨터수가 되고
        sb.append(list.size()).append("\n");
        // 각 컴퓨터에 담긴 종료 시간들이 이용한 이용객들의 수가 된다.
        for (PriorityQueue<Integer> integers : list)
            sb.append(integers.size()).append(" ");
        System.out.println(sb);
    }
}