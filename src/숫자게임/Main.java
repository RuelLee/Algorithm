/*
 Author : Ruel
 Problem : Baekjoon 2923번 숫자 게임
 Problem address : https://www.acmicpc.net/problem/2923
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 숫자게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] aCard;
    static int[] bCard;

    public static void main(String[] args) throws IOException {
        // A카드와 B카드가 지속적으로 주어진다
        // 그리고 매번 A카드와 B카드를 모두 쌍으로 만들고, 그 중 최대값을 쌍의 최대값이라고 하자
        // 쌍의 최대값을 최소로 하는 쌍을 만들고, 그 값을 출력하자
        // 만약 1 / 2, 5 / 3, 2 / 2 라고 값이 주어진다면
        // 첫번째에선 1과 2 뿐이니 3이 답이고, 두번째에선 1-3, 5-2가 쌍을 이뤄 7이 된다. 세번째는 1-3, 2-2, 5-2 가 짝을 이뤄 7이 된다
        // 카운팅 정렬이라는 것을 사용한다!
        // 주어지는 값들이 100이하라는 점을 갖고서, 배열에 각 숫자의 개수를 저장한다
        // 그 후 한 쪽 카드에서는 작은 값부터, 다른 한 쪽 카드에서는 큰 값부터 서로 매칭을 해가며, 최대값을 찾는다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        aCard = new int[100];
        bCard = new int[100];

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            aCard[Integer.parseInt(st.nextToken())]++;
            bCard[Integer.parseInt(st.nextToken())]++;
            sb.append(findMaxValue()).append("\n");
        }
        System.out.println(sb);
    }

    static int findMaxValue() {
        int aIdx = 0;       // a값은 0번 idx부터 시작. 오름차순으로 살펴본다
        int aCount = aCard[aIdx];
        int bIdx = 99;      // b값은 99번 idx부터 시작. 내림차순으로 살펴본다.
        int bCount = bCard[bIdx];

        int max = 0;
        while (aIdx < 99 && bIdx > 0) {
            while (aIdx < 99 && aCount == 0)        // 현재 a카드가 비어있다면 있는 곳을 찾아가 개수를 가져온다
                aCount = aCard[++aIdx];
            while (bIdx > 0 && bCount == 0)         // 현재 b카드가 비어있다면 있는 곳을 찾아가 개수를 가져온다
                bCount = bCard[--bIdx];

            if (aCount != 0 && bCount != 0) {
                max = Math.max(max, aIdx + bIdx);           // 두 종류의 카드 합이 최대값을 갱신했는지 살펴보고
                int minus = Math.min(aCount, bCount);       // 두 종류의 카드들 중 작은 개수만큼을 각각 빼준다.
                aCount -= minus;
                bCount -= minus;
            }
        }
        // 그리고 현재 상황에서 쌍의 최대값을 반환한다.
        return max;
    }
}