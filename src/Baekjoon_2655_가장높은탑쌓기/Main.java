/*
 Author : Ruel
 Problem : Baekjoon 2655번 가장 높은 탑 쌓기
 Problem address : https://www.acmicpc.net/problem/2655
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2655_가장높은탑쌓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    static int[][] memo;
    static int[][] bricks;

    public static void main(String[] args) throws IOException {
        // n개의 벽돌에 대해
        // 밑면의 넓이, 높이, 무게가 주어진다.
        // 벽돌은 자신보다 넓이와 무게가 더 적은 벽돌만 위에 쌓을 수 있다.
        // 벽돌을 쌓아 최대 높이를 만들고자할 때
        // 이 때 사용되는 벽돌의 수와 가장 위의 벽돌부터 순서대로 출력하라
        //
        // 메모이제이션 문제
        // 메모이제이션을 통해, 각 벽돌에서부터 쌓을 수 있는 최대 높이와, 바로 위에 쌓이는 벽돌을 기록해둔다.
        // 추후 마지막에 각 벽돌을 주춧돌로 했을 때, 쌓을 수 있는 최대 높이를 비교하며
        // 가장 아래 벽돌을 구하고, 기록된 자신의 위에 쌓인 벽돌을 통해 쌓인 순서를 파악하고 답안을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 벽돌
        int n = Integer.parseInt(br.readLine());

        // 각 벽돌들
        bricks = new int[n + 1][];
        for (int i = 1; i < bricks.length; i++)
            bricks[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 벽돌로부터 위에 다른 벽돌을 쌓았을 때 최대 높이와
        // 바로 위에 쌓인 벽돌의 번호를 기록한다.
        memo = new int[n + 1][2];
        int max = 0;
        for (int i = 1; i < bricks.length; i++)
            max = Math.max(max, findAnswer(i, new boolean[n + 1]));

        // 가장 주춧돌을 구한다.
        int headStone = 0;
        for (int i = 1; i < bricks.length; i++) {
            if (memo[i][0] > memo[headStone][0])
                headStone = i;
        }

        // 주춧돌을 토대로 스택을 통해
        // 가장 아래 벽돌부터 가장 위 벽돌까지 계산해나간다.
        Stack<Integer> stack = new Stack<>();
        stack.push(headStone);
        while (memo[stack.peek()][1] != 0)
            stack.push(memo[stack.peek()][1]);

        StringBuilder sb = new StringBuilder();
        // 현재 사용된 벽돌의 개수와
        sb.append(stack.size()).append("\n");
        // 스택을 역순으로 뽑아내며 위에서부터의 벽돌을 순서대로 기록하고
        while (!stack.isEmpty())
            sb.append(stack.pop()).append("\n");
        // 답안을 출력한다.
        System.out.print(sb);
    }

    static int findAnswer(int idx, boolean[] visited) {
        // 이미 계산된 결과값이 있다면 바로 참고한다.
        if (memo[idx][0] != 0)
            return memo[idx][0];

        // 방문 체크
        visited[idx] = true;
        // 다른 벽돌들을 살펴보며
        for (int i = 1; i < bricks.length; i++) {
            // idx 위에 쌓을 수 있는 벽돌인지 확인하고, 아직 사용되지 않은 벽돌이라면
            if (!visited[i] && bricks[idx][0] > bricks[i][0] && bricks[idx][2] > bricks[i][2]) {
                // i 벽돌로부터 쌓을 수 있는 최대 높이를 구하고
                int maxHeight = findAnswer(i, visited);
                // 그 높이가 다른 벽돌을 idx 벽돌 위에 놓았을 때보다 더 높다면
                // memo 값을 수정해준다.
                if (maxHeight > memo[idx][0]) {
                    memo[idx][0] = maxHeight;
                    memo[idx][1] = i;
                }
            }
        }
        // idx 위에 쌓을 수 있는 모든 벽돌을 살펴보았다.
        
        // memo[idx][0]에는 idx 자신의 높이가 포함되지 않았으므로 값을 더해주고
        memo[idx][0] += bricks[idx][1];
        // 방문 체크 해제
        visited[idx] = false;
        // 쌓을 수 있는 최대 높이 반환.
        return memo[idx][0];
    }
}