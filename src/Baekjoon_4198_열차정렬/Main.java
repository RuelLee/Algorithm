/*
 Author : Ruel
 Problem : Baekjoon 4198번 열차정렬
 Problem address : https://www.acmicpc.net/problem/4198
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4198_열차정렬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 열차의 차량 n개가 순서대로 주어진다
        // 해당 열차가 역에 도착할 때, 전면 혹은 후면에 차량을 추가하거나 추가하지 않으면서
        // 전면에는 무거운 차량, 후면에는 가벼운 차량으로 최대 길이 정렬하고자 한다.
        // 그 때 그 길이는?
        //
        // 최장 증가 수열 응용 문제
        // 열차를 추가하거나 추가하지 않을 수 있으므로
        // 현재 열차부터 후속으로 오늘 열차들에 대해 최장 증가 수열, 최장 감소 수열을 구해
        // 현 열차부터 앞으로 최대한 많이 추가할 수 있는 열차의 개수와
        // 뒤로 최대한 많이 추가할 수 있는 열차의 개수를 구해 답을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력으로 주어지는 열차의 개수와 그 순서
        int n = Integer.parseInt(br.readLine());
        int[] trains = new int[n];
        for (int i = 0; i < trains.length; i++)
            trains[i] = Integer.parseInt(br.readLine());

        // 최장 증가 수열을 구하기 위해 각 순서에 오는 기차의 최대 무게를 찾는다
        int[] heavierOrders = new int[n];
        // 최장 감소 수열을 구하기 위해 각 순서에 오는 기차의 최소 무게를 찾는다.
        int[] lighterOrders = new int[n];
        Arrays.fill(lighterOrders, Integer.MAX_VALUE);

        // 각 차량들에 후속으로 오는 열차들을 추가하여 만든 최장 증가 수열의 길이
        int[] ascLengths = new int[n];
        int ascMaxLength = 0;
        // 각 차량들에 후속으로 오는 열차들을 추가하여 만든 최장 감소 수열의 길이
        int[] descLengths = new int[n];
        int descMaxLength = 0;

        // 후속으로 오는 열차들에 대해 찾아야하므로
        // 가장 끝 열차부터 첫번째까지 내림차순으로 탐색한다.
        for (int i = trains.length - 1; i >= 0; i--) {
            // i번째 열차의 전면(= 더 무거운)에 몇 대의 열차들을 추가할 수 있는지 찾는다.
            int start = 0;
            int end = ascMaxLength + 1;
            while (start < end) {
                int mid = (start + end) / 2;
                if (trains[i] < heavierOrders[mid])
                    start = mid + 1;
                else
                    end = mid;
            }
            heavierOrders[start] = Math.max(heavierOrders[start], trains[i]);
            ascLengths[i] = start + 1;
            ascMaxLength = Math.max(ascMaxLength, start + 1);

            // i번째 열차의 후면(= 더 가벼운)에 몇 대의 열차들을 추가할 수 있는지 찾는다.
            start = 0;
            end = descMaxLength + 1;
            while (start < end) {
                int mid = (start + end) / 2;
                if (trains[i] > lighterOrders[mid])
                    start = mid + 1;
                else
                    end = mid;
            }
            lighterOrders[start] = Math.min(lighterOrders[start], trains[i]);
            descLengths[i] = start + 1;
            descMaxLength = Math.max(descMaxLength, start + 1);
        }

        // 각 열차들에 대해 최장 증가 수열의 길이와 최장 감소 수열의 길이를 따져보고
        // 두 합 - 1(기준 열차가 중복되므로)의 최대값을 찾는다.
        int answer = 0;
        for (int i = 0; i < n; i++)
            answer = Math.max(answer, ascLengths[i] + descLengths[i] - 1);
        // 답안 출력
        System.out.println(answer);
    }
}