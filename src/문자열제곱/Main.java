/*
 Author : Ruel
 Problem : Baekjoon 4354번 문자열 제곱
 Problem address : https://www.acmicpc.net/problem/4354
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 문자열제곱;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // KMP 활용 문제!
        // 문자열을 부분문자열의 제곱형태로 나타낼 수 있을 때,
        // 최대 몇 제곱으로 나타낼 수 있는가에 대한 문제
        // 각 문자열의 pi 배열을 구한다!
        // 여기서 pi 배열의 값이 의미하는 것은, 숫자번째만큼의 접두사와 값의 -1 값까지가 동일하다는 의미
        // 따라서 1값이 들어있다면, 그 위치의 문자는 접두사의 첫번째 문자와 동일
        // 그렇다면 가장 마지막으로 나오는 1의 이전 위치가 패턴의 마지막 문자가 될 것이다
        // 그 후, pi배열의 마지막 숫자가 패턴 길이의 배수가 되어야한다
        // 그렇지 않다면, 패턴이 나오다 중간에 끊긴 형태가 될 수 있다.
        // 이를 고려한다면 부분문자열의 몇제곱인지 구할 수 있다!

        Scanner sc = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();
        String input = sc.nextLine();
        while (!input.equals(".")) {
            int j = 0;
            int[] pi = new int[input.length()];
            for (int i = 1; i < input.length(); i++) {
                while (j > 0 && input.charAt(i) != input.charAt(j))
                    j = pi[j - 1];
                if (input.charAt(i) == input.charAt(j))
                    pi[i] = ++j;
            }
            int patternLength = 1;      // 기본값으로는 전체문자열의 1제곱으로 상정하고 1
            for(int i = input.length()-1; i>0; i--) {
                if (pi[i] == 1) {       // 가장 마지막 위치의 1 값을 찾는다.
                    patternLength = i;
                    break;
                }
            }

            // 가장 마지막 pi 배열 값이 0이거나, patternLength의 배수가 아니라면 특정 문자열의 제곱으로 나타낸 것이 아니다.
            if (pi[pi.length - 1] == 0 || pi[pi.length - 1] % patternLength != 0)
                patternLength = input.length();     // 그냥 전체 문자열의 1제곱으로 보자.
            sb.append(input.length() / patternLength).append("\n");     // 전체 길이를 패턴의 길이로 나눈 것이 정답!
            input = sc.nextLine();
        }
        System.out.println(sb);
    }
}