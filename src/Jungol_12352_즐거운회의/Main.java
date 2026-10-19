/*
 Author : Ruel
 Problem : Jungol 12352번 즐거운 회의
 Problem address : https://jungol.co.kr/problem/12352
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12352_즐거운회의;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람과 m개의 친구 관계가 주어진다.
        // 0 ~ t까지 회의를 진행하는데, 각 시각마다 친구 관계 쌍의 개수를 출력하라
        //
        // 차분 배열 문제
        // 정렬과 우선순위큐로 풀려했으나 시간 초과가 났다.
        // 중요한 건, 친구 관계가 어느 시점부터 어느 시점까지 영향을 끼치느냐이다.
        // c와 d의 친구 관계가 주어질 때, 두 친구가 동시에 회의에 출석하는 구간에 쌍의 개수가 1 증가한다.
        // 차분 배열을 통해 처리하면 한 번에 처리할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 인원, m개의 관계, 회의 진행 시간 t
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 각 인원의 출퇴장 시각
        int[][] comeAndLeaves = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            comeAndLeaves[i][0] = Integer.parseInt(st.nextToken());
            comeAndLeaves[i][1] = Integer.parseInt(st.nextToken());
        }

        // 누적합, 차분 배열
        int[] psums = new int[t + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 친구 관계
            int c = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;

            // 서로 마주치지 않는다면 건너뛴다.
            if (comeAndLeaves[c][1] <= comeAndLeaves[d][0] ||
                    comeAndLeaves[d][1] <= comeAndLeaves[c][0])
                continue;

            // 마주친다면, 둘이 만나는 시각과 헤어지는 시각을 구해
            int start = Math.max(comeAndLeaves[c][0], comeAndLeaves[d][0]);
            int end = Math.min(comeAndLeaves[c][1], comeAndLeaves[d][1]);
            // 배열에 표시
            psums[start]++;
            psums[end]--;
        }

        // 누적처리하며 답을 구한다.
        StringBuilder sb = new StringBuilder();
        sb.append(psums[0]).append("\n");
        for (int i = 1; i < t; i++)
            sb.append(psums[i] += psums[i - 1]).append("\n");
        // 출력
        System.out.print(sb);
    }
}