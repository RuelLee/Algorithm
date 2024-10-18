/*
 Author : Ruel
 Problem : Baekjoon 1660번 캡틴 이다솜
 Problem address : https://www.acmicpc.net/problem/1660
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1660_캡틴이다솜;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 대포알을 갖고 있으며, 이를 사면체 모양으로 쌓고자 한다.
        // 사면체를 만드는 방법은 길이가 n인 정삼각형 모양 위에
        // 길이가 n-1인 정삼각형을 얹는 과정을 반복하여, 마지막에 길이가 1인 정사각형 모양을 얹는 방법이다.
        // 예를 들어 사이즈가 3인 사면체는
        //   X
        //
        //   X
        //  X X
        //
        //   X
        //  X X
        // X X X
        // 모양이 차곡차곡 포개진 형태이다.
        // 최소 개수의 사면체로 대포알을 정리하고자할 때, 그 수는?
        //
        // DP, BFS 문제
        // n이 최대 30만으로 주어지므로
        // 먼저, 한 층에 해당하는 삼각형에 들어가는 대포알을 구하고
        // 이를 바탕으로 사면체에 들어가는 대포알의 수를 구한다.
        // 그 후, 하나의 사면체에 해당하는 대포알의 개수들을 큐에 담아
        // BFS 탐색을 시작하며, 각 대포알의 수로 만들 수 있는 최소 사면체의 개수를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 대포알의 수
        int n = Integer.parseInt(br.readLine());
        
        // 삼각형과 사면체에 들어가는 대포알의 수
        int[] triangles = new int[120];
        int[] tetrahedrals = new int[120];
        triangles[0] = tetrahedrals[0] = 1;
        for (int i = 1; i < tetrahedrals.length; i++) {
            triangles[i] = triangles[i - 1] + (i + 1);
            tetrahedrals[i] = tetrahedrals[i - 1] + triangles[i];
        }
        
        // dp[i] = i개로 만들 수 있는 사면체의 최소 수
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // 초기값 0
        // 0개의 대포알로 만들 수 있는 사면체의 수 0개
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            // tetrahedral 개의 대포알을 사용하여 사면체를 하나 더 추가한다.
            for (int tetrahedral : tetrahedrals) {
                if (current + tetrahedral >= dp.length)
                    break;

                // current + tetrahedral 개의 대포알로 만드는 사면체의 수가
                // 이번의 최소 개수일 경우
                if (dp[current + tetrahedral] > dp[current] + 1) {
                    dp[current + tetrahedral] = dp[current] + 1;
                    queue.offer(current + tetrahedral);
                }
            }
        }
        // n개의 대포알로 만들 수 있는 사면체의 최소 개수를 출력한다.
        System.out.println(dp[n]);
    }
}