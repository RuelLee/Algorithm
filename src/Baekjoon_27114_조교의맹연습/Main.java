/*
 Author : Ruel
 Problem : Baekjoon 27114번 조교의 맹연습
 Problem address : https://www.acmicpc.net/problem/27114
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27114_조교의맹연습;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 좌로 돌아, 우로 돌아, 뒤로 돌아를 1회 수행하는데 각각 a, b, c의 에너지가 소모된다.
        // 수행 횟수를 최소화하며, k의 에너지를 모두 사용하여 원래 방향으로 제식을 마치는데
        // 드는 최소 제식 수행 횟수는?
        //
        // DP 문제
        // 에너지와 현재 바라보고 있는 방향에 대한 정보가 필요하다.
        // 따라서 dp[남은 에너지][방향]으로 2차원 배열을 세우고
        // BFS를 돌려주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 좌 a, 우 b, 뒤 c의 에너지가 소모
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        // 갖고 있는 에너지 k
        int k = Integer.parseInt(st.nextToken());

        // 초기 값 설정
        int[][] dp = new int[k + 1][4];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 처음 상태
        dp[k][0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        // 큐에 추가
        queue.offer(k * 4);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 남은 에너지
            int energy = current / 4;
            // 바라보고 있는 방향
            int direction = current % 4;
            
            // 다음 제식 수행 횟수
            int nextCycle = dp[energy][direction] + 1;
            // 좌로 돌았을 때
            // 남은 에너지와 방향을 비교하여 해당 제식 수행 횟수가 최소라면
            if (energy - a >= 0 && dp[energy - a][(direction + 3) % 4] > nextCycle) {
                dp[energy - a][(direction + 3) % 4] = nextCycle;
                queue.offer((energy - a) * 4 + (direction + 3) % 4);
            }
            
            // 우로 돌았을 때
            if (energy - b >= 0 && dp[energy - b][(direction + 1) % 4] > nextCycle) {
                dp[energy - b][(direction + 1) % 4] = nextCycle;
                queue.offer((energy - b) * 4 + (direction + 1) % 4);
            }
            
            // 뒤로 돌았을 때
            if (energy - c >= 0 && dp[energy - c][(direction + 2) % 4] > nextCycle) {
                dp[energy - c][(direction + 2) % 4] = nextCycle;
                queue.offer((energy - c) * 4 + (direction + 2) % 4);
            }
        }

        // 에너지가 0이고, 처음 시작했던 방향인 0으로 제식을 마쳤을 때의
        // 최소 제식 수행 횟수
        // 만약 초기값 그대로라면 불가능한 경우이므로 -1을 출력
        // 아니라면 기록된 값을 출력.
        System.out.println(dp[0][0] == Integer.MAX_VALUE ? -1 : dp[0][0]);
    }
}