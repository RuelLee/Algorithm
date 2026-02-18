/*
 Author : Ruel
 Problem : Baekjoon 25381번 ABBC
 Problem address : https://www.acmicpc.net/problem/25381
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25381_ABBC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        // A와 B, C로 이루어진 문자열 S가 주어진다.
        // 다음과 같은 시행을 할 수 있다.
        // A와 뒤에 있는 B를 지운다.
        // B와 뒤에 있는 C를 지운다.
        // 가장 많은 시행을 할 때, 그 횟수는?
        //
        // 큐 문제
        // A는 B와 B는 C와 엮어있다.
        // C는 B랑만 엮어있으므로, 우선, B를 큐에 담아가며, C를 만난 경우 앞에서부터 B를 제거한다.
        // 이렇게 하면, 먼저나오는 B를 우선적으로 제거하게되며, A와 뒤에 있는 B가 최대한 매칭되게 할 수 있다.
        // 그 후, A를 큐에 담아가며, 만나는 B를 A와 함께 제거한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력
        char[] input = br.readLine().toCharArray();

        // 제거된 문자
        boolean[] selected = new boolean[input.length];
        Queue<Integer> queue = new LinkedList<>();
        int count = 0;
        // 먼저 큐에 B를 담아가며, C를 만난 경우 B와 함께 제거한다.
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 'B')
                queue.offer(i);
            else if (input[i] == 'C' && !queue.isEmpty()) {
                selected[i] = true;
                selected[queue.poll()] = true;
                count++;
            }
        }
        queue.clear();

        // 그 후, A를 담아가며, B를 만난 경우 함께 제거한다.
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 'A')
                queue.offer(i);
            else if (input[i] == 'B' && !selected[i] && !queue.isEmpty()) {
                selected[i] = true;
                selected[queue.poll()] = true;
                count++;
            }
        }
        // 최대 시행 횟수 출력
        System.out.println(count);
    }
}