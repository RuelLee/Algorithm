/*
 Author : Ruel
 Problem : Baekjoon 28303번 자석
 Problem address : https://www.acmicpc.net/problem/28303
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28303_자석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 실험대에 1번부터 n번까지의 각 칸이 주어지고 칸에는 에너지 상수가 주어진다.
        // 자석을 실험대에 설치하면 배터리가 충전이 되는데
        // 1. n극이 놓인 칸의 상수만큼 충전
        // 2. s극이 놓은 칸의 상수만큼 소모
        // 3. n극과 s극이 놓인 칸의 번호 차 * k 만큼 에너지가 소모
        // 한 번을 충전하여 얻을 수 있는 가장 배터리 충전량은?
        // 충전 전보다 에너지가 감소할 수도 있다.
        //
        // 스위핑 문제
        // 배터리 충전에 대해 식으로 나타내면
        // i > j
        // ai > aj 인 경우
        // ai - aj - (i - j) * k = (ai - i * k) - (aj - j * k)
        // ai - j * k = bi라 할 때
        // 순서대로 살펴보며 bi - 이전에 등장했던 가장 작은 b값을 해준다.
        // ai < aj인 경우는
        // aj - ai -(i - j) * k  = (aj + j + k) - (ai + i * k)
        // ai + i + k를 bi로 가정하고
        // 역순으로 살펴보며 bi - 이전에 등장했던 가장 작은 b값
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 실험대의 크기
        int n = Integer.parseInt(st.nextToken());
        // 상수 k
        int k = Integer.parseInt(st.nextToken());
        
        // 에너지 상수들
        int[] bench = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 답안
        int answer = Integer.MIN_VALUE;
        // ai > aj인 경우
        // 순서대로 살펴본다
        int min = bench[0] - k;
        for (int i = 1; i < bench.length; i++) {
            answer = Math.max(answer, bench[i] - k * (i + 1) - min);
            min = Math.min(min, bench[i] - k * (i + 1));
        }
        
        // aj > ai인 경우
        // 역순으로 살펴본다.
        min = bench[n - 1] + n * k;
        for (int i = n - 2; i >= 0; i--) {
            answer = Math.max(answer, bench[i] + (i + 1) * k - min);
            min = Math.min(min, bench[i] + (i + 1) * k);
        }
        // 예를 들어 순서대로 계산하는데, ai > aj가 아닌 ai < aj인 경우와 같이
        // 해당하지 않는 경우도 모두 값을 계산했지만
        // 정순, 역순 중 올바른 순서에 해당하는 경우에
        // 더 큰 값의 값이 계산되어 남았을 것이므로 무시해도 된다.

        // 답안 출력
        System.out.println(answer);
    }
}