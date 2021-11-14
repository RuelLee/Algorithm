/*
 Author : Ruel
 Problem : Baekjoon 13392번 방법을 출력하지 않는 숫자 맞추기
 Problem address : https://www.acmicpc.net/problem/13392
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 방법을출력하지않는숫자맞추기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[][] dp;
    static String original;
    static String target;

    public static void main(String[] args) throws IOException {
        // DP 문제는 정말 어렵다..... ^^.......
        // 이차원 배열로 DP를 설정하고, dp[i][j]라 할 때, i는 (i + 1)번째 나사,
        // j는 i번째 나사까지 왼쪽으로 돌린 회수 % 10 의 경우를 나타내고, 값으로는 그 때의 회전의 최소값을 담는다.
        // 예를 들어, dp[i][j]에 최소값을 알아내는 경우는 현재 i+1 나사의 숫자를 찾고, 원하는 값으로 맞추기 위해서는
        // 왼쪽으로 a 번, 오른쪽으로 b 번 돌리면 가능한지 각각 구한다
        // 먼저 왼쪽으로 돌릴 경우, dp[i + 1][(j + a) % 10] + a값이 왼쪽으로 돌릴 경우 가능한 값을 것이고
        // (현재 나사를 a번 왼쪽으로 돌리므로, i + 2 번째 나사로서는 j + a 번 왼쪽으로 돌아간 경우다. 여기서 i + 1번째 나사가 돌아가는 a를 더한 값이 왼쪽으로 돌렸을 때 최소 회전 수)
        // 오른쪽으로 돌릴 경우, dp[i + 1][j] + b 값이 가능한 값일 것이다.
        // (현재 나사를 b번 왼쪽으로  돌리므로, i + 2번째 나사로는 j번 왼쪽으로 돌아간 경우 그대로의 값을 가져오고, i + 1번째 나사로는 오른쪽으로 돌아간 경우도 합산해야하므로 + b가 된다.
        // 이러한 방법으로 마지막 나사부터 값을 채워가며, 0번째 나사
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        dp = new int[n][10];
        original = br.readLine();       // 원래 상태
        target = br.readLine();         // 원하는 상태

        findMinTurn(0, 0);      // 0번째 나사가 0번 왼쪽으로 돌아간 상태(초기 상태)일 때 첫번째 첫번째부터 마지막 나사까지 원하는 모양으로 맞춰지는 최소 회전 수를 구한다.
        System.out.println(dp[0][0]);
    }

    static int findMinTurn(int n, int leftTurn) {
        if (dp[n][leftTurn] != 0)       // 0이 아니라면 이미 전에 값을 찾은 경우. 바로 최소 회전수를 리턴해주자.
            return dp[n][leftTurn];

        int currentNum = (original.charAt(n) - '0' + leftTurn) % 10;        // n번째 나사의 현재 값
        int targetNum = target.charAt(n) - '0';             // n번째 나사에 원하는 값
        int needLeftTurn = currentNum <= targetNum ? targetNum - currentNum : 10 - currentNum + targetNum;      // 왼쪽으로 돌릴 경우 회전 수
        int needRightTurn = targetNum <= currentNum ? currentNum - targetNum : currentNum + 10 - targetNum;     // 오른쪽으로 돌릴 경우 회전 수

        if (n == dp.length - 1)         // 마지막 나사인 경우에는, 왼쪽으로 돌리든 오른쪽으로 돌리든 두 값 중 최소값을 저장하고 리턴해주자.
            return dp[n][leftTurn] = Math.min(needLeftTurn, needRightTurn);

        // n번째 나사를 왼쪽으로 needLeftTurn 만큼 돌린다면, n + 1번째 나사를 (leftTurn + needLeftTurn) % 10 만큼 왼쪽으로 돌렸을 때의 최소 회전 수에 추가로 needLeftTurn 만큼을 회전시켜야한다.
        int totalTurnWhenLeftTurn = findMinTurn(n + 1, (leftTurn + needLeftTurn) % 10) + needLeftTurn;
        // n번째 나사를 오른쪽으로 needRightTurn 만큼 돌린다면 n + 1번째 나사를 leftTurn 만큼 오른쪽으로 돌렸을 때의 최소 회전 수에 추가로 needRightTurn 만큼을 회전시켜야한다.
        int totalTurnWhenRightTurn = findMinTurn(n + 1, leftTurn) + needRightTurn;
        // 두 값 중 작은 값을 dp에 저장하고 리턴하자.
        return dp[n][leftTurn] = Math.min(totalTurnWhenLeftTurn, totalTurnWhenRightTurn);
    }
}