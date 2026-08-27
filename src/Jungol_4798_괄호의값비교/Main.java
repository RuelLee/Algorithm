/*
 Author : Ruel
 Problem : Jungol 4798번 괄호의 값 비교
 Problem address : https://jungol.co.kr/problem/4798
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4798_괄호의값비교;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 괄호 문자열이 등장한다
        // ()는 1이며
        // (x) = 2 * x
        // x y = x + y가 된다.
        // t쌍의 문자열들이 주어질 때, 두 개마다 서로의 대소 관계를 비교하라
        //
        // 수학 문제
        // 괄호를 보자마자 스택 문제다! 하고 스택으로 풀면 시간과 범위 초과로 틀렸습니다를 받는다.
        // 괄호 한 번 당 값이 두 배가 되므로, 최대 길이가 300만인 길이를 통하면 2^149도 될 수 있다.
        // 따라서, 배열로 각 괄호의 깊이에 따라 값을 따로 계산하고
        // 해당 깊이의 값이 2보다 큰 경우, 위로 올림을 처리한다. 그 후, 두 배열을 비교한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 두 개의 괄호 문자열
            int[] left = toValue(br.readLine());
            int[] right = toValue(br.readLine());

            // 대소 비교
            boolean found = false;
            for (int i = 1499999; i >= 0 && !found; i--) {
                if (left[i] != right[i]) {
                    found = true;
                    sb.append(left[i] > right[i] ? ">" : "<").append("\n");
                }
            }
            // 끝까지 두 괄호 문자열의 대소를 찾지 못했다면 같은 경우
            if (!found)
                sb.append("=").append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // 두 괄호 문자열을 깊이에 따른 배열로 만든다.
    static int[] toValue(String str) {
        // 최대 150만까지 깊이를 내려갈 수 있다.
        int[] array = new int[1500000];
        int depth = 0;
        for (int i = 0; i < str.length(); i++) {
            // (인 경우, 깊이를 하나 내려간다.
            if (str.charAt(i) == '(')
                depth++;
            else {
                // )인 경우, 깊이를 하나 내려가고
                depth--;
                // 직전 문자열이 ( 였던 경우 () = 1로 값을 하나 추가한다.
                if (str.charAt(i - 1) == '(')
                    array[depth]++;
            }
        }

        // 각 자리의 수가 2보다 같거나 큰 경우, 위로 올림
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > 1) {
                array[i + 1] += array[i] / 2;
                array[i] %= 2;
            }
        }
        // 배열 반환
        return array;
    }
}