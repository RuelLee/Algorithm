/*
 Author : Ruel
 Problem : Baekjoon 31778번 PPC 만들기
 Problem address : https://www.acmicpc.net/problem/31778
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31778_PPC만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 문자열 s가 주어진다.
        // 이 문자열은 P 혹은 C로만 이루어져있고,
        // k번 동안 두 문자의 자리를 바꾸는 것이 가능하다.
        // 이렇게 바꾸는 것을 통해 부분문자열이 PPC가 가장 많게 만드는 것이 목적이다.
        // 부분문자열의 최대 수는?
        //
        // 두 포인터, 조합 문제
        // PPC가 가장 많게끔 바꾸려면, P는 최대한 앞쪽에, C는 최대한 뒤 쪽에 배치하는 것이 유리하다.
        // k번 동안 해당하는 P와 C를 바꿔준다.
        // 그 후, 앞에서부터 살펴보며
        // 여태까지 등장한 P로 만들 수 있는 PP를 쌓아간다.
        // 새로운 P가 등장할 때마다 이전에 등장한 p의 개수 만큼 만들 수 있는 PP의 개수가 늘어난다.
        // 그리고 C가 등장할 때마다, 여태 만든 PP의 개수만큼 ppc 부분문자열의 개수가 증가한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n의 문자열, k번의 교환 횟수
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 최대한 앞쪽의 C와 최대한 뒤의 P를 k번 교환한다.
        char[] s = br.readLine().toCharArray();
        int right = s.length - 1;
        for (int left = 0; left < right && k > 0; left++) {
            if (s[left] == 'P')
                continue;

            while (right > left && s[right] == 'C')
                right--;

            if (left < right) {
                s[left] = 'P';
                s[right] = 'C';
                k--;
            }
        }
        
        // 만들어진 PP의 개수
        long combinations = 0;
        // 부분문자열의 개수
        long answer = 0;
        // P개의 개수
        int pCounter = 0;
        for (int i = 0; i < n; i++) {
            switch (s[i]) {
                // P가 등장한 경우
                // 여태까지의 pCounter만큼 pp조합이 증가하고, pCounter 증가
                case 'P' -> combinations += pCounter++;
                // C가 등장한 경우
                // pp조합만큼 부분문자열의 개수 증가
                case 'C' -> answer += combinations;
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}