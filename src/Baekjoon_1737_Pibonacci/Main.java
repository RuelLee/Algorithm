/*
 Author : Ruel
 Problem : Baekjoon 1737번 Pibonacci
 Problem address : https://www.acmicpc.net/problem/1737
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1737_Pibonacci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static final long LIMIT = 1_000_000_000_000_000_000L;
    static HashMap<Double, Long> hashMap;

    public static void main(String[] args) throws IOException {
        // pibonacci 수열은 다음과 같이 정의된다.
        // P[n] = 1 (0 ≤ n ≤ π)
        // P[n] = P[n-1] + P[n-π] (그 외)
        // 자연수 n이 주어졌을 때 P[n]을 구하라
        //
        // 메모이제이션 문제
        // 값을 구하는데 메모이제이션을 사용하여 값을 재사용하면서 연산을 줄인다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // P[n]을 구한다.
        int n = Integer.parseInt(br.readLine());
        
        // 메모이제이션에 사용할 해쉬맵
        hashMap = new HashMap<>();
        // P[n]의 값 출력
        System.out.println(findAnswer(n));
    }
    
    // P[n]을 구하는 메소드
    static long findAnswer(double n) {
        // n이 0이상, π이하라면 1을 반환
        if (n >= 0 && n <= Math.PI)
            return 1;

        // 해쉬맵에 값이 기록되지 않았다면
        // 두번째 조건을 사용하여 값을 구해 해쉬맵에 저장한다.
        if (!hashMap.containsKey(n))
            hashMap.put(n, (findAnswer(n - 1) + findAnswer(n - Math.PI)) % LIMIT);
        
        // 해당하는 P[n]값 반환
        return hashMap.get(n);
    }
}