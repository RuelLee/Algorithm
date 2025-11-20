/*
 Author : Ruel
 Problem : Baekjoon 17469번 트리의 색깔과 쿼리
 Problem address : https://www.acmicpc.net/problem/17469
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17469_트리의색깔과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parents;
    static List<HashSet<Integer>> members;

    public static void main(String[] args) throws IOException {
        // n개의 정점으로 구성된 트리가 주어진다.
        // 각 노드엔 1 ~ 10만 이하의 자연수로 표현되는 색이 주어진다.
        // 루트 노드인 1을 제외한 각 노드의 부모 노드가 주어진다.
        // 다음 두 쿼리가 주어진다.
        // 1 a : a와 a의 부모 노드 간의 간선을 끊는다.
        // 2 a : a와 연결된 모든 노드들의 색상 가짓수를 출력한다.
        // 1번 쿼리는 총 n-1개, 2번 쿼리는 q개 주어진다.
        //
        // 오프라인 쿼리, 분리 집합 문제
        // 1번 쿼리가 n-1개 주어진다는 것은 결국 모두 단일 노드로 쪼개진다는 것이다.
        // 따라서 쿼리들을 역순으로 처리한다면, 결국 다시 모든 노드가 연결되어 트리형태가 된다는 것을 의미한다.
        // 쿼리들을 역순으로 처리하며, 노드들을 분리 집합으로 같은 집합으로 묶으며 연결하고
        // 그 때 연결된 노드들의 색상 가짓수를 해쉬셋으로 처리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, q개의 2번 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 분리 집합을 위한 초기화
        parents = new int[n + 1];
        members = new ArrayList<>();
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
            members.add(new HashSet<>());
        }
        
        // 각 노드의 부모 노드
        int[] parentNodes = new int[n + 1];
        for (int i = 2; i < parentNodes.length; i++)
            parentNodes[i] = Integer.parseInt(br.readLine());
        // 색상 처리
        for (int i = 0; i < n; i++)
            members.get(i + 1).add(Integer.parseInt(br.readLine()));
        
        // 쿼리들을 일단 모드 입력 받은 후
        int[][] queries = new int[n - 1 + q][2];
        for (int i = 0; i < queries.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < queries[i].length; j++)
                queries[i][j] = Integer.parseInt(st.nextToken());
        }

        // 역순으로 처리한다.
        Stack<Integer> answer = new Stack<>();
        for (int i = queries.length - 1; i >= 0; i--) {
            switch (queries[i][0]) {
                // 1번 쿼리의 경우, a노드와 a의 부모 노드와 연결
                case 1 -> union(queries[i][1], parentNodes[queries[i][1]]);
                // 2번 쿼리인 경우, a노드가 속한 집합의 모든 색상 가짓수를 스택에 담는다.
                case 2 -> answer.push(members.get(findParent(queries[i][1])).size());
            }
        }
        
        // 스택에서 역순으로 꺼내며 답 기록
        StringBuilder sb = new StringBuilder();
        while (!answer.isEmpty())
            sb.append(answer.pop()).append("\n");
        // 답 출력
        System.out.print(sb);
    }

    // a와 b를 한 집합으로 묶는다.
    // 속한 색상의 가짓수가 적은 쪽이 많은 쪽에 속하게끔 한다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (members.get(pa).size() >= members.get(pb).size()) {
            parents[pb] = pa;
            members.get(pa).addAll(members.get(pb));
        } else {
            parents[pa] = pb;
            members.get(pb).addAll(members.get(pa));
        }
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}