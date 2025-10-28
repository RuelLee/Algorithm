/*
 Author : Ruel
 Problem : Baekjoon 32407번 식물 기르기
 Problem address : https://www.acmicpc.net/problem/32407
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32407_식물기르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 식물이 주어진다.
        // 각 식물에 물을 주어야하는 최소 주기가 주어진다.
        // 이 주기는 2의 제곱들이다.
        // 모든 식물을 영원히 시들지 않게 하기 위해
        // 하루에 물을 줘야하는 최소 식물의 수는?
        //
        // 그리디 문제
        // 2^i 주기로 물을 줘야하는 식물이 2개라면 이는 2^(i-1)주기의 식물 하나와 같다고 볼 수 있다.
        // 모든 식물의 주기를 log2 취한 값을 토대로 2개씩 짝지어, 더 낮은 주기의 식물로 치환한다.
        // 하나일 경우, 버릴 수 없으므로, 이 또한 낮은 주기의 하나로 본다.
        // 이렇게 차근차근 주기 1까지 올라가면, 하루에 물을 줘야하는 최소 식물의 수를 구할 수 있다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 식물
        int n = Integer.parseInt(br.readLine());
        // 각 식물을 log2 값의 개수를 센다.
        int[] counts = new int[17];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            counts[toLog2(Integer.parseInt(st.nextToken()))]++;
        
        // 가장 주기가 긴 식물부터
        for (int i = counts.length - 1; i > 0; i--) {
            // 개수가 0개가 아닌 경우
            if (counts[i] != 0) {
                // 그 개수를 +1하여, 더 낮은 제곱의 수로 편입시킨다.
                counts[i - 1] += (counts[i] + 1) / 2;
                counts[i] = 0;
            }
        }
        // 최종적으로 주기가 1인 식물의 개수를 출력한다.
        System.out.println(counts[0]);
    }

    // 주어진 2의 제곱 수 n을 log2 취한 값을 반환한다.
    static int toLog2(int n) {
        int count = 0;
        while (n > 0) {
            n /= 2;
            count++;
        }
        return count - 1;
    }
}