/*
 Author : Ruel
 Problem : Baekjoon 19700번 수업
 Problem address : https://www.acmicpc.net/problem/19700
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19700_수업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n명의 학생의 키와 자신이 팀에서 원하는 키의 최소 등수가 주어진다.
        // n명을 팀으로 나누고자할 때, 최소 팀의 수는?
        //
        // 그리디, 이분 탐색 문제
        // 키를 내림차순으로 정렬한 뒤,
        // 키가 가장 큰 사람부터 팀에 배치하기 시작한다.
        // 키가 큰 사람부터 배치했으므로, 팀에 배치된 모든 사람들은 현재 배치하는 사람보다 키가 크다.
        // 따라서 팀원의 수가 자신이 원하는 최소 등수보다 적은 곳에 사람들을 배치해나가며
        // 팀의 개수를 센다.
        // 자신이 원하는 등수보다 적은 수를 갖는 팀을 찾을 때, 이분 탐색을 사용했다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 학생
        int n = Integer.parseInt(br.readLine());
        int[][] students = new int[n][];
        for (int i = 0; i < students.length; i++)
            students[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 내림차순
        Arrays.sort(students, (o1, o2) -> Integer.compare(o2[0], o1[0]));
        
        // 펜윅 트리
        fenwickTree = new int[500_001];
        // 팀의 개수
        int teamCount = 0;
        for (int i = 0; i < students.length; i++) {
            // 자신의 원하는 등수보다 적은 인원을 갖는 팀이 존재한다면
            if (countBelowN(students[i][1] - 1) > 0) {
                // 이분 탐색을 통해 해당 팀의 인원을 찾는다.
                int start = 1;
                int end = students[i][1] - 1;
                while (start < end) {
                    int mid = (start + end) / 2;
                    if (countBelowN(end) - countBelowN(mid) > 0)
                        start = mid + 1;
                    else
                        end = mid;
                }
                // end명의 팀에, 현재 사람을 추가시키므로
                // end명 팀의 개수 하나 감소
                putValue(end, -1);
                // end+1 명 팀의 개수 하나 증가
                putValue(end + 1, 1);
            } else {
                // 자신이 원하는 등수보다 적은 인원을 갖는 팀이 존재하지 않는다면
                // 팀 개수 증가
                teamCount++;
                // 한 명인 팀 증가
                putValue(1, 1);
            }
        }
        // 찾은 팀의 개수 출력
        System.out.println(teamCount);
    }

    // idx에 value 값을 추가.
    static void putValue(int idx, int value) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx] += value;
            idx += (idx & -idx);
        }
    }

    // n이하의 팀원을 갖는 팀의 개수를 찾는다.
    static int countBelowN(int n) {
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }
}