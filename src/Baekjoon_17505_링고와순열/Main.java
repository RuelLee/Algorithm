/*
 Author : Ruel
 Problem : Baekjoon 17505 링고와 순열
 Problem address : https://www.acmicpc.net/problem/17505
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17505_링고와순열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 수가 주어진다.
        // 반전은 더 작은 수가 뒤에 등장하는 경우를 말한다.
        // 4 3 1 2의 경우, (4, 3), (4, 1), (4, 2), (3, 1), (3, 2)가 반전이다.
        // n개의 수 중 k개의 반전을 갖는 순열을 아무거나 하나 구하라
        //
        // 그리디 문제
        // 큰 수를 우선적으로 배치할 경우, 뒤에 오는 모든 수에 대해 반전을 가져갈 수 있다.
        // 따라서 데크를 활용하여
        // 현재 큰 수를 놓을 경우, 생기는 반전이 남은 반전의 수보다 같거나 작을 경우
        // 우선 적으로 큰 수를 배치해나가며
        // 그렇지 않은 경우엔 작은 수를 배치해나간다.
        // 최종적으로 남은 반전의 개수가 0인 경우는 해당 배치를 출력하고
        // 그렇지 않다면 -1을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수, k개의 반전
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        // 데크에 순서대로 수 추가.
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i <= n; i++)
            deque.offerLast(i);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            // 남은 반전의 수가 현재 큰 수를 둘 경우보다 더 크거나 같다면
            if (k >= (n - i)) {
                // 해당 반전의 수 만큼 감소
                k -= (n - i);
                // 우선적으로 큰 수를 배치
                sb.append(deque.pollLast()).append(" ");
            } else      // 그렇지 않은 경우엔 작은 수를 둬, 반전이 생기지 않도록 한다.
                sb.append(deque.pollFirst()).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        
        // 최종적으로 원하는 반전의 수를 만들었다면 배치 기록 출력
        // 그렇지 않다면 -1을 출력
        System.out.println(k == 0 ? sb : -1);
    }
}