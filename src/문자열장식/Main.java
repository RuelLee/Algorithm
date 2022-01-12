/*
 Author : Ruel
 Problem : Baekjoon 1294번 문자열 장식
 Problem address : https://www.acmicpc.net/problem/1294
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 문자열장식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 여러개의 문자열이 주어지고, 각 문자열을 조각 내어, 이어붙였을 때 알파벳 순으로 가장 이른 새 문자열은 무엇인가
        // 각 조각들은 원래 문자열의 순서를 유지한다
        // 각 문자열은 최소 단위인 한 글자씩 떼어 붙일 수도 있다. 하지만 순서인 문자가 같은 문자라면 어떤 문자가 우선적으로 와야하는가에 대한 고민이 되어야한다
        // 만약 예제와 같이 CCCA CCCB CCCD CCCE 라는 4개의 문자열이 주어진다면
        // 첫글자는 모두 C이므로 같다고 생각할 수 있지만
        // 가장 우선적으로 가장 이른 문자인 A가 나타는 CCCA의 C가 우선도가 높다
        // 다시 말해서, 같은 문자를 비교하더라도 뒷 문자까지 고려한 순서로 갖다붙여야한다.
        // 또한 서로 길이가 다른 문자열이 주어질 수도 있는데 이 때 또한 고려를 해야한다
        // CCCA CCC CCCE가 주어진다면 가장 우선순위가 높아야하는건 A를 가장 앞으로 보낼 수 있는 CCCA이고, 그 다음에는 CCC, CCCE다.
        // 먼저 CCCA 와 CCC를 비교해보면, 세번째 글자까지 같고, 네번째 문자열의 순서에 'A', ''을 비교해야한다
        // CCC의 마지막 문자까지 일치했다면, 긴 쪽이 짧은 쪽을 안에 품고 있는 형태가 된다
        // 따라서 짧은 쪽을 연이어 붙인 형태로 양 문자열을 비교하면 된다
        // 결국 CCCA와 CCC는 CCCA와 CCCC를 비교하는 형태가 될 것이며, CCC와 CCCD는 CCCC와 CCCD가 된다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 지속적으로 문자열의 값이 변경되므로, 변경이 가능한 StringBuilder를 사용하여 메모리를 절약해주자.
        PriorityQueue<StringBuilder> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            int maxLength = Math.max(o1.length(), o2.length());
            // o1, o2 중 긴 쪽의 길이를 가져와 양 문자열을 비교한다
            for (int i = 0; i < maxLength; i++) {
                // 짧은 쪽은 자신의 문자열을 연이어 붙은 형태로 검사한다
                // o1이 더 작은 값을 같고 있을 경우, 현재의 순서 유지
                if (o1.charAt(i % o1.length()) < o2.charAt(i % o2.length()))
                    return -1;
                // o2가 더 작을 경우 순서가 바뀌어야한다.
                else if(o1.charAt(i % o1.length()) > o2.charAt(i % o2.length()))
                    return 1;
            }
            // 끝날 때까지 결정되지 않았다면 두 문자열은 같은 순서를 갖는다고 할 수 있다.
            return 0;
        });
        // 우선순위큐에 각 문자열을 넣고
        for (int i = 0; i < n; i++)
            priorityQueue.offer(new StringBuilder(br.readLine()));

        StringBuilder sb = new StringBuilder();
        while (!priorityQueue.isEmpty()) {
            // 하나씩 꺼내 첫 글자만 sb로 옮겨준다.
            StringBuilder current = priorityQueue.poll();
            sb.append(current.charAt(0));
            if (current.length() > 1) {     // 길이가 1이상이라면 앞글자를 sb에 담고, current에서는 첫글자를 지운 후, 우선순위큐에 담아준다.
                current.deleteCharAt(0);
                priorityQueue.offer(current);
            }
            // 길이가 1이라면 그대로 버린다.
        }
        // 최종적으로 완성되는 sb가 답.
        System.out.println(sb);
    }
}