/*
 Author : Ruel
 Problem : Baekjoon 16397번 탈출
 Problem address : https://www.acmicpc.net/problem/16397
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16397_탈출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 현재 수 n, 버튼을 누를 수 있는 횟수 t, 목표하는 수 g가 주어진다.
        // 두 버튼을 t회 이하로 눌러, 수 n을 g로 만들고자 한다.
        // 1. 수를 1 증가 시킨다.
        // 2. 수를 2배한 후, 가장 높은 자리 수를 1 낮춘다. (123 -> 146, 5 -> 0)
        // 단 범위는 99,999이다.
        // 따라서,
        // 2번째 버튼을 누르려면, 중간에 거쳐가는 값인 2배한 값도 99,999를 넘어서는 안된다.
        // 불가능하다면 ANG를 가능하다면 누른 횟수를 출력하라
        //
        // BFS 문제
        // BFS를 통해 각 값에 이르는 최소 버튼 횟수를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 수 n
        int n = Integer.parseInt(st.nextToken());
        // 버튼 횟수 제한 t
        int t = Integer.parseInt(st.nextToken());
        // 목표 값
        int g = Integer.parseInt(st.nextToken());

        // 각 수에 이르는 최소 버튼 누름 횟수
        int[] minButtons = new int[100_000];
        Arrays.fill(minButtons, Integer.MAX_VALUE);
        // n일 때는 0
        minButtons[n] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // current가 목표 수거나, 현재 버튼 누름 횟수 t라면
            // 더 이상 진행하지 않는다
            if (current == g || minButtons[current] == t)
                break;
            
            // 다음 수
            int next = buttonA(current);
            // 다음 수가 범위 내이고, 
            // 다음 수의 버튼 최소 누름 횟수가 갱신이 된다면
            if (next < minButtons.length && minButtons[next] > minButtons[current] + 1) {
                // 누름 횟수 반영
                minButtons[next] = minButtons[current] + 1;
                // 큐 추가
                queue.offer(next);
            }

            // 두번째 버튼을 누르기 위해서는
            // 2배한 값도 99,999를 넘어서는 안된다.
            if (current * 2 < minButtons.length) {
                next = buttonB(current);
                // 다음 수의 최소 누름 횟수를 갱신한다면 마찬가지로
                // 값 반영 후, 큐 추가
                if (minButtons[next] > minButtons[current] + 1) {
                    minButtons[next] = minButtons[current] + 1;
                    queue.offer(next);
                }
            }
        }
        
        // g에 이르는 최소 누름 횟수가 초기값이라면 불가능한 경우이므로 ANG 출력
        // 초기값이 아니라면 해당 횟수 출력
        System.out.println(minButtons[g] == Integer.MAX_VALUE ? "ANG" : minButtons[g]);
    }
    
    // 버튼 A를 누를 경우
    static int buttonA(int value) {
        return value + 1;
    }
    
    // 버튼 B를 누르는 경우
    static int buttonB(int value) {
        int answer = 0;
        for (int i = 4; i >= 0; i--) {
            if (value * 2 >= Math.pow(10, i)) {
                answer = value * 2 - (int) Math.pow(10, i);
                break;
            }
        }
        return answer;
    }
}