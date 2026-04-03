/*
 Author : Ruel
 Problem : Baekjoon 2171번 직사각형의 개수
 Problem address : https://www.acmicpc.net/problem/2171
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2171_직사각형의개수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final long BIAS = 1_000_000_000L;
    static final long MOD = BIAS * 2 + 1;

    public static void main(String[] args) throws IOException {
        // 2차원 평면 위의 점 n개가 주어진다.
        // 서로 다른 네 개의 점을 이어 x축과 y축에 평행한 직사각형을 만들고자 한다.
        // 만들 수 있는 직사각형의 수는?
        //
        // 정렬, 두 포인터 문제
        // 각 x좌표에 따라 해쉬맵, y좌표에 따라 해쉬셋으로 풀려고 하니 메모리 초과.
        // 극단적으로 메모리를 아껴보자 싶어, 1차원 배열로 n개의 점들의 입력을 받고,
        // 정렬 후, 서로 다른 x좌표 집합끼리 살펴보며 같은 y좌표의 개수를 두 포인터로 세어주었다.
        // 그 후, 가능한 직사각형의 개수를 누적.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 점
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;

        // 각 점들
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i] = (Integer.parseInt(st.nextToken()) + BIAS) * MOD + (Integer.parseInt(st.nextToken()) + BIAS);
        }
        // 정렬
        Arrays.sort(arr);

        // 서로 다른 x 좌표의 개수
        int cnt = 0;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] / MOD != arr[i + 1] / MOD)
                cnt++;
        }
        // 각 다른 x좌표 집합의 시작 idx
        int[] district = new int[cnt + 2];
        cnt = 1;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] / MOD != arr[i + 1] / MOD)
                district[cnt++] = i + 1;
        }
        // 마지막은 집합의 끝
        district[cnt] = arr.length;

        int answer = 0;
        // i번 집합과
        for (int i = 0; i < district.length - 2; i++) {
            // j번 집합은 서로 다른 x좌표를 가짐
            for (int j = i + 1; j < district.length - 1; j++) {
                // 각 집하에서의 시작 idx
                int idx1 = district[i];
                int idx2 = district[j];

                cnt = 0;
                // 두 포인터를 통해 같은 y좌표를 갖는 점의 개수를 센다.
                while (idx1 < district[i + 1] && idx2 < district[j + 1]) {
                    long y1 = arr[idx1] % MOD;
                    long y2 = arr[idx2] % MOD;

                    if (y1 < y2)
                        idx1++;
                    else if (y1 == y2) {
                        cnt++;
                        idx1++;
                        idx2++;
                    } else
                        idx2++;
                }
                // 같은 y좌표 점의 개수를 통해 만들 수 있는 직사각형의 수를 누적
                answer += cnt * (cnt - 1) / 2;
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}