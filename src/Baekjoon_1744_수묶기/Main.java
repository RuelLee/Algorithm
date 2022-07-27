/*
 Author : Ruel
 Problem : Baekjoon 1744번 수 묶기
 Problem address : https://www.acmicpc.net/problem/1744
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1744_수묶기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다. 이 합을 최대로 하려고 한다
        // 수를 두 개를 묶어, 곱해 하나의 수로 만드는 것이 가능하다.
        // 모든 수는 한번만 묶거나 묶지 않아야한다.
        // 최대 값을 출력하라.
        //
        // 수의 범위가 음수 ~ 양수까지이므로 범위를 나눠 생각한다
        // 양수인 범위에서는 가장 큰 두 수를 뽑아 서로 곱해 더해주는 것이 유리하다
        // 단, 작은 수가 1일 경우에는, 곱하면 큰 수 값 하나만 되므로, 따로따로 더하는게 유리하다.
        // 0보다 같거나 작은 경우에는
        // 가장 작은 두 수(= 절대값이 큰 두 수)를 곱해 더해주는 것이 유리하다.
        // 음수가 아니라 0보다 같거나 작은 경우인 이유는, 가장 큰 수가(=절대값이 제일 작은) 0일 경우
        // 해당 수도 곱해주는 것이 유리하다. 어차피 곱해지지 않는다면 음수값이므로.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 양수들
        PriorityQueue<Integer> higherThanZero = new PriorityQueue<>(Comparator.reverseOrder());
        // 0과 음수들
        PriorityQueue<Integer> lowerThanZero = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine());
            if (num > 0)
                higherThanZero.offer(num);
            else
                lowerThanZero.offer(num);
        }

        int sum = 0;
        // 양수인 범위에 대해
        while (higherThanZero.size() > 1) {
            // 두 수를 뽑는다
            int a = higherThanZero.poll();
            int b = higherThanZero.poll();
            // 작은 수가 1인 경우에는 따로따로 더하고
            if (b == 1)
                sum += a + b;
            // 그렇지 않을 땐 묶어, 곱해 더해준다.
            else
                sum += a * b;
        }
        
        // 0보다 같거나 작은 경우
        // 무조건 두 개를 뽑아 곱해 더해준다.
        while (lowerThanZero.size() > 1)
            sum += lowerThanZero.poll() * lowerThanZero.poll();

        // 양수와 양수의 개수가 홀수여서, 하나가 남은 경우 그냥 더해준다.
        if (!higherThanZero.isEmpty())
            sum += higherThanZero.poll();
        if (!lowerThanZero.isEmpty())
            sum += lowerThanZero.poll();

        // 최종 값 출력.
        System.out.println(sum);
    }
}