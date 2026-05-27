/*
 Author : Ruel
 Problem : Jungol 1863번 종교
 Problem address : https://jungol.co.kr/problem/1863
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1863_종교;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n명의 학생들이 각각 자신이 믿는 종교가 주어진다.
        // m개의 서로 같은 종교를 믿는 학생 쌍이 주어진다.
        // 가능한 가장 많은 종교의 가짓수는?
        //
        // 분리 집합 문제
        // 분리 집합을 통해 같은 종교 쌍에 대해 서로 묶어나간다.
        // 최후에 모든 인원에 대해 각각이 믿는 종교의 대표를 해쉬셋에 저장하여
        // 그 크기를 출력한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람, m개의 학생 쌍
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 인원이 믿는 집합의 대표
        parents = new int[n + 1];
        for (int i = 1; i <= n; i++)
            parents[i] = i;
        // 연산을 줄이기 위해, rank가 적은 집합을 더 큰 집합에 속하게 한다.
        ranks = new int[n + 1];

        // 학생 쌍
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            // x와 y가 서로 다른 종교로 표시되어있다면 하나의 종교로 바꿔준다.
            if (findParent(x) != findParent(y))
                union(x, y);
        }

        // 각 인원이 믿는 종교의 대표를 해쉬셋에 저장한다.
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 1; i <= n; i++)
            hashSet.add(findParent(i));
        // 해쉬셋의 크기 출력
        System.out.println(hashSet.size());
    }

    // a와 b를 하나의 집합으로 묶는다
    static void union(int a, int b) {
        // 각 집합의 대표를 찾아
        int pa = findParent(a);
        int pb = findParent(b);

        // ranks를 비교해, 더 큰 랭크 집합에 작은 랭크 집합이 속하도록 한다.
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}