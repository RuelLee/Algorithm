/*
 Author : Ruel
 Problem : Baekjoon 2042번 구간 합 구하기
 Problem address : https://www.acmicpc.net/problem/2042
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 구간합구하기;

import java.util.Scanner;

public class Main {
    static long[] fenwickTree;
    static long[] nums;

    public static void main(String[] args) {
        // 간단한 세그먼트 트리 문제
        // 입력되는 숫자의 범위와 출력하는 숫자의 범위를 고려해서 int가 아닌 long으로 받아주자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        init(n);

        for (int i = 0; i < n; i++)
            inputValue(i + 1, sc.nextLong());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m + k; i++) {
            if (sc.nextInt() == 1)      // 1이라면 값 변경
                inputValue(sc.nextInt(), sc.nextLong());
            else        // 2라면 구간합 출력
                sb.append(getValue(sc.nextInt(), sc.nextInt())).append("\n");
        }
        System.out.println(sb);
    }

    static long getValue(int targetStart, int targetEnd) {          // targetStart ~ targetEnd까지의 합을 구한다.
        return getValueFromOne(targetEnd) - getValueFromOne(targetStart - 1);
    }

    static long getValueFromOne(int loc) {      // 1 ~ loc 까지의 합을 구한다.
        long sum = 0;
        while (loc > 0) {
            sum += fenwickTree[loc];
            loc -= (loc & -loc);
        }
        return sum;
    }

    static void init(int n) {
        fenwickTree = new long[n + 1];
        nums = new long[n + 1];
    }

    static void inputValue(int loc, long value) {       // 값을 바꿔줄 때 사용!
        long diff = value - nums[loc];      // 기존에 있던 값과 비교해서 차이를 구한 뒤
        nums[loc] = value;      // nums[loc] 에는 새로운 값을 넣어주고ㅡ
        while (loc < fenwickTree.length) {      // fenwickTree에는 차이만큼을 더해준다.
            fenwickTree[loc] += diff;
            loc += (loc & -loc);
        }
    }
}