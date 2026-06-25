/*
 Author : Ruel
 Problem : Jungol 3982번 폭격 (Small)
 Problem address : https://jungol.co.kr/problem/3982
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3982_폭격_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks, members;

    public static void main(String[] args) throws IOException {
        // n개의 도시가 m개의 도로로 연결되어있다.
        // 적국에서 n개의 도시를 차례대로 폭격한다. 그 때마다, 해당 도시와 연결된 도로들이 모두 파괴된다.
        // 각 턴마다 폭격 받지 않은 모든 도시가 직간접적으로 연결되어있는지를 출력하라
        // 폭격 받지 않은 모든 도시의 연결 여부부터 출력한다.
        //
        // 분리 집합, 오프라인 쿼리 문제
        // 역순으로, 모든 도시가 폭격받은 시점부터 시작하여, 도시를 하나씩 복구해나가며
        // 연결된 도시들을 한 집합으로 묶어, 해당 집합에 속한 도시의 수가 턴의 수와 같은지 확인하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 도로
        List<List<Integer>> roads = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            roads.get(x).add(y);
            roads.get(y).add(x);
        }

        // 분리 집합을 위한 배열들
        parents = new int[n + 1];
        ranks = new int[n + 1];
        members = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parents[i] = i;
            members[i] = 1;
        }

        // 폭격을 역순으로 처리하여 도시를 건설해나간다
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++)
            stack.push(Integer.parseInt(br.readLine()));

        Stack<Boolean> answer = new Stack<>();
        // 가장 마지막에 폭격받은 도시부터 시작
        int root = stack.pop();
        boolean[] appeared = new boolean[n + 1];
        appeared[root] = true;
        int cnt = 1;
        while (!stack.isEmpty()) {
            // 새로 생기는 도시
            int current = stack.pop();
            // 등장 여부 체크
            appeared[current] = true;

            // current와 연결된 도시들 중 이미 건설이 됐고, current와 다른 집합인 경우
            // 하나의 집합으로 묶는다.
            for (int near : roads.get(current)) {
                if (appeared[near] && findParent(current) != findParent(near))
                    union(current, near);
            }

            // 처음 건설된 도시가 속한 집합의 도시 개수가 턴 수와 같은지 확인하여 stack에 담는다.
            answer.push(members[findParent(root)] == ++cnt);
        }

        // 스택에서 답을 거꾸로 빼내며 답을 기록
        StringBuilder sb = new StringBuilder();
        while (!answer.empty())
            sb.append(answer.pop() ? "YES" : "NO").append("\n");
        // 마지막 하나의 도시만 남았을 땐, 당연히 YES
        sb.append("YES");
        // 답 출력
        System.out.println(sb);
    }

    // a와 b를 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            // 속한 도시 수를 pa에 누적
            members[pa] += members[pb];
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            parents[pa] = pb;
            // 반대로 pb에 누적
            members[pb] += members[pa];
        }
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}