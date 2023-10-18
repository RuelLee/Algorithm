/*
 Author : Ruel
 Problem : Baekjoon 9519번 졸려
 Problem address : https://www.acmicpc.net/problem/9519
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9519_졸려;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // 눈을 한 번 깜박하면 단어의 뒷 부분 절반이 앞 부분과 섞이게 된다.
        //
        // 마지막 글자가 첫 번째 글자와 두 번째 글자 사이로 이동한다.
        // 뒤에서 두 번째 글자가 두 번째 글자와 세 번째 글자 사이로 이동한다.
        // 뒤에서 k번째 글자는 앞에서부터 k번째와 k+1번째 글자 사이로 이동한다.
        // x번 깜빡인 후의 단어가 주어졌을 때
        // 원래 단어를 구하라
        //
        // 시뮬레이션 문제
        // X가 최대 10억으로 주어지므로, 전부 구하라는 말은 아니고
        // 반복되는 사이클이 생기고, 이를 통해 구하는 방법이다.
        // 먼저 위에 주어진 조건에 따라
        // 하나 이전의 단어로 거슬러 올라가는 방법은 섞이는 방법으로 역으로 풀어낸다.
        // 홀수번째 글자들을 쭉 이어붙인 후
        // 뒤에서부터 짝수번째 글자들을 이어붙이면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 깜빡인 횟수와 단어
        int x = Integer.parseInt(br.readLine());
        String word = br.readLine();

        // n번 이전의 단어들을 순서대로 리스트에 담는다.
        List<String> list = new ArrayList<>();
        list.add(word);
        // 원래 단어
        StringBuilder pre = new StringBuilder(word);
        // 다음 단어
        StringBuilder next = new StringBuilder();
        int cycle = -1;
        for (int i = 0; i < x; i++) {
            // 홀수번째 글자들을 쭉 이어붙인 후
            for (int j = 0; j < pre.length(); j += 2)
                next.append(pre.charAt(j));
            // 뒤에서부터 짝수번째 단어들을 이어붙인다.
            for (int j = (pre.length() % 2 == 0 ? pre.length() - 1 : pre.length() - 2); j >= 0; j -= 2)
                next.append(pre.charAt(j));
            // 완성된 결과를 pre에 넣고.
            pre = next;
            next = new StringBuilder();
            
            // 해당 단어가 리스트에 있는지 확인.
            // 있다면 사이클이 발견된 것이므로 종료
            if (list.contains(pre.toString())) {
                cycle = i + 1;
                break;
            } else      // 그렇지 않다면 리스트에 추가
                list.add(pre.toString());
        }

        // 사이클이 발견됐다면 사이클을 토대로 모듈러 연산을 해 답을 구하고
        // 그렇지 않은 경우는 모든 답을 구했으므로 해당하는 답을 출력한다.
        System.out.println(cycle != -1 ? list.get(x % cycle) : list.get(x));
    }
}