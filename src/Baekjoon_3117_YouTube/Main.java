/*
 Author : Ruel
 Problem : Baekjoon 3117번 YouTube
 Problem address : https://www.acmicpc.net/problem/3117
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3117_YouTube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생에 대해 처음 볼 동영상의 번호가 주어진다.
        // k개의 동영상에 대해 해당 동영상 이후 다음 플레이되는 동영상의 정보가 주어진다.
        // m분이 흘렀을 때, n명의 학생들이 보고 있는 동영상들을 구하라.
        // 동영상은 모두 1분이다.
        //
        // 희소 배열 문제
        // 희소 배열을 통해, 해당 동영상의 2^i분 뒤에 보고 있는 영상들을 계산해두고
        // 이를 통해 연산을 줄이자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 희소 배열.
        // m분 뒤의 영상을 구해야하므로, m보다 작은 2^i을 구해, 해당 값까지만 희소배열을 통해 계산해두자.
        int[][] sparseArray = new int[k + 1][(int) (Math.log(m) / Math.log(2)) + 1];
        // 학생들이 처음 시청하는 동영상.
        int[] students = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // i번 동영상 뒤에 재생되는 동영상이 주어진다.
        // 1분 뒤 영상이므로, 2^0, sparseArray[i][0]에 저장한다.
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < sparseArray.length; i++)
            sparseArray[i][0] = Integer.parseInt(st.nextToken());

        // i - 1번째 희소배열을 통해 i번째 희소배열을 구한다.
        for (int i = 1; i < sparseArray[0].length; i++) {
            for (int j = 0; j < sparseArray.length; j++)
                sparseArray[j][i] = sparseArray[sparseArray[j][i - 1]][i - 1];
        }

        // 각 학생별로 m분 뒤 시청하는 영상을 구한다.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < students.length; i++) {
            // 남은 시간.
            int remainMinutes = m - 1;
            // 현재 보고 있는 동영상.
            int currentVideo = students[i];
            // 남은 시간이 0보다 큰 동안.
            while (remainMinutes > 0) {
                // 남은 시간보다 작지만 가장 큰 2의 제곱을 구해, 희소배열을 통해
                // 해당 값만큼의 시간을 줄이낟.
                int jump = (int) (Math.log(remainMinutes) / Math.log(2));
                // 해당 시간만큼을 remainMinutes에서 빼준다.
                remainMinutes -= Math.pow(2, jump);
                // 그리고 그 때 동영상은 희소배열을 통해 구한다.
                currentVideo = sparseArray[currentVideo][jump];
            }
            // 0분이 되어 반복문을 나왔고, 이 때 동영상이 i번 학생이 m분 뒤 보고 있는 동영상이다.
            sb.append(currentVideo).append(" ");
        }
        // 모든 학생들이 보고 있는 동영상 번호를 출력한다.
        System.out.println(sb);
    }
}