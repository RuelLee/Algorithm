/*
 Author : Ruel
 Problem : Jungol 21995번 완벽한 오트밀죽
 Problem address : https://jungol.co.kr/problem/21995
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_21995_완벽한오트밀죽;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생과 각 학생이 인내할 수 있는 시간 i가  주어진다.
        // 죽을 2분마다 만들 수 있고, m분이 지나면 식는다고 한다.
        // 또한 식은 죽은 1분을 들여 재가열할 수 있다고 한다.
        // 최대한 많은 학생들에게 동시에 죽을 내어주려고 할 때, 동시에 줄 수 있는 가장 많은 죽의 수는?
        //
        // 그리디, 정렬 문제
        // 먼저 정렬을 해, 오름차순으로 학생들을 살펴보며, 해당 학생을 기준으로 죽을 만들어 내놓을 때, 먹을 수 있는 사람의 수를 구한다.
        // t분까지 기다릴 수 있는 학생을 기준으로 잡으면 math.max(0, t - m) ~ t까지 죽을 만들어 t에 내놓아야한다.
        // 그러면 죽이 식는 시간인 m이내에 가능한 많은 죽을 만들어야한다.
        // 죽을 처음 만들면 2분, 재가열하면 1분의 시간이 소모되므로, 가능한 많은 죽을 m분 이전에 만들어두면 시간을 절약할 수 있다.
        // 따라서 다시 타임라인 기준으로 생각해보면
        // 0 ~ t - m -> 가능한 많은 죽을 미리 만들어둔다.
        // t-m ~ t -> 만들어둔 죽을 재가열하거나 새로 만든다.
        // t -> 내놓는다.
        // 예외로 고려해야하는 점들이 있다.
        // 1. t- m이 0보다 작은 경우
        // 2. 미리 만들어둔 죽이 아무리 많아도 m분 동안 재가열하므로 최대 m개의 죽을 서빙할 수 있다.
        // 3. 아무리 많은 죽을 만들어도 현재 학생 ~ 이후 학생까지만 서빙할 수 있다. 이전 학생들은 제외해야한다.
        // 4. 만약 0 ~ t-m까지의 시간 중 1분이 남고, t-m ~ t까지도 1분이 남는다면 죽을 하나 조리하는 것이 가능하다.
        // 위 예외들을 고려하여 계산하자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, 죽이 식는 시간 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 학생들을 최대 대기시간에 맞춰 오름차순 정렬
        // 중복되는 시간이 있을 수 있다.
        int[] students = new int[n];
        for (int i = 0; i < n; i++)
            students[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(students);

        // 최대 서빙 수
        int max = 0;
        // 마지막으로 계산된 학생의 시간
        int lastTime = -1;
        for (int i = 0; i < n; i++) {
            // 마지막으로 계산된 학생과 동일한 시간이라면, 더 적은 학생 수로 다시 계산할 필요 없다.
            if (lastTime == students[i])
                continue;
            // 마지막 계산 시간 갱신
            lastTime = students[i];

            // 미리 죽을 만들어 둘 수 있는 시간
            int preTime = Math.max(0, students[i] - m);
            // 최대한 조리. 최대 개수는 m을 넘을 수 없다.
            int preCook = Math.min(preTime / 2, m);
            // 만약 m개를 조리했다면 잔여 시간은 0, 아닌 경우. 0 혹은 1의 잔여 시간이 남는다.
            preTime = preCook == m ? 0 : (preTime - preCook * 2);
            // m분 혹은 i번 학생의 대기 시간 중 짧은 시간 동안, 미리 선 조리해둔 죽들을 재가열하고
            // + 선조리에서 1분이 남았다면 그 시간을 더해 죽들을 조리한다.
            int canServe = (Math.min(students[i], m) - preCook + preTime) / 2 + preCook;
            // 현재 마련 가능한 죽의 수 canServe와 학생의 수를 비교하여 서빙 가능한 죽의 수를 계산
            max = Math.max(max, Math.min(canServe, n - i));
        }
        // 답 출력
        System.out.println(max);
    }
}