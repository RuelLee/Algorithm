/*
 Author : Ruel
 Problem : Baekjoon 1756번 피자 굽기
 Problem address : https://www.acmicpc.net/problem/1756
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1756_피자굽기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 오븐의 크기는 일정하지 않으며, 깊이에 따른 너비가 주어진다.
        // 피자 반죽의 크기가 주어진다.
        // 피자 반죽을 순서대로 오븐에 넣을 때
        // 맨 위의 피자 반죽이 얼마나 깊이 들어있는지 계산하라
        //
        // 이분탐색 문제
        // 오븐을 통해 피자 반죽을 넣을 때
        // 반죽 사이즈보다 좁은 오븐 위치를 지나갈 수 없다.
        // 따라서 오븐의 사이즈를 입력 받고, 통과할 수 있는 사이즈의 반죽의 크기로만 다시 오븐 사이즈를 재계산한다.
        // 그 후, 이분탐색을 통해 해당 반죽이 들어갈 수 있는 가장 깊은 곳의 위치를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 오븐의 깊이 d, 피자 반죽의 개수 n
        int d = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 오븐의 사이즈
        int[] oven = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 통과할 수 있는 반죽의 크기로 재계산한다.
        // 증가하지 않는 수열이 된다.
        for (int i = 1; i < oven.length; i++)
            oven[i] = Math.min(oven[i], oven[i - 1]);
        // 피자 반죽
        int[] pizzas = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 현재 사용할 수 있는 최대 깊이
        int maxDepth = oven.length - 1;
        boolean possible = true;
        // 순서대로 피자를 넣는다.
        for (int pizza : pizzas) {
            // 피자를 넣을 수 있는 가장 깊은 깊이
            maxDepth = findIdx(pizza, maxDepth, oven) - 1;
            // 만약 그 깊이가 -1보다 작다면 불가능한 경우.
            if (maxDepth < -1) {
                possible = false;
                break;
            }
        }
        // maxDepth은 현재 사용할 수 있는 가장 깊은 빈 위치이다.
        // 문제에서 물어보는 것은 가장 위의 피자가 놓인 위치이므로
        // 값을 보정하여 출력한다.
        // 불가능할 경우는 0을 출력.
        System.out.println(possible ? maxDepth + 2 : 0);
    }

    // 이분 탐색을 통해 피자가 놓일 수 있는 최대 깊이를 찾는다.
    static int findIdx(int size, int end, int[] oven) {
        int start = 0;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (size <= oven[mid])
                start = mid + 1;
            else
                end = mid - 1;
        }
        return end;
    }
}