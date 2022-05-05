/*
 Author : Ruel
 Problem : Baekjoon 2169번 로봇 조종하기
 Problem address : https://www.acmicpc.net/problem/2169
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2169_로봇조종하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 구역에 가치가 주어진다
        // 로봇은 아래/왼쪽/오른쪽 세 방향으로만 움직일 수 있으며, 방문했던 지역은 다시 가지 않는다고 한다
        // (1, 1) 구역에서 시작하여, (n, m) 구역에 도달할 때 얻을 수 있는 최대 가치는?
        //
        // 간단한 DP문제
        // 로봇의 방향이 아래 / 왼쪽 / 오른쪽 세 방향으로 정해져있는데, 한 번 아래로 내려갈 경우, 다시 올라올 방법은 없다
        // 따라서 왼쪽 아래로만 이동하는 DP와 오른쪽 아래로만 이동하는 DP를 만든 후, 위에서 내려오는 값과, 왼쪽이나 오른쪽으로 이동했을 때의 값과 비교하여 큰 값을 저장해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] mars = new int[n][];
        for (int i = 0; i < mars.length; i++)
            mars[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 오른쪽으로만 이동하는 DP
        int[][] right = new int[n][m];
        right[0][0] = mars[0][0];       // (0, 0)값
        // (0, i)에 방문하려면, 무조건 (0, i-1)에서 오른쪽으로 오는 수밖에 없다.
        for (int i = 1; i < right[0].length; i++)
            right[0][i] = right[0][i - 1] + mars[0][i];
        // 왼쪽으로만 이동하는 DP
        int[][] left = new int[n][m];
        // 첫줄에서 왼쪽으로 이동하는 방법은 없다. 큰 음수로 초기값읗 세팅해주자.
        Arrays.fill(left[0], Integer.MIN_VALUE);

        // 두번째 줄부터 생각한다.
        for (int i = 1; i < mars.length; i++) {
            // 먼저 오른쪽으로 이동하는 경우
            for (int j = 0; j < mars[i].length; j++) {
                // 해당 지점에 위에서 내려오는 경우를 생각한다
                // 위에서는 왼쪽으로 이동했든, 오른쪽으로 이동했든 최종적으로 내려오는 값이 크기만 하면 되므로
                // left[i - 1][j]과 right[i - 1][j] 값 중 큰 값에, 현재 지점의 가치를 더한 값으로 초기 값을 세팅해준다.
                left[i][j] = right[i][j] = Math.max(left[i - 1][j], right[i - 1][j]) + mars[i][j];

                // 그리고 j값이 0보다 큰 경우(자신보다 왼쪽이 있는 경우)
                // 위에서 내려온 값과, 자신의 왼쪽에서 온 값 중 더 큰 값을 right에 저장한다.
                if (j > 0)
                    right[i][j] = Math.max(right[i][j], right[i][j - 1] + mars[i][j]);
            }

            // 왼쪽으로 이동하는 경우
            // 위에서 내려오는 경우로 이미 초기값이 세팅되어있다.
            // 그렇다면 끝에서 두번째 값부터(자신의 오른편의 값이 있는 경우부터)
            // 현재 값(위에서 내려온 값)과 오른쪽에서 온 값 중 더 큰 값을 left에 저장한다.
            for (int j = mars[i].length - 2; j >= 0; j--)
                left[i][j] = Math.max(left[i][j], left[i][j + 1] + mars[i][j]);
        }
        // 최종적으로 도착지점에서의 left, right 값을 비교하고 더 큰 값을 출력한다.
        System.out.println(Math.max(left[n - 1][m - 1], right[n - 1][m - 1]));
    }
}