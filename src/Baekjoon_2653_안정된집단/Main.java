/*
 Author : Ruel
 Problem : Baekjoon 2653번 안정된 집단
 Problem address : https://www.acmicpc.net/problem/2653
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2653_안정된집단;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람들에 대해 서로 간에 선호도가 주어진다.
        // 만약 사람들을 그룹으로 통해 나누어, 그룹 내 인원끼리는 모두 선호,
        // 그룹 외 인원에게는 모두 비선호로 만들 수 있다면 해당 집단은 안정된 집단이라고 한다.
        // 단 그룹은 2명 이상으로 이루어진다.
        // 다음 집단이 안정된 집단인지 판별하라
        //
        // 그래프 탐색 문제
        // 선호하는 인원을 따라가며, 다른 그룹을 선호하는 경우가 발생하는지 체크한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 인원
        int n = Integer.parseInt(br.readLine());

        // 각 인원의 선호도
        int[][] adjMatrix = new int[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int groupCounter = 1;
        int[] groups = new int[n];
        boolean possible = true;
        // 먼저 각 인원이 자신만 선호하는 경우가 있는지 살펴본다.
        // 그룹 인원은 최소 2명이므로, 해당 경우 불가능한 경우.
        for (int[] am : adjMatrix) {
            if (Arrays.stream(am).sum() == n - 1) {
                possible = false;
                break;
            }
        }

        if (possible) {
            // 각 인원을 순서대로 탐색하며 그룹을 나눈다.
            for (int i = 0; i < n; i++) {
                if (groups[i] == 0) {
                    groups[i] = groupCounter++;
                    if (!divideGroups(i, adjMatrix, groups)) {
                        possible = false;
                        break;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        if (possible) {     // 가능한 경우엔
            // 그룹의 개수
            sb.append(groupCounter - 1).append("\n");
            // 각 그룹에 속한 인원 기록
            for (int i = 1; i <= groupCounter; i++) {
                for (int j = 0; j < n; j++) {
                    if (groups[j] == i)
                        sb.append(j + 1).append(" ");
                }
                sb.deleteCharAt(sb.length() - 1).append("\n");
            }
        } else          // 불가능한 경우엔 0 출력
            sb.append(0).append("\n");

        // 답안 출력
        System.out.print(sb);
    }

    // 그룹을 나눈다.
    static boolean divideGroups(int num, int[][] adjMatrix, int[] groups) {
        // num의 선호도를 탐색한다.
        for (int i = 0; i < adjMatrix[num].length; i++) {
            // 선호하지 않는 사람인데 같은 그룹이라면 불가능한 경우
            // false 리턴
            if (adjMatrix[num][i] == 1 && groups[num] == groups[i])
                return false;
            else if (adjMatrix[num][i] == 0) {      // 선호하는 사람이라면
                if (groups[i] == 0) {       // 그룹이 나뉘지 않은 사람이라면
                    groups[i] = groups[num];    // 같은 그룹에 속하게 하고
                    // 해당 사람으로부터 탐색 결과를 반환받아 false가 반환된다면
                    // false를 반환한다.
                    if (!divideGroups(i, adjMatrix, groups))
                        return false;
                } else if (groups[i] == groups[num])        // 만약 이미 같은 그룹이라면 탐색하지 않는다.
                    continue;
                else        // 서로 다른 그룹이라면 불가능하므로 false 반환
                    return false;
            }
        }
        // 위의 false를 반환하는 경우들에 걸리지 않았다면
        // 같은 그룹으로 묶는 것이 가능한 경우이므로 true 반환.
        return true;
    }
}