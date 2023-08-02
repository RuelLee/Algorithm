/*
 Author : Ruel
 Problem : Baekjoon 15789번 CTP 왕국은 한솔 왕국을 이길 수 있을까?
 Problem address : https://www.acmicpc.net/problem/15789
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15789_CTP왕국은한솔왕국을이길수있을까;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // CTP 왕국과 한솔 왕국은 적대관계이다.
        // 동맹의 동맹은 동맹이 된다.
        // CTP 왕국은 한솔 왕국과 동맹이 아닌 국가들과 k번 동맹을 맺으려 한다.
        // 이 때 동맹국을 최대한 늘리고자할 때, 그 개수는?
        //
        // 분리 집합 문제
        // 분리 집합을 통해 처음에 주어지는 동맹을 하나의 집합으로 묶는다.
        // 그 후, CTP와 한솔 어느 쪽에도 속하지 않는 동맹국들의 집합을 집합원의 수로 내림차순 정렬한 뒤
        // 상위 k개를 동맹국으로 맺는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 국가와
        int n = Integer.parseInt(st.nextToken());
        // m개의 기존 동맹
        int m = Integer.parseInt(st.nextToken());

        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            union(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        st = new StringTokenizer(br.readLine());
        // ctp 왕국 번호
        int c = Integer.parseInt(st.nextToken());
        // 한솔 왕국 번호
        int h = Integer.parseInt(st.nextToken());
        // k번의 동맹 기회
        int k = Integer.parseInt(st.nextToken());

        // 각 그룹의 그룹원 수를 계산한다.
        int[] groupMembers = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            groupMembers[findParent(i)]++;
        
        // 그룹원 수를 내림차순으로 정렬
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 1; i < groupMembers.length; i++) {
            if (groupMembers[i] != 0 &&
                    findParent(i) != h && findParent(i) != c)
                priorityQueue.offer(groupMembers[i]);
        }

        // 상위 k개의 동맹 집합과 동맹을 맺는다.
        int sum = groupMembers[findParent(c)];
        while (!priorityQueue.isEmpty() && k > 0) {
            sum += priorityQueue.poll();
            k--;
        }
        
        // 최대 동맹국의 수 출력
        System.out.println(sum);
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

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] != n)
            parents[n] = findParent(parents[n]);
        return parents[n];
    }
}