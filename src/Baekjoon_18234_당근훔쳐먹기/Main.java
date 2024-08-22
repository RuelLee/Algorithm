/*
 Author : Ruel
 Problem : Baekjoon 18234번 당근 훔쳐 먹기
 Problem address : https://www.acmicpc.net/problem/18234
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18234_당근훔쳐먹기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 오리는 n 종류의 당근을 심고 t일 동안 재배할 예정이다.
        // 각 당근은 처음에 wi만큼의 맛을 갖고 있고,
        // 매일 각 당근에게 정해진 서로 다른 영양제를 주어, pi만큼씩 맛이 상승한다.
        // 오리는 매일 오전에 해당 자리에 당근이 있다면 영양제를 주고, 없다면 해당하는 당근을 심는다.
        // 토끼는 오후에 나타나, 당근을 먹을수도, 먹지 않을 수도 있다.
        // 토끼가 t일 동안 먹은 당근들 맛의 합이 최대가 되게끔하고자할 때
        // 토끼가 먹은 당근들 맛의 합은?
        //
        // 그리디, 정렬 문제
        // wi보다 pi가 항상 크다.
        // 따라서 무조건 당근은 영양제를 최대한 받은 후 먹는게 좋다.
        // 하지만 t일이라는 조건이 주어진다.
        // 따라서 가장 pi가 큰 당근은 마지막날, 두번째 pi가 큰 날은 마지막이 되기 전날 ...
        // 가장 pi가 작은 당근은 t - n + 1번째 날에 먹는 것이 좋다.
        // 토끼는 t - n일까지 아무것도 먹지 않고서, t - n + 1번째 날부터 가장 pi가 적은 당근부터 먹는 것이 유리하다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n종류의 당근을 t일 동안 재배한다.
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 당근들
        int[][] carrots = new int[n][2];
        for (int i = 0; i < carrots.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < carrots[i].length; j++)
                carrots[i][j] = Integer.parseInt(st.nextToken());
        }

        // 영양제에 따라 오름차순 정렬
        Arrays.sort(carrots, Comparator.comparingInt(o -> o[1]));
        long sum = 0;
        // t - n + i + 1번째 날 i번 당근을 먹는다
        // 해당 때까지 투여된 영양제의 개수는 t-n+i개
        for (int i = 0; i < carrots.length; i++)
            sum += carrots[i][0] + carrots[i][1] * (long) (t - n + i);

        // 답안 출력
        System.out.println(sum);
    }
}