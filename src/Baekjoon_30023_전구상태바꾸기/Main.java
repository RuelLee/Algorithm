/*
 Author : Ruel
 Problem : Baekjoon 30023번 전구 상태 바꾸기
 Problem address : https://www.acmicpc.net/problem/30023
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30023_전구상태바꾸기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 전구가 일렬로 늘어서 있으며 각각 R, G, B 중 하나의 색을 띄고 있다.
        // 연속한 3개의 전구를 골라 색을 R -> G, G -> B, B -> R로 바꿀 수 있다.
        // 최소 몇 번을 바꾸면, 모든 전구의 색을 같은 색으로 만들 수 있는가?
        // 불가능하다면 -1을 출력한다.
        //
        // 브루트포스, 누적합 문제
        // 핵심은 모든 전구를 같은 색으로 만드는 것이다.
        // 따라서, 모든 전구의 색을 R로 바꾸는 경우, G로 바꾸는 경우, B로 바꾸는 경우 3가지를 모두 계산한다.
        // 색이 한 방향으로 바뀌며 순환한다.
        // 따라서 원래 색과 바뀐 횟수를 누적시켜 나머지를 구하는 형식으로 색을 구해주자.
        // 색을 바꿀 수 있는 전구의 범위가 3개가 아니라 m으로 주어진다면 더 효율적이었겠지만
        // 이 때, 해당 전구의 색이 바뀐 횟수를 누적합, 차분 배열 트릭으로 계산하면 편하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 전구
        int n = Integer.parseInt(br.readLine());

        String input = br.readLine();
        // 전구에 따라 R은 0, G는 1, B는 2의 값 할당
        int[] bulbs = new int[n];
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case 'R' -> bulbs[i] = 0;
                case 'G' -> bulbs[i] = 1;
                case 'B' -> bulbs[i] = 2;
            }
        }
        
        // 해당 전구의 색이 바뀐 횟수
        int[] changes = new int[n + 1];
        // 최소 전구 색 변경 횟수 값을 구한다.
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            // changes 초기화
            Arrays.fill(changes, 0);
            // 모든 전구 색을 i로 바꿀 때
            // 전체 전구의 색을 바꾼 횟수.
            // 첫번째 전구의 색을 몇 번 바꿔야하는지에 대한 값으로 초기화.
            int count = changes[0] = (i + 3 - bulbs[0]) % 3;
            // 차분 배열 트릭
            // changes[0]에서 증가한 값 만큼 changes[0 + 3]에서 값 차감
            changes[3] = -changes[0];

            for (int j = 1; j < bulbs.length - 2; j++) {
                // 변화 횟수 누적
                changes[j] += changes[j - 1];
                // 현재 전구의 색을 i로 바꾸려면 색을 바꿔야하는 횟수.
                int diff = (i + 3 - (bulbs[j] + changes[j]) % 3) % 3;
                // 해당 값 누적 처리
                changes[j] += diff;
                // 전체 변환 횟수 누적
                count += diff;
                // 마찬가지로 changes[j + 3]에서 해당 값 차감
                changes[j + 3] -= diff;
            }

            // 3개씩 선택가능하므로, n - 2번째 전구에서 계산은 끝남.
            // 하지만 누적합 처리를 한 후, n-1, n번째 전구가 i색인지 확인해야함.
            for (int j = changes.length - 3; j < changes.length; j++)
                changes[j] += changes[j - 1];
            
            // 마지막 두 전구의 색이 i이라면
            // 해당 색 변환 횟수를 min에 반영
            if ((bulbs[n - 2] + changes[n - 2]) % 3 == i &&
                    (bulbs[n - 1] + changes[n - 1]) % 3 == i)
                min = Math.min(min, count);
        }
        // min이 초기값이라면 불가능한 경우이므로 -1을 출력
        // 그 외의 경우 min 값 출력
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }
}