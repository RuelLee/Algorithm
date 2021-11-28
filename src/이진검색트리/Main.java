/*
 Author : Ruel
 Problem : Baekjoon 1539번 이진 검색 트리
 Problem address : https://www.acmicpc.net/problem/1539
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 이진검색트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node {
    int value;
    int depth;

    public Node(int value, int depth) {
        this.value = value;
        this.depth = depth;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 이진 트리에 값을 넣을 때, 삽입되는 값들의 높이의 합을 구하여라.
        // 이진 트리의 형태에 대해 고민해보면서 lowerBound와 upperBound에 고민해볼 수 있었다.
        // 이진 트리에 값을 넣을 때, 해당하는 값보다 작은 값들 중에 가장 큰 값의 오른쪽 자식 노드에 들어가거나
        // 해당하는 큰 값들 중에 가장 작은 값의 왼쪽 자식 노드에 들어간다.
        // 위에 해당하는 lowerBound와 upperBound의 높이 중 큰 값을 구해 +1 을 해주면 해당 높이의 값이 된다.
        // JAVA에서는 이를 어떻게 사용해야하나 찾아봤더니, Collections.binarySearch()를 이용하는 방법이 있었으나, 리스트를 이용해서 그런지 값의 삽입에 오버헤드가 커 시간 초과가 났다
        // 다른 구조를 찾아보니 TreeSet은 트리 형태로 구현된 set이므로 문제에 딱맞는 자료구조였다.
        // 더군다나 메소드로 lowerBound와 upperBound에 해당하는 floor(), ceiling()도 존재했으로 여러모로 딱인 자료구조였다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        TreeSet<Node> treeSet = new TreeSet<>((o1, o2) -> Integer.compare(o1.value, o2.value));     // comparator 를 정해줌.
        long sum = 0;
        for (int i = 0; i < n; i++) {
            Node node = new Node(Integer.parseInt(br.readLine()), 0);       // value 값으로 집어넣고
            // 트리셋에서 작은 값들 중 가장 큰 값은 무엇인지, 큰 값들 중 가장 작은 값은 무엇인지 (만약 그러한 값이 없다면 0이라고 생각하고) 구해 +1 해주어 높이를 구해준다
            int depth = Math.max(treeSet.floor(node) == null ? 0 : treeSet.floor(node).depth,
                    treeSet.ceiling(node) == null ? 0 : treeSet.ceiling(node).depth) + 1;
            // 높이를 지정하고, sum 에도 더해준 후
            sum += node.depth = depth;
            // 추가해준다
            treeSet.add(node);
        }
        System.out.println(sum);
    }
}