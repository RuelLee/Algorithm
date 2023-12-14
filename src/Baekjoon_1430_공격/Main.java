/*
 Author : Ruel
 Problem : Baekjoon 1430번 공격
 Problem address : https://www.acmicpc.net/problem/1430
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1430_공격;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 타워의 위치가 주어지고, 타워의 사정거리 r, 가지고 있는 에너지 d가 주어진다.
        // 적의 좌표는 x, y로 주어진다.
        // 타워는 자신의 사정거리 내에 있는 아군 타워에 에너지를 보내줄 수 있다.
        // 다만 보낼 때 자신이 가진 에너지를 전부 소모하여, 그 양의 반을 줄 수 있다.
        // 적에게 가할 수 있는 총 에너지의 양은 얼마인가
        //
        // BFS 문제
        // 사실 상 적까지 도달하는데, 몇 번의 아군 타워를 거쳐가야하는지 세야하는 문제이다.
        // 따라서 적으로 직접 타격이 가능한 곳의 거리는 0.
        // 직접 타격이 가능한 타워에 에너지를 보낼 수 있다면 1. ,,,
        // 식으로 모든 타워에 대해 적까지 가는데 거쳐가야하는 타워 수를 세고
        // 한번 타워를 거칠 때마다 에너지가 반감됨을 고려하여
        // 공격을 가할 수 있는 전체 에너지 합을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 타워, 사정 거리 r, 초기 에너지 d, 적의 좌표 x, y
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        
        // 각 타워의 위치
        int[][] towers = new int[n][];
        for (int i = 0; i < towers.length; i++)
            towers[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        Queue<Integer> queue = new LinkedList<>();
        // 경유해야하는 타워의 수
        int[] minDistances = new int[n];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        // 직접 타격한 타워 찾기
        for (int i = 0; i < towers.length; i++) {
            if (calcDistance(towers[i][0], towers[i][1], x, y) <= r) {
                minDistances[i] = 0;
                queue.offer(i);
            }
        }
        
        // BFS
        // 최소 경유 타워의 수 계산
        while (!queue.isEmpty()) {
            // 현재 타워
            int current = queue.poll();

            // 현재 타워를 경유하는 것이 가장 유리한 타워들을 찾는다.
            for (int i = 0; i < towers.length; i++) {
                if (calcDistance(towers[current][0], towers[current][1], towers[i][0], towers[i][1]) <= r &&
                        minDistances[i] > minDistances[current] + 1) {
                    minDistances[i] = minDistances[current] + 1;
                    queue.offer(i);
                }
            }
        }
        
        // 최소 경유 타워수를 기반으로 각 타워에서
        // 에너지를 보냈을 때 직접 타격한 타워에서 받는 에너지의 합
        double sum = 0;
        for (int dis : minDistances) {
            if (dis == Integer.MAX_VALUE)
                continue;

            sum += d / Math.pow(2, dis);
        }
        // 합 출력
        System.out.println(sum);
    }
    
    // 두 좌표의 거리 계산
    static double calcDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}