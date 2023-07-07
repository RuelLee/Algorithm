/*
 Author : Ruel
 Problem : Baekjoon 1612번 가지고 노는 1
 Problem address : https://www.acmicpc.net/problem/1612
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1612_가지고노는1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n의 배수 중 1으로만 이루어진 수를 찾고 싶다.
        // 그 중 가장 작은 수를 출력하라
        //
        // 정수론...?
        // 코딩은 간단하지만 약간의 생각이 필요한 문제
        // int나 long 범위에서 1로만 이루어진 n의 배수가 없을 수도 있다.
        // 따라서 n보다는 큰 1로만 이루어진 수 중 가장 작은 수 a를 찾는다.
        // 그 후, a에서 a보다 작은 가장 큰 n의 배수를 빼주고
        // a가 n보다 작아졌다면, a = a * 10 + 1의 과정을 해
        // n보다 크지만 가장 작은 수로 만들어준다.
        // 위의 a * 10 + 1의 과정이 뒤에 1을 하나 더 추가해나가는 과정이므로
        // 위와 같이 계산해나가면 값의 범위 초과 없이 계산할 수 있다.
        //
        // 또한 값을 찾을 수 없는 n값이 존재하는데, 2의 배수, 5의 배수인 n이다.
        // n값이 2나 5의 배수일 경우에는 -1을 바로 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 2나 5의 배수일 경우 답을 찾을 수 없다. -1 출력
        if (n % 2 == 0 || n % 5 == 0)
            System.out.println(-1);
        else {
            int num1 = 1;
            int count = 1;
            // n보다 크지만 가장 작은 1로만 이루어진 수를 만든다.
            while (n > num1) {
                num1 = num1 * 10 + 1;
                count++;
            }
            
            // 1로만 이루어진 수 num1이 0보다 큰 경우 동안
            while (num1 > 0) {
                // num1보다 작은 가장 큰 n의 배수를 빼준다.
                num1 -= (num1 / n) * n;
                // 만약 num1이 0이 아니고 n보다 작아졌다면
                // *10 + 1을 통해 가장 작은 n보다 큰 수로 만들어준다.
                // 위 과정을 통해 num1의 자릿수가 하나씩 늘어나는 것이기 때문에
                // count를 하나씩 증가시켜준다.
                while (num1 != 0 && num1 < n) {
                    num1 = num1 * 10 + 1;
                    count++;
                }
            }
            // 최종적으로 num1이 0이 된 순간 반복문을 빠져나오고
            // 그 때의 자릿수가 count에 계산된다.
            // count 출력.
            System.out.println(count);
        }
    }
}