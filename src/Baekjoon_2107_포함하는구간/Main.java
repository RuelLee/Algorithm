/*
 Author : Ruel
 Problem : Baekjoon 2107번 포함하는 구간
 Problem address : https://www.acmicpc.net/problem/2107
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2107_포함하는구간;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수직선 상에 n개의 구간이 있다.
        // 이 때 다른 구간을 최대로 포함하는 구간이 포함하는 다른 구간의 개수를 출력하라
        //
        // 정렬, 브루트포스 문제
        // 먼저 구간들을 시작점 기준으로 정렬한다.
        // 그 후, i번째의 끝점보다 시작점이 작은 모든 구간에 대해
        // 끝점이 i번째 끝점보다 작은지를 체크하여 개수를 센다.
        // 그 개수가 i번째 구간이 포함하는 구간의 개수이다.
        // 모든 구간에 대해 위 계산을 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 구간
        int n = Integer.parseInt(br.readLine());
        int[][] lines = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < lines.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < lines[i].length; j++)
                lines[i][j] = Integer.parseInt(st.nextToken());
        }
        // 정렬
        Arrays.sort(lines, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        int max = 0;
        for (int i = 0; i < lines.length; i++) {
        // i번째 구간이 포함하는 구간의 개수
            int cnt = 0;
            for (int j = i + 1; j < lines.length && lines[j][0] < lines[i][1]; j++) {
                if (lines[j][1] < lines[i][1])
                    cnt++;
            }
            max = Math.max(max, cnt);
        }
        // 답 출력
        System.out.println(max);
    }
}