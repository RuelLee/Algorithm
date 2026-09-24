/*
 Author : Ruel
 Problem : Jungol 2534번 갱스
 Problem address : https://jungol.co.kr/problem/2534
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2534_갱스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n명의 갱스터와 각 갱스터 간의 관계가 주어진다.
        // E p q : 서로 적대 관계이다.
        // F p q : 서로 친구 관계이다.
        // 친구의 친구는 친구이며, 적의 적 또한 친구이다.
        // 혼자 혹은 친구끼리 갱을 이룬다고 할 때, 갱의 수는?
        //
        // 분리 집합 문제
        // 친구 끼리는 한 그룹, 적의 적 관계를 한 그룹으로 묶는 작업을 한 후
        // 총 그룹의 수를 세면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 갱스터, m개의 관계
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        // 분리 집합
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];

        // 적대 관계
        List<List<Integer>> enemies = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            enemies.add(new ArrayList<>());

        StringTokenizer st;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 적대 관계인 경우, 저장
            if (st.nextToken().charAt(0) == 'E') {
                int p = Integer.parseInt(st.nextToken());
                int q = Integer.parseInt(st.nextToken());

                enemies.get(p).add(q);
                enemies.get(q).add(p);
            } else {        // 친구 관계인 경우. 한 그룹으로 묶음
                int p = Integer.parseInt(st.nextToken());
                int q = Integer.parseInt(st.nextToken());

                if (findParent(p) != findParent(q))
                    union(p, q);
            }
        }

        // 적의 적을 찾아 친구로 묶는다.
        for (int i = 1; i < n + 1; i++) {
            for (int enemy : enemies.get(i)) {
                for (int eoe : enemies.get(enemy)) {
                    if (findParent(i) != findParent(eoe))
                        union(i, eoe);
                }
            }
        }

        // 그룹의 수를 센다.
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 1; i < n + 1; i++)
            hashSet.add(findParent(i));
        // 답 출력
        System.out.println(hashSet.size());
    }

    // a가 속한 그룹과 b가 속한 그룹을 하나로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            parents[pa] = pb;
            ranks[pb]++;
        }
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}