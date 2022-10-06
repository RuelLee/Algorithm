/*
 Author : Ruel
 Problem : Baekjoon 1484번 다이어트
 Problem address : https://www.acmicpc.net/problem/1484
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1484_다이어트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 10만 이하의 g가 주어진다.
        // 이 g는 현재 어떤 자연수의 제곱에서 다른 어떤 자연수의 제곱의 차이다.
        // 이 때 가능한 큰 쪽 자연수들을 출력하라.
        //
        // 두 포인터를 활용한 간단한 문제
        // n의 제곱과 n-1의 제곱의 차이가 g보다 같거나 작은 동안
        // back, front의 두 개의 포인터를 갖고
        // 두 제곱의 차이가 g보다 작을 경우, front를 늘리고, g보다 큰 경우 back을 늘려가며 탐색한다.
        // g와 같을 경우, 한 가지 경우를 찾은 것이기 때문에 front를 다시 늘려준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int g = Integer.parseInt(br.readLine());

        int pre = 1;
        int current = 1;
        StringBuilder sb = new StringBuilder();
        // currnet의 제곱과 current - 1의 제곱의 차이가 g보다 같거나 작은 동안.
        while (Math.pow(current, 2) - Math.pow(current - 1, 2) <= g) {
            // 제곱은 long의 범위가 나올 수 있다.
            long currentPow = (long) Math.pow(current, 2);
            long prePow = (long) Math.pow(pre, 2);

            // 제곱의 차이가 g보다 작은 경우 current를 늘려주고
            if (currentPow - prePow < g)
                current++;
            // 큰 경우는 back을 늘려줘 제곱의 차이를 줄여준다.
            else if (currentPow - prePow > g)
                pre++;
            // 만약 g에 해당하는 값이 나왔다면 답안에 추가해주고
            // current를 늘려 탐색을 계속해준다.
            else
                sb.append(current++).append("\n");
        }
        // 만약 답안이 비어있다면 불가능한 경우이므로 -1을 출력.
        // 가능하다면 답안을 출력한다.
        System.out.println(sb.isEmpty() ? -1 : sb);
    }
}