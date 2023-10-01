/*
 Author : Ruel
 Problem : Baekjoon 17951번 흩날리는 시험지 속에서 내 평점이 느껴진거야
 Problem address : https://www.acmicpc.net/problem/17951
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17951_흩날리는시험지속에서내평점이느껴진거야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 시험지가 주어진다.
        // 순서대로 k개의 그룹으로 묶어 각 그룹의 점수 합 중 최소값을 전체 시험의 점수로 한다.
        // 받을 수 있는 최대 점수는?
        //
        // 이분 탐색 문제
        // 가능한 점수를 정해두고, 해당 점수로 k개 이상의 그룹으로 나눠지는지 확인한다.
        // 위 과정을 이분 탐색으로 점수들의 범위를 좁혀나가며 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 시험지
        int n = Integer.parseInt(st.nextToken());
        // k개의 그룹
        int k = Integer.parseInt(st.nextToken());
        
        // 점수들
        int[] scores = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 이분 탐색
        int start = 0;
        int end = (n / k) * 20 + 20;
        while (start <= end) {
            int mid = (start + end) / 2;
            // mid 점수로 k개 이상의 그룹으로 나누는 것이 가능한 경우
            if (binarySearch(k, mid, scores))
                start = mid + 1;
            // 불가능 한 경우
            else
                end = mid - 1;
        }
        // 찾은 점수값 출력.
        System.out.println(end);
    }
    
    // score 점수로 k개 이상 그룹 나누는 것이 가능한 경우
    static boolean binarySearch(int k, int score, int[] scores) {
        // 이번 그룹 합
        int sum = 0;
        // 찾은 그룹의 개수
        int count = 0;
        for (int i = 0; i < scores.length; i++) {
            // 이번 그룹 합에 i번째 점수 추가
            sum += scores[i];
            // 이번 점수로 score를 달성했다면 그룹 분리
            if (sum >= score) {
                sum = 0;
                // 찾은 그룹의 수 추가
                count++;
            }
        }
        // 최종적으로 나눈 그룹의 개수가 k개 이상이라면 가능한 경우
        return count >= k;
    }
}