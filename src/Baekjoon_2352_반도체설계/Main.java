/*
 Author : Ruel
 Problem : Baekjoon 2352번 반도체 설계
 Problem address : https://www.acmicpc.net/problem/2352
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2352_반도체설계;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] minValueInOrder;

    public static void main(String[] args) throws IOException {
        // n개의 왼쪽 포트들과 오른쪽 포트들이 있다
        // 왼쪽 포트들의 순서대로 오른쪽의 연결되어야할 포트가 주어진다
        // 연결선이 서로 교차하지 않으면서 최대한 많은 포트들을 연결하고 싶을 때,
        // 최대한 연결할 수 있는 포트의 수는?
        //
        // 최장 증가 부분 수열에 관련한 문제
        // LIS에 관련한 문제는 문제 내용을 조금 다르지만 같은 문제가 정말 많은 것 같다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] ports = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 처음 길이는 1
        int maxLength = 1;
        // 각 순서에 해당하는 가장 작은 값을 저장해둔다.
        minValueInOrder = new int[n + 1];
        // Integer.MAX_VALUE로 초기 세팅.
        Arrays.fill(minValueInOrder, Integer.MAX_VALUE);
        for (int i = 0; i < ports.length; i++) {
            // ports[i]에 해당하는 순서를 찾는다.
            // 이미 생성된 길이보다 증가하는 길이가 될 수 있으므로 end의 범위를 maxLength + 1로 해주었다.
            // 사실 end를 그냥 ports 배열의 길이로 해버려도 큰 상관은 없으나, 조금이나마 연산을 줄일 수 있다.
            int order = findOrder(ports[i], maxLength + 1);
            // 해당 순서를 통해 최장 증가 부분 수열의 길이가 갱신되었는지 체크.
            maxLength = Math.max(maxLength, order);
            // 해당 순서에 해당하는 값이 이번 값이 최소값인지 확인하고 갱신.
            minValueInOrder[order] = Math.min(minValueInOrder[order], ports[i]);
        }
        // 최장 증가 부분 수열의 길이 출력.
        System.out.println(maxLength);
    }

    // 이분 탐색으로 minValueInOrder에서 value보다 작거나 같지만 가장 큰 값의 순서를 찾는다.
    static int findOrder(int value, int end) {
        // 시작 범위는 1부터, end까지.
        int start = 1;
        while (start < end) {
            int mid = (start + end) / 2;
            // minValueInOrder[mid] 값이 value보다 같거나 크다면
            // end의 범위를 mid로 낮춰준다..
            if (value <= minValueInOrder[mid])
                end = mid;
            // 반대로 value보다 크다면
            // start 범위를 mid + 1로 높여준다.
            else
                start = mid + 1;
        }
        // 최종적으로 멈추는 start 값이 value에 해당하는 최장 증가 부분 수열에서의 순서.
        return start;
    }
}