/*
 Author : Ruel
 Problem : Baekjoon 1701번 Cubeditor
 Problem address : https://www.acmicpc.net/problem/1701
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1701_Cubeditor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // KMP 알고리즘의 PI 배열을 구하는 방법을 사용하여 푸는 문제!
        // 길이를 늘려가며, 접두사와 동일한 접미사를 찾는 것이 PI 배열
        // 따라서, 살짝 변형하여
        // 0부터 시작하는 문자열, 1부터 시작하는 문자열,,, 등에 대해 모두 PI 배열을 찾고 최대 길이를 저장해주면 된다.
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();

        int[] pi = new int[input.length()];
        int maxLength = 0;
        for (int start = 0; start < pi.length; start++) {       // 0 ~ 끝까지, 1 ~ 끝까지,, 등의 부분문자열을 나타낸다.
            pi[start] = start;      // 각 부분문자열의 시작은 자기 자신.

            int j = start;      // pi배열에 최대 공통 부분의 위치를 담는 idx
            for (int i = start + 1; i < pi.length; i++) {       // 검사할 대상
                pi[i] = start;      // 검사할 대상이 일치하지 않더라도 돌아가야할 곳은 이번 부분 문자열의 시작인 0이 아닌 start 다.

                while (j > start && input.charAt(i) != input.charAt(j))         // 만약 j번째 문자와 i번째 문자가 일치하지 않는다면
                    j = pi[j - 1];        // 이전 문자에 해당하는 pi 배열이 갖고 있는 idx 위치로 이동한다.
                if (input.charAt(j) == input.charAt(i)) {       // 일치한다면
                    maxLength = Math.max(maxLength, (j + 1 - start));       // 현재 j의 위치와 start의 위치가 일치하는 부분문자열. 최대 부분 문자열의 길이를 갱신해준다.
                    pi[i] = ++j;        // i위치의 pi값은 j로 설정하고, j값을 하나 증가.
                }
            }
        }
        System.out.println(maxLength);
    }
}