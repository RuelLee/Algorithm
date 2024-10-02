/*
 Author : Ruel
 Problem : Baekjoon 12924번 멋진 쌍
 Problem address : https://www.acmicpc.net/problem/12924
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12924_멋진쌍;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 자연수 n과 m이 멋진 쌍인 경우는 다음과 같다.
        // n의 뒤에서 몇 자리를 떼서 앞에 붙이면 m을 얻을 수 있는 경우.
        // 예를 들어 12345의 뒤에서 345를 떼 앞에 붙이면 34512과 되므로 12345와 34512는 멋진 쌍이다.
        // a와 b가 주어질 때
        // a<= x < y <= b 에서 멋진 쌍 x, y의 개수를 출력하라
        //
        // 브루트 포스 + 조합 약간? 문제
        // a와 b가 최대 200만으로 주어지므로 일일이 계산이 가능.
        // 따라서 각 수마다
        // 한 자리씩 뒤에서 앞에 떼다 붙이는 경우가 a ~ b 범위에 속할 경우 해당 수를 세어나간다.
        // 그 후 찾은 수들로 만들 수 있는 쌍의 개수를 구해나간다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 범위 a <= x < y <= b
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 방문 여부
        boolean[] visited = new boolean[b + 1];
        // 총 쌍의 개수
        long sum = 0;
        // a부터 b까지 차근차근 계산해나간다.
        for (int i = a; i <= b; i++) {
            // i가 이미 다른 수의 멋진 쌍으로 계산된 경우 건너뛴다.
            if (visited[i])
                continue;

            // i와 가능한 멋진 쌍들을 찾아본다.
            int count = 0;
            // 처음 수
            int num = i;
            // num이 a ~ b 범위 밖이거나, 범위 내이나 아직 방문하지 않은 경우 계속해서 반복한다.
            while (num < a || num > b || !visited[num]) {
                // num이 범위에 해당한다면
                if (num >= a && num <= b) {
                    // count 증가, 방문 체크
                    count++;
                    visited[num] = true;
                }

                // 이제 다음 수를 만들기 위해 num의 마지막 수를 떼, 가장 앞에 붙인다.
                // 마지막 수가 0이 아닌 경우
                if (num % 10 != 0)
                    num = num / 10 + (num % 10) * (int) (Math.pow(10, (int) (Math.log(num) / Math.log(10))));
                else {
                    // 마지막 수가 0인 경우
                    // 연속하여 0일 수 있으므로 해당하는 만큼 0을 지우고
                    int zeroCount = 0;
                    while (num % 10 == 0) {
                        num /= 10;
                        zeroCount++;
                    }
                    // 앞에 붙일 때 해당하는 0의 개수만큼 추가적으로 곱해 더해준다.
                    num = num / 10 + (num % 10) * (int) (Math.pow(10, (int) (Math.log(num) / Math.log(10)))) * (int) Math.pow(10, zeroCount);
                }
            }
            // 찾은 수들을 쌍으로 만드는 경우의 수는
            // count Combination 2
            sum += count * (count - 1) / 2;
        }
        // 답 출력
        System.out.println(sum);
    }
}