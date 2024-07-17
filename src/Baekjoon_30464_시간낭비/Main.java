/*
 Author : Ruel
 Problem : Baekjoon 30464번 시간낭비
 Problem address : https://www.acmicpc.net/problem/30464
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30464_시간낭비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] dp;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        // 1 ~ n개의 칸이 있으며 학교는 n 위치에 있다.
        // 1번 칸에서 시작하며
        // 현재 방향에 따라, 현재 칸에 적힌 수만큼을 1분 동안 이동한다.
        // 현재 방향은 오른쪽이며, 이동하면서 방향은 총 2번 바꿀 수 있다.
        // 학교에 최대한 늦게 도착하고자할 때, 그 시간은?
        //
        // 메모이제이션, DP, BFS 문제
        // dp[현재위치][방향을바뀐횟수] = 학교에 도달하는데 걸리는 최대 시간
        // 으로 정하고 bottom - up 방식으로 풀었다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 학교의 위치 n
        int n = Integer.parseInt(br.readLine()) - 1;
        // 각 칸에 적힌 수
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp, 값 초기화
        dp = new int[n + 1][3];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MIN_VALUE);
        Arrays.fill(dp[n], 0);
        visited = new boolean[n + 1][3];
        
        // 0번 위치에서, 한번도 방향을 바꾸지 않은 상태로 시작할 때
        // 학교에 도달하는 최대 시간
        int answer = dfs(0, 0, nums);
        // 만약 초기값이 되돌아왔다면 불가능한 경우이므로 -1 출력
        // 그 외에는 answer 출력
        System.out.println(answer == Integer.MIN_VALUE ? -1 : answer);
    }

    // bottom - up 방식으로
    // (idx, reverse) 상태에서 (next, reverse) 혹은 (next, reverse + 1)로 진행하는 경우를 따져보고
    // 두 경우 중 더 큰 값을 선택한다.
    static int dfs(int idx, int reverse, int[] nums) {
        // 칸에 적힌 수가 0이라 무한 루프를 하는 경우이거나
        // 이미 계산할 결과라면 해당 값을 그대로 반환.
        if (dp[idx][reverse] != Integer.MIN_VALUE ||
                nums[idx] == 0 || visited[idx][reverse])
            return dp[idx][reverse];

        // 방문 체크
        visited[idx][reverse] = true;
        // 그대로 이동하는 경우.
        int next = idx + nums[idx] * (reverse == 1 ? -1 : 1);
        // 값이 범위를 벗어나지 않았다면
        if (next >= 0 && next < dp.length) {
            // next에서  reverse로 학교에 도달하는 최대 시간은 answer
            int answer = dfs(next, reverse, nums);
            // 만약 초기값이라면 불가능한 경우이므로 건너뛰고,
            // 값이 존재한다면
            // idx, reverse에서 next, reverse로 이동할 경우
            // answer + 1만큼의 시간이 걸린다.
            // 해당 값이 최대값을 갱신하는지 확인.
            if (answer != Integer.MIN_VALUE)
                dp[idx][reverse] = Math.max(dp[idx][reverse], answer + 1);
        }
        // 방향을 바꾸어서 진행하는 경우.
        if (reverse < 2) {
            next = idx + nums[idx] * (reverse == 1 ? 1 : -1);
            if (next >= 0 && next < dp.length) {
                int answer = dfs(next, reverse + 1, nums);
                if (answer != Integer.MIN_VALUE)
                    dp[idx][reverse] = Math.max(dp[idx][reverse], answer + 1);
            }
        }
        // 현재 기록된 dp[idx][revese]값을 반환한다.
        return dp[idx][reverse];
    }
}