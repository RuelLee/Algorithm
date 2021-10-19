/*
 Author : Ruel
 Problem : Baekjoon 1321번 군인
 Problem address : https://www.acmicpc.net/problem/1321
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 군인;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // 세그먼트 트리와 이분탐색을 활용한 문제
        // 각 부대의 인원을 알고 있을 때, n번째 군인은 몇번째 부대에 속하는지 구하는 문제
        // 각 부대에 대해 인원을 알고 있으니 누적합을 구해 해당 부대에 몇번째 군인까지가 배속되는지를 구한다
        // 이를 이분탐색을 활용하여 부대를 검색해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        fenwickTree = new long[n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            inputValue(i + 1, Integer.parseInt(st.nextToken()));

        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            if (Integer.parseInt(st.nextToken()) == 1)      // 첫번째 값이 1이라면 군부대 인원 변동이 생긴다.
                inputValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            else        // 아니라면 특정 군번의 군인이 몇번째 부대에 속하는지 출력한다.
                sb.append(findTroopNum(Integer.parseInt(st.nextToken()))).append("\n");
        }
        System.out.println(sb);
    }

    static int findTroopNum(int serviceNumber) {        // 군번을 알고 있을 때 몇번째 부대에 속하는지 구하는 메소드.
        int start = 1;      // 시작은 1번
        int end = fenwickTree.length - 1;       // 끝은 n번
        while (start < end) {       // start < end 동안
            int middle = (start + end) / 2;
            int num = getLastNumOfTroop(middle);        // 중간값을 구해
            if (num < serviceNumber)        // 해당 부대에 속하는 가장 마지막 군번이 찾고 있는 군번보다 작다면
                start = middle + 1;         // 다음 start 값을 늘려 다음 부대부터 찾는다
            else
                end = middle;       // 아니라면 end 값을 줄여 이전 부대부터 찾는다.
        }
        return start;
    }

    static int getLastNumOfTroop(int troop) {       // 해당 부대의 마지막 군번을 출력한다.
        int sum = 0;        // 누적합이 곧 군번이 된다.
        while (troop > 0) {
            sum += fenwickTree[troop];
            troop -= (troop & -troop);
        }
        return sum;
    }

    static void inputValue(int loc, int value) {        // 부대에 변동 인원을 펜윅 트리에 적용한다.
        while (loc < fenwickTree.length) {
            fenwickTree[loc] += value;
            loc += (loc & -loc);
        }
    }
}