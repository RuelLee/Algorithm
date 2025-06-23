/*
 Author : Ruel
 Problem : Baekjoon 2650번 교차점개수
 Problem address : https://www.acmicpc.net/problem/2650
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2650_교차점개수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 직사각형 변 위에 여러 점이 있고, 두 점을 잇는 곡선 n개가 주어진다.
        // 두 점은 두 개의 수로 이루어지며, 
        // 첫번째 수는 윗변 1, 밑변 2, 왼쪽 변 3, 오른쪽 변 4
        // 두번째 수는 윗변이나 밑변의 경우 왼쪽 꼭짓점부터의 거리,
        // 왼쪽이나 오른쪽 변의 경우는 위쪽 꼭짓점부터의 거리를 나타낸다.
        // 어떤 세 곡선도 한 점에서 만나지 않는다할 때
        // 교차점의 최소 개수와, 가장 많은 교차점을 갖는 곡선의 교차점 개수를 출력하라
        //
        // 기하학 문제
        // ccw를 통하여 풀려하였으나 너무 복잡해지는 거 같아 다른 사람의 풀이를 참고하였다.
        // 사각형이 아닌 원형으로 보고, (1, 0)부터 시계방향으로 순서대로 점들에 번호를 할당하고
        // 두 곡선에 대해, 한 곡선의 서로 다른 두 점이 다른 한 곡선에 시계 방향, 반시계 방향에 각각 존재할 경우
        // 반드시 교차한다.
        // 두 점 모두 시계 방향 혹은 반시계 방향에 있다면 굳이 교차하지 않아도 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 점
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        // 만들어야하는 곡선은 n / 2개
        int[][] lines = new int[n / 2][2];
        for (int i = 0; i < lines.length; i++) {
            st = new StringTokenizer(br.readLine());
            // 두 점에 대해 번호를 할당.
            int a = toIdx(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            int b = toIdx(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            
            // 작은 번호를 0, 큰 번호를 1번에 저장
            lines[i][0] = Math.min(a, b);
            lines[i][1] = Math.max(a, b);
        }

        int[] counts = new int[n / 2];
        // i번째 곡선과
        for (int i = 0; i < lines.length; i++) {
            // j번째 곡선이 교차하는지 판별
            for (int j = i + 1; j < lines.length; j++) {
                // j의 두 점이 i 곡선의 시계 방향인지 반시계 방향인지
                int one = (lines[j][0] > lines[i][0] && lines[j][0] < lines[i][1] ? 1 : -1);
                int another = (lines[j][1] > lines[i][0] && lines[j][1] < lines[i][1] ? 1 : -1);

                // 두 점이 서로 다른 방향에 존재한다면 반드시 교차한다.
                if (one * another == -1) {
                    counts[i]++;
                    counts[j]++;
                }
            }
        }

        // 모든 교차점의 합
        int sum = 0;
        int max = 0;
        for (int i = 0; i < counts.length; i++) {
            sum += counts[i];
            max = Math.max(max, counts[i]);
        }
        // 만약 i 곡선과 j 곡선이 교차할 경우, i에서 1번, j에서 1번 총 2번 세어지게 된다.
        // 따라서 2로 나눈 값이 전체 교차점의 수
        System.out.println(sum / 2);
        // 가장 많은 교차점을 갖는 곡선의 교차점 수
        System.out.println(max);
    }

    // 주어진 두 값을 통해, 점에 번호를 할당한다.
    static int toIdx(int a, int b) {
        // 편의상 윗변 1, 오른쪽 변 2, 밑변 3, 왼쪽 변 4가 되도록 a값을 바꿔준다.
        if (a == 2)
            a = 3;
        else if (a == 3)
            a = 4;
        else if (a == 4)
            a = 2;

        // 한 변에 최대 51개의 점이 놓인다.
        // a가 윗변 혹은 오른쪽 변일 경우는 순서대로 b만큼 점의 번호가 증가하며
        if (a < 3)
            return (a - 1) * 51 + b;
        // 그 외의 경우는 (a - 1) * 51 + (50 - b)만큼 빠진 값이 된다.
        return a * 51 - b;
    }
}