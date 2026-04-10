/*
 Author : Ruel
 Problem : Baekjoon 9082번 지뢰찾기
 Problem address : https://www.acmicpc.net/problem/9082
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9082_지뢰찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2 * n 크기의 배열로 지뢰의 정보가 주어진다.
        // 첫번째 줄에는 자신을 포함한 좌우 칸에 매설된 지뢰의 수를 나타낸다.
        // 두번째 줄에는 아직 모르는 위치는 #, 지뢰가 확실히 매설된 위치는 *으로 주어진다.
        // 가능한 최대 지뢰의 개수를 구하라
        //
        // 그리디 문제
        // 앞에서부터 살펴보며 첫번째 입력을 보며
        // 현재 칸과 좌우칸을 살펴보며 모두 값이 1이상인 경우
        // 해당 칸의 지뢰를 확정시키며 진행하나간다.
        // 해당 방법을 사용하면, 정확한 지뢰의 위치는 알 수 없지만, 가능한 최대 지뢰의 개수는 알 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            int n = Integer.parseInt(br.readLine());
            // 인근 지뢰 매설 수
            String input = br.readLine();
            int[] mines = new int[n];
            for (int i = 0; i < mines.length; i++)
                mines[i] = input.charAt(i) - '0';
            br.readLine();

            // 가능한 지뢰의 개수
            int cnt = 0;
            for (int i = 0; i < mines.length; i++) {
                // 인근 칸이 모두 가능한지 살펴본다.
                boolean allPossible = true;
                for (int j = i - 1; j <= i + 1; j++) {
                    if (j >= 0 && j < mines.length && mines[j] == 0) {
                        allPossible = false;
                        break;
                    }
                }

                // 현재 칸과 인근 칸 모두 1이상이라면
                // 현재 칸에 지뢰를 확정하고 각각 카운터를 1씩 감소
                if (allPossible) {
                    cnt++;
                    for (int j = i - 1; j <= i + 1; j++) {
                        if (j >= 0 && j < mines.length)
                            mines[j]--;
                    }
                }
            }
            // 최대 지뢰의 개수 기록
            sb.append(cnt).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}