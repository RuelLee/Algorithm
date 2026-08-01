/*
 Author : Ruel
 Problem : Jungol 5809번 흑역사
 Problem address : https://jungol.co.kr/problem/5809
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5809_흑역사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n명의 사람들이 m개의 모임에 참여한다.
        // 그 중 주인공의 흑역사를 아는 사람의 수 k와 그 번호가 주어진다.
        // 그리고 각 모임에 참여하는 인원과 그 번호들이 주어진다.
        // 한 모임에 흑역사를 아는 사람이 한 명이라도 있다면 그 그룹에는 흑역사가 모두 퍼지게 된다.
        // 그리고 다른 모임에 가서 다시 퍼뜨린다.
        // 주인공의 흑역사를 모르는 모임만 참여하고자 한다면 최대 몇 개를 참여할 수 있는가?
        //
        // 분리 집합 문제
        // 먼저 흑역사를 아는 모든 사람들을 한 그룹으로 묶는다.
        // 그리고 각 모임의 참가원을 모두 한 그룹으로 묶는다.
        // 흑역사를 아는 사람과 한 그룹으로 묶인 참가원이 있는 모임을 배제해나가며 수를 센다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람, m개의 모임
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 분리 집합을 위한 배열 초기화
        parents = new int[n + 1];
        ranks = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;

        // 0번 그룹 : 흑역사를 아는 사람들
        // 그 외 : 각 모임
        List<List<Integer>> groups = new ArrayList<>();
        for (int i = 0; i < m + 1; i++)
            groups.add(new ArrayList<>());

        for (int i = 0; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 0; j < num; j++)
                groups.get(i).add(Integer.parseInt(st.nextToken()));
        }

        // 각 모임을 하나의 그룹으로 묶는다.
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).isEmpty())
                continue;

            int rep = groups.get(i).get(0);
            for (int j = 1; j < groups.get(i).size(); j++) {
                if (findParent(rep) != findParent(groups.get(i).get(j)))
                    union(rep, groups.get(i).get(j));
            }
        }

        // 흑역사를 아는 인원이 포함된 모임의 수를 빼나간다.
        int answer = m;
        if (!groups.get(0).isEmpty()) {
            int black = groups.get(0).get(0);
            for (int i = 1; i <= m; i++) {
                if (findParent(black) == findParent(groups.get(i).get(0)))
                    answer--;
            }
        }
        // 답 출력
        System.out.println(answer);
    }

    // a와 b를 한 그룹으로 묶는다.
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

    // n이 속한 그룹의 대표를 찾는다
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}