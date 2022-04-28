/*
 Author : Ruel
 Problem : Baekjoon 16900 이름 정하기
 Problem address : https://www.acmicpc.net/problem/16900
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16900_이름정하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 원하는 문자열 s와 이들의 반복 횟수 k가 주어진다.
        // s가 k번 반복되는 문자열의 최소 길이는?
        //
        // 문자열에서 s가 중복하여 등장할 수 있다
        // 예를 들어 aba 가 s라면 ababa 일 경우, 'aba'ba, ab'aba'로 두번 등장했고 이 때의 길이는 5이다
        // 따라서 KMP 알고리즘을 통해 접미사가 접두사랑 얼마나 겹치는지 여부를 판단하고, 그 중복된 길이만큼을 빼주면 된다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        String s = st.nextToken();
        long k = Long.parseLong(st.nextToken());

        // S는 최대 50만, K는 100만이므로 오버플로우에 주의하며 Long 타입으로.
        // 기본적인 길이는 s의 길이 * k
        long length = s.length() * k;
        // 그 후, 접두사와 접미사가 겹치는 길이는 빼준다
        // pi배열 마지막에 저장된 값이 접두사와 접미사가 중복되는 최대 길이이고
        // 이를 (k - 1)개 만큼 빼주면 된다.
        length -= getPi(s)[s.length() - 1] * (k - 1);
        System.out.println(length);
    }

    static int[] getPi(String s) {      // pi 배열 구하기.
        int[] pi = new int[s.length()];
        int j = 0;
        for (int i = 1; i < pi.length; i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j))
                j = pi[j - 1];
            if (s.charAt(i) == s.charAt(j))
                pi[i] = ++j;
        }
        return pi;
    }
}