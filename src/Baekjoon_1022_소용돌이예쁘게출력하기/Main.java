/*
 Author : Ruel
 Problem : Baekjoon 1022번 소용돌이 예쁘게 출력하기
 Problem address : https://www.acmicpc.net/problem/1022
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1022_소용돌이예쁘게출력하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // (0, 0)에서부터 무한하게 반시계로 회전하며 양의 정수를 채운다.
        //     -3 -2 -1  0  1  2  3
        //    --------------------
        // -3 |37 36 35 34 33 32 31
        // -2 |38 17 16 15 14 13 30
        // -1 |39 18  5  4  3 12 29
        //  0 |40 19  6  1  2 11 28
        //  1 |41 20  7  8  9 10 27
        //  2 |42 21 22 23 24 25 26
        //  3 |43 44 45 46 47 48 49
        //
        // 가장 왼쪽 가장 위 칸 (r1, c1)와 가장 오른쪽, 가장 아래 칸(r2, c2)가 주어질 때,
        // 해당 범위에 해당하는 칸들을 출력하라
        // 각 원소는 공백으로 구분하고
        // 모든 행은 같은 길이를 갖어야한다.
        // 만약 수의 길이가 가장 길이가 긴 수보다 짧다면 앞에 공백을 삽입하여 맞춘다.
        //
        // 수학, 구현..? 문제
        // 먼저 (0, 0)에서부터 ↖ ↙ ↗ ↘ 네 방향의 점들을 기준으로 삼는다.
        // ↖ 점은 첫번째 항이 5이고, 17, 37, ... 로 커져나간다.
        // 차가 12, 20, 28... 로 차가 등차수열을 이룬다.
        // 따라서 일반항을 구하면 n * (8 + 8 * (n - 1)) / 2 + 1로 정의할 수 있다.
        // 이렇게 네 방향으로 뻗어나가는 점들의 일반항을 구하고 인근의 수를 기준점이 된 수에 + 혹은 -를 해 수를 찾는다.
        // 또한 이렇게 수들을 먼저 전부 구한 후
        // 최대 길이를 갖는 수를 찾고, 해당 수보다 짧은 길이의 수들에게는 해당 길이만큼 공백을 추가해준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // (r1, c1) ~ (r2, c2)까지의 범위
        int r1 = Integer.parseInt(st.nextToken());
        int c1 = Integer.parseInt(st.nextToken());
        int r2 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());

        // 해당 범위를 배열로 표현
        int[][] table = new int[Math.abs(r2 - r1) + 1][Math.abs(c2 - c1) + 1];
        int maxLength = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                // 해당 하는 수를 넣고
                table[i][j] = getNum(i + r1, j + c1);
                // 해당 수가 현재 등장한 수들 중 최대 길이를 갱신하는지 확인
                maxLength = Math.max(maxLength, numLength(table[i][j]));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                // (i, j)의 수가 최대 길이의 수보다 더 짧다면 해당 길이만큼 공백 추가
                for (int k = 0; k < maxLength - numLength(table[i][j]); k++)
                    sb.append(" ");
                // 수 기록
                sb.append(table[i][j]).append(" ");
            }
            // 마지막 공백 제거 후, 줄바꿈
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // 수의 길이를 반환
    static int numLength(int num) {
        int length = 0;
        while (num > 0) {
            length++;
            num /= 10;
        }
        return length;
    }

    // r, c의 값을 찾는다.
    static int getNum(int r, int c) {
        // (0, 0)부터 몇번째 외곽인지 센다.
        int level = Math.max(Math.abs(r), Math.abs(c));
        int criteriaNum = 1;
        // ↖ 방향인 경우
        if (r <= 0 && c <= 0) {
            // 해당 기준 점
            criteriaNum += level * (8 + 8 * (level - 1)) / 2;
            // 기준점보다 아랫쪽인 경우
            if (level > -r)
                criteriaNum += (level + r);
            else        // 기준점보다 오른쪽인 겨우
                criteriaNum -= (level + c);
        } else if (r > 0 && c <= 0) {       // ↙ 방향
            criteriaNum += level * (12 + 8 * (level - 1)) / 2;
            // 기준점보다 위쪽인 경우
            if (r < level)
                criteriaNum -= (level - r);
            else        // 기준점보다 오른쪽인 경우
                criteriaNum += (level + c);
        } else if (r > 0 && c > 0) {        // ↘ 방향
            if (r < level) {
                // 기준점보다 위쪽인 경우
                // 조금 특이한데 기준점보다 위인 곳부터 해당 외곽이 시작이기 때문.
                // 전 외곽의 마지막 수 +1로 기준을 삼은 후 값 보정
                criteriaNum += (level - 1) * (16 + 8 * (level - 2)) / 2 + 1;
                criteriaNum += (level - r - 1);
            } else {
                // 왼쪽인 경우
                criteriaNum += level * (16 + 8 * (level - 1)) / 2;
                criteriaNum -= (level - c);
            }
        } else {        // ↗ 방향
            criteriaNum += level * (4 + 8 * (level - 1)) / 2;
            // 기준점보다 왼쪽인 경우
            if (c < level)
                criteriaNum += (level - c);
            else        // 아랫쪽인 경우
                criteriaNum -= (level + r);
        }
        return criteriaNum;
    }

}