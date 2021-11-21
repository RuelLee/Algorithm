/*
 Author : Ruel
 Problem : Baekjoon 1670번 정상 회담2
 Problem address : https://www.acmicpc.net/problem/1670
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 정상회담2;

import java.util.Scanner;

public class Main {
    static long[] dp;
    static final int LIMIT = 987654321;

    public static void main(String[] args) {
        // 원형 탁자로 짝수 n명의 정상들이 앉았고, 서로 악수를 한다
        // 두 쌍의 손이 서로 교차하는 형태의 악수는 불가능하다고 한다
        // 예를 들어 4명이 있을 때, 〓 || 형태의 악수는 가능하지만 X 형태의 악수는 불가능하다.
        // n이 주어질 때 가능한 악수의 가지 수를 구하여라.
        // 메모이제이션을 활용한 문제!
        // 한 명을 기준으로 그 사람과 악수할 사람을 정한다(이 때 손이 교차해서는 안되므로 짝수 명으로 건너 뛴 사람과만 악수가 가능하다)
        // 그렇게 하면 자신과 악수한 사람을 기준으로 왼쪽과 오른쪽으로 그룹이 나뉘게 된다.
        // 만약 2m명이 악수를 하는데, 내가 2k명만큼을 건너뛴 사람과 악수를 한다고 하자. 그러면 왼쪽에는 건너뛴 사람만큼인 2k명이 남게 되고
        // 오른쪽에는 2m - (2k+2)만큼이 남게 된다. 이에 대해 각각 2k명이 악수할 수 있는 가지 수 * (2m - (2k+2))명이 악수 할 수 있는 가지수를 구하면 된다

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        dp = new long[n + 1];
        dp[0] = 1;
        System.out.println(findCounts(n));
    }

    static long findCounts(int n) {     // n명이 악수를 한다.
        if (dp[n] != 0)     // 이미 메모이제이션으로 값이 구해져있다면 바로 참고한다.
            return dp[n];

        long multi = 0;         // 아니라면 총 악수의 회수는
        for (int i = 0; i < n; i += 2) {        // i는 0명을 건너뛴 경우(옆 사람과 악수하는 경우부터 2명씩 늘려간다)
            // i명을 건너 뛰었기 때문에, i명이 서로 악수하는 경우 * (나머지 인원인 i명과 기준이 되는 나와 상대방을 제외한) n - 2 - i명이 서로 악수하는 경우
            multi += findCounts(i) * findCounts(n - 2 - i);
            multi %= LIMIT;
        }
        // 해당 값을 메모이제이션으로 기억해주고, 리턴해주자.
        return dp[n] = multi;
    }
}