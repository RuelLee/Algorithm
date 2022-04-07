/*
 Author : Ruel
 Problem : Baekjoon 1759번 암호 만들기
 Problem address : https://www.acmicpc.net/problem/1759
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1759_암호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb;        // 최종 출력할 가능 암호 목록
    static char[] alphabets;        // 사용 가능한 알파벳들

    public static void main(String[] args) throws IOException {
        // 암호는 l개의 서로 다른 알파벳 소문자로 이루어지며, 알파벳 오름차순을 따른다.
        // 또한 최소 한 개의 모음과 최소 2개의 자음으로 구성되어있다고 한다.
        // c개의 사용 가능한 알파벳이 주어질 때, 가능한 암호를 모두 출력하라
        //
        // 백트래킹을 활용한 순열 문제
        // 어떤 알파벳이 선택되었는지 표시하기 위해 비트마스킹을 이용하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int l = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        alphabets = new char[c];
        for (int i = 0; i < alphabets.length; i++)
            alphabets[i] = st.nextToken().charAt(0);

        // 사용 가능한 알파벳을 오름차순 정리
        Arrays.sort(alphabets);
        sb = new StringBuilder();
        permutation(0, 0, l, 0);
        System.out.print(sb);
    }

    static void permutation(int idx, int selected, int l, int bitmask) {        // 순열
        if (selected == l) {        // l개의 알파벳이 모두 선택되었다면
            StringBuilder password = new StringBuilder();
            int vowelCount = 0;     // 모음 개수를 센다.
            for (int i = 0; i < alphabets.length; i++) {
                if ((bitmask & 1 << i) == 1 << i) {     // bitmask에 표시되어있다면
                    password.append(alphabets[i]);      // password에 alphabets[i]를 추가
                    // 모음이라면 vowelCount를 증가시켜준다
                    if (alphabets[i] == 'a' || alphabets[i] == 'e' || alphabets[i] == 'i' ||
                            alphabets[i] == 'o' || alphabets[i] == 'u') {
                        vowelCount++;
                    }
                }
            }
            // 최종적으로 완성된 암호에 모음이 하나 이상이고, 자음이 2개 이상일 때만
            // 최종 출력 sb에 추가시킨다.
            if (vowelCount > 0 && vowelCount < l - 1)
                sb.append(password).append("\n");
            return;
        }

        // idx가 alphabets의 길이를 넘어간다면, 종료.
        if (idx >= alphabets.length)
            return;

        // alphabets[i]를 선택한 경우, idx 증가, selected 증가, 비트마스크에 표시.
        permutation(idx + 1, selected + 1, l, bitmask | 1 << idx);
        // 선택하지 않은 경우, idx 증가, selected 유지, 비트마스크 유지.
        permutation(idx + 1, selected, l, bitmask);
    }
}