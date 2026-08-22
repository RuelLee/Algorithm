/*
 Author : Ruel
 Problem : Jungol 5123번 ABBC
 Problem address : https://jungol.co.kr/problem/5123
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5123_ABBC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        // A, B, C로 이루어진 문자열이 주어진다. 다음 두 가지 연산을 할 수 있다.
        // 1. A는 뒤에 있는 B와 같이 지울 수 있다
        // 2. B는 뒤에 있는 C와 함께 지울 수 있다.
        // 연산을 시행하는 최대 횟수는?
        //
        // 큐 문제
        // A는 B와 연관되어있고, B는 C와 연관되어있다.
        // B가 양쪽에 영향을 미치고, C는 B의 영향만 받으므로
        // B를 큐에 담아가며, C가 등장할 때마다, 먼저 등장한 B들을 지워나간다.
        // 그 후, 다시 처음부터 살펴보며 A가 등장할 때마다, 남은 B들 중 자신보다 늦게 등장하는 가장 이른 B를 지워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 문자열
        String input = br.readLine();

        // B의 위치를 담을 큐
        Queue<Integer> queue = new LinkedList<>();
        // 연산의 횟수
        int ans = 0;
        for (int i = 0; i < input.length(); i++) {
            // B를 큐에 담는다.
            if (input.charAt(i) == 'B')
                queue.offer(i);
            // C인 경우, 남은 B 중 가장 먼저 등장한 B를 지운다.
            else if (input.charAt(i) == 'C' && !queue.isEmpty()) {
                queue.poll();
                ans++;
            }
        }

        // A를 살펴본다.
        for (int i = 0; i < input.length(); i++) {
            // 큐가 빈 경우, 연산을 더 이상할 수 없으므로 반복문 종료
            if (queue.isEmpty())
                break;

            // A인 경우
            if (input.charAt(i) == 'A') {
                // 큐에서 A보다 먼저 등장한 B들은 제거
                while (!queue.isEmpty() && queue.peek() < i)
                    queue.poll();
                // 큐가 안 비었다면 현재 A보다 늦지만 가장 이른 B랑 같이 제거
                if (!queue.isEmpty()) {
                    queue.poll();
                    ans++;
                }
            }
        }
        // 답 출력
        System.out.println(ans);
    }
}