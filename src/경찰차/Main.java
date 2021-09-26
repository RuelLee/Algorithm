/*
 Author : Ruel
 Problem : Baekjoon 2618번 경찰차
 Problem address : https://www.acmicpc.net/problem/2618
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 경찰차;

import java.util.*;

class Point {
    int r;
    int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static final int MAX = 2_000_000;

    public static void main(String[] args) {
        // 완전탐색으로 하다간 2^w만큼의 계산을 해야해서 당연히 시간이 터진다
        // DP문제
        // DP문제는 어떻게 DP를 사용할지에 대해 알아내는 것이 제일 중요한 것 같다
        // dp[a][b] 는 가장 최근에 1번 경찰차는 a번째 사고를 해결했고, 2번 경찰차는 b번째 사고를 해결한 케이스들 중에 가장 적은 이동거리를 갖는 값으로 정의하자
        // dp[a][b]에 도달하면 Math.max(a, b) + 1 값이 다음 사건의 번호가 되고, dp[다음사건][b]와 dp[a][다음사건] 두가지 경우로 파생될 수 있다
        // 두 경우를 체크하고, 기존에 들어있던 거리보다 작은 값이 산출된다면 갱신해준다
        // 최종적으로 w번째 까지 문제를 해결했다면, dp의 가장 아래줄과 가장 오른쪽 줄이 모든 사건을 해결한 결과값들이 저장되어있을 것이다
        // 가장 적은 값을 찾고, 거꾸로 경로를 찾아가며 선택했던 경찰차 번호를 차자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int w = sc.nextInt();
        List<Point> incidents = new ArrayList<>();
        incidents.add(new Point(1, 1));     // 편의상 0번 사건에 A경찰차의 초기 위치를 넣는다.
        for (int i = 0; i < w; i++)
            incidents.add(new Point(sc.nextInt(), sc.nextInt()));

        int[][] dp = new int[w + 1][w + 1];
        for (int[] d : dp)
            Arrays.fill(d, MAX);
        dp[0][1] = calcDistance(new Point(n, n), incidents.get(1));     // 첫 사건을 B 경찰차가 맡은 경우
        dp[1][0] = calcDistance(incidents.get(0), incidents.get(1));    // 첫 사건을 A 경찰차가 맡은 경우
        Point[][] prePoints = new Point[w + 1][w + 1];          // 각 사건의 최소거리가 갱신될 때 어느 i, j에서 왔는지 저장해줄 것이다.
        prePoints[0][1] = prePoints[1][0] = new Point(0, 0);        // 두 경우 모두 0, 0에서 시작했다고 생각하자

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length - 1; j++) {
                if (i == j)
                    continue;
                int nextIncident = Math.max(i, j) + 1;      // 다음 사건은 i, j 중 큰 값 + 1
                if (dp[nextIncident][j] > dp[i][j] + calcDistance(incidents.get(i), incidents.get(nextIncident))) {     // 만약 1번 경찰차가 다음 사건을 맡았을 때 최소거리가 갱신된다면
                    dp[nextIncident][j] = dp[i][j] + calcDistance(incidents.get(i), incidents.get(nextIncident));       // 값 갱신
                    prePoints[nextIncident][j] = new Point(i, j);           // 해당 사건의 이전 기록에 현재 위치 기록
                }
                if (j == 0) {       // j가 0이라면, B경찰차가 아직 시작 위치라면
                    if (dp[i][nextIncident] > dp[i][j] + calcDistance(new Point(n, n), incidents.get(nextIncident))) {      // 2번 경찰차의 시작 위치인 n, n을 기준으로 계산한다
                        dp[i][nextIncident] = dp[i][j] + calcDistance(new Point(n, n), incidents.get(nextIncident));        // 최소거리값 갱신
                        prePoints[i][nextIncident] = new Point(i, j);           // 해당 사건의 이전 기록에 현재 위치 기록
                    }
                } else {
                    if (dp[i][nextIncident] > dp[i][j] + calcDistance(incidents.get(j), incidents.get(nextIncident))) {     // 그 외의 경우라면 incidents 리스트에 들어있는 값을 그냥 사용하면 된다.
                        dp[i][nextIncident] = dp[i][j] + calcDistance(incidents.get(j), incidents.get(nextIncident));
                        prePoints[i][nextIncident] = new Point(i, j);
                    }
                }
            }
        }
        // 가장 작은 거리의 값 찾기
        int minDistance = Integer.MAX_VALUE;
        Point point = null;
        for (int i = 0; i < dp.length - 1; i++) {
            if (dp[i][dp[i].length - 1] < minDistance) {
                minDistance = dp[i][dp[i].length - 1];
                point = new Point(i, dp[i].length - 1);
            }
        }
        for (int j = 0; j < dp[dp.length - 1].length - 1; j++) {
            if (dp[dp.length - 1][j] < minDistance) {
                minDistance = dp[dp.length - 1][j];
                point = new Point(dp.length - 1, j);
            }
        }

        // 가장 작은 거리의 값을 가진 dp위치로부터 거꾸로 0,0까지 찾아가며, 선택된 경찰차들을 stack 에 담는다
        Stack<Integer> stack = new Stack();
        while (!(point.r == 0 && point.c == 0)) {
            if (prePoints[point.r][point.c].r == point.r)       // 만약 이전의 r값과 현재 r값이 같다면, 2번 경찰차가 선택된 경우
                stack.push(2);
            else        // r값이 달라졌다면 1번 경찰차가 선택된 경우.
                stack.push(1);
            point = prePoints[point.r][point.c];
        }
        StringBuilder sb = new StringBuilder();
        sb.append(minDistance).append("\n");
        while (!stack.isEmpty())
            sb.append(stack.pop()).append("\n");
        System.out.println(sb);
    }

    static int calcDistance(Point a, Point b) {
        return Math.abs(a.r - b.r) + Math.abs(a.c - b.c);
    }
}