/*
 Author : Ruel
 Problem : Jungol 4424번 제자리멀리뛰기
 Problem address : https://jungol.co.kr/problem/4424
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4424_제자리멀리뛰기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 축 위에 시작 위치 0와 도착 위치 d가 주어진다.
        // 그 사이에는 n개의 돌들이 있으며, 이중 m개를 제외하여
        // 인접한 돌들의 거리 중 최솟값이 최대가 되게끔하고자 한다.
        // 이 때, 인접한 돌들의 거리중 최솟값은?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해 가능한 범위를 좁혀나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 0 ~ d까지의 거리에 n개의 돌이 있으며, m개의 돌을 제거할 수 있다.
        int d = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 돌들의 입력
        // 시작 위치와 도착 위치도 돌이 있다고 가정한다.
        int[] stones = new int[n + 2];
        for (int i = 1; i <= n; i++)
            stones[i] = Integer.parseInt(br.readLine());
        stones[n + 1] = d;
        // 정렬
        Arrays.sort(stones);

        // 이분 탐색
        int start = 1;
        int end = d;
        while (start <= end) {
            // 최소 거리를 mid로 만들 수 있는지 확인
            int mid = (start + end) / 2;
            // 가능한 경우 mid + 1 ~ end까지의 범위로 재탐색
            if (isValid(mid, stones, m))
                start = mid + 1;
            else        // 불가능할 경우 start ~ mid -1의 범위로 재탐색
                end = mid - 1;
        }
        // 가능한 값을 출력
        System.out.println(end);
    }

    // stones의 돌중 m개를 제거해 최소 거리를 min으로 만들 수 있는지 확인한다.
    static boolean isValid(int min, int[] stones, int m) {
        // 제거한 돌의 개수
        int cnt = 0;
        // 현재 돌
        int current = 0;
        // 다음 돌
        int next;
        while (current < stones.length && cnt <= m) {
            // current에서 next의 돌로 이동
            next = current + 1;
            // 만약 그 거리가 min 미만이라면, next를 제거하고 다음 돌로 넘어간다.
            // 그러던 중 cnt가 m을 초과하는 경우 반복문을 종료한다.
            while (next < stones.length && stones[next] - stones[current] < min) {
                next++;
                if (++cnt > m)
                    break;
            }
            // 다음 돌로 이동
            current = next;
        }
        // 제거한 돌의 개수가 m 이하인지 확인
        return cnt <= m;
    }
}