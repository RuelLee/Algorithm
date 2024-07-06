/*
 Author : Ruel
 Problem : Baekjoon 28078번 중력 큐
 Problem address : https://www.acmicpc.net/problem/28078
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28078_중력큐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 왼쪽이 앞, 오른쪽이 뒤인 큐가 존재한다.
        // 이를 90도 방향으로 돌릴 수 있으며
        // 큐에는 공 또는 가림막을 넣을 수 있다.
        // 공은 중력의 영향을 받으며, 가림막은 중력의 영향을 받지 않는다.
        // 만약 큐가 앞이나 뒤가 위를 보고 있는 상태에서
        // 가림막보다 아래에 존재하는 공은 모두 아래로 떨어지게 된다.
        // q개의 쿼리가 주어질 때 쿼리를 처리하라
        // push b : 공을 하나 뒤에 추가
        // push w : 가림막을 하나 뒤에 추가
        // pop : 가장 앞에 있는 공이나 가림막을 하나 꺼낸다.
        // rotate l : 반시계로 90도 회전
        // ratate r : 시계로 90도 회전
        // count b : 큐에 있는 공의 개수 출력
        // count w : 큐에 있는 가림막의 개수 출력
        //
        // 데크, 시뮬레이션 문제
        // 데크를 통해 간단하게 해결할 수 있는 문제
        // 방향에 따라 데크 내에 있는 공이 떨어지는가를 판별해주면서 쿼리를 처리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // q개의 쿼리
        int q = Integer.parseInt(br.readLine());
        
        // 데크
        Deque<Integer> deque = new LinkedList<>();
        // 방향
        int direction = 0;
        // 가림막과 공의 개수
        int[] counts = new int[2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String order = st.nextToken();
            switch (order) {
                // 뒤에 공이나 가림막을 추가
                case "push" -> {
                    if (st.nextToken().equals("b")) {
                        deque.offerLast(1);
                        counts[1]++;
                    } else {
                        deque.offerLast(0);
                        counts[0]++;
                    }
                }
                // 가장 앞에 있는 물체를 꺼냄
                case "pop" -> {
                    if (!deque.isEmpty())
                        counts[deque.pollFirst()]--;
                }
                // 회전
                case "rotate" -> {
                    if (st.nextToken().equals("l"))
                        direction = (direction + 3) % 4;
                    else
                        direction = (direction + 1) % 4;
                }
                // 큐 내에 있는 공이나 가림막의 수 출력
                case "count" -> {
                    if (st.nextToken().equals("b"))
                        sb.append(counts[1]).append("\n");
                    else
                        sb.append(counts[0]).append("\n");
                }
            }

            // 만약 방향이 앞이 아래를 보고 있다면
            // 가장 앞에 가림막이 없는 한, 공을 모두 꺼낸다.
            while (direction == 1 && !deque.isEmpty() && deque.peekFirst() != 0)
                counts[deque.pollFirst()]--;
            // 만약 방향이 뒤가 아래를 보고 있다면
            // 가장 뒤에 가림막이 없는 한, 공을 모두 꺼낸다.
            while (direction == 3 && !deque.isEmpty() && deque.peekLast() != 0)
                counts[deque.pollLast()]--;
        }
        // 결과 출력
        System.out.print(sb);
    }
}