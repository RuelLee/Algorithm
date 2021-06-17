package 별자리만들기;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

class Pos {
    double r;
    double c;

    public Pos(double r, double c) {
        this.r = r;
        this.c = c;
    }
}

class Road {
    int star1;
    int star2;
    double distance;

    public Road(int star1, int star2, double distance) {
        this.star1 = star1;
        this.star2 = star2;
        this.distance = distance;
    }
}

public class Main2 {
    public static void main(String[] args) {
        // 이번에는 prim 알고리즘으로 풀어보자
        // prim 은 각 지점에 도달하기 직전의 지점을 정하는 pi 배열과, 해당 지점에 도달하는 비용을 저장할 costs 배열이 필요하다.
        // 임의이 한 점(시작점)에 비용을 0으로 표시하고, 해당 시작점에서 출발하는 간선들 중 아직 연결되지 않았으며, 최소 비용인 간선을 선택한다.
        // 그리고 해당 간선을 선택해고, 이를 pi 배열과 costs 배열에 반영한다.
        // 이번에 연결된 지점을 포함하여 연결되지 않는 지점에 방문할 수 있는 간선들에 대해 위 사항을 반복한다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Pos[] stars = new Pos[n];
        for (int i = 0; i < stars.length; i++)
            stars[i] = new Pos(sc.nextDouble(), sc.nextDouble());

        int[] pi = new int[stars.length];
        double[] costs = new double[stars.length];
        boolean[] check = new boolean[n];
        Arrays.fill(pi, -1);
        Arrays.fill(costs, Double.MAX_VALUE);
        costs[0] = 0;
        check[0] = true;

        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(((o1, o2) -> Double.compare(o1.distance, o2.distance)));    // 우선순위큐에 삽입 거리가 짧은 순서대로 확인하자.
        addRoads(priorityQueue, 0, stars, check);

        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();

            if (costs[current.star2] > current.distance) {  // 새로운 지점을 연결할 수 있다면
                check[current.star2] = true;
                costs[current.star2] = current.distance;
                pi[current.star2] = current.star1;
                addRoads(priorityQueue, current.star2, stars, check);       // current.star2로 부터 출발할 수 있는 간선들을 추가해준다.
            }
        }
        System.out.println(Arrays.stream(costs).sum());
    }

    static void addRoads(PriorityQueue<Road> priorityQueue, int n, Pos[] stars, boolean[] check) {      // 아직 연결되지 않은 지점 중 n과 연결할 수 있는 간선들을 찾아 우선순위큐에 추가해준다.
        for (int i = 0; i < stars.length; i++) {
            if (i == n || check[i])     // 자신에게 가는 간선이나, 이미 연결된 지점으로 연결되는 간선은 포함하지 않는다.
                continue;
            double distance = Math.sqrt(Math.pow(Math.abs(stars[n].r - stars[i].r), 2) + Math.pow(Math.abs(stars[n].c - stars[i].c), 2));
            priorityQueue.add(new Road(n, i, distance));
        }
    }
}