/*
 Author : Ruel
 Problem : Baekjoon 16282번 Black Chain
 Problem address : https://www.acmicpc.net/problem/16282
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16282_BlackChain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 고리가 연결된 체인이 주어진다.
        // 각 고리의 무게는 1g이다.
        // 이 고리들을 이용하여 1 ~ n g의 무게를 표현하려고 한다.
        // 고리 하나를 풀면 1g짜리의 풀린 고리 하나와 두 개의 덩어리가 생긴다.
        // n이 주어질 때, 최소 몇 개의 고리를 풀어야하는가?
        //
        // 수학 문제
        // k개의 고리를 푼다고 생각해보자
        // k개의 고리를 풀면, k개의 고리와 k+1개의 덩어리가 생긴다.
        // 이들로 표현 가능한 최대 수를 생각해보면
        // 먼저 1g짜리 k개의 고리를 통해 kg까지 표현이 가능하다. 그렇다면 첫번째 덩어리는
        // k보다 1 큰 k+1g인 것이 좋다. 그렇게 하면 k + k + 1 g까지 표현이 가능하다.
        // 두번째 덩어리는 (k + k + 1) + 1인 것이 좋고, 최대 (k + k + 1) + (k + k + 1 + 1)g까지 표현이 가능하다.
        // 주어진 n의 범위에 대해서 최대 55덩어리면 모두 표현이 가능하니, 순서대로 계산해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 고리
        long n = Long.parseLong(br.readLine());
        
        // 1 ~ ng을 모두 표현 가능하도록 풀어야하는 고리의 개수를 찾는다.
        int idx = 1;
        while (maxCover(idx) < n)
            idx++;
        // 답 출력
        System.out.println(idx);
    }
    
    // k개의 고리를 풀 때, 표현 가능한 최대 범위
    static long maxCover(int k) {
        // 1g짜리들로 kg 표현 가능
        long sum = k;
        // sum보다 1 큰 값을 다음 덩어리로 추가한다.
        // 총 k+1번 반복
        for (int i = 0; i < k + 1; i++)
            sum += (sum + 1);
        // 최대 표현 가능한 값 반환
        return sum;
    }
}