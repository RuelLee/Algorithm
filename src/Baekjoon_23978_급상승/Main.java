/*
 Author : Ruel
 Problem : Baekjoon 23978번 급상승
 Problem address : https://www.acmicpc.net/problem/23978
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23978_급상승;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static HashMap<Long, Integer> diffs;
    static long k;

    public static void main(String[] args) throws IOException {
        // 무한개의 코인을 소지하고 있다.
        // 특정한 n개의 날짜에 가격을 x원으로 바꿀 수 있고,
        // 해당 날짜부터 1개씩 코인을 팔 수 있다.
        // 가격을 조정할 날짜 이후로는 매일 1씩 가치가 하락한다.
        // k 이상을 현금화하고자할 때, 최소 x를 구하라
        //
        // 이분 탐색 문제
        // 다음 날짜와의 간격이 중요하다.
        // 그 사이의 간격 동안 코인을 팔 수 있기 때문.
        // 가치 조정이 되는 날 사이의 간격에 따라 분류한다.
        // 마지막 날부터는 사실상 무한한 날이므로 큰 값으로 대체.
        // 그 후, 이분 탐색을 통해 해당 x원으로 k 이상을 현금화할 수 있는 확인해가며 값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 날
        int n = Integer.parseInt(st.nextToken());
        // 목표 금액 k
        k = Long.parseLong(st.nextToken());

        // 가치 조정 날 사이의 날에 따라 분류한다.
        diffs = new HashMap<>();
        st = new StringTokenizer(br.readLine());
        long preDay = Long.parseLong(st.nextToken());
        for (int i = 1; i < n; i++) {
            long day = Long.parseLong(st.nextToken());
            long diff = day - preDay;

            if (!diffs.containsKey(diff))
                diffs.put(diff, 1);
            else
                diffs.put(diff, diffs.get(diff) + 1);
            preDay = day;
        }
        diffs.put((long) Integer.MAX_VALUE, 1);

        // 이분 탐색
        long start = 1;
        long end = Integer.MAX_VALUE;
        while (start < end) {
            long mid = (start + end) / 2;
            if (possible(mid))
                end = mid;
            else
                start = mid + 1;
        }

        // 답안 출력
        System.out.println(start);
    }

    // x가 cost일 때 k 이상을 현금화할 수 있는지 찾는다.
    static boolean possible(long cost) {
        long sum = 0;
        for (long key : diffs.keySet()) {
            // cost와 간격을 비교해 며칠 동안 팔 수 있는지 계산한다.
            // 두 값 중 더 적은 값까지 팔 수 있다.
            long num = Math.min(cost, key);
            // 등차수열의 합 : n ( a + l ) / 2 을 이용해
            // 해당 간격 동안 코인을 팔아 얻을 수 있는 이득을 구한다.
            sum += num * (cost + (cost - num + 1)) / 2 * diffs.get(key);
            
            // 현금화한 금액이 k를 넘는지 확인하고 넘는다면 true 반환
            if (sum >= k)
                return true;
        }
        // 그렇지 못한다면 false 반환.
        return false;
    }
}