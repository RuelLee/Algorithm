package 별자리만들기;

import java.util.PriorityQueue;
import java.util.Scanner;

class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Distance {
    int star1;
    int star2;
    double distance;

    public Distance(int star1, int star2, double distance) {
        this.star1 = star1;
        this.star2 = star2;
        this.distance = distance;
    }
}

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) {
        // 각 별들을 모두 이어줘야한다.
        // 이은 것과 잇지 않은 것으로 나눌 수 있으므로, union-find 를 활용한 kruskal 알고리즘을 활용할 수 있다
        // * prim 알고리즘도 활용할 수 있다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Point[] stars = new Point[n];
        for (int i = 0; i < stars.length; i++)
            stars[i] = new Point(sc.nextDouble(), sc.nextDouble());

        parents = new int[n];
        ranks = new int[n];
        makeSet();

        // 각 별 들간의 거리를 모두 측정해서 거리가 짧은 순서대로 우선순위큐에 넣자.
        PriorityQueue<Distance> queue = new PriorityQueue<>(((o1, o2) -> Double.compare(o1.distance, o2.distance)));
        for (int i = 0; i < stars.length - 1; i++) {
            for (int j = i + 1; j < stars.length; j++) {
                double distance = Math.sqrt(Math.pow(Math.abs(stars[i].x - stars[j].x), 2) + Math.pow(Math.abs(stars[i].y - stars[j].y), 2));
                queue.add(new Distance(i, j, distance));
            }
        }

        double distanceSum = 0;
        int count = 1;
        while (!queue.isEmpty()) {      // 각각의 집단에 속해있는 별들을 모두 한 집단으로 만들어주면 끝난다.
            if (count == stars.length)      // 별들의 갯수만큼 한 집단에 속했다면 끝.
                break;
            Distance current = queue.poll();

            if (findParents(current.star1) != findParents(current.star2)) {     // 두 별이 서로 다른 집단이라면 한 집단에 합쳐준다.
                union(current.star1, current.star2);
                distanceSum += current.distance;
                count++;
            }
        }
        System.out.println(distanceSum);        // 집단을 합칠 때마다 더한 거리의 총합.
    }

    static void makeSet() {
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
    }

    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }

    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[a] > ranks[b])
            parents[pb] = pa;
        else {
            parents[pa] = pb;
            if (ranks[pa] == ranks[pb])
                ranks[pb]++;
        }
    }
}