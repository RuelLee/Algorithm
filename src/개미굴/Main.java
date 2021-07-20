/*
 Author : Ruel
 Problem : Baekjoon 14725번 개미굴
 Problem address : https://www.acmicpc.net/problem/14725
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 개미굴;

import java.util.PriorityQueue;
import java.util.Scanner;

class Node {
    String name;
    PriorityQueue<Node> subNodes;

    public Node(String name) {
        this.name = name;
        subNodes = new PriorityQueue<>((o1, o2) -> o1.name.compareTo(o2.name));     // Node가 들어있으므로 이를 Node.name으로 비교하도록 Comparator를 설정해주자 (= Comparator.comparing(o -> o.name))
    }
}

public class Main {
    public static void main(String[] args) {
        // 트리를 만들고, 이를 전위순회하여 출력.
        // 단 서브노드들을 방문할 때 이름에 따라 사전순 방문이므로 우선순위큐를 활용하자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        Node root = new Node("root");   // 처음 시작 루트 노드를 구성.

        for (int i = 0; i < n; i++) {
            int t = sc.nextInt();
            String[] route = sc.nextLine().trim().split(" ");
            Node current = root;    // 언제든 입력의 처음은 root 부터
            for (int j = 0; j < t; j++) {
                boolean check = false;
                for (Node no : current.subNodes) {
                    if (no.name.equals(route[j])) {     // 이미 입력된 이름이라면
                        check = true;
                        current = no;       // 순서만 다음으로 바꿔줌.
                        break;
                    }
                }
                if (!check) {   // 입력되지 않은 이름이라면
                    Node node = new Node(route[j]);     // 새로운 노드를 만들어
                    current.subNodes.add(node);     // 서브노드로 추가해주고
                    current = node;         // 다음 순서로 만듦
                }
            }
        }
        // -- 트리 구성 끝 --

        StringBuilder sb = new StringBuilder();
        dfs(root, sb, 0);       // dfs로 방문하자.
        System.out.println(sb);
    }

    static void dfs(Node current, StringBuilder sb, int level) {
        if (!current.name.equals("root")) {     // root일 때는 표시 안함!
            sb.append("--".repeat(Math.max(0, level - 1)));
            sb.append(current.name).append("\n");
        }

        while (!current.subNodes.isEmpty()) {       // 우선순위에 담겨있는 노드들을 하나씩 방문
            Node next = current.subNodes.poll();
            dfs(next, sb, level + 1);       // level을 하나씩 늘려 "--"가 추가될 수 있도록 하자
        }
    }
}