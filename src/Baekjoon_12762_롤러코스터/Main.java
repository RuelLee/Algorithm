/*
 Author : Ruel
 Problem : Baekjoon 12762번 롤러코스터
 Problem address : https://www.acmicpc.net/problem/12762
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12762_롤러코스터;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 롤러코스터의 기둥들이 주어진다.
        // 기둥들 몇 개를 제거하여, 내리막을 내려왔다가 다시 올라가는 구간을 최대한 길게 만들고자한다.
        // 살릴 수 있는 기둥의 최대 개수는 몇 개인가?
        //
        // 최장 증가 수열 문제
        // 첫번째 기둥에서부터 시작하여 내림차순을 가장 긴 부분 수열을 구하고
        // 마지막 기둥부터 시작하여 내림차순으로 가장 긴 부분 수열을 구하여
        // 두 수열의 길이를 더해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 기동
        int n = Integer.parseInt(br.readLine());
        int[] pillars = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 첫번째 기둥으로부터 시작하여
        // 내림차순으로 가장 긴 부분 수열을 찾는다.
        // 그 때 해당 기둥의 순서.
        int[] desc = new int[n];
        // n번째로 올 수 있는 가장 큰 기둥의 길이.
        int[] orders = new int[n];
        orders[0] = pillars[0];
        for (int i = 1; i < pillars.length; i++) {
            int start = 0;
            int end = n - 1;
            while (start < end) {
                int mid = (start + end) / 2;
                if (orders[mid] > pillars[i])
                    start = mid + 1;
                else
                    end = mid;
            }
            orders[start] = Math.max(orders[start], pillars[i]);
            desc[i] = start;
        }

        // 마지막 기둥으로부터 시작하여
        // 내림차순으로 최장 증가 수열의 길이를 구한다.
        int[] asc = new int[n];
        Arrays.fill(orders, 0);
        orders[0] = pillars[n - 1];
        for (int i = n - 1; i >= 0; i--) {
            int start = 0;
            int end = n - 1;
            while (start < end) {
                int mid = (start + end) / 2;
                if (orders[mid] > pillars[i])
                    start = mid + 1;
                else
                    end = mid;
            }
            orders[start] = Math.max(orders[start], pillars[i]);
            asc[i] = start;
        }

        int max = 0;
        // desc[i] = 첫 기둥으로부터 내림차순으로 최장 증가 수열을 구했을 때
        // i번째 기둥이 올 수 있는 가장 큰 순서
        // asc[i] = 마지막 기둥으로부터 내림차순으로 최장 증가 수열을 구했을 때
        // i번째 기둥이 올 수 있는 가장 큰 순서
        // 두 순서를 더한다.
        // 0번째 부터 시작하므로 +1 값 보정
        for (int i = 0; i < desc.length; i++)
            max = Math.max(max, desc[i] + asc[i] + 1);

        // 살릴 수 있는 최다 기둥의 수 출력
        System.out.println(max);
    }
}