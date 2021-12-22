/*
 Author : Ruel
 Problem : Baekjoon 17268번 미팅의 저주
 Problem address : https://www.acmicpc.net/problem/17268
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 미팅의저주;

import java.util.Scanner;

public class Main {
    static long[] memo;
    static final int LIMIT = 987654321;

    public static void main(String[] args) {
        // DP문제!
        // 원형 테이블에 n(n은 짝수)명이 둘러 앉아 있다.
        // 이들이 둘씩 악수를 할 때, 서로 팔이 교차하는 커플이 없는 경우의 수는?
        // 사람에게 순서대로 번호를 붙이자
        // 악수를 하는 사람 사이에 사람이 홀수 명이 있다면 그 사람은 반드시 팔을 교차해야 악수를 할 수 있으므로 불가능한 경우이다
        // 따라서 악수가 가능한 방법은 바로 인접한 사람이거나, 짝수명을 건너뛴 뒤에 있는 사람이다
        // 1번 사람과 악수가 가능한 사람은, 2번, 4번, 6번... 이다
        // 이는 1번 사람이 악수하는 사람으로 두 그룹으로 나뉜다고 볼 수 있다.
        // 총 인원이 6명이고 1번이 2번과 악수하면, 0명 / 4명
        // 1번과 4번이 악수하면 2명 / 2명
        // 1번과 6명이 악수하면 0명 / 4명
        // 따라서 n명이 악수하는 가지수는 0명 / n-2명, 2명 / n-4명, ... , n-2명 / 0명이 악수하는 경우의 합으로 볼 수 있따
        // DP를 통해 순차적으로 값을 채워나가면 된다.

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        memo = new long[n / 2 + 1];     // 낭비되는 메모리를 없애기 위해 쌍 단위로 생각해준다.
        memo[0] = memo[1] = 1;      // 0쌍, 1쌍이 악수하는 가지수는 1로 초기화해준다.
        for (int i = 2; i < memo.length; i++) {     // 2쌍일 때부터, n쌍까지
            for (int j = 0; j < i; j++) {       // i쌍일 때 나눌 수 있는 가지수는 0 ~ (i - 1)까지
                memo[i] += memo[j] * memo[i - 1 - j];       // j쌍이 악수하는 경우 * (i - 1 - j )쌍이 악수하는 경우.
                memo[i] %= LIMIT;           // LIMIT으로 나머지 연산.
            }
        }
        // 최종적으로 마지막 memo[n]에 답이 들어있다.
        System.out.println(memo[memo.length - 1]);
    }
}