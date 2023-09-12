/*
 Author : Ruel
 Problem : Baekjoon 25635번 자유 이용권
 Problem address : https://www.acmicpc.net/problem/25635
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25635_자유이용권;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 놀이기구가 주어진다.
        // 그리고 각 놀이기구를 탈 수 있는 횟수가 정해져있으며
        // 같은 놀이기구를 연속해서 타지는 못한다.
        // 최대한 많이 놀이기구를 타고자할 때 그 횟수는?
        //
        // 딱히... 알고리즘 분류가 있나? 싶은 문제
        // 가장 횟수가 많은 놀이기구를 다른 놀이기구들 
        // 탑승 사이 사이에 끼어넣는다는 느낌으로 푸는 문제
        // 먼저 가장 횟수가 많은 놀이기구의 탑승 횟수 max와
        // 나머지 놀이기구의 탑승 횟수의 합 sum을 구한다.
        // max보다 sum + 1이 같거나 더 크다면 모든 놀이기구의 탑승 횟수를 소진하여 사용할 수 있다.
        // 그렇지 않은 경우에는 max를 모두 소진할 수 없고, sum + 1에 해당하는 만큼 소진할 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 전체 합
        long sum = 0;
        // 가장 큰 탑승 횟수
        int max = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            max = Math.max(max, num);
            sum += num;
        }
        // max를 제외한 전체 탑승 횟수
        sum -= max;

        // max가 sum + 1보다 같거나 작다면
        // max의 모든 탑승 기회를 소진하는 것이 가능.
        // 따라서 가능한 전체 탑승 횟수는 max + sum
        // 그렇지 않다면
        // max 놀이기구를 이용할 수 있는 횟수는 sum + 1회
        // 전체 탑승 횟수는 sum + 1 + sum
        System.out.println(max <= sum + 1 ? (max + sum) : (sum * 2 + 1));
    }
}