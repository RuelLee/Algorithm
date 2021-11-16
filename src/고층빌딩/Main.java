/*
 Author : Ruel
 Problem : Baekjoon 1328번 고층 빌딩
 Problem address : https://www.acmicpc.net/problem/1328
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 고층빌딩;

import java.util.Scanner;

public class Main {
    static final int LIMIT = 1000000007;

    public static void main(String[] args) {
        // DP 문제는 점화식을 어떻게 떠올리느냐에 대한 문제 같다..
        // 어렵다 ㅠㅠ
        // n은 전체 빌딩의 수, L은 왼쪽에서 봤을 때 볼 수 있는 빌딩의 수, R은 오른쪽에서 봤을 때 보이는 빌딩의 수
        // n, ㅣ, r이 주어질 때 가능한 빌딩의 배치 가지수는?
        // 먼저 n개의 빌딩과 n-1 빌딩과의 연관 관계를 찾아보자
        // 주어진 조건대로 빌딩을 배치하려면
        // n - 1개의 빌딩이 l - 1 , r 배치된 상태에서, 가장 작은 빌딩을 왼쪽에 덧붙여주면 된다
        // 다른 방법으로는 n - 1개의 빌딩이 l, r - 1 상태로 배치된 상태에 가장 작은 빌딩을 오른쪽에 덧붙여주면 된다
        // 마지막으로는 n - 1개의 빌딩의 l, r 상태인 상태에서 양 옆을 제외한 아무 곳에 가장 작은 빌딩을 배치해주면 된다
        // n, l, r 조건을 만족하는 방법의 개수는 위 3가지 방법 가지수에 대한 합이다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int l = sc.nextInt();
        int r = sc.nextInt();

        long[][][] dp = new long[n + 1][l + 1][r + 1];
        dp[1][1][1] = 1;        // 1개의 빌딩을 왼쪽에서, 오른쪽에서 봐도 한개로 보이는 가지수는 1가지 이다.

        for (int i = 2; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                for (int k = 1; k < dp[i][j].length; k++) {
                    dp[i][j][k] = (dp[i - 1][j - 1][k]      // i - 1개의 빌딩으로 왼쪽에선 j - 1개, 오른쪽에선 k개로 보이는 가지수에 가장 왼쪽에 제일 작은 빌딩 하나를 배치한다고 생각하자
                            + dp[i - 1][j][k - 1]           // i - 1개의 빌딩으로 왼쪽에선 j개, 오른쪽에선 k - 1개로 보이는 가지수에 가장 오른쪽에 제일 작은 빌딩 하나를 배치한다고 생각하자.
                            + dp[i - 1][j][k] * (i - 2)) % LIMIT;       // i - 1개의 빌딩으로 왼쪽에선 j, 오른쪽에선 k개 보이는 상태에서 최측면을 제외한 (i - 2)의 위치에 제일 작은 빌딩을 배치한다고 생각하자.
                }
            }
        }
        System.out.println(dp[n][l][r]);
    }
}