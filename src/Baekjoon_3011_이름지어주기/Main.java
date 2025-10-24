/*
 Author : Ruel
 Problem : Baekjoon 3011번 이름 지어주기
 Problem address : https://www.acmicpc.net/problem/3011
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3011_이름지어주기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 아들이 이름이 주어진다. 아들들의 이름은 짝수이다.
        // 딸의 이름을 짓고자 한다. 이름의 범위는 a ~ b이며 딸의 이름은 홀수이다.
        // 또한 가장 크기가 비슷한 아들의 이름과의 차이가 클수록 예쁜 이름이라고 한다.
        // 딸의 이름을 정하라
        //
        // 브루트 포스 문제
        // 두 수 간의 차이가 가장 먼 수를 구한다면, 두 수 밖의 수 혹은 두 수 사이의 평균값이 된다.
        // 따라서 아들들의 이름을 입력을 받되, 첫아들보다 작은 이름, 마지막 아들보다 큰 이름이 반영될 수 있도록
        // 가장 작은 값으로 Integer.MIN_VALUE, 가장 큰 값으로 Integer.MAX_VALUE를 추가해둔다.
        // 그 후, 이웃한 두 아들간의 평균값을 후보로 삼되, 범위 a ~ b 사이에 포함되는지 여부를 보정해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 아들
        int n = Integer.parseInt(br.readLine());
        
        // 이름들
        long[] evenNumbers = new long[n + 2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            evenNumbers[i] = Integer.parseInt(st.nextToken());
        // 가장 작은 값과 큰 값을 추가해준 뒤 정렬
        evenNumbers[0] = Integer.MIN_VALUE;
        evenNumbers[n + 1] = Integer.MAX_VALUE;
        Arrays.sort(evenNumbers);

        st = new StringTokenizer(br.readLine());
        // 딸의 이름이 가능한 범위
        // 딸의 이름은 홀수만 가능하므로 a와 b가 짝수라면 이를 보정
        long a = Integer.parseInt(st.nextToken());
        if (a % 2 == 0)
            a++;
        long b = Integer.parseInt(st.nextToken());
        if (b % 2 == 0)
            b--;

        // 아들들과 이름 차이가 가장 큰 이름을 찾는다.
        long maxDiff = 0;
        long answer = 0;
        for (int i = 1; i < evenNumbers.length; i++) {
            // i-1번째 아들과, i번째 아들의 평균값 이름
            long candidate = (evenNumbers[i] + evenNumbers[i - 1]) / 2 / 2 * 2 + 1;
            // 이름 범위 보정
            candidate = Math.max(candidate, a);
            candidate = Math.min(candidate, b);
            // 만약 이름 후보가 i-1번 아들보다 이름이 작다면
            // candidate = Math.min(candidate, b)에 의해 작아졌을 것이다.
            // 앞으로 등장할 이름들은 더 큰 값이 될테고, 해당 범위는 이미 이전 이름에 의해 계산이 됐다.
            // break로 반복문을 종료한다.
            if (candidate < evenNumbers[i - 1])
                break;
            // 이름 후보가 i번 아들보다 크다면
            // candidate = Math.max(candidate, a)에 의해 커졌을 것이다.
            // 이는 앞으로 계산될 것이기 때문에 건너뛴다.
            else if (candidate > evenNumbers[i])
                continue;

            // 두 수와 candidate간의 차이의 최소값을 구한다.
            long diff = Math.min(Math.abs(candidate - evenNumbers[i]), Math.abs(candidate - evenNumbers[i - 1]));
            // 이 값이 maxDiff보다 크다면
            // 현재 찾은 이름들 중 차이가 최대값이므로 답을 갱신한다.
            if (diff > maxDiff) {
                maxDiff = diff;
                answer = candidate;
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}