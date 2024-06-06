/*
 Author : Ruel
 Problem : Baekjoon 9213번 꽤 좋은 수
 Problem address : https://www.acmicpc.net/problem/9213
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9213_꽤좋은수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 완전수는 자기 자신을 제외한 약수의 합이 자신이 되는 수이다.
        // 예를 들어 6 = 1 + 2 + 3이므로 완전수이다.
        // 자연수의 나쁨은 약수의 합과 자기 자신의 차이다.
        // 3은 자기 자신을 제외한 약수가 1 뿐이고, 나쁨은 2이다.
        // start stop badness 형태로 쿼리가 주어질 때
        // start ~ stop까지 나쁨 정도가 badness 이하인 자연수의 개수를 구하라
        //
        // 에라토스테네스의 체 문제
        // 2부터 자연수들을 살펴나가며
        // 자신의 배수들에게 자신을 더해나간다.
        // 주어지는 값의 범위가 최대 100만인데,
        // 이 때 연산을 줄이기 위해 10만까지만 살펴봐도 된다.
        // 배수가 i * j 꼴인데, i와 j 두 수 모두 약수이니 한번에 판별하기 때문에 제곱근까지만 살펴본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 약수의 합
        int[] diff = new int[1_000_001];
        // 약수 자기 자신은 제외되므로 1 * n 꼴에서 1은 포함되어야한다.
        Arrays.fill(diff, 1);
        diff[0] = diff[1] = 0;
        // 2부터 제곱이 100만 이하일 때까지 살펴본다.
        for (int i = 2; i * i < diff.length; i++) {
            // j는 i부터
            // 만약 i와 j가 같다면 하나만 더하고
            // 서로 다르다면 각각을 더해준다.
            for (int j = i; i * j < diff.length; j++)
                diff[i * j] += (i + (i == j ? 0 : j));
        }

        // 쿼리 처리
        StringBuilder sb = new StringBuilder();
        String input = br.readLine();
        int t = 0;
        while (input != null) {
            StringTokenizer st = new StringTokenizer(input);
            int start = Integer.parseInt(st.nextToken());
            int stop = Integer.parseInt(st.nextToken());
            int badness = Integer.parseInt(st.nextToken());
            // 세 수가 모두 0이라면 종료
            if (start == 0 && stop == 0 && badness == 0)
                break;

            // start부터 stop까지
            // 나쁨이 badness 이하은 수들을 센다.
            int count = 0;
            for (int i = start; i <= stop; i++) {
                if (Math.abs(diff[i] - i) <= badness)
                    count++;
            }
            // 답안 작성
            sb.append("Test ").append(++t).append(": ").append(count).append("\n");
            input = br.readLine();
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}