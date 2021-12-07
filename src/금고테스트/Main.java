/*
 Author : Ruel
 Problem : Baekjoon 2266번 금고 테스트
 Problem address : https://www.acmicpc.net/problem/2266
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 금고테스트;

import java.util.Scanner;

public class Main {
    static int[][] dp;

    public static void main(String[] args) {
        // 공(https://www.acmicpc.net/problem/2695) 문제와 같은 문제
        // 이진탐색 아니다
        // 메모이제이션을 활용해야한다
        // k개의 금고가 주어질 때, 최대 몇 층에서 떨어뜨려도 견디는지 확인하는 문제
        // 5층과 금고 한개가 주어진다면, 1층부터 하나씩 시도해보는 수밖에 없다. 최악의 경우 5회의 시도가 필요하다
        // 7층과 3개의 금고가 주어진다면, 3층에서 떨어뜨려보고, 깨진다면, 1, 2층을 시도해본다
        // 깨지지 않았다면 5층에서 떨어뜨려보고, 깨진다면, 3,4층, 깨지지 않았다면 6,7층에서 시도해보면 된다
        // 최악의 경우 3회의 시도가 필요하다
        // n층과 k금고의 개수에 대한 메모이제이션을 하며
        // 1층부터 n층 사이의 각 층에 대해서 떨어뜨렸을 때, 깨졌을 때와 깨지지 않았을 때 중 큰 값을 가져와 그 중 가장 작은 값이 최악의 경우의 최소 시도 회수가 된다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        dp = new int[n + 1][k + 1];
        System.out.println(findAnswer(n, k));
    }

    static int findAnswer(int n, int k) {
        if (dp[n][k] != 0)          // 이미 값이 있다면 바로 참고
            return dp[n][k];
        else if (k == 1)        // 금고가 하나만 남았다면 1층부터 시도해보는 수밖에 없다. 총 n회
            return dp[n][k] = n;
        else if (n <= 1)        // 1층이라면 금고의 개수가 어떻든 1회만 시도하면 된다.
            return dp[n][k] = n;

        int maxSafes = Integer.MAX_VALUE;       // n층, k개의 금고를 테스트하여 최악의 경우 최소 시도 회수를 구한다.
        for (int i = 1; i < n; i++) {       // 1층부터 n-1층까지
            int current = 0;
            // 해당 층에서 금고가 깨졌다면 1층 ~ i - 1층까지 k - 1개의 금고로 시도해보아야한다. 깨진 것도 시도회수이므로 +1
            current = Math.max(current, findAnswer(i - 1, k - 1) + 1);
            // 해당 층에서 금고가 깨지지 않았다면, i + 1 ~ n층까지, 즉, 1층부터 (n - 1)층까지 k개의 시도하는 것과 같다. 깨지지 않았더라도 시도했으므로 시도 회수 +1
            current = Math.max(current, findAnswer(n - i, k) + 1);
            // 깨지든 깨지지 않았던 두 중 큰 값을 가져와, 최악의 경우 시도 회수가 더 줄어드는지 확인하고 갱신
            maxSafes = Math.min(maxSafes, current);
        }
        // 연산한 값은 저장해두고 나중에 재사용하도록 하자.
        return dp[n][k] = maxSafes;
    }
}