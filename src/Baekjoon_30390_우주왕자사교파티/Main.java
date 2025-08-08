/*
 Author : Ruel
 Problem : Baekjoon 30390번 우주왕자 사교파티
 Problem address : https://www.acmicpc.net/problem/30390
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30390_우주왕자사교파티;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 포도주 a병과 빵 b개를 갖고 있다.
        // 이를 최대한 많은 사람에게 동일한 개수로 모두 나눠주고 싶다.
        // 최대 k번 포도주 -> 빵 혹은 빵 -> 포도주로 변환이 가능할 때
        // 포도주와 빵 모두 나눠주는 것이 가능한 최대 인원수는?
        //
        // 수학 문제
        // p명에게 포도주 와 빵을 모두 나눠준다면
        // 포도주는 p * x병, 빵은 p * y개로 존재해야한다.
        // 따라서 a + b = p * x + p * y
        // (a + b) / p = x + y
        // a + b가 p의 배수여야한다. 
        // a + b의 약수에 대해서 k번 변환 내에 두 수 모두 p의 배수가 되는 경우를 찾으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 포도주와 빵 그리고 최대 변환 횟수
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        long answer = 1;
        for (int i = 2; (long) i * i <= a + b; i++) {
            // a + b가 i의 배수인 경우
            if ((a + b) % i == 0) {
                // k번 이내에 a와 b를 i의 배수로 만들 수 있는 경우
                if (a % i <= k || b % i <= k)
                    answer = Math.max(answer, i);
                // 마찬가지로 k번 이내에 a + b를 (a + b) / i의 배수로 만들 수 있는 경우
                if (a % ((a + b) / i) <= k || b % ((a + b) / i) <= k)
                    answer = Math.max(answer, (a + b) / i);
            }
        }
        
        // 포도주나 빵 한 쪽으로 모두 변환하여, 해당 물건만 하나씩 나눠주는 것이 가능한 경우
        if (Math.min(a, b) <= k)
            answer = Math.max(answer, a + b);

        // 답 출력
        System.out.println(answer);
    }
}