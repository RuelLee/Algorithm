/*
 Author : Ruel
 Problem : Baekjoon 1637번 날카로운 눈
 Problem address : https://www.acmicpc.net/problem/1637
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 날카로운눈;

import java.util.Scanner;

public class Main {
    static long[][] inputs;

    public static void main(String[] args) {
        // 이분탐색을 어떻게 적용할지에 대한 고민이 필요했다.
        // 주어진 범위가 너무 넓고 숫자 하나하나의 개수도 많기 때문에 모두 표시한다는 건 불가능하다
        // 따라서 범위에 따라서 계산을 해야하는데,
        // 특정 범위에 주어진 숫자들의 개수의 합이 홀수라면, 그 범위 안에 홀수 개인 숫자가 있는 것이다!
        // 따라서 이분탐색을 통해 범위를 줄여가며 하나의 값을 특정해야한다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        inputs = new long[n][3];

        long start = Integer.MAX_VALUE;
        long end = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++)
                inputs[i][j] = sc.nextInt();
            start = Math.min(start, inputs[i][0]);
            end = Math.max(end, inputs[i][1]);
        }

        while (start < end) {   // start == end 가 같아질 때까지
            long middle = (start + end) / 2;
            if ((getNumberOfNumbers(middle) & 1) == 1)  // middle까지의 숫자의 개수의 합이 홀수라면
                end = middle;   // middle ~ end까지의 범위는 자연히 짝수이므로, end를 middle로 바꿔 start ~ middle 까지의 범위로 줄여준다
            else
                start = middle + 1;     // 반대라면 middle + 1 ~ end 까지의 범위로 줄여준다.
        }
        // 최종적으로 나온 값이 있을텐데, 이는 진짜 홀수개로 존재해서 나온 값일 수도 있고, 범위가 밀리고 밀려 나온 마지막 값일 수도 있다.
        // 따라서 start까지의 숫자의 개수에서 start-1 까지의 숫자의 개수를 빼주면 start 값만의 개수가 나온다!
        int count = getNumberOfNumbers(start) - getNumberOfNumbers(start - 1);
        System.out.println((count & 1) == 1 ? start + " " + count : "NOTHING");
    }

    static int getNumberOfNumbers(long end) {   // end 까지의 숫자의 개수를 구하는 메소드
        int count = 0;
        for (long[] ip : inputs) {
            if (end < ip[0])    // 겹치는 범위가 없다면 패쓰
                continue;
            count += (Math.min(end, ip[1]) - ip[0]) / ip[2] + 1;    // c와 end 중 작은 값에서 a값을 빼고, 이를 b로 나눈 값에 + 1을 더하면 주어진 범위 내에 포함된 숫자의 개수
        }
        return count;
    }
}