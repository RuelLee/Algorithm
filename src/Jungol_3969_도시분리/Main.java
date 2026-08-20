/*
 Author : Ruel
 Problem : Jungol 3969번 도시 분리
 Problem address : https://jungol.co.kr/problem/3969
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3969_도시분리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 시민의 각각의 집 위치가 주어진다. 주어지는 값은 홀수로 주어진다.
        // 짝수인 a, b로 x = a, y = b로 선을 그어 네 개의 구역으로 나누었을 때
        // 각각의 구역에 있는 집의 수가 최대한 비슷하게끔 하고자 한다.
        // 네 구역 중 가장 많은 집의 개수를 m이라고 할 때, 최소 m의 값은?
        //
        // 좌표 압축, 누적합 문제
        // 집의 위치가 최대 100만으로 주어지지만, n이 최대 1000으로 그리 크지 않다.
        // 따라서 좌표를 압축하여, 누적합으로 계산해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 집
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;

        // 좌표 압축
        PriorityQueue<Integer> xCompress = new PriorityQueue<>();
        PriorityQueue<Integer> yCompress = new PriorityQueue<>();
        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            points[i][0] = Integer.parseInt(st.nextToken());
            points[i][1] = Integer.parseInt(st.nextToken());
            xCompress.offer(points[i][0]);
            yCompress.offer(points[i][1]);
        }

        HashMap<Integer, Integer> xMap = new HashMap<>();
        while (!xCompress.isEmpty()) {
            int x = xCompress.poll();
            if (!xMap.containsKey(x))
                xMap.put(x, xMap.size() + 1);
        }
        HashMap<Integer, Integer> yMap = new HashMap<>();
        while (!yCompress.isEmpty()) {
            int y = yCompress.poll();
            if (!yMap.containsKey(y))
                yMap.put(y, yMap.size() + 1);
        }

        // 누적합
        int[][] psums = new int[xMap.size() + 1][yMap.size() + 1];
        for (int[] point : points)
            psums[xMap.get(point[0])][yMap.get(point[1])]++;

        for (int i = 1; i < psums.length; i++) {
            for (int j = 1; j < psums[i].length; j++)
                psums[i][j] += psums[i - 1][j] + psums[i][j - 1] - psums[i - 1][j - 1];
        }

        int ans = Integer.MAX_VALUE;
        // 모든 좌표에 대해 네 구역으로 나누어본다.
        for (int i = 1; i < psums.length; i++) {
            for (int j = 1; j < psums[i].length; j++) {
                // 현재 위치로부터 네 구역으로 나눠, 각각의 집의 수를 세고
                int leftUp = psums[i][j];
                int rightUp = psums[i][yMap.size()] - leftUp;
                int leftDown = psums[xMap.size()][j] - leftUp;
                int rightDown = psums[xMap.size()][yMap.size()] - leftUp - rightUp - leftDown;

                // 가장 큰 값과 ans을 비교하여 더 작은 값을 ans에 반영
                ans = Math.min(ans, Math.max(leftUp, Math.max(rightUp, Math.max(leftDown, rightDown))));
            }
        }
        // 답 출력
        System.out.println(ans);
    }
}