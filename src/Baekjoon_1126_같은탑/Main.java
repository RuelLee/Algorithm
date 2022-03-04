/*
 Author : Ruel
 Problem : Baekjoon 1126번 같은 탑
 Problem address : https://www.acmicpc.net/problem/1126
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1126_같은탑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 블록의 높이들이 주어진다
        // 블록들로 같은 높이의 두 개의 탑을 쌓고자 한다.
        // 이 때 가장 높게 쌓을 수 있는 탑의 높이는?
        // DP를 활용한 문제다
        // DP 문제는 무엇을 기준으로 생각할 것인가가 제일 어려운 것 같다
        // dp[i][j]에서 i는 사용이 가능한 블록, j는 그 때 두 탑의 높이 차이 그리고 값은 그 때의 높은 탑의 최대 높이가 들어갈 것이다
        // 그러면서 블록을 하나씩 살펴보며, 1. 높은 곳에 쌓았을 때, 2. 쌓지 않았을 때, 3. 낮은 곳에 쌓았을 때를 계산해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] blocks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[][] maxHeight = new int[n][Arrays.stream(blocks).sum() + 1];

        // maxHeight[0]에서는 첫블럭을 사용하느냐 안하느냐 밖에 없다.
        // 사용했을 때 blocks[0]만큼 차이가 발생하고 그 때 큰 쪽의 높이는 blocks[0]이다.
        maxHeight[0][blocks[0]] = blocks[0];
        for (int i = 1; i < blocks.length; i++) {
            // 항상 해당 블럭을 하나만 쌓을 경우가 존재한다.
            maxHeight[i][blocks[i]] = blocks[i];
            for (int j = 0; j < maxHeight[i - 1].length; j++) {     // 이전 블록까지만 사용했을 때를 살펴보며
                if (maxHeight[i - 1][j] == 0)       // 그 때의 높이가 0이라면(방법이 없다면) 건너뛴다.
                    continue;

                // 건너뛰지 않았다면 maxHeight[i - 1][j]가 가능한 경우이다
                // 그렇다면 여기에 i번째 블록을 어디에 올리느냐가 문제가 된다
                // 1. 높은 쪽에 쌓았을 경우.
                maxHeight[i][j + blocks[i]] = Math.max(maxHeight[i][j + blocks[i]], maxHeight[i - 1][j] + blocks[i]);
                // 2. 쌓지 않았을 경우.
                maxHeight[i][j] = Math.max(maxHeight[i][j], maxHeight[i - 1][j]);
                // 3.낮은 곳에 쌓았을 때.
                // 3-1. 블록이 두 탑의 차이보다 긴 경우.
                if (blocks[i] > j)
                    maxHeight[i][blocks[i] - j] = Math.max(maxHeight[i][blocks[i] - j], maxHeight[i - 1][j] + blocks[i] - j);
                // 3-2. 블록이 두 탑의 차이보다 작거나 같은 경우.
                else
                    maxHeight[i][j - blocks[i]] = Math.max(maxHeight[i][j - blocks[i]], maxHeight[i - 1][j]);
            }
        }
        // 최종적으로 n개의 블록을 모두 살펴보고, 높이 차이가 0인 탑이 존재한다면 그 탑의 높이가 답이다
        // maxHeight[n - 1][0]이 0이라면 불가능한 경우이므로 -1을 출력. 그렇지 않고, 값이 존재한다면 그 값을 출력한다.
        System.out.println(maxHeight[n - 1][0] == 0 ? -1 : maxHeight[n - 1][0]);
    }
}