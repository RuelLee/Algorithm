/*
 Author : Ruel
 Problem : Baekjoon 15732번 도토리 숨기기
 Problem address : https://www.acmicpc.net/problem/15732
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15732_도토리숨기기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 상자, k개의 규칙, d개의 도토리가 주어진다.
        // 규칙은
        // a b c로 주어지며, a부터 b상자까지 c마다의 상자에 도토리를 넣느다는 뜻이다.
        // 마지막 도토리를 넣는 상자의 번호를 출력하라
        //
        // 이분 탐색 문제
        // 이분 탐색으로, x번 상자까지 들어가는 도토리의 개수를 구해나가며
        // 범위를 좁혀나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 상자, k개의 규칙, d개의 도토리
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        
        // 규칙들
        int[][] rules = new int[k][3];
        for (int i = 0; i < rules.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < rules[i].length; j++)
                rules[i][j] = Integer.parseInt(st.nextToken());
        }
        // 범위에 따라 정렬
        Arrays.sort(rules, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        
        // 이분 탐색
        // 1번 상자부터, 1백만번 상자까지
        int start = 1;
        int end = 1_000_000;
        while (start < end) {
            int mid = (start + end) / 2;

            // 들어가는 도토리의 개수
            // 는 int 범위를 넘어설 수 있다.
            long count = 0;
            for (int[] rule : rules) {
                // 시작 범위가 mid보다 크다면, 
                // 더 이상 규칙을 살펴보지 않고 반복문 종료
                if (rule[0] > mid)
                    break;
                
                // 끝 범위는 mid와 rule[1] 중 더 작은 값
                // 범위 안에 들어가는 도토리의 수 누적
                count += ((Math.min(rule[1], mid) - rule[0]) / rule[2]) + 1;
            }

            // 도토리의 개수가 d보다 같거나 크다면
            // end 범위를 mid로 줄이고
            if (count >= d)
                end = mid;
            else        // 더 적다면 start를 mid + 1로 키운다.
                start = mid + 1;
        }
        // 답 출력
        System.out.println(start);
    }
}