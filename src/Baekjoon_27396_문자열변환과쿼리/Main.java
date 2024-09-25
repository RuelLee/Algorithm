/*
 Author : Ruel
 Problem : Baekjoon 27396번 문자열 변환과 쿼리
 Problem address : https://www.acmicpc.net/problem/27396
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27396_문자열변환과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] alphabets;

    public static void main(String[] args) throws IOException {
        // 최대 길이 10만의 s가 주어지고, 쿼리의 수 n이 최대 30만까지 주어진다.
        // 1 a b -> s에 존재하는 a 알파벳을 b로 바꾼다.
        // 2 -> 바뀐 s를 출력한다
        // 위 두 쿼리들을 처리하라
        //
        // 문자열 문제
        // s가 최대 10만까지 주어지므로 당연히 일일이 바꿔서는 시간 초과.
        // 따라서 일일이 모두 바꾸는 것보다는 해당하는 알파벳이 무슨 알파벳으로 바뀌는지만 체크해두고
        // 2번 쿼리가 들어왔을 때, 바뀐 알파벳을 참조하는 형태로 풀자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 문자열 s
        String s = st.nextToken();
        // n개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        
        // 알파벳들의 원래 값
        alphabets = new int[(int) 'z' + 1];
        for (int i = 1; i < alphabets.length; i++)
            alphabets[i] = i;

        StringBuilder sb = new StringBuilder();
        // n개의 쿼리 처리
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int query = Integer.parseInt(st.nextToken());
            // 1번 쿼리일 경우
            if (query == 1) {
                // 두 알파벳
                char a = st.nextToken().charAt(0);
                char b = st.nextToken().charAt(0);

                // 알파벳들을 살펴보며, a에 해당하는 값들을 b로 바꾼다.
                for (int j = 65; j < alphabets.length; j++) {
                    if (alphabets[j] == a)
                        alphabets[j] = b;
                }
            } else {
                // s의 문자를 하나씩 살펴보며
                // 대응대는 알파벳으로 바꿔 기록한다.
                for (int j = 0; j < s.length(); j++)
                    sb.append((char) alphabets[s.charAt(j)]);
                sb.append("\n");
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}