/*
 Author : Ruel
 Problem : Baekjoon 27066번 나무 블럭 게임
 Problem address : https://www.acmicpc.net/problem/27066
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27066_나무블럭게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 각각 주머니에 담겨있다.
        // 이 때 주머니를 두 개 골라, 합치는 작업을 원하는 만큼 할 수 있다.
        // 원하는 만큼 작업을 한 뒤, 남은 k개의 주머니들에 담긴 수들의 평균값으로 정렬을 했을 때
        // ⌊(k + 1)/2⌋ 번째 수를 점수로 갖는다 할 때, 최대 점수는?
        //
        // 정렬, 그리디 문제
        // 푸는 방법이 재밌는 문제
        // n개의 주머니를 그대로 정렬하면
        // ⌊(n + 1)/2⌋ 번째 수를 점수로 얻을 수 있다.
        // n-1개의 주머니 중에서는 얻을 수 있는 점수 중 최대는 어느 것일까?
        // 첫번째와 두번째 주머니를 합치면, 세번째 주머니보다는 같거나 작은 수가 되고
        // 이전 판의 주머니 혹은 그 다음 주머니가 점수가 되게 된다.
        // 위와 같이 작은 주머니들을 하나로 몰아 가장 작은 주머니 하나를 만들고,
        // 나머지 큰 수들을 유지해주는 경우가 가장 큰 점수를 얻게 된다.
        // 따라서 위 과정을 반복하다보면 결국
        // 두번째로 큰 수까지 점수로 가질 수 있고, 별개로 모든 주머니를 하나로 합쳤을 때의 값도 계산해준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());

        int[] nums = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 모든 수의 합
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += nums[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(nums);
        
        // 0 ~ n-1번째 수들을
        // 작은 수들을 지속적으로 하나의 주머니로 합쳐나가면, 결국 n-2번째 수가 점수가 되는 때가 생긴다.
        // 그 때의 최대 점수
        double answer = (n >= 2 ? nums[n - 2] : 0);
        // 모든 수를 하나의 주머니로 합쳤을 때의 점수
        answer = Math.max(answer, (double) sum / n);
        // 답 출력
        System.out.println(answer);
    }
}