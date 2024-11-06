/*
 Author : Ruel
 Problem : Baekjoon 30463번 K-문자열
 Problem address : https://www.acmicpc.net/problem/30463
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30463_K문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 서로 다른 문자의 개수가 k인 문자열을 k-문자열이라고 한다.
        // 길이가 10이고, 숫자로만 이루어진 문자열 n개가 주어진다.
        // 두 문자열 si, sj(i <= i < j <= n)을 이어붙였을 때, k 문자열이 될 수 있는
        // (i, j) 순서쌍의 개수를 구하라
        //
        // 비트마스킹 문제
        // 먼저 각 문자열을 비트마스킹하여, 개수를 센다.
        // 그 후, 두 문자열을 비트 연산 or 을 하였을 때, 비트의 개수가 k개인 경우
        // 해당 조합의 개수 만큼을 세어나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 문자열, 서로 다른 문자의 개수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 비트마스킹을 한 후, 같은 값들의 개수를 센다.
        long[] bitmasks = new long[1024];
        for (int i = 0; i < n; i++)
            bitmasks[StringToBitmask(br.readLine())]++;

        long sum = 0;
        for (int i = 1; i < bitmasks.length; i++) {
            // i가 0개인 경우 건너뜀.
            if (bitmasks[i] == 0)
                continue;

            // i만으로도 k문자열인 경우
            // i 두개를 이어붙여도 k 문자열
            // 해당 경우를 센다.
            if (countVariance(i) == k)
                sum += bitmasks[i] * (bitmasks[i] - 1) / 2;

            for (int j = i + 1; j < bitmasks.length; j++) {
                // j가 0개인 경우 건너뜀.
                if (bitmasks[j] == 0)
                    continue;

                // i or j가 k인 경우
                // 해당 조합의 개수를 누적.
                if (countVariance((i | j)) == k)
                    sum += bitmasks[i] * bitmasks[j];
            }
        }
        // 답 출력
        System.out.println(sum);
    }

    // 비트마스킹에 서로 다른 문자의 개수를 계산.
    static int countVariance(int bitmask) {
        int count = 0;
        while (bitmask > 0) {
            if (bitmask % 2 == 1)
                count++;
            bitmask /= 2;
        }
        return count;
    }
    
    // 문자열을 비트마스킹한 수로 변환
    static int StringToBitmask(String s) {
        int bitmask = 0;
        for (int i = 0; i < s.length(); i++)
            bitmask |= (1 << (s.charAt(i) - '0'));
        return bitmask;
    }
}