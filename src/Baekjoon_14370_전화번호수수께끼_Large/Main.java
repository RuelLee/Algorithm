/*
 Author : Ruel
 Problem : Baekjoon 14370번 전화번호 수수께끼 (Large)
 Problem address : https://www.acmicpc.net/problem/14370
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14370_전화번호수수께끼_Large;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final String[] numbers = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN"};
    static final int[] order = {0, 2, 4, 6, 8, 1, 5, 3, 7, 9};

    public static void main(String[] args) throws IOException {
        // 전화번호가 영어로 변환된 뒤, 그 알파벳들이 순서없이 섞여있다.
        // 전화번호는 오름차순으로 정렬되어있다.
        // 문자열이 주어질 때, 해당하는 전화번호를 찾아라
        //
        // 애드 혹
        // 먼저 문자열을 알파벳에 따라 수로 분리한다.
        // 그 후, 해당하는 수가 존재하는지를 찾는데,
        // 남은 수들 중 유일한 알파벳을 갖고 있는 수들을 먼저 찾는다.
        // 예를 들어 'Z'ERO -> T'W'O -> FO'U'R -> ...
        // 와 같은 순서이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스의 수
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 주어진 문자열
            String s = br.readLine();
            // 을 알파벳 수로 변환
            int[] alphabets = new int[26];
            for (int i = 0; i < s.length(); i++)
                alphabets[s.charAt(i) - 'A']++;
            
            // 전화번호에 존재하는 수 계산
            int[] counts = new int[10];
            // 순서에 따라 수들이 존재하는지 찾는다.
            for (int o : order) {
                boolean found = true;
                while (found) {
                    // numbers[o]에 존재하는 알파벳이 모두 존재하는지 체크
                    for (int i = 0; i < numbers[o].length(); i++) {
                        if (alphabets[numbers[o].charAt(i) - 'A'] < 1) {
                            found = false;
                            break;
                        }
                    }

                    // 존재한다면 해당 수는 반드시 존재.
                    // 해당하는 수의 개수 증가 후
                    // 해당하는 알파벳들 감소.
                    if (found) {
                        counts[o]++;
                        for (int i = 0; i < numbers[o].length(); i++)
                            alphabets[numbers[o].charAt(i) - 'A']--;
                    }
                }
            }
            // 찾은 테스트케이스의 답안 작성
            sb.append("Case #").append(testCase + 1).append(": ");
            for (int i = 0; i < counts.length; i++) {
                for (int j = 0; j < counts[i]; j++)
                    sb.append(i);
            }
            sb.append("\n");
        }
        // 전체 답안 출력
        System.out.println(sb);
    }
}