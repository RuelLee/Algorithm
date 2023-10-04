/*
 Author : Ruel
 Problem : Baekjoon 28069번 김밥천국의 계단
 Problem address : https://www.acmicpc.net/problem/28069
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28069_김밥천국의계단;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0번부터 n번까지의 계단이 주어진다.
        // 계단을 오르는 방법은 2가지 이다.
        // 1. 한 칸 올라간다.
        // 2. i번째 계단에서 i + [i / 2]번째 계단으로 순간이동한다.
        // n번째 계단에 정확히 k번만에 오를 수 있는지 확인하라
        //
        // BFS 문제
        // k라는 수가 주어져서 복잡하게 계산해야할 것 같지만
        // 사실 0번째 계단에서 2번 행동을 통해 제 자리에 있을 수 있으므로
        // 사실 n번째 오르는 행동의 최소 횟수를 구하고
        // k가 그보다 같거나 큰 경우에는 모두 가능하다.
        // 따라서 BFS를 통해 n번째 계단에 이르는 최소 행동 횟수를 구해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 계단 n
        int n = Integer.parseInt(st.nextToken());
        // 행동 횟수 k
        int k = Integer.parseInt(st.nextToken());
        
        // 각 계단에 이르는 최소 행동 횟수
        int[] minSteps = new int[n + 1];
        Arrays.fill(minSteps, Integer.MAX_VALUE);
        // 0번째 계단에서 시작
        minSteps[0] = 0;
        
        // 방문 체크
        boolean[] visited = new boolean[n + 1];

        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 이전에 계산한 적이 있다면 건너뛴다.
            if (visited[current])
                continue;
            
            // 방문 체크
            visited[current] = true;
            // 한 칸 올라가는 경우
            if (current + 1 < minSteps.length &&
                    minSteps[current + 1] > minSteps[current] + 1) {
                minSteps[current + 1] = minSteps[current] + 1;
                queue.offer(current + 1);
            }
            
            // 순간 이동 하는 경우
            int teleport = current + current / 2;
            if (teleport < minSteps.length && minSteps[teleport] > minSteps[current] + 1) {
                minSteps[teleport] = minSteps[current] + 1;
                queue.offer(teleport);
            }
        }
        
        // n번째 계단에 오르는 행동의 최소 횟수가 k보다 같거나 작다면 가능한 경우
        // 그렇지 않으면 불가능한 경우.
        // 경우에 따라 올바르게 답안 출력
        System.out.println(minSteps[n] <= k ? "minigimbob" : "water");
    }
}