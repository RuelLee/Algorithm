/*
 Author : Ruel
 Problem : Baekjoon 1300번 k번째 수
 Problem address : https://www.acmicpc.net/problem/1300
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1300_k번째수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 배열을 만들고 배열의 값은 행과 열의 곱으로 한다고 한다.(행과 열은 1부터 시작)
        // 예를 들어 n = 3일 때(n <= 10^5)
        // 1 2 3
        // 2 4 6
        // 3 6 9 와 같은 형태로 만들어진다
        // 이 때 이 수들을 오름차순 정렬했을 때, k번째 수는 무엇인지 출력하라
        // k는 Math.min(10^9, n^2)보다 같거나 작은 수이다.
        //
        // 이분탐색 문제
        // 최소값은 1, 최대값은 n^2 범위에서 이분 탐색을 시행한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());

        // 답이 될 수 있는 최소 값은 1
        int start = 1;
        // 값이 될 수 있는 최대값은 n^2. 하지만 k가 10^9보다 같거나 작은 수이니 최대 10^9까지 가능.
        int end = n < Math.sqrt(Math.pow(10, 9)) ? n * n : (int) Math.pow(10, 9);
        while (start < end) {
            int mid = (start + end) / 2;
            // mid의 순서가 k보다 같거나 크다면 끝 범위를 mid로 바꾼다.
            if (calcOrder(n, mid) >= k)
                end = mid;
            // mid의 순서가 k보다 작다면 시작 범위를 mid + 1로 바꾼다.
            else
                start = mid + 1;

        }
        // 최종적으로 구해지는 start가 답.
        System.out.println(start);
    }

    static int calcOrder(int n, int num) {      // n * n의 행렬일 때, num의 순서를 센다.
        int count = 0;
        // 각 행마다 num보다 작은 수의 개수를 센다
        // i행의 마지막 열의 수와, num 중 작은 수를 i로 나눈 개수만큼이 해당 행에서 num보다 작은 수의 개수.
        for (int i = 1; i <= n; i++)
            count += Math.min((long) i * n, num) / i;
        return count;
    }
}