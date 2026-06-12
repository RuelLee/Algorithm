/*
 Author : Ruel
 Problem : Jungol 1830번 나머지(Granica)
 Problem address : https://jungol.co.kr/problem/1830
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1830_나머지_Granica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수를 m으로 나눴을 때, 전부 나머지가 같게되는 1보다 큰 m을 모두 구해 오름차순으로 출력하라
        //
        // 유클리드 호제법, 약수 문제
        // 모든 수의 나머지를 같게 하는 m 중 가장 큰 값은
        // 이웃한 두 수의 차이들의 최대공약수를 구하면 된다.
        // 그리고 그러한 모든 m들은 구한 최대공약수의 약수들이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine().trim());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = Integer.parseInt(br.readLine().trim());
        // 정렬
        Arrays.sort(arr);

        // 두번째 수와 첫번째 수의 차이
        int gcd = arr[1] - arr[0];
        // 이웃한 모든 수들의 차이들의 최대공약수를 구한다.
        for (int i = 2; i < arr.length; i++)
            gcd = getGCD(gcd, arr[i] - arr[i - 1]);

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 찾은 최대공약수의 약수를 모두 구한다.
        for (int i = 2; i * i <= gcd; i++) {
            if (gcd % i == 0) {
                priorityQueue.offer(i);

                if (i * i != gcd)
                    priorityQueue.offer(gcd / i);
            }
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        while (!priorityQueue.isEmpty())
            sb.append(priorityQueue.poll()).append(" ");
        sb.append(gcd);
        // 출력
        System.out.println(sb);
    }

    // 유클리드 호제법으로 최대공약수를 구한다.
    static int getGCD(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}