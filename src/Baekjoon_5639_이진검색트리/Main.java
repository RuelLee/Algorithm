/*
 Author : Ruel
 Problem : Baekjoon 5639번 이진 검색 트리
 Problem address : https://www.acmicpc.net/problem/5639
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5639_이진검색트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Node {
    int value;
    Node parent, left, right;

    public Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
    }
}

public class Main {
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        // 이진 검색 트리
        // 왼쪽 서브 트리의 모든 노드는 현재 노드보다 작다
        // 오른쪽 서브 트리의 모든 노드는 현재 노드보다 크다
        // 왼쪽, 오른쪽 서브 트리도 이진 검색 트리이다.
        // 전위 순회한 값이 주어질 때, 후위 순회한 값을 구하여라
        //
        // 트리에 관한 문제
        // 전위 순회한 값과, 이진 검색 트리라는 것이 주어졌으므로 해당 사항으로
        // 트리를 만들고, 이를 후위 순회하면 된다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 첫번째 값은 루트 노드
        Node root = new Node(Integer.parseInt(br.readLine()), null);
        String input;
        Node current = root;
        // 입력에 대한 개수가 주어지지 않으므로, 입력 값이 없을 때까지 받는다.
        while ((input = br.readLine()) != null) {
            // 입력된 숫자.
            int num = Integer.parseInt(input);

            // 현재 노드에 부모 노드가 존재하고, num이 현재 노드보다 크다면, 부모 노드로 보낸다.
            // num이 갈 수 있는 가장 높은 조상 노드까지 보냄.
            while (current.parent != null && num > current.value)
                current = current.parent;

            // num이 현재 노드보다 작다면, 왼쪽 자식, 크다면 오른쪽 자식 노드로 보낸다.
            while ((num < current.value && current.left != null) ||
                    (num > current.value && current.right != null)) {
                if (num < current.value)
                    current = current.left;
                else
                    current = current.right;
            }

            // 현재 노드는 말단 노드이다.
            // 여기서 num이 현재 노드보다 작다면 왼쪽 자식 노드로,
            // 크다면 오른쪽 자식 노드로 설정해준다.
            if (num < current.value) {
                current.left = new Node(num, current);
                current = current.left;
            } else {
                current.right = new Node(num, current);
                current = current.right;
            }
        }

        sb = new StringBuilder();
        // 후위 순회하고
        postOrder(root);
        // 그 값을 출력한다.
        System.out.print(sb);
    }

    static void postOrder(Node node) {
        // 왼쪽 자식 노드가 있다면 먼저 재귀적으로 탐색하고
        if (node.left != null)
            postOrder(node.left);
        // 그 후에 오른쪽 자식 노드가 있다면 마찬가지로 탐색한다.
        if (node.right != null)
            postOrder(node.right);
        // 그리고 최종적으로 자신을 출력한다.
        sb.append(node.value).append("\n");
    }
}