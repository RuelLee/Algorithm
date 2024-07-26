/*
 Author : Ruel
 Problem : Baekjoon 14848번 정수 게임
 Problem address : https://www.acmicpc.net/problem/14848
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14848_정수게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] array;

    public static void main(String[] args) throws IOException {
        // 1. 정수 n과 크기 k인 배열 A를 정한다.
        // 2. 1부터 N까지 정수를 모두 종이에 쓴다.
        // 3. 배열 A의 가장 첫 수를 고르고, 그 수를 배열에서 제거한다. 고른 수를 x라고 했을 때, 종이에 적혀있는 수 중에 x의 배수를 지운다.
        // 4. 배열이 빌 때까지 3번 작업을 반복한다.
        // 게임이 종료될 때 종이에 남아있는 수의 개수를 구하라
        //
        // 유클리드 호제법, 포함 배제의 원리
        // 문제를 살펴보면 결국 A에 속한 원소들의 배수들은 모두 종이에서 사라지게 된다.
        // 배수를 지우는 작업이기 때문에 교집합이 생기는 부분을 고려해야한다.
        // 따라서 포함 배제의 원리를 이용하여
        // 한 수의 배수들은 제거 -> 두 수의 최소공배수는 추가 -> 세 수의 최소공배수는 제거 -> ...
        // 하는 작업을 반복하여 종이의 남은 수의 개수를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 정수 n
        n = Integer.parseInt(st.nextToken());
        // 크기 k인 배열
        int k = Integer.parseInt(st.nextToken());
        array = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 지우는 수의 총 개수를 구한다
        long sum = 0;
        // 홀수개는 +, 짝수개는 -
        for (int i = 1; i <= k; i++)
            sum += countNums(0, 0, new int[i]) * (i % 2 == 0 ? -1 : 1);
        // 전체 수에서 지워진 수를 뺀 값이 남은 수의 개수이다.
        // 해당 수 출력
        System.out.println(n - sum);
    }
    
    // selected.length의 개수만큼의 수를 골라
    // 해당하는 수들의 최소공배수를 구해, n이하에 몇개가 속해있는지 구한다.
    static long countNums(int idx, int currentSize, int[] selected) {
        // 수를 모두 고른 경우.
        // 1 ~ n까지에 최소공배수가 몇 개 있는지 계산.
        if (currentSize == selected.length) {
            return n / getLCM(selected);
        } else if (idx >= array.length)     // 수를 모두 고르지 못한 경우. 0개
            return 0;
        
        // 현재 idx번째 수를 포함 혹은 미포함시
        // 계산될 수 있는 n이하 최소 공배수의 개수
        long sum = 0;
        // idx번째 수를 고른 경우
        selected[currentSize] = array[idx];
        sum += countNums(idx + 1, currentSize + 1, selected);
        // idx번째 수를 고르지 않은 경우.
        sum += countNums(idx + 1, currentSize, selected);
        // 해당 개수 반환.
        return sum;
    }
    
    // 최소 공배수
    static long getLCM(int[] array) {
        long lcm = array[0];
        for (int i = 1; i < array.length; i++)
            lcm *= (array[i] / getGCD(lcm, array[i]));
        return lcm;
    }

    // 유클리드 호제법
    // 최대 공약수
    static long getGCD(long a, long b) {
        long max = Math.max(a, b);
        long min = Math.min(a, b);
        while (min > 0) {
            long temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}