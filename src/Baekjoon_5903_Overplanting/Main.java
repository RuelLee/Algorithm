/*
 Author : Ruel
 Problem : Baekjoon 5903번 Overplanting
 Problem address : https://www.acmicpc.net/problem/5903
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5903_Overplanting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int SIZE = 20000;
    static int[] temp = new int[SIZE + 1];

    public static void main(String[] args) throws IOException {
        // n개의 사각형의 왼쪽 위, 오른쪽 아래의 좌표가 주어진다.
        // 전체 사각형의 넓이를 구하라
        //
        // 정렬, 스위핑 문제
        // x와 y의 값의 범위가 -1만 ~ 1만까지로 주어진다.
        // int 범위 내이지만, 2차원 배열로 일일이 계산해서는 시간 초과가 난다.
        // x축으로 스위핑하며, 동시에 높이는 차분 배열을 이용하여 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 사각형
        int n = Integer.parseInt(br.readLine());

        // 사각형의 좌표들
        int[][] grasses = new int[n][4];
        StringTokenizer st;
        // 좌표의 범위가 -1만 ~ 1만이기 때문에 이를 보정하여
        // 0 ~ 2만으로 바꿔준다.
        for (int i = 0; i < grasses.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 4; j > 0; j--)
                grasses[i][j % 4] = Integer.parseInt(st.nextToken()) + 10000;
        }
        // 정렬
        Arrays.sort(grasses, (o1, o2) -> {
            for (int i = 0; i < 4; i++) {
                if (o1[i] != o2[i])
                    return Integer.compare(o1[i], o2[i]);
            }
            return 0;
        });

        // 포함된 사격형들의 y좌표들의 개수
        int[] heights = new int[SIZE + 1];
        // 현재 살펴보고 있는 범위의 높이 합
        int height = 0;
        // 넓이 반영이 끝난 마지막 x좌표 
        int lastCalcedX = 0;
        // 너비 합
        int answer = 0;
        // 현재 살펴보고 있는 범위에 대해 포함된 사각형들
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> grasses[o][2]));
        for (int i = 0; i < grasses.length; i++) {
            // i번째 사각형 차례

            // i번째 사각형과 겹치지 않는 사각형들에 대해서
            // 너비 계산을 한다.
            while (!priorityQueue.isEmpty() && grasses[priorityQueue.peek()][2] < grasses[i][0]) {
                // idx번째 사각형
                int idx = priorityQueue.poll();
                // 기존에 계산된 높이 합 * (idx 좌표의 오른쪽 끝 x좌표 - 마지막으로 너비 계산된 x 좌표)
                answer += height * (grasses[idx][2] - lastCalcedX);
                // lastCalcedX 및 높이 합 갱신
                lastCalcedX = grasses[idx][2];
                modifyLine(heights, idx, grasses, -1);
                height = calcHeight(heights);
            }

            // i번째 사각형보다 왼쪽 부분에 대해서는 너비 계산을 마친다.
            answer += height * (grasses[i][0] - lastCalcedX);
            lastCalcedX = grasses[i][0];
            // i번째 사각형 추가
            priorityQueue.offer(i);
            // 높이 합 갱신
            modifyLine(heights, i, grasses, 1);
            height = calcHeight(heights);
        }

        // 남아있는 모든 사각형들의 너비 계산
        while (!priorityQueue.isEmpty()) {
            int idx = priorityQueue.poll();
            answer += height * (grasses[idx][2] - lastCalcedX);
            lastCalcedX = grasses[idx][2];
            modifyLine(heights, idx, grasses, -1);
            height = calcHeight(heights);
        }
        System.out.println(answer);
    }

    // heights를 보고, 높이 합을 계산한다.
    // 하나의 y좌표에 여러 선들이 겹쳐있더라도, 하나만 센다.
    static int calcHeight(int[] heights) {
        int cnt = 0;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] > 0)
                cnt++;
        }
        return cnt;
    }

    // idx번째 사각형을 높이 합에서 추가 혹은 제외시킨다.
    static void modifyLine(int[] heights, int idx, int[][] grasses, int addMinus) {
        // 차분 배열 트릭
        temp[grasses[idx][1]] += addMinus;
        temp[grasses[idx][3]] -= addMinus;

        // 하나씩 살펴보며
        for (int i = 0; i < SIZE; i++) {
            // 값이 0이 아닌 경우
            if (temp[i] != 0) {
                // 해당 값을 heights에 반영
                heights[i] += temp[i];
                // i+1에도 반영
                temp[i + 1] += temp[i];
                // temp[i]는 나중에 다시 써야하므로 0으로 초기화
                temp[i] = 0;
            }
        }

        // 마지막 위치 값도 계산.
        if (temp[SIZE] != 0) {
            heights[SIZE] += temp[SIZE];
            temp[SIZE] = 0;
        }
    }
}