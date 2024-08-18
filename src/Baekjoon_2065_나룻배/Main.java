/*
 Author : Ruel
 Problem : Baekjoon 2065번 나룻배
 Problem address : https://www.acmicpc.net/problem/2065
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2065_나룻배;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int DIFF = 100_000;

    public static void main(String[] args) throws IOException {
        // 좌, 우 두 선착장이 주어진다.
        // 두 선착장에는 총 n명의 손님이 맞은편으로 가고자 한다.
        // 배는 현재 왼쪽 선착장에 있다.
        // 배는 맞은편에서 승객이 기다리고 있다면 승차객이 없더라도 맞은편을 향해 출발한다.
        // 배에는 최대 m명 탑승할 수 있고, 이동하는데 걸리는 시간은 t이며
        // 승선, 하선하는데는 시간이 걸리지 않는다.
        // 각 승객들이 내리는 시간을 출력하라
        //
        // 시뮬레이션, 큐 문제
        // 배가 현재 위치에서 승객을 태우는지
        // 혹은 다음 승객을 기다리는지
        // 혹은 맞은 편 선착장으로 이동해야하는지 등을 잘 따져 프로그래밍한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 배의 탑승 인원 m, 이동 시간 t, 승객 n명
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 각 승차장에서 기다리는 승객
        Queue<Integer> left = new LinkedList<>();
        Queue<Integer> right = new LinkedList<>();
        int[] passenger = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            String direction = st.nextToken();

            if (direction.equals("left"))
                left.offer(i);
            else
                right.offer(i);
            passenger[i] = time;
        }
        
        // 각 승객의 도착 시간
        int[] arrivalTimes = new int[n];
        // 현재 시간
        int currentTime = 0;
        // 현재 왼족 위치
        boolean ship = false;
        // 모든 승객을 나를 때까지.
        while (!(left.isEmpty() && right.isEmpty())) {
            // 현재 배가 왼쪽에 있는 경우.
            if (!ship) {
                // 왼쪽 승객이 아직 남았고, 현재 배를 기다린 승객이 있거나
                // 오른쪽 승객이 없거나
                // 다음 승객도 오른쪽 승객보다 왼쪽 승객이 먼저 기다리는 경우
                // 배는 왼쪽에서 승객을 태운다.
                if (!left.isEmpty() && (passenger[left.peek()] <= currentTime || right.isEmpty() || passenger[left.peek()] <= passenger[right.peek()])) {
                    // 시간 조정
                    currentTime = Math.max(currentTime, passenger[left.peek()]);
                    // 탑승 가능 인원
                    int count = m;
                    // 탑승
                    while (!left.isEmpty() && passenger[left.peek()] <= currentTime && count > 0) {
                        count--;
                        // 승객의 도착 시간
                        arrivalTimes[left.poll()] = currentTime + t;
                    }
                    // 오른쪽으로 이동하므로 시간 조정
                    currentTime += t;
                } else if (!right.isEmpty())        // 위 경우에 해당하지 않고, 오른쪽 선착장 승객이 있다면 오른쪽 선착장으로 이동한다.
                    currentTime = Math.max(currentTime + t, passenger[right.peek()] + t);
                // 배 위치 조정
                ship = true;
            } else {        // 오른쪽 선착장일 때도 마찬가지로 시뮬레이션한다.
                if (!right.isEmpty() && (passenger[right.peek()] <= currentTime || left.isEmpty() || passenger[right.peek()] <= passenger[left.peek()])) {
                    currentTime = Math.max(currentTime, passenger[right.peek()]);
                    int count = m;
                    while (!right.isEmpty() && passenger[right.peek()] <= currentTime && count > 0) {
                        count--;
                        arrivalTimes[right.poll()] = currentTime + t;
                    }
                    currentTime += t;
                } else if (!left.isEmpty())
                    currentTime = Math.max(currentTime + t, passenger[left.peek()] + t);
                ship = false;
            }
        }

        // 각 승객들의 하차 시간 기록
        StringBuilder sb = new StringBuilder();
        for (int arrivalTime : arrivalTimes)
            sb.append(arrivalTime).append("\n");
        // 답안 출력
        System.out.print(sb);
    }
}