package 여행가자;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static int[] parent;
    static int[] ranks;

    public static void main(String[] args) {
        // union-find 로 해결 가능.
        // 서로 연결이 된 곳은 하나의 집합으로 표시.
        // 그리고 다른 지점으로 이동할 떄 같은 집합인지 확인.
        // 집합이 아니라면 이동 불가.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] loadMatrix = new int[n][n];
        for (int i = 0; i < loadMatrix.length; i++) {
            for (int j = 0; j < loadMatrix[i].length; j++)
                loadMatrix[i][j] = sc.nextInt();
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < m; i++)
            queue.add(sc.nextInt() - 1);

        parent = new int[n];
        ranks = new int[n];
        makeSet();

        for (int i = 0; i < loadMatrix.length; i++) {
            for (int j = 0; j < loadMatrix[i].length; j++) {    // 연결이 되어있다면 두 집합을 하나의 집합으로 합침.
                if (loadMatrix[i][j] == 1)
                    union(i, j);
            }
        }

        boolean possible = true;
        int current = queue.poll();
        while (!queue.isEmpty()) {
            int next = queue.poll();
            if (findParent(current) == findParent(next))    // current와 next가 같은 집합인지 확인.
                current = next;
            else {      // 아니라면 여행 불가.
                possible = false;
                break;
            }
        }
        System.out.println(possible ? "YES" : "NO");
    }

    static void makeSet() {     // 각자 각각의 집합으로 표시
        for (int i = 0; i < parent.length; i++)
            parent[i] = i;
    }

    static int findParent(int n) {      // 어느 집합에 속해있는지 표시
        if (parent[n] == n)
            return n;
        parent[n] = findParent(parent[n]);
        return parent[n];
    }

    static void union(int a, int b) {   // 두 집합을 하나의 집합으로 합침.
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] > ranks[pb])
            parent[pb] = pa;
        else {
            parent[pa] = pb;
            if (ranks[pa] == ranks[pb])
                ranks[pb]++;
        }
    }
}