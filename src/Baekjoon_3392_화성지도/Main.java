/*
 Author : Ruel
 Problem : Baekjoon 3392번 화성 지도
 Problem address : https://www.acmicpc.net/problem/3392
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3392_화성지도;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 사진의 왼쪽 아래 좌표와 오른쪽 위 좌표가 주어진다.
        // 사진들을 통해 확인할 수 있는 총 넓이를 계산하라
        //
        // 스위핑, 누적합, 차분 배열 트릭 문제
        // 세그먼트 트리로도 할 수 있다고 하나, 누적합과 차분 배열 트릭을 통해 풀었다.
        // 먼저 사진들을 왼쪽 아래 x좌표에 대해 오름차순 정렬한 뒤 순서대로 살펴본다.
        // 우선순위큐를 통해 오른쪽 x좌표가 일찍 끝나는 순서대로 관리한다.
        // 이번 사진과 겹치지 않고, 오른쪽 x좌표가 이번 사진보다 작은 사진들에 대해 넓이를 계산한다.
        // 그 후, 이번 사진을 통해 y길이에 대한 변동이 생기므로, 이번 사진의 왼쪽 x까지의 넓이를 마저 계산해둔다.
        // 그 후, 이번 사진을 통해 변동되는 y값을 차분 배열 트릭을 사용할 수 있게 기록하고, 우선순위큐에 현재 사진을 추가한다.
        // 위 과정을 모든 사진에 대해 시행하고, 남은 우선순위큐에 있는 사진들에 대해서도 모두 넓이를 계산해준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 사진
        int n = Integer.parseInt(br.readLine());
        int[][] maps = new int[n][4];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++)
                maps[i][j] = Integer.parseInt(st.nextToken());
        }
        // 왼쪽 x좌표에 대해 오름차순 정렬
        Arrays.sort(maps, Comparator.comparing(o -> o[0]));

        // 우선순위큐로 현재 살펴보고 있는 사진들을 관리
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> maps[o][2]));
        // 해당하는 y에 속하는 사진이 몇 개 있는지 계산
        int[] ys = new int[30_001];
        // 차분 배열 트릭
        int[] temp = new int[30_001];

        // 마지막으로 계산된 x좌표
        int lastCalcedLoc = 0;
        // 총 누적 넓이
        int sum = 0;
        for (int i = 0; i < maps.length; i++) {
            // i번 사진의 왼쪽 x좌표 보다 오른쪽 x좌표가 더 작은 사진들을 모두 제거
            while (!priorityQueue.isEmpty() && maps[priorityQueue.peek()][2] <= maps[i][0]) {
                // 해당하는 사진
                int current = priorityQueue.poll();
                // y 길이 계산
                int length = calcLength(ys, temp);
                // 넓이 누적
                sum += (maps[current][2] - lastCalcedLoc) * length;
                // 마지막으로 계산된 x좌표 갱신
                lastCalcedLoc = maps[current][2];

                // 차분 배열 트릭으로 current의 길이에 해당하는 y좌표들 제거
                temp[maps[current][1]]--;
                temp[maps[current][3]]++;
            }
            // i번 사진의 왼쪽 x좌표 이전에 대한 넓이를 모두 계산
            // y 길이
            int length = calcLength(ys, temp);
            // 넓이 누적
            sum += (maps[i][0] - lastCalcedLoc) * length;
            // 마지막으로 계산된 x좌표 갱신
            lastCalcedLoc = maps[i][0];

            // 우선순위큐에 i 추가
            priorityQueue.offer(i);
            // 차분 배열 트릭으로 i번 사진의 y 길이 표시
            temp[maps[i][1]]++;
            temp[maps[i][3]]--;
        }
        // 남아있는 모든 사진을 제거하며 넓이 누적
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            int length = calcLength(ys, temp);
            sum += (maps[current][2] - lastCalcedLoc) * length;
            lastCalcedLoc = maps[current][2];

            temp[maps[current][1]]--;
            temp[maps[current][3]]++;
        }
        // 답 출력
        System.out.println(sum);
    }

    // 차분 배열 트릭으로 현재 y길이 계산
    static int calcLength(int[] ys, int[] temp) {
        // 0좌표에 해당하는 사진이 있는지 체크
        int sum = (ys[0] += temp[0]) > 0 ? 1 : 0;
        // 1번 부터, temp를 통해서는 차분 배열 트릭으로 누적시켜나가며
        // ys의 값이 0보다 큰 경우, 해당하는 사진이 존재하는 것이므로 sum 증가
        for (int i = 1; i <= 30000; i++) {
            if ((ys[i] += (temp[i] += temp[i - 1])) > 0)
                sum++;
        }
        // temp 배열 초기화
        Arrays.fill(temp, 0);
        // 해당하는 길이 반환
        return sum;
    }
}