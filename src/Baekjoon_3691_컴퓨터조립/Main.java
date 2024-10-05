/*
 Author : Ruel
 Problem : Baekjoon 3691번 컴퓨터 조립
 Problem address : https://www.acmicpc.net/problem/3691
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3691_컴퓨터조립;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Part {
    String type;
    int price;
    int quality;

    public Part(String type, int price, int quality) {
        this.type = type;
        this.price = price;
        this.quality = quality;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 부품과 b의 예산으로 컴퓨터를 맞추고자 한다.
        // 컴퓨터의 성능은 모든 부품들 중 최소 quality의 값으로 결정된다.
        // 부품들은 type name price quality 와 같은 형식으로 주어지고
        // 각 type에 속하는 부품은 하나씩 구입해한다.
        // 100개 이하의 테스트케이스가 주어질 때
        // 맞출 수 있는 컴퓨터의 최대 성능을 구하라
        //
        // 해쉬맵, 우선순위큐, 그리디 문제
        // 먼저 부품들을 type에 따라 분류한다.
        // 하되, 가격적으로 이점을 갖는 부품들을 먼저 보기 위해 우선순위큐를 사용하여, 오름차순으로 살펴볼 수 있도록한다.
        // 그리고 모든 부품에 대해 하나씩 컴퓨터에 포함시키며,
        // 컴퓨터에 포함된 부품들은 quality에 따라 오름차순으로 살펴볼 수 있도록 우선순위큐에 담는다.
        // 그 후, 컴퓨터에 부품들 중 가장 quality가 낮은 부품들부터, 다음 부품으로 교체가 가능한지 살펴보며
        // 컴퓨터의 성능을 점차 올려나가며 답을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int t = 0; t < testCase; t++) {
            st = new StringTokenizer(br.readLine());
            // n개의 부품, 예산 b
            int n = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 해쉬맵을 통해 부품둘을 type에 따라 분류하며, 우선순위큐를 사용하여 가격 오르차순으로 살펴볼 수 있도록 한다.
            HashMap<String, PriorityQueue<Part>> hashMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                String type = st.nextToken();
                String name = st.nextToken();
                int price = Integer.parseInt(st.nextToken());
                int quality = Integer.parseInt(st.nextToken());

                if (!hashMap.containsKey(type))
                    hashMap.put(type, new PriorityQueue<>(Comparator.comparingInt(o -> o.price)));

                hashMap.get(type).offer(new Part(type, price, quality));
            }
            
            // 컴퓨터에 사용된 예산
            int sum = 0;
            // 부품들을 quality 오름차순으로 살펴볼 수 있는 우선순위큐
            PriorityQueue<Part> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.quality));
            // 모든 타입의 부품을 하나씩 담는다.
            for (String type : hashMap.keySet()) {
                sum += hashMap.get(type).peek().price;
                priorityQueue.offer(hashMap.get(type).poll());
            }
            
            // 현재 quality가 가장 낮은 부품이 속한 type에 더 이상 살펴볼 다른 제품이 없을 때까지
            while (!hashMap.get(priorityQueue.peek().type).isEmpty()) {
                // 현재 제품을 꺼내고
                Part current = priorityQueue.poll();
                boolean found = false;

                // 현재 제품이 속한 타입에서 더 quality가 높은 제품을 찾는다.
                while (!hashMap.get(current.type).isEmpty()) {
                    Part next = hashMap.get(current.type).poll();
                    // 물론 quality가 더 높아야하지만, 예산이 b를 초과해서는 안된다.
                    if (next.quality > current.quality && sum + next.price - current.price <= b) {
                        // 해당 부품을 찾은 경우, 예산 반영, 우선순위큐 추가
                        sum += (next.price - current.price);
                        priorityQueue.offer(next);
                        found = true;
                        // 반복문 종료
                        break;
                    }
                }
                // 만약 더 높은 quality면서, 예산을 만족하는 부품을 찾지 못한 경우
                // 현재 부품을 다시 담는다.
                if (!found)
                    priorityQueue.offer(current);
            }

            // 우선순위큐에 담긴 가장 낮은 quality의 부품의 quality를 출력한다.
            System.out.println(priorityQueue.peek().quality);
        }
    }
}