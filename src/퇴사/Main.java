/*
 Author : Ruel
 Problem : Baekjoon 14501번 퇴사
 Problem address : https://www.acmicpc.net/problem/14501
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 퇴사;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1일부터, n일까지 할 수 있는 상담이 주어진다.
        // 상담에 소요되는 시간과 보상이 주어진다.
        // 어느 상담을 골라서 진행할 경우, 주어진 n일까지 중 최대이익을 낼 수 있는가
        // 상담의 유무에 따른 최대 이익의 누적 -> DP로 풀자
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] array = new int[n][2];
        for (int i = 0; i < array.length; i++) {
            array[i][0] = sc.nextInt();
            array[i][1] = sc.nextInt();
        }

        // 0번째 행은 아무 상담도 선택하지 않았을 경우
        // 0번째 열은 0일, n+1번째 열은 n+1일 -> 상담에 얻은 이익을 익일 받는다고 풀었기 때문에 n 일 경우 n+1 일이 필요하다.
        int[][] dp = new int[n + 1][n + 2];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                // 상담이 들어오는 날 + 상담이 소요되는 날 = 이익을 받는 날.
                // 이전 일들의 이익은 해당 상담을 진행하지 않았을 때 이익과 같다.
                if (j < i + array[i - 1][0])
                    dp[i][j] = dp[i - 1][j];

                    // i번재 날에 받은 (i - 1)번째 상담 이익이 들어오는 날
                    // i + 상담 소요일 + 1 번째 날에 얻을 수 있는 최대 이익은 (i - 1)번째 상담을 진행하지 않고 얻은 이익과
                    // i번째 날 까지 얻은 이익 + i번째 날에 받은 상담을 통해 얻은 이익 중 큰 값이다.
                else if (j == array[i - 1][0] + i)
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][i] + array[i - 1][1]);

                    // i번째 날에 받은 상담의 이익에 대한 계산이 끝난 이후로는
                    // 해당 금액과 해당 상담을 진행하지 않았을 경우 얻은 이익 중 큰 값을 저장하면 된다.(상담을 했냐, 안했느냐에 따라서 이익이 갈릴 뿐 추가적인 이익은 없으므로)
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        System.out.println(dp[dp.length - 1][dp[dp.length - 1].length - 1]);
    }
}