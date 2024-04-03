/*
 Author : Ruel
 Problem : Baekjoon 16432번 떡장수와 호랑이
 Problem address : https://www.acmicpc.net/problem/16432
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16432_떡장수와호랑이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 날 동안 매일 9종류 중 정해진 종류의 떡을 가지고 산을 넘어간다.
        // 산에서 호랑이를 만나는데, 매일 떡을 하나씩 주되, 어제와는 다른 떡을 줘야한다.
        // n일 동안 호랑이에게 줄 수 있는 떡들을 고르라.
        //
        // DP 문제
        // 백트래킹...인줄 알았지만 DP 문제였다.
        // n이 1000까지 주어지고, 떡의 종류가 9가지이므로
        // 마지막 두 날에 어떻게해도 서로 다른 떡을 줄 수 없는 경우인 경우
        // 앞 날에 대한 모든 경우의 수를 계산하기 때문에 불가능.
        // 따라서 dp를 통해
        // dp[날][떡] = 가능한 어제 주었던 떡 중 아무 idx
        // 로 정하고 dp를 채워주고, 가능한 경우, 거꾸로 idx들을 따라가며 떡의 조합을 계산했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 날짜
        int n = Integer.parseInt(br.readLine());

        // 매일 가지고 가는 떡의 종류
        int[][] ddeoks = new int[n][];
        // dp
        // answer[날짜][떡] = 어제 주는 것이 가능한 떡의 idx
        int[][] answers = new int[n][];
        for (int i = 0; i < ddeoks.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            ddeoks[i] = new int[num];
            // dp 초기화
            answers[i] = new int[num];
            Arrays.fill(answers[i], -1);
            for (int j = 0; j < ddeoks[i].length; j++)
                ddeoks[i][j] = Integer.parseInt(st.nextToken());
        }

        // 첫날에는 어느 떡이든 가져가는 것이 가능
        Arrays.fill(answers[0], 0);
        // i번째 날
        for (int i = 1; i < ddeoks.length; i++) {
            // j번째 떡
            for (int j = 0; j < ddeoks[i].length; j++) {
                // 어제의 k번째 떡
                for (int k = 0; k < ddeoks[i - 1].length; k++) {
                    // 어제 k번째 떡으로 도달하는 경우의 수가 없는 경우
                    // 건너뛴다.
                    if (answers[i - 1][k] == -1)
                        continue;

                    // 오늘의 j번째 떡과 어제의 k번째 떡이 서로 다르다면
                    if (ddeoks[i][j] != ddeoks[i - 1][k]) {
                        // dp[i][j]에 k를 기록하고 반복문 종료
                        answers[i][j] = k;
                        break;
                    }
                }
            }
        }

        // 마지막 날에 모든 값이 -1이라면
        // 불가능한 경우. -1 출력
        if (Arrays.stream(answers[n - 1]).max().getAsInt() == -1)
            System.out.println(-1);
        else {
            // 그 외의 경우 스택을 통해 가능한 떡의 조합을 계산한다.
            Stack<Integer> stack = new Stack<>();
            // n번째 날 가능한 떡 중 하나를 아무거나 고른다.
            for (int i = 0; i < answers[n - 1].length; i++) {
                if (answers[n - 1][i] != -1) {
                    stack.push(i);
                    break;
                }
            }
            // 해당 떡으로부터 거꾸로 첫번째 날까지
            // 가능한 떡을 찾아나간다.
            int idx = n - 1;
            while (idx > 0)
                stack.push(answers[idx--][stack.peek()]);

            // 답안 작성
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++)
                sb.append(ddeoks[i][stack.pop()]).append("\n");
            // 답안 출력
            System.out.print(sb);
        }
    }
}