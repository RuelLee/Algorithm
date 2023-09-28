/*
 Author : Ruel
 Problem : Baekjoon 1263번 시간 관리
 Problem address : https://www.acmicpc.net/problem/1263
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1263_시간관리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 일에 대한 정보가 주어진다.
        // 각 일은 일을 하는데 걸리는 시간과 마감 시간이 주어진다.
        // 모든 일을 처리하는데, 첫 일을 가장 늦은 시간에 시작하고 싶다.
        // 일을 시작하게 되는 시간은?
        //
        // 그리디 문제
        // 일을 마감 시간에 대해 내림차순 정렬을 한다.
        // 그 후 마감시간에 따라 마지막 일부터 배치해나가며
        // 첫 일의 시작 시간을 가장 늦춘 시간을 찾아낸다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 일
        int n = Integer.parseInt(br.readLine());
        
        // 일들의 작업 시간과 마감 시간
        int[][] works = new int[n][];
        for (int i = 0; i < works.length; i++)
            works[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 마감 시간에 대해 내림차순 정렬
        Arrays.sort(works, (o1, o2) -> Integer.compare(o2[1], o1[1]));
        
        // 마지막 작업부터 배치해나간다.
        // 마지막 작업의 시작 시간은 마감시간 - 작업 시간
        int nextWorkStartTime = works[0][1] - works[0][0];
        for (int i = 1; i < works.length; i++) {
            // i - 1번 작업이 nextWorkStartTime에 시작되어야하므로
            // i번 작업은 자신의 마감 시간과 nextWorkStartTime을 비교하여
            // 더 이른 시간에 작업이 종료되어야한다.
            nextWorkStartTime = Math.min(nextWorkStartTime, works[i][1]);
            // 해당하는 i번째 작업 시작 시간을 계산
            nextWorkStartTime -= works[i][0];
            
            // 만약 작업시간이 음수로 내려갔다면
            // 모든 작업을 완료하는 것이 불가능한 경우
            if(nextWorkStartTime < 0)
                break;
        }

        // 모든 작업을 마치는 것이 불가능하다면 -1
        // 가능하다면 시작 시간을 출력한다.
        System.out.println(nextWorkStartTime < 0 ? -1 : nextWorkStartTime);
    }
}