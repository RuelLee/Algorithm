/*
 Author : Ruel
 Problem : Baekjoon 2825번 수업시간에 교수님 몰래 교실을 나간 상근이
 Problem address : https://www.acmicpc.net/problem/2825
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2825_수업시간에교수님몰래교실을나간상근이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 수 간의 친구 관계란
        // 두 수를 이루는 숫자가 적어도 하나 겹치는 쌍을 친구라 한다.
        // 12, 1758는 1을 공유하므로 친구 관계이다.
        // n개의 수 안에서 친구 관계 수를 구하라.
        //
        // 비트마스킹 문제
        // 각 수를 0 ~ 9가 포함되어있는지에 따라 비트마스킹한 수로 바꾸어준다.
        // 그 후, 각 비트마스킹한 값으 토대로 친구 관계를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());

        // 비트마스킹한 값을 해쉬맵에 담는다.
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            long num = Long.parseLong(br.readLine());
            int bitmask = 0;
            while (num > 0) {
                bitmask |= (1 << (num % 10));
                num /= 10;
            }
            if (!hashMap.containsKey(bitmask))
                hashMap.put(bitmask, 1);
            else
                hashMap.put(bitmask, hashMap.get(bitmask) + 1);
        }
        
        // 중복하여 선택되는 일이 없기 위해 해쉬셋을 이용
        HashSet<Integer> hashSet = new HashSet<>();
        // 친구 관계의 수
        long count = 0;
        for (int bit : hashMap.keySet()) {
            // bit를 해쉬셋에 추가.
            hashSet.add(bit);

            // bit에 해당하는 수들 사이의에서의 친구 관계 합산.
            count += (long) hashMap.get(bit) * (hashMap.get(bit) - 1) / 2;
            // bit와 next 사이에 친구 관계가 성립하는지 확인하고
            // 성립한다면 두 그룹 사이의 친구 관계 합산.
            for (int next : hashMap.keySet()) {
                if (hashSet.contains(next) || (bit & next) == 0)
                    continue;
                count += (long) hashMap.get(bit) * hashMap.get(next);
            }
        }

        // 전체 친구 관계 수 출력.
        System.out.println(count);
    }
}