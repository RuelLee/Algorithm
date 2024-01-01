/*
 Author : Ruel
 Problem : Baekjoon 25332번 수들의 합 8
 Problem address : https://www.acmicpc.net/problem/25332
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25332_수들의합8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기가 n인 두 배열 A와 B가 주어진다.
        // Ai + ... + Aj == Bi + ... + Bj 인 i, j쌍의 개수를 구하라 (i <= j)
        //
        // 누적합 문제
        // 수열을 차이의 누적합으로 하나의 배열로 만들어준다.
        // 그 후 순차적으로 방문하며, 현재 방문한 값이 이전에 몇 번이나 등장했는지를 세어주면 된다.
        // 현재 값과 같은 누적합을 같은 이전 값이 존재한다면 차이가 0인 구간이 등장했다는 뜻이기 때문
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기 n과 수열 A, B
        int n = Integer.parseInt(br.readLine());
        int[] as = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] bs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 차이의 누적합
        int[] diffSums = new int[n + 1];
        for (int i = 1; i < diffSums.length; i++)
            diffSums[i] = diffSums[i - 1] + as[i - 1] - bs[i - 1];

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        long count = 0;
        // 0 위치는 수열 누적합의 시작 전 초기값
        // 이 값은 첫번째 수부터, 해당 수까지 모든 수의 합이 0이 되는 경우를 센다.
        hashMap.put(0, 1);
        for (int i = 1; i < diffSums.length; i++) {
            // 만약 현재 차이누적합과 같은 값이 이전에 등장했다면
            if (hashMap.containsKey(diffSums[i])) {
                // 그 개수만큼을 세고
                count += hashMap.get(diffSums[i]);
                // 개수를 하나 증가시킨다.
                hashMap.put(diffSums[i], hashMap.get(diffSums[i]) + 1);
            } else      // 처음 등장했다면 해당 값에 1회 등장함을 남겨둔다.
                hashMap.put(diffSums[i], 1);
        }
        // 전체 쌍의 개수 출력
        System.out.println(count);
    }
}