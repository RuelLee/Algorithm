/*
 Author : Ruel
 Problem : Baekjoon 3356번 라디오 전송
 Problem address : https://www.acmicpc.net/problem/3356
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3356_라디오전송;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 라디오 방송국은 메시지를 청취자들에게 확실히 받게 하기 위해 메세지를 반복하여 전송한다
        // 한 청취자가 받은 메시지가 주어지는데, 방송국이 보낸 메시지보다 항상 길거나 같다.
        // 즉 입력 S가 주어졌을 때, S' + S' + ... + S'의 부분 문자열이 되는 가장 짧은 문자열의 길이를 출력하라.
        // cabcabca의 경우 cab + cab + ca로 나누어 길이가 3인 cab가 연속해서 붙은 형태로 볼 수 있다.
        //
        // 문자열에 관련된 문제
        // kmp 알고리즘을 사용한다
        // kmp 알고리즘을 사용하면 앞 부분에 일치하는 문자가 연속된다면 계속해서 값이 증가한다
        // cabcabca의 경우 0 0 0 1 2 3 4 5 가 저장된다.
        // 이 값을 통해 단위 문자열 뒤에 반복되는 단위문자열의 길이를 알 수 있으며
        // 간단하게 전체 문자열의 길이에서 반복되는 단위 문자열의 길이를 빼주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int s = Integer.parseInt(br.readLine());
        String l = br.readLine();

        // pi배열
        int[] pi = new int[l.length()];
        int j = 0;
        for (int i = 1; i < pi.length; i++) {
            while (j > 0 && l.charAt(i) != l.charAt(j))
                j = pi[j - 1];

            if (l.charAt(i) == l.charAt(j))
                pi[i] = ++j;
        }
        // 전체 문자열의 길이에서 반복되는 단위 문자열의 길이를 빼준다.
        System.out.println(s - pi[pi.length - 1]);
    }
}