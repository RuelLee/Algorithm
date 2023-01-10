/*
 Author : Ruel
 Problem : Baekjoon 17071번 숨바꼭질 5
 Problem address : https://www.acmicpc.net/problem/17071
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17071_숨바꼭질5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[][] minTimes;

    public static void main(String[] args) throws IOException {
        // 주인공은 현재 n의 위치에 있고 동생은 k의 위치에 있다.
        // 주인공은 1초 뒤에 -1, +1, x2의 위치로 이동할 수 있고
        // 동생은 + 방향으로 이동하되, 매 초마다 가속을 받아, 1초 뒤 +1, 2초 뒤 +2, ..., n초 뒤 +n 만큼을 이동한다고 한다.
        // 주인공이 동생을 만날 수 있는 최소 시간은?
        //
        // BFS 문제
        // 이되 조금 생각해볼만한 문제
        // 동생의 위치가 매 시간마다 바뀌므로, 정확한 시간에 해당 위치에 도착하기 보다는
        // 일찍 이동하여 옆 칸을 다녀오는 방식으로도 동생을 만날 수 있다.
        // 가령 주인공의 처음 위치가 3이라면, 2와 4에는 1초, 3초, 5초, ... 에 동생이 온다면 만날 수 있다.
        // 따라서 각 지점에 BFS 탐색을 하되, 홀수시간과 짝수 시간에 이르는 최소 시간을 구한 뒤
        // 동생과 만날 수 있는지를 확인하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 각 지점에 이르는 짝수, 홀수 시간에 따른 최소 시간.
        minTimes = new int[500_001][2];
        for (int[] mt : minTimes)
            Arrays.fill(mt, Integer.MAX_VALUE);
        // 처음 시작 위치
        minTimes[n][0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 각각 짝수, 홀수 시간에 대해 살펴본다.
            for (int i = 0; i < 2; i++) {
                // i == 0 짝수, i == 1 홀수
                // current 위치에 도달한 기록이 있다면
                if (minTimes[current][i] != Integer.MAX_VALUE) {
                    // current가 짝수 일 때는 current + 1은 홀수, current가 홀수라면 current + 1은 짝수
                    // +1 이동이 500_000 범위를 넘지 않고
                    // +1 이동한 결과가 해당 위치에 도달하는 최소 시간을 갱신한다면
                    if (checkRange(current + 1) && minTimes[current + 1][(i + 1) % 2] > minTimes[current][i] + 1) {
                        minTimes[current + 1][(i + 1) % 2] = minTimes[current][i] + 1;
                        queue.offer(current + 1);
                    }

                    // -1 이동
                    if (checkRange(current - 1) && minTimes[current - 1][(i + 1) % 2] > minTimes[current][i] + 1) {
                        minTimes[current - 1][(i + 1) % 2] = minTimes[current][i] + 1;
                        queue.offer(current - 1);
                    }

                    // x2 이동
                    if (checkRange(current * 2) && minTimes[current * 2][(i + 1) % 2] > minTimes[current][i] + 1) {
                        minTimes[current * 2][(i + 1) % 2] = minTimes[current][i] + 1;
                        queue.offer(current * 2);
                    }
                }
            }
        }

        // 동생의 위치를 시간에 따라 변화시켜가며
        // 주인공과 만나는지 확인한다.
        int loc = k;
        int time = 0;
        while (loc < minTimes.length) {
            // 주인공이 minTime에 도달하는 시간이 동생이 도달하는 시간보다 적고
            // 짝홀수가 같다면
            // 해당 시간에 해당 지점에서 만난다.
            if (minTimes[loc][time % 2] <= time)
                break;
            
            // 그렇지 않다면
            // 시간이 증가하며 동생 위치 이동
            loc += ++time;
        }

        // 최종적으로 500_000 이하의 위치에서 반복문이 끝났다면 해당 위치에서 동생을 만난 것.
        // 그 때 시간을 출력.
        System.out.println(loc <= 500_000 ? time : -1);
    }

    static boolean checkRange(int n) {
        return n >= 0 && n < minTimes.length;
    }
}