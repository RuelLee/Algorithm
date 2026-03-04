/*
 Author : Ruel
 Problem : Baekjoon 32963번 맛있는 사과
 Problem address : https://www.acmicpc.net/problem/32963
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32963_맛있는사과;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 사과의 맛 ti와 크기 si가 주어진다.
        // q개의 쿼리가 주어진다.
        // p : 맛이 p 이상인 사과들 중, 크기가 가장 큰 사과의 개수
        //
        // 오프라인 쿼리, 트리 맵 문제
        // 맛이 일정 수치 이상인 것들 중 크기가 가장 큰 사과의 개수를 출력해야한다.
        // 사과들을 맛에 대해 오름차순으로 정렬하고
        // 뒤에서부터 살펴보며, 크기가 가장 큰 사과의 개수를 세어나간다.
        // 맛이 달라지는 시점이 오면, 그 때마다 오른쪽에 대해 해당 맛 이상의 사과들 중 최대 크기 사과의 개수를 기록해둔다.
        // 이를 트리 맵으로 해두면 나중에 참고하기 편하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 사과, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 사과의 정보 입력
        // 0번 사과는 맛 0, 크기 0으로 설정.
        int[][] apples = new int[n + 1][2];
        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < n + 1; j++)
                apples[j][i] = Integer.parseInt(st.nextToken());
        }
        // 맛에 대해 오름차순 정렬
        Arrays.sort(apples, Comparator.comparingInt(o -> o[0]));

        // 가장 오른쪽 사과의 크기
        int max = apples[n][1];
        // 개수
        int cnt = 1;
        TreeMap<Integer, Integer> answers = new TreeMap<>();
        // 쿼리에서 p가 최대 10억으로 주어진다.
        // 주어지는 p에 대해 만족하는 사과 맛이 없다면 1_000_000_001 사과를 참고하도록 초기값 설정
        answers.put(1_000_000_001, 0);
        for (int i = n - 1; i >= 0; i--) {
            // 만약 맛이 달라지는 지점이라면
            // 오른편에 대해 정리
            if (apples[i][0] != apples[i + 1][0])
                answers.put(apples[i + 1][0], cnt);

            // 크기가 더 커졌다면
            // max와 cnt 초기화
            if (apples[i][1] > max) {
                max = apples[i][1];
                cnt = 1;
            } else if (apples[i][1] == max)     // 크기가 같다면 cnt만 증가
                cnt++;
            // 따로 적진 않았지만
            // 크기가 더 작다면 그냥 무시
        }

        StringBuilder sb = new StringBuilder();
        // 각각의 쿼리에 대해 처리
        for (int i = 0; i < q; i++)
            sb.append(answers.ceilingEntry(Integer.parseInt(br.readLine())).getValue()).append("\n");
        // 답 출력
        System.out.print(sb);
    }
}