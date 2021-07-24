/*
 Author : Ruel
 Problem : Baekjoon 14003번 가장 긴 증가하는 부분 수열 5
 Problem address : https://www.acmicpc.net/problem/14003
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 가장긴증가하는부분수열5;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static final int MAX = 1_000_000_001;

    public static void main(String[] args) {
        // 최장증가부분수열 문제
        // 14002번 가장 긴 증가하는 부분 수열 4를 풀 때, 자신보다 크거나 같은 수 중 가장 작은 수(부분 수열에서의 순서)를 구할 때, for문으로 일일이 탐색하였다.
        // 이를 이분탐색으로 개선한다면 시간적 이득을 얻을 수 있다!

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[] input = new int[n];
        for (int i = 0; i < input.length; i++)
            input[i] = sc.nextInt();

        int[] order = new int[n];       // 부분수열에서 가능한 순서
        int[] orderValue = new int[n];      // 해당하는 순서에 가능한 수들 중 가장 작은 수를 저장할 공간
        Arrays.fill(orderValue, MAX);

        int maxOrder = 0;           // 가장 큰 순서의 크기. 최장증가부분수열의 크기가 될 것이다.
        for (int i = 0; i < input.length; i++) {
            int currentOrder = findOrder(input[i], orderValue, maxOrder + 1);
            order[i] = currentOrder;            // 현재 값의 순서를 저장해주고
            orderValue[currentOrder] = input[i];        // 각 순서의 최소값을 현재 값으로 갱신해준다(지속적으로 같거나 더 작은 값으로만 해당 순서에 할당될 것이므로)
            maxOrder = Math.max(maxOrder, currentOrder);    // 그 후 순서의 크기가 증가했는지 확인해준다.
        }

        StringBuilder sb = new StringBuilder();
        sb.append(maxOrder + 1).append("\n");

        Stack<Integer> stack = new Stack<>();       // 스택으로 뒤에서부터 maxOrder에 해당하는 값을 담는다.
        for (int i = input.length - 1; i >= 0; i--) {
            if (maxOrder == -1)
                break;

            if (order[i] == maxOrder) {
                stack.push(input[i]);
                maxOrder--;
            }
        }

        sb.append(stack.pop());
        while (!stack.isEmpty())        // 거꾸로 pop 하면서 값을 나열하면 최장증가부분수열.
            sb.append(" ").append(stack.pop());
        System.out.println(sb);
    }

    // 각 순서의 최솟값이 저장된 orderValue에서 자신보다 크거나 같은 값 중 가장 작은 값의 위치를 반환해줄 이분탐색 함수
    static int findOrder(int n, int[] orderValue, int maxOrder) {
        int left = 0;
        int right = maxOrder;

        while (left < right) {
            int mid = (left + right) / 2;
            if (orderValue[mid] * 2 + 1 <= 2 * n)
                left = mid + 1;
            else if (orderValue[mid] * 2 + 1 > 2 * n)
                right = mid;
        }
        return left;
    }
}