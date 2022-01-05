/*
 Author : Ruel
 Problem : Baekjoon 2141번 우체국
 Problem address : https://www.acmicpc.net/problem/2141
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 우체국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Village {
    int loc;
    int pop;

    public Village(int loc, int pop) {
        this.loc = loc;
        this.pop = pop;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 각 마을의 위치와 인구수가 주어진다
        // 어느 마을에 우체국을 짓는 것이 모든 사람들이 우체국을 갈 때의 거리 합이 최소가 될 수 있는지 구하라
        // 왼쪽에서부터 우체국을 지을 때, 해당 마을보다 왼쪽에 있는 인원이 우체국에 도달하는 거리를 구한다
        // 다음으로 오른쪽에서부터 우체국을 지을 때, 해당 마을보다 오른쪽에 있는 인원이 우체국에 도달하는 거리를 구한다
        // 최종적으로 같은 위치에서 합이 적은 곳에 우체국을 세우면 된다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        Village[] villages = new Village[n];
        StringTokenizer st;
        for (int i = 0; i < villages.length; i++) {
            st = new StringTokenizer(br.readLine());
            villages[i] = new Village(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(villages, Comparator.comparingInt(o -> o.loc));

        // 왼쪽에서부터 우체국을 짓기 시작한다.
        long[] fromLeft = new long[n];
        // 첫번째 마을에 세울 경우는 자신보다 왼쪽 마을이 없으므로 0
        // 두번째 마을을 위해 첫번째 마을의 인구만 popSum에 더해주자.
        long popSum = villages[0].pop;
        // 두번째(idx = 1) 마을부터 시작한다
        // i 마을에 우체국을 세울 경우, 자신보다 왼쪽에 있는 마을들에 있는 인원이 해당 마을에 도착하는 총 거리는
        // (i - 1) 마을에 우체국을 세울 때 경우 + 자신보다 왼쪽에 있는 인원 수(i - 1보다 i마을에 세울 경우, 마을원들이 모두 거리가 1씩 늘어나는 셈이므로)
        for (int i = 1; i < villages.length; i++) {
            fromLeft[i] += fromLeft[i - 1] + popSum;
            popSum += villages[i].pop;
        }

        // 오른쪽에서부터도 해준다.
        long[] fromRight = new long[n];
        popSum = villages[villages.length - 1].pop;
        for (int i = villages.length - 2; i >= 0; i--) {
            fromRight[i] += fromRight[i + 1] + popSum;
            popSum += villages[i].pop;
        }

        // 합이 최소인 곳을 찾고, 그 때의 마을 위치를 기록해준다.
        long distanceSum = Long.MAX_VALUE;
        int idx = 0;
        for (int i = 0; i < fromLeft.length; i++) {
            if (distanceSum > fromLeft[i] + fromRight[i]) {
                distanceSum = fromLeft[i] + fromRight[i];
                idx = villages[i].loc;
            }
        }
        System.out.println(idx);
    }
}