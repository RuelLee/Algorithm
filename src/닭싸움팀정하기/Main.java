/*
 Author : Ruel
 Problem : Baekjoon 1765번 닭싸움 팀 정하기
 Problem address : https://www.acmicpc.net/problem/1765
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 닭싸움팀정하기;

import java.util.*;

public class Main {
    static int[] parents;
    static int[] ranks;
    static List<List<Integer>> friends;
    static List<List<Integer>> enemies;

    public static void main(String[] args) {
        // 분리집합문제
        // 나의 친구는 친구이고, 나의 원수의 원수는 친구이다
        // 위 조건에 따라 팀을 나눌 때, 가장 많은 팀으로 나뉘는 팀의 개수를 구하라는 문제
        // union-find를 사용하자!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        init(n);

        StringTokenizer st;
        sc.nextLine();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(sc.nextLine());
            if (st.nextToken().equals("F")) {       // 친구라면
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                friends.get(a).add(b);      // a와 b를 친구로 저장
                friends.get(b).add(a);
            } else {            // 적이라면
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                enemies.get(a).add(b);      // a와 b를 적으로 저장.
                enemies.get(b).add(a);
            }
        }

        for (int i = 1; i < n + 1; i++) {       // 1번부터 n번까지 돌면서
            for (int friend : friends.get(i)) {
                if (findParents(i) != findParents(friend))      // 자신의 친구를 한 그룹으로 저장.
                    union(i, friend);
            }

            for (int enemy : enemies.get(i)) {      // 적의
                for (int friend : enemies.get(enemy)) {     // 적을
                    if (findParents(i) != findParents(friend))          // 친구로 저장.
                        union(i, friend);
                }
            }
        }

        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 1; i < parents.length; i++)        // 1번부터 n번까지 돌면서 각자가 속한 집단을 HashSet에 담아주자.
            hashSet.add(findParents(i));
        System.out.println(hashSet.size());     // 이 때 HashSet의 크기가 최대 팀의 개수.
    }

    static void init(int n) {
        parents = new int[n + 1];
        ranks = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;

        friends = new ArrayList<>();
        enemies = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            friends.add(new ArrayList<>());
            enemies.add(new ArrayList<>());
        }
    }

    static int findParents(int n) {
        if (parents[n] == n)
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