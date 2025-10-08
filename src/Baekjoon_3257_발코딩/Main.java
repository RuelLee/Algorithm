/*
 Author : Ruel
 Problem : Baekjoon 3257번 발코딩
 Problem address : https://www.acmicpc.net/problem/3257
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3257_발코딩;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // 창영이와 강산이가 각각의 단어를 생각한다.
        // 그리고 하나의 메모장에 두 명이 동시에 각 단어를 입력하기 시작한다.
        // 창영이가 "weissblume", 강산이가 "exupery"를 입력하며
        // 화면에는 "weeisxsbulupmerey"를 출력한다고 하자.
        // 그럼 각 알파벳을 누가 쳤는지를
        // 11211211211212212과 같이 출력한다.
        //
        // dp 문제
        // dp[첫단어의idx][두번째단어의idx] = 이전 위치
        // 로 dp를 채워나간 후, 마지막에 끝에서부터 역추적하며 답을 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 두 명이 입력하는 각각의 단어와 화면에 출력되는 단어
        String[] words = new String[3];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();

        // dp[첫단어의idx][두번째단어의idx] = 이전 위치
        int[][] dp = new int[words[0].length() + 1][words[1].length() + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 초기 위치
        dp[0][0] = 0;

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 초기값인 경우, 건너뜀
                if (dp[i][j] == -1)
                    continue;
                
                // 현재 위치
                int loc = i * dp[0].length + j;
                // 현재 출력되는 단어의 알파벳 위치가
                // 첫 단어의 위치와 일치하는 경우
                if (i < words[0].length() && words[2].charAt(i + j) == words[0].charAt(i))
                    dp[i + 1][j] = loc;
                // 두번째 단어의 위치와 일치하는 경우
                if (j < words[1].length() && words[2].charAt(i + j) == words[1].charAt(j))
                    dp[i][j + 1] = loc;
                // 각 다음 위치에 현재 위치를 기록
            }
        }
        
        // 스택을 통해 답을 거꾸로 담음
        Stack<Integer> stack = new Stack<>();
        // dp의 마지막 위치에서 시작
        int row = dp.length - 1;
        int col = dp[0].length - 1;
        // (0, 0)이 될 때까지
        while (row != 0 || col != 0) {
            // dp[row][col]의 값을 nextRow와 nextCol로 분리
            int nextRow = dp[row][col] / dp[0].length;
            int nextCol = dp[row][col] % dp[0].length;
            // row 값이 다르면 첫번째 단어의 알파벳
            if (row != nextRow)
                stack.push(1);
            // col 값이 다르면 두번째 단어의 알파벳
            else
                stack.push(2);
            // 위치 변경
            row = nextRow;
            col = nextCol;
        }
        
        // 스택에서 꺼내며 답안 작성
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty())
            sb.append(stack.pop());
        // 답 출력
        System.out.println(sb);
    }
}