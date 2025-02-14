/*
 Author : Ruel
 Problem : Baekjoon 2746번 좋은 배열 만들기
 Problem address : https://www.acmicpc.net/problem/2746
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2746_좋은배열만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 배열이 주어진다.
        // 한 원소의 합이 다른 모든 원소의 합으로 표현될 경우, 좋은 배열이라고 한다.
        // 정확히 2개의 원소를 제거하여 좋은 배열이 되는 경우의 수를 구하라
        //
        // 정렬, 조건이 많은 분기
        // 정말 조건이 많아 여러가지를 고려하면 머리가 아파오는 문제
        // 코드마다 조건에 대해 설명하겠다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기 n의 배열
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        long[] array = new long[n];
        // 배열 원소들의 합
        long totalSum = 0;
        for (int i = 0; i < array.length; i++)
            totalSum += (array[i] = Integer.parseInt(st.nextToken()));
        // 정렬
        Arrays.sort(array);

        // 가장 큰 값의 개수를 센다.
        int right = n - 1;
        long count = 0;
        while (right > 0 && array[right] == array[right - 1])
            right--;
        
        // 4개 이상일 경우는 어떻게 하더라도 좋은 배열을 만들 수 없다.
        // 가장 큰 값이 3개인 경우
        if (n - right == 3) {
            // 가장 큰 값 2개를 지운 후, 가장 큰 값 하나가
            // 나머지들의 합과 같은 경우.
            // 이 때, 가장 큰 값 어느 것을 지워도 되므로, 가능하다면 해당 경우의 수는 3개
            if (totalSum == array[right] * 4)
                count = 3;
        } else if (n - right == 2) {        // 가장 큰 값이 2개인 경우
            // 가장 큰 값 중 하나를 지우고,
            // 다른 하나가 나머지 원소들의 합 - 임의의 원소 값이 되는 경우를 찾는다.
            // 이 때, 가장 큰 값 2개 중 어느 것을 지워도 되므로 경우의 수는 2개씩 늘어난다.
            for (int i = 0; i < right; i++) {
                if (totalSum - array[i] == array[right] * 3)
                    count += 2;
            }
            // 가장 큰 값 2개를 모두 지운 경우
            // 두번째로 큰 값과, 나머지 값들의 합이 같다면 경우의 수 증가.
            if (totalSum - array[right] * 2 == array[right - 1] * 2)
                count++;
        } else if (n - right == 1) {        // 가장 큰 값이 하나인 경우
            // 두 포인터를 사용하여 가장 큰 값과
            // 나머지 원소들의 합에서 2개의 원소를 제외한 값이 같아지는 경우를 찾는다.
            right = n - 2;
            int rightCount = 1;
            for (int left = 0; left < right; left++) {
                // 나머지 원소들의 합이 작은 경우, right 포인터를 왼쪽으로 이동시켜 합을 증가시킨다.
                while (left < right && totalSum - array[left] - array[right] < array[n - 1] * 2) {
                    right--;
                    rightCount = 1;
                }
                // 같은 값이 있다면 같은 값의 개수를 세어가며 right 포인터를 이동
                while (left < right && array[right] == array[right - 1]) {
                    right--;
                    rightCount++;
                }
                
                // 가장 큰 값과 나머지 원소들의 합이 일치한 경우
                if (totalSum - array[left] - array[right] == array[n - 1] * 2) {
                    // 두 포인터가 서로 다른 경우
                    // left와 right가 가르키는 값의 개수의 곱만큼이 경우의 수로 생긴다.
                    if (left < right)
                        count += rightCount;
                    else
                        count += (long) rightCount * (rightCount - 1) / 2;
                    // 두 포인터가 서로 같은 경우
                    // 사실 위 경우는 left와 right가 같은 값이거나
                    // 나머지 원소들의 합이 작아 가장 큰 값과 같아지지 못해 포인터가 겹친 경우이다.
                    // 두 경우 모두, rightCountC2를 한 값과 같으므로
                    // 해당 값을 구해 더한다.
                }
            }

            // 가장 큰 값은 하나이고
            // 두번째로 큰 값이 2개인 경우 (두번째로 큰 값이 3개인 경우는 좋은 배열을 만드는 것이 불가능하다)
            if (array[n - 2] == array[n - 3]) {
                // 첫번째로 큰 값과
                // 두번째로 큰 값 중 하나를 지워
                // 나머지 합과 비교한다.
                // 두번째로 큰 값 어느 것을 지워도 되므로 경우의 수는 2개가 늘어난다.
                if (totalSum - array[n - 1] == array[n - 2] * 3)
                    count += 2;
            } else {
                // 두번째로 큰 값이 하나인 경우
                // 첫번째로 큰 값을 지우고
                // 두번째로 큰 값과 나머지 원소들이 합에서 하나의 원소를 뺀 값을 비교한다.
                for (int i = 0; i < n - 2; i++) {
                    if (totalSum - array[n - 1] - array[i] == array[n - 2] * 2)
                        count++;
                }
                // 첫번째로 큰 값, 두번째로 큰 값을 모두 지우는 경우
                // 세번째로 큰 값이 나머지들이 합과 동일한지 비교한다.
                if (totalSum - array[n - 1] - array[n - 2] == array[n - 3] * 2)
                    count++;
            }
        }
        // 답 출력
        System.out.println(count);
    }
}