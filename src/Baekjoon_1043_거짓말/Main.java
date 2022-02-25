/*
 Author : Ruel
 Problem : Baekjoon 1043번 거짓말
 Problem address : https://www.acmicpc.net/problem/1043
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1043_거짓말;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // 주인공은 파티에 참석하여 이야기를 과장하여 말하는 걸 좋아한다고 한다
        // 전체 사람의 수 n, 파티의 수 m이 주어지고
        // 진실을 아는 사람들의 수와 그 정보 그리고 각 파티에 참석하는 사람들이 주어진다
        // 그리고 진실을 아는 사람은 자신이 아는 이야기를 같은 파티에 참석했던 사람들에게 말하며,
        // 이렇게 진실을 아는 사람이 있는 파티에서 주인공은 이야기를 과장해서 말하면 안된다.
        // 주인공은 최대 몇 개의 파티에서 이야기를 과장하여 말할 수 있는가?
        // union find를 통한 분리집합 문제
        // 진실을 아는 사람과 같은 파티에 참석했던 사람들을 한 그룹으로 묶어, 진실을 아는 사람들로 표시한다
        // 그 후, 파티마다 한명씩 진실을 아는 그룹에 포함되어있는지 살펴보고 아니라면 해당 파티에서 과장되게 말해도 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        init(n);

        st = new StringTokenizer(br.readLine());
        int numOfPeopleWhoKnowTruth = Integer.parseInt(st.nextToken());
        for (int i = 0; i < numOfPeopleWhoKnowTruth; i++)       // 0번 사람은 없으므로, 진실을 아는 사람들을 0번에 묶는다고 생각하도록 하자.
            union(0, Integer.parseInt(st.nextToken()));

        List<List<Integer>> partyAttendant = new ArrayList<>();
        for (int i = 0; i < m; i++)
            partyAttendant.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int numOfAttendants = Integer.parseInt(st.nextToken());
            partyAttendant.get(i).add(Integer.parseInt(st.nextToken()));
            // 한 파티에 참석한 사람들을 한 그룹으로 묶어준다.
            // 그 중 한 명이 진실은 안다면 진실을 아는 그룹으로 묶일 것이다.
            // 차후에 이 사람이 진실을 아는 사람과 같은 파티에 참석한다면 역시 이 사람이 참석했던 파티에
            // 연관된 모든 사람이 진실을 아는 그룹으로 묶일 것이다.
            for (int j = 0; j < numOfAttendants - 1; j++) {
                partyAttendant.get(i).add(Integer.parseInt(st.nextToken()));
                union(partyAttendant.get(i).get(0), partyAttendant.get(i).get(j + 1));
            }
        }

        int count = 0;
        for (List<Integer> party : partyAttendant) {        // 각 파티 참석원 중 한명만 골라 0(진실을 아는)과 같은 그룹인지 체크한다.
            if (findParents(party.get(0)) != findParents(0))        // 아니라면 과장되게 말해도 되는 파티
                count++;
        }
        System.out.println(count);
    }

    static void init(int n) {
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
    }

    static void union(int a, int b) {       // a와 b를 같은 그룹으로 묶어준다.
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

    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }
}