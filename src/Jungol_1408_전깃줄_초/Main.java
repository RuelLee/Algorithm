/*
 Author : Ruel
 Problem : Jungol 1408번 전깃줄(초)
 Problem address : https://jungol.co.kr/problem/1408
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1408_전깃줄_초;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 좌 우로 한 열씩 전봇대가 n개씩 주어진다.
        // 좌우로 전봇대들이 전깃줄로 연결이 되어있고, 연결된 전봇대의 순서가 주어진다.
        // 몇 개의 전깃줄을 끊어, 교차하는 전깃줄을 없애고자 한다.
        // 끊어야하는 전깃줄의 최소 개수는?
        //
        // 최장증가부분수열 문제.
        // 연결된 전봇대들을 입력받은 후
        // 왼쪽 전봇대 순서에 따라 정렬한다.
        // 그 뒤, order 배열을 만들고, 전봇대 연결을 순서대로 살펴가며 오른쪽 전봇대 순서에 따라
        // order에 오름차순으로 정렬해나간다.
        // 정렬된 순서만큼 남길 수 있고, 나머지는 끊어야하는 줄이다.
        // n - 정렬된 개수가 답

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 전봇대
        int n = Integer.parseInt(br.readLine().trim());
        int[][] connections = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            connections[i][0] = Integer.parseInt(st.nextToken());
            connections[i][1] = Integer.parseInt(st.nextToken());
        }
        // 왼쪽 순서에 따라 정렬
        Arrays.sort(connections, Comparator.comparingInt(o -> o[0]));

        // 남길 수 있는 최대 길이
        int max = 0;
        // 오른쪽 전봇대의 순서대로 오름차순으로 기록해나간다.
        int[] order = new int[n];
        Arrays.fill(order, Integer.MAX_VALUE);
        for (int i = 0; i < connections.length; i++) {
            // max부터 0번까지 살펴보며
            int j = max;
            // 오른쪽 전봇대보다 큰 값인 경우, j를 줄여나간다.
            // j가 남는 곳은
            while (j > 0 && order[j - 1] > connections[i][1])
                j--;
            // j번째가 올 수 있는 최대 순서를 찾고,
            // 오른쪽 열의 전봇대들 중 최소 번호인지 확인
            order[j] = Math.min(order[j], connections[i][1]);
            // j번째 연결을 끝으로 하는 최대 연결의 개수가 최댓값으 갱신하는지 확인
            max = Math.max(max, j + 1);
        }
        // 모든 연결을 살펴보고, n개의 연결 중 살릴 수 있는 max개를 제외한 연결을 모두 끊는다.
        System.out.println(n - max);
    }
}