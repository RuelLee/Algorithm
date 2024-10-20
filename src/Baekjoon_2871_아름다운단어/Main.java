/*
 Author : Ruel
 Problem : Baekjoon 2871번 아름다운 단어
 Problem address : https://www.acmicpc.net/problem/2871
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2871_아름다운단어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 상근이와 희원이는 게임을 한다. n개의 종이에 한 글자씩 적혀있다.
        // 두 사람이 번걸아가며 하나의 종이를 가져가며, 순서대로 나열한 단어가
        // 사전순으로 더 이른 사람이 승리한다.
        // 상근이부터 게임을 시작하며, 상근이는 게임을 아직 다 이해하지 못해, 가장 오른쪽에 있는 종이를 가져간다.
        // 희원이가 이길 수 있다면 DA, 그렇지 않다면 NE
        // 그리고 희원이가 만든 단어를 출력한다.
        //
        // 그리디 문제
        // 상근이는 오른쪽으로 순서대로 살펴가며 글자를 가져가고
        // 희원이는 알파벳 순으로 가장 이른 글자를, 그러한 글자가 여러개라면 상근이가 가져갈 확률이 높은
        // 가장 오른편에 있는 해당 글자를 가져가는게 유리하다.
        // 위 과정을 모든 종이를 가져갈 때까지 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 장의 종이
        int n = Integer.parseInt(br.readLine());
        String s = br.readLine();

        // 희원이가 가져갈 종이의 순서
        // 알파벳으로 이른 순으로 가져가되, 그러한 글자가 여러개라면 가장 오른편에 있는 글자부터.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (s.charAt(o1) == s.charAt(o2))
                return Integer.compare(o2, o1);
            return Character.compare(s.charAt(o1), s.charAt(o2));
        });
        for (int i = 0; i < s.length(); i++)
            priorityQueue.offer(i);
        
        // 상원이가 만드는 단어
        StringBuilder first = new StringBuilder();
        // 희원이가 만드는 단어
        StringBuilder second = new StringBuilder();
        // 상원이가 가져갈 종이의 위치
        int idx = s.length() - 1;
        // 가져간 종이 표시.
        boolean[] selected = new boolean[s.length()];
        // 두 사람이 아직 가져갈 종이가 있는 동안
        while (idx >= 0 && !priorityQueue.isEmpty()) {
            // 상원이가 가져갈 종이가 이미 가져간 종이라면
            // 왼편으로 계속 이동
            while (idx >= 0 && selected[idx])
                idx--;
            // 아직 상원이가 가져갈 종이가 있다면
            // 해당 글자를 가져가고, 표시.
            if (idx > 0) {
                first.append(s.charAt(idx));
                selected[idx--] = true;
            }

            // 희원이가 가져갈 종이가 이미 없다면
            // 계속 다음 순서의 종이로 넘긴다.
            while (!priorityQueue.isEmpty() && selected[priorityQueue.peek()])
                priorityQueue.poll();
            // 희원이가 가져갈 종이가 있다면
            // 해당 종이를 가져가고 표시.
            if (!priorityQueue.isEmpty()) {
                second.append(s.charAt(priorityQueue.peek()));
                selected[priorityQueue.poll()] = true;
            }
        }
        // 완성된 단어를 비교하여, 희원이가 더 앞선다면 DA, 아니라면 NE 출력
        System.out.println(second.compareTo(first) < 0 ? "DA" : "NE");
        // 희원이가 만든 단어 출력
        System.out.println(second);
    }
}