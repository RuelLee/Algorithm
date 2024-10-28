/*
 Author : Ruel
 Problem : Baekjoon 27163번 벚꽃 내리는 시대에 결투를
 Problem address : https://www.acmicpc.net/problem/27163
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27163_벚꽃내리는시대에결투를;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 턴 동안 공격을 버텨야한다.
        // 현재 주인공은 오라 / 라이프를 갖고 있다.
        // 공격은 오라 / 라이프 형태로 주어지는데
        // 두 수 모두 0보다 크거나 같은 경우, 둘 중 하나의 데미지를 선택하여 받을 수 있다.
        // X / Y 의 공격을 받았는데, 오라가 X보다 작을 경우, 무조건 라이프에 Y의 데미지를 받고
        // X / -1 의 공격을 받은 경우, 오라에 X의 데미지를 입는다. 데미지 후, 오라는 0으로 회복된다.
        // -1 / Y 의 공격을 받은 경우, 라이프에 Y의 데미지를 받는다.
        // n개의 턴이 주어질 때, 살아남으려면
        // 받아야하는 공격의 타입을 출력하라
        //
        // DP 문제
        // 오라가 최대 10억까지 주어지므로
        // dp[턴][남은라이프] = 남은 오라의 형태로 계산한다.
        // 공격 타입은 추후에 라이프 수치를 비교하며 역산하려했으나
        // 공격이 0인 경우도 있기 때문에, 공격 타입을 남겨두는 것이 좋다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 턴, 초기 오라 a, 초기 라이프 l
        int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        // 매턴의 공격
        int[][] attacks = new int[n][];
        for (int i = 0; i < attacks.length; i++)
            attacks[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp[턴][남은라이프] = 남은 오라
        int[][] dp = new int[n + 1][5001];
        // parents에 해당 상태에 도달하기 위한 전 상태의 idx를 남겨둔다.        
        int[][] parents = new int[n + 1][5001];
        // 해당 상태가 될 때, 받은 공격 타입
        char[][] attacked = new char[n + 1][5001];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MIN_VALUE);
        // 초기 라이프와 오라
        dp[0][l] = a;

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 초기값인 경우, 불가능한 경우이므로 건너뛴다.
                if (dp[i][j] == Integer.MIN_VALUE)
                    continue;
                
                // 현재 상태에 대한 인덱스
                int idx = i * 5001 + j;
                // 공격이 둘 다 양수인 경우
                if (attacks[i][0] >= 0 && attacks[i][1] >= 0) {
                    // 오라로 공격을 받아내는 경우
                    if (dp[i][j] >= attacks[i][0] && dp[i + 1][j] < dp[i][j] - attacks[i][0]) {
                        dp[i + 1][j] = dp[i][j] - attacks[i][0];
                        attacked[i + 1][j] = 'A';
                        parents[i + 1][j] = idx;
                    }
                    
                    // 라이프로 공격을 받아내는 경우
                    if (j > attacks[i][1] && dp[i + 1][j - attacks[i][1]] < dp[i][j]) {
                        dp[i + 1][j - attacks[i][1]] = dp[i][j];
                        attacked[i + 1][j - attacks[i][1]] = 'L';
                        parents[i + 1][j - attacks[i][1]] = idx;
                    }
                } else if (attacks[i][0] >= 0) {        // 오라로만 공격을 받아내야하는 경우
                    int remainAura = Math.max(dp[i][j] - attacks[i][0], 0);
                    if (dp[i + 1][j] < remainAura) {
                        dp[i + 1][j] = remainAura;
                        attacked[i + 1][j] = 'A';
                        parents[i + 1][j] = idx;
                    }
                } else if (j > attacks[i][1] && dp[i + 1][j - attacks[i][1]] < dp[i][j]) {      // 라이프로만 공격을 받아내야하는 경우
                    dp[i + 1][j - attacks[i][1]] = dp[i][j];
                    attacked[i + 1][j - attacks[i][1]] = 'L';
                    parents[i + 1][j - attacks[i][1]] = idx;
                }
            }
        }

        // n턴까지 버티는게 가능했던 한 상태를 찾는다.
        Stack<Integer> stack = new Stack<>();
        Stack<Character> attackedStack = new Stack<>();
        for (int i = 1; i < dp[n].length; i++) {
            if (dp[n][i] != Integer.MIN_VALUE) {
                stack.push(n * 5001 + i);
                attackedStack.push(attacked[n][i]);
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        // 그러한 경우가 없는 경우
        if (stack.isEmpty())
            sb.append("NO");
        else {      // 있는 경우
            sb.append("YES").append("\n");
            // parents를 거꾸로 찾아가며, 공격 타입들을 스택에 담는다.
            while (stack.peek() / 5001 != 1) {
                stack.push(parents[stack.peek() / 5001][stack.peek() % 5001]);
                attackedStack.push(attacked[stack.peek() / 5001][stack.peek() % 5001]);
            }
            
            // 남긴 공격 타입들을 꺼내며 답안 작성
            while (!attackedStack.isEmpty())
                sb.append(attackedStack.pop());
        }
        // 답안 출력
        System.out.println(sb);
    }
}