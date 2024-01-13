/*
 Author : Ruel
 Problem : Baekjoon 19566번 수열의 구간 평균
 Problem address : https://www.acmicpc.net/problem/19566
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19566_수열의구간평균;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 n인 수열이 주어진다.
        // 부분 수열 중 평균이 k인 개수를 구하려한다.
        //
        // 누적합 문제
        // 어떻게 풀어야할지 고민이 많이 필요했던 문제
        // i번째까지의 누적합을 sumi라 한다면, i개의 수가 평균 k라면 이 때 이 합은 i * k 이다.
        // 이 두 수 간의 차이를 구하고
        // 이 전에 이 차이만큼을 갖는 누적합이 등장했다면, 해당 값부터 i까지의 평균이 k이다.
        // 따라서
        // i번째 수의 누적합과 i * k 값을 비교하여 차이를 구하고
        // 이전에 해당 차이가 등장했던 만큼 count를 누적시키며 부분수열의 개수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n, 구하고자 하는 평균 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        // 누적합
        long[] sums = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        for (int i = 1; i < sums.length; i++)
            sums[i] += sums[i - 1];

        HashMap<Long, Integer> hashMap = new HashMap<>();
        // 초기값
        hashMap.put(0L, 1);
        // 해당하는 부분 수열의 개수
        long count = 0;
        for (int i = 0; i < sums.length; i++) {
            // i까지의 수의 평균이 k가 되기 위해서 필요한 차이
            long diff = (long) (i + 1) * k - sums[i];
            // 만약 이전에 등장했다면
            // 해당 값부터, i까지의 평균이 k
            if (hashMap.containsKey(diff))
                count += hashMap.get(diff);

            // 해당하는 diff의 누적 개수를 해쉬맵에 추가.
            if (!hashMap.containsKey(diff))
                hashMap.put(diff, 1);
            else
                hashMap.put(diff, hashMap.get(diff) + 1);
        }

        // 찾은 부분 수열의 개수를 구한다.
        System.out.println(count);
    }
}