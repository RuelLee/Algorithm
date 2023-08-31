/*
 Author : Ruel
 Problem : Baekjoon 17250번 은하철도
 Problem address : https://www.acmicpc.net/problem/17250
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17250_은하철도;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;
    static long[] stars;

    public static void main(String[] args) throws IOException {
        // n개의 은하와 각 은하에 속한 행성의 수가 주어진다.
        // 은하 간에 철도가 연결되면 각 은하에 속한 행성들 간의 여행이 가능해진다.
        // m개의 은하 철도가 건설된다할 때
        // 각 철도가 연결될 때마다, 해당 철도를 이용할 수 있는 행성들의 수를 출력하라
        //
        // 분리 집합 문제
        // 은하의 개수와 행성의 개수를 갖고서 분리 집합을 구현한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 은하와 m개의 은하 철도
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 은하 철도가 연결된 그룹의 대표 은하
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        // 각 은하에 속한 행성의 수
        stars = new long[n + 1];
        for (int i = 1; i < stars.length; i++)
            stars[i] = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // a은하와 b은하에 은하 철도를 건설한다.
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // 두 은하가 서로 다른 집합이라면
            // (= 철도로 연결되어있지 않다면)
            // 연결하고
            if (findParent(a) != findParent(b))
                union(a, b);
            // 두 은하가 속한 대표 은하를 찾아
            // 총 속한 행성의 수를 기록한다.
            sb.append(stars[findParent(a)]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // a와 b 은하를 묶는다.
    // = 은하 철도를 건설한다.
    static void union(int a, int b) {
        // 두 은하의 대표 은하를 찾아
        int pa = findParent(a);
        int pb = findParent(b);
        
        // 랭크를 서로 비교하여
        // 랭크가 더 높은 쪽에 작은 쪽을 속하게 한다.
        // 이 때 행성의 수는 포함하는 쪽에 포함되는 쪽의 수를 더한다.
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            stars[pa] += stars[pb];
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            parents[pa] = pb;
            stars[pb] += stars[pa];
        }
    }

    // n 은하와 은하 철도로 연결된 은하들 중 대표 은하를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}
