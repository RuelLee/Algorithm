/*
 Author : Ruel
 Problem : Baekjoon 22866번 탑 보기
 Problem address : https://www.acmicpc.net/problem/22866
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22866_탑보기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 빌딩에 대해 높이가 주어지고
        // 각 빌딩은 자신의 높이보다 더 높은 빌딩만 볼 수 있다고 한다
        // 바라보는 방향에 높이 L인 건물 뒤에 높이가 L이하인 건물은 보이지 않는다고 한다
        // 각 빌딩에서 좌우로 보이는 빌딩의 개수와 가장 가까운 빌딩 중 건물의 번호가 작은 빌딩을 출력한다.
        //
        // 스택 문제
        // 스택을 이용하면 각각 n개의 빌딩에 대해 n번을 계산할 것을
        // 2 * n 으로 끝낼 수 있는 것 같다
        // 내림차순 모노톤 스택을 이용한다
        // 왼쪽 -> 오른쪽, 오른쪽 -> 왼쪽 두번 살펴보며
        // 스택에 현재 만나는 빌딩보다 같거나 작은 빌딩을 모두 빼준다면
        // 현재 빌딩에서 볼 수 있는 빌딩의 개수가 스택에 남아있게 된다
        // 따라서 이 과정을 왼쪽 끝에서 시작과 오른쪽 끝에서 시작 두 번 반복해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // n개의 빌딩과 입력
        int n = Integer.parseInt(br.readLine());
        int[] buildings = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 스택
        Stack<Integer> stack = new Stack<>();
        int[][] answers = new int[n][2];
        // 가장 가까운 빌딩에 대해 초기값으로 음의 값중 큰 값으로 초기화시켜준다.
        for (int i = 0; i < answers.length; i++)
            answers[i][1] = -100001;
        // 왼쪽에서 오른쪽으로 살펴보며
        for (int i = 0; i < buildings.length; i++) {
            // 스택에 현 빌딩보다 같거나 작은 빌딩이 보인다면 모두 빼준다.
            while (!stack.isEmpty() && buildings[i] >= buildings[stack.peek()])
                stack.pop();

            // 스택에 남은 빌딩은 i 빌딩에서 왼쪽으로 볼 수 있는 빌딩의 개수이므로 해당 수를 기록해주고
            answers[i][0] = stack.size();
            // 가장 가까운 빌딩 또한 기록해준다.
            if (!stack.isEmpty())
                answers[i][1] = stack.peek();

            // 현재 빌딩을 스택에 넣는다.
            stack.push(i);
        }

        // 스택을 초기화
        stack = new Stack<>();
        // 오른쪽에서 왼쪽으로 살펴본다
        for (int i = buildings.length - 1; i >= 0; i--) {
            // 마찬가지로 스택에 현 빌딩보다 같거나 작은 빌딩은 모두 제거한다.
            while (!stack.isEmpty() && buildings[i] >= buildings[stack.peek()])
                stack.pop();

            // 남은 스택의 크기가 i 빌딩에서 오른쪽으로 볼 수 있는 빌딩의 개수
            // 해당 개수를 더해주고
            answers[i][0] += stack.size();
            // 기록되어있는 가장 가까운 빌딩보다 현재 스택의 최상단 빌딩이 더 가깝다면
            // 값을 갱신해준다.
            if (!stack.isEmpty() && i - answers[i][1] > stack.peek() - i)
                answers[i][1] = stack.peek();

            // 현 빌딩을 스택에 담는다.
            stack.push(i);
        }

        // 기록된 답안들을 출력 형태에 맞게 수정하여 출력한다.
        StringBuilder sb = new StringBuilder();
        for (int[] a : answers) {
            sb.append(a[0]);
            if (a[0] != 0)
                sb.append(" ").append(a[1] + 1);
            sb.append("\n");
        }
        System.out.print(sb);
    }
}