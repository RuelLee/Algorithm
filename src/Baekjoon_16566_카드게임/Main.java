/*
 Author : Ruel
 Problem : Baekjoon 16566번 카드 게임
 Problem address : https://www.acmicpc.net/problem/16566
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16566_카드게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] smallestBiggerThanN;
    static int[] cards;

    public static void main(String[] args) throws IOException {
        // n개의 범위 안의 카드 m개가 주어진다
        // 상대방이 k개의 카드를 순차적으로 내는데, 나는 이보다 큰 카드들 중 가장 작은 카드로 매번 이기고 싶다
        // 이 때 내가 내는 카드의 번호를 출력하라.
        // n과 m의 범위가 최대 400만으로 매우 크고, 카드를 내는 횟수 k도 최대 1만으로 주어진다.
        // 상대방이 내는 카드가 주어질 때, 내가 갖고 있는 카드 중 이보다 크지만 가장 작은 카드 값을 골라내고
        // 그리고 그 카드가 사용되었다면 다음 카드에 대해서 찾을 수 있어야한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        cards = new int[m];         // 내 카드패를 저장한다.
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < cards.length; i++)
            cards[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(cards);     // 이분 탐색을 사용하기 위해 정렬을 해둔다.
        smallestBiggerThanN = new int[m + 1];       // cards[idx]보다 크거나 같은 '사용 가능한' 카드의 위치를 저장한다.
        for (int i = 1; i < smallestBiggerThanN.length; i++)        // 처음에는 아직 사용하지 않았으므로, 자신의 위치를 저장.
            smallestBiggerThanN[i] = i;

        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            int card = Integer.parseInt(st.nextToken());        // 상대방이 낸 카드
            // findMinBigNum(card)로 내가 가진 패 중에 상대방 카드보다 크지만 가장 작은 카드의 idx를 가져온다.
            // 그 후 findValuableCard()를 통해 해당 카드와 같거나 큰 카드 중에 '사용 가능한' 카드의 위치를 가져온다.(= 아직 내지 않은)
            int idx = findValuableCard(findMinBigNum(card));
            // cards[idx]가 이번 내가 낼 카드.
            sb.append(cards[idx]).append("\n");
            // idx 카드는 사용을 했으므로, smallestBiggerThanN[idx] 위치에는 idx값 대신 card[idx]보다 큰 카드들 중 가장 작은 카드의 idx를 저장해둔다.
            smallestBiggerThanN[idx] = findValuableCard(idx + 1);
        }
        System.out.println(sb);
    }

    static int findValuableCard(int n) {        // n보다 크거나 같은 사용 가능한 카드의 위치를 반환한다.
        if (smallestBiggerThanN[n] == n)        // n번 위치 카드가 사용 가능하다면 n 반환
            return n;
        // 그렇지 않다면, smallestBiggerThanN[n]보다 크거나 같은 사용 가능한 카드의 위치를 가져와 저장하고 반환.
        return smallestBiggerThanN[n] = findValuableCard(smallestBiggerThanN[n]);
    }


    static int findMinBigNum(int n) {
        int start = 0;      // 0부터
        int end = cards.length - 1;     // cards.length - 1까지의 범위를 탐색한다.
        while (start < end) {
            int mid = (start + end) / 2;
            if (cards[mid] <= n)        // cards[mid]가 n보다 작거나 같다면, start = mid + 1을 한다(우리가 원하는 건 n보다 큰 카드이므로)
                start = mid + 1;
            else        // 만약 크다면 end를 mid로 줄여준다.
                end = mid;
        }
        // 최종적으로 start 값이 n보다 크지만 가장 작은 카드의 idx가 된다.
        return start;
    }
}