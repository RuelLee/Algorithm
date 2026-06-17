/*
 Author : Ruel
 Problem : Jungol 5501번 두 문자 제거 (delete two characters)
 Problem address : https://jungol.co.kr/problem/5501
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5501_두문자제거_deleteTwoCharacters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스마다 문자열이 각각 길이와 함께 주어진다.
        // 이 때, 연속한 두 문자를 제거하여 만들 수 있는 새로운 문자열의 개수를 계산하라
        //
        // 애드 혹 ㅜㅁ베
        // 연속한 두 문자를 제거하여, 같은 문자열이 생기는지 여부를 판단해야한다.
        // aba와 같은 경우, ab를 지우든, ba를 지우든 a만 남게 된다.
        // 따라서 연속한 세 문자 중 앞과 뒤의 문자가 같다면, 그 중 하나가 남아 같은 문자열이 되게 된다.
        // 길이가 n인 문자열인 경우, 두 문자를 지울 수 있는 경우의 수는 n-1개이고
        // 이 중 연속한 세 문자 중 앞 뒤 문자가 같은 경우의 수를 빼주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 문자열의 길이와 문자열
            int n = Integer.parseInt(br.readLine());
            String input = br.readLine();
            // 연속한 세 문자 중 앞 뒤의 문자가 같은 경우를 센다.
            int cnt = 0;
            for (int i = 0; i < input.length() - 2; i++) {
                if (input.charAt(i) == input.charAt(i + 2))
                    cnt++;
            }
            // 답 기록
            sb.append(n - 1 - cnt).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}