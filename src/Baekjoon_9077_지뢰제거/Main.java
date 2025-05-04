/*
 Author : Ruel
 Problem : Baekjoon 9077번 지뢰제거
 Problem address : https://www.acmicpc.net/problem/9077
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9077_지뢰제거;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // 10000 * 10000의 작업장에 최대 10만개의 지뢰가 묻혀있다.
        // 한 칸에는 최대 1개의 지뢰가 묻혀있고, 10 * 10 크기 공간의 지뢷르을 한 번에 제거할 수 있다.
        // 한 번에 제거할 수 있는 가장 많은 지뢰의 수는?
        //
        // 누적합, 좌표 압축 문제
        // 그냥 누적합 처리를 하려면, 10만 * 10만으로 1억개의 int 공간이 필요하므로
        // 좌표 압축을 통해 누적합 처리를 해주고
        // 그 값을 바탕으로 10 * 10 크기의 공간에서 제거 가능한 지뢰들의 최대 개수를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            // n개의 지뢰
            int n = Integer.parseInt(br.readLine());
            int[][] points = new int[n + 1][4];
            points[0][0] = points[0][1] = -1;
            for (int i = 1; i < points.length; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 2; j++)
                    points[i][j] = Integer.parseInt(st.nextToken());
            }
            
            // 먼저 y좌표 압축
            List<Integer> ypos = new ArrayList<>();
            ypos.add(-1);
            Arrays.sort(points, Comparator.comparingInt(o -> o[1]));
            for (int i = 1; i < points.length; i++) {
                if (points[i][1] != points[i - 1][1]) {
                    points[i][3] = ypos.size();
                    ypos.add(points[i][1]);
                } else
                    points[i][3] = points[i - 1][3];
            }
            // x좌표 압축
            List<Integer> xpos = new ArrayList<>();
            xpos.add(-1);
            Arrays.sort(points, Comparator.comparingInt(o -> o[0]));
            for (int i = 1; i < points.length; i++) {
                if (points[i][0] != points[i - 1][0]) {
                    points[i][2] = xpos.size();
                    xpos.add(points[i][0]);
                } else
                    points[i][2] = points[i - 1][2];
            }
            
            // 누적합 처리
            int[][] psums = new int[xpos.size()][ypos.size()];
            for (int i = 1; i < points.length; i++)
                psums[points[i][2]][points[i][3]] = 1;
            for (int i = 1; i < psums.length; i++) {
                for (int j = 1; j < psums[i].length; j++)
                    psums[i][j] += psums[i - 1][j] + psums[i][j - 1] - psums[i - 1][j - 1];
            }

            int answer = 0;
            // 각 지뢰를 기준으로
            for (int i = 1; i < points.length; i++) {
                // 현재 지뢰가 10 * 10 크기의 공간에서 어디에 위치하는지 정한다.
                // 10 * 10 공간에서 (xDiff, yDiff)에 위치할 때
                // 왼쪽 아래 끝 점과 오른쪽 위 끝 점의 좌표가 중요하다.
                for (int xDiff = 0; xDiff < 10; xDiff++) {
                    // 왼쪽 아래 끝 점의 x좌표를 구한다.
                    // 압축되어있으므로 꼭 맞는 값이 아닐 수 있다.
                    // 이 값은 i번 지뢰의 x좌표 -xDiff보다 작아야한다.
                    int leftDownXIdx = points[i][2];
                    while (leftDownXIdx > 0 && xpos.get(leftDownXIdx) >= points[i][0] - xDiff)
                        leftDownXIdx--;
                    // 오른쪽 이 끝 점의 x좌표를 구한다.
                    // 이 값은 i번 지뢰의 x좌표 - xDiff + 10보다 같거나 작아야한다.
                    int rightUpXIdx = points[i][2];
                    while (rightUpXIdx + 1 < xpos.size() && xpos.get(rightUpXIdx + 1) <= points[i][0] - xDiff + 10)
                        rightUpXIdx++;

                    // 마찬가지로 기준이 되는 두 점의 y좌표 값을 구한다.
                    for (int yDiff = 0; yDiff < 10; yDiff++) {
                        int leftDownYIdx = points[i][3];
                        while (leftDownYIdx > 0 && ypos.get(leftDownYIdx) >= points[i][1] - yDiff)
                            leftDownYIdx--;
                        int rightUpYIdx = points[i][3];
                        while (rightUpYIdx + 1 < ypos.size() && ypos.get(rightUpYIdx + 1) <= points[i][1] - yDiff + 10)
                            rightUpYIdx++;

                        // 두 끝점을 기준으로 내부에 있는 지뢰의 개수를 구한다.
                        answer = Math.max(answer, psums[rightUpXIdx][rightUpYIdx] - psums[leftDownXIdx][rightUpYIdx] - psums[rightUpXIdx][leftDownYIdx] + psums[leftDownXIdx][leftDownYIdx]);
                    }
                }
            }
            // 답 기록
            sb.append(answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}