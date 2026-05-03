/*
 Author : Ruel
 Problem : Jungol 1570번 중앙값
 Problem address : https://jungol.co.kr/problem/1570
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1570_중앙값;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n(홀수)개의 수가 한개, 그 뒤로 2개씩 주어진다.
        // 각 턴마다 중앙값을 출력하라
        //
        // 우선순위큐 문제
        // 중앙값을 기준으로 왼쪽과 오른쪽에 우선순위큐를 두고
        // 두 우선순위큐의 크기가 같도록 조절하며 중앙값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());

        // 두 개의 우선순위큐
        PriorityQueue<Integer> left = new PriorityQueue<>(Comparator.reverseOrder());
        left.offer(Integer.parseInt(br.readLine()));
        PriorityQueue<Integer> right = new PriorityQueue<>();

        // 답 작성
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        // 첫번째 중앙값
        bw.write(String.valueOf(left.peek()));
        bw.newLine();
        StringTokenizer st;
        for (int i = 0; i < n / 2; i++) {
            // 두 개의 수 입력
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++) {
                int num = Integer.parseInt(st.nextToken());
                // 우선순위큐 크기에 따른 수 배정
                if (left.size() <= right.size())
                    left.offer(num);
                else
                    right.offer(num);
            }
            // 서로 간의 최대값, 최소값이 역전되는 경우 값을 교 체한다.
            while (!left.isEmpty() && !right.isEmpty() && left.peek() > right.peek()) {
                int temp = right.poll();
                right.offer(left.poll());
                left.offer(temp);
            }
            // 중앙값 기록
            bw.write(String.valueOf(left.peek()));
            bw.newLine();
        }
        // 답 출력
        bw.flush();
        bw.close();
    }
}