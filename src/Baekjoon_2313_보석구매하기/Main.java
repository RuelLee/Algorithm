/*
 Author : Ruel
 Problem : Baekjoon 2313번 보석 구매하기
 Problem address : https://www.acmicpc.net/problem/2313
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2313_보석구매하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 보석들이 n개 줄에 나열되어있다.
        // 각 줄에서 몇 개의 보석을 구매하되, 연속한 보석들을 한번에 구매해야하며, 이 때의 가치합을 최대로 하고자 한다.
        // 총 구매한 보석의 가치합과
        // 각 줄에서 구매해야하는 보석들의 시작 번호와 끝 번호를 출력하라
        // 그러한 경우가 여러개라면 보석의 개수가 적은 것을 우선, 그러한 것도 여러개라면
        // 사전순으로 가장 앞에 오는 것을 출력한다.
        //
        // 누적합 문제
        // 누적합을 통해, 이전까지의 보석들과 현재 보석을 가치 구매하는 것이 좋은지
        // 아니면 이전값을 버리고 현재부터 다시 계산하는지를 따져보면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 줄
        int n = Integer.parseInt(br.readLine());
        
        // 전체 보석들의 가치 합
        int totalSum = 0;
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            // 각 줄마다 연속한 보석들을 구매한다.
            // l개의 보석
            int l = Integer.parseInt(br.readLine());
            // 가치합
            int psum = Integer.MIN_VALUE;
            // 시작과 끝 번호
            int start = 0;
            int end = 0;
            
            // 이번 줄에서의 최대 가치합
            int maxSum = Integer.MIN_VALUE;
            // 그 때의 시작 번호와 끝 번호
            int[] answer = new int[2];
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= l; j++) {
                // 보석의 가치
                int gem = Integer.parseInt(st.nextToken());
                // 만약 누적합의 보석 가치가 0이하라면
                // 이전까지의 결과는 버리는 것이 낫다.
                // 지금부터 시작하는 경우의 합보다 같거나 작아지기 때문.
                if (psum <= 0) {
                    start = j;
                    end = j;
                    psum = gem;
                } else {
                    // 그렇지 않다면 현재 보석을 이전까지의 결과에 누적시킨다.
                    psum += gem;
                    end = j;
                }

                // 현재까지의 값이 보석 가치 합의 최댓값을 갱신하는지 확인.
                // 혹은 최댓값과 동일하지만 더 적은 수의 보석인지 확인.
                if (psum > maxSum ||
                        (psum == maxSum && answer[1] - answer[0] > end - start)) {
                    maxSum = psum;
                    answer[0] = start;
                    answer[1] = end;
                }
            }
            // 찾은 값을 전체 보석 가치 합에 누적
            totalSum += maxSum;
            // 현재 줄의 시작 번호와 끝 번호 기록
            sb.append(answer[0]).append(" ").append(answer[1]).append("\n");
        }
        // 전체 보석의 가치 합과
        // 각 줄에서의 시작 번호와 끝 번호 출력
        System.out.println(totalSum);
        System.out.print(sb);
    }
}