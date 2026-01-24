/*
 Author : Ruel
 Problem : Baekjoon ㅏ
 Problem address : https://www.acmicpc.net/problem/1548
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1548_부분삼각수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] array;

    public static void main(String[] args) throws IOException {
        // 세 수 x, y, z가
        // x + y > z, x + z > y, y + z > x를 만 족하면 세 수는 삼각관계에 있다고 한다.
        // 길이가 n인 수열이 주어졌을 때, 임의의 세 수에 대해 위 관계가 만족하면 해당 수열은 삼각 수열이라고 한다.
        // n개의 수가 주어질 때, 몇 개의 수를 제외하여 삼각 수열로 만들고자 한다.
        // 만들어진 삼각 수열의 최대 길이는?
        //
        // 정렬, 브루트 포스 문제
        // 임의의 세 수에 대해 위 관계가 성립해야하지만, 수열로 주어질 경우
        // 가장 성립하지 않을 가능성이 높은 것은 가장 작은 두 수와 가장 큰 수와의 관계이다.
        // 위 가장 작은 두 수의 합 > 가장 큰 수를 만족한다면 다른 수들에 대해 살펴볼 필요없이 삼각 수열이다.
        // 따라서 위 조건을 만족한다면 해당 수열의 길이
        // 만족하지 않는다면, 가장 작은 수 혹은 가장 큰 수를 제외하는 과정을 거친다.
        // 두 경우 모두 해보아, 가장 길이가 긴 경우를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        // 수열
        array = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(array);
        // 0 ~ n-1까지의 범위에 대해 답을 찾는다.
        System.out.println(findAnswer(0, n - 1, new boolean[n][n]));
    }

    static int findAnswer(int start, int end, boolean[][] visited) {
        // 수열의 길이가 3 미만이 됐다면, 그 길이 그대로 반환
        if (end - start + 1 < 3)
            return end - start + 1;
        // 이미 방문한 적 있다면 다른 가지에서 찾아질 것이므로 0 반환
        else if (visited[start][end])
            return 0;

        // 방문 체크
        visited[start][end] = true;
        // 현재 범위로 가능하다면 해당 길이 반환.
        if (possible(start, end))
            return end - start + 1;
        // 불가능하다면, 가장 작은 수를 제외했을 때와 가장 큰 수를 제외했을 때
        // 경우를 비교하여 더 긴 수열의 길이를 반환
        return Math.max(findAnswer(start + 1, end, visited),
                findAnswer(start, end - 1, visited));
    }

    // start ~ end 범위의 수열이 삼각 수열이 판별.
    static boolean possible(int start, int end) {
        return (array[start] + array[start + 1] > array[end]);
    }
}