/*
 Author : Ruel
 Problem : Baekjoon 1593번 문자 해독
 Problem address : https://www.acmicpc.net/problem/1593
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1593_문자해독;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 단어 w와, 문자열 s가 주어진다.
        // 문자열  s 안에서 문자열 w의 순열 중 하나가 부분 문자열로 들어있는 모든 경우의 수를 구하라
        //
        // 슬라이딩 윈도우 문제
        // w에 대해 모든 알파벳의 개수를 센 후
        // 일정 범위 내에 해당 알파벳이 모두 쓰였는지 체크한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // w와 s의 길이
        int g = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 단어 w, 문자열 s
        String w = br.readLine();
        String s = br.readLine();

        // 단어에 속한 알파벳의 개수를 센다.
        int[] alphabets = new int['z' - 'A' + 1];
        for (char c : w.toCharArray())
            alphabets[c - 'A']++;

        // 경우의 수
        int count = 0;
        // 끝점 포인터
        int end = 0;
        // 시작점 포인터는 계속해서 증가한다.
        for (int start = 0; start < s.length(); start++) {
            // 만약 end가 start보다 작다면 start와 같은 위치를 가르키도록 한다.
            if (end < start)
                end = start;

            // end 포인터를 w에 해당하는 문자들의 개수가 허락하는 만큼 증가시킨다.
            while (end < s.length() && alphabets[s.charAt(end) - 'A'] > 0)
                alphabets[s.charAt(end++) - 'A']--;

            // 만약 start와 end 포인터가 같은 곳을 가르키고 있다면
            // 가르키는 곳이 w에 해당하는 문자가 아닌 것.
            // 건너뛴다.
            if (start == end)
                continue;
            else if (end - start == g)      // 만약 두 포인터들 사이의 거리가 w 단어 길이라면 count를 증가시킨다.
                count++;

            // 현재 start가 가르키는 문자의 미사용 개수를 증가시키고
            // start 포인터는 start + 1로 이동한다.
            alphabets[s.charAt(start) - 'A']++;
        }
        System.out.println(count);
    }
}