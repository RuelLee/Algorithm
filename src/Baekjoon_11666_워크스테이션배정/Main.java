/*
 Author : Ruel
 Problem : Baekjoon 11666번 워크스테이션 배정
 Problem address : https://www.acmicpc.net/problem/11666
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11666_워크스테이션배정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 연구원들이 각각 ai분에 출근하여, si분 동안 작업을 마치고 퇴근한다.
        // 연구원들은 각각 워크스테이션을 배정받는데, 워크스테이션은 m분 초과하는 동안 작업이 없을 때 자동으로 잠긴다.
        // 이전에 퇴근한 연구원이 남긴 워크스테이션을 받아, 잠금 해제를 줄이고자할 때
        // 최대 줄일 수 있는 횟수는?
        //
        // 정렬, 우선순위큐 문제
        // 연구원들을 출근 순서에 따라 정렬한다.
        // 순서대로 연구원들을 살펴보며, 잠기지 않은 워크스테이션이 있다면
        // 해당 워크스테이션을 배정하며 센다.
        // 워크스테이션은 연구원이 퇴근할 때, 우선순위큐에 담아 관리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 연구원, 워크스테이션이 작업이 없을 경우 잠기는 시간 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 연구원들을 출근 시간에 따라 오름차순 정렬
        int[][] researchers = new int[n][];
        for (int i = 0; i < researchers.length; i++)
            researchers[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(researchers, Comparator.comparingInt(o -> o[0]));
        
        // 비어있는 워크스테이션을 우선순위큐로 관리
        PriorityQueue<Integer> emtpyComputers = new PriorityQueue<>();
        int count = 0;
        // 순차적으로 연구원을 살펴본다.
        for (int[] researcher : researchers) {
            // 워크스테이션이 반납된 시간이
            // 현재 연구원의 출근시간 - m분보다 작으면 이미 잠긴 워크스테이션
            // 해당 워크스테이션들은 제거.
            while (!emtpyComputers.isEmpty() && emtpyComputers.peek() + m < researcher[0])
                emtpyComputers.poll();
            
            // 만약 현재 연구원이 사용할 수 있는 잠기지 않은 워크스테이션이 존재한다면
            // 해당 워크스테이션 제거 및 줄인 잠금 해제 횟수 증가
            if (!emtpyComputers.isEmpty() && emtpyComputers.peek() <= researcher[0]) {
                emtpyComputers.poll();
                count++;
            }
            // 현재 연구원이 퇴근하며 해당 워크스테이션 반납
            emtpyComputers.offer(researcher[0] + researcher[1]);
        }
        // 최종적으로 줄인 최대 잠금 해제 횟수 출력
        System.out.println(count);
    }
}