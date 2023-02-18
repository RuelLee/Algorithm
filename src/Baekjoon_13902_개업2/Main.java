/*
 Author : Ruel
 Problem : Baekjoon 13902번 개업 2
 Problem address : https://www.acmicpc.net/problem/13902
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13902_개업2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2개의 화구와 m개의 웍을 가지고서 요리를 만든다.
        // 2개의 화구와 웍을 통해 한번에 요리를 만들 수 있다.
        // 웍의 크기는 각각 주어진다.
        // m그릇 주문이 들어왔을 때, 최소 몇 번의 요리를 만들어야 모든 주문을 처리할 수 있는가?
        // ex) 1, 3 크기의 웍이 있고, 5의 주문이 들어왔다면
        // (1, 3) + (1)으로 총 두번이다.
        //
        // DP문제
        // 화구가 2개로 제한되어있으므로
        // 한 번에 만들 수 있는 그릇 수를 웍을 2개씩 골라 모두 구한다.
        // 그 후, DP를 통해 각 그릇을 만들 수 있는 최소 요리 횟수를 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 입력 처리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] woks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 해쉬맵을 통해 한번에 만들 수 있는 그릇의 수를 미리 계산한다.
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < woks.length; i++) {
            hashSet.add(woks[i]);
            for (int j = i + 1; j < woks.length; j++)
                hashSet.add(woks[i] + woks[j]);
        }

        // 배열을 통해 오름차순 정렬해둔다.
        int[] once = hashSet.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(once);

        // DP
        // 큰 값으로 초기화 후, dp[0]은 0
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // 0그릇부터 살펴보며
        for (int i = 0; i < dp.length; i++) {
            // 만들 수 없는 그릇 수일 경우 건너뛴다.
            if (dp[i] == Integer.MAX_VALUE)
                continue;

            // 현재 i그릇이 완성된 상태이며
            // 여기에 다시 한번 요리할 경우 만들 수 있는 그릇 수는
            // i + o이다.
            for (int o : once) {
                // 만약 범위를 벗어난다면 종료.
                // once가 오름차순 정렬이므로
                if(i + o >= dp.length)
                    break;
                
                // 만약 i + 0 그릇을 만드는데 최소 요리 횟수를 갱신했다면
                // 값 갱신
                if (dp[i + o] > dp[i] + 1)
                    dp[i + o] = dp[i] + 1;
            }
        }

        // n 그릇을 만드는데 필요한 최소 요리 횟수를 출력한다.
        System.out.println(dp[n] == Integer.MAX_VALUE ? -1 : dp[n]);
    }
}