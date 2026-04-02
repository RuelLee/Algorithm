/*
 Author : Ruel
 Problem : Baekjoon 1089번 스타트링크 타워
 Problem address : https://www.acmicpc.net/problem/1089
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1089_스타트링크타워;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 10^n - 1층이 디지털 번호로 적혀있다.
        // 각 숫자마다 5 * 3개의 칸이 필요하며, 각 숫자는 하나의 불 꺼진 열로 구분된다.
        // 다음은 0 ~ 9까지의 수를 적은 형태이다.
        // ###...#.###.###.#.#.###.###.###.###.###
        // #.#...#...#...#.#.#.#...#.....#.#.#.#.#
        // #.#...#.###.###.###.###.###...#.###.###
        // #.#...#.#.....#...#...#.#.#...#.#.#...#
        // ###...#.###.###...#.###.###...#.###.###
        // n개의 숫자로 이루어진 번호판 상태가 주어진다.
        // 해당 전구들 몇 개가 고장나 있다고 한다.
        // 가능한 층수의 평균을 구하라
        //
        // 비트마스킹, 확률
        // 몇 개의 전구가 꺼져있는데 원래 형태를 추정하기 위해서는
        // 비트마스킹을 통해 현재 전구의 상태와 수의 상태를 or 연산했을 때, 다시 수가 나오는지 여부를 보면 된다.
        // 전체 층의 평균을 원하므로, 각 자리에서의 평균을 구해 더해주면 된다.

        // 주어진 숫자 포맷
        char[][] nums = new char[5][];
        nums[0] = "###...#.###.###.#.#.###.###.###.###.###".toCharArray();
        nums[1] = "#.#...#...#...#.#.#.#...#.....#.#.#.#.#".toCharArray();
        nums[2] = "#.#...#.###.###.###.###.###...#.###.###".toCharArray();
        nums[3] = "#.#...#.#.....#...#...#.#.#...#.#.#...#".toCharArray();
        nums[4] = "###...#.###.###...#.###.###...#.###.###".toCharArray();

        // 을 통해 각 자리의 수를 bitmask화 하여 저장
        int[] bitmasks = new int[10];
        for (int i = 0; i < 10; i++)
            bitmasks[i] = toBitmask(4 * i, 4 * i + 2, nums);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        // 입력
        char[][] input = new char[5][];
        for (int i = 0; i < 5; i++)
            input[i] = br.readLine().toCharArray();

        double answer = 0;
        // 각 자리가 하나 늘어날 때마다 값은 10배 증가
        int pow = 1;
        // 전구 상태가 불가능한 경우가 있는지 판별
        boolean possible = true;
        // 작은 자리인 뒤에서부터 살펴본다.
        for (int i = n - 1; i >= 0 && possible; i--) {
            // 현재 수를 비트마스크화
            int curBit = toBitmask(4 * i, 4 * i + 2, input);
            // 가능한 수들의 합
            double sum = 0;
            // 개수
            int cnt = 0;
            // 가능한 수들을 더한다.
            for (int j = 0; j < bitmasks.length; j++) {
                if ((curBit | bitmasks[j]) == bitmasks[j]) {
                    sum += j;
                    cnt++;
                }
            }
            // 만약 가능한 수가 없다면 전구 상태가 잘못된 것
            // possible에 false를 주고 반복문 종료
            if (cnt == 0) {
                possible = false;
                continue;
            }

            // 그 외의 경우에는 answer에 값을 누적
            answer += sum / cnt * pow;
            pow *= 10;
        }

        // possible이 false인 경우는 -1을 출력
        // 그 외의 경우 구한 평균을 출력
        System.out.println(possible ? answer : -1);
    }

    // 주어진 char 배열에서 start ~ end 열을 살펴보며
    // bitmask 값을 구함.
    static int toBitmask(int start, int end, char[][] input) {
        int bitmask = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = start; j <= end; j++) {
                if (input[i][j] == '#')
                    bitmask |= (1 << (i * 3 + j - start));
            }
        }
        return bitmask;
    }
}