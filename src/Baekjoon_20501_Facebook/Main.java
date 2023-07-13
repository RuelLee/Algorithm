/*
 Author : Ruel
 Problem : Baekjoon 20501번 Facebook
 Problem address : https://www.acmicpc.net/problem/20501
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20501_Facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람과 친구관계가 주어진다.
        // q개의 쿼리로서 a, b가 가진 공통된 친구의 수를 묻는다면 답하는 프로그램을 작성하라
        //
        // 비트셋 문제
        // 공통된 이라는 말에서 비트마스킹의 향기가 난다.
        // 하지만 n이 int나 long 범위를 훨씬 넘어서므로
        // 이를 대체할 수 있는 비트셋을 활용하여 문제를 해결한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 사람
        int n = Integer.parseInt(br.readLine());
        // 해당하는 비트셋을 리스트로 준비
        List<BitSet> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            list.add(new BitSet());

        for (int i = 0; i < n; i++) {
            // i번째 사람의 친구관계
            String input = br.readLine();
            for (int j = 0; j < n; j++) {
                // j와 친구라면 해당 비트를 설정해준다.
                if (input.charAt(j) == '1')
                    list.get(i).set(j);
            }
        }

        StringBuilder sb = new StringBuilder();
        // q개의 쿼리
        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 공통된 친구의 수를 세려는 대상 a, b
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            
            // 답안용 비트셋
            BitSet answer = (BitSet) list.get(a).clone();
            // a와 b를 and 연산한다.
            answer.and(list.get(b));
            // answer에 살아있는 비트의 수를 기록한다.
            sb.append(answer.cardinality()).append("\n");
        }
        
        // 전체 답안 출력
        System.out.print(sb);
    }
}