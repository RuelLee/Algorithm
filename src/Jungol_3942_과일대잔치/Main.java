/*
 Author : Ruel
 Problem : Jungol 3942번 과일 대잔치
 Problem address : https://jungol.co.kr/problem/3942
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3942_과일대잔치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 오렌지를 하나 먹으면 a만큼, 레몬을 하나 먹으면 b만큼 포만감이 증가한다.
        // 포만감은 t를 넘을 수 없으며, 1회에 한해 현재 포만감을 반으로 줄일 수 있다.
        // 만들 수 있는 최대 포만감은?
        //
        // t도 최대 500만으로 그리 크지 않으므로
        // 가능한 모든 포만감을 만들어가며 계산하면 된다.
        // 0부터 증가하며 가능한 포만감을 a를 더한 경우, b를 더한 경우, 그리고 현재 포만감에 반을 낮춘 경우를 체크해주고
        // 다시 한 번, 0부터 살펴보며 이번에는 포만감을 반으로 낮추는 경우는 만들지 않으며 a, b 경우만 따져가며 체크한다.
        // 그 후, 가장 t에 가장 가까운 값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 최대 포만감 t, 오렌지 a, 레몬 b
        int t = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        // 가능한 포만감 수치
        boolean[] possible = new boolean[t + 1];
        // 0은 기본
        possible[0] = true;
        for (int i = 0; i < t; i++) {
            // i 포만감이 불가능할 때는 그냥 건너뛴다.
            if (!possible[i])
                continue;

            // 가능한 경우는 반으로 낮추는 경우도 생각.
            possible[i / 2] = true;
            // 현재 포만감에서 오렌지와 레몬을 먹는 경우
            if (i + a <= t)
                possible[i + a] = true;
            if (i + b <= t)
                possible[i + b] = true;
        }

        // 위 경우에서 반을 낮추는 경우 또한 모두 계산하였으므로
        // 이제 해당 경우에서 오렌지와 레몬을 먹는 경우를 계산
        for (int i = 0; i < t; i++) {
            if (!possible[i])
                continue;

            if (i + a <= t)
                possible[i + a] = true;
            if (i + b <= t)
                possible[i + b] = true;
        }

        // t에 가장 가까운 포만감 수치를 찾는다.
        int answer = 0;
        for (int i = t; i >= 0; i--) {
            if (possible[i]) {
                answer = i;
                break;
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}