/*
 Author : Ruel
 Problem : Baekjoon 1725번 히스토그램
 Problem address : https://www.acmicpc.net/problem/1725
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 히스토그램;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // 오래만에 다시 풀어보았지만 역시 개념을 떠올리기가 쉽지 않은 문제
        // 모노톤 스택을 이용하여 푸는 문제이다.
        // 처음부터 끝까지 살펴보며 스택에 담는다
        // 히스토그램에 스택의 peek() 값보다 같거나 큰 값이라면 그냥 담고,
        // 히스토그램에 스택의 peek() 값보다 작은 값이 나온다면, 해당 막대보다 같거나 작은 막대가 나올 때까지
        // 현재 구간에서의 사각형 넓이를 구한다
        // 이 때의 높이는 스택에 담겨있는 막대가 되고, peek()의 아래 막대의 위치로부터, 현재 히스토그램의 막대까지의 길이가 된다.
        // 이렇게 너비를 정할 수 있는 것은, peek()의 높이보다 작은 막대들이라면 스택에 반드시 담겨있을 것이다. 그렇지 않은 막대는 peek()보다 긴 막대임을 의미하기 때문.
        // -> 모노톤 스택의 성질
        // 따라서 peek()의 바로 아래 막대가 peek()보다 작은 가장 가까운 막대가 된다.
        // 위와 같은 연산을 반복해주면 된다.
        // 하지만 위와 같이만 수행하면 나중에 스택에 연산되지 않은 막대가 남을 수 있다.
        // 따라서 처음과 끝을 0으로 채워주면, 알아서 모든 막대들에 대한 연산을 수행한다!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] histogram = new int[n + 2];       // 처음과 끝에 0을 넣어주기 위해 +2를 해 배열을 설정해주자.
        histogram[0] = histogram[histogram.length - 1] = 0;     // 처음과 끝에 0

        for (int i = 1; i < n + 1; i++)
            histogram[i] = sc.nextInt();        // 값 입력

        Stack<Integer> stack = new Stack<>();
        long square = 0;        // 최대 넓이를 저장해둘 공간
        for (int i = 0; i < histogram.length; i++) {
            while (!stack.isEmpty() && histogram[stack.peek()] > histogram[i]) {        // 스택이 비어있거나, peek()값이 히스토그램의 막대보다 클 경우
                int height = histogram[stack.pop()];    // 높이는 현재 peek() 막대의 높이
                int width = i - (stack.peek() + 1);     // 너비는 peek()막대 바로 아래 막대의 위치로부터 i까지.
                square = Math.max(square, (long) height * width);       // 넓이를 구해 최대값을 갱신.
            }
            stack.push(i);      // 위 사항을 마치고 나서야 스택에 이번 히스토그램 막대를 넣어준다.
        }
        System.out.println(square);     // 값 출력.
    }
}