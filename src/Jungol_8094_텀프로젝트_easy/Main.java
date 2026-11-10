/*
 Author : Ruel
 Problem : Jungol 8094번 텀 프로젝트 (easy)
 Problem address : https://jungol.co.kr/problem/8094
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8094_텀프로젝트_easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생과 자신이 좋아하는 학생이 주어진다.
        // 좋아하는 학생끼리 순환을 이루면 그룹으로써 프로젝트를 진행한다고 한다.
        // 그룹에 포함되지 못하는 학생의 수를 구하라
        //
        // 재귀, bfs 문제
        // 선호 학생을 쭈욱 따라가다 이미 방문했던 학생이 다시 나온다면 그 학생까지의 순환을 끊어 그룹으로 만든다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 학생
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 학생의 선호 학생
        int[] prefer = new int[n + 1];
        for (int i = 1; i <= n; i++)
            prefer[i] = Integer.parseInt(st.nextToken());

        int cnt = 0;
        // 탐색 진행 여부
        boolean[] visited = new boolean[n + 1];
        // 현재 탐색의 포함 여부
        boolean[] enqueued = new boolean[n + 1];
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= n; i++) {
            if (visited[i])
                continue;

            // i번 학생부터 탐색 시작
            stack.push(i);
            visited[i] = true;
            enqueued[i] = true;
            // 이번 학생의 선호 학생이 미방문 학생일 경우 계속 진행
            while (!visited[prefer[stack.peek()]]) {
                int next = prefer[stack.peek()];
                stack.push(next);
                visited[next] = true;
                enqueued[next] = true;
            }

            // 현재 최상단 학생의 선호 학생이
            // 방문이며, 스택에 들어있다면 해당 학생까지의 순환을 끊어 그룹으로 만든다
            if (enqueued[prefer[stack.peek()]]) {
                int to = prefer[stack.peek()];
                while (stack.peek() != to) {
                    enqueued[stack.pop()] = false;
                    cnt++;
                }
                enqueued[stack.peek()] = false;
                cnt++;
            }

            // 최상단 학생이 방문이지만, 이번 탐색에서 방문했던 학생이 아니어서 스택에는 없다면
            // 순환을 찾을 수 없는 경우. 모두 스택에서 제거
            while (!stack.isEmpty())
                enqueued[stack.pop()] = false;
        }
        // 그룹을 이룬 학생이 cnt이므로 n에서 cnt를 뺀 값을 출력
        System.out.println(n - cnt);
    }
}