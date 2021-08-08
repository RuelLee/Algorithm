/*
 Author : Ruel
 Problem : Baekjoon 2150번 Strongly Connected Component
 Problem address : https://www.acmicpc.net/problem/2150
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package StronglyConnectedComponent;

import java.util.*;

public class Main {
    static List<List<Integer>> connection;
    static PriorityQueue<PriorityQueue<Integer>> SCC;
    static Stack<Integer> stack;
    static int nodeCount;           // 노드에게 방문 순서에 따라 값을 할당할 것이다.
    static int[] nodeNum;           // nodeCount를 남겨놔, 자신보다 먼저 방문한 노드인지 확인할 수 있게 한다.
    static boolean[] check;         // 방문했었는지(조상 노드인지)를 표시할 boolean 배열
    static boolean[] isSCC;         // SCC를 이루었는지에 대해 남겨놓을 boolean 배열

    public static void main(String[] args) {
        // 강한 연결 요소에 대한 문제!
        // 코사라주 알고리즘과 타잔 알고리즘이 있다!
        // stack을 활용하는 타잔 알고리즘을 활용하여 풀었다!
        Scanner sc = new Scanner(System.in);

        int v = sc.nextInt();
        int e = sc.nextInt();
        init(v);

        for (int i = 0; i < e; i++)
            connection.get(sc.nextInt()).add(sc.nextInt());

        for (int i = 1; i < check.length; i++) {
            if (!isSCC[i])
                findSCC(i);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(SCC.size()).append("\n");
        while (!SCC.isEmpty()) {
            PriorityQueue<Integer> current = SCC.poll();
            while (!current.isEmpty())
                sb.append(current.poll()).append(" ");
            sb.append(-1).append("\n");
        }
        System.out.println(sb);
    }

    static int findSCC(int current) {
        check[current] = true;      // 방문체크
        int minAncestor = nodeNum[current] = nodeCount++;       // 자신에게 nodeCount를 할당하고, 최소조상에 일단 자신을 대입한다.
        stack.push(current);        // stack에 자신을 push

        for (int next : connection.get(current)) {      // current에서 갈 수 있는 다음 노드들
            if (!isSCC[next]) {     // 이미 SSC를 이룬 노드는 방문할 필요가 없다.
                if (check[next])        // 방문했던 노드라면(= 조상노드라면)
                    minAncestor = Math.min(minAncestor, nodeNum[next]);     // 최소 조상에 현재값과 nodeNum[next]중 작은 값을 저장한다
                else        // 방문을 하지 않은 경우이므로 findSSC 함수 자체를 재귀로 불러 minAncestor 값을 가져와 작은 값을 남겨둔다.
                    minAncestor = Math.min(minAncestor, findSCC(next));
            }
        }

        if (minAncestor == nodeNum[current]) {      // 만약 minAncestor가 자신이라면(= 자식 노드 중에 조상 노드로 갈 수 있는 노드가 없을 경우) SSC를 이룬다.
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            while (stack.peek() != current) {   // 자신이 최상위에 올 때까지
                isSCC[stack.peek()] = true;     // 팝하면서 isSCC에 SSC를 이루었다고 남겨둔다.
                priorityQueue.offer(stack.pop());
            }
            isSCC[stack.peek()] = true;     // 마지막으로 자신까지 pop하고 기록.
            priorityQueue.offer(stack.pop());
            SCC.offer(priorityQueue);
        }
        return minAncestor;         // 현재 minAncestor 값을 리턴한다.
    }

    static void init(int v) {
        connection = new ArrayList<>();     // a -> b로 가는 길을 저장해둘 리스트의 리스트!
        for (int i = 0; i < v + 1; i++)
            connection.add(new ArrayList<>());

        // SSC에 대해 정렬을 요하므로 2중 우선순위큐를 사용하자
        // 각 SSC는 오름차순 정리되어야하고, SSC들은 각 첫번째 요소에 따라 정렬해야한다.
        SCC = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.peek(), o2.peek()));
        stack = new Stack<>();

        nodeCount = 1;
        nodeNum = new int[v + 1];
        check = new boolean[v + 1];
        isSCC = new boolean[v + 1];
    }
}