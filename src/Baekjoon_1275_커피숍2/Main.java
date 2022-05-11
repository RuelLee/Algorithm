/*
 Author : Ruel
 Problem : Baekjoon 1275번 커피숍2
 Problem address : https://www.acmicpc.net/problem/1275
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1275_커피숍2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] fenwickTree;
    static int[] nums;

    public static void main(String[] args) throws IOException {
        // n개의 숫자가 주어진다
        // 그리고 q개의 쿼리가 주어진다
        // 쿼리는 x, y, a, b로 주어지며, x, y까지의 합을 묻고, a번째 수를 b로 교체한다
        // 각 쿼리 답을 출력하라
        // x가 y보다 클 수 있다. 이 때의 범위는 y ~ x가 된다
        // 숫자의 범위는 -2^31 ~ 2^31이다.
        //
        // 세그먼트 트리 문제
        // 합을 묻는 문제이므로, 펜윅 트리를 사용하여 풀 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        fenwickTree = new long[n + 1];
        nums = new int[n + 1];

        // 숫자를 입력받으면서 펜윅 트리를 초기화해준다.
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            inputDiff(i + 1, nums[i + 1] = Integer.parseInt(st.nextToken()));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // x가 y보다 클 경우 자리 변경
            if (x > y) {
                int temp = x;
                x = y;
                y = temp;
            }

            // x ~ y까지의 합을 구해 Stringbuilder에 추가해주고
            sb.append(getSum(y) - getSum(x - 1)).append("\n");
            // a번재 수에 차이(b - nums[a])만큼을 반영해준다.
            inputDiff(a, (long)b - nums[a]);
            nums[a] = b;
        }
        System.out.print(sb);
    }

    // 입력된 차이 만큼을 펜윅 트리에 반영한다
    // -2^31 ~ 2^31까지의 값이 주어지므로, diff가 long 범위임에 유의하자.
    static void inputDiff(int loc, long diff) {
        while (loc < fenwickTree.length) {
            fenwickTree[loc] += diff;
            loc += (loc & -loc);
        }
    }

    // 0 ~ loc까지의 합을 구한다.
    static long getSum(int loc) {
        // 값이 long 범위임을 유의.
        long sum = 0;
        while (loc > 0) {
            sum += fenwickTree[loc];
            loc -= (loc & -loc);
        }
        return sum;
    }
}