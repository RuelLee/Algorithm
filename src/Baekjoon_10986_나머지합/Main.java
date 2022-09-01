/*
 Author : Ruel
 Problem : Baekjoon 10986 나머지 합
 Problem address : https://www.acmicpc.net/problem/10986
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10986_나머지합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다
        // 이 때 Ai + ... + Aj의 합이 m으로 나누어떨어지는 i, j의 쌍을 구하라.
        //
        // 누적합
        // 연속된 수들의 합에서 바로 누적합이 떠올랐다.
        // 그런데, n이 최대 100만개가 주어지므로, 일일이 쌍을 구해서는 안된다.
        // 누적합을 모듈러 연산으로 계산한 값을 생각해보자
        // 만약 i까지의 누적합의 모듈러 값이 3, j까지의 모듈러 값이 3이라면
        // Ai+1 ~ Aj까지의 합이 모듈러 연산 값 0이 됨을 알 수 있다.
        // 누적합의 모듈러 연산값을 구해되, 같은 값을 갖는 두 i, j 쌍의 개수를 계산해내면 된다.
        // 또한 그 자체로 0이라면 A1 ~ Ai까지의 모듈려 연산 값이 0이라는 점도 잊지 말자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 누적합의 모듈러 연산.
        int[] prefixSumMods = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        // 같은 모듈러 연산 값의 개수를 센다.
        HashMap<Integer, Integer> modCounts = new HashMap<>();
        for (int i = 1; i < prefixSumMods.length; i++) {
            // 누적합의 모듈러 연산.
            prefixSumMods[i] = (prefixSumMods[i - 1] + Integer.parseInt(st.nextToken())) % m;

            // 모듈러 값이 존재하지 않다면 추가
            if (!modCounts.containsKey(prefixSumMods[i]))
                modCounts.put(prefixSumMods[i], 1);
            // 존재한다면 count 증가.
            else
                modCounts.put(prefixSumMods[i], modCounts.get(prefixSumMods[i]) + 1);
        }

        // 같은 모듈러 값들 중 2개를 골라 쌍을 이루는 개수를 센다.
        long pairCount = 0;
        // 그 자체로 0인 경우는, 1 ~ i까지의 누적합이 m으로 나누어 떨어지는 경우.
        if (modCounts.containsKey(0))
            pairCount += modCounts.get(0);
        // 모듈러 값이 같은 경우가 count 만큼씩 주어진다
        // count 개 중 2개를 조합으로 뽑는 개수를 더해준다.
        for (int count : modCounts.values())
            pairCount += ((long) count * (count - 1)) / 2;

        // 최종 쌍의 개수를 출력한다.
        System.out.println(pairCount);
    }
}