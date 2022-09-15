/*
 Author : Ruel
 Problem : Baekjoon 2015번 수들의 합 4
 Problem address : https://www.acmicpc.net/problem/2015
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2015_수들의합4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // A1, A2, ... , An의 n개의 수가 주어진다.
        // 이들의 부분합은 Ai ~ Aj까지의 합을 나타낸다고 한다.
        // 이 부분합들 중 그 값이 k가 되는 부분합이 몇 개 존재하는지 구하라.
        //
        // 누적합을 이용하는 문제
        // 누적합 관련 문제가 상당히 재밌는 것 같다.
        // 부분합이 k가 되는 것을 구해야하는데, n의 범위가 최대 20만, k의 범위가 최대 +-20억이므로
        // 누적합을 셀 때, 배열로 셀 수 없으므로, 해쉬맵을 통해 카운팅한다.
        // 기본적인 방법으로는 A1 ~ An까지 누적합을 구하며
        // 1. 해당 누적합이 k값을 만족하는지
        // 2. 해당 누적합 - 이전 누적합 값이 k를 만족하는 쌍이 몇 개인지 를 세어 나가면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 수의 개수
        int n = Integer.parseInt(st.nextToken());
        // 원하는 부분합 값
        int k = Integer.parseInt(st.nextToken());

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        // 누적합
        int psum = 0;
        // 만족하는 부분합의 개수
        // 21억개를 넘을 수 있으므로, long 타입으로.
        long count = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            // i까지의 누적합.
            psum += Integer.parseInt(st.nextToken());
            // 누적합 그 자체가 k를 만족한다면 count 증가.
            if (psum == k)
                count++;

            // psum - (이전 누적합)이 k를 만족하는 이전 누적합의 개수를 세고
            // 그 개수만큼 count 증가.
            if (hashMap.containsKey(psum - k))
                count += hashMap.get(psum - k);

            // i번째 누적합을 해쉬맵에 개수 추가.
            if (!hashMap.containsKey(psum))
                hashMap.put(psum, 1);
            else
                hashMap.put(psum, hashMap.get(psum) + 1);
        }
        // 총 부분합의 개수 출력.
        System.out.println(count);
    }
}