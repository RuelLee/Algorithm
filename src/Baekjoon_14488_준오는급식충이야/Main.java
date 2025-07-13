/*
 Author : Ruel
 Problem : Baekjoon 14488번 준오는 급식충이야!!
 Problem address : https://www.acmicpc.net/problem/14488
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14488_준오는급식충이야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1차원 직선 위에 n명의 학생이 있다.
        // 각 학생의 위치와 이동 속도가 주어진다.
        // 남은 시간 t초 이내에 한 지점에 모여야한다.
        // 그럴 수 있다면 1 그렇지 않다면 0을 출력한다.
        //
        // 큰 수 연산
        // t가 10억 + 소수점 넷째자리, 위치와 이동 속도가 10억 이내로 주어진다.
        // 소수점이 있는 것이 문제인데, 큰 수로 연산할 경우, 소수점은 부정확한 값을 나타낼 수 있다.
        // 따라서 값을 10000배 하여 BigInteger를 통해 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생
        int n = Integer.parseInt(st.nextToken());
        // 제한 시간 t
        long t = (long) (Double.parseDouble(st.nextToken()) * 10000);

        int[][] students = new int[n][2];
        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < students.length; j++)
                students[j][i] = Integer.parseInt(st.nextToken());
        }
        
        // 각 학생이 제한 시간 내에 움직일 수 있는 범위
        BigInteger[][] ranges = new BigInteger[n][2];
        // 10000배 된 시간
        BigInteger modedT = BigInteger.valueOf(t);
        for (int i = 0; i < n; i++) {
            ranges[i][0] = BigInteger.valueOf(students[i][0]).multiply(BigInteger.valueOf(10000)).subtract(BigInteger.valueOf(students[i][1]).multiply(modedT));
            ranges[i][1] = BigInteger.valueOf(students[i][0]).multiply(BigInteger.valueOf(10000)).add(BigInteger.valueOf(students[i][1]).multiply(modedT));
        }

        boolean possible = true;
        // 첫번째 학생의 이동 범위
        BigInteger min = ranges[0][0];
        BigInteger max = ranges[0][1];
        // 모든 학생들에 대해 이동 범위의 교집합이 있는지 확인한다.
        for (int i = 1; i < ranges.length; i++) {
            min = min.max(ranges[i][0]);
            max = max.min(ranges[i][1]);

            if (min.compareTo(max) > 0) {
                possible = false;
                break;
            }
        }
        // 교집합이 존재한다면 만날 수 있으므로 1
        // 그렇지 않다면 0을 출력
        System.out.println(possible ? 1 : 0);
    }
}