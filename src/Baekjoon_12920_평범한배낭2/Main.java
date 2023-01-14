/*
 Author : Ruel
 Problem : Baekjoon 12920번 평범한 배낭 2
 Problem address : https://www.acmicpc.net/problem/12920
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12920_평범한배낭2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // N개의 물건 종류에 대해
        // 물건의 무게 V, 배낭에 담았을 때의 만족도 C, 물건의 개수 K가 주어진다.
        // 배낭에 담을 수 있는 최대 무게를 M이라 할 때
        // 배낭에 물건들을 담아 얻을 수 있는 최대 만족도는 얼마인가?
        // N, M (1 ≤ N ≤ 100, 1 ≤ M ≤ 10,000)
        // V, C, K (1 ≤ V ≤ M, 1 ≤ C, K ≤ 10,000, 1 ≤ V * K ≤ 10,000)
        //
        // DP문제
        // 평범한 배낭 문제...가 아니다.
        // 각 물건들을 여러개 담을 수 있으므로 이에 대한 고려가 필요하다.
        // 가장 먼저 떠오르는 방법은 일반적인 배낭 문제와 같이 생각할 수 있게
        // a라는 물건이 여러개 있다고 생각하지 않고, 해당 a라는 물건을 전부 각각으로 생각하는 것이다.
        // 하지만 이럴 경우, 조건에 따라 k가 최대 1만개 주어지므로, 최악의 경우, 1만 * 1만 * 100, 총 100억번의 연산이 필요할 수 있다.
        // 그렇다면 k개의 물건을 k개로 생각하는 것이 아닌 더 적은 수의 개수로 나누는 방법을 생각해야한다.
        // 그 방법 중 하나로 2진법을 떠올리며 하나의 비트 필드로 생각하는 것이다.
        // 가령 27이라는 수를 분할한다고 생각한다면
        // 1 + 2 + 4 + ... 씩 2의 제곱 수를 더해나가며 27보다 같거나 작지만 가장 큰 수를 찾는다.
        // 1 + 2 + 4 + 8 = 15로 총 4개의 비트 필드를 만들고, 나머지 12에 대해서는 채울 수 있는 가장 큰 2의 제곱수들로 채운다.
        // 위 경우에서는 1, 2, 4, 8, 8, 4로 채워진다.
        // 1 ~ 15까지의 수는 1, 2, 4, 8의 수들로 모두 만들 수 있다. 2진법과 같으므로.
        // 16 ~ 27까지의 수는 추가적으로 있는 4, 8의 수와 1 ~ 8까지의 수들을 적절히 조합하여 얻어낼 수 있다.
        // 27이 27번의 연산이 아닌 6개의 연산으로 줄었다.
        // 이제 해당 조건으로 평범하게 냅색 문제처럼 풀면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 물건의 종류 n, 가방의 최대 무게 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 물건들의 조건
        // 무게, 만족도, 개수
        int[][] things = new int[n][];
        for (int i = 0; i < things.length; i++)
            things[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 0 ~ m까지의 무게
        int[] dp = new int[m + 1];
        // 물건들을 하나하나 살펴본다.
        for (int[] thing : things) {
            // 물건들을 2의 제곱수들로 나눈다.
            int remain = thing[2];
            Queue<Integer> queue = new LinkedList<>();
            // 1, 2, 4, ... 처럼 2의 제곱 오름차순으로 수들을 추가시킨다.
            // 제곱들의 합이 물건의 개수를 넘어서는 안된다.
            // 각각이 하나의 비트 필드를 확장하는 것과 같다.
            // 1, 2가 추가될 경우, 1 ~ 3까지의 모든 수를 합으로 표현이 가능하고
            // 1, 2, 4가 추가될 경우, 1 ~ 7까지의 모든 수를 합으로 표현이 가능하다.
            for (int i = 0; Math.pow(2, i) < remain; i++) {
                int num = (int) Math.pow(2, i);
                queue.offer(num);
                remain -= num;
            }

            // 남은 수에 대해서는 채울 수 있는 가장 적은 수의 2의 제곱으로 채운다.
            while (remain > 0) {
                int num = (int) Math.pow(2, (int) (Math.log(remain) / Math.log(2)));
                queue.offer(num);
                remain -= num;
            }

            // 큐에 담긴 모든 수들의 합을 통해
            // 1 ~ thing[2]까지의 모든 수를 표현이 가능하다.
            // 모든 묶음을 살펴본다.
            while (!queue.isEmpty()) {
                // queue.peek() 개수 묶음의 무게와 만족도
                int weight = queue.peek() * thing[0];
                int pleasure = queue.poll() * thing[1];

                // 해당 묶음을 갖고서, 평범한 배낭 문제와 같이 DP를 채운다.
                for (int j = dp.length - 1; j - weight >= 0; j--)
                    dp[j] = Math.max(dp[j], dp[j - weight] + pleasure);
            }
        }

        // 최종적으로 DP에 담긴 만족도들 중 가장 큰 값이 정답.
        System.out.println(Arrays.stream(dp).max().getAsInt());
    }
}