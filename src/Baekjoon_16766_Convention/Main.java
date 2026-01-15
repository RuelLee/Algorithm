/*
 Author : Ruel
 Problem : Baekjoon 16766번 Convention
 Problem address : https://www.acmicpc.net/problem/16766
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16766_Convention;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int m, c;
    static int[] times;

    public static void main(String[] args) throws IOException {
        // n마리의 소가 주어진 시각에 버스 정류장에 도착한다.
        // m대의 버스가 주어지고, 각 버스에는 최대 c마리의 소가 탈 수 있다.
        // 가장 많이 기다리는 소의 대기 시간을 최소화하고자 할 때
        // 해당 대기 시간은?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해, 해당 대기 시간 내에 모든 소를 m대의 버스에 다 태워보낼 수 있는지 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 소, m대의 버스, 한 대에 탈 수 있는 소의 수 c
        int n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 각 소의 도착 시각
        times = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            times[i] = Integer.parseInt(st.nextToken());
        // 오름차순 정렬
        Arrays.sort(times);

        // 만약 소의 수와 버스의 수가 같다면 대기하지 않아도 됨
        int start = 0;
        // 한 대에 모두 타는 경우의 최대 대기 시간
        int end = times[n - 1] - times[0];
        // 이분탐색으로 대기 시간을 탐색
        while (start < end) {
            int mid = (start + end) / 2;
            // 가장 많이 기다리는 소가 mid 시간 내가 가능하다면
            // end를 mid로 줄이고
            if (possible(mid))
                end = mid;
            else        // 불가능하다면 start를 mid로 줄임
                start = mid + 1;
        }
        // 최종적으로 얻은 답 출력
        System.out.println(start);
    }

    // waitingTime의 대기 시간 내에 모든 소가 버스를 탈 수 있는지 계산
    static boolean possible(int waitingTime) {
        int usedBus = 0;
        // 아직 버스를 타지 못한 소부터
        for (int i = 0; i < times.length; ) {
            int j = i + 1;
            // 최대 c마리, i번 소의 대기 시간 waitingTime 내까지의 소를 태움
            while (j < i + c && j < times.length && times[j] - times[i] <= waitingTime)
                j++;
            // 해당 버스 출발
            usedBus++;
            // 다음 버스의 첫 탑승 소는 j
            i = j;
        }
        // 사용한 버스의 수가 m이하인 경우, 가능
        return usedBus <= m;
    }
}