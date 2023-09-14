/*
 Author : Ruel
 Problem : Baekjoon 1911번 흙길 보수하기
 Problem address : https://www.acmicpc.net/problem/1911
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1911_흙길보수하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 웅덩이와 무한한 길이 l의 널빤지가 주어진다.
        // 모든 웅덩이를 널빤지로 덮고자할 때 필요한 최소 개수는?
        //
        // 그리디 문제
        // 순차적으로 살펴보되, 널빤지로 덮이지 않은 웅덩이가 보인다면
        // 무조건 널빤지를 사용하여 웅덩이를 덮어야한다.
        // 이전 널빤지로 커버되는 끝점을 기억하며,
        // 새로운 널빤지로 계속 웅덩이를 덮는 과정을 진행한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 웅덩이
        int n = Integer.parseInt(st.nextToken());
        // 길이 l의 널빤지
        int l = Integer.parseInt(st.nextToken());
        
        // 웅덩이들
        int[][] pools = new int[n][];
        for (int i = 0; i < pools.length; i++)
            pools[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 시작 지점에 따라 웅덩이를 정렬한다.
        Arrays.sort(pools, Comparator.comparingInt(o -> o[0]));
        
        // 이전 널빤지들로 커버되는 최대 지점
        int covered = 0;
        // 사용한 널빤지의 수
        int count = 0;
        for (int[] pool : pools) {
            // 이번 웅덩이와 이전 널빤지들로 커버되는 영역을 비교하여
            // 새로 설치해야하는 널빤지의 시작 위치를 정한다.
            int start = Math.max(covered, pool[0] - 1);
            // 이번 웅덩이의 끝지점과 시작 지점을 비교하여 필요한 널빤지의 수 계산
            int need = (pool[1] - 1 - start + l - 1) / l;
            // 개수 추가
            count += need;
            // 최대 커버되는 영역 기록
            covered = start + need * l;
        }

        // 사용한 널빤지의 수 출력.
        System.out.println(count);
    }
}