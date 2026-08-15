/*
 Author : Ruel
 Problem : Jungol 5832번 야바위2 (Stone Arranging 2)
 Problem address : https://jungol.co.kr/problem/5832
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5832_야바위2_StoneArranging2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 돌이 1 ~ 10^9의 색으로 주어진다.
        // 순서대로 돌을 놓아가되, 이전에 등장한 색과 같은 색의 돌이 등장한다면
        // 두 돌 사이의 돌들을 모두 이번 돌의 색으로 바꿔준다.
        // 모든 돌을 놓았을 때, 각 돌의 색은?
        //
        // 스택, 데크 문제
        // 스택을 통해 현재 연속한 돌의 색과 개수를 저장한다.
        // 해쉬맵을 통해 이전에 등장한 돌의 개수를 관리한다.
        // 그러면서 새로운 돌이 들어왔을 때, 이전에 등장한 색인지 여부를 계산하고
        // 이전에 등장했다면, 스택에서 해당 색이 나올 때까지 꺼내가며 해당 구간의 돌을 전부 같은 색으로 바꾼다.
        // 전혀 새로운 돌이라면, 다시 새로운 돌로 스택에 담는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 돌
        int n = Integer.parseInt(br.readLine());
        // 각 색의 돌 등장 여부
        HashMap<Integer, Integer> map = new HashMap<>();
        // 스택이 필요하나, 나중에 답안 작성 시 역방향이 아니라 정방향으로 봐야하므로 둘 다 가능한 데크로 처리
        Deque<int[]> deque = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            // 이번 돌의 색
            int num = Integer.parseInt(br.readLine());
            // 처음 등장한 경우
            if (!map.containsKey(num) || map.get(num) == 0) {
                // 값 처리 후, 데크에 추가
                map.put(num, 1);
                deque.offerLast(new int[]{num, 1});
            } else {
                // 이미 등장했던 경우
                // 현재 돌 부터
                int sum = 1;
                // 해당 돌까지 스택에서 꺼내가며 개수를 누적
                while (deque.peekLast()[0] != num) {
                    int temp = deque.peekLast()[0];
                    sum += deque.pollLast()[1];
                    // 해쉬맵에서 개수 차감
                    map.put(temp, map.get(temp) - 1);
                }
                // 마지막 같은 색의 돌도 누적
                sum += deque.pollLast()[1];
                // 같은 색으로 이루어진 새로운 구간 추가
                deque.offerLast(new int[]{num, sum});
            }
        }

        // 답 작성
        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()) {
            for (int i = 0; i < deque.peekFirst()[1]; i++)
                sb.append(deque.peekFirst()[0]).append("\n");
            deque.pollFirst();
        }
        // 출력
        System.out.print(sb);
    }
}