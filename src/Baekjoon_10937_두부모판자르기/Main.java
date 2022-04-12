/*
 Author : Ruel
 Problem : Baekjoon 10937번 두부 모판 자르기
 Problem address : https://www.acmicpc.net/problem/10937
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10937_두부모판자르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static char[][] dubu;
    // 두 두부의 등급에 대한 가격을 표시.
    static int[][] grades = new int[][]{
            {100, 70, 40, 0, 0, 0},
            {70, 50, 30, 0, 0, 0},
            {40, 30, 20, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0}
    };
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // 크기가 n * n인 두부 모판이 있다
        // 두부를 1 * 2 내지 2 * 1 모양으로 잘라서 판매한다고 한다
        // 두부의 가치는 각 칸의 품질표에 따라 정해진다
        // 두부 모판의 두부로 얻을 수 있는 최대 가치는 얼마인가?
        //
        // 메모이제이션과 비트마스킹을 활용한 문제
        // 각 두부에서 행할 수 있는 행동은 3개이다
        // 현재 두부를 담겨두는 방법
        // 오른쪽 두부와 같이 잘라 파는 방법
        // 아래 두부와 같이 잘라 파는 방법
        // 따라서 위의 두부에서 아래 두부가 잘려서 사용하지 못할 수 있기 때문에 이를 비트마스킹을 통해 표시한다.
        // 비트마스킹을 통해 다음 n개의 두부에 대해서 상태를 표시하며 계산한다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        dubu = new char[n][];
        for (int i = 0; i < n; i++)
            dubu[i] = br.readLine().toCharArray();
        // dp의 행은 각 두부의 idx. 따라서 총 n * n개의 공간이 필요하다
        // 열은 비트마스킹 자리로, 총 n개의 비트필드가 필요하므로, 2^n로 설정한다
        dp = new int[n * n][(int) Math.pow(2, n)];
        // dp는 -1로 초기화해준다.
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 첫번째 두부부터 시작.
        calcMaxBenefit(0, 0);

        System.out.println(dp[0][0]);
    }

    // 두부는 각 행 별로 분리하여 쭉 이은 하나의 열이라고 생각하자.
    // idx는 그 때의 번호, bitmask는 다음 n개의 두부에 대한 정보다.
    static int calcMaxBenefit(int idx, int bitmask) {
        // 범위를 벗어나면 0 리턴.
        if (idx >= dubu.length * dubu.length)
            return 0;

        // 메모이제이션을 통해 이미 계산된 결과라면 바로 참조.
        if (dp[idx][bitmask] != -1)
            return dp[idx][bitmask];

        // 다음은 두부에 대해서 행하는 행동이다
        // 1. idx 두부를 사용하지 않고 넘어가는 경우.
        // 현재 두부가 사용됐던 사용되지 않았던 상관없이 행할 수 있다.
        dp[idx][bitmask] = calcMaxBenefit(idx + 1, bitmask >> 1);
        // 현재 두부가 사용되지 않았고
        if ((bitmask & 1) == 0) {
            // idx두부가 행의 가장 마지막이 아니고, 오른쪽 두부가 사용되지 않았다면
            // idx 두부와 idx + 1 두부를 잘라 팔 때의 가격을 찾는다.
            if ((idx + 1) % dubu.length != 0 && (bitmask & 2) == 0)
                dp[idx][bitmask] = Math.max(dp[idx][bitmask], calcMaxBenefit(idx + 2, bitmask >> 2) + getValue(idx, idx + 1));

            // idx 두부가 속한 행이 마지막 행이 아니라면
            // idx 두부와 idx + n번 두부를 잘라 팔 때의 가격을 찾는다.
            if (idx / dubu.length < dubu.length - 1)
                dp[idx][bitmask] = Math.max(dp[idx][bitmask], calcMaxBenefit(idx + 1, (bitmask | 1 << dubu.length) >> 1) + getValue(idx, idx + dubu.length));
        }
        return dp[idx][bitmask];
    }

    // 두 두부에 대한 가치를 리턴한다.
    static int getValue(int idx1, int idx2) {
        return grades[dubu[idx1 / dubu.length][idx1 % dubu.length] - 'A'][dubu[idx2 / dubu.length][idx2 % dubu.length] - 'A'];
    }
}