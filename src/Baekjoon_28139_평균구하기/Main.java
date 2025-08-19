/*
 Author : Ruel
 Problem : Baekjoon 28139번 평균 구하기
 Problem address : https://www.acmicpc.net/problem/28139
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28139_평균구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] points;

    public static void main(String[] args) throws IOException {
        // n개의 점이 주어진다.
        // 한 점에서 시작하여 모든 점을 방문하는 모든 경로의 길이의 평균을 내고자 한다.
        // 그 값은?
        //
        // 수학, 조합 문제
        // 모든 경로에 대해서 고려해야하므로
        // 모든 점에서 다른 모든 점에 이르는 거리를 더한다.
        // 각 점에서 자신을 제외한 다른 점을 잇는 n-1개의 각선이 추가되는 것이다.
        // 각 간선이 전체 경우의 수에 등장하는 횟수는 같으므로
        // n개로 나누어주면, 모든 경로에 대해 n-1개 간선의 길이의 평균이 되고 이것이 답니다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 점
        int n = Integer.parseInt(br.readLine());
        points = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < points.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < points[i].length; j++)
                points[i][j] = Integer.parseInt(st.nextToken());
        }

        double sum = 0;
        // i -> j나 j -> i나 값은 같으므로 중복을 피해 계산한다.
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++)
                sum += calcDistance(i, j) * 2;
        }
        // 모든 점에 대해 n-1개의 다른 점을 잇는 간선을 길이를 더했다.
        // 이를 n으로 나누어주면 모든 경로에 대해 n-1개의 간선의 길이 합의 평균이 된다.
        System.out.println(sum / n);
    }
    
    // idx1, idx2번째 점 사이의 거리를 계산
    static double calcDistance(int idx1, int idx2) {
        return Math.sqrt(Math.pow(points[idx1][0] - points[idx2][0], 2) + Math.pow(points[idx1][1] - points[idx2][1], 2));
    }
}