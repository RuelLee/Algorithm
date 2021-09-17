/*
 Author : Ruel
 Problem : Baekjoon 1202번 보석 도둑
 Problem address : https://www.acmicpc.net/problem/1202
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 보석도둑;

import java.util.PriorityQueue;
import java.util.Scanner;

class Gem {
    int weight;
    int value;

    public Gem(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) {
        // 보석과 가방의 수가 크기 때문에 선형적으로 계산을 할 수 있어야한다
        // 가방을 오름차순, 보석을 무게에 대해 오름차순으로 정렬하자
        // 첫번째 가방보다 무게가 같거나 작은 보석들을 차례로 꺼내 우선순위큐에 담아 가치가 가장 큰 보석을 선택하자
        // 두번째 가방보다 무게가 같거나 작은 보석들을 꺼내, 첫번째 미선택된 보석들이 담긴 우선순위큐에 담고, 그 중 가치가 가장 큰 보석을 선택하자.
        // ... 반복
        // 최종적으로 선택된 보석의 가치의 합을 더해준다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        PriorityQueue<Gem> gems = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.weight, o2.weight));       // 보석을 무게에 오름차순 정렬될 우선순위큐
        for (int i = 0; i < n; i++)
            gems.offer(new Gem(sc.nextInt(), sc.nextInt()));

        PriorityQueue<Integer> bags = new PriorityQueue<>();        // 가방이 오름차순 정렬될 우선순위큐
        for (int i = 0; i < k; i++)
            bags.offer(sc.nextInt());

        PriorityQueue<Gem> availableGems = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.value, o1.value));        // 현재 가방 + 이후 가방들에서 선택될 수 있는 보석들이 담길 우선순위큐
        long sum = 0;
        while (!bags.isEmpty()) {       // 가방이 있는 한
            int bag = bags.poll();

            while (!gems.isEmpty() && gems.peek().weight <= bag)        // 아직 후보에 오르지 않은 보석들이 있고, 그 보석들이 현재 가방보다 무게가 작거나 같다면
                availableGems.offer(gems.poll());           // availableGems 우선순위큐에 담아 후보로 등록시켜주자

            if (!availableGems.isEmpty())       // 후보군이 비어있지않다면
                sum += availableGems.poll().value;      // 그 중 가장 가치가 높은 보석을 꺼내 담는다.
        }
        System.out.println(sum);
    }
}