/*
 Author : Ruel
 Problem : Baekjoon 1774번 우주신과의 교감
 Problem address : https://www.acmicpc.net/problem/1774
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 우주신과의교감;

import java.util.PriorityQueue;
import java.util.Scanner;

class Link {
    int a;
    int b;
    double cost;

    public Link(int a, int b, double cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }
}

public class Main {
    static int[] ranks;
    static int[] parents;

    public static void main(String[] args) {
        // Kruskal로 풀어야하는 문제.
        // prim으로 풀다가 이미 연결된 간선이 서로 다른 그룹에 있는 경우를 처리하다 틀릴 수 있다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        ranks = new int[n];
        parents = new int[n];

        int[][] gods = new int[n][2];
        for (int i = 0; i < n; i++) {
            gods[i][0] = sc.nextInt();
            gods[i][1] = sc.nextInt();
        }

        makeSet();
        for (int i = 0; i < m; i++)
            union(sc.nextInt() - 1, sc.nextInt() - 1);

        PriorityQueue<Link> priorityQueue = new PriorityQueue<>(((o1, o2) -> Double.compare(o1.cost, o2.cost)));
        // 간선 간의 가중치가 주어지지 않고, 좌표로 일일이 모두 구해야한다.
        for (int i = 0; i < gods.length; i++) {
            for (int j = i + 1; j < gods.length; j++)
                priorityQueue.add(new Link(i, j, getCost(gods[i], gods[j])));
        }

        double costSum = 0;
        while (!priorityQueue.isEmpty()) {
            Link current = priorityQueue.poll();
            if (findParents(current.a) == findParents(current.b))
                continue;
            union(current.a, current.b);
            costSum += current.cost;
        }
        System.out.printf("%.2f", costSum);
    }

    static void makeSet() {
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
    }

    // 부모를 찾아가며 다음 번엔 재귀과정을 생략할 수 있도록 parents[n]의 값도 갱신해주자.
    static int findParents(int n) {
        if (n == parents[n])
            return n;
        return parents[n] = findParents(parents[n]);
    }

    // 두 신의 그룹을 하나로 묶자.
    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] > ranks[pb])
            parents[pb] = pa;
        else {
            parents[pa] = pb;
            if (ranks[pa] == ranks[pb])
                ranks[pb]++;
        }
    }

    // 두 신 간의 거리를 계산. 제곱의 경우 int 범위를 넘을 수 있으므로, long 값으로 바꿔서 계산을 해야한다.
    static double getCost(int[] godA, int[] godB) {
        return Math.sqrt(Math.pow((long) godA[0] - godB[0], 2) + Math.pow((long) godA[1] - godB[1], 2));
    }
}