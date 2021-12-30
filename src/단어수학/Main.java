/*
 Author : Ruel
 Problem : Baekjoon 1339번 단어 수학
 Problem address : https://www.acmicpc.net/problem/1339
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 단어수학;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수의 숫자들이 알파벳 대문자로 치환되어 주어진다
        // 알파벳에 어느 숫자를 할당하는 것이 수들의 합을 최대로 할 수 있는지 구하는 문제
        // 각 알파벳이 어느 위치에 몇번 나왔는지가 중요하다
        // A라는 알파벳이 1의 자리에 나왔다면 +1, 십의 자리에 나왔다면 +10.. 처럼 자리수에 관해 더한 합을 기록하자
        // 그 후 그 값이 가장 큰 값부터 9부터 할당하여 그 값을 더해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        String[] words = new String[n];
        int[] alphabets = new int[26];      // 어느 알파벳이 어느 자리에 몇 번 나왔는지 기록
        for (int i = 0; i < words.length; i++) {
            words[i] = br.readLine();
            int multi = 1;
            for (int j = words[i].length() - 1; j >= 0; j--) {      // 끝의 자리부터 10배씩 해가면서
                alphabets[words[i].charAt(j) - 'A'] += multi;       // 해당하는 자리에 더해준다.
                multi *= 10;
            }
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));
        for (int i : alphabets)
            pq.offer(i);

        int answer = 0;
        int num = 9;
        // 최종적으로 모인 빈도수를 내림차순으로 뽑아내며, 9부터 1까지 할당해가며 곱을 더해주자
        while (!pq.isEmpty() && pq.peek() > 0)
            answer += pq.poll() * num--;
        System.out.println(answer);
    }
}