/*
 Author : Ruel
 Problem : Baekjoon 13701번 중복 제거
 Problem address : https://www.acmicpc.net/problem/13701
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13701_중복제거;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정수를 읽고, 이들 중 반복되는 수를 제외하고 남은 수를 입력된 수로 출력하시오
        //
        // 비트 문제
        // 메모리에 관한 생각을 해볼 수 있는 문제
        // A의 범위가 33554432 까지이므로 잘못된 타입을 활용하여 체크할 경우
        // 메모리 초과가 나게된다.
        // 각 수의 등장 여부만 표시하면 되므로 각 수에 대해 0, 1 2개의 비트로만 유무를 판단하면 메모리를 절약할 수 있다.
        // 이 때 사용할 수 있는 것이 비트셋이다.
        // 혹은 int 타입이 32개의 비트로 이루어진 것을 이용하여 33554432 / 32 개의 비트로 나타내는 방법도 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 비트셋을 활용하여 메모리 사용을 줄인다.
        BitSet bitSet = new BitSet();
        // 수들을 입력 받는다.
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 답안을 기록할 StringBuilder
        StringBuilder sb = new StringBuilder();
        // 수가 있는 동안
        while (st.hasMoreTokens()) {
            // 입력된 수
            int num = Integer.parseInt(st.nextToken());
            // 이전에 입력된 적이 없는 수라면
            if (!bitSet.get(num)) {
                // 비트셋에 표시해주고
                bitSet.set(num);
                // StringBuilder이 기록한다.
                sb.append(num).append(" ");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        // 최종 답안 출력.
        System.out.println(sb);
    }
}