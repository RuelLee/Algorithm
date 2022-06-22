/*
 Author : Ruel
 Problem : Baekjoon 17398번 통신망 분할
 Problem address : https://www.acmicpc.net/problem/17398
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17398_통신망분할;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] groupMembers;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 통신탑이 주어지며, m개의 연결이 주어진다.
        // 이 때 q개의 연결을 제거함으로써 통신망을 분할하려 한다
        // 이 때의 비용은 통신망이 서로 다른 통신망들로 나뉘어지게 된다면, 각각 통신망에 속한 통신탑들의 갯수의 곱이 된다.
        // 전체 비용을 구하라
        //
        // 분리 집합 문제
        // 라는 점과 오프라인 쿼리가 먼저 떠올랐다.
        // 끊어지지 않는 연결들을 먼저 연결하고 나서
        // 끊어지는 연결들을 역순으로 처리해나간다
        // 만약 끊어지는 연결이 서로 다른 집합을 갖고 있다면 해당 연결이 끊어짐으로써
        // 통신망이 분리되며 비용이 발생한다. 이 때의 비용을 계산하고, 역순으로 진행중이므로끊어지는 두 통신탑을 연결시켜준다.
        // 위와 같은 내용을 모든 끊어지는 연결에 대해 반복한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 통신탑들 간의 연결들.
        int[][] connections = new int[m][2];
        for (int i = 0; i < connections.length; i++) {
            st = new StringTokenizer(br.readLine());
            connections[i][0] = Integer.parseInt(st.nextToken());
            connections[i][1] = Integer.parseInt(st.nextToken());
        }

        // 끊어지는 연결들을 역순으로 처리하기 위해 스택에 담는다.
        Stack<Integer> stack = new Stack<>();
        // 안 끊어지는 연결들을 선처리하기 위해, 끊어질 연결들을 표시해둔다.
        boolean[] willDelete = new boolean[m];
        for (int i = 0; i < q; i++) {
            int a = Integer.parseInt(br.readLine()) - 1;
            stack.push(a);
            willDelete[a] = true;
        }

        init(n);
        // 안 끊어지는 연결들은 모두 같은 집합으로 묶어준다.
        for (int i = 0; i < connections.length; i++) {
            int x = connections[i][0];
            int y = connections[i][1];

            if (!willDelete[i] && findParent(x) != findParent(y))
                union(x, y);
        }

        long sum = 0;
        // 스택에서 꺼내며 역순으로 연결 제거를 처리한다.
        while (!stack.isEmpty()) {
            // x 송신탑과
            int x = connections[stack.peek()][0];
            // y 송신탑
            int y = connections[stack.pop()][1];
            // 그리고 각각의 송신탑이 속한 그룹
            int px = findParent(x);
            int py = findParent(y);

            // 서로 다른 그룹을 갖고 있다면
            // 이번 연결 제거를 통해 서로 다른 통신망으로 분리된 것이다.
            // 비용을 계산해서 더해주고, 두 송신탑을 연결해 하나의 통신망으로 만들어준다.
            if (px != py) {
                sum += (long) groupMembers[px] * groupMembers[py];
                union(x, y);
            }
        }
        // 최종 비용 출력.
        System.out.println(sum);
    }

    // union 연산. 하나의 그룹으로 묶는다.
    // union 연산마다 각 그룹의 멤버수도 같이 계산해준다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            // pb그룹이 pa그룹에 속하게 됐으므로
            // pa 멤버 수에 pb 멤버 수를 더해준다.
            groupMembers[pa] += groupMembers[pb];
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            parents[pa] = pb;
            // pa그룹이 pb그룹에 속하게 됐으므로
            // pb 멤버 수에 pa 멤버 수를 더해준다.
            groupMembers[pb] += groupMembers[pa];
        }
    }

    // 그룹의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }

    // 초기화.
    static void init(int n) {
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        groupMembers = new int[n + 1];
        Arrays.fill(groupMembers, 1);
        ranks = new int[n + 1];
    }
}