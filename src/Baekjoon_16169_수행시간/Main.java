/*
 Author : Ruel
 Problem : Baekjoon 16169번 수행 시간
 Problem address : https://www.acmicpc.net/problem/16169
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16169_수행시간;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 컴퓨터로 이루어진 시스템이 있으며
        // 각 컴퓨터는 계급과 동작 시간이 있다.
        // 제일 낮은 계급의 컴퓨터를 제외한 모든 컴퓨터는 자신보다 한 단계 낮은 계급의 모든 컴퓨터로부터
        // 정보를 받은 뒤 동작한다.
        // 모든 컴퓨터의 계급을 오름차순 정렬했을 때, 이웃한 컴퓨터 간의 계급 차는 0 or 1이다.
        // i, j번 컴퓨터 간의 전송 시간은 (i - j)의 제곱이다.
        // 제일 낮은 계급의 컴퓨터는 바로 동작을 시작한다.
        // 모든 컴퓨터가 동작을 마치면 종료된다.
        // 가장 낮은 계급은 1이다.
        // 시스템이 동작을 완료하는데 걸리는 시간은?
        //
        // 단순한 그래프 탐색 문제
        // 컴퓨터를 계급별로 분류한 뒤
        // 한 계급에 있는 모든 컴퓨터들에 자료 입력이 끝난 시간, 동작 시간을 계산하고
        // 그 후, 자신보다 위 계급에 있는 컴퓨터들에게 자료를 전송시켜, 위 계급에 있는 컴퓨터들의 입력 시간을 계산해준다.
        // 위 과정을 마지막 컴퓨터까지 반복한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 컴퓨터
        int n = Integer.parseInt(br.readLine());

        // n개의 컴퓨터로 만들 수 있는 계급의 개수는 최대 n개
        List<List<Integer>> grades = new ArrayList<>();
        for (int i = 0; i < n; i++)
            grades.add(new ArrayList<>());

        // 해당 컴퓨터의 자료 입력이 끝나는 시간
        int[] inputTimes = new int[n];
        // 동작 시간
        int[] runningTimes = new int[n];
        // 모든 동작이 완료된 시간
        int[] finishTimes = new int[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 계급
            int grade = Integer.parseInt(st.nextToken()) - 1;
            int runningTime = Integer.parseInt(st.nextToken());
            // 해당 계급에 i번 컴퓨터 추가
            grades.get(grade).add(i);
            runningTimes[i] = runningTime;
        }

        // 0번 계급(= 문제에서의 가장 낮은 계급)부터
        // 순차적으로 모든 계급을 살펴보며
        for (int i = 0; i < grades.size(); i++) {
            for (int com : grades.get(i)) {
                // 속한 각 컴퓨터의 동작 완료 시간을 계산
                finishTimes[com] = inputTimes[com] + runningTimes[com];

                // 그리고 위 계급 컴퓨터들에게 자료를 전송한다.
                if (i + 1 < grades.size() && !grades.get(i + 1).isEmpty()) {
                    // i+1 계급에 속한 컴퓨터는 i 계급에 속한 모든 컴퓨터에게서 자료를 입력받아야 동작을 시작하므로
                    // 가장 오래 걸리는 inputTime을 구해준다.
                    for (int nextCom : grades.get(i + 1))
                        inputTimes[nextCom] = Math.max(inputTimes[nextCom], finishTimes[com] + (int) Math.pow((nextCom - com), 2));
                }
            }
        }

        // 모든 컴퓨터에서 작업이 완료된 가장 늦은 시간을 출력한다.
        System.out.println(Arrays.stream(finishTimes).max().getAsInt());
    }
}