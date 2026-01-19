/*
 Author : Ruel
 Problem : Baekjoon 25401번 카드 바꾸기
 Problem address : https://www.acmicpc.net/problem/25401
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25401_카드바꾸기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 카드가 주어진다.
        // 모든 카드를 같은 값으로 만들거나
        // 일정한 값으로 증가 혹은 감소하도록 만들고자할 때, 값을 변경해야하는 카드의 수는?
        //
        // 브루트 포스 문제
        // 두 개의 수를 골라, 해당하는 수열을 만들어 나머지 수들 중 해당 수열에 속하는 수개 몇개나 있는지 세어주면 된다.
        // n이 500이므로, 최대 500C2 * 500 번의 연산, 약 6천만번으로 통과 가능
        // 초항과 공차를 기억해두면, 동일 수열에 대한 반복 연산을 줄일 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 카드
        int n = Integer.parseInt(br.readLine());
        int[] cards = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < cards.length; i++) {
            cards[i] = Integer.parseInt(st.nextToken());
        }

        int answer = Integer.MAX_VALUE;
        // 초항과 공차로 방문 체크를 한다.
        HashMap<Integer, HashSet<Integer>> visited = new HashMap<>();
        // i번째 카드와
        for (int i = 0; i < cards.length - 1; i++) {
            // j번째 카드로 등차 수열을 만든다.
            for (int j = i + 1; j < cards.length; j++) {
                // 만약 공차가 소수라면 건너뜀.
                if ((cards[j] - cards[i]) % (j - i) != 0)
                    continue;

                // 공차와 초항
                int d = (cards[j] - cards[i]) / (j - i);
                int first = cards[i] - i * d;
                // 이미 계산한 적이 있다면 건너뜀
                if (visited.containsKey(first) && visited.get(first).contains(d))
                    continue;

                // 수열에 속하지 않는 카드의 개수
                int cnt = 0;
                for (int k = 0; k < cards.length; k++) {
                    if (cards[i] + (k - i) * d != cards[k])
                        cnt++;
                }

                // 방문 체크
                if (!visited.containsKey(first))
                    visited.put(first, new HashSet<>());
                visited.get(first).add(d);
                // 해당하는 cnt가 최솟값인지 확인
                answer = Math.min(answer, cnt);
            }
        }
        // 얻은 최솟값 출력
        System.out.println(answer);
    }
}