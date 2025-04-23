/*
 Author : Ruel
 Problem : Baekjoon 24620번 Sleeping in Class
 Problem address : https://www.acmicpc.net/problem/24620
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24620_SleepinginClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 연속해있는 수를 서로 합쳐, 남은 모든 수가 같은 수가 되게끔하고자 한다.
        // 합쳐야하는 최소 횟수는?
        //
        // 브루트 포스, 누적합 문제
        // 연속해있는 수들을 하나의 수로 합칠 수 있다.
        // 따라서 첫번째수부터 ~ i번째 수까지를 하나의 수로 합친 후,
        // 남은 수를 해당 수로 만들 수 있는지 확인한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            // n개의 수
            int n = Integer.parseInt(br.readLine());
            int[] a = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < a.length; j++)
                a[j] = Integer.parseInt(st.nextToken());
            
            // 누적합
            long[] psums = new long[n];
            psums[0] = a[0];
            for (int j = 1; j < psums.length; j++)
                psums[j] = psums[j - 1] + a[j];

            boolean found = false;
            // 첫번째 수부터 ~ firstEnd까지의 수를 합쳐 하나의 수로 만든 후
            // 남은 수들을 해당 수로 만들 수 있는지 확인한다.
            // 첫번째 합친 수의 값이 전체 합의 반 이하인 동안에만 시행.
            for (int firstEnd = 0; firstEnd < psums.length && psums[firstEnd] <= psums[psums.length - 1] / 2; firstEnd++) {
                // 첫번째 합친 수가 0이 아닌데, 전체 합이 첫번째 합친 수로 나누어떨어지지 않는다면
                // 당연히 불가능한 경우. 건너뛴다.
                if (psums[firstEnd] != 0 && psums[n - 1] % psums[firstEnd] != 0)
                    continue;

                boolean possible = true;
                // 현재까지 살펴본 인덱스
                int endIdx = firstEnd;
                // 합친 횟수
                int count = firstEnd;
                for (int j = firstEnd + 1; j < psums.length && possible; j++) {
                    // 만약 첫번째 합친 수보다 커졌다면
                    // 불가능한 경우.
                    if (psums[j] - psums[endIdx] > psums[firstEnd])
                        possible = false;
                    // 딱 맞아떨어지는 경우는 아직까진 가능한 경우
                    // 합친 횟수를 누적.
                    else if (psums[j] - psums[endIdx] == psums[firstEnd]) {
                        count += (j - endIdx - 1);
                        endIdx = j;
                    }
                    // 적은 경우는 넘어가 다음 수까지 더해본다.
                }
                // for문이 끝났고, 계산했던 수들이 정확히 첫번째 합친 수의 값과 일치하도록 만들 수 있었고
                // 마지막 수까지도 계산이 끝났다면 가능한 경우
                // 해당 횟수 기록
                if (possible && endIdx == psums.length - 1) {
                    sb.append(count).append("\n");
                    found = true;
                    break;
                }
            }
            
            // 만약 위의 경우에서 찾지 못했다면
            // 결국 전체 수를 합쳐서 하나의 수로 만들어야하는 경우
            if (!found)
                sb.append(n - 1).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}