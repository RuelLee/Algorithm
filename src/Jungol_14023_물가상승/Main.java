/*
 Author : Ruel
 Problem : Jungol 14023번 물가 상승
 Problem address : https://jungol.co.kr/problem/14023
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_14023_물가상승;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 음식에 대한 가격이 각각 주어진다
        // 다음 2개의 연산 중 하나를 q일 동안 매일 하나씩 실행한다.
        // 1. INFLATION x : 모든 음식 가격에 x를 더한다.
        // 2. SET x y : 현재 가격이 x인 음식을 모두 y로 만든다.
        // 매일 모든 음식의 가격 합을 출력하라
        //
        // 해쉬 맵 문제
        // 인플레이션은 모든 가격의 음식이 일정한 값으로 상승하므로, 인플레이션 수치는 따로 계산하고
        // 나머지에 대해서, 해쉬맵 통해 가격과 개수 그리고 합을 미리 계산해둔다.
        // 그리고 각 연산을 처리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 종류의 음식
        long n = Integer.parseInt(br.readLine());
        // 해쉬 맵을 통해 정리
        HashMap<Long, Integer> hashMap = new HashMap<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 음식들 가격의 합
        long sum = 0;
        for (int i = 0; i < n; i++) {
            long dish = Integer.parseInt(st.nextToken());
            hashMap.put(dish, hashMap.getOrDefault(dish, 0) + 1);
            sum += dish;
        }

        // q개의 연산 처리
        int q = Integer.parseInt(br.readLine());
        // 인플레이션 값 보정
        long bias = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 인플레이션인 경우
            if (st.nextToken().equals("INFLATION")) {
                int add = Integer.parseInt(st.nextToken());
                // 증가 수치만큼 전체 합에 증가
                sum += n * add;
                // 값 보정
                bias += add;
            } else {
                // 가격이 x인 음식을 y로 조정
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                // 서로 다른 가격이며
                // 현재 보정된 값이 x인 음식들이 존재한다면
                if (x != y && hashMap.containsKey(x - bias) && hashMap.get(x - bias) > 0) {
                    // 그 개수
                    int cnt = hashMap.get(x - bias);
                    // 전체 합 변화량 계산
                    sum += (long) (y - x) * cnt;
                    // 해쉬 맵에 개수 반영
                    hashMap.put(y - bias, hashMap.getOrDefault(y - bias, 0) + cnt);
                    hashMap.put(x - bias, 0);
                }
            }
            // i+1일차 전체 음식 가격 합 기록
            sb.append(sum).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}