/*
 Author : Ruel
 Problem : Baekjoon 25308번 방사형 그래프
 Problem address : https://www.acmicpc.net/problem/25308
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25308_방사형그래프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 8개의 능력치로 이루어진 방사형 그래프가 주어진다.
        // 팔각형이며, 각 꼭짓점은 원점을 기준으로 45 * k도 만큼 떨어져있다.
        // 모든 능력치를 적당히 배열하여 그려지는 그래프가 볼록 다각형이 되게끔 하고 싶다.
        // 가능한 가짓수는?
        //
        // 수학, 순열, 브루트포스 문제
        // 브루트포스와 순열을 통해 모든 경우의 수를 구한 뒤,
        // 모든 점들에 대해 볼록을 만족하는지 체크하고
        // 그러하다면 1가지의 경우로 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 능력치들
        int[] abilities = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 능력치들로 만들 수 있는 볼록 다각형의 가짓수
        System.out.println(findAnswer(0, new int[8], abilities, new boolean[8]));
    }
    
    static int findAnswer(int idx, int[] order, int[] abilities, boolean[] pick) {
        // 모든 능력치들을 배열했다면
        if (idx == 8) {
            // 볼록하지 않는 꼭짓점이 있는지 찾고 있다면
            // 0 반환
            for (int i = 0; i < order.length; i++) {
                if (!isConvex(order[i], order[(i + 1) % 8], order[(i + 2) % 8]))
                    return 0;
            }
            // 모두가 볼록하다면 1 반환
            return 1;
        }
        
        // 아직 배열이 끝나지 않은 경우
        int sum = 0;
        for (int i = 0; i < abilities.length; i++) {
            // 선택되지 않은 능력치라면
            if (!pick[i]) {
                // 선택 체크
                pick[i] = true;
                // 위치에 값 기록
                order[idx] = abilities[i];
                // 다음 idx로 넘겨서 가짓수를 계산하고 그 값을 sum에 더함.
                sum += findAnswer(idx + 1, order, abilities, pick);
                // 선택 체크 해제
                pick[i] = false;
            }
        }
        // 찾은 경우의 수 반환.
        return sum;
    }

    // 세 점의 높이를 통해 볼록한지 판단.
    static boolean isConvex(int first, int second, int third) {
        return first * third * Math.sqrt(2) <= (first + third) * second;
    }
}