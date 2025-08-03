/*
 Author : Ruel
 Problem : Baekjoon 25571번 지그재그 부분배열
 Problem address : https://www.acmicpc.net/problem/25571
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25571_지그재그부분배열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 테스트케이스마다 배열의 길이 n이 주어지고, 해당 배열이 주어진다.
        // 값의 증가와 감소가 반복될 경우, 지그재그 배열이라고 한다.
        // 주어진 배열에서 지그재그 배열의 부분 배열 개수를 세어라
        //
        // 두 포인터 문제
        // 먼저 이전 수와 현재 수가 같지 않은 지점까지 이동한다.
        // 왼쪽 포인터는 오른쪽포인터 -1 지점에 고정시켜둔다.
        // 그 후, 오른쪽 포인터(rightIdx)와 rightIdx -1번째 수의 차 * rightIdx-1과 rightIdx-2의 차가 음수가 되는 경우 동안
        // == 수의 감소와 증가가 반복되는 동안 오른쪽 포인터를 이동시키며
        // 왼쪽 포인터와 오른쪽 포인터의 거리 차만큼의 부분 배열을 가질 수 있으므로 해당 개수를 누적시킨다.
        // 오른쪽 포인터가 배열의 끝에 도달할 때까지 반복하여 개수를 세어주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스
        int t = Integer.parseInt(br.readLine());
        // 배열
        int[] arr = new int[100000];
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 크기 n의 배열
            int n = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++)
                arr[i] = Integer.parseInt(st.nextToken());

            // 오른쪽, 왼쪽 idx
            int rightIdx = 1;
            int leftIdx = 0;
            long sum = 0;
            while (rightIdx < n) {
                // rightIdx와 rightIdx-1이 같은 동안 rightIdx 증가
                while (arr[rightIdx] == arr[rightIdx - 1])
                    rightIdx++;
                
                // rightIdx가 끝에 도달하지 않았다면
                // rightIdx와 rightIdx-1이 가르키는 수가 서로 다른 경우
                // 지그재그 부분배열 길이 2짜리 수열 하나를 찾았다.
                // 개수 누적 및 rightIdx 증가
                if (rightIdx < n) {
                    sum++;
                    leftIdx = rightIdx++ - 1;
                }
                
                // rightIdx와 rightIdx-1의 차 * rightIdx-1과 rightIdx-2의 차를 곱한 값이 음수라면
                // leftIdx ~ rightIdx까지 지그재그 배열
                // 더 세분해서 보면
                // leftIdx ~ rightIdx까지도 지그재그 배열
                // leftIdx + 1 ~ rightIdx까지도 지그재그 배열
                // ...
                // rightIdx - 1 ~ rightIdx도 지그재그 배열
                // 해당 개수 누적
                while (rightIdx < n && (long) (arr[rightIdx] - arr[rightIdx - 1]) * (arr[rightIdx - 1] - arr[rightIdx - 2]) < 0)
                    sum += (rightIdx++ - leftIdx);
            }
            // 테스트케이스의 답 기록
            sb.append(sum).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}