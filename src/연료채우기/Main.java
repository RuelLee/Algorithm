/*
 Author : Ruel
 Problem : Baekjoon 1826번 연료 채우기
 Problem address : https://www.acmicpc.net/problem/1826
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 연료채우기;

import java.util.PriorityQueue;
import java.util.Scanner;

class GasStation {
    int loc;
    int oil;

    public GasStation(int loc, int oil) {
        this.loc = loc;
        this.oil = oil;
    }
}

public class Main {
    public static void main(String[] args) {
        // 우선순위큐를 활용한 문제
        // BFS나, DFS를 활용하여 풀려고 했으나, 메모리 초과.
        // DP를 활용하여 풀려고 했으나, 역시 주유소의 개수가 많아 메모리 초과.
        // 두 개의 우선순위큐를 이용하여 푸는 문제였다.
        // 먼저 주유소를 거리에 따라 오름차순으로 우선순위 큐에 넣고
        // 현재 위치와 기름으로 갈 수 있는 거리 내에 있는 주유소의 기름을 우선순위큐에 내림차순으로 담아준다.
        // 일단 현재 위치와 기름으로 갈 수 있는 거리를 간다
        // 그 후 기름 우선순위 큐에 지나온 주유소들의 기름을 담아준다
        // 다음 주유소에 도착할 수 있을 만큼의 기름을 우선순위큐에서 꺼내가며 거리에 더해준다.
        // 반복하여 최종적으로 마을에 도착했을 때, 몇 번 주유했는지 출력해주면 된다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        PriorityQueue<GasStation> gasStations = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.loc, o2.loc));       // 거리에 따라 주유소를 정렬해줄 우선순위큐
        for (int i = 0; i < n; i++)
            gasStations.offer(new GasStation(sc.nextInt(), sc.nextInt()));

        int village = sc.nextInt();
        int loc = sc.nextInt();

        PriorityQueue<Integer> canFuelOil = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));       // 현재 주유할 수 있는 주유소를 표시할 우선순위큐
        int count = 0;
        while (!gasStations.isEmpty() && loc >= gasStations.peek().loc) {           // 아직 남은 주유소가 있고, 현재 위치가 아직 계산 안된 주유소를 지나쳐왔을 때
            while (!gasStations.isEmpty() && gasStations.peek().loc <= loc)     // 현재 위치보다 같거나 작은 주유소들의 기름을 canFuelOil 우선순위큐에 담아준다.
                canFuelOil.offer(gasStations.poll().oil);
            while (!canFuelOil.isEmpty() && !gasStations.isEmpty() && loc < gasStations.peek().loc) {       // 다음 주유소의 위치까지 필요한 기름을 큰 순서대로 더해준다.
                loc += canFuelOil.poll();
                count++;
            }
        }
        while (!canFuelOil.isEmpty() && loc < village) {        // 현재 위치로부터 마을까지 도달하는데 필요한 기름을 선택하며 주유했다고 생각하자
            loc += canFuelOil.poll();
            count++;
        }
        System.out.println(loc < village ? -1 : count);         // 만약 loc이 village에 도달하지 못했다면, 불가능한 경우. 아니라면 count 회수만큼 주유를 해서 도착한 경우.
    }
}