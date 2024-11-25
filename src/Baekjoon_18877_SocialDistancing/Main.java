/*
 Author : Ruel
 Problem : Baekjoon 18877번 Social Distancing
 Problem address : https://www.acmicpc.net/problem/18877
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18877_SocialDistancing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1차원 상에 n마리의 소와 m개의 목초지가 주어진다.
        // 이곳에는 cowvid-19가 창궐하여 퍼지고 있다.
        // 목초지의 위치가 a ~ b의 위치로 주어질 때
        // 소들을 목초지 위에만 배치하되, 간격을 최대화고자한다.
        // 이 때의 최대 간격은?
        //
        // 이분 탐색
        // 이분 탐색을 통해 가능한 소들 사이의 간격을 찾는다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 소, m개의 목초지
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 목초지 위치
        long[][] grasslands = new long[m][2];
        for (int i = 0; i < grasslands.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < grasslands[i].length; j++)
                grasslands[i][j] = Long.parseLong(st.nextToken());
        }
        // 정렬
        Arrays.sort(grasslands, Comparator.comparingLong(o -> o[0]));
        
        // 이분 탐색
        long start = 1;
        long end = (grasslands[m - 1][1] + 1) / (n - 1);
        while (start <= end) {
            long mid = (start + end) / 2;
            // 소들끼리 mid 간격으로 배치하는 것이 가능하다면
            // start = mid + 1으로 범위를 줄여 탐색
            if (canPlaceCows(n, grasslands, mid))
                start = mid + 1;
            else        // 불가능하다면 end = mid - 1로 범위를 줄여 탐색
                end = mid - 1;
        }
        // 최종 end 값이 소들의 최대 간격
        // 답 출력
        System.out.println(end);
    }
    
    // 소 cows 마리를 grasslands에 distance 간격으로 배치하는 것이 가능한지 반환
    static boolean canPlaceCows(int cows, long[][] grasslands, long distance) {
        // 마지막 소의 위치
        long lastCowLoc = Long.MIN_VALUE;
        // 배치된 소의 마릿수
        int count = 0;
        for (long[] grassland : grasslands) {
            // 현재 목초지에 소를 배치하는 것이 가능한 동안
            while (lastCowLoc + distance <= grassland[1]) {
                // 마지막 소부터 distance 떨어진 거리가
                // 이번 목초지 시작 지점보다 작다면
                // 시작 지점에 다음 소를 배치한다.
                if (lastCowLoc + distance <= grassland[0])
                    lastCowLoc = grassland[0];
                else        // 그 외의 경우는 마지막 소에서 distance 만큼 떨어진 위치에 배치
                    lastCowLoc += distance;
                // 배치한 소의 마릿수 증가
                count++;
            }
        }
        // 배치할 수 있는 소의 마릿수가 cows보다 같거나 크다면
        // distance 간격으로 소를 배치하는 것이 가능
        return count >= cows;
    }
}