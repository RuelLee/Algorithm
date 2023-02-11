/*
 Author : Ruel
 Problem : Baekjoon 22945번 팀 빌딩
 Problem address : https://www.acmicpc.net/problem/22945
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22945_팀빌딩;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 개발자들이 한 줄로 서있다.
        // 개발자 A와 개발자 B가 팀을 만들 때 능력치는
        // A와 B 개발자 능력치 중 낮은 값 * 두 개발자 사이에 있는 개발자들의 수
        // 예를 들어 1 4 2 5로 서있을 때, 1, 5개발자가 팀을 이룬다면 1 * 2가 된다.
        // 팀 빌딩에서 나올 수 있는 팀 중 능력치의 최대값을 구하라
        //
        // 두 포인터
        // 코딩 자체는 간단했지만 두 포인터 문제임을 알아채는데 시간이 걸렸다.
        // 두 포인터를 양쪽 끝에 위치 시킨다.
        // 두 개발자들 사이의 간격을 계속 줄이면서 탐색하기 때문에
        // 능력치를 높이기 위해서는 두 개발자 중 낮은 쪽의 능력치가 높아지도록 변경해야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        int n = Integer.parseInt(br.readLine());
        int[] developers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 두 포인터를 양쪽 끝에 배치
        int left = 0;
        int right = developers.length - 1;
        // 팀 능력치
        int ability = 0;
        // 두 포인터가 교차할 때까지.
        while (left < right) {
            // 능력치의 최대값을 계산한다.
            // 현재 포인터들이 가르키는 두 개발자의 능력치값을 계산 후
            // 최대값이 갱신되는지 확인.
            ability = Math.max(ability,
                    Math.min(developers[left], developers[right]) * (right - left - 1));

            // 포인터들이 가르키는 개발자들 중 left가 가르키는 쪽이 더 작다면
            // left를 증가시켜 더 능력치가 높은 개발자를 가르키도록 한다.
            if (developers[left] < developers[right])
                left++;
            // right가 작다면 right를 감소시켜 더 능력치가 높은 개발자를 가르키도록 한다.
            else
                right--;
        }

        // 답안 출력.
        System.out.println(ability);
    }
}