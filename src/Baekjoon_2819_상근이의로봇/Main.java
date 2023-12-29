/*
 Author : Ruel
 Problem : Baekjoon 2819번 상근이의 로봇
 Problem address : https://www.acmicpc.net/problem/2819
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2819_상근이의로봇;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[][] fenwickTree;

    public static void main(String[] args) throws IOException {
        // (0, 0)에 로봇이 위치하고 있으며, n개의 위치에 조사점이 주어진다.
        // 로봇은 m번의 명령에 따라 상하좌우로 이동하며
        // 이동할 때마다 각 조사점에서는 맨해튼 거리를 측정한다.
        // 이동마다 조사점들의 맨해튼 거리 합을 출력하라
        //
        // 누적합 문제
        // n이 최대 10만, m이 최대 30만으로 주어지므로
        // 매번 모든 조사점의 맨해튼 거리를 측정하는 것은 불가능하다.
        // 따라서 초기 상태의 x, y의 맨해튼 거리를 각각 측정한 후
        // 각 이동에 따라 변화하는 양만큼을 누적합을 통해 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 조사점, m개의 명령
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 펜윅트리를 통해 누적합 계산
        fenwickTree = new long[2][2_000_001];
        // x와 y의 맨해튼 거리 각각 측정
        long xDistanceSum = 0;
        long yDistanceSum = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) + 1_000_000;
            int y = Integer.parseInt(st.nextToken()) + 1_000_000;

            xDistanceSum += Math.abs(x - 1_000_000);
            yDistanceSum += Math.abs(y - 1_000_000);
            inputValue(0, x);
            inputValue(1, y);
        }

        String order = br.readLine();
        // 처음 위치는 (0, 0)이다.
        // 하지만 좌표의 값이 절대값 100만 이하로 주어지므로
        // +100만을 해 모두 양수로 만들어준 상태로 시작한다.
        int x = 1_000_000;
        int y = 1_000_000;
        StringBuilder sb = new StringBuilder();
        // m개의 명령 처리
        for (int i = 0; i < m; i++) {
            switch (order.charAt(i)) {
                // S일 경우 위로 이동
                case 'S' -> {
                    // 현재 y보다 같거나 작은 조사점의 개수를 세어
                    // 같거나 작은 경우에는 거리가 1 증가하고
                    // 큰 조사점들에 한해서는 거리가 1 감소한다.
                    int smallerThanY = countToIdx(1, y++);
                    yDistanceSum += 2 * smallerThanY - n;
                }
                // J일 경우 아래로 이동
                case 'J' -> {
                    // y보다 작은 조사점의 개수를 센다.
                    // 작은 경우에는 거리가 1 감소하고
                    // 크거나 같은 경우에는 거리가 1 증가한다.
                    int smallerThanY = countToIdx(1, --y);
                    yDistanceSum += n - 2 * smallerThanY;
                }
                // I일 경우 오른쪽으로 이동
                case 'I' -> {
                    int smallerThanX = countToIdx(0, x++);
                    xDistanceSum += 2 * smallerThanX - n;
                }
                // Z일 경우 왼쪽으로 이동
                case 'Z' -> {
                    int smallerThanX = countToIdx(0, --x);
                    xDistanceSum += n - 2 * smallerThanX;
                }
            }
            // 현재 맨해튼 거리 기록
            sb.append(xDistanceSum + yDistanceSum).append("\n");
        }
        
        // 전체 답안 출력
        System.out.print(sb);
    }

    static void inputValue(int xy, int idx) {
        while (idx < fenwickTree[xy].length) {
            fenwickTree[xy][idx]++;
            idx += (idx & -idx);
        }
    }

    static int countToIdx(int xy, int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[xy][idx];
            idx -= (idx & -idx);
        }
        return sum;
    }
}