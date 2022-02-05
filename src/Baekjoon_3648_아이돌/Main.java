/*
 Author : Ruel
 Problem : Baekjoon 3648번 아이돌
 Problem address : https://www.acmicpc.net/problem/3648
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3648_아이돌;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> relationships;
    static int[] nodeNum;
    static int ancestorCounter;
    static boolean[] visited;
    static boolean[] isSSC;
    static int[] group;
    static int groupCounter;
    static Stack<Integer> stack;

    public static void main(String[] args) throws IOException {
        // n명의 참가자, m명의 심사위원이 주어진다
        // 심사위원은 2표를 행사하는데, 각 표는 한 참가자의 찬성일수도 반대일 수도 있다.
        // 1번 참가자가 프로그램을 조작해 반드시 찬성이 되게하려는데, 심사위원은 자신이 행사한 두 표 모두 결과에 반영되지 않았을 경우 결과를 의심한다고 한다
        // 1번 참가자가 심사위원의 의심을 받지 않고 진출할 수 있는가?
        // 오랜만에 풀어본 2-SAT 문제
        // 2-SAT란 (A1∪A2)∩(B1∪B2)∩(C1∪C2)∩(D1∪D2)∩....∩(Z1∪Z2) 가 참이 되게 만드는 문제.
        // 각 괄호 안에 두 개의 명제 중 하나는 참이 되어야하고, 모든 괄호는 참이 되어야한다
        // 결국 각 알파벳 중 하나는 참이 되어야한다.
        // 이를 대우 명제랑 엮어, ~A1 -> A2, ~A2 -> A1으로 둘러 나눠, 서로 명제 간의 관계를 파악한다
        // 그 후 ~A1 -> ... -> A1이나 A1 -> ... -> ~A1 과 같이 모순되는 상황이 발생하는지 체크해주면 된다.
        // 여기서 강한 연결 요소랑 연결되게 되는데, 각 명제들을 강한 연결 요소로 묶어 같은 그룹에 서로 상반되는 명제가 동시에 존재하는지를 살펴봐주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            init(n);

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                relationships.get(n - a).add(n + b);        // n - a는 ~a, n + b는 b를 나타낸다. 이는 ~a -> b
                relationships.get(n - b).add(n + a);        // n - b는 ~b, n + a는 a를 나타낸다. 이는 ~b -> a
            }
            // 1번 참가자가 진출해야하므로
            // (1∪1)이라는 명제가 하나 더 있다고 생각하자
            // ~1 -> 1이어야한다.
            relationships.get(n - 1).add(n + 1);

            for (int i = 1; i <= n; i++) {
                if (!isSSC[n + i])      // i번 참가자의 찬성에 대한 SSC 검사
                    tarjan(n + i);
                if (!isSSC[n - i])      // i번 참가자의 반대에 대한 SSC 검사
                    tarjan(n - i);
            }

            boolean isPossible = true;
            for (int i = 1; i <= n; i++) {
                if (group[n + i] == group[n - i]) {     // 만약 찬성과 반대가 같은 그룹에 묶여있다면 모순이 발생하여 불가능한 경우.
                    isPossible = false;
                    break;
                }
            }
            sb.append(isPossible ? "yes" : "no").append("\n");
        }
        System.out.println(sb);
    }

    static int tarjan(int node) {       // tarjan 알고리즘. 스택과 DFS를 활용한다.
        int minAncestor = nodeNum[node] = ancestorCounter++;        // 최소 조상 번호와 자신의 번호를 기록.
        visited[node] = true;       // 방문체크
        stack.push(node);       // 스택에 푸쉬.

        for (int next : relationships.get(node)) {
            if (!isSSC[next]) {     // 자신에게 연결된 노드들 중 SSC 형성이 안됐고,
                if (visited[next])      // 방문한 적이 있다면 조상노드 중 하나. next의 노드 번호를 가져온다
                    minAncestor = Math.min(minAncestor, nodeNum[next]);
                else        // 방문한 적이 없다면 탐색한 적이 없다. tarjan으로 재귀적 탐사한다.
                    minAncestor = Math.min(minAncestor, tarjan(next));
            }
        }

        if (minAncestor == nodeNum[node]) {     // 자신이 가장 이른 조상 번호라면(=하위노드랑 나의 조상 노드랑 연결되는 경우가 없다면)
            while (stack.peek() != node) {      // 스택에서 내가 나올 때까지
                isSSC[stack.peek()] = true;
                group[stack.pop()] = groupCounter;
            }
            // 나를 포함해 하나의 그룹으로 묶어준다.
            isSSC[stack.peek()] = true;
            group[stack.pop()] = groupCounter++;
        }
        // 최종적으로 얻은 최소 조상 번호를 리턴한다.
        return minAncestor;
    }

    static void init(int n) {
        int size = 2 * n + 1;
        relationships = new ArrayList<>();
        for (int i = 0; i < size; i++)
            relationships.add(new ArrayList<>());
        nodeNum = new int[size];
        ancestorCounter = 1;
        visited = new boolean[size];
        isSSC = new boolean[size];
        group = new int[size];
        groupCounter = 1;
        stack = new Stack<>();
    }
}