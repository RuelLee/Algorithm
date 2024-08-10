/*
 Author : Ruel
 Problem : Baekjoon 9007번 카누 선수
 Problem address : https://www.acmicpc.net/problem/9007
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9007_카누선수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어느 학교에 각 n명으로 구성된 4개의 카누반이 있다.
        // 각 반에서 한명씩 뽑아 대표팀을 꾸리고자 한다.
        // 각 반에서 선출된 네 명의 선수 몸무게 합이 k에 가까울수록 최대 성과를 낼 수 있다고 한다.
        // 만약 차이가 같다면 몸무게 합이 더 적은 쪽이 유리하다.
        // 해당하는 네 학생의 몸무게 합을 출력하라
        //
        // 정렬, 이분탐색 문제
        // n이 최대 1000까지 주어지므로 각각을 모두 고려한다면
        // 1000^4의 시간이 들어간다
        // 하지만 두 반씩 묶어, 가능한 몸무게 합을 구하고
        // 한 반에 대해서는 이분탐색을 통해 가장 적당한 몸무게를 찾는다면
        // 연산을 줄일 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 최대 성과를 낼 수 있는 몸무게 합 k
            int k = Integer.parseInt(st.nextToken());
            // 각 반의 학생 수 n
            int n = Integer.parseInt(st.nextToken());

            int[][] students = new int[4][n];
            for (int i = 0; i < students.length; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < students[i].length; j++)
                    students[i][j] = Integer.parseInt(st.nextToken());
            }

            // 두반씩 묶어 몸무게 합을 구한다.
            int[][] sums = new int[2][n * n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    sums[0][i * n + j] = students[0][i] + students[1][j];
                    sums[1][i * n + j] = students[2][i] + students[3][j];
                }
            }
            // sums[1]에 대해서 이분탐색을 진행할 것이므로 정렬.
            Arrays.sort(sums[1]);

            int answer = Integer.MAX_VALUE;
            // sums[0]에 속한 모든 몸무게 합에 대해
            for (int firstSum : sums[0]) {

                // sums[1]에서 이분탐색을 통해 가장 합이 k이 근사한 위치를 찾는다.
                int start = 0;
                int end = sums[1].length - 1;
                while (start < end) {
                    int mid = (start + end) / 2;
                    if (firstSum + sums[1][mid] < k)
                        start = mid + 1;
                    else
                        end = mid;
                }
                
                // 찾은 end 값은 네 명의 몸무게 합이 k와 같거나 큰 값이다.
                // 하지만 작은 경우에도 몸무게 차이는 더 적을 수도 있다.
                // 따라서 end 뿐만 아니라 end - 1도 같이 계산해준다.
                for (int i = end; i >= 0 && i >= end - 1; i--) {
                    int currentDiff = Math.abs(firstSum + sums[1][i] - k);
                    int answerDiff = Math.abs(answer - k);
                    if (answerDiff > currentDiff ||
                            (answerDiff == currentDiff && answer > firstSum + sums[1][i]))
                        answer = firstSum + sums[1][i];
                }
            }
            // 이번 테스트 케이스의 답 answer
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}