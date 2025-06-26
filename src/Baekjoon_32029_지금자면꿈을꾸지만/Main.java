/*
 Author : Ruel
 Problem : Baekjoon 32029번 지금 자면 꿈을 꾸지만
 Problem address : https://www.acmicpc.net/problem/32029
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32029_지금자면꿈을꾸지만;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 과제가 있으며, 각 과제들의 제출 기한이 주어진다.
        // 하나의 과제를 처리하는데 a만큼 시간이 걸리며,
        // a 미만의 정수 x를 골라, b * x만큼 자면, 이후 과제를 처리는데 걸리는 시간이 (a - x)로 줄어든다.
        // 잠은 한 번만 잘 수 있다고 할 때, 최대 처리할 수 있는 과제의 개수는?
        //
        // 브루트 포스 문제
        // 어떻게 풀어야할까? 라는데 시간이 오래 걸리는 문제
        // 잠을 기준으로
        // 잠을 자기 전에 과제를 풀고 -> 잠을 자고 -> 남은 과제를 처리
        // 하는 순으로 진행한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 과제, 과제를 처리하는데 걸리는 시간 a, 잠 단위 b
        int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 과제들
        int[] homeworks = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            homeworks[i] = Integer.parseInt(st.nextToken());
        // 오름차순 정렬
        Arrays.sort(homeworks);
        
        // 최대로 푼 과제의 개수
        int max = 0;
        // 잠을 자고 난 후 처리하는 과제의 번호
        int startIdx = 0;
        // 자기 전에 처리한 과제의 수
        int startCount = 0;
        // i개의 과제를 자기 전에 처리한다.
        for (int i = 0; i < n; i++) {
            // 자기 전에 처리한 과제의 수가 i보다 작고
            // 아직 모든 과제를 살펴보지 않았다면
            while (startCount < i && startIdx < n) {
                // 다음 과제를 처리할 수 있다면 startCount 증가
                if (homeworks[startIdx++] >= (startCount + 1) * a)
                    startCount++;
            }

            // sleep 만큼 잠을 잔다.
            for (int sleep = 0; sleep < a; sleep++) {
                // 잠을 잔 후 처리한 과제의 수
                int count = 0;
                // startIdx부터 살펴보며
                for (int j = startIdx; j < n; j++) {
                    // j번 과제의 마감이
                    // 자기 전에 처리한 과제들이 걸린 시간 + 잔 시간 + 잔 후에 처리한 과제와 j번 과제를 처리하는데 걸리는 시간
                    // 보다 같거나 늦어야만, j번 과제를 처리 가능
                    if (homeworks[j] >= startCount * a + b * sleep + (count + 1) * (a - sleep))
                        count++;
                }
                // 최대로 처리한 과제의 수를
                // 자기 전에 처리한 개수와 잔 후의 처리한 과제의 수의 합과 비교
                max = Math.max(max, startCount + count);
            }
        }
        // 최대로 처리한 과제의 수 출력
        System.out.println(max);
    }
}