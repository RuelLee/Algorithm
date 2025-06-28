/*
 Author : Ruel
 Problem : Baekjoon 2911번 전화 복구
 Problem address : https://www.acmicpc.net/problem/2911
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2911_전화복구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 동서 일직선 상에 m개의 집이 있다.
        // 감지기는 두 집 사이에 설치할 수 있고, 설치된 위치보다 동쪽에서, 서쪽에 있는 집에 대해
        // 서로 통화하는 것을 감지할 수 있다고 한다.
        // m개의 감지기 위치가 주어지고, 감지한 통화의 수가 주어진다고 했을 때
        // 전체 마을에서 발생한 통화가 적어도 몇 통 있었는지 계산하라
        //
        // 그리디, 정렬 문제
        // 먼저 감지기기 위치를 오름차순에 따라 정렬한다.
        // 그 후, 순서대로 살펴가며
        // 해당 감지기에 감지된 통화가 서쪽 끝으로 통화한다고 가정하고 계산해나간다.
        // 현재까지의 통화보다 같거나 더 많은 수의 통화가 감지된다면, 해당 지점부터 끝까지의 통화가 더 추가된 것으로 보고
        // 더 적은 수의 통화가 감지된다면, 차이만큼 해당 전화가 해당 지점에서 끝난 것으로 생각한다.
        // 그렇게 해당 지점에서 끝난 통화들과, 마지막 지점까지 가져간 통화들의 합을 구해 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 감지기, m개의 집
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 감지기의 위치와 감지한 통화의 수
        int[][] detectors = new int[n][2];
        for (int i = 0; i < detectors.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < detectors[i].length; j++)
                detectors[i][j] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(detectors, Comparator.comparingInt(o -> o[0]));
        
        // 총 통화
        long sum = 0;
        // 현재 지점까지 이어져있는 통화
        long call = 0;
        for (int i = 0; i < detectors.length; i++) {
            // 더 많은 통화가 현 감지기에서 나타난다면
            // 해당 수만큼의 통화가 추가된 것으로 생각
            if (detectors[i][1] >= call)
                call = detectors[i][1];
            else {
                // 적은 수의 통화라면 해당 수 만큼의 통화가
                // 현재 지점 이전에 끊어진 것으로 생각
                sum += (call - detectors[i][1]);
                // 그리고 차이를 뺀 만큼이 끝까지 이어지는 통화들로 생각
                call = detectors[i][1];
            }
        }
        // 각 지점에서 끝난 통화들과 끝까지 이어진 통화들의 합
        sum += call;
        // 전체 마을에서 발생한 최소 통화의 수
        System.out.println(sum);
    }
}