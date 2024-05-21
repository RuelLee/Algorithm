/*
 Author : Ruel
 Problem : Baekjoon 30015번 학생회 뽑기
 Problem address : https://www.acmicpc.net/problem/30015
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30015_학생회뽑기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생과 각각의 능력치(A1, ... An)가 주어진다.
        // 0 <= An < 2^20
        // 그 중 k명을 학생회 멤버로 뽑았을 때, 학생회의 능력은 모든 멤버의 능력치에 대한 &연산으로 정의된다고 한다.
        // 학생회의 능력 = A1 & A2 & ... & An
        // 학생회 능력의 최댓값을 구하라
        //
        // 비트마스킹, 그리디 알고리즘
        // 학생회의 능력이 비트연산 &으로 이루어지므로
        // 가장 높은 19번째 자리부터 해당 비트에 값이 1인 학생이 k명이 넘는지 계산한다.
        // 만약 넘는다면 우선적으로 해당 인원들을 후보에 넣고, 나머지 인원들을 배제한다.
        // 18 ~ 1번째 자리에 모두 1이 있더라도 19번째 자리에 1이 있는 것이 더 크므로.
        // 그 이후, 높은 순으로 비트를 순회하며, 배제된 인원은 제외하고 k명 이상이 만족하는 자리가 더 있는지 확인하며
        // 학생회의 능력치를 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, k명의 학생회 멤버
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 능력치
        int[] abilities = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int answer = 0;
        // 배제 여부
        boolean[] notSelected = new boolean[n];
        // 가장 큰 자리의 비트부터 높은순으로 살펴본다.
        for (int i = 19; i >= 0; i--) {
            // 해당하는 인원이 몇 명인지 센다.
            int count = 0;
            Queue<Integer> excluded = new LinkedList<>();
            for (int j = 0; j < abilities.length; j++) {
                // 이미 배제된 학생이라면 건너뛴다.
                if (notSelected[j])
                    continue;
                else if ((abilities[j] & (1 << i)) != 0)        // 해당 비트에 1이 있다면 count 증가
                    count++;
                else        // 그렇지 않다면 배제 가능 인원에 추가.
                    excluded.offer(j);
            }

            // 만약 i번째 비트에 1이 있는 인원이 k명 이상이라면
            if (count >= k) {
                // 배제 후보 명단에 있던 학생들을 모두 배제
                while (!excluded.isEmpty())
                    notSelected[excluded.poll()] = true;
                // 답에는 i번째 비트에 1을 추가시켜준다.
                answer |= (1 << i);
            }
        }
        // 계산된 답 출력
        System.out.println(answer);
    }
}