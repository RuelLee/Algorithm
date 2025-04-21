/*
 Author : Ruel
 Problem : Baekjoon 11565번 바이너리 게임
 Problem address : https://www.acmicpc.net/problem/11565
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11565_바이너리게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1과 0으로만 이루어진 문자열 a와 b가 주어진다.
        // a의 문자열을 b문자열로 바꾸는데 다음과 같은 두 연산을 할 수 있다.
        // 맨 앞의 한 글자를 삭제한다.
        // 맨 뒤에 한 글자를 추가한다. a에 있는 문자열에 1 개수가 홀수라면 1, 짝수라면 0이 추가된다.
        // a를 b 문자열로 만들 수 있는지 여부를 판단하라
        //
        // 애드 혹 문제
        // 몇 가지 경우를 직접해보면,
        // 1의 개수가 홀수일 때는 하나 추가하여 짝수가 될 수 있다.
        // 0이 몇 개가 있던, 1을 적절히 추가하거나 삭제하면서 뒤에 추가하여 0의 위치를 재배열할 수 있다.
        // 라는 점을 알 수 있다.
        // 결국 a에 포함된 1의 개수를 세어, a 문자열의 최대 1의 개수를 세고
        // 이 개수가 b에 포함된 1의 개수보다 많거나 같은지 여부를 판단하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // a, b 문자열
        String a = br.readLine();
        String b = br.readLine();
        
        // 각각의 1의 개수
        int[] counts = new int[2];
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '1')
                counts[0]++;
        }

        for (int i = 0; i < b.length(); i++) {
            if (b.charAt(i) == '1')
                counts[1]++;
        }

        // a에 포함된 1의 개수가 홀수라면 +1개 한 개수, 짝수라면 그대로의 개수와
        // b에 포함된 1의 개수를 비교하여, 게임 승리 여부를 판단한다.
        System.out.println((counts[0] % 2 == 0 ? 0 : 1) + counts[0] >= counts[1] ? "VICTORY" : "DEFEAT");
    }
}