/*
 Author : Ruel
 Problem : Baekjoon 5052번 전화번호 목록
 Problem address : https://www.acmicpc.net/problem/5052
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 전화번호목록;

import java.util.HashMap;
import java.util.Scanner;

class Trie {
    HashMap<Character, Trie> childNode = new HashMap<>();
    boolean isLastCharacter;

    public Trie(boolean isLastCharacter) {
        this.isLastCharacter = isLastCharacter;
    }
}

public class Main2 {
    static Trie root;

    public static void main(String[] args) {
        // Trie(트라이)를 사용해서 풀어보았다.
        // 트리를 이용한 자료구조인 것 같다.
        // 해당 노드에서 끝난 다른 번호가 있는지 확인하고 있다면 해당 번호는 접두사가 중복
        // 또는 번호의 마지막 숫자의 차례에서 노드가 새로 생성되지 않고, 이미 있던 노드라면 이번 번호도 중복.
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = sc.nextInt();
            root = new Trie(false);

            sc.nextLine();
            String[] inputs = new String[n];
            for (int i = 0; i < inputs.length; i++)
                inputs[i] = sc.nextLine();

            boolean result = true;
            for (String input : inputs) {
                result = inputPhoneNumber(input.replaceAll(" ", ""));
                if (!result)
                    break;
            }

            sb.append(result ? "YES" : "NO").append("\n");
        }
        System.out.println(sb);
    }

    static boolean inputPhoneNumber(String phoneNumber) {
        Trie currentNode = root;
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (currentNode.childNode.containsKey(phoneNumber.charAt(i))) {     // 이미 이번 문자에 해당하는 childNode 가 있다.
                if (i == phoneNumber.length() - 1)      // 이번 번호의 마지막 문자라면, 이번 번호가 다른 번호의 접두사에 중복.
                    return false;

                currentNode = currentNode.childNode.get(phoneNumber.charAt(i));     // 다음 노드로 넘어간다.
                if (currentNode.isLastCharacter)        // 다음 노드가 마지막 노드라면 다른 번호가 현재 번호의 접두사에 해당하는 상태.
                    return false;
            } else {
                currentNode.childNode.put(phoneNumber.charAt(i), new Trie(i == phoneNumber.length() - 1));      // 새로운 문자로 자식 노드를 생성.
                currentNode = currentNode.childNode.get(phoneNumber.charAt(i));     // currentNode 를 자식 노드로 교체.
            }
        }
        return true;
    }
}