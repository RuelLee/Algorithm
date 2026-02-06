/*
 Author : Ruel
 Problem : Baekjoon 16934번 게임 닉네임
 Problem address : https://www.acmicpc.net/problem/16934
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16934_게임닉네임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

// 트라이의 노드
// 맵을 통해, 현재 글자와 하위 트라이노드를 엮는다.
// count를 통해 현재 위치에서 끝나는 닉네임의 개수를 센다.
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    int count;
}

class Trie {
    // 처음 위치
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // 닉네임 추가
    public String insert(String word) {
        // 루트에서 시작
        TrieNode node = root;
        int len = -1;
        for (int i = 0; i < word.length(); i++) {
            // 만약 해당하는 글자가 없다면 -> 새로운 접두사 -> 별칭으로 가능
            if (!node.children.containsKey(word.charAt(i))) {
                // 해당 노드 추가 후
                // 별칭으로써의 길이를 체크
                node.children.put(word.charAt(i), new TrieNode());
                if (len == -1)
                    len = i;
            }
            // 트라이 구조는 계속 엮어나감
            node = node.children.get(word.charAt(i));
        }
        // 마지막 노드에 count 증가
        node.count++;

        // 만약 len이 설정되어있다면, 유일한 접두사를 찾았으므로
        // 해당 길이만큼 반환
        if (len != -1)
            return word.substring(0, len + 1);
        // 유일한 접두사는 못 찾았지만, 닉네님 자체가 현재로써 유일하다면
        // 해당 닉네임 반환
        else if (node.count == 1)
            return word;
        else        // 동일한 닉네임이 여러개라면 + 개수
            return word + node.count;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 닉네임이 주어진다.
        // 별칭은 유저의 닉네임 접두사 중 가장 짧은 것으로 한다.
        // 이전에 가입한 사람의 접두사가 아닌 것만 별칭으로 가능하다.
        // 가능한 별칭이 없는 경우, 닉네임 자체를 별칭으로 사용한다.
        // 같은 닉네임의 수가 1이라면 닉네임 그대로를, 그보다 많다면 닉네임 + 개수를 별칭으로 사용한다.
        //
        // 트라이 문제
        // 오랜만에 다시 구현한 트라이 구조.
        // 새로운 트라이 노드를 선언하는 순간 -> 동일한 접두사를 가진 닉네임이 없는 순간 -> 별칭
        // 으로 선택
        // 모든 글자를 살펴봤지만, 새로운 트라이 노드를 선언하지 않았다면
        // -> 해당 닉네임이 유일한 경우 해당 닉네임을 별칭으로 or 해당 닉네임 + 개수

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 트라이
        Trie trie = new Trie();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append(trie.insert(br.readLine())).append("\n");
        System.out.print(sb);
    }
}