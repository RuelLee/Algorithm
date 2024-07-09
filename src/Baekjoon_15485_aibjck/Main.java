/*
 Author : Ruel
 Problem : Baekjoon 15485번 a^i * b^j * c^k
 Problem address : https://www.acmicpc.net/problem/15485
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15485_aibjck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_000 + 7;

    public static void main(String[] args) throws IOException {
        // a, b, c로만 주어진 문자열 s가 주어졌으 ㄹ때
        // s의 부분 수열 중 a^i * b^j * c^k의 형태로 이루어진 것의 개수를 구하라
        //
        // DP 문제
        // 여태까지 등장한 a와 b와 c의 개수를 비교하며
        // 해당 a^i의 개수, a^i * b^j의 개수 a^i * b^i * c^k의 개수를 계산해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 문자열
        char[] s = br.readLine().toCharArray();

        // 2의 제곱을 미리 계산해둔다.
        int[] pow = new int[1_000_000 + 1];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = (pow[i - 1] * 2) % LIMIT;
        
        // 여태까지 등장한 a, b, c의 개수
        int[] counts = new int[3];
        // 여태까지 찾은 a^i * b^i * c^k의 개수
        long[] cases = new long[3];
        for (char c : s) {
            switch (c) {
                // a일 경우
                case 'a' -> {
                    // a의 개수 증가
                    counts[0]++;
                    // 그 때 가짓수는 2의 counts[0] 제곱 - 1개 만큼
                    // a를 0개 고르는 불가능하므로.
                    cases[0] = pow[counts[0]] - 1;
                }
                case 'b' -> {
                    // b의 개수 증가
                    counts[1]++;
                    // 현재 b로 끝나는 a^i * b^j는
                    // 이미 이전 b로 끝났던 cases[1]의 뒤에 b를 하나 추가로 붙이는 경우와
                    // 추가로 a^i * b^1의 형태로 이번 b 하나로만 끝나는 경우가 있다.
                    // 위의 경우는, 기존 경우를 남겨두고, 추가되는 형태이므로 기존 값에 2배를 해주고
                    // 아래의 경우는 a^i - 1만큼의 경우를 가지므로 cases[0]만큼을 더해준다.
                    cases[1] = cases[1] * 2 + cases[0];
                    cases[1] %= LIMIT;
                }
                case 'c' -> {
                    // c의 개수 증가.
                    counts[2]++;
                    // 마찬가지로 이전 c로 끝났던 경우에 c를 추가로 붙이는 경우
                    // cases[2]의 2배를 해주고,
                    // a^i * b^j * c^1의 형태로 이번 c 하나로만 끝나는 경우
                    // 이전에 계산해둔 cases[1] 값을 가져온다.
                    cases[2] = cases[2] * 2 + cases[1];
                    cases[2] %= LIMIT;
                }
            }
        }
        // 답안 출력
        System.out.println(cases[2]);
    }
}