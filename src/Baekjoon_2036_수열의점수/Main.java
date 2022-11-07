/*
 Author : Ruel
 Problem : Baekjoon 2036번 수열의 점수
 Problem address : https://www.acmicpc.net/problem/2036
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2036_수열의점수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정수로 주어진 수열이 있다. n은 최대 10만, 하나의 수는 절대값이 백만을 넘지 않는 수가 주어진다.
        // 이 중 하나의 수를 제거할 때는 그 수의 값, 두 수를 제거할 때는 두 수의 곱이 점수가 된다.
        // 점수를 최대로 하고자 할 때 그 값은?
        //
        // 그리디 문제
        // 두 수를 제거할 때는 두 수의 곱이 점수가 되므로,
        // 음수와 양수로 나누어 절대값이 큰 값들 두 개를 쌍으로 묶어 지울 때마다
        // 가장 큰 값을 점수로 얻을 수 있다.
        // 이제 0과 1의 처리와 각 부호의 수가 하나만 남았을 때를 고려해야한다.
        // 음수가 하나 남았을 때는 이를 그냥 더해야하는데, 이를 그냥 더하는 것보다
        // 0이 있다면 0을 곱해 점수를 0을 만들어버리는 것이 유리하다.
        // 따라서 0은 음수들과 함께 넣어 음수가 하나 남았을 때 점수가 음수가 되지 않도록 만들어주는 게 좋다.
        // 1의 경우는 양수이긴한데, 다른 수와 곱하는 경우 다른 수만 남게되므로,
        // 다른 수와 1을 하나씩 지워 각각 점수로 얻는게 유리하다.
        // 따라서 1이 나올 경우는 그냥 바로 지워버려 점수에 1점을 더하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // n개의 수
        int n = Integer.parseInt(br.readLine());
        // 0을 포함한 음수들
        PriorityQueue<Integer> minus = new PriorityQueue<>();
        // 1보다 큰 양수들
        PriorityQueue<Integer> plus = new PriorityQueue<>(Comparator.reverseOrder());
        // 점수 합
        long sum = 0;

        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine());

            // 1보다 작다면 minus 우선순위큐에
            if (num < 1)
                minus.offer(num);
            // 1보다 크다면 plus 우선순위큐에
            else if (num > 1)
                plus.offer(num);
            // 1이라면 점수에 바로 더한다.
            else
                sum += num;
        }

        // 음수 우선순위큐에서 절대값이 큰 순서대로 두 수를 뽑아 두 수를 곱해 점수에 더한다.
        // 만약 하나의 수만 남는다면 어쩔 수 없이 해당 값을 그대로 더한다.
        while (!minus.isEmpty())
            sum += (long) minus.poll() * (!minus.isEmpty() ? minus.poll() : 1);
        // 양수 우선선위큐에서 값이 큰 순서대로 두 수를 뽑아 곱해 점수에 더한다.
        // 만약 하나의 수만 남는다면 해당 값을 그냥 더한다.
        while (!plus.isEmpty())
            sum += (long) plus.poll() * (!plus.isEmpty() ? plus.poll() : 1);

        // 답 출력.
        System.out.println(sum);
    }
}