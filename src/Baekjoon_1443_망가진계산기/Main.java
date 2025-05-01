/*
 Author : Ruel
 Problem : Baekjoon 1443번 망가진 계산기
 Problem address : https://www.acmicpc.net/problem/1443
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1443_망가진계산기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int LIMIT;

    public static void main(String[] args) throws IOException {
        // d자리까지만 나타나는 계산기가 있다.
        // 처음 수는 1이며, 이에 2부터 9까지의 수 중 하나를 p번 곱해 만들 수 있는 가장 큰 수를 구하고자 한다.
        // d는 2 ~ 8까지의 수이고, p는 30보다 같거나 작은 음이 아닌 정수이다.
        // 만들 수 있는 모든 수가 d자리를 넘어간다면 -1을 출력한다.
        //
        // 브루트 포스 문제
        // 가지치기를 생각하며 해야하는 문제
        // 곱해지는 수들을 나열한다고 하면
        // 2 ~ 9까지의 수를 최대 30개 늘여놓는 것과 같다.
        // 이 때의 순서는 어떻게 되든 모두 같은 값이 나온다.
        // 따라서 중복되지 않게 계산하기 위해 내림차순으로 계산한다고 생각하자
        // 예를 들어 이전 수가 6이었다면, 이후로는 6이하의 수로만 곱해나가는 것이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // p번 곱해 d자리의 수 중 가장 큰 값을 구한다.
        int d = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        // 자릿수 제한
        LIMIT = (int) Math.pow(10, d);
        // 답 출력
        System.out.println(bruteForce(p, 1, 9));
    }

    // 현재 남은 곱셈의 수 idx
    // 현재 값 value, 곱하는 수의 범위 제한 maxMulti
    static int bruteForce(int idx, int value, int maxMulti) {
        // p번 모두 곱했다면 해당 수 반환.
        if (idx == 0)
            return value;

        // 불가능한 경우 -1을 반환해야하므로 초기값을 -1로 세팅.
        int max = -1;
        // 이전 값에 따라 곱하는 수에 제한을 건다.
        // maxMulti 이하로만 곱한다.
        for (int i = maxMulti; i > 1; i--) {
            // i를 곱한 값이 자릿수 제한을 벗어나지 않는다면
            // 해당 경우로 파생된 경우, 최대값을 반환 받아, 이를 max와 비교한다.
            if (value * i < LIMIT)
                max = Math.max(max, bruteForce(idx - 1, value * i, i));
        }

        // 구한 최대값 반환.
        // 불가능했다면 초기값인 -1이 반환될 것이다.
        return max;
    }
}