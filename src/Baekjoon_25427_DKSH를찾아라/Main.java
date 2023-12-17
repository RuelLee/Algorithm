/*
 Author : Ruel
 Problem : Baekjoon 25427번 DKSH를 찾아라
 Problem address : https://www.acmicpc.net/problem/25427
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25427_DKSH를찾아라;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 문자열이 주어진다.
        // 순서대로 DKSH를 이루는 경우를 찾고자한다.
        // D는 a번째, K는 b번째, S는 c번째, H는 d번째일 때 a < b < c < d인 경우다.
        // 해당 경우의 수를 구하라.
        //
        // 누적합 문제
        // 문자열에서 D의 개수를 계속 누적시켜나가며
        // K가 등장했을 때는, 여태까지 등장 D의 개수
        // S가 등장했을 때는 위에 계산한 DK 순서 쌍의 개수
        // H가 등장했을 때는 위에 계산한 DKS 순서 쌍의 개수를 누적합으로 구해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 문자열 s
        int n = Integer.parseInt(br.readLine());
        char[] s = br.readLine().toCharArray();
        
        // 누적합
        long[] sums = new long[4];
        for (char c : s) {
            switch (c) {
                case 'D' -> sums[0]++;      // D는 개수만 세고
                case 'K' -> sums[1] += sums[0];     // K는 여태까지 등장한 D의 개수
                case 'S' -> sums[2] += sums[1];     // S는 여태까지 등장한 DK의 개수
                case 'H' -> sums[3] += sums[2];     // H는 여태까지 등장한 DKS의 개수를 더해나간다.
            }
        }
        // 최종적으로 DKSH의 개수를 출력한다.
        System.out.println(sums[3]);
    }
}