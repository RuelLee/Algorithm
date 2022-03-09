/*
 Author : Ruel
 Problem : Baekjoon 5573번 산책
 Problem address : https://www.acmicpc.net/problem/5573
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5573_산책;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int h, w;

    public static void main(String[] args) throws IOException {
        // 세로 h, 가로 w의 맵이 주어진다
        // 각 길에는 다음으로 나아갈 방향을 나타내는 표지판이 있다. 방향은 오른쪽 혹은 아래쪽이다
        // 산책을 할 때마다 표지판에 있는 대로 방향으로 나아가되, 한번 지나갈 때마다 표지판을 바꾼다고 한다(아래쪽 -> 오른쪽, 오른쪽 -> 아래쪽)
        // 산책은 오른쪽 끝이나 아래쪽 끝에 도착하면 끝난다고 한다
        // n번째 산책을 했을 때, 도달하는 위치가 어디인가
        //
        // n이 최대 천만까지 주어지므로 직접 시뮬레이션하는 건 말도 안된다
        // 우리는 각 지점에 도착했을 때 몇번째 도착했는지를 가지고서 몇번을 아래쪽으로 갈 것인지, 몇번을 오른쪽으로 갈 것인지 정할 수 있다
        // 예를 들어 0, 0 지점에 표지판이 아래쪽으로 표시되어있고, n이 101이라면
        // 51번은 아래쪽으로 나아가고, 50번은 오른쪽으로 나아갈 것이다
        // 따라서 n - 1번에 대해서 DP를 채우면서 표지판 값을 수정하고서
        // n번째는 직접 표지판을 따라가도록 하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[][] map = new int[h][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 해당 지점의 방문 횟수
        int[][] visitTimes = new int[h][w];
        // 0, 0지점에서 n - 1번 시작한다.
        visitTimes[0][0] = n - 1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (visitTimes[i][j] % 2 == 0) {        // 만약 방문 횟수가 짝수라면
                    if (checkArea(i + 1, j))        // 아래쪽으로는 visitTimes[i][j] / 2 번 진행하고
                        visitTimes[i + 1][j] += visitTimes[i][j] / 2;
                    if (checkArea(i, j + 1))        // 오른쪽으로도 visitTimes[i][j] / 2 번 진행한다.
                        visitTimes[i][j + 1] += visitTimes[i][j] / 2;
                } else {        // 방문 횟수가 홀수라면
                    if (map[i][j] == 0) {       // 표지판이 아래쪽으로 표시되어 있다면
                        if (checkArea(i + 1, j))        // 아래쪽으로는 visitTimes[i][j] / 2 보다 한 번 더 진행하고
                            visitTimes[i + 1][j] += (visitTimes[i][j] + 1) / 2;
                        if (checkArea(i, j + 1))        // 오른쪽으로는 visitTimes[i][j] / 2 값 만큼 진행한다.
                            visitTimes[i][j + 1] += visitTimes[i][j] / 2;
                    } else {        // 표지판이 오른쪽으로 표시되어 있다면
                        if (checkArea(i + 1, j))        // 아래쪽으로는 visitTimes[i][j] / 2 번 진행하고
                            visitTimes[i + 1][j] += visitTimes[i][j] / 2;
                        if (checkArea(i, j + 1))        // 오른쪽으로는 visitTimes[i][j] / 2 보다 한 번 더 진행한다.
                            visitTimes[i][j + 1] += (visitTimes[i][j] + 1) / 2;
                    }
                    // 그리고 홀수번 방문이라면 표지판의 방향도 바뀐다.
                    map[i][j] = (map[i][j] + 1) % 2;
                }
            }
        }

        // 마지막 n번째는 직접 표지판을 따라서 간다.
        int r = 0;
        int c = 0;
        while (r < map.length && c < map[r].length) {
            if (map[r][c] == 0)     // 표지판이 아래쪽이라면 r 증가
                r++;
            else        // 오른쪽이라면 c 증가
                c++;
        }
        // 최종적으로 나오는 (r + 1)과 (c + 1)이 정답.
        System.out.println((r + 1) + " " + (c + 1));
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}