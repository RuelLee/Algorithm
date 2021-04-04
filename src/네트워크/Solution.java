package 네트워크;

import java.util.HashSet;

public class Solution {
    static int[] parents;   // 각각이 어느 집단에 속하는지 표시할 int 배열.
    static int[] ranks;     // 각 집단의 자식 노드가 몇단계인지 표시할 배열

    public static void main(String[] args) {
        // union-find 문제

        int n = 4;
        int[][] computers = {
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 1, 1},
                {0, 1, 0, 1}
        };

        parents = new int[n];
        ranks = new int[n];

        makeSet();

        for (int i = 0; i < computers.length; i++) {
            for (int j = 0; j < computers[i].length; j++) {
                if (i != j && computers[i][j] == 1)
                    union(i, j);
            }
        }
        HashSet<Integer> hashSet = new HashSet<>();

        for (int i = 0; i < n; i++)
            hashSet.add(findSet(i));

        System.out.println(hashSet.size());
    }

    static void makeSet() {
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
    }

    static int findSet(int n) {     // 집단 대표를 반환.
        if (n == parents[n])
            return n;
        parents[n] = findSet(parents[n]);
        return parents[n];
    }

    static void union(int n, int m) {   // 같은 집단으로 만듦.
        int sn = findSet(n);
        int sm = findSet(m);

        if (ranks[sn] > ranks[sm])
            parents[sm] = sn;
        else {
            parents[sn] = sm;
            if (ranks[sn] == ranks[sm])
                ranks[sm]++;
        }
    }
}