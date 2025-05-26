/*
 Author : Ruel
 Problem : Baekjoon 23250번 하노이 탑 K
 Problem address : https://www.acmicpc.net/problem/23250
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23250_하노이탑K;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] moves;

    public static void main(String[] args) throws IOException {
        // 1, 2, 3 막대가 있고, 1 막대에 n개의 원판이 순서대로 쌓여있다.
        // 한 번에 한 개의 원판만 움직이며, 놓인 원판은 아래 원판보다 항상 작다고 할 때
        // 1에 있는 원판들을 3으로 옮길 때, k번째 옮기는 원판이 꽂혀있는 막대와 옮길 막대를 출력하라
        //
        // 하노이 탑, 재귀 문제
        // 하노이 탑 문제는 세 부분으로 나누어 문제를 푼다.
        // n개의 원판이 주어진다면
        // 1. n-1개의 원판을 2번 막대로 옮기고,
        // 2. 가장 마지막 원판을 3번 막대로 옮기고
        // 3. 2번에 있는 n-1개의 원판을 3번으로 옮긴다.
        // 하노이 탑 해법은 위와 같다.
        // n이 최대 60으로 값이 크므로 일일이 시행해서는 안되고
        // size개의 원판을 옮기는데 걸리는 행동의 횟수를 미리 계산해두자.
        // 점화식은 위 과정에 따라 dp[i] = dp[i-1] + 1 + dp[i-1]로 세울 수 있다.
        // 그리고 재귀를 통해 계산하며
        // n, k가 주어질 때
        // k번째 행동이 1 ~ 3번 중 어디에 해당하는지를 찾아나가면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 원판, k번째 행동
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());
        
        // moves[i] = i개의 원판을 옮기는데 필요한 행동의 수
        moves = new long[n + 1];
        for (int i = 1; i < moves.length; i++)
            moves[i] = moves[i - 1] * 2 + 1;
        
        int[] answer = kHanoi(n, k, 1, 2, 3);
        // 답 출력
        System.out.println(answer[0] + " " + answer[1]);
    }
    
    // 재귀
    // size개의 원판을 start에서 end로 옮기는데 k번째 행동을 반환한다.    
    static int[] kHanoi(int size, long k, int start, int via, int end) {
        // k번째 행동이 size-1개의 원판을 start -> via로 옮기는 도중에 있는 경우
        if (k <= moves[size - 1])
            return kHanoi(size - 1, k, start, end, via);
        // k번째 행동이 size-1개의 원판을 옮기고, 마지막 큰 원판을 start -> end로 옮기는 과정인 경우
        else if (k == moves[size - 1] + 1)
            return new int[]{start, end};
        // k번째 행동이 size-1개의 원판을 via -> end로 옮기는 과정에 있는 경우
        else
            return kHanoi(size - 1, k - (moves[size - 1] + 1), via, start, end);
    }
}