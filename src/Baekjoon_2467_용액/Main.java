/*
 Author : Ruel
 Problem : Baekjoon 2467번 용액
 Problem address : https://www.acmicpc.net/problem/2467
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2467_용액;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 용액이 오름차순으로 주어진다
        // 산성 용액은 1 ~ 1_000_000_000 까지의 양의 정수로 주어지고
        // 알칼리 용액은 -1 ~ -1_000_000_00 까지의 음의 정수로 주어진다
        // 혼합한 용액의 특성값이 0에 가장 가까운 용액을 만드려고 할 때, 섞어야하는 두 용액은?
        //
        // 두 포인터를 활용한 간단한 문제
        // 정렬이 된 상태로 주어지므로, 하나의 포인터는 왼쪽, 하나의 포인터는 오른쪽에서 시작해
        // 두 포인터가 교차하기 전까지 체크해준다
        // 각 단계마다 포인터들이 가르키는 용액을 섞어보고 그 때의 특성값이 최소 특성값을 갱신하는지 살펴본다
        // 그리고 혼합물의 특성값이 양의 정수일 때는, 이를 줄여주기 위해, 오른쪽 포인터를 하나 왼쪽으로 이동시키고
        // 음의 정수일 때는 이를 줄여주기 위해 왼쪽 포인터를 오른쪽으로 이동시켜줘가며 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] solutions = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int left = 0;       // 처음 시작은 0
        int right = n - 1;      // 오른쪽 포인터의 시작은 n - 1
        int a = 0, b = 0;
        int minSum = Integer.MAX_VALUE;     // 혼합물의 최소 특성값. 초기값은 MAX_VALUE.
        while (left < right) {      // 두 포인터가 교차하기 전까지
            // 혼합물의 특성값
            int sum = solutions[left] + solutions[right];

            // 최소 특성값을 갱신한다면
            if (Math.abs(minSum) > Math.abs(sum)) {
                // 그 때의 특성값을 기록하고
                minSum = sum;
                // 포인터들의 위치도 기록한다.
                a = left;
                b = right;
            }
            // 혼합물의 특성값이 양수라면
            // 오른쪽 포인터를 왼쪽으로 이동시키고
            if (sum > 0)
                right--;
            // 0이라면 원하는 상태를 얻었으므로 종료
            else if (sum == 0)
                break;
            // 음수라면 왼쪽 포인터를 오른쪽으로 이동시켜준다.
            else
                left++;
        }
        // 최종적으로 얻어진 최소 특성값에 해당하는 두 용액을 출력한다.
        System.out.println(solutions[a] + " " + solutions[b]);
    }
}