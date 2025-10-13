/*
 Author : Ruel
 Problem : Baekjoon 30054번 웨이팅
 Problem address : https://www.acmicpc.net/problem/30054
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30054_웨이팅;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 예약 정보가 주어진다.
        // t1 t2 : 예약 시간, 실제 도착 시간
        // 해당 손님 들은 다음 규칙에 따라 입장한다.
        // 1. 매 정각마다 한 손님의 입장이 이루어지며, 한 시간 뒤 퇴장한다.
        // 2. 자신의 예약 시간이 아닌 경우, 대기 줄에 줄을 선다. 동시에 도착한 경우는 예약 시간이 빠른 순서로 선다.
        // 3. 자신의 예약 시간에 늦지 않은 손님은 예약 시간이 되면 우선적으로 입장한다.
        // 4. 예약자가 없거나 도착하지 않았다면 대기 줄의 첫번째 손님이 입장한다.
        // 모든 손님들을 처리했을 때, 가장 오래 기다린 손님의 시간은?
        //
        // 우선순위큐 문제
        // 두 개의 우선순위큐로 처리했다.
        // 하나는 지각 하지 않은 손님들을 예약 시간 오름차순으로, 다른 하나는 도착 시간과 예약 시간을 기준으로 선언했다.
        // 시간을 증가시키며, 예약 시간에 도착하거나 기다리고 있던 손님은 우선적으로 입장한다.
        // 예약이 없거나, 해당 손님이 도착 안한 경우에는 대기 줄의 첫번째 손님을 입장시켰다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 예약 정보
        int n = Integer.parseInt(br.readLine());

        int[][] reservations = new int[n][2];
        // 도착 시간 오름차순, 도착 시간이 같다면 예약 시간 오름차순
        PriorityQueue<Integer> comingOrder = new PriorityQueue<>((o1, o2) -> {
            if (reservations[o1][1] == reservations[o2][1])
                return Integer.compare(reservations[o1][0], reservations[o2][0]);
            return Integer.compare(reservations[o1][1], reservations[o2][1]);
        });
        // 예약 시간보다 같거나 일찍 온 손님들을 예약 시간에 따라 오름차순
        PriorityQueue<Integer> inTime = new PriorityQueue<>(Comparator.comparingInt(o -> reservations[o][0]));

        StringTokenizer st;
        for (int i = 0; i < reservations.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < reservations[i].length; j++)
                reservations[i][j] = Integer.parseInt(st.nextToken());
            comingOrder.offer(i);
            if (reservations[i][0] >= reservations[i][1])
                inTime.offer(i);
        }
        
        // 시간
        int time = 1;
        // 이미 처리된 손님
        boolean[] served = new boolean[n];
        // 최대 대기 시간
        int maxDiff = 0;
        while (!comingOrder.isEmpty()) {
            // 각 우선순위큐의 최상단 값이 이미 처리된 손님인 경우 제거
            while (!comingOrder.isEmpty() && served[comingOrder.peek()])
                comingOrder.poll();
            // 더 이상 손님이 없는 경우 반복문 종료
            if (comingOrder.isEmpty())
                break;
            while (!inTime.isEmpty() && served[inTime.peek()])
                inTime.poll();

            int idx = -1;
            // 예약 시각이 된 손님이 있다면 해당 손님 우선적으로 배치
            if (!inTime.isEmpty() && time == reservations[inTime.peek()][0])
                idx = inTime.poll();
            else        // 그 외의 대기줄에서 배치
                idx = comingOrder.poll();
            
            // 입장 시각
            // 현재 시각과 도착 시각을 비교하여 더 큰 값에 입장
            int enterTime = Math.max(time, reservations[idx][1]);
            // 그 때의 대기 시간
            maxDiff = Math.max(maxDiff, enterTime - reservations[idx][1]);
            // 입장 처리
            served[idx] = true;
            // 시간 증가
            time = enterTime + 1;
        }
        // 답 출력
        System.out.println(maxDiff);
    }
}