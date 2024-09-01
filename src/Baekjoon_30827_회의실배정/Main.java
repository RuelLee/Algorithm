/*
 Author : Ruel
 Problem : Baekjoon 30827번 회의실 배정
 Problem address : https://www.acmicpc.net/problem/30827
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30827_회의실배정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // k개의 회의실, n개의 회의가 주어진다.
        // 각각의 회의는 시작시간과 종료 시간이 주어진다.
        // 회의실에는 두 개의 회의가 동시에 진행될 수 없으며
        // 회의가 끝난 즉시 다른 회의가 시작될 수 없다.
        // 최대한 많은 회의를 진행하고자할 때, 진행한 회의들의 수는?
        //
        // 그리디, 정렬 문제
        // 회의를 종료 시간에 대해 오름차순으로 살펴본다.
        // 그러면서 회의실에 빈 공간이 있다면 회의를 진행하고
        // 그렇지 않다면 건너뛰는 식으로 살펴본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 회의, k개의 회의실
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 회의들
        int[][] meetings = new int[n][2];
        for (int i = 0; i < meetings.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < meetings[i].length; j++)
                meetings[i][j] = Integer.parseInt(st.nextToken());
        }
        // 종료 시간에 따른 오름차순 정렬
        Arrays.sort(meetings, Comparator.comparingInt(o -> o[1]));

        // 회의실의 회의 종료 시간
        int[] meetingRooms = new int[k];
        int count = 0;
        for (int[] meeting : meetings) {
            int idx = -1;
            // 현재 진행하려는 회의보다 일찍 종료되었으며
            // 그러한 회의실이 여러개라면 가장 늦게 종료된 회의실을 찾는다.
            for (int i = 0; i < meetingRooms.length; i++) {
                if (meetingRooms[i] < meeting[0] &&
                        (idx == -1 || meetingRooms[idx] < meetingRooms[i]))
                    idx = i;
            }

            // 해당 회의실을 찾았다면 회의 배정
            // 진행한 회의의 개수 증가
            if (idx != -1) {
                meetingRooms[idx] = meeting[1];
                count++;
            }
        }
        // 전체 진행한 회의들의 수 출력
        System.out.println(count);
    }
}