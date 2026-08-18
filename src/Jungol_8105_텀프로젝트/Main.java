/*
 Author : Ruel
 Problem : Jungol 8105번 텀 프로젝트
 Problem address : https://jungol.co.kr/problem/8105
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8105_텀프로젝트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생과 각 학생이 팀을 이루고 싶은 학생이 하나씩 주어진다.
        // 팀을 이루고 싶은 학생이 순환하는 경우 해당 학생들을 한 팀으로 이룬다고 한다.
        // 이 때, 팀을 이루지 못하는 학생의 수는?
        //
        // DFS, 스택 문제
        // n이 10만으로 크므로 스택을 통한 DFS로 풀자.
        // 각 학생의 선호 학생을 따라가며 이전에 등장한 학생이 다시 등장하여 순환하는지 여부를 체크해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 학생
        int n = Integer.parseInt(br.readLine());
        // 각 학생의 선호 학생
        int[] prefer = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            prefer[i] = Integer.parseInt(st.nextToken());

        // 스택
        Stack<Integer> stack = new Stack<>();
        // 팀을 이루는 학생의 수
        int cnt = 0;
        // 팀 배정이 끝났는지 체크
        boolean[] finished = new boolean[n + 1];
        // 방문 여부
        boolean[] visited = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            // 다른 학생에서부터 시작하여 선호 학생을 따라간 경우
            // 이미 탐색이 끝났을 수도 있다. 그 경우 건너뜀.
            if (visited[i])
                continue;

            // 스택에 현재 학생을 넣고
            stack.push(i);
            // 방문 체크
            visited[i] = true;
            // 선호 학생을 계속 따라간다.
            while (!visited[prefer[stack.peek()]]) {
                stack.push(prefer[stack.peek()]);
                visited[stack.peek()] = true;
            }

            // 만약 현재 스택 최상단에 있는 학생의 선호 학생이
            // 아직 팀배정이 안 끝났다면
            // 해당 학생까지 순환이 있다.
            if (!finished[prefer[stack.peek()]]) {
                // 현재 스택 최상단부터 to 학생까지 팀으로 배정한다.
                int to = prefer[stack.peek()];
                while (stack.peek() != to) {
                    finished[stack.pop()] = true;
                    cnt++;
                }
                finished[stack.pop()] = true;
                cnt++;
                // 그리고 각 학생에게 팀 배정 여부 체크
            }
            // 팀을 이루지 못한 학생들도 팀을 이루어지지 않았다는 결과가 남았으므로
            // 배정 여부 체크
            while (!stack.isEmpty())
                finished[stack.pop()] = true;
        }
        // 답 출력
        System.out.println(n - cnt);
    }
}