/*
 Author : Ruel
 Problem : Baekjoon 3013번 부분 수열의 중앙값
 Problem address : https://www.acmicpc.net/problem/3013
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3013_부분수열의중앙값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 자연수 n개로 이루어진 수열이 주어진다.
        // 이 때 연속한 부분 수열 중 중앙값이 b인 부분수열의 개수는?
        //
        // 누적합 문제
        // 각 수가 한번씩만 등장하므로
        // b의 위치를 찾고 해당 위치로부터 좌우로
        // b보다 큰 수와 작은 수의 개수를 구한다.
        // 그 후, 좌우를 비교하여, 작은 수와 큰 수의 개수가
        // 범위들을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 자연수
        int n = Integer.parseInt(st.nextToken());
        // 원하는 중앙값 b
        int b = Integer.parseInt(st.nextToken());

        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // b의 위치를 찾는다.
        int findIdx = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == b) {
                findIdx = i;
                break;
            }
        }

        // 누적합
        int[] psum = new int[n];
        // 왼쪽 부분
        HashMap<Integer, Integer> left = new HashMap<>();
        // 오른쪽 부분
        HashMap<Integer, Integer> right = new HashMap<>();
        // b는 오른쪽 부분에 포함시킨다.
        right.put(0, 1);

        // 왼쪽 부분 탐색
        // b의 위치로부터 왼쪽으로 탐색하며,
        // b보다 큰 수일 경우 +1, 작은 수일 경우 -1을 하며 누적합을 구한다.
        for (int i = findIdx - 1; i >= 0; i--) {
            psum[i] = psum[i + 1] + (nums[i] > b ? 1 : -1);
            if (!left.containsKey(psum[i]))
                left.put(psum[i], 1);
            else
                left.put(psum[i], left.get(psum[i]) + 1);
        }

        // 마찬가지로 오른쪽 부분에 대해서도 구한다.
        for (int i = findIdx + 1; i < nums.length; i++) {
            psum[i] = psum[i - 1] + (nums[i] > b ? 1 : -1);
            if (!right.containsKey(psum[i]))
                right.put(psum[i], 1);
            else
                right.put(psum[i], right.get(psum[i]) + 1);
        }

        // 중앙값이 0인 부분 수열의 개수.
        // 길이가 1인 b만 포함하는 부분 수열이 right에 포함되어있으므로
        // right에서 0인 개수를 세어 초기값으로 설정한다.
        int count = right.get(0);
        // 왼쪽에 등장하는 누적합의 값을 차례대로 살펴보며
        // 해당 값과 더해 0이 되는 값이 오른쪽에 존재하는지 살펴보고
        // 그러하다면 나올 수 있는 경우의 수를 구해 더한다.
        for (int leftSum : left.keySet()) {
            if (right.containsKey(leftSum * -1))
                count += (left.get(leftSum) * right.get(leftSum * -1));
        }
        
        // 전체 답안 출력
        System.out.println(count);
    }
}