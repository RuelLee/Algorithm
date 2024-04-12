/*
 Author : Ruel
 Problem : Baekjoon 26156번 나락도 락이다
 Problem address : https://www.acmicpc.net/problem/26156
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26156_나락도락이다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1000000007;

    public static void main(String[] args) throws IOException {
        // 길이 n의 문자열 s가 주어진다.
        // 부분문자열 중 'ROCK'로 끝나는 문자열의 개수를
        // 10^9 + 7로 나눈 나머지를 출력하라
        //
        // dp, 조합 문제
        // 부분문자열에 대해 앞부분이 어떻든
        // 'ROCK'로 끝나는 경우의 수를 세야한다.
        // 따라서 R의 앞부분에는 어떤 문자열이 오든 안오든 상관이 없다.
        // 따라서 R의 앞부분에 오는 문자열의 길이로 2의 제곱을 하여 R의 위치에 값을 더한다.
        // O가 등장한 경우, 이전에 등장한 R의 경우의 수만큼을 더하고
        // 마찬가지로 C의 경우는 O의 경우의 수 만큼
        // K의 경우는 C의 경우의 수만큼을 더해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이가 n인 문자열 s
        int n = Integer.parseInt(br.readLine());
        String s = br.readLine();

        // 2의 제곱이 값이 커질 수 있으므로
        // 미리 계산하여 10^9 + 7 값으로 모듈러 처리해준다.
        long[] pow = new long[n + 1];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = (pow[i - 1] * 2) % LIMIT;

        // ROCK의 각 문자에 대한 경우의 수를 구한다.
        int[] counts = new int[4];
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'R' -> {
                    // R이 나온 경우
                    // 앞에 어떤 문자가 오든 상관이 없다.
                    // 따라서 앞에 있는 문자의 길이의 제곱만큼을 더해준다.
                    counts[0] += pow[i];
                    counts[0] %= LIMIT;
                }
                case 'O' -> {
                    // O가 나온 경우
                    // 앞에 반드시 R이 존재해야하므로
                    // R의 경우의 수만큼을 더해준다.
                    counts[1] += counts[0];
                    counts[1] %= LIMIT;
                }
                case 'C' -> {
                    // C의 경우는 O가 반드시 앞에 있어야하므로..
                    counts[2] += counts[1];
                    counts[2] %= LIMIT;
                }
                case 'K' -> {
                    // K의 경우도 C가 앞에 반드시 있어야하므로..
                    counts[3] += counts[2];
                    counts[3] %= LIMIT;
                }
            }
        }
        // ROCK으로 끝나는 가능한 경우의 수를 출력한다.
        System.out.println(counts[3]);
    }
}