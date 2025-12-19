/*
 Author : Ruel
 Problem : Baekjoon 14252번 공약수열
 Problem address : https://www.acmicpc.net/problem/14252
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14252_공약수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기의 n의 배열이 주어진다.
        // 여기에 몇 개의 수를 추가하여, 정렬하였을 때, 연속한 두 수의 최대공약수가 1을 넘지 않게 하려한다.
        // 최소 몇 개의 수를 추가해야하는가?
        //
        // 유클리드 호제법, 브루트 포스 문제
        // 먼저, 수를 정렬하여 연속한 수에 대해 최대공약수가 몇 인지 살펴본다.
        // 2 이상일 경우, 1개부터 두 수 사이의 간격까지 만큼의 수를 추가하여 연속한 수들의 최대공약수를 1로 만들 수 있는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기 n의 배열
        int n = Integer.parseInt(br.readLine());
        int[] array = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(array);

        // 추가한 수의 개수
        int cnt = 0;
        boolean possible = true;
        for (int i = 0; i < array.length - 1 && possible; i++) {
            // 연속한 수가 이미 서로소라면 건너뜀.
            if (getGCD(array[i], array[i + 1]) == 1)
                continue;

            boolean found = false;
            // 그 외의 경우, 사이에 j개의 수를 추가하여본다.
            for (int j = 1; j < array[i + 1] - array[i] && !found; j++) {
                // 가능한 경우. j개의 수를 추가
                if (canMakeArray(j, array[i], array[i + 1])) {
                    cnt += j;
                    found = true;
                }
            }

            // 못 찾은 경우, 불가능한 경우
            if (!found)
                possible = false;
        }
        // 추가한 수의 개수 출력
        System.out.println(cnt);
    }

    // left와 right 사이에 remain 개의 수를 추가하여 연속한 수들을 서로소로 만들 수 있는지 확인한다.
    static boolean canMakeArray(int remain, int left, int right) {
        // 추가할 수가 더 이상 없다면 left와 right가 서로소인지 판별
        if (remain == 0)
            return getGCD(left, right) == 1;

        // 그 외의 경우
        // left + 1부터 right - 1까지의 수를 검증
        for (int i = left + 1; i < right; i++) {
            // left와 i가 서로소이며, 앞으로 remain - 1개의 수를 추가하여
            // 연속한 수들을 서로소로 만드는 것이 가능한 경우
            // true 반환
            if (getGCD(left, i) == 1 && canMakeArray(remain - 1, i, right))
                return true;
        }
        // 위 반복문을 도는 동안 못 찾은 경우 false 반환
        return false;
    }

    // 유클리드 호제법
    // a와 b의 최대공약수
    static int getGCD(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}