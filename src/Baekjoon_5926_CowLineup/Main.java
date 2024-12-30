/*
 Author : Ruel
 Problem : Baekjoon 5926번 Cow Lineup
 Problem address : https://www.acmicpc.net/problem/5926
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5926_CowLineup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소들이 있다.
        // 각 소들의 놓여있는 x 위치와 품종이 주어진다.
        // 한 구간을 선택하여 사진을 찍는데, 모든 품종이 한 사진에 담기도록 찍고 싶다.
        // 사진이 담아야하는 최소 너비는 얼마인가?
        //
        // 두 포인터 문제
        // 소들을 위치에 따라 정렬한 후
        // 두 포인터를 사용하여, 모든 품종의 소를 담기 위한 범위를 찾아나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;

        // 소들의 위치와 품종
        int[][] cows = new int[n][2];
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cows[i].length; j++)
                cows[i][j] = Integer.parseInt(st.nextToken());
            counts.put(cows[i][1], 0);
        }
        Arrays.sort(cows, Comparator.comparing(o -> o[0]));

        // 현재 범위 내의 품종 수
        int kinds = 0;
        // 끝 범위
        int idx = -1;
        // 사진이 담아야하는 최소 너비
        int answer = Integer.MAX_VALUE;
        // 시작 포인터
        // i ~ idx 까지로 모든 소의 품종을 담고자한다.
        for (int i = 0; i < cows.length; i++) {
            // 모든 품종이 담길 때까지 포인터를 뒤로 민다.
            while (idx + 1 < cows.length && kinds < counts.size()) {
                idx++;
                // 새로운 품종이라면 kinds 증가
                if (counts.get(cows[idx][1]) == 0)
                    kinds++;
                counts.put(cows[idx][1], counts.get(cows[idx][1]) + 1);
            }
            // 만약 모든 품종을 담는데 실패했다면
            // 더 이상은 불가능한 경우이므로 종료한다.
            if (kinds < counts.size())
                break;
            
            // i ~ idx까지 모든 품종을 담았으므로
            // 해당 너비를 answer에 기록
            answer = Math.min(answer, cows[idx][0] - cows[i][0]);
            // i+1번째로 넘어가야하므로, i번째 소를 제외하는 작업을 해준다.
            counts.put(cows[i][1], counts.get(cows[i][1]) - 1);
            // 만약 i번째 소를 제외함으로써 품종이 하나 줄었는지 확인.
            if (counts.get(cows[i][1]) == 0)
                kinds--;
        }
        // 답 출력
        System.out.println(answer);
    }
}