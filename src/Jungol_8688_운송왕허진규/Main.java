/*
 Author : Ruel
 Problem : Jungol 8688번 운송왕 허진규
 Problem address : https://jungol.co.kr/problem/8688
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8688_운송왕허진규;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int m, c;
    static int[] students;

    public static void main(String[] args) throws IOException {
        // n명의 학생, m대의 버스가 주어지며, 각 버스는 c명씩 학생을 태울 수 있다.
        // 각 학생이 정류장에 도착하는 시간이 주어진다.
        // m대의 버스로 n명의 학생을 모두 태우되, 출발 때까지 대기하는 학생들 중 최대 대기 시간을 최소화하고자 한다.
        // 가능한 최대 대기 시간의 최소값은?
        //
        // 이분 탐색 문제
        // t가 최대 10억으로 주어지므로 0 ~ 10억까지의 범위를 이분탐색으로 탐색한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, m대의 버스, 각 버스의 최대 탑승 인원 c
        int n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 각 학생의 도착 시간
        students = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < students.length; i++)
            students[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(students);

        // 이분 탐색
        int start = 0;
        int end = 1_000_000_000;
        while (start < end) {
            int mid = (start + end) / 2;
            // 최대 대기 시간을 mid 이하로 맞추는 것이 가능하다면
            // 범위를 start ~ end로 좁힘
            if (possible(mid))
                end = mid;
            else    // 불가능하다면 범위를 mid + 1 ~ end로 좁힘
                start = mid + 1;
        }
        // 답 출력
        System.out.println(start);
    }

    // 최대 대기 시간을 maxWait 이하로 맞추는 것이 가능한지 살펴본다.
    static boolean possible(int maxWait) {
        // 버스 수
        int bus = 1;
        // 버스에 첫 탑승한 학생의 idx
        int firstIdx = 0;
        for (int i = 1; i < students.length; i++) {
            // 버스 탑승 인원이 c명 초과가 됐거나
            // 대기 시간이 maxWait을 넘어간 경우, 다음 버스에 배정
            if (i - firstIdx + 1 > c ||
                    students[i] - students[firstIdx] > maxWait) {
                // 만약 버스 수가 m 초과가 됐다면 false 반환
                if (++bus > m)
                    return false;
                // 첫 학생의 idx 기록
                firstIdx = i;
            }
        }
        // 모두 처리하는 것이 가능했다면 true 반환
        return true;
    }
}