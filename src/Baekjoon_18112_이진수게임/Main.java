/*
 Author : Ruel
 Problem : Baekjoon 18112번 이진수 게임
 Problem address : https://www.acmicpc.net/problem/18112
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18112_이진수게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 이진수 l을 이진수 k로 최소 동작으로 바꾸고자 한다.
        // 동작에는 3가지 종류가 있으며
        // 1. 한 자리 숫자를 보수로 바꾸기. 맨 앞자리는 바꿀 수 없다. 101 -> 111
        // 2. 현재 수에 1 더하기 11 -> 100
        // 3. 현재 수에서 1 빼기. 현재 수가 0이라면 빼기가 불가능하다. 110 -> 101
        //
        // 비트마스킹, BFS 문제
        // 1번 동작에 대해 비트마스킹을 통한 연산이 필요하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 시작 수 l, 목표 수 k
        int l = Integer.parseInt(br.readLine(), 2);
        int k = Integer.parseInt(br.readLine(), 2);
        
        // 각 수에 이르는 최소 동작 수
        int[] minActions = new int[2 << 10];
        Arrays.fill(minActions, Integer.MAX_VALUE);
        // 시작 수에서는 0
        minActions[l] = 0;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(l);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            // 1번째 동작
            // current를 String 타입의 2진수로 변환
            // 첫번째 수는 바꿀 수 없음으로 반복문 범위를 조절
            String binary = Integer.toBinaryString(current);
            for (int i = 0; i < binary.length() - 1; i++) {
                int num;
                
                // 만약 뒤에서 i번째 수가 0이라면
                // 1로 변환하여 더해준다.
                if (binary.charAt(binary.length() - 1 - i) == '0')
                    num = current + (1 << i);
                // 만약 1이라면 해당하는 수를 빼준다.
                else
                    num = current - (1 << i);
                
                // 해당 수에 이르는 동작 수가 최소값을 갱신하는지 확인
                if (minActions[num] > minActions[current] + 1) {
                    minActions[num] = minActions[current] + 1;
                    queue.offer(num);
                }
            }
            
            // 2번 동작
            if (current + 1 < minActions.length &&
                    minActions[current + 1] > minActions[current] + 1) {
                minActions[current + 1] = minActions[current] + 1;
                queue.offer(current + 1);
            }
            
            // 3번 동작
            if (current > 0 && minActions[current - 1] > minActions[current] + 1) {
                minActions[current - 1] = minActions[current] + 1;
                queue.offer(current - 1);
            }
        }
        
        // k에 이르는 최소 동작 수 출력
        System.out.println(minActions[k]);
    }
}