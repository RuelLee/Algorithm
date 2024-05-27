/*
 Author : Ruel
 Problem : Baekjoon 28423번 게임
 Problem address : https://www.acmicpc.net/problem/28423
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28423_게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n을 초기값으로 갖고 있을 때
        // 각 자리수의 합 A, 각 자리수의 곱을 B라고 한다.
        // 이 둘을 이어붙여 AB = f(n)라는 새로운 수를 만든다.
        // 예를 들어 12352라면
        // A는 13, B는 60, f(n) = 1360이다
        // n이 주어질 때, f(n), f(f(n)), ... 형태로 나아갈 때
        // 언젠가 f(x) = x 형태가 나오는지를 확인한다.
        // 나오게 된다면 g(n) = 1, 그렇지 않다면 0, 중간에 10만보다 큰 수가 한번이라도 등장한다면
        // 계산하기 어려우므로 -1로 표현한다.
        // 이후, l, r이 주어질 때
        // g(l) + ... + g(r)의 값을 구하라
        //
        // DFS
        // n이 주어지면 계속해서 f(n) 연산을 하며 값을 계산하다가
        // 10만을 넘어가거나, f(x) = x 를 찾거나, 반복 구간을 찾는다면 DFS를 종료하고
        // g(n) 값들을 지정해준다.
        // 차후에 구간에 따른 합을 계산해 답을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 구간 l, r
        int l = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        
        // 최대 10만 범위까지 검색한다.
        int[] dp = new int[100_001];
        // 초기값
        Arrays.fill(dp, -2);

        boolean[] visited = new boolean[100_001];
        for (int i = 1; i < dp.length; i++) {
            // 초기값이라면, DFS 통해 탐색
            if (dp[i] == -2)
                dfs(i, dp, visited);
        }
        
        // l ~ r 구간을 찾아 합을 구한다.
        int sum = 0;
        for (int i = l; i <= r; i++)
            sum += dp[i];
        System.out.println(sum);
    }
    
    // 합과 곱을 더해 새로운 수를 만들기 위해
    // multi의 자릿수에 해당하는 10의 제곱을 구한다.
    static int shift(int num) {
        int count = 10;
        while (num >= count)
            count *= 10;
        return count;
    }
    
    // DFS
    static int dfs(int idx, int[] dp, boolean[] visited) {
        // 값이 주어져있다면 해당 값 바로 참조
        if (dp[idx] > -2)
            return dp[idx];
        // 이미 방문했다면, 순환하는 경우
        // 0 반환
        else if (visited[idx])
            return dp[idx] = 0;
        
        // 방문 체크
        visited[idx] = true;
        // f(idx) 값을 구한다.
        int sum = 0;
        int multi = 1;
        int temp = idx;
        while (temp > 0) {
            sum += temp % 10;
            multi *= temp % 10;
            temp /= 10;
        }
        
        // 구한 f(x) = next
        int next = sum * shift(multi) + multi;
        // 만약 f(idx) = next를 찾았다면
        // 1 값을 저장하고 반환
        if (idx == next)
            dp[idx] = 1;
        // next가 10만을 넘어가면 -1 반환
        else if (next > 100_000)
            dp[idx] = -1;
        else        // 그 외의 경우에는 다시 dfs 탐색
            dp[idx] = dfs(next, dp, visited);

        // dp[idx] 값 반환
        return dp[idx];
    }
}