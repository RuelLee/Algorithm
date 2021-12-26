/*
 Author : Ruel
 Problem : Baekjoon 1027번 고층 건물
 Problem address : https://www.acmicpc.net/problem/1027
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 고층건물;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] buildings;

    public static void main(String[] args) throws IOException {
        // 기초적인 수학 직선의 기울기를 활용하여 풀었다
        // n개의 빌딩이 주어지고, 각 빌딩은 천장을 이은 선분으로 다른 빌딩을 볼 수 있다했을 때
        // 다른 빌딩을 가장 많이 볼 수 있는 빌딩을 찾고 그 때 볼 수 있는 다른 빌딩의 개수를 출력
        // 각 빌딩마다 좌/우를 보며, 좌로는 직선의 기울기가 감소하는 방향으로 ( | → / → ― → ＼ 와 같이)
        // 우로는 직선의 기울기가 증가하는 방향으로 ( | → ＼ → ― → / 와 같이)
        // 빌딩을 세어주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        buildings = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            buildings[i] = Integer.parseInt(st.nextToken());

        int max = 0;
        for (int i = 0; i < buildings.length; i++)
            max = Math.max(max, countCanSeeBuildingsFromN(i));
        System.out.println(max);
    }

    static int countCanSeeBuildingsFromN(int n) {
        double canSee = Double.MAX_VALUE;       // 현재 볼수 있는 마지막 빌딩의 기울기. 최대값에서 점점 낮아진다.
        int count = 0;
        for (int i = n - 1; i >= 0; i--) {
            double current = (double) (buildings[n] - buildings[i]) / (n - i);      // i번 빌딩과 n번 빌딩의 기울기를 구하고
            if (canSee > current) {     // 증가한다면
                count++;        // 개수를 세어주고
                canSee = current;       // canSee에 현재값 대입
            }
        }
        canSee = -Double.MAX_VALUE;     // 최소값으로 초기화해주고
        for (int i = n + 1; i < buildings.length; i++) {
            double current = (double) (buildings[n] - buildings[i]) / (n - i);      // i번 빌딩과의 기울기를 구해
            if (canSee < current) {     // 볼 수 있다면
                count++;        // 세어주고
                canSee = current;       // canSee에 값 대입
            }
        }
        return count;
    }
}