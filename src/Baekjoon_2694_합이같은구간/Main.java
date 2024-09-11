/*
 Author : Ruel
 Problem : Baekjoon 2694번 합이 같은 구간
 Problem address : https://www.acmicpc.net/problem/2694
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2694_합이같은구간;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기 m의 수열이 주어진다.
        // 해당 순서를 유지하면서, 합이 같은 그룹으로 나누고자 한다.
        // 이 때 합의 최소값은?
        //
        // 브루트포스 문제
        // 합이 최소가 되고자한다면 수열을 최대한 많은 그룹으로 나누어야한다.
        // 최대한 많은 그룹으로 나눈다면 m개의 그룹으로 나눌 수 있다.
        // m개부터 1개까지 그룹으로 나누는 경우를 계산해나간다.
        // 그룹의 수가 총합의 약수가 되며, 같은 합의 그룹으로 나누는 것이 가능한지 살펴본다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 수열의 크기 m
            int m = Integer.parseInt(br.readLine());
            // 수열
            int[] array = new int[m];
            for (int i = 0; i < m / 10; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 10; j++)
                    array[i * 10 + j] = Integer.parseInt(st.nextToken());
            }
            if (m % 10 != 0) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < m % 10; j++)
                    array[m / 10 * 10 + j] = Integer.parseInt(st.nextToken());
            }
            
            // 전체 합
            int totalSum = Arrays.stream(array).sum();
            // 한 개의 그룹으로 나누는 것은 항상 가능하니 해당 값을 초기값으로 준다.
            int answer = totalSum;
            for (int i = m; i > 0; i--) {
                // 만약 i가 전체 합의 약수가 아니라면 건너뛴다.
                if (totalSum % i != 0)
                    continue;

                // 같은 합인 i개의 그룹으로 나누는 것이 가능하다면
                // 답을 갱신하고 반복문을 종료한다.
                if (canDivide(array, totalSum / i)) {
                    answer = totalSum / i;
                    break;
                }
            }
            // 답 기록
            sb.append(answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // array를 각 그룹의 합이 sum인 그룹들로 나눌 수 있는지 확인한다.
    static boolean canDivide(int[] array, int sum) {
        int currentSum = 0;
        for (int i = 0; i < array.length; i++) {
            // 합이 sum보다 적다면 더하고
            if (currentSum + array[i] < sum)
                currentSum += array[i];
            // 정확히 sum이 된다면 합을 0으로 리셋
            else if (currentSum + array[i] == sum)
                currentSum = 0;
            else        // 넘친다면 불가능한 경우이므로 false 반환
                return false;
        }
        // 위 경우를 모두 통과하며 false를 반환하지 않았다면
        // 가능한 경우이므로 true 반환
        return true;
    }
}