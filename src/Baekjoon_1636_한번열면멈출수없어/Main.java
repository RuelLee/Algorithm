/*
 Author : Ruel
 Problem : Baekjoon 1636번 한번 열면 멈출 수 없어
 Problem address : https://www.acmicpc.net/problem/1636
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1636_한번열면멈출수없어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 프링글스가 주어지며
        // 각 프링글스마다 조절할 수 있는 스트레스의 범위가 주어지며 그 범위내에서만 조절해야한다.
        // 스트레스는 1 변경될 때마다 수명이 1 감소한다고 한다.
        // 첫 프링글스를 먹기 전의 스트레스는 마음대로 조절할 수 있다.
        // 순서대로 프링글스를 먹을 때, 줄어드는 수명의 최소값 매번 프링글스를 먹을 때의 스트레스는?
        //
        // 그리디 문제
        // 첫 스트레스 값이 중요한데, 첫번째부터 값의 교집합을 해 범위를 줄여나가다
        // 처음으로 교집합이 생기지 않을 때, 가장 인접한 교집합의 값을
        // 스트레스의 첫 값으로 지정해준다.
        // 그 후는 순서대로 프링글스를 먹으며, 스트레스가 범위 내라면 조절x
        // 범위를 벗어날 때만 최소로 스트레스를 조절해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 프링글스
        int n = Integer.parseInt(br.readLine());
        int[][] pringles = new int[n][];
        for (int i = 0; i < pringles.length; i++)
            pringles[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 스트레스 초기값
        // 첫 프링글스의 시작값
        int stress = pringles[0][0];
        // 구할 교집합의 범위
        int[] range = new int[2];
        range[0] = pringles[0][0];
        range[1] = pringles[0][1];
        for (int i = 1; i < pringles.length; i++) {
            // i번째 프링글스 값의 범위가 오른쪽으로 벗어나는 경우
            if (pringles[i][0] >= range[1]) {
                stress = range[1];
                break;
            } else if (pringles[i][1] <= range[0]) {        // 왼쪽으로 벗어나는 경우
                stress = range[0];
                break;
            } else {        // 교집합의 생기는 경우
                range[0] = Math.max(range[0], pringles[i][0]);
                range[1] = Math.min(range[1], pringles[i][1]);
            }
        }

        StringBuilder sb = new StringBuilder();
        // 첫 프링글스를 먹을 때 스트레스
        sb.append(stress).append("\n");
        int sum = 0;
        for (int i = 1; i < pringles.length; i++) {
            // i번째 프링글스의 최소 범위가 stress보다 클 때
            if (pringles[i][0] > stress) {
                sum += (pringles[i][0] - stress);
                stress = pringles[i][0];
            } else if (pringles[i][1] < stress) {       // 최대 범위가 stress보다 작을 때
                sum += (stress - pringles[i][1]);
                stress = pringles[i][1];
            }
            // 조절을 마친 후, 스트레스 값 기록
            sb.append(stress).append("\n");
        }
        // 전체 줄어든 수명과, 각 프링글스를 먹을 때의 스트레스 출력
        System.out.println(sum);
        System.out.print(sb);
    }
}