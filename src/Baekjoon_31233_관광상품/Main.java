/*
 Author : Ruel
 Problem : Baekjoon 31233번 관광 상품
 Problem address : https://www.acmicpc.net/problem/31233
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31233_관광상품;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 명소가 주어지며, 각 명소의 아름다움이 주어진다.
        // 관광 상품은 2개 이상의 연속된 명소를 하나의 상품으로 만들며
        // 관광 상품의 전체 만족도는 포함된 명소들의 아름다움 값들에 대한 중앙값으로 책정된다.
        // 가능한 관광 상품의 최대 만족도는?
        //
        // 애드혹 문제
        // 구현은 간단하나, 문제는 푸는 방법을 떠올리기 어려운 문제
        // 먼저 관광 상품에 포함된 명소가 2곳이라면, 두 곳 중 더 적은 아름다움 값이 만족도가 된다
        // 3곳이라면, 두번째로 작은 값이 만족도가 된다.
        // 4곳이더라도 마찬가지로 두번째로 작은 값이 만족도가 된다.
        // 따라서 4곳 이상부터는 3곳으로 만드는 관광 상품보다 더 높은 만족도를 가질 수 없다.
        // 따라서 전체 구간에 대해, 3곳으로 만들 수 있는 최대 만족도를 구하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 관광 명소
        int n = Integer.parseInt(br.readLine());

        int[] scores = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 초기값은 첫번째와 두번째 관광 명소 아름다움들 중 더 작은 값
        int answer = Math.min(scores[0], scores[1]);
        // i, i+1, i+2번째 관광 명소의 아름다움들을 비교한다.
        for (int i = 0; i < scores.length - 2; i++) {
            // i + j번째 관광 명소가 최솟값인지 확인한다.
            for (int j = 0; j < 3; j++) {
                // 나머지 두 값들 중 더 적은 값
                int another = Math.min(scores[i + (j + 1) % 3], scores[i + (j + 2) % 3]);
                // i+j번째가 another보다 같거나 작은 값이라면
                // i+j번째를 최소값으로 보아도 된다.
                // 따라서 두번째로 작은 값인 another가 i ~ i+2까지 3개의 명소가 포함된 관광 상품의 만족도가 된다.
                // 이 값이 최대값을 갱신하는지 확인하고 반복문 종료.
                if (scores[i + j] <= another) {
                    answer = Math.max(answer, another);
                    break;
                }
            }
        }
        // 찾은 최대 만족도를 출력한다.
        System.out.println(answer);
    }
}