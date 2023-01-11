/*
 Author : Ruel
 Problem : Baekjoon 6068번 시간 관리하기
 Problem address : https://www.acmicpc.net/problem/6068
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6068_시간관리하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 농부에게 해야하는 n개의 일에 대한 정보들이 주어진다.
        // 소요 시간 T와 끝내야하는 시간 S가 주어질 때
        // 가장 늦게 첫 일을 시작해도 되는 시간을 구하고 싶다.
        // 해당 시간은?
        //
        // 정렬과 그리디에 관한 문제
        // 모든 일에 대해서 마감 시간 내림차순으로 정렬한 뒤
        // 일들을 살펴보며 해당 일을 시작해야하는 가장 늦은 시간을 차례대로 구해나간다.
        // 마지막 일까지 살펴보며, 모든 일을 마치는데 필요한 가장 늦은 첫 일 시작 시간이
        // 0보다 크다면 가능한 경우, 0보다 작다면 불가능한 경우이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 일들에 대한 입력
        int[][] works = new int[n][2];
        for (int i = 0; i < works.length; i++)
            works[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 작업들을 종료 시간에 따른 내림차순 정렬한다.
        Arrays.sort(works, (o1, o2) -> Integer.compare(o2[1], o1[1]));

        // 제한 시간 or 해당 작업을 마치고 다음 작업을 시작해야하는 시간.
        int limitTime = Integer.MAX_VALUE;
        for (int[] work : works) {
            // 다음 작업을 limitTime에 시작해야하므로
            // limitTime과 i번째 작업을 마쳐야하는 시간들 중 더 이른 시각에서
            // i번째 작업의 소요 시간을 뺀 만큼의 시각이 i번째 작업을 시작해야하는 시간.
            limitTime = Math.min(limitTime, work[1]) - work[0];

            // 0보다 작아져버렸다면 해당 작업들을 마칠 수 없는 경우.
            // 반복문 탈출
            if (limitTime < 0)
                break;
        }
        
        // limitTime 값을 살펴보고 0보다 같거나 크다면 해당 시간 출력
        // 아니라면 불가능한 경우이므로 -1 출력
        System.out.println(limitTime < 0 ? -1 : limitTime);
    }
}