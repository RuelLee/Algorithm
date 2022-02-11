/*
 Author : Ruel
 Problem : Baekjoon 14572번 스터디 그룹
 Problem address : https://www.acmicpc.net/problem/14572
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14572_스터디그룹;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 학생의 수 n, 알고리즘의 수 k, 그룹 내 허용할 학생 간 실력 차이 d가 주어진다
        // 또한 학생 별, 알고 있는 알고리즘 개수와 종류, 실력이 주어진다
        // 이 때 그룹스터디의 효율성을 (그룹 인원이 알고 있는 전체 알고리즘의 수 - 모든 그룹원이 아는 알고리즘의 수) * 그룹원의 수라고 정의할 때
        // 최대 효율성은 얼마인가
        // 그룹 원 내의 실력 차이가 정해져있다
        // 학생들을 실력 순으로 정의한 뒤, 두 포인터를 사용해보자!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        // 각 학생에 대한 정보를 저장한다
        // 알고리즘의 종류가 최대 30개이므로 int 값을 활용한 비트마스킹으로 처리 가능.
        int[][] students = new int[n][2];
        for (int i = 0; i < students.length; i++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());       // 학생이 알고 있는 알고리즘의 수
            students[i][0] = Integer.parseInt(st.nextToken());  // 학생의 실력
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                students[i][1] |= 1 << Integer.parseInt(st.nextToken());        // 알고리즘의 종류를 비트마스킹으로 처리
        }
        Arrays.sort(students, Comparator.comparingInt(o -> o[0]));      // 각 학생의 실력에 따라서 정렬.

        // 각 알고리즘을 알고 있는 인원을 체크
        int[] knownAlgorithms = new int[k + 1];
        // 그 때의 효율성
        long maxEfficiency = 0;
        int j = 0;
        for (int i = 0; i < students.length; i++) {
            while (students[i][0] - students[j][0] > d) {       // i번째 학생과 j번째 학생의 실력의 차이가 d 초과로 벌어진다면,
                // j학생을 그룹에서 제외한다.
                for (int g = 1; g <= k; g++) {      // 알고 있는 알고리즘을 빼주고,
                    if ((students[j][1] & 1 << g) != 0)
                        knownAlgorithms[g]--;
                }
                // j값을 늘려 그룹 인원에서 제외
                j++;
            }
            // i번째 학생을 추가.
            for (int g = 1; g <= k; g++) {
                if ((students[i][1] & 1 << g) != 0)     // 알고있는 알고리즘들 추가
                    knownAlgorithms[g]++;
            }
            int algoKnownAll = 0;       // 그룹 인원이 알고 있는 전체 알고리즘의 수
            int allAlgo = 0;        // 모든 그룹원이 알고 있는 알고리즘의 수
            for (int knownAlgorithm : knownAlgorithms) {
                if (knownAlgorithm > 0) {       // 아는 사람이 한명이라도 있다면
                    allAlgo++;      // allAlgo 증가
                    if (knownAlgorithm == i - j + 1)        // 혹시 모든 그룹원이 알고 있다면
                        algoKnownAll++;     // algoKnownAll 증가
                }
            }
            // 효율성 최대값 확인.
            maxEfficiency = Math.max(maxEfficiency, (long) (allAlgo - algoKnownAll) * (i - j + 1));
        }
        // 최대 효율 값 출력.
        System.out.println(maxEfficiency);
    }
}