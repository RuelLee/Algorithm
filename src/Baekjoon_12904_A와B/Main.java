/*
 Author : Ruel
 Problem : Baekjoon 12904번 A와 B
 Problem address : https://www.acmicpc.net/problem/12904
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12904_A와B;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // s와 t 문자열이 주어진다
        // 문자열을 바꿀 때는 두가지의 경우만 가능하다
        // 1. 문자열 뒤에 A를 추가한다
        // 2. 문자열을 뒤집고 B를 추가한다
        // s 문자열을 바꿔 t 문자열을 만들 수 있는지 출력하라
        // s 문자열에서 선택이 가능한 경우는 2가지의 경우가 모두 가능하며
        // 매번 2개의 문자열이 생겨, 2의 제곱으로 가짓수가 늘어난다.
        // 문제 조건으로 살펴보면 s의 최소 길이는 1, t의 최대길이는 1000으로 2^999가지가 나올 수 있다(물론 중복되는 경우는 빠지겠지만)
        // 따라서 위 방법보다는 t에서 문자열을 하나씩 줄여나가며 s와 같은 길이까지 줄였을 때 두 문자열이 일치하는가를 살펴봐주면 된다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        String t = br.readLine();

        System.out.println(canBeConvert(s, t) ? 1 : 0);
    }

    static boolean canBeConvert(String original, String target) {
        boolean reverse = false;        // t의 처음 상태
        StringBuilder sb = new StringBuilder(target);
        while (original.length() < sb.length()) {
            if (reverse) {      // 2번째 연산으로 뒤집어진 상태라면, 뒷글자가 아닌 앞글자를 살펴본다.
                if (sb.charAt(0) == 'B')        // 앞글자가 B라면, 2번 연산으로 다시 뒤집어진다. A라면 아무것도 하지 않는다
                    reverse = false;
                sb.deleteCharAt(0);     // 그 후 맨 앞글자를 하나 지운다.
            } else {
                if (sb.charAt(sb.length() - 1) == 'B')      // 뒤집어지지 않은 상태라면 맨 뒷글자를 확인.
                    reverse = true;
                sb.deleteCharAt(sb.length() - 1);       // 맨 뒷글자를 하나 지운다.
            }
        }
        if (reverse) {      // 뒤집어진 상태라면 문자열을 다시 뒤집어 원래 상태로 만들고, s와 일치하는가 살펴본다.
            StringBuilder reversedWord = new StringBuilder();
            for (int i = sb.length() - 1; i >= 0; i--)
                reversedWord.append(sb.charAt(i));
            return original.equals(reversedWord.toString());
        }
        // 그렇지 않다면 바로 s와 비교한다.
        return original.equals(sb.toString());
    }
}