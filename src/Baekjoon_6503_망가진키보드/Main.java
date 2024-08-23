/*
 Author : Ruel
 Problem : Baekjoon 6503번 망가진 키보드
 Problem address : https://www.acmicpc.net/problem/6503
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6503_망가진키보드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 키보드의 키가 망가져 현재 m개의 키만 작동시킬 수 있다.
        // 레이아웃을 변경하여 단어를 입력하고자 한다.
        // 입력하고자하는 문장이 주어졌을 때, 레이 아웃을 더 이상 바꾸지 않고서
        // 입력할 수 있는 연속하는 문자의 최대 길이를 출력하라
        //
        // 두 포인터 문제
        // 두 포인터를 통해 그 사이에 있는 문자들의 개수를 조절하며 최대 길이를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력
        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (input != null) {
            int m = Integer.parseInt(input);
            // m이 0이 들어올 경우 종료
            if (m == 0)
                break;

            // 최대 길이
            int maxLength = 0;
            // 두 포인터 사이의 알파벳 개수를 센다.
            int[] alphabets = new int[128];
            String sentence = br.readLine();
            // 포인터는 i와 j
            int j = -1;
            // 현재 사용되고 있는 키의 개수
            int keys = 0;
            for (int i = 0; i < sentence.length(); i++) {
                j = Math.max(j, i - 1);

                // j+1번째 문자가 문장을 범위를 넘어가지 않으며
                // 아직 여유키가 있거나, 두 포인터 사이 내의 문자가 다시 나와 입력할 수 있을 경우
                // j 포인터를 뒤로 한칸 밀어준다.
                while (j + 1 < sentence.length() &&
                        (keys < m || alphabets[sentence.charAt(j + 1) - ' '] > 0)) {
                    // 새로 추가되는 알파벳인 경우 할당된 키 개수 추가
                    if (alphabets[sentence.charAt(++j) - ' ']++ == 0)
                        keys++;
                }

                // 최대 길이 계산
                maxLength = Math.max(maxLength, j - i + 1);

                // i번째 문자를 제외시킨다.
                if (--alphabets[sentence.charAt(i) - ' '] == 0)
                    keys--;
            }
            // 찾은 최대 길이 출력
            sb.append(maxLength).append("\n");
            // 다음 입력
            input = br.readLine();
        }
        // 답 출력
        System.out.print(sb);
    }
}