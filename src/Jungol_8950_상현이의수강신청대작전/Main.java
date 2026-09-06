/*
 Author : Ruel
 Problem : Jungol 8950번 상현이의 수강신청 대작전
 Problem address : https://jungol.co.kr/problem/8950
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8950_상현이의수강신청대작전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 과목에 대해 선호도와 학점이 주어진다.
        // 총 학점이 m이하가 되게끔하면서, 선호도의 합이 최대가 되게끔하고자 할 때
        // 선택해야하는 과목의 수와 번호를 출력하라
        //
        // 배낭 문제, 역추적
        // 배낭 문제인데, 역추적을 해서 선택한 과목들을 출력해야한다.
        // dp[m]으로 m개의 학점에 대해 계산을 하며
        // choice[i][j] = i번째 과목까지 살펴봤고, 학점이 j이며 선호도의 합이 최대일 때,
        // 마지막으로 선택한 과목으로 정한다.
        // choice[n][m] = x인 경우 x를 스택에 담고, 다음은 choice[x-1][m - x의 학점]의 위치를 살펴본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 과목, 최대 이수 학점 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 과목 점수
        int[][] subjects = new int[n + 1][2];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            subjects[i][0] = Integer.parseInt(st.nextToken());
            subjects[i][1] = Integer.parseInt(st.nextToken());
        }

        // m까지 배낭
        int[] dp = new int[m + 1];
        // 선택한 과목 기록
        int[][] choice = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++)
                choice[i][j] = choice[i - 1][j];

            for (int j = m; j - subjects[i][1] >= 0; j--) {
                if (dp[j] < dp[j - subjects[i][1]] + subjects[i][0]) {
                    dp[j] = dp[j - subjects[i][1]] + subjects[i][0];
                    choice[i][j] = i;
                }
            }
        }

        // 불가능한 경우
        if (dp[m] == 0)
            System.out.println(-1);
        else {
            // 가능한 경우
            Stack<Integer> stack = new Stack<>();
            // 처음 학점은 m
            int credit = m;
            // 그 때의 선택한 과목
            stack.push(choice[n][m]);
            while (stack.peek() != 0) {
                // 학점 차감
                credit -= subjects[stack.peek()][1];
                // 다음 선택한 과목으로 이동
                stack.push(choice[stack.peek() - 1][credit]);
            }
            // 마지막에 0이 들어왔으므로 제거
            stack.pop();

            // 답안 작성
            StringBuilder sb = new StringBuilder();
            sb.append(stack.size()).append("\n");
            sb.append(stack.pop());
            while (!stack.isEmpty())
                sb.append(" ").append(stack.pop());
            // 출력
            System.out.println(sb);
        }
    }
}