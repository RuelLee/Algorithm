/*
 Author : Ruel
 Problem : Baekjoon 7432번 디스크 트리
 Problem address : https://www.acmicpc.net/problem/7432
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7432_디스크트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 각각의 폴더를 Node를 통해 Trie로 정리한다.
class Node {
    String name;
    // 사전순 출력을 해야하기 때문에 우선순위큐 사용
    PriorityQueue<Node> subNode;

    public Node(String name) {
        this.name = name;
        this.subNode = new PriorityQueue<>(Comparator.comparing(o -> o.name));
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 디렉토리들의 전체 경로가 모두 주어졌을 때
        // 디렉토리 구조를 구해 보기 좋게 출력하는 프로그램을 작성하시오
        // 한 줄에 하나씩 디렉토리 이름을 출력하며
        // 공백은 디렉토리 구조상에서 깊이를 의미한다.
        // 서브 디렉토리는 사전순으로 출력하며, 부모 디렉토리에서 출력한 공백보다 하나 많은 공백을 출력한다.
        //
        // 트라이 구조 문제
        // 디렉토리들의 전체 경로가 주어지므로, 당연히 중복되는 경로들이 존재한다.
        // 따라서 트라이 구조를 통해 중복되는 상위 경로들을 정리하고, 그 후에 DFS를 이용하여 출력하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 최상위 경로
        Node trie = new Node("root");
        
        // 디렉토리 경로 정리
        for (int i = 0; i < n; i++) {
            // \로 경로 구분
            String[] split = br.readLine().split("\\\\");

            // root에서 부터 하나씩 내려간다.
            Node loc = trie;
            for (String s : split) {
                boolean found = false;
                // 해당하는 하위 경로가 이미 root의 subNode로 존재하는지 찾는다.
                for (Node sub : loc.subNode) {
                    // 존재한다면 해당 node의 위치로 이동
                    if (s.equals(sub.name)) {
                        found = true;
                        loc = sub;
                        break;
                    }
                }

                // 존재 하지않는다면 새로운 subNode를 생성하고 그 위치로 이동
                if (!found) {
                    Node sub = new Node(s);
                    loc.subNode.offer(sub);
                    loc = sub;
                }
            }
        }
        // 전체 디렉토리 경로 정리 끝

        // DFS를 통해 StringBuilder에 답안을 기록한다.
        StringBuilder sb = new StringBuilder();
        // 전체 경로의 모든 subNode들을 기록
        while (!trie.subNode.isEmpty())
            writeDirectory(0, trie.subNode.poll(), sb);

        System.out.print(sb);
    }

    // DFS
    // 현재 깊이, 현재 위치, 기록하는 StringBuilde를 매개 변수로 받는다.
    static void writeDirectory(int depth, Node node, StringBuilder sb) {
        // 현재 깊이에 따른 공백 기록
        for (int i = 0; i < depth; i++)
            sb.append(" ");
        // 현재 위치의 폴더명 기록
        sb.append(node.name).append("\n");
        // 하위 폴더가 존재한다면 DFS를 통해 한 단계 더 내려간다.
        while (!node.subNode.isEmpty())
            writeDirectory(depth + 1, node.subNode.poll(), sb);
    }
}