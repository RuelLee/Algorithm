/*
 Author : Ruel
 Problem : Jungol 4536번 딴짓을 하지 말자
 Problem address : https://jungol.co.kr/problem/4536
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4536_딴짓을하지말자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생이 운동장에서 x축 혹은 y축에 평행하도록 일정 구간을 왕복하며 달린다.
        // 서로 충돌 위험이 있는 학생들이 있는지 판별하라
        //
        // 선분 교차 문제
        // x축 혹은 y축에 평행하게만 달리므로, x축 따로, y축 따로 모아 충돌 여부를 판단해도 되지만
        // 선분의 교차 여부로 판단해도 된다.
        // 먼저, 선분이 같은 범위 내에 있는지부터 판별하고
        // 그 후, ccw를 통해 선분들이 서로를 교차하는지 판별한다.
        // (x2 - x1)(y3 - y1) - (y2 - y1)(x3 - x1)의 값이 음수인 경우, 한 선분이 다른 선분을 양분한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // n개의 선분
            int n = Integer.parseInt(br.readLine());
            long[][] lines = new long[n][4];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 4; j++)
                    lines[i][j] = Integer.parseInt(st.nextToken());

                // 앞쪽에 작은 x값을 갖는 점을 배치
                if (lines[i][0] > lines[i][2]) {
                    long temp = lines[i][0];
                    lines[i][0] = lines[i][2];
                    lines[i][2] = temp;
                    temp = lines[i][1];
                    lines[i][1] = lines[i][3];
                    lines[i][3] = temp;
                }
            }

            boolean safe = true;
            // i선분과 j선분이 교차하는지 확인
            for (int i = 0; i < n && safe; i++) {
                for (int j = i + 1; j < n && safe; j++) {
                    // 범위가 겹치며, 교차하는 경우
                    if (checkRange(i, j, lines) && isCross(i, j, lines))
                        safe = false;
                }
            }
            // 답 기록
            sb.append(safe ? "SAFE" : "DANGEROUS").append("\n");
        }
        // 출력
        System.out.print(sb);
    }

    // a선분과 b선분의 범위가 겹치는지 확인
    static boolean checkRange(int a, int b, long[][] lines) {
        long aMinX = Math.min(lines[a][0], lines[a][2]);
        long aMaxX = Math.max(lines[a][0], lines[a][2]);
        long aMinY = Math.min(lines[a][1], lines[a][3]);
        long aMaxY = Math.max(lines[a][1], lines[a][3]);

        long bMinX = Math.min(lines[b][0], lines[b][2]);
        long bMaxX = Math.max(lines[b][0], lines[b][2]);
        long bMinY = Math.min(lines[b][1], lines[b][3]);
        long bMaxY = Math.max(lines[b][1], lines[b][3]);

        // a선분의 큰 x값이 b선분의 작은 x값보다 작거나 반대인 경우
        // y값에 대해서도 판단해, 안 겹치는 경우 false 반환
        if (aMaxX < bMinX || bMaxX < aMinX ||
                aMaxY < bMinY || bMaxY < aMinY)
            return false;
        else        // 그 외의 경우 true 반환
            return true;
    }

    // ccw를 통한 교차 판정
    static boolean isCross(int ab, int cd, long[][] lines) {
        // (x2 - x1)(y3 - y1) - (y2 - y1)(x3 - x1)
        long abc = (lines[ab][2] - lines[ab][0]) * (lines[cd][1] - lines[ab][1])
                - (lines[ab][3] - lines[ab][1]) * (lines[cd][0] - lines[ab][0]);
        long abd = (lines[ab][2] - lines[ab][0]) * (lines[cd][3] - lines[ab][1])
                - (lines[ab][3] - lines[ab][1]) * (lines[cd][2] - lines[ab][0]);
        long cda = (lines[cd][2] - lines[cd][0]) * (lines[ab][1] - lines[cd][1])
                - (lines[cd][3] - lines[cd][1]) * (lines[ab][0] - lines[cd][0]);
        long cdb = (lines[cd][2] - lines[cd][0]) * (lines[ab][3] - lines[cd][1])
                - (lines[cd][3] - lines[cd][1]) * (lines[ab][2] - lines[cd][0]);

        // ab선분이 cd 선분을 양분하거나, cd선분이 ab선분을 양분하는지 판별
        return abc * abd <= 0 && cda * cdb <= 0;
    }
}