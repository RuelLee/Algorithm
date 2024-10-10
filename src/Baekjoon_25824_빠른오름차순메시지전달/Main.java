/*
 Author : Ruel
 Problem : Baekjoon 25824번 빠른 오름차순 메시지 전달
 Problem address : https://www.acmicpc.net/problem/25824
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25824_빠른오름차순메시지전달;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] adjMatrix;

    public static void main(String[] args) throws IOException {
        // 선생님이 12명의 학생에게 메시지를 전달하고자 한다.
        // 각 학생들은 앞에서부터 두명씩 그룹으로 묶여있다.
        // 1,2번 학생이 한 그룹, 3,4 번 학생이 한 그룹, ... , 11,12번 학새잉 한 그룹
        // 선생님은 첫번째 그룹에 메시지를 전달한다.
        // 그룹에 속한 (1 혹은 2번) 학생이 메시지를 받고, 메시지를 받지 못한 그룹 내 다른 학생에게 메시지를 전달한다.
        // 그룹에서 두번째로 메시지를 전달 받은 학생은 +1번째 그룹으로 메시지를 전달하고 이 과정이 반복되어 12명이 모두 메시지를 전달받게 된다.
        // 각 학생들이 다른 학생에게 메시지를 전달하는 시간이 주어질 때
        // 모든 학생이 메시지를 전달받게되는 최소 시간을 출력하라
        //
        // 브루트 포스 문제
        // 12명이라는 작은 수로 주어지므로, 모든 경우의 수를 따져 최소 시간을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 인접 행렬
        // 다른 학생에게 메시지를 전달하는데 소요되는 시간
        adjMatrix = new int[12][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 첫번째로 메시지를 전달 받는 학생이 1번일수도, 2번일수도 있다.
        // 두 경우 모두 따져 적은 값을 채택.
        int answer = Math.min(bruteForce(0, 0, new boolean[12]),
                bruteForce(1, 0, new boolean[12]));
        // 답 출력
        System.out.println(answer);
    }

    // 브루트 포스
    // idx번째 학생이 메시지를 sum 시각에 메시지를 전달받은 경우
    // 남은 학생들에게 메시지를 전달할 때 걸리는 최소 시각을 찾는다.
    static int bruteForce(int idx, int sum, boolean[] visited) {
        // 방문 체크
        visited[idx] = true;

        int min = Integer.MAX_VALUE;
        // idx가 짝수이고
        if (idx % 2 == 0) {
            // idx가 그룹 내 첫번째로 메시지를 전달받은 경우.
            // 해당 학생에게 메시지 전달.
            if (!visited[idx + 1])
                min = Math.min(min, bruteForce(idx + 1, sum + adjMatrix[idx][idx + 1], visited));
            // idx가 그룹 내에 두번째로 메시지를 전달 받은 경우
            // 마지막 그룹일 경우 현재까지의 값을 체크
            else if (idx + 2 >= 12)
                min = Math.min(min, sum);
            else {
                // 마지막 그룹이 아닌 경우
                // 옆 그룹으로 메시지 전달.
                // 이 때 idx+2 or idx+3의 선택지가 있다.
                min = Math.min(min, bruteForce(idx + 2, sum + adjMatrix[idx][idx + 2], visited));
                min = Math.min(min, bruteForce(idx + 3, sum + adjMatrix[idx][idx + 3], visited));
            }
        } else {        // 홀수인 경우도 마찬가지로 계산
            if (!visited[idx - 1])
                min = Math.min(min, bruteForce(idx - 1, sum + adjMatrix[idx][idx - 1], visited));
            else if (idx + 1 >= 12)
                min = Math.min(min, sum);
            else {
                min = Math.min(min, bruteForce(idx + 1, sum + adjMatrix[idx][idx + 1], visited));
                min = Math.min(min, bruteForce(idx + 2, sum + adjMatrix[idx][idx + 2], visited));
            }
        }
        
        // 방문 체크 해제
        visited[idx] = false;
        // 찾은 최소값 반환.
        return min;
    }
}