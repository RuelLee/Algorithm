/*
 Author : Ruel
 Problem : Baekjoon 28359번 수열의 가치
 Problem address : https://www.acmicpc.net/problem/28359
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28359_수열의가치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열의 가치는 다음과 같이 정의된다.
        // 감소하지 않는 부분 수열의 p와 증가하지 않는 부분 수열 q를 선택했을 때
        // p의 모든 원소의 합 + q의 모든 원소의 합
        // n가 p가 주어질 때
        // p의 원소들을 재배치하여, 수열의 가치를 최대화하고
        // 그 때의 가치와 수열을 출력하라
        //
        // 그리디, 정렬 문제
        // 먼저 같은 수들을 인접하게 배치하는 것이 좋다.
        // 증가하거나 감소하지 않는 수열에 한 그룹으로 한번에 포함될 수 있기 때문.
        // 또한 여러가지 경우들을 생각해보면
        // 각 수들의 그룹은 최대 한번씩 p 혹은 q 그룹에 속할 수 있고
        // 단 하나의 수 그룹만 두 그룹에 모두 포함될 수 있다.
        // 따라서 전체 합과 한 수의 그룹 합이 수열 가치의 최대합이 되고
        // 단순히 정렬한 형태가 최대가 가치를 수열의 형태 중 하나가 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 수의 개수를 센다
        int[] counts = new int[n + 1];
        // 전체 합
        int totalSum = 0;
        // 수 그룹 중 최대 합
        int maxSum = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            // 수열의 수
            int a = Integer.parseInt(st.nextToken());
            // 전체 합에 누적
            totalSum += a;
            // 해당 수가 포함된 그룹이 최대 합인지 확인
            maxSum = Math.max(maxSum, ++counts[a] * a);
        }

        StringBuilder sb = new StringBuilder();
        // 전체 합 + 최대 합 수 그룹의 값
        sb.append(totalSum + maxSum).append("\n");
        // 순서대로 정렬된 수열 기록
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] == 0)
                continue;
            for (int j = 0; j < counts[i]; j++)
                sb.append(i).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}