/*
 Author : Ruel
 Problem : Baekjoon 2470번 두 용액
 Problem address : https://www.acmicpc.net/problem/2470
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2470_두용액;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 용액이 주어진다.
        // 각 용액은 특성 값이 -10억 ~ 10억 사이의 0이 아닌 정수로 주어진다.
        // 두 용액을 합쳐 특성값이 0에 가까운 용액을 만들려고 할 때, 두 용액의 특성값을 출력하시오
        //
        // 두 포인터 문제
        // 두 포인터 문제이지만 세세한 사항에 있어서는 조금 생각을 요했던 문제.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] solutions = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 용액을 오름차순으로 정렬한다.
        Arrays.sort(solutions);

        // 초기값 설정.
        int[] answer = new int[]{0, 1};
        int j = 1;
        for (int i = 0; i < solutions.length - 1; i++) {
            // 서로 다른 두 용액을 섞어야하므로 i, j가 같을 일은 없다.
            if (i == j)
                j++;

            // 현재 i, j의 용액의 특성합보다, i, j+1의 합이 더 0에 가까운 경우, j를 늘려준다.
            while (j < solutions.length - 1 && Math.abs(solutions[i] + solutions[j]) > Math.abs(solutions[i] + solutions[j + 1]))
                j++;

            // j를 증가시키는 경우만 생각하면 안되는 것이
            // -100 1 2 3 과 같이 음의 수가 너무 커서 양의 수를 모두 합쳐도 음수인 경우, 양수끼리만의 합이 0에 더 가까운 경우가 생기기 때문.
            // 따라서 j를 줄이는 과정도 필요.
            // 반대로 i, j의 특성합보다 i, j - 1의 합이 더 0에 가까운 경우, j를 줄여준다.
            while (j > i + 1 && Math.abs(solutions[i] + solutions[j]) > Math.abs(solutions[i] + solutions[j - 1]))
                j--;

            // 최종적으로 선택된 i, j가 기존에 계산됐던 값들보다 더 0에 가까운지 비교하고 답안에 기록한다.
            if (Math.abs(solutions[answer[0]] + solutions[answer[1]]) > Math.abs(solutions[i] + solutions[j])) {
                answer[0] = i;
                answer[1] = j;
            }
        }
        // 최종적으로 선택된 두 용액을 출력한다.
        System.out.println(solutions[answer[0]] + " " + solutions[answer[1]]);
    }
}