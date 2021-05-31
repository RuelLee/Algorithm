package 셔틀버스;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution {
    public static void main(String[] args) {
        // 버스 시간과 승객이 줄을 서는 시간을 비교해서 가장 마지막에 버스를 탈 수 있는 시간을 구해야한다.
        // 1. 가득 차지 않은 버스가 있다면 그 버스의 출발 시간에 도착하면 버스에 탑승할 수 있다.
        // 2. 버스에 탑승하는 승객 중 마지막 승객보다 1분 빠르게 대기열을 선다면 버스에 탑승할 수 있다.
        // 1과 2과정을 9시부터 시작하여 마지막 버스까지 계산한 후,
        // 1에서 구해진 버스 시간과, 2에서 구해진 끼어들 수 있는 시간 중 더 늦은 시간을 반환한다.

        int n = 1;
        int t = 1;
        int m = 5;
        String[] timetable = {"00:01", "00:01", "00:01", "00:01", "00:01"};

        Queue<Integer> busQueue = new LinkedList<>();
        for (int i = 0; i < n; i++)
            busQueue.offer(timeToInt("09:00") + i * t);

        PriorityQueue<Integer> passengers = new PriorityQueue<>();
        for (String s : timetable)
            passengers.add(timeToInt(s));

        // 버스 탑승하는 마지막 승객 시간의 초기화. 마지막 버스 출발 시간보다 늦은 승객에게 끼어들어선 의미가 없다.
        int lastPassenger = passengers.peek() < 540 + (n - 1) * t ? passengers.peek() : busQueue.peek() + 1;
        int notFullLastBus = 0;
        while (!busQueue.isEmpty()) {
            int currentBus = busQueue.poll();
            int nextPassenger;

            if (passengers.isEmpty() || passengers.peek() > currentBus) {   // 승객의 대기열이 비어있거나, 현재 버스보다 늦은 시간에 줄을 선다면, 꽉 차지 않은 버스로 표시 후, 다음 버스.
                notFullLastBus = currentBus;
                continue;
            }
            nextPassenger = passengers.poll();
            lastPassenger = nextPassenger;

            int count = 0;
            for (int i = 0; i < m - 1 && !passengers.isEmpty(); i++) {
                int currentPassenger = passengers.poll();
                if (currentPassenger > currentBus) {    // 현재 버스보다 늦은 시간에 줄을 선다면, 다시 대기열로 돌려 놓고 반복 중단.
                    passengers.add(currentPassenger);
                    break;
                }
                lastPassenger = currentPassenger;
                count++;
            }

            if (count + 1 < m)      // 정원이 차지 않았다면 현재 버스를 꽉 차지 않은 버스로 표시.
                notFullLastBus = currentBus;
        }
        int lastTime = Math.max(lastPassenger - 1, notFullLastBus);     // 버스에 탑승하는 마지막 승객보다 1분 이른 시간과, 꽉 차지 않은 마지막 버스 중 더 늦은 시간이 정답.
        String answer = intToTime(lastTime);
        System.out.println(answer);
    }

    static int timeToInt(String s) {
        String[] split = s.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }

    static String intToTime(int i) {
        return (i / 60 < 10 ? "0" + i / 60 : i / 60) + ":" + (i % 60 < 10 ? "0" + i % 60 : i % 60);
    }
}