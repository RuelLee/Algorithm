/*
 Author : Ruel
 Problem : Baekjoon 1766번 문제집
 Problem address : https://www.acmicpc.net/problem/1766
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 문제집;

import java.util.*;

class Node {
    int num;
    int inDegree;

    public Node(int num, int inDegree) {
        this.num = num;
        this.inDegree = inDegree;
    }
}

public class Main {
    public static void main(String[] args) {
        // 위상 정렬 문제
        // 다만 같은 진입차수더라도 우선순위가 존재한다 ( 번호가 적은 문제를 우선)
        // 진입차수와 문제 번호를 고려한 우선순위큐를 활용해서 풀어보자.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Integer>[] lists = new List[n + 1];    // 각 문제의 후행 문제를 저장할 리스트들
        for (int i = 1; i < lists.length; i++)
            lists[i] = new ArrayList<>();

        Node[] nodes = new Node[n + 1];     // 진입 차수와 문제 번호를 저장할 Node 배열
        for (int i = 1; i < nodes.length; i++)
            nodes[i] = new Node(i, 0);

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            lists[a].add(b);        // b는 a의 후행 문제이므로 lists[a]에 b 값을 저장한다.
            nodes[b].inDegree++;    // b의 진입차수를 증가시켜준다.
        }
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.inDegree == o2.inDegree)         // 진입차수가 같다면
                return Integer.compare(o1.num, o2.num);     // 번호가 적은 문제가 우선
            return Integer.compare(o1.inDegree, o2.inDegree);   // 진입차수가 다르다면 진입차수가 적은 문제를 우선
        });
        priorityQueue.addAll(Arrays.asList(nodes).subList(1, nodes.length));

        StringBuilder sb = new StringBuilder();
        while (!priorityQueue.isEmpty()) {      // 큐가 빌 때까지
            // 문제 조건이 항상 문제를 풀 수 있는 경우이므로, 항상 나오는 Node 는 진입차수가 0인 것들 중 가장 번호가 낮은 문제이다.
            Node current = priorityQueue.poll();
            sb.append(current.num).append(" ");     // StringBuilder 에 해당 문제 번호를 기록.

            for (int i : lists[current.num]) {      // 현 문제의 후행 문제들의 진입차수를 줄여주고, 우선순위큐에 다시 넣어 재배치해주자.
                nodes[i].inDegree--;
                priorityQueue.remove(nodes[i]);
                priorityQueue.add(nodes[i]);
            }
        }
        System.out.println(sb);
    }
}