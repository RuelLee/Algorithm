/*
 Author : Ruel
 Problem : Baekjoon 5670번 휴대폰 자판
 Problem address : https://www.acmicpc.net/problem/5670
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5670_휴대폰자판;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

class Node {
    HashMap<Character, Node> child = new HashMap<>();
}

public class Main {
    static Node root;

    public static void main(String[] args) throws IOException {
        // n개의 단어가 주어진다
        // 휴대폰에서 자동완성 기능을 지원한다고 한다
        // 주어진 단어가 hello와 hell 이라면, h를 입력했을 때, 'hell'까지는 자동완성이 되어지고, 여기서 마쳐 hell 이라는 단어를 사용하거나
        // o를 추가 입력해 hello라는 단어를 사용할 수 있다고 한다
        // 단어의 목록들이 주어졌을 때 전체 단어의 평균적인 입력 횟수를 구하라.
        // 모든 단어의 시작이 똑같은 알파벳으로 시작하더라도 처음 키는 눌러야한다고 한다.
        //
        // 자료 구조 중 하나인 트라이를 이용하는 문제
        // 단어를 한글자씩 분할하여 트리 형태로 만든다
        // 그리고 이를 트리 형태로 연결해간다
        // 최종적으로 입력 횟수를 구할 때는 자식 노드가 1개일 때는 자동완성으로 완성될테고,
        // 자식 노드가 여러개 일 때가 자동완성이 불가능한 지점이므로 여기서 하나씩 입력 횟수가 늘어간다.
        //
        // 처음에는 hello, hell이라는 단어가 들어왔을 때, h / e / l / l / o로 분할하는게 아닌
        // hell / o 와 같이 단어열로 구분하려 했으나, 어차피 최종적으로 많은 단어가 들어옴에 따라, 각 글자별로 나뉘어질테니
        // 처음부터 하나의 글자로 나누는게 시간적 이득이 컸다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String input = br.readLine();
            if (input == null)
                break;

            root = new Node();
            int n = Integer.parseInt(input);

            for (int i = 0; i < n; i++)
                input(br.readLine());

            // hitCount 메소드로 전체 단어들의 입력 횟수를 구한다
            // 만약 root의 자식 노드가 하나라면, 어차피 첫글자를 입력해야하므로 hit에 1을 주고
            // 자식 노드가 여러개라면 자동완성을 지원 안해 메소드 내에서 1이 증가할 것이므로 0을 주자.
            int totalHit = hitCount(root, root.child.size() == 1 ? 1 : 0);
            sb.append(String.format("%.2f", (double) totalHit / n)).append("\n");
        }
        System.out.println(sb);
    }

    static int hitCount(Node node, int hit) {       // 전체 단어들의 총 입력 횟수를 구한다.
        int sum = 0;
        boolean isSplit = node.child.size() > 1;     // 현재 노드의 자식 노드가 여러개인지 여부.
        for (char key : node.child.keySet()) {
            if (key == '.')     // '.'은 주어진 알파벳들로 끝나는 단어가 있음을 나타낸다. 현재의 hit를 sum에 더한다.
                sum += hit;
            else
                sum += hitCount(node.child.get(key), isSplit ? hit + 1 : hit);
            // 그렇지 않고 다른 알파벳들이 더 있다면, hitCount 메소드를 재귀적으로 부른다.
            // 이 때 현재 지점이 자동완성을 지원하지 않는다면(자식 노드가 여러개라면) hit를 하나 증가시키고, 그렇지 않다면 hit를 그대로 가져간다.
        }
        // 최종적인 sum을 리턴한다.
        return sum;
    }

    static void input(String s) {       // 단어 s를 트라이 구조에 넣는다.
        Node current = root;        // 처음 위치는 root
        // s단어를 한글자씩 뜯어 살펴본다
        for (int i = 0; i < s.length(); i++) {
            // 현재 글자가 트라이 구조에 없다면
            // 추가.
            if (!current.child.containsKey(s.charAt(i)))
                current.child.put(s.charAt(i), new Node());
            // 해당 글자의 자식 노드로 들어간다.
            current = current.child.get(s.charAt(i));
        }
        // 최종적으로 단어의 모든 글자를 살펴봤다면, 끝났다는 종료 표시로 .을 추가한다.
        current.child.put('.', new Node());
    }
}