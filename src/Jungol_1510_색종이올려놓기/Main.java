/*
 Author : Ruel
 Problem : Jungol 1510번 색종이 올려 놓기
 Problem address : https://jungol.co.kr/problem/1510
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1510_색종이올려놓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] papers;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        // 색종이의 가로 세로 길이가 주어진다.
        // 색종이들을 쌓는데, 위에 쌓인 색종이의 가로 세로는 아래 깔린 색종이와 평행해야하며
        // 각각의 길이 또한 넘지 않아야한다고 한다.
        // 최대한 많이 쌓을 수 있는 색종이의 수는?
        //
        // 재귀, 정렬, 메모이제이션 문제
        // 색종이들의 가로 세로를 입력받아, 가로 세로 중 작은 값을 앞에, 큰 값을 뒤에 배치한다.
        // 그 뒤 색종이들을 두 변에 따라 정렬한다
        // 그 후, 각 색종이들을 자신을 포함해 아래에 깔 수 있는 색종이의 수를 구해나간다.
        // 만약 이미 구해진 값이라면 해당 값을 바로 참조한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n장의 색종이
        int n = Integer.parseInt(br.readLine());
        papers = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 작은 값을 0번에, 큰 값을 1번에
            papers[i][0] = Math.min(a, b);
            papers[i][1] = Math.max(a, b);
        }
        // 정렬
        Arrays.sort(papers, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        dp = new int[n];
        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            // 해당 색종이로부터 계산하지 않았다면 해당 색종이를 맨 위에 올리는 경우부터 계산한다
            if (dp[i] == 0)
                max = Math.max(max, bfs(i));
        }
        System.out.println(max);
    }

    // idx번 아래에 깔 수 있는 색종이들을 계산한다.
    static int bfs(int idx) {
        // 이미 계산된 결과가 있다면 가져온다.
        if (dp[idx] != 0)
            return dp[idx];

        // 없다면 현재 색종이 하나만 쌓는 경우를 1로 초기값을 설정하고
        dp[idx] = 1;
        // 자신보다 후순위 색종이들을 살펴보며
        for (int i = idx + 1; i < papers.length; i++) {
            // 자신의 두 변보다 작지 않은 색종이들을 깔아본다.
            // 그리고 그 결과가 쌓을 수 있는 색종이의 최대 개수를 갱신하는지 확인한다.
            if (papers[i][0] >= papers[idx][0] && papers[i][1] >= papers[idx][1])
                dp[idx] = Math.max(dp[idx], bfs(i) + 1);
        }
        // 계산된 결과를 반환한다.
        return dp[idx];
    }
}