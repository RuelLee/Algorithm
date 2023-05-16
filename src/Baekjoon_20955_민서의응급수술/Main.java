/*
 Author : Ruel
 Problem : Baekjoon 20955번 민서의 응급 수술
 Problem address : https://www.acmicpc.net/problem/20955
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20955_민서의응급수술;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 뉴런과 m개의 시냅스가 주어진다.
        // 모든 뉴런들을 트리 형태로 연결하기 위해 시냅스들을 잇거나 끊는 작업을 할 수 있다.
        // 최소 몇 번 작업을 해야 모든 뉴런을 트리 형태로 연결할 수 있는가
        //
        // 분리 집합 문제
        // m개의 시냅스에 대해 양쪽의 뉴런이 서로 다른 집합이라면 하나로 묶는 작업을 진행한다.
        // 만약 양쪽의 뉴런이 이미 같은 집합이라면 사이클이 존재하게 되므로, 해당 시냅스를 끊는 작업을 진행해야한다.
        // 모든 작업을 마친 후엔 현재 존재하는 모든 집합을 세고,
        // 각각의 집합의 한 뉴런들끼리 이어 서로 한 집합으로 만드는 작업들을 행해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 뉴런과 m개의 시냅스
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 집합
        parents = new int[n + 1];
        // 각 집합 트리의 높이
        ranks = new int[n + 1];
        // 초기화
        init();

        int count = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a와 b가 다른 집합이라면 한 집합으로 묶고
            if (findParent(a) != findParent(b))
                union(a, b);
            // 그렇지 않다면 끊는 작업을 진행한다.
            else
                count++;
        }

        // 모든 집합의 수를 세어준다.
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 1; i < parents.length; i++)
            hashSet.add(findParent(i));
        // 모든 집합의 수 - 1만큼의 서로 잇는 작업을 통해 모두 하나의 집합으로 묶어줄 수 있다.
        // 답안 출력
        System.out.println(count + hashSet.size() - 1);
    }

    // a와 b를 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // n 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
    
    // parents 배열 초기화
    static void init() {
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
    }
}