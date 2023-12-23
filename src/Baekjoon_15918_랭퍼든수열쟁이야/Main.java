/*
 Author : Ruel
 Problem : Baekjoon 15918번 랭퍼든 수열쟁이야!!
 Problem address : https://www.acmicpc.net/problem/15918
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15918_랭퍼든수열쟁이야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n의 수가 주어졌을 때
        // 각각의 수를 두번씩 사용하여 2n 길이의 수열을 만들고자 한다.
        // 이 때 수열을 x와 x 사이에는 x개의 수가 들어가야한다.
        // 예를 들어 3 1 2 1 3 2는 n이 3인 수열이다.
        // 그리고 하나의 조건을 추가하여 x번째 수와 y번째 수는 같아야한다.
        // 그러한 수열은 모두 몇 가지인가?
        //
        // 브루트 포스, 백트래킹 문제
        // n이 12로 크지 않으므로 브루트 포스를 통해 계산할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n
        int n = Integer.parseInt(st.nextToken());
        // x, y번째 수는 서로 같아야한다.
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        // 답안 출력
        System.out.println(countArrays(0, new int[2 * n], new boolean[n + 1], x, y));
    }

    // 브루트 포스, 백트래킹
    static int countArrays(int picked, int[] array, boolean[] selected, int x, int y) {
        // y번째까지 수열이 만들어졌을 때, x, y번째 수가 서로 일치하는지 확인하고
        // 그렇지 않다면 더 이상 진행하지 않고 0 반환
        if (picked == y && array[x - 1] != array[y - 1])
            return 0;
        else if (picked == array.length)        // 수열에 수 배치가 끝났다면 1 반환
            return 1;
        else if (array[picked] != 0)        // 현재 칸에 0이 아니라면 이전에 수를 배치함으로 같이 배치된 경우. 순서로 넘어간다.
            return countArrays(picked + 1, array, selected, x, y);

        // 파생되는 경우의 수를 센다.
        int sum = 0;
        for (int i = 1; i < selected.length; i++) {
            // i번째 수를 아직 사용하지 않았고, 현재 위치로부터 i + 1만큼 떨어진 위치에 수를 배치할 수 있는지 살펴본 후
            if (!selected[i] && picked + i + 1 < array.length && array[picked + i + 1] == 0) {
                // 가능하다면 배치하고 진행한다.
                selected[i] = true;
                array[picked] = array[picked + i + 1] = i;
                sum += countArrays(picked + 1, array, selected, x, y);

                // 위에 해당하는 조건의 경우의 수를 계산했다면
                // 값을 복구해 다음 경우의 수 계산에 사용한다.
                selected[i] = false;
                array[picked] = array[picked + i + 1] = 0;
            }
        }
        // 경우의 수 반환.
        return sum;
    }
}