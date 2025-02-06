/*
 Author : Ruel
 Problem : Baekjoon 23749번 카드컨트롤
 Problem address : https://www.acmicpc.net/problem/23749
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23749_카드컨트롤;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        // O와 X가 적힌 카드가 각각 n장씩 있다.
        // 준석이는 첫번째 카드를, 수현이는 두번째 카드를 가져간다.
        // 그 후, 카드가 같으면 무승부, 다른 카드면 O가 적힌 사람이 1점을 가져간다.
        // 위 시행을 n회 반복한 뒤, 점수를 계산한다.
        // 준석이는 한 번의 조작으로 원하는 카드를 하나 빼서 맨 위로 올리는 것이 가능하다.
        // 준석이가 이기기 위해서 해야하는 최소 조작은?
        //
        // 브루트 포스 문제
        // n이 5까지로 그리 크지 않다.
        // 모든 카드에 손을 대 순서를 바꾸더라도 10! 300만 정도이며,
        // 이미 찾아진 조작 횟수보다 더 큰 조작 횟수는 시행조차 안한다고 한다면 더 줄일 수 있다.
        // 먼저, 원래 카드에서 준석이가 이길 수 있는지 확인
        // 그 후, 이길 수 없다면, 카드들 중 한 장을 맨 앞으로 올려서 다시 계산해본다.
        // 이와 같은 시행을 브루트 포스, 재귀를 이용하여 구현하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n장의 카드 쌍
        int n = Integer.parseInt(br.readLine());
        
        // 2 * n개의 카드가 주어진다.
        // 하지만 2 * n장의 카드를 모두 일일이 앞으로 보낼 경우를 생각하여
        // 2 * 2 * n장의 배열에 카드를 배치한다.
        int[] cards = new int[4 * n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 2 * n; i < cards.length; i++) {
            if (st.nextToken().charAt(0) == 'O')
                cards[i] = 1;
            else
                cards[i] = 2;
        }
        System.out.println(bruteForce(cards.length / 2, cards));
    }

    // 처음 카드의 위치와 카드가 주어진다.
    static int bruteForce(int startIdx, int[] cards) {
        // 만약 이미 계산할 결과보다 이번에 성공한다하더라도 더 높은 조작 횟수를 갖게 된다면
        // 계산하지 않고 큰 값을 반환.
        if (cards.length / 2 - startIdx >= min)
            return Integer.MAX_VALUE;
        
        // 각 친구의 점수
        int[] scores = new int[2];
        // 이미 고른 카드.
        boolean[] selected = new boolean[cards.length];
        // 총 card.length / 4번의 게임을 한다.
        for (int i = 0; i < cards.length / 4; i++) {
            // 두 장의 카드를 뽑는다.
            int[] pickCards = new int[2];
            for (int j = startIdx; j < cards.length; j++) {
                // card[j] == 0인 경우는 원래 해당 순서에 있던 카드가
                // 앞으로 끌어올려진 경우이다. 건너뛴다.
                if (cards[j] != 0 && !selected[j]) {
                    selected[j] = true;
                    if (pickCards[0] == 0)
                        pickCards[0] = cards[j];
                    else {
                        pickCards[1] = cards[j];
                        break;
                    }
                }
            }
            
            // 준석이가 이긴 경우
            if (pickCards[0] < pickCards[1])
                scores[0]++;
            // 수현이가 이긴 경우
            else if (pickCards[1] < pickCards[0])
                scores[1]++;
        }

        // 점수를 비교하여 준석이가 이긴 경우
        // min 값 갱신 후, 반환.
        if (scores[0] > scores[1])
            return min = Math.min(min, cards.length / 2 - startIdx);

        // 이기지 못한 경우. 조작을 통해 한 장의 카드를 맨 앞으로 올린다.
        int answer = Integer.MAX_VALUE;
        for (int i = cards.length / 2; i < cards.length; i++) {
            // 해당 카드가 0이 아니라면(= 이미 앞으로 올린 카드가 아니라면)
            if (cards[i] != 0) {
                // 현재 시작 지점의 카드보다 앞 자리에 해당 카드를 놓고
                cards[startIdx - 1] = cards[i];
                // i번째 카드는 0을 놓은 카드 배열을 만든다.
                cards[i] = 0;
                // 그 후, 해당 경우로 게임을 진행한다.
                answer = Math.min(answer, bruteForce(startIdx - 1, cards));
                // 카드 배열을 원래대로 복구한다.
                cards[i] = cards[startIdx - 1];
            }
        }
        // 찾은 최소 조작 횟수를 반환한다.
        return answer;
    }
}