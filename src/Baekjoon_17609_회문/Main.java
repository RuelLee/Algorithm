/*
 Author : Ruel
 Problem : Baekjoon 17609번 회문
 Problem address : https://www.acmicpc.net/problem/17609
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17609_회문;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 회문은 앞과 뒷 방향으로 볼 때, 같은 순서의 문자로 구성된 문자열을 말한다.
        // 유사회문은 하나의 글자를 지울 경우, 회문이 되는 문자열을 말한다.
        // t개의 단어가 주어질 때, 해당 단어가 회문인지, 유사회문인지, 둘 모두 아닌지를 판별하라.
        //
        // 두 포인터, 재귀 문제
        // 두 포인터를 통해, 앞과 뒤에서 문자열을 비교하며, 포인터끼리 만나거나 교차할 때까지 반복한다.
        // 그러다 일치하지 않는 문자를 만난 경우, 1회에 한해, 앞 문자를 지우거나, 뒷 문자를 지우는 것이 허용된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            // 단어
            char[] word = br.readLine().toCharArray();
            // 팰린드롬 여부 기록
            sb.append(isPalindrome(word, 0, word.length - 1, 1)).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // word의 start부터 end까지 문자들이 팬린드롬인지 확인한다.
    // remain개의 문자를 지우는 것이 허용된다.
    static int isPalindrome(char[] word, int start, int end, int remain) {
        // 두 포인터가 만나거나 교차할 때까지
        while (start < end) {
            // 일치한다면, 각각 포인터를 한 칸씩 땡김
            if (word[start] == word[end]) {
                start++;
                end--;
            }
            // remain이 남았고, 앞의 한 문자를 지우거나, 뒤의 한 문자를 지울 경우 회문이 되는 경우
            // 1을 반환
            else if (remain > 0 && (isPalindrome(word, start + 1, end, remain - 1) == 0 ||
                    isPalindrome(word, start, end - 1, remain - 1) == 0))
                return 1;
            else        // reamin이 없거나, 문자를 지우더라도 회문이 되지 않는 경우는 2를 반환
                return 2;
        }
        // 위에서 끝나지 않았다면, 그자체로 회문인 경우
        // 0 반환.
        return 0;
    }
}