/*
 Author : Ruel
 Problem : Baekjoon 28427번 Tricknology
 Problem address : https://www.acmicpc.net/problem/28427
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28427_Tricknology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // L <= x < y <= R을 만족하는 정수 x, y에 대해 x<= k <= y를 만족하는
        // 모든 자연수의 합이 소수가 되게 하는 정수 쌍의 개수를 구하라
        //
        // 소수 판정, 에라토스테네스의 체, 누적합 문제
        // 먼저 x ~ y까지의 합을 식으로 나타내면
        // (y - x + 1) * (x + y) / 2 로 나타낼 수 있다.
        // 이 때 위 값이 소수여만 하는 것이고
        // 2를 이항하면
        // (y - x + 1) * (x + y) = 2 * 소수가 된다
        // 만약 (y - x + 1)이 소수가 된다면 x + y가 2가 되야하고
        // 범위 상 x + y가 2가 될 수는 없다.
        // 따라서 y - x + 1이 2가 되야하고, x + y가 소수가 되어야한다.
        // y - x + 1이 2가 된다는 뜻은 항이 2개이란 뜻이고
        // x + y가 소수라는 것은 그 두 항의 합이 소수여야한다는 뜻이다.
        // 따라서 x + y가 소수인 개수를 찾으면 된다.
        // 이를 위해 에라토스테네스의 체로 소수들을 최대 999_999 범위에 대해 탐색해두고
        // x + (x+1)이 소수인 것들을 누적합 처리로 나중에 쿼리가 들어왔을 때
        // 구간 별 합을 찾기 편하도록 만들어두면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 쿼리의 개수 q
        int q = Integer.parseInt(br.readLine());
        
        // 소수 판정
        boolean[] notPrimeNumber = new boolean[1_000_000];
        notPrimeNumber[1] = true;
        for (int i = 2; i < notPrimeNumber.length; i++) {
            if (notPrimeNumber[i])
                continue;

            for (int j = 2; i * j < notPrimeNumber.length; j++)
                notPrimeNumber[i * j] = true;
        }

        // 누적합
        int[] psums = new int[500_001];
        for (int i = 2; i < 500_000; i++) {
            psums[i] += psums[i - 1];
            if (!notPrimeNumber[i + (i + 1)])
                psums[i]++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // l ~ r 범위에서 가능한 소수 합 개수를 찾는다.
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            
            // x + y에서 y는 x+1이 되므로
            // l <= x<= r-1이 된다.
            // 해당하는 구간에서의 누적합을 찾아 기록
            sb.append(psums[r - 1] - psums[l - 1]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}