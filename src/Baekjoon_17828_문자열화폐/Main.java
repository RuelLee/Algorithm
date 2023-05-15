/*
 Author : Ruel
 Problem : Baekjoon 17828번 문자열 화폐
 Problem address : https://www.acmicpc.net/problem/17828
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17828_문자열화폐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문자열나라에서 알파벳 대문자로 구성된 문자열을 화폐로 사용한다.
        // 'A' 는 1의 가치, ... 'Z' 는 26의 가치를 갖는다.
        // 이 알파벳들을 이어붙여 문자열을 화폐로 쓰며 각 알파벳의 합이 가치가 된다.
        // 환전소에서는 사전순으로 앞서는 문자열을 우선적으로 환전해준다고 한다.
        // 사전순으로 가장 앞서는 길이 n과 가치x의 문자열을 구하라
        //
        // 그리디 문제
        // 어떻게해서든 앞부분에 사전순으로 가장 앞서는 문자가 오면 우선적으로 환전을 받을 수 있다.
        // 따라서 가능한 가장 큰 알파벳으로 문자열을 만들어, 나머지를 모두 A로 채운 뒤
        // 사전순으로 가장 앞서도록 역순으로 배치한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 길이와 가치
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        // 우리는 가장 큰 가치를 갖는 문자를 우선적으로 뒤로 배치할 것이다.
        // 따라서 스택을 이용
        Stack<Integer> stack = new Stack<>();
        // 남은 길이가 0 이상이고,
        // 남은 가치가 남은 길이보다 같거나 커야하며
        // 남은 가치가 남은 길이를 모두 Z로 변환했을 때보단 작거나 같아야한다.
        while (n > 0 && x >= n && x <= n * 26) {
            // 이번에 바꿀 알파벳의 가치
            // 남은 자리를 고려하여 가장 큰 값과 실제로 가능한 가장 큰 알파벳 Z의 가치 중
            // 더 작은 것을 택한다.
            int value = Math.min(x - (n - 1), 26);
            // 스택에 넣고
            stack.push(value);
            // 남은 가치에서 가치만큼을 제외하고
            x -= value;
            // 길이 1 감소
            n--;
        }

        StringBuilder sb = new StringBuilder();
        // 스택에 담긴 모든 알파벳들을 꺼내며 StringBuilder로 문자열을 생성해준다.
        while (!stack.isEmpty())
            sb.append((char) (stack.pop() + 'A' - 1));
        // 만약 StringBuilder가 비어있다면 불가능한 경우이므로 !을 출력하고
        // 그렇지 않다면 화폐(문자열)을 출력한다.
        System.out.println(sb.isEmpty() ? '!' : sb);
    }
}