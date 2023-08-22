/*
 Author : Ruel
 Problem : Baekjoon 23085번 판치기
 Problem address : https://www.acmicpc.net/problem/23085
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23085_판치기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class State {
    int coins;
    int times;

    public State(int coins, int times) {
        this.coins = coins;
        this.times = times;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 동전들에 대한 상태가 주어지고, 
        // 뒤집기를 통해 한번에 k개의 동전을 뒤집을 수 있다고 한다.
        // 총 몇 번의 뒤집기를 통해 모든 동전을 뒷면으로 만들 수 있는가?
        //
        // BFS 문제
        // 연속한이라는 조건이 붙지 않았으므로, 비연속적인 동전 k개를 한번에 뒤집을 수 있다.
        // 따라서 가능한 경우는
        // 앞면인 k개의 동전을 뒷면으로 바꾸는 경우 (=뒷면인 동전 k개 추가)
        // 앞면인 k-1개의 동전을 뒷면으로 바꾸고, 뒷면인 한개를 앞면으로 바꾸는 경우(= 뒷면인 동전 k - 2개 추가)
        // ...
        // 뒷면인 동전 k개를 앞면으로 바꾸는 경우 (= 뒷면인 동전 -k개)
        // 다시 말해, 뒷면의 동전 개수를 기준으로
        // -k개, -k+2개, ... , k-2개, k개의 동전을 뒷면으로 만들 수 있다.
        // 따라서 위 조건으로 너비 우선 탐색을 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 전체 동전 n개와 k개의 동전을 한번에 뒤집을 수 있다.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 동전들의 상태
        String s = br.readLine();
        int coins = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'T')
                coins++;
        }

        // 최종적으로 n개의 동전을 모두 뒤집고자 한다.
        // 뒷면인 동전의 개수에 따라 최소 횟수를 기록한다.
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[coins] = 0;
        
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(coins, 0));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            // 현재 앞면의 수
            int front = n - current.coins;
            // 뒷면의 수
            int back = current.coins;
            // dp에 기록된 값이 큐에 있는 값보다 더 적다면
            // 해당 값은 건너뛴다.
            if (dp[current.coins] < current.times)
                continue;

            // 가능한 모든 경우를 살펴본다.
            for (int i = 0; i <= k; i++) {
                // 앞면을 뒷면으로
                int frontToBack = k - i;
                // 뒷면을 앞면으로
                int backToFront = i;
                
                // 해당하는 상태의 동전이 개수만큼 존재하고
                // 최소 횟수를 갱신한다면
                if (front >= frontToBack && back >= backToFront &&
                        dp[back + k - 2 * i] > dp[back] + 1) {
                    // dp에 기록
                    dp[back + k - 2 * i] = dp[back] + 1;
                    // 큐 추가
                    queue.offer(new State(back + k - 2 * i, current.times + 1));
                }
            }
        }

        // 최종적으로 뒷면인 동전을 n개로 만드는데
        // 초기값인 그대로라면 불가능한 경우이므로 -1을 출력하고
        // 그 외의 기록된 dp[n]을 출력한다.
        System.out.println(dp[n] == Integer.MAX_VALUE ? -1 : dp[n]);
    }
}