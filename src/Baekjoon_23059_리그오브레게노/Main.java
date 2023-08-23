/*
 Author : Ruel
 Problem : Baekjoon 23059번 리그 오브 레게노
 Problem address : https://www.acmicpc.net/problem/23059
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23059_리그오브레게노;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 아이템 구매에는 순서가 있어, 선행 아이템들을 구매해야만 후행 아이템을 구매할 수 있다.
        // n개 아이템 선후행 관계가 주어지고, 다음과 같은 방법으로 아이템을 구매한다.
        // 1. 현재 구매할 수 있는 아이템들 중 아직 구매하지 않은 아이템을 모두 찾는다.
        // 2. 찾은 아이템을 사전 순으로 모두 구매한다.
        // 모든 아이템을 구매할 때까지 1, 2 반복
        // 이 때의 구매 순서를 출력하라
        //
        // 위상 정렬 문제
        // 서로의 관계들을 가지고서
        // 후행 아이템에 대해 진입차수를 하나씩 증가 시킨다.
        // 그 후, 선행 아이템을 하나씩 구입할 때마다, 후행 아이템의 진입 차수를 하나씩 차감하며
        // 진입 차수가 0이 된 아이템들을 구매해나간다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 진입 차수
        HashMap<String, Integer> inDegrees = new HashMap<>();
        // 선후행 관계
        HashMap<String, List<String>> connections = new HashMap<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            String b = st.nextToken();
            
            // a 아이템이 이전에 등장한 적 없는 아이템이라면
            if (!inDegrees.containsKey(a)) {
                inDegrees.put(a, 0);
                connections.put(a, new ArrayList<>());
            }
            connections.get(a).add(b);
            
            // b 아이템이 이전에 등장한 적 없는 아이템이라면
            if (!inDegrees.containsKey(b)) {
                inDegrees.put(b, 0);
                connections.put(b, new ArrayList<>());
            }
            inDegrees.put(b, inDegrees.get(b) + 1);
        }

        // 진입 차수가 0인 아이템들을 구매한다.
        PriorityQueue<String> nextPriorityQueue = new PriorityQueue<>();
        for (String key : inDegrees.keySet()) {
            if (inDegrees.get(key) == 0)
                nextPriorityQueue.offer(key);
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;
        // 진입 차수가 0인 아이템들
        while (!nextPriorityQueue.isEmpty()) {
            // 위 아이템들을 1차적으로 모두 한번에 구매한다.
            // 따라서 이번 턴에 구매할 아이템과 다음 턴에 구매할 아이템을 구분한다.
            // 이번 턴에 구매할 아이템들
            PriorityQueue<String> priorityQueue = nextPriorityQueue;
            // 다음 턴에 구매할 아이템들
            nextPriorityQueue = new PriorityQueue<>();

            // 이번 턴에 구매할 아이템을 모두 구매한다.
            while (!priorityQueue.isEmpty()) {
                String current = priorityQueue.poll();
                sb.append(current).append("\n");
                count++;

                // 후행 아이템들에 대해 진입 차수를 감소시키고
                // 0이 된다면, 다음 턴에 구매할 아이템 목록에 추가하자.
                for (String next : connections.get(current)) {
                    inDegrees.put(next, inDegrees.get(next) - 1);
                    if (inDegrees.get(next) == 0)
                        nextPriorityQueue.offer(next);
                }
            }
        }

        // 구매한 아이템 개수와
        // 진입 차수에 기록된 아이템 목록을 살펴보고
        // 모두 구매했다면, 아이템 구매 순서를
        // 그러지 않다면 -1을 출력한다.
        System.out.println(count == inDegrees.size() ? sb : -1);
    }
}