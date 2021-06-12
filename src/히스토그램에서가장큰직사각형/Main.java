package 히스토그램에서가장큰직사각형;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // 히스토그램이 주어졌을 때, 가장 넓이가 큰 직사각형의 넓이를 구하여라.
        // 스택을 활용한 탐색.
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        while (true) {
            int n = sc.nextInt();
            if (n == 0)
                break;

            int[] board = new int[n + 2];               // 양 끝을 높이 0으로 설정해주자. 마지막에 0 값을 만나면 증가하는 모양으로 히스트로그램이 끝났더라도 넓이를 체크해줄 것이다.
            for (int i = 1; i < board.length - 1; i++)
                board[i] = sc.nextInt();

            Stack<Integer> stack = new Stack<>();
            long maxArea = 0;
            for (int i = 0; i < board.length; i++) {
                // 스택에 들어있는 idx 의 높이보다 i번째 막대의 높이가 더 낮다면
                // 자신보다 같거나 작은 높이의 막대가 나올 때까지, 스택에서 값을 pop() 하며, maxArea 값을 갱신해주자.
                while (!stack.isEmpty() && board[stack.peek()] > board[i]) {
                    // 최대값 후보 중 하나인 넓이는, i번째 막대가 아닌, 스택에 담겨있는 i번째 막대기보다 높은 막대기들로 생기는 넓이다.
                    // 높이는 스택에서 뽑은 idx 에 해당하는 높이를 가져오자
                    // 너비는 스택에 담긴 (다음 막대 + 1) 의 위치에서 (i - 1)까지의 길이를 재면 된다.
                    // -> 스택에 담겨있다는 것은 현 막대보다 작거나 같은 막대만 담겨있다. -> 반대로 안 담겨있는 막대는 현 막대보다 긴 막대이므로 고려 안해도 된다.
                    long height = board[stack.pop()];
                    int width = (i - 1) - (stack.peek() + 1) + 1;
                    maxArea = Math.max(maxArea, width * height);
                }
                stack.push(i);      // 스택에 담겨있던 i 번째보다 긴 막대들을 모두 제거하고 넓이를 계산했으므로, i 를 스택에 담아준다.
            }
            sb.append(maxArea).append("\n");
        }
        System.out.println(sb);
    }
}