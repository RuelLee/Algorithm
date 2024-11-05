/*
 Author : Ruel
 Problem : Baekjoon 32358번 근성아 일하자
 Problem address : https://www.acmicpc.net/problem/32358
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32358_근성아일하자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n번 동안 쿼리가 주어진다.
        // 처음 위치는 0이다.
        // 1 x -> x 위치에 쓰레기를 버린다.
        // 2 -> 현재 위치에서 가장 가까운 쓰레기를 줍는다. 가장 가까운 곳이 2곳 이상이라면 좌표가 적은 곳으로 이동한다.
        // 모든 쿼리를 처리 후, 이동한 거리를 출력하라.
        //
        // 우선순위큐, 시뮬레이션 문제
        // 현재 위치를 기준으로, 왼쪽은 최대힙, 오른쪽은 최소힙 우선순위큐를 통해
        // 쓰레기의 위치를 관리하며
        // 현재 위치로부터 가장 가까운 좌우 쓰레기를 비교하며, 주으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 총 이동 거리
        long sum = 0;
        // 현재 위치
        int loc = 0;
        // 현재 위치로부터 좌, 우의 쓰레기 위치를 분리하여
        // 좌는 최대힙, 우는 최소힙 우선순위큐로 관리한다.
        PriorityQueue<Integer> left = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> right = new PriorityQueue<>();
        StringTokenizer st;
        // n개의 쿼리 처리
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            // 1번 쿼리일 경우
            if (order == 1) {
                int x = Integer.parseInt(st.nextToken());
                // 기준 위치에 따라 좌우로 나누어 우선순위큐에 추가
                if (x >= loc)
                    right.offer(x);
                else
                    left.offer(x);
            } else {
                // 2번 쿼리가 들어올 경우, 모든 쓰레기를 줍는다.
                while (!(left.isEmpty() && right.isEmpty())) {
                    // 왼쪽에 더 이상 쓰레기가 없거나
                    // 가장 가까운 왼쪽 쓰레기의 위치가 가장 가까운 오른쪽 쓰레기의 위치보다 더 먼 경우
                    // 오른쪽 쓰레기를 주으러 이동한다.
                    if (left.isEmpty() ||
                            (!right.isEmpty() && loc - left.peek() > right.peek() - loc)) {
                        sum += right.peek() - loc;
                        loc = right.poll();
                    } else {        // 그 외의 경우, 왼쪽 쓰레기를 줍는다.
                        sum += loc - left.peek();
                        loc = left.poll();
                    }
                }
            }
        }
        // 총 이동 거리 출력
        System.out.println(sum);
    }
}