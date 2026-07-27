/*
 Author : Ruel
 Problem : Jungol 11759번 모두 고유하게 만들기
 Problem address : https://jungol.co.kr/problem/11759
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_11759_모두고유하게만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // n(1 <= n <= 2 * 10^5)과 0이 아닌 k가 주어진다.
        // 원하는 만큼 하나의 원소에 +k를 하는 연산을 하여, 모든 원소가 서로 다르게 만들고자할 때
        // 최소 연산의 수는?
        //
        // 정렬, 그리디, 해쉬맵, 트리셋 문제
        // 같은 값이 중복하여 발생할 수 있고, 그로 인한 중복 연산이 많아질 수 있으므로
        // 맵을 통해 같은 값을 한 번에 k씩 증가시켜가며 연산의 수를 센다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n개의 수, 한번에 변하는 수의 값 k
            int n = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());

            // 트리맵
            // 각 수의 개수를 세어 맵에 저장
            TreeMap<Long, Integer> treeMap = new TreeMap<>();
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                long num = Long.parseLong(st.nextToken());
                treeMap.put(num, treeMap.getOrDefault(num, 0) + 1);
            }

            long cnt = 0;
            // k가 양수인 경우와 음수인 경우를 분리하여 계산
            if (k > 0) {
                while (!treeMap.isEmpty()) {
                    // 수의 값
                    long key = treeMap.firstKey();
                    // 개수
                    int num = treeMap.pollFirstEntry().getValue();

                    // 만약 num이 1 이상이라면
                    // 하나를 제외하고 나머지는 전부 k만크씩 증가시켜 다시 맵에 담는다.
                    if (num > 1) {
                        // 그 때 발생하는 연산의 횟수 누적
                        cnt += (num - 1);
                        treeMap.put(key + k, treeMap.getOrDefault(key + k, 0) + num - 1);
                    }
                }
            } else {
                while (!treeMap.isEmpty()) {
                    long key = treeMap.lastKey();
                    int num = treeMap.pollLastEntry().getValue();

                    if (num > 1) {
                        cnt += (num - 1);
                        treeMap.put(key + k, treeMap.getOrDefault(key + k, 0) + num - 1);
                    }
                }
            }
            // 답 출력
            sb.append(cnt).append("\n");
        }
        System.out.print(sb);
    }
}