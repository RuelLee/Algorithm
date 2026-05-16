/*
 Author : Ruel
 Problem : Jungol 2062번 라그랑쥐 수
 Problem address : https://jungol.co.kr/problem/2062
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2062_라그랑쥐수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static int MAX = 1 << 15;
    static int[] counts;
    static List<Integer> pows;

    public static void main(String[] args) throws IOException {
        // 양의 정수는 많아야 4개의 제곱수들의 합으로 표현된다고 한다.
        // 2^15이하의 정수가 주어질 때, 해당 수를 제곱수의 합으로 표현되는 가짓수를 구하라
        //
        // 브루트 포스 문제
        // 먼저 2^15이하의 제곱수들을 모두 구한 뒤
        // 1 ~ 4개의 제곱수로 표현 가능한 경우의 수들을 세어나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각 수를 표현할 수 있는 제곱수의 합 개수
        counts = new int[MAX + 1];
        // 제곱수들
        pows = new ArrayList<>();

        for (int i = 1; i * i <= MAX; i++)
            pows.add(i * i);

        // 1 ~ 4개의 제곱수의 합으로 표현 가능한 수들을 찾는다.
        for (int i = 1; i < 5; i++)
            bruteForce(0, 0, i, 0);

        // 답 출력
        System.out.println(counts[Integer.parseInt(br.readLine())]);
    }

    // 제곱수들 가운데 idx번까지 살펴봤고, 현재 selected개의 수를 골랐으며
    // num개의 수를 고를 것이다. 현재까지의 합은 sum
    static void bruteForce(int idx, int selected, int num, int sum) {
        // 만약 모두 골랐다면 해당 sum의 경우의 수 +1
        if (selected == num) {
            counts[sum]++;
            return;
        } else if (idx == pows.size())  // 다 고르지 못했는데 idx를 초과하는 경우. 버림
            return;

        // 제곱수의 중복 선택이 가능하므로 idx번부터 다시 선택
        // 합이 MAX 이내인 경우 동안만
        for (int i = idx; i < pows.size() && sum + pows.get(i) <= MAX; i++)
            bruteForce(i, selected + 1, num, sum + pows.get(i));
    }
}