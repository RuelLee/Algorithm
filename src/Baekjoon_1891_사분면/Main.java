/*
 Author : Ruel
 Problem : Baekjoon 1891번 사분면
 Problem address : https://www.acmicpc.net/problem/1891
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1891_사분면;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int d;
    static long[] pow;

    public static void main(String[] args) throws IOException {
        // 2 1
        // 3 4 와 같이 사분면으로 나누고 번호를 붙인다.
        // 그 후 각 사분면을 연속하여 사분면으로 나누는 것이 가능하다.
        // 22 21 12 11
        // 23 24 13 14
        // 32 31 42 41
        // 33 34 43 44
        // 와 같은식으로 뒤에 사분면의 번호가 다시 붙는다.
        // 길이 d의 사분면 조각 번호가 주어질 때
        // 이를 오른쪽으로 x만큼, 위로 y만큼 이동시킨 사분면 조각 번호를 구하라.
        //
        // 구현
        // 먼저 주어지는 조각 번호를 토대로 (r, c)를 구한다.
        // 그 후, (-y, +x)를 적용한 값을 구해
        // 이를 다시 조각 번호로 변환한다.

        // 2의 제곱이 계속 쓰이므로 미리 구해둔다.
        pow = new long[51];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = pow[i - 1] * 2;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 조각 번호의 길이와 조각 번호
        d = Integer.parseInt(st.nextToken());
        String num = st.nextToken();

        // 오른쪽으로 x, 위로 y만큼 이동 시킨다.
        st = new StringTokenizer(br.readLine());
        long x = Long.parseLong(st.nextToken());
        long y = Long.parseLong(st.nextToken());

        // num의 좌표 (r, c)
        long[] rc = stringToRC(num);
        // 이에 오른쪽으로 x, 위로 y만큼 이동시킨다.
        rc[0] -= y;
        rc[1] += x;
        // 해당 좌표를 조각 번호로 다시 변환하여 출력
        System.out.println(rcToString(rc));
    }

    // (r, c)를 조각 번호로 변환.
    static String rcToString(long[] loc) {
        // 만약 값의 범위를 벗어난다면 -1을 반환.
        for (long l : loc) {
            if (l < 0 || l >= pow[d])
                return "-1";
        }

        // 가장 큰 사분면부터 번호를 할당하기 시작.
        long diff = pow[d - 1];
        StringBuilder sb = new StringBuilder();
        // diff가 0보다 큰 동안 계속하여 반복
        while (diff > 0) {
            // r이 diff보다 크거나 같다면 오른쪽에 위치하는 1, 4분면 중 하나
            if (loc[0] >= diff) {
                // c가 diff보다 크거나 같다면 4사분면
                if (loc[1] >= diff) {
                    sb.append(4);
                    loc[1] -= diff;
                } else      // 그렇지 않다면 3사분면
                    sb.append(3);
                loc[0] -= diff;
            } else {        // 왼쪽에 위치하는 2, 3사분면 중 하나
                if (loc[1] >= diff) {       // c가 diff보다 크거나 같다면 1사분면
                    sb.append(1);
                    loc[1] -= diff;
                } else      // 그렇지 않다면 2사분면
                    sb.append(2);
            }
            // diff을 반으로 줄여, 해당 사분면 내의 사분면을 다시 조사
            diff /= 2;
        }
        return sb.toString();
    }

    // 조각 번호를 토대로 (r, c)를 구한다.
    static long[] stringToRC(String s) {
        long diff = pow[d - 1];
        long[] loc = new long[2];
        for (int i = 0; i < s.length(); i++) {
            // 현재 조각 번호에 따라
            switch (s.charAt(i)) {
                // 1사분면이라면 c에 diff를 더하고
                case '1' -> loc[1] += diff;
                // 3사분면이라 r에 diff
                case '3' -> loc[0] += diff;
                // 4사분면이라면 r과 c 모두에 diff를 더한다.
                case '4' -> {
                    loc[0] += diff;
                    loc[1] += diff;
                }
            }
            // 다음 사분면의 변동값은 diff / 2
            diff /= 2;
        }
        // 구한 (r, c) 반환
        return loc;
    }
}