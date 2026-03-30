/*
 Author : Ruel
 Problem : Baekjoon 30406번 산타 춘배의 선물 나눠주기
 Problem address : https://www.acmicpc.net/problem/30406
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30406_산타춘배의선물나눠주기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 값이 0 ~ 3인 선물이 n(n은 짝수)개 주어진다.
        // 이를 2개싞 n / 2마리의 아기 고양이에게 나눠주려고 한다.
        // 이 때, 각 고양이가 얻는 만족감은 두 선물 가격의 xor 값이라고 한다.
        // 만족도의 합이 최대가 되게끔 나눠줄 때, 만족도의 합은?
        //
        // 그리디 문제
        // 값의 범위가 작다.
        // 0 ~ 3이므로, xor 값의 최대값은 3이고 이 때 가능한 경우의 수는 (0, 3), (1, 2) 뿐이다.
        // 따라서 3점부터 1점까지 내려가며 가능한 선물들을 최대한 많이 나눠준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 선물
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 가격에 따라 분류
        int[] counts = new int[4];
        for (int i = 0; i < n; i++)
            counts[Integer.parseInt(st.nextToken())]++;

        // 만족도의 합
        int sum = 0;
        // 두 선물의 xor 값
        // 3점부터 1점까지 내려간다.
        for (int score = 3; score > 0; score--) {
            for (int i = 0; i < 4; i++) {
                for (int j = i + 1; j < 4; j++) {
                    // i와 j의 xor 값이 score인 경우
                    // 가능한 많은 고양이들에게 나눠준다.
                    if ((i ^ j) == score) {
                        // num개의 세트
                        int num = Math.min(counts[i], counts[j]);
                        // 점수 누적
                        sum += score * num;
                        // 각 선물의 개수 차감
                        counts[i] -= num;
                        counts[j] -= num;
                    }
                }
            }
        }
        // 답 출력
        System.out.println(sum);
    }
}