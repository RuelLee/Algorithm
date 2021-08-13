/*
 Author : Ruel
 Problem : Baekjoon 9252번 LCS 2
 Problem address : https://www.acmicpc.net/problem/9252
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package LCS2;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // 최장 공통 부분 수열 문제!
        // DP를 활용하여 풀 수 있다
        // ASDF ADF라는 문자열 두개가 있다고 할 때
        //    '' A S D F
        // '' 0  0 0 0 0
        // A  0  1 1 1 1
        // D  0  1 1 2 2
        // F  0  1 1 2 3
        // 위와 같은 최장 공통 부분 수열의 크기에 대해 계산할 수 있다.
        // 순차적으로 진행하며, 행과 열에 같은 문자가 등장했다면
        // 해당 문자가 등장하기 전까지의 가장 긴 길이를 나타내는 dp[row-1][col-1]에 1을 더해 값을 저장해준다
        // 같은 문자가 등장하지 않았다면, 왼쪽이나, 위쪽 중 큰 값을 가져온다
        // 현재 row, col에서 위쪽 값이 뜻하는 것은 row-1 길이와 col 길이의 문자열에서 최장 공통 문자열의 크기이며
        // 마찬가지로 왼쪽 값이 뜻하는 것은 row 길이와 col-1 길이의 문자열에서 최장 공통 부분 문자열의 크기이다.
        // 1행 1열에서 값이 1로 증가했는데, 이는 ADF의 1번째 문자와 ASDF의 1번째 문자가 같아 증가한 것이며
        // 2행 3열에서 값이 1 증가했는데 이는 ADF의 2번째 문자와 ASDF의 3번째 문자가 같아서,
        // 3행 4열에서 값이 증가한 것은 ADF의 3번째 문자와 ASDF의 4번째 문자가 같아서이다.
        // 그리고 최장 공통 부분 수열의 길이가 아닌 그 값을 구하기 위해서는
        // 최우측하단에서부터 시작하여, 왼쪽이든 위쪽이든 같은 값인 곳으로 최대한 이동
        // 그 후 자신의 왼쪽 위 값이 자신보다 1값 작다면 왼쪽 문자열에서 row에 해당하는 값을 취하거나 위쪽 문자열에서 col에 해당하는 값을 스택에 취하면 된다(어차피 같은 값)
        // 그리고 count를 하나 낮추고, 왼쪽, 위의 위치로 이동하여 위와 같은 작업을 반복. 현재 값이 0이 될 때까지 한다.
        // 그 후 값에서 스택에서 꺼내면 됨!

        Scanner sc = new Scanner(System.in);

        String[] inputs = new String[2];
        inputs[1] = sc.nextLine();
        inputs[0] = sc.nextLine();

        int[][] dp = new int[inputs[0].length() + 1][inputs[1].length() + 1];

        // DP를 채우자!
        // 해당하는 행과 열의 값이 같다면 왼쪽, 위의 값에서 +1을 한 값을 저장하고
        // 그렇지 않다면 왼쪽과 위쪽 값 중 큰 값을 가져오자
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++)
                dp[i][j] = inputs[0].charAt(i - 1) == inputs[1].charAt(j - 1) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i][j - 1], dp[i - 1][j]);
        }

        StringBuilder sb = new StringBuilder();
        int count = dp[dp.length - 1][dp[dp.length - 1].length - 1];    // 최종적으로 만들어진 최장 공통 부분 수열의 길이
        sb.append(count).append("\n");

        Stack<Character> stack = new Stack<>();
        int row = dp.length - 1;
        int col = dp[dp.length - 1].length - 1;
        // 최장 공통 부분 수열을 구하기 위해 DP를 거꾸로 추적하자
        while (row > 0 && col > 0 && count > 0) {
            if (dp[row][col] == dp[row - 1][col])   // 위쪽 값이 현재 값과 같다면 row를 하나 줄이고
                row--;
            else if (dp[row][col] == dp[row][col - 1])  // 왼쪽 값이 현재와 같다면 col을 하나 줄여가며
                col--;
            else if (dp[row][col] == dp[row - 1][col - 1] + 1) {    // 양쪽 모두 값이 다르고, 대각선 위의 값이 현재 값보다 하나 작다면
                stack.push(inputs[0].charAt(row - 1));  // 스택에 저장
                row--;      // 그리고 왼쪽 위 대각선 위치로 이동
                col--;
            }
        }
        while (!stack.isEmpty())
            sb.append(stack.pop());
        System.out.println(sb);
    }
}