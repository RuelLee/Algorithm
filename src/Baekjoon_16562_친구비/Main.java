/*
 Author : Ruel
 Problem : Baekjoon 16562번 친구비
 Problem address : https://www.acmicpc.net/problem/16562
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16562_친구비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] costs;
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n명의 친구, m개의 친구 관계, k원이 주어진다
        // 주인공은 친구비를 내면 친구 관계를 얻을 수 있으며, 친구의 친구는 주인공의 친구이다.
        // n명을 모두 친구로 만드는데 드는 비용을 출력하라. k원으로 모두 친구로 만들 수 없다면 Oh no를 출력한다
        //
        // 분리 집합 문제
        // 최대한 적은 집합으로 n명을 묶고, 각 집합에서 가장 싼 친구비를 합산하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        costs = new int[n + 1];     // 각 친구의 친구 비
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < costs.length; i++)
            costs[i] = Integer.parseInt(st.nextToken());

        parents = new int[n + 1];       // 각 친구의 집합
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (findParents(a) != findParents(b)) {     // a와 b의 집합이 다르다면
                // 두 집합 중 더 싼 친구비를 저장해두었다가
                int minCost = Math.min(costs[findParents(a)], costs[findParents(b)]);
                union(a, b);
                // 하나의 집합으로 묶인 집합의 친구비로 저장해준다.
                costs[findParents(a)] = minCost;
            }
        }

        int sum = 0;
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 1; i < costs.length; i++) {
            // 집합 마다 한번씩만 친구비를 내면 된다
            // 계산한 적이 없는 집합이라면
            if (!hashSet.contains(findParents(i))) {
                // 그 때 해당 집합의 최소 친구비를 더하고
                sum += costs[findParents(i)];
                // 해쉬셋에 표시해준다.
                hashSet.add(findParents(i));
            }
        }
        // k원 이하라면 가격을, 이상이라면 Oh no를 출력한다.
        System.out.println(sum <= k ? sum : "Oh no");
    }

    // 각 친구가 속한 집합을 반환한다.
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        // 경로 단축.
        return parents[n] = findParents(parents[n]);
    }

    // 두 집합을 하나의 집합으로 합친다.
    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        // 연산을 줄여주기 위해 더 적은 rank를 갖는 집합을 큰 rank를 갖는 집합에 속하게 한다.
        if (ranks[pa] < ranks[pb])
            parents[pa] = pb;
        else {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        }
    }
}