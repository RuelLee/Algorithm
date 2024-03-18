/*
 Author : Ruel
 Problem : Baekjoon 9763번 마을의 친밀도
 Problem address : https://www.acmicpc.net/problem/9763
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9763_마을의친밀도;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int MAX = 4 * 1000 * 3;

    public static void main(String[] args) throws IOException {
        // n개의 마을의 (x, y, z)가 주어진다.
        // 친밀도 = d12 + d23 (dij = |xi - xj| + |yi - yj| + |zi - zj|)
        // 친밀도가 가장 작은 세 마을의 친밀도를 구하라
        //
        // 브루트 포스 문제
        // 이지만 세 마을을 모두 골라서는 마을이 최대 1만으로 주어지므로
        // 1만^3으로 연산수가 너무 많아진다.
        // 친밀도는 두번째 마을에서 다른 두 마을로 이르는 최소 거리 합으로도 볼 수 있다.
        // 위와 같이 구하면 1만^2으로 줄일 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 마을
        int n = Integer.parseInt(br.readLine());
        int[][] villages = new int[n][];
        for (int i = 0; i < villages.length; i++)
            villages[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 최소 친밀도
        int min = MAX;
        for (int mid = 0; mid < villages.length; mid++) {
            // mid 마을에서부터
            // 가장 작은 거리
            int first = MAX;
            // 두번째 작은 거리
            int second = MAX;
            for (int another = 0; another < villages.length; another++) {
                // mid와 another이 같은 마을이어선 안된다
                if (mid == another)
                    continue;
                
                // 거리
                int distance = calcManhattanDistance(mid, another, villages);
                // second보다 작고
                if (distance < second) {
                    // first보다도 작다면 distasnce가 first
                    // first가 second로 내려간다.
                    if (distance < first) {
                        second = first;
                        first = distance;
                    } else      // first보단 크고, second보단 작다면 second 값만 바뀐다.
                        second = distance;
                }
            }
            // mid 마을을 중간 마을로 정했을 때의 최소 친밀도 min
            // 전체 마을의 최소 친밀도 값을 갱신하는지 확인.
            min = Math.min(min, first + second);
        }
        // 최소 친밀도 값 출력
        System.out.println(min);
    }

    static int calcManhattanDistance(int a, int b, int[][] villages) {
        return Math.abs(villages[a][0] - villages[b][0]) +
                Math.abs(villages[a][1] - villages[b][1]) +
                Math.abs(villages[a][2] - villages[b][2]);
    }
}