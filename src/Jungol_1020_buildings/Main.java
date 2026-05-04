/*
 Author : Ruel
 Problem : Jungol 1020번 buildings
 Problem address : https://jungol.co.kr/problem/1020
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1020_buildings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 건물의 2차원 평면 위에 놓여있다.
        // 각 건물의 시작 위치와 끝 위치 그리고 높이가 주어진다.
        // 정면에서 바라본 전체 건물의 넓이를 구하라
        //
        // 우선순위큐 및 스위핑 문제
        // 왼쪽에서 오른쪽으로 쓸며, 현재 보고 있는 건물들의 끝점과 가장 높은 건물의 높이를 정리한다.
        // 건물마다, 해당 건물 시작점 이전의 넓이에 대해 처리한다.
        // 현재 건물보다 이전에 끝난 건물, 맞닿은 건물, 겹친 건물

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 건물
        int n = Integer.parseInt(br.readLine());
        // 각 건물의 시작, 끝, 높이
        long[][] buildings = new long[n][3];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                buildings[i][j] = Integer.parseInt(st.nextToken());
        }
        // 시작점에 대해 정렬
        Arrays.sort(buildings, Comparator.comparingLong(o -> o[0]));

        // 현재 보고 있는 건물들
        PriorityQueue<Integer> current = new PriorityQueue<>(Comparator.comparingLong(o -> buildings[o][1]));
        // 포함 여부를 boolean 배열로 체크
        boolean[] enqueue = new boolean[n];
        // 가장 높은 건물의 높이
        PriorityQueue<Integer> height = new PriorityQueue<>((o1, o2) -> Long.compare(buildings[o2][2], buildings[o1][2]));

        // 넓이 합
        long sum = 0;
        // 계산을 마친 x좌표
        long lastLoc = 0;
        for (int i = 0; i < buildings.length; i++) {
            // i번째 건물 이전에 끝나는 건물들에 대한 넓이 정리
            while (!current.isEmpty() && buildings[current.peek()][1] <= buildings[i][0]) {
                // 최대 높이 중 무효한 건물들을 제외
                while (!height.isEmpty() && !enqueue[height.peek()])
                    height.poll();

                // 넓이 계산
                sum += (buildings[current.peek()][1] - lastLoc) * buildings[height.peek()][2];
                // 끝 점 위치 조정
                lastLoc = buildings[current.peek()][1];
                // current의 최상단 값을 제거
                enqueue[current.poll()] = false;
            }

            // 겹쳐있는 건물 lastLoc까지의 넓이를 처리한다.
            if (!current.isEmpty()) {
                while (!height.isEmpty() && !enqueue[height.peek()])
                    height.poll();

                sum += (buildings[i][0] - lastLoc) * buildings[height.peek()][2];
            }

            // 현재 건물을 큐에 담는다.
            enqueue[i] = true;
            current.offer(i);
            height.offer(i);
            // 다시 시작점은 i번째 건물의 시작 위치
            lastLoc = buildings[i][0];
        }

        // 큐를 비워가며 나머지 넓이를 계산한다.
        while (!current.isEmpty()) {
            while (!height.isEmpty() && !enqueue[height.peek()])
                height.poll();

            sum += (buildings[current.peek()][1] - lastLoc) * buildings[height.peek()][2];
            lastLoc = buildings[current.peek()][1];
            enqueue[current.poll()] = false;
        }

        // 답 출력
        System.out.println(sum);
    }
}