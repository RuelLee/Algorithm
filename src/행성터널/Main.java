/*
 Author : Ruel
 Problem : Baekjoon 2887번 행성 터널
 Problem address : https://www.acmicpc.net/problem/2887
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 행성터널;

import java.util.PriorityQueue;
import java.util.Scanner;

class Tunnel {
    int from;
    int to;
    int distance;

    public Tunnel(int from, int to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
}

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) {
        // 간선이 막연히 많구나라는 생각으로 prim 알고리즘으로 풀어서 메모리 초과를 맞았다.
        // 간선이 적당히 많은 것이 아니라 정점이 10만개이므로, 10만 C 2 갯수만큼이나 많다!
        // 문제에 거리에 대한 접근을 달리해야한다.
        // 행성 간의 거리는 x, y, z 값의 차이 중 작은 값을 거리로 한다!
        // 따라서 각 행성을 x순, y순, z순으로 정렬한 후, 두 행성 간의 거리를 축마다 각각 구해준다.
        // 이럴 때 나오는 총 거리의 순은 (10만 - 1) * 3개로 10만 C 2보다 훨씬 적다!
        // 이를 바탕으로 union-find 를 이용해 최소 스패닝 트리를 구하자!

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] stars = new int[n][3];
        init(n);

        for (int i = 0; i < n; i++) {
            stars[i][0] = sc.nextInt();
            stars[i][1] = sc.nextInt();
            stars[i][2] = sc.nextInt();
        }

        PriorityQueue<Tunnel> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.distance, o2.distance));   // 전체 거리를 입력 받을 우선순위큐.
        for (int i = 0; i < 3; i++) {
            PriorityQueue<Tunnel> TempPriorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.distance, o2.distance));   // 축마다 좌표 순으로 정렬할 우선순위큐

            for (int j = 0; j < n; j++)
                TempPriorityQueue.add(new Tunnel(0, j, stars[j][i]));       // Tunnel의 to와 distance만 사용하여, 각 행성의 축의 좌표를 입력 받는다.

            Tunnel pre = TempPriorityQueue.poll();
            while (!TempPriorityQueue.isEmpty()) {          // 인접한 행성 들의 거리를 축마다 계산한 후, 통합우선순위큐에 담아주자.
                Tunnel current = TempPriorityQueue.poll();
                priorityQueue.add(new Tunnel(pre.to, current.to, Math.abs(pre.distance - current.distance)));
                pre = current;
            }
        }

        long sum = 0;       // 좌표가 약 -10억부터 10억까지 주어지므로, int의 범위를 넘는다! 따라서 long으로 계산해주자.
        while (!priorityQueue.isEmpty()) {
            Tunnel current = priorityQueue.poll();

            if (findParents(current.from) != findParents(current.to)) {
                union(current.from, current.to);        // 두 점이 한 그룹으로 연결되지 않았다면 union 연산을 하자.
                sum += current.distance;            // 이 때의 거리를 누적해주자.
            }
        }
        System.out.println(sum);        // 누적된 거리가 답!
    }

    static void init(int n) {
        ranks = new int[n];
        parents = new int[n];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
    }

    static int findParents(int n) {
        if (n == parents[n])
            return n;

        return parents[n] = findParents(parents[n]);
    }

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
}