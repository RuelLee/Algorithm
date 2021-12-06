/*
 Author : Ruel
 Problem : Baekjoon 6569번 몬드리안의 꿈
 Problem address : https://www.acmicpc.net/problem/6569
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 몬드리안의꿈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static long[][] dp;
    static int h, w;

    public static void main(String[] args) throws IOException {
        // 격자판 채우기(https://www.acmicpc.net/problem/1648)와 동일한 문제
        // 다만 int 범위를 넘어가는 것에 대해 제한이 없으므로, Long 타입으로 자료를 저장하자
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while (true) {
            st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            if (h == 0 && w == 0)
                break;

            // 왼쪽에서 오른쪽으로, 위에서 아래로, 한 칸마다 숫자 하나를 할당한다 -> row
            // 그리고 자신을 포함한 m개의 다음 칸에 대한 상태를 bitmask로 갖는다. -> column
            dp = new long[h * w][1 << w];
            for (long[] d : dp)
                Arrays.fill(d, -1);     // -1로 초기화
            sb.append(findNumberOfMethod(0, 0)).append("\n");
        }
        System.out.println(sb);
    }

    static long findNumberOfMethod(int idx, int bitmask) {
        if (idx == h * w && bitmask == 0)       // 0 ~ (h * w -1)까지 할당되어있으므로, h * w에 bitmask가 0으로 도착했다면 정상적으로 채운 1가지의 방법.
            return 1;
        else if (idx >= h * w)      // 이외에 bitmask가 0이 아니거나, h * w를 넘어서 도착했다면 불가능한 가지수.
            return 0;

        if (dp[idx][bitmask] != -1)         // 이미 계산된 방법이라면 바로 참고.
            return dp[idx][bitmask];

        if ((bitmask & 1) == 1)         // 이번 칸이 옆칸이나 위 칸에서 1 * 2 or 2 * 1 타일로 채워졌다면 다음 칸으로 넘어간다.
            return dp[idx][bitmask] = findNumberOfMethod(idx + 1, bitmask >> 1);
        else {
            // 이번 칸이 비어있다면
            // 아래 칸은 항상 비어있으므로 1 * 2 타일을 놓아 이번 칸과 아래 칸을 채운다.
            // 타일을 놓고, w번째 bit에 마스킹해주고, idx + 1번째로 넘기므로 오른쪽으로 쉬프트 연산해준다.
            long count = findNumberOfMethod(idx + 1, (bitmask | (1 << w)) >> 1);
            // 이번 칸이 가장 오른쪽 칸이 아니고, 옆 칸이 비어있다면
            // 2 * 1 타일을 놓을 수 있다.
            // idx + 2로 넘겨주고, bit는 오른쪽으로 2번 쉬프트 연산해준다.
            if (((idx % w) != w - 1) && (bitmask & 2) == 0)
                count += findNumberOfMethod(idx + 2, bitmask >> 2);
            // 계산된 count를 dp에 저장해주고 반환.
            return dp[idx][bitmask] = count;
        }
    }
}