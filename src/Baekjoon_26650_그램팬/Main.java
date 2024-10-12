/*
 Author : Ruel
 Problem : Baekjoon 26650번 그램팬
 Problem address : https://www.acmicpc.net/problem/26650
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26650_그램팬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 팬그램은 A ~ Z까지 모든 알파벳이 최소 한 번씩 등장하는 문자열을 말한다.
        // 그램팬은 팬그램 중에서도 모든 알파벳이 순서대로 나열된 형태이다.
        // 문자열 S가 주어질 때, S의 부분 문자열 중 그램팬의 개수를 출력하라
        //
        // 구현 문제
        // 부분 문자열은 문자열의 연속된 일부를 말하므로
        // 순서대로 문자열의 문자들을 살펴나가며
        // 이전에 등장한 문자보다 더 순서적으로 앞서는 문자열이 등장한다면
        // 이전까지를 하나의 부분 문자열로 보고, 그 안에 그램팬이 존재하는지 계산하여 더해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        // 그램팬의 수
        long answer = 0;
        // 현재 계산하는 부분 문자열 내의 알파벳들의 수
        int[] counts = new int[26];
        counts[s.charAt(0) - 'A']++;
        for (int i = 1; i < s.length(); i++) {
            // i번째 문자가 i-1번째 문자보다 더 이른 문자라면
            // i-1까지를 하나의 부분 문자열로 계산.
            if (s.charAt(i) < s.charAt(i - 1)) {
                // 만약 이전에 등장한 문자열이 Z였다면
                // 그램팬일 가능성이 있으므로, 계산.
                if (s.charAt(i - 1) == 'Z')
                    answer += grampan(counts);
                // Z가 아니었다면 그램팬일 확률이 없으므로
                // counts 배열 초기화
                Arrays.fill(counts, 0);
            }
            // 이번 문자의 개수 증가
            counts[s.charAt(i) - 'A']++;
        }
        // 마지막 문자까지 살펴봤다면, 마지막까지 계산한 counts 배열로
        // 그램팬 여부 계산
        answer += grampan(counts);

        // 답 출력
        System.out.println(answer);
    }

    // counts 배열로 그램팬 여부 확인
    static long grampan(int[] counts) {
        // 만약 개수가 0인 문자가 있다면 그램팬이 아니다.
        for (int count : counts) {
            if (count < 1)
                return 0;
        }

        // 모든 문자가 최소 한번씩 등장했다면
        // 부분 문자열의 시작 지점인 A의 개수와
        // 부분 문자열의 종료 지점인 Z의 개수를
        // 곱한 값이 그램팬의 개수
        return (long) counts[0] * counts[25];
    }
}