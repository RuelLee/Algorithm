/*
 Author : Ruel
 Problem : Baekjoon 30105번 아즈버의 이빨 자국
 Problem address : https://www.acmicpc.net/problem/30105
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30105_아즈버의이빨자국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 초코바에 이빨 자국 n개가 찍혀있다.
        // 이빨 자국을 남길 때는 x와 x + k 두 위치에 자국이 남게된다.(k > 0)
        // 같은 위치에 여러개의 자국을 남기더라도 하나만 남는다.
        // 가능한 이빨 간격이 될 수 있는 수를 모두 출력하라
        //
        // 브루트 포스 문제
        // 가능한 모든 간격에 대해서 모두 시도해본다.
        // 첫 이빨자국으로부터, 가운데 있는 이빨 자국까지의 간격들은 가능하다.
        // 이 길이들을 가지고, 모든 이빨들을 두개씩 짝지을 수 있는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 이빨자국
        int n = Integer.parseInt(br.readLine());
        int[] locs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 가능한 간격들을 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < locs.length / 2 + 1; i++) {
            // 0 ~ i번째까지 자국까지의 간격을 길이로 삼는다.
            int length = locs[i] - locs[0];

            int k = i;
            // 해당 이빨 자국과 length만큼 차이나는 이빨 자국이 있는지 여부.
            boolean[] checked = new boolean[n];
            // length로 모든 이빨 자국을 찍을 수 있는지 여부
            boolean possible = true;
            // 왼쪽 이빨 자국의 위치 j
            for (int j = 0; j < locs.length; j++) {
                // 오른쪽 이빨 자국의 위치 k
                // j로부터 k가 length보다 간격이 적다면 k를 하나씩 증가시킨다.
                while (k + 1 < locs.length && locs[k] < locs[j] + length)
                    k++;

                // 두 자국 사이의 간격이 length로 일치한다면
                // 두 자국 모두 checked 표시
                if (locs[k] - locs[j] == length)
                    checked[j] = checked[k] = true;
                else if (!checked[j]) {
                    // 만약 그렇지 않다면
                    // j번째 자국에서는 더 이상 확인할 쌍이 없다.
                    // 따라서 이미 checked가 된 상태가 아니라면 불가능한 경우.
                    possible = false;
                    break;
                }
            }

            // 가능하다면 해당 길이를 큐에 담는다.
            if (possible)
                queue.offer(length);
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 큐의 크기 = 가능한 간격의 쌍
        sb.append(queue.size()).append("\n");
        // 간격들 기록
        while (!queue.isEmpty())
            sb.append(queue.poll()).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답안 출력
        System.out.println(sb);
    }
}