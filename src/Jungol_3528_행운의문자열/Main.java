/*
 Author : Ruel
 Problem : Jungol 3528번 행운의 문자열
 Problem address : https://jungol.co.kr/problem/3528
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3528_행운의문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대 10개의 문자로 이루어진 문자열이 주어진다.
        // 해당 문자들을 모두 사용하여 이웃한 문자가 서로 다른 문자열을 만들고자할 때
        // 문자열의 경우의 수는?
        //
        // 백트래킹
        // 문자의 개수가 많지 않으므로 모든 경우의 수를 탐색한다하더라도 최대 10! = 약 360만개로 크기가 작다.
        // 백트래킹을 통해 직접 계산해도 괜찮다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력
        char[] input = br.readLine().toCharArray();

        // 문자의 종류 별로 분리
        int[] cnts = new int[26];
        for (char c : input)
            cnts[c - 'a']++;

        // 답 출력
        System.out.println(findAnswer(-1, cnts, input.length));
    }

    // 이전에 쓰인 문자 pre, 현재 남은 문자들의 수 cnts
    // 남은 문자의 합 sum
    static int findAnswer(int pre, int[] cnts, int sum) {
        // 모든 문자를 사용했다면 경우의 수 1가지
        if (sum == 0)
            return 1;

        // 단일 문자 최대 개수를 계산
        int max = 0;
        for (int i = 0; i < cnts.length; i++)
            max = Math.max(max, cnts[i]);
        // 만약 단일 문자의 수가 나머지 수의 합 +1 보다 많다면 배치가 불가능한 경우.
        // 0 반환
        if (max - 1 > sum - max)
            return 0;

        // 그 외의 경우
        int num = 0;
        for (int i = 0; i < cnts.length; i++) {
            // 이전에 쓰인 문자가 아니며, 개수가 남았을 때
            if (i != pre && cnts[i] > 0) {
                cnts[i]--;
                // 해당 문자를 사용하는 경우의 수 계산하여 합산
                num += findAnswer(i, cnts, sum - 1);
                // 복원
                cnts[i]++;
            }
        }
        // 계산된 경우의 수 반환
        return num;
    }
}