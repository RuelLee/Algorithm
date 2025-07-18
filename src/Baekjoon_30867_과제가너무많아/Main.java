/*
 Author : Ruel
 Problem : Baekjoon 30867번 과제가 너무 많아
 Problem address : https://www.acmicpc.net/problem/30867
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30867_과제가너무많아;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 l의 문자열 s가 주어진다.
        // i번째 문자와 i+1번째 문자가 w와 h라면, 두 개의 순서를 바꾼다.
        // 이 시행을 총 n회 했을 때, s를 출력하라
        //
        // 애드혹
        // w가 연속하여있는 한, h가 최대 n번 앞으로 갈 수 있다.
        // 문자열이 wwwwh이며 n이 4인 경우 hwwww가 된다.
        // 따라서 w가 연속하여있는 경우, 그 수를 세어주고
        // 그 뒤에 바로 h가 온 경우, 연속한 w의 개수와 n을 비교하여 적은 만큼 앞으로 땅겨준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 l, 시행의 횟수 n
        int l = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        char[] s = br.readLine().toCharArray();
        // 연속한 w의 개수
        int wCount = 0;
        for (int i = 0; i < s.length; i++) {
            switch (s[i]) {
                // w인 경우, 연속 개수 추가
                case 'w' -> wCount++;
                // h인 경우
                // wCount와 n을 비교하여 더 적은 수 만큼의 w와 h를 교체한다.
                case 'h' -> {
                    int idx = i - Math.min(wCount, n);
                    s[i] = 'w';
                    s[idx] = 'h';
                }
                // 그 외의 경우, 다른 문자들은 wCount를 0으로 초기화
                default -> wCount = 0;
            }
        }
        
        // 답 출력
        StringBuilder sb = new StringBuilder();
        for (char c : s)
            sb.append(c);
        System.out.println(sb);
    }
}