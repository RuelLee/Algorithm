/*
 Author : Ruel
 Problem : Baekjoon 12738번 가장 긴 증가하는 부분 수열 3
 Problem address : https://www.acmicpc.net/problem/12738
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12738_가장긴증가하는부분수열3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열이 주어졌을 때
        // 가장 긴 증가하는 부분 수열을 찾는 문제
        // 가령 10, 20, 10, 30, 20, 50 이 주어진다면
        //      10, 20,     30,     50 으로 길이 4인 수열이 답.
        // 앞 순서대로 수를 살펴보면서 각 순서의 최소값을 찾아나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 주어진 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 순서의 최소값을 찾는다.
        int[] minValue = new int[n + 1];
        // 최대값으로 초기화.
        Arrays.fill(minValue, 1_000_000_001);
        // 첫번째 순서의 최소값은 첫번째 수로 시작.
        minValue[1] = nums[0];
        // 크기 1
        int maxSIze = 1;
        // 모든 수에 대해 살펴본다.
        for (int i = 1; i < nums.length; i++) {
            // 이분 매칭으로 자신과 같거나 크지만 가장 작은 수를 찾는다.
            // 범위는 첫번째 순서부터
            int start = 1;
            // 현재 찾아진 가장 긴 길이보다 하나 큰 수까지.
            // (=이번의 수가 길이를 늘리는 가장 큰 수가 될 수 있으므로)
            int end = start + maxSIze;
            while (start < end) {
                int mid = (start + end) / 2;
                if (minValue[mid] < nums[i])
                    start = mid + 1;
                else
                    end = mid;
            }
            // 찾아진 순서를 통해 가장 긴 증가하는 부분 수열의 길이가 더 길어졌는지 확인하고
            maxSIze = Math.max(maxSIze, start);
            // 해당하는 순서의 최소값인지 확인한다.
            minValue[start] = Math.min(minValue[start], nums[i]);
        }
        // 최종적으로 구한 maxSIze가 가장 긴 증가하는 부분 수열의 길이.
        System.out.println(maxSIze);
    }
}