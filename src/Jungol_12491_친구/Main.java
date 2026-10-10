/*
 Author : Ruel
 Problem : Jungol 12491번 친구
 Problem address : https://jungol.co.kr/problem/12491
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12491_친구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생에 대해 수직선 위 집의 위치 x와 다니는 학교 s가 주어진다.
        // 다음 둘 중 하나를 만족할 때, 두 학생은 친구라고 한다.
        // 1. 같은 학교를 다니며 서로의 집 사이 거리가 k1 이하인 경우
        // 2. 다른 학교를 다니며 서로의 집 사이 거리가 k2 이하인 경우
        // 각 학생마다 친구의 수를 출력하라
        //
        // 두 포인터, 슬라이딩 윈도우 문제
        // 각 학생들의 입력을 받아
        // 전체 학생들에 대해 집 위치로 정렬하여 정리한다.
        // 또한 각 학교 별로 나눠서도 정리한다.
        // 다음 학교 별로 나눈 정보를 토대로 두 포인터를 활용하여,
        // 각 학생마다 집 사이의 거리가 k1 이내인 같은 학교 학생이 몇 명 존재하는지 미리 계산해둔다.
        // 그리고 나서 전체 학생들을 차례대로 살펴보며, 두 포인터를 사용하여 집 사이의 거리가 k2 이내인
        // 다른 학교 학생의 수를 구한다. 두 값을 합쳐 각 학생의 친구의 수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생. 같은 학교이며 친구이기 위한 조건 k1, 다른 학교이며 친구이기 위한 조건 k2
        int n = Integer.parseInt(st.nextToken());
        int k1 = Integer.parseInt(st.nextToken());
        int k2 = Integer.parseInt(st.nextToken());

        // 학생들의 정보
        // students[i][0] = i번 학생의 집 위치
        // students[i][1] = i번 학생의 학교
        // students[i][2] = 정렬 되기 전 원래 순서
        // students[i][3] = 같은 학교에 집 사이의 거리가 k1 이내인 학생의 수
        int[][] students = new int[n][4];
        // 각 학교 학생의 수를 셈
        int[] counts = new int[n + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            students[i][0] = Integer.parseInt(st.nextToken());
            students[i][1] = Integer.parseInt(st.nextToken());
            students[i][2] = i;
            counts[students[i][1]]++;
        }
        // 집 위치로 정렬
        Arrays.sort(students, Comparator.comparingInt(s -> s[0]));

        // 학교 별로 각 학생들의 집 위치와 전체 배열에서의 순서
        int[][][] dividedBySchool = new int[n + 1][][];
        for (int i = 1; i <= n; i++)
            dividedBySchool[i] = new int[counts[i]][2];

        // 전체 학생이 정렬되어있으므로
        // 끝에서부터 counts를 줄여나가며 집의 위치와 전체 배열에서의 순서를 담는다.
        for (int i = n - 1; i >= 0; i--) {
            dividedBySchool[students[i][1]][--counts[students[i][1]]][0] = students[i][0];
            dividedBySchool[students[i][1]][counts[students[i][1]]][1] = i;
        }
        for (int i = 1; i <= n; i++) {
            // 각 학교의 학생마다 같은 학교 내 집 사이의 거리가 k1 이내인 학생의 수를 구한다.
            int left = 0;
            int right = 0;
            for (int j = 0; j < dividedBySchool[i].length; j++) {
                while (dividedBySchool[i][j][0] - dividedBySchool[i][left][0] > k1)
                    left++;
                while (right + 1 < dividedBySchool[i].length && dividedBySchool[i][right + 1][0] - dividedBySchool[i][j][0] <= k1)
                    right++;

                students[dividedBySchool[i][j][1]][3] = right - left + 1;
            }
        }

        // 전체 학생을 한 명씩 살펴나가며
        // k2 거리 이내의 다른 학교 학생의 수를 구하고
        // k1 이내 거리의 같은 학교 학생의 수를 더해, 각 학생의 친구 수를 구한다.
        int left = 0;
        int right = 0;
        int[] answer = new int[n];
        counts[students[0][1]]++;
        for (int i = 0; i < students.length; i++) {
            // 포인터를 옮겨가며, 범위 내 각 학교의 학생 수도 구한다.
            while (students[i][0] - students[left][0] > k2)
                counts[students[left++][1]]--;
            while (right + 1 < n && students[right + 1][0] - students[i][0] <= k2)
                counts[students[++right][1]]++;

            // k2 범위 내의 전체 학생의 수 - k2 범위 내 i와 같은 학교 학생의 수 + k1 이내 범위의 같은 학교 학생의 수
            answer[students[i][2]] = right - left + 1 - counts[students[i][1]] + students[i][3] - 1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(answer[0]);
        for (int i = 1; i < n; i++)
            sb.append(' ').append(answer[i]);
        // 답 출력
        System.out.println(sb);
    }
}