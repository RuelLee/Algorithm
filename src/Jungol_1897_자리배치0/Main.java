/*
 Author : Ruel
 Problem : Jungol 1897번 자리배치0
 Problem address : https://jungol.co.kr/problem/1897
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1897_자리배치0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int c;
    static int[] desks;

    public static void main(String[] args) throws IOException {
        // 일렬로 늘어선 n개의 책상과 놓인 위치가 주어진다.
        // 이 책상들에 c명의 학생을 배치하되, 가장 가까운 한 쌍의 학생의 거리가 제일 멀게끔하고자 한다.
        // 최소 거리의 최대값은?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해 gap 이상의 거리로 모든 학생들을 배치할 수 있는가를 계속 질문으로 던지며 값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 책상과 c명의 학생
        int n = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 책상들
        desks = new int[n];
        for (int i = 0; i < n; i++)
            desks[i] = Integer.parseInt(br.readLine().trim());
        // 정렬
        Arrays.sort(desks);

        // 이분 탐색
        int start = 1;
        int end = desks[n - 1];
        while (start <= end) {
            int mid = (start + end) / 2;
            // 최소 거리를 mid 이상으로 만들 수 있는가?
            if (possible(mid))
                start = mid + 1;
            else
                end = mid - 1;
        }
        // 답 출력
        System.out.println(end);
    }

    // gap 이상으로 모든 학생들의 간격을 맞출 수 있는지 확인한다.
    static boolean possible(int gap) {
        // 첫 학생은 가장 앞 책상 고정
        int cnt = 1;
        int last = 0;
        // 책상들을 살펴보며, 이전 학생보다 gap 이상의 떨어진 책상에 배치
        for (int i = 1; i < desks.length && cnt < c; i++) {
            if (desks[i] - desks[last] < gap)
                continue;

            last = i;
            cnt++;
        }
        // 가능한지 여부 반환
        return cnt == c;
    }
}