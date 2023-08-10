/*
 Author : Ruel
 Problem : Baekjoon 25908번 수열의 합
 Problem address : https://www.acmicpc.net/problem/25908
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25908_수열의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n의 모든 약수를 d1, ... , dk라 할 때, An = (-1)^(d1) + ... + (-1)^(dk)라 한다
        // 양의 정수 s, t가 주어질 때,
        // As + ... + At를 구하라
        //
        // 수학.. 문제?
        // 코딩 자체는 너무 간단한데, An에 대해 조금 생각해봐야하는 문제
        // An은 짝수인 약수 - 홀수인 약수의 개수와 같다.
        // 어차피 우리는 범위로 다루기 때문에 그렇다면
        // A1 + ... + An 에 대해 생각해보면
        // 1 ~ n인 수들에 대해, 1이상 n이하의 배수가 몇 개인지 세는 문제와 같다고 볼 수 있다.
        // 1은 홀수이므로, 자신의 배수의 개수만큼 -1이 추가될 것이고
        // 2는 짝수이므로, 자신의 배수의 개수만큼 +1이 추가될 것이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // As + ... + At
        // 의 범위 s와 t
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // As ~ At까지 값이므로
        // 해당하는 범위의 값을 찾아 출력한다.
        System.out.println(findValueBelowN(t) - findValueBelowN(s - 1));
    }

    // A1+ ... + An의 값을 찾는다.
    static int findValueBelowN(int n) {
        int answer = 0;
        // 1이상 n이하의 수들에 대해
        // 1 ~ n 범위에 자신의 배수 개수 * (자신의 홀수라면 -1, 짝수라면 1)
        // 한 값을 더한다.
        for (int i = 1; i <= n; i++)
            answer += (i % 2 == 0 ? 1 : -1) * n / i;
        // 찾은 값을 반환.
        return answer;
    }
}