/*
 Author : Ruel
 Problem : Baekjoon 17435번 합성함수와 쿼리
 Problem address : https://www.acmicpc.net/problem/17435
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 합성함수와쿼리;

import java.util.Scanner;

public class Main {
    static int[][] dp;

    public static void main(String[] args) {
        // 수열이라든지, 이런 범위가 큰 것들은 결국 이진탐색을 활용하여 탐색시간을 줄이는 것같다
        // 위 문제도 생성되는 수열의 2*n번째 값들(희소 배열이라고 부른다)을 저장해두고 이를 활용하여 탐색시간을 줄인다
        // 처음엔 희소 배열을 먼저 구해두지 않고, 구하는 요청이 왔을 때 저장하는 방식을 사용하려 했으나, 오히려 이럴 때 시간 초과가 났다.
        // 미리 희소 배열을 구해두는 것이 더 유리한가보다.
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        dp = new int[m + 1][19];
        for (int i = 1; i < dp.length; i++)
            dp[i][0] = sc.nextInt();

        // 이전 값을 활용하므로, 하나의 row를 잡아 col을 쭉 구할 수 없다
        // col을 하나씩 증가시켜가며 해당하는 row값을 모두 채워주자
        for (int col = 1; col < dp[0].length; col++) {
            for (int row = 1; row < dp.length; row++)
                dp[row][col] = dp[dp[row][col - 1]][col - 1];
        }

        int q = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int n = sc.nextInt();
            int x = sc.nextInt();

            sb.append(findAnswer(n, x)).append("\n");
        }
        System.out.println(sb);
    }

    static int findAnswer(int n, int x) {
        if (n == 1)
            return dp[x][0];

        int pow = 0;
        int calculated = 1;
        while (calculated * 2 < n) {
            pow++;
            calculated *= 2;
        }
        return findAnswer(n - calculated, dp[x][pow]);  // calculated 만큼의 function의 값이 저장되어있던 dp[x][pow]로 빠르게 대체되었다!
    }
}