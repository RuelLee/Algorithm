/*
 Author : Ruel
 Problem : Baekjoon 28421번 곱하기와 쿼리
 Problem address : https://www.acmicpc.net/problem/28421
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28421_곱하기와쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다. 수는 최대 1만까지 주어진다.
        // 두 가지의 쿼리가 존재한다.
        // 1 x : 수열에서 서로 다른 두 수를 골라 곱해 x를 만들 수 있다면 1 아니라면 0을 출력한다
        // 2 i : i번째 수를 0으로 바꾼다.
        // x는 최대 1억까지 주어진다.
        // q개의 쿼리를 처리한 결과를 출력하라
        //
        // 정수, 브루트포스
        // 수열에 존재하는 수의 개수를 센다.
        // 1번 쿼리가 들어올 때, 1부터 x의 제곱근까지 비교하며, 곱해 x가 나오는 경우가 있는지 찾는다.
        // 2번 쿼리가 들어오면 해당 순서의 수를 0으로 바꾸며, 개수를 줄인다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수, 쿼리의 수 q
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 수열
        int[] nums = new int[n];
        // 수의 개수
        int[] counts = new int[10_001];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            // i번째 수
            nums[i] = Integer.parseInt(st.nextToken());
            // 해당 수의 개수 증가
            counts[nums[i]]++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 1번 쿼리
            if (Integer.parseInt(st.nextToken()) == 1) {
                int x = Integer.parseInt(st.nextToken());
                // x가 0일 때
                // n이 1보단 커야하고, 0의 개수 또한 1개 이상 존재해야한다.
                if (x == 0)
                    sb.append(n > 1 && counts[0] > 0 ? 1 : 0).append("\n");
                else {
                    // x가 0이 아닌 경우
                    boolean found = false;
                    int j = 1;
                    // 두 수의 곱이 x의 제곱근보다 작을 때까지 살펴본다.
                    for (; j * j < x; j++) {
                        // 수열의 수가 1만까지 주어지므로
                        // x / j 값 또한 1만 범위 내에서 찾는다.
                        // 따라서 범위를 넘어간다면 건너뛴다.
                        if (x / j > 10_000)
                            continue;

                        // x가 j로 나누어떨어지고
                        // j와 x / j의 개수가 0개 이상이라면
                        // 수열 내 의 두 수를 곱해 x를 만들 수 있는 경우.
                        if (x % j == 0 && counts[j] > 0 && counts[x / j] > 0) {
                            found = true;
                            break;
                        }
                    }
                    // x가 j의 제곱인 경우
                    // j가 2개 이상 있어야한다.
                    if (j * j == x && counts[j] > 1)
                        found = true;

                    // 결과 기록
                    sb.append(found ? 1 : 0).append("\n");
                }
            } else {
                // 2번 쿼리인 경우
                int idx = Integer.parseInt(st.nextToken()) - 1;
                // nums[idx]의 개수를 하나 줄인다.
                counts[nums[idx]]--;
                // idx번째 수를 0으로 바꾼다.
                nums[idx] = 0;
                // 0의 개수 하나 증가
                counts[0]++;
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}