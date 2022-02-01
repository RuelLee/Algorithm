/*
 Author : Ruel
 Problem : Baekjoon 2110번 공유기 설치
 Problem address : https://www.acmicpc.net/problem/2110
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 공유기설치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] houses;

    public static void main(String[] args) throws IOException {
        // 집 n개와 c개의 공유기가 주어진다
        // c개의 공유기를 n개의 집에 설치하되, 공유기 간의 최소거리가 최대가 되도록 하고 싶다.
        // 이 때 최소 거리를 구하라.
        // 집의 좌표가 10억까지 매우 크게 주어지므로 -> 이분탐색을 통해 푼다
        // c개의 공유기가 주어지면, 이 때 모든 공유기를 일정한 거리로 배치(최소거리를 이론상 최대로 할 수 있는 값)할 경우
        // 첫번째 집부터, 마지막 집까지의 거리 / (c - 1)값이 된다
        // 이 값을 최대값으로 두고, 최소거리는 1로 두고, 이분탐색을 해보자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        houses = new int[n];
        for (int i = 0; i < houses.length; i++)
            houses[i] = Integer.parseInt(br.readLine());
        Arrays.sort(houses);

        int start = 1;
        // 인접 공유기 간 거리들이 같으면서 최대가 되는 값.
        int end = (houses[0] + houses[n - 1]) / (c - 1);
        while (start <= end) {
            int mid = (start + end) / 2;
            if (canPutRouter(mid, c))       // 라우터 설치가 가능하다면, 더 큰 값에 대해 시도해본다.
                start = mid + 1;
            else        // 불가능하다면 더 적은 값에 대해 시도해본다.
                end = mid - 1;
        }
        // start와 end가 같아지는 지점에도 시도를 해 결국 start > end 값으로 변하게 될 것이다.
        // start 값은 가능한지 안하지 알 수 없지만,
        // start > end가 되는 시점에서의 end값은 무조건 가능하다.
        // 따라서 end값이 정답.
        System.out.println(end);
    }

    static boolean canPutRouter(int distance, int num) {
        int preLoc = houses[0];     // 첫번째 집에 무조건 공유기 설치.
        num--;      // 개수 감소
        for (int i = 1; i < houses.length; i++) {
            if (houses[i] - preLoc >= distance) {       // i번째 집이 distance 이상일 경우
                preLoc = houses[i];     // i번재 집에 공유기 설치.
                num--;      // 개수 감소.
                if (num == 0)       // 공유기 설치가 끝났다면 종료.
                    break;
            }
        }
        // 남은 공유기가 없다면 설치 가능. 그렇지 않다면 불가능.
        return num == 0;
    }
}