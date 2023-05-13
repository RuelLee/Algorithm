/*
 Author : Ruel
 Problem : Baekjoon 1097번 마법의 문자열
 Problem address : https://www.acmicpc.net/problem/1097
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1097_마법의문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static String[] words;
    static int k;

    public static void main(String[] args) throws IOException {
        // L개의 문자로 이루어진 문자열 T가 있다.
        // T(i)는 i번째 문자부터 시작되게 원형 이동한 문자열이다.
        // T(i)와 T가 같게되는 i가 정확히 k개 있다면 이를 마법의 문자열이라고 부른다.
        // N개의 문자열이 주어지고, 순열을 통해 문자열을 이어붙인 새로운 문자열을 만들 수 있다.
        // 이 때, 마법의 문자열은 총 몇 개인가
        //
        // 브루트포스 + KMP 문제
        // 먼저 순열을 통해 새로운 문자열을 만들어낸 후
        // 만들어진 문자열을 통해 원래 문자열과 같은 T(i)가 몇개 있는지 세어주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 문자열
        int n = Integer.parseInt(br.readLine());
        words = new String[n];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();
        // 상수 k값
        k = Integer.parseInt(br.readLine());

        System.out.println(findAnswer(0, new boolean[n], new StringBuilder()));
    }

    // 순열을 통해 새로운 문자열을 만들고
    // 해당 순열이 마법의 문자열인지 판별한다.
    static int findAnswer(int idx, boolean[] selected, StringBuilder sb) {
        // 순열을 통해 새로운 문자열 생성이 끝났다면
        if (idx == words.length) {
            // KMP 알고리즘에 따라
            // pi 배열을 만들고
            int[] pi = new int[sb.length()];
            for (int i = 1, j = 0; i < pi.length; i++) {
                while (j > 0 && sb.charAt(i) != sb.charAt(j))
                    j = pi[j - 1];

                if (sb.charAt(i) == sb.charAt(j))
                    pi[i] = ++j;
            }

            // 마법의 문자열을 만족하는 i가 몇개 있는지 센다.
            int count = 0;
            for (int i = 1, j = 0; i < pi.length * 2; i++) {
                // 원형 이동이기 때문에, i는 새로운 문자열의 길이 * 2까지 살펴본다.
                while (j > 0 && sb.charAt(i % pi.length) != sb.charAt(j))
                    j = pi[j - 1];

                if (sb.charAt(i % pi.length) == sb.charAt(j))
                    j++;

                // 만약 길이가 생성된 문자열과 같은 길이라면
                // 마법의 문자열을 만족하는 i를 하나 찾았다.
                // count 증가
                // j는 이전으로 되돌려, 현재 세어진 길이와 중복하여 만족하는 마법의 문자열이 존재하는지 확인한다.
                if (j == sb.length()) {
                    count++;
                    j = pi[j - 1];
                }
            }

            // count가 정확히 k와 일치한다면 마법의 문자열
            // 해당 순열이 마법의 문자열을 만족하는 것이므로 1 반환.
            // 그렇지 않다면 0 반환.
            return count == k ? 1 : 0;
        }
        
        // 순열을 통해 새로운 문자열 생성
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            if (!selected[i]) {
                // words[i]가 아직 선택되지 않았다면 StringBuilder에 words[i]를 이어붙이고
                sb.append(words[i]);
                // 선택 체크
                selected[i] = true;
                // 메소드를 재귀 호출해 다음 단어를 이어붙인다.
                // 그러한 결과 생성된 문자열 중 마법의 문자열의 개수가 반환되어온다.
                // 그 값읋 count에 더해준다.
                count += findAnswer(idx + 1, selected, sb);
                // 위 과정이 끝났다면 선택 해제.
                selected[i] = false;
                // words[i]에 해당하는 만큼을 StringBuilder에서 제거해준다.
                sb.delete(sb.length() - words[i].length(), sb.length());
            }
        }

        // 찾은 마법의 문자열의 개수 반환.
        return count;
    }
}