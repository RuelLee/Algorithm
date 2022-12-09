/*
 Author : Ruel
 Problem : Baekjoon 18116번 로봇 조립
 Problem address : https://www.acmicpc.net/problem/18116
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18116_로봇조립;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] ranks;
    static int[] counts;
    static int MAX_NUMBER = (int) Math.pow(10, 6);

    public static void main(String[] args) throws IOException {
        // 1 ~ 10^6 까지의 부품들이 주어진다
        // 부품이 속한 로봇은 robot(i)라고 표현하며, 서로 다른 로봇은 부품을 공유하지 않는다.
        // 2종류의 명령이 둘어온다.
        // 1. 서로 다른 두 부품을 말하며 같은 로봇의 부품이라는 정보를 준다.
        // 2. 부품 i에 대해 지금까지 알아낸 robot(i)의 부품이 몇 개인지 물어본다.
        //
        // 분리 집합 문제
        // 부품이 100만개이므로, 2번 명령이 들어왔을 때 일일이 모두 세기엔 부담이 크다.
        // 따라서 union 연산을 할 때, 두 집합에 대표로 선정되지 않은 집합에 속한 부품의 수를
        // 집합으로 선정된 부품의 수에 더해주도록하자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 분리집합 연산을 위한 초기 세팅.
        // 처음에는 모두 각각의 집합으로 세팅.
        parents = new int[MAX_NUMBER + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        // 연산을 줄이기 위해 트리의 높이를 활용한다.
        ranks = new int[MAX_NUMBER + 1];
        // 각 집합에 속한 부품의 수.
        counts = new int[MAX_NUMBER + 1];
        Arrays.fill(counts, 1);

        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            switch (st.nextToken()) {
                // 1번 명령일 때.
                case "I" -> {
                    int a = Integer.parseInt(st.nextToken());
                    int b = Integer.parseInt(st.nextToken());
                    // 서로 다른 집합을 갖고 있다면 하나의 집합으로 합치낟.
                    if (findParent(a) != findParent(b))
                        union(a, b);
                }
                // 2번 쿼리일 경우.
                // c의 집합의 대표자를 찾고, 해당 집합에 속한 부품의 수를 출력한다.
                case "Q" -> sb.append(counts[findParent(Integer.parseInt(st.nextToken()))]).append("\n");
            }
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }

    // 두 집합을 하나의 집합으로 합친다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        // 높이를 고려하여, 높이 낮은 집합을 높은 집합으로 합친다.
        // pa 집합의 높이가 pb보다 높을 경우.
        if (ranks[pa] >= ranks[pb]) {
            // 합치기 전에, pb 집합원 속한 개수를 pa 집합원 개수에 더해준다.
            counts[pa] += counts[pb];
            // pb를 pa의 집합에 합친다.
            parents[pb] = pa;
            // 높이가 같다면, pa의 높이 증가.
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            // pb 집합의높이가 더 높은 경우.
            counts[pb] += counts[pa];
            parents[pa] = pb;
        }
    }

    // 집합의 대표자를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        // 차후 집합의 대표자를 찾을 때 연산을 줄이기 위해
        // 경로 단축.
        return parents[n] = findParent(parents[n]);
    }
}