/*
 Author : Ruel
 Problem : Baekjoon 2877번 4와 7
 Problem address : https://www.acmicpc.net/problem/2877
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2877_4와7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 4와 7로만 이루어진 수의 순서를 세고자 한다.
        // k번째 작은 4와 7로만 이루어진 수는?
        //
        // 구현 문제
        // 일단 순서대로 나열해보면
        // (4, 7), (44, 47, 74, 77), (444, 447, 474, 477, 744, 747,  774, 777)
        // 그래서 자릿수 대로 묶어보면
        // 2개, 4개, 8개, ... 으로 나타난다.
        // 따라서, 2의 제곱수를 더해가며 총 몇자리 수인지 확인하고
        // 해당 자릿수 그룹에서 순서가 반 이하라면 4, 초과라면 7을 갖게 된다.
        // 그렇다면 앞자리수는 정해졌으므로, 다음 자릿수를 정하기 위해
        // 2의 제곱수 합에서 해당하는 2의 제곱수만큼을 다시 빼줘 다음 자리로 낮추고
        // 다음 자릿수에서의 수는, 현재 그룹에서의 순서와 같으므로 % 모듈러 연산을 활용한다.
        // 예를 들어 474는 세자릿수 그룹에서 3번째에 위치한다. 이는 두자릿수 그룹에서 74가 세번째 위치하는 것과 같다
        // 774의 경우는 맨 앞에 7이 오는 수들 중에서 세번째에 해당.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        // n이 0보다 큰 경우에 진행
        while (n > 0) {
            // 2의 제곱수 합
            int sum = 0;
            // 현재 2의 제곱
            int pow = 0;

            // sum에 다음 2의 제곱수를 더한 값이 n보다 작을 경우에만
            // 계속 제곱 수를 더해준다.
            while (sum + Math.pow(2, pow + 1) < n)
                sum += Math.pow(2, ++pow);

            // 현재 sum은 n의 자릿수 그룹보다 하나 작은 자릿수에서
            // 마지막 수의 순서를 가르키고 있는 상태
            // 여기에 2의 pow 제곱을 더함으로써 현재 그룹에서 순서가
            // 반이하인지, 초과인지 알 수 있다.
            // 이하라면 4, 초과라면 7
            if (n <= sum + Math.pow(2, pow))
                sb.append(4);
            else
                sb.append(7);

            // sum에서 2의 pow 제곱을 빼줌으로써 하나 아래자릿수 그룹을 가르키게 된다.
            // 현재 그룹에서의 순서는 유지시켜서 아랫자릿수 그룹으로 내려가야하므로
            // n에서 sum과 1을 빼준 값을 2의 pow 제곱으로 모듈러 연산을 해주고
            // 다시 +1을 해 순서를 맞춰준다.
            n = sum - (int) Math.pow(2, pow) + (n - sum - 1) % (int) Math.pow(2, pow) + 1;
            // n은 아랫자릿수 그룹에서 동일한 순서를 가리킨다.
        }

        // 답안 출력.
        System.out.println(sb);
    }
}