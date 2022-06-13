/*
 Author : Ruel
 Problem : Baekjoon 2487번 섞기 수열
 Problem address : https://www.acmicpc.net/problem/2487
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2487_섞기수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    // n개의 카드가 섞기 수열이 주어진다
    // 가령 섞기수열이 3, 2, 5, 6, 1, 4로 주어진다면
    // 1 2 3 4 5 6 -> 3 2 5 6 1 4 -> 5 2 1 4 3 6 -> 1 2 3 6 5 4로 카드가 섞이게 된다
    // 카드가 맨 처음 상태로 돌아가는 횟수를 수열의 궤적이라 부른다.
    // 이 때 궤적을 구하라.
    //
    // 섞기 수열을 통해 각 카드들이 원래 값으로 돌아가는 횟수를 구한다.
    // 그리고 이 횟수들의 최대공배수를 구해주면 그 값이 궤적이 된다.
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] shuffle = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[] cards = new int[n];
        for (int i = 0; i < cards.length; i++)
            cards[i] = i + 1;

        // 원래 카드로 돌아가는 turn수를 찾은 카드의 개수.
        int found = 0;
        // 턴.
        int turn = 1;
        int[] turns = new int[n];
        Arrays.fill(turns, Integer.MAX_VALUE);
        // 만약 변동이 없는 카드가 있다면 해당 카드의 turn은 0.
        for (int i = 0; i < shuffle.length; i++) {
            if (shuffle[i] == i + 1) {
                turns[i] = 0;
                found++;
            }
        }
        // 모든 카드가 원래 카드로 돌아가기 위한 최소 turn을 구할 때까지 반복한다.
        while (found < n) {
            for (int i = 0; i < cards.length; i++) {
                // i번째 카드에는 해당 카드가 가르키는 섞기 수열의 값을 가져온다.
                cards[i] = shuffle[cards[i] - 1];
                // 만약 그 값이 첫카드와 동일하고, 이번이 처음 발견된 최소 사이클이라면
                // 해당 turn을 기록해주고, found를 증가시켜준다.
                if (cards[i] == i + 1 && turns[i] > turn) {
                    turns[i] = turn;
                    found++;
                }
            }
            // 턴 증가.
            turn++;
        }

        // 모든 카드에 대해 원래 카드로 돌아가기 위한 최소 사이클을 구했다.
        // 해당 사이클들을 통해 최소공배수를 구한다.
        // 같은 사이클에 대해서는 여러번 구할 필요가 없으니 해쉬셋으로 방문체크해주자.
        HashSet<Integer> hashSet = new HashSet<>();
        // 0은 구할 필요가 없으므로 미리 추가시켜주자.
        hashSet.add(0);

        int answer = 1;
        for (int i = 0; i < turns.length; i++) {
            // 구한적이 있는 수라면 건너뛴다.
            if (hashSet.contains(turns[i]))
                continue;

            // 그렇지 않다면 anser가 최대공배수를 구해 그 값을 answer로 취한다.
            answer = answer / getGCD(answer, turns[i]) * turns[i];
            // 방문체크.
            hashSet.add(turns[i]);
        }
        System.out.println(answer);
    }

    // 유클리드 호제법을 통해 최대공약수를 구한다.
    static int getGCD(int a, int b) {
        int c = Math.max(a, b);
        // a는 작은 값.
        a = Math.min(a, b);
        // b는 큰 값.
        b = c;
        // a가 0이 아닌 동안
        while (a != 0) {
            // c에는 큰 값에 대해 작은 값으로 모듈러 연산한 값을 저장한다.
            c = b % a;
            // a가 이제 큰 값이 되고
            b = a;
            // c가 작은 값이 된다.
            a = c;
        }
        // 최종적으로 b가 최대공약수가 된다.
        return b;
    }
}