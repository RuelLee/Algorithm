/*
 Author : Ruel
 Problem : Baekjoon 14002번 가장 긴 증가하는 부분 수열 4
 Problem address : https://www.acmicpc.net/problem/14002
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 가장긴증가하는부분수열4;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // 최장증가부분수열
        // 각 숫자에 최대 순서를 적어주자
        // 그리고 각 순서마다 최소 숫자를 남겨야한다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[] array = new int[n + 1];
        int[] order = new int[n + 1];       // i번째 숫자는 최대 몇번째 순서가 될 수 있는지 남겨놓자.
        int[] minValue = new int[n + 1];    // n번째 순서에 해당하는 숫자들 중 최소값을 적어두자.
        Arrays.fill(minValue, Integer.MAX_VALUE);       // 각 순서의 최소값은 갱신될 수 있도록 Integer.MAX_VALUE로 셋팅해주자.
        minValue[0] = 0;    // 0번째 순서에는 0으로 셋팅해, 1번째 순서부터 각 값들이 세팅될 수 있도록 하자.

        for (int i = 1; i < array.length; i++)
            array[i] = sc.nextInt();

        int maxOrder = 0;       // 가장 큰 순서(최장증가부분수열의 길이)를 계산.
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < minValue.length; j++) {
                if (array[i] <= minValue[j])    // 자신보다 큰 값을 갖는 순서가 나타나면 그만.
                    break;

                if (array[i] > minValue[j]) {       // 자신보다 작은 값을 갖는 순서가 나타나면
                    order[i] = Math.max(order[i], j + 1);       // 자신의 순서를 갱신해준다.
                    maxOrder = Math.max(maxOrder, order[i]);    // 그리고 최장길이가 갱신되었는지 확인.
                    minValue[j + 1] = Math.min(minValue[j + 1], array[i]);      // 해당하는 순서의 최솟값이 현재 값인지 확인하고 갱신
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(maxOrder).append("\n");
        Stack<Integer> stack = new Stack<>();
        for (int i = order.length - 1; i > 0; i--) {    // 뒤에서부터 순서대로 최대길이부터 해당하는 숫자를 하나씩 stack 에 넣는다.
            if (order[i] == maxOrder) {
                stack.push(array[i]);
                maxOrder--;
            }
        }
        while (!stack.isEmpty())        // 다시 스택에서 꺼내면 최장 증가 부분 수열.
            sb.append(stack.pop()).append(" ");
        System.out.println(sb);
    }
}