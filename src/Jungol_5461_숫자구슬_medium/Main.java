/*
 Author : Ruel
 Problem : Jungol 5461번 숫자구슬(medium)
 Problem address : https://jungol.co.kr/problem/5461
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5461_숫자구슬_medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] bids;
    static int m;

    public static void main(String[] args) throws IOException {
        // n개의 구슬이 일렬로 있다.
        // 이 구슬을 연속한 구슬들로 m개 이하의 그룹으로 나눠,
        // 해당 그룹에 속한 구슬의 합들 중 최댓값이 최소가 되게끔 만들고자 한다.
        // 가능한 최댓값은?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해, 값의 범위를 좁혀나가며 답을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구슬, m개 이하의 그룹
        int n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 구슬 입력
        bids = new long[n];
        long start = 1;
        long end = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < bids.length; i++) {
            // end는 모든 구슬의 합
            end += (bids[i] = Integer.parseInt(st.nextToken()));
            // start는 단일 구슬의 최댓값
            start = Math.max(start, bids[i]);
        }

        // 이분 탐색
        while (start < end) {
            long mid = (start + end) / 2;
            if (limitMaxSum(mid))
                end = mid;
            else
                start = mid + 1;
        }
        // 답 출력
        System.out.println(start);
    }

    // 한 그룹의 합을 sum이하면서, m개 그룹으로 나눌 수 있는지 확인한다.
    static boolean limitMaxSum(long sum) {
        int group = 1;
        long currentSum = 0;
        for (int i = 0; i < bids.length; i++) {
            // i번째 구슬까지 이전 그룹에 속해도 sum이하인 경우, 합 누적
            if (currentSum + bids[i] <= sum)
                currentSum += bids[i];
            // 단일 구슬이 sum을 넘거나 그룹이 m개 초과가 되는 경우 false 반환
            else if (bids[i] > sum || ++group > m)
                return false;
            else        // 그 외의 경우 새로운 그룹에 i번째 그룹을 처음 구슬로 시작
                currentSum = bids[i];
        }
        // 반복문을 통과한 경우 true 반환
        return true;
    }
}