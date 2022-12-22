/*
 Author : Ruel
 Problem : Baekjoon 14921번 용액 합성하기
 Problem address : https://www.acmicpc.net/problem/14921
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14921_용액합성하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // -1억 ~ 1억 까지의 특성값을 갖는 용액들을 n개 갖고 있다.
        // 이 중 두 용액을 같은 양을 섞어 0에 가까운 특성값을 갖는 혼합액을 만들고 싶다.
        // 이 때 만들 수 있는 혼합액의 특성값은?
        //
        // 두 포인터 문제
        // 용액을 정렬한 뒤, 왼쪽 끝과 오른쪽 끝에서 시작하는 두 포인터를 갖고서
        // 0에 가까운 혼합액의 특성값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        int n = Integer.parseInt(br.readLine());
        int[] solutions = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(solutions);
        
        // left 포인터와 right 포인터
        int left = 0;
        int right = n - 1;
        int answer = Integer.MAX_VALUE;
        // 두 포인터가 교차하기 전까지
        while (left < right) {
            // 두 용액을 혼합.
            int sum = solutions[left] + solutions[right];
            // 혼합액 특성값의 절대값이 최소치를 갱신하는지 확인.
            if (Math.abs(answer) > Math.abs(sum))
                answer = sum;

            // 혼합액이 양수라면
            // 오른쪽 포인터를 하나씩 낮춰가며
            if (sum > 0)
                right--;
            // 음수라면
            // 왼쪽 포인터를 하나씩 높여간다.
            else if (sum < 0)
                left++;
            // 만약 특성값이 0은 혼합액을 찾았다면
            // 더 이상 살펴볼 필요없이 반복문을 종료한다.
            else
                break;
        }
        // 혼합액 최소 절대값을 갖는 특성값 출력.
        System.out.println(answer);
    }
}