/*
 Author : Ruel
 Problem : Baekjoon 2374번 같은 수로 만들기
 Problem address : https://www.acmicpc.net/problem/2374
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2374_같은수로만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 각각의 수에 대해 add(i) 연산을 할 수 있는데, i번째 수와 좌우에 인접한 같은 수들에게 모두 +1을 한다.
        // 예를 들어 1 1 3 4로 수가 주어지고 add(1)을 할 경우, 2 2 3 4 가 되는 식이다.
        // 이 때 최소 연산 횟수로 모든 수를 같게 만들고자 할 때, 그 횟수는?
        //
        // 그리디 문제
        // 같은 수들을 하나의 그룹으로 묶는다던가, 같은 수들의 범위를 지정해서 낮은 수부터 올리는 올리는 방법 등을
        // 생각해봤지만, 너무나도 간단하게 그리디로 풀 수 있는 문제다.
        // 수가 1 3 5 7과 같이 오름차순으로 주어진다면 그냥 두 수의 차만큼을 계속해서 더해나가면 된다.
        // 작은 수들에게 연산을 해서 오른쪽 수와 같아지고, 그 후로는 한번에 같이 연산을 적용받기 때문
        // 반대로 7 5 3 1 처럼 낮아진다면, 가장 컸던 7을 기억해뒀다가, 마지막에 1과의 차 만큼을 더해주면 된다.
        // 좀 더 이미지상으로 쉽게 말하자면
        // 수열을 ↗↘↗↘↗↘ 과 같은 형태를 띄게 될 것이다.
        // 이 때 ↗↘인 구간에서는 꼭지점을 기준으로 왼쪽과 오른쪽을 한번에 연산 작업을 할 수 없으므로
        // 왼쪽 구간은 계산을 하여 →↘과 같은 형태로 만들어주고
        // ↘은 일단 무시하고 차후에 ↗과 같이 올려주면 된다.
        // 이 때 ↗↘↗↘
        //       ↑요 부분이
        //           ↑이 부분보다 낮아 증가량이 적을 수도 있는데 이는
        // 최대값을 꾸준히 계산해두다가 마지막 구간에서 한번에 처리해주면 된다.
        // 반대로 왼쪽 부분이 오른쪽 부분보다 낮은 경우에는, 오른쪽 증가분을 처리하는 동안 값이 같아지는 지점에서
        // 같이 오르게 되므로 생각할 필요가 없다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        long sum = 0;
        // 이전 값과 등장한 최대값
        int pre, max;
        // 첫번째 값으로 세팅
        max = pre = Integer.parseInt(br.readLine());

        for (int i = 1; i < n; i++) {
            // 이번 수
            int num = Integer.parseInt(br.readLine());
            // 최대값을 갱신하는지 확인
            max = Math.max(max, num);
            // 만약 이번에 등장한 수가 이전 값보다 크다면
            // 두 수의 차만큼을 더해준다.
            if (num > pre)
                sum += num - pre;
            // 만약 같거나 작다면 아무런 작업을 하지 않아도 된다.
            // 이전 값에 현재 값을 넣어준다.
            pre = num;
        }
        // 마지막 오름차순 구간을 처리한 뒤, 만들어진 수가
        // 최대값보다 작다면 해당 값만큼을 증가시킨다.
        sum += max - pre;
        // 답안 출력.
        System.out.println(sum);
    }
}