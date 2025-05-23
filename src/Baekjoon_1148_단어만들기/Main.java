/*
 Author : Ruel
 Problem : Baekjoon 1148번 단어 만들기
 Problem address : https://www.acmicpc.net/problem/1148
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1148_단어만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대 20만 개의 단어(길이 4 ~ 9)가 주어진다.
        // 그리고 각 게임마다 9개의 알파벳이 주어지고, 그 중 하나의 알파벳은 반드시 사용해야한다.
        // 이 때, 꼭 사용하는 알파벳을 무엇으로 지정할 때,
        // 만들 수 있는 단어가 가장 적은지와 가장 많은지를 구하라
        //
        // 구현 문제
        // 단어가 20만개로 많고, 메모리 제한이 128MB으로 크지 않으므로
        // 재활용할 부분들을 재활용하여 계산해준다.
        // 각 게임마다 9개의 알파벳과 단어들을 비교하여 만들 수 있는 단어들을 추리고
        // 단어에 속한 알파벳들에 대한 가중치들을 중복하지 않고 하나씩 증가시킨다.
        // 최종적으로 9개의 알파벳들 중 가중치가 가장 적은 것들과 큰 것들을 추려내 답안을 작성한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 단어를 - 입력이 들어올 때까지 받는다.
        List<String> words = new ArrayList<>();
        String input;
        while (!(input = br.readLine()).equals("-"))
            words.add(input);

        StringBuilder sb = new StringBuilder();
        // 각 게임
        // 게임이 종료된 경우, #이 입력된다.
        while (!(input = br.readLine()).equals("#")) {
            // 9개의 알파벳들에 대해, 종류를 분류하여 개수를 센다.
            int[] board = new int[26];
            for (int i = 0; i < input.length(); i++)
                board[input.charAt(i) - 'A']++;

            // 꼭 사용해야하는 알파벳의 가중치
            int[] middleCandidate = new int[26];

            // 단어에 속해있는 알파벳을 종류별로 개수를 센다.
            int[] counts = new int[26];
            for (String word : words) {
                // 20만개나 되므로, 단어마다 0을 채워넣어 재활용한다.
                Arrays.fill(counts, 0);
                // 9개의 알파벳들로 해당 단어를 만들 수 있는지 여부
                boolean possible = true;
                for (int i = 0; i < word.length(); i++) {
                    char c = word.charAt(i);
                    // 해당하는 알파벳의 수가 주어진 9개의 알파벳 수를 넘어서는 안된다.
                    if (++counts[c - 'A'] > board[c - 'A']) {
                        possible = false;
                        break;
                    }
                }

                // 만드는 것이 가능하다면
                // 단어에 속한 알파벳들을 중복하지 않고 가중치를 하나씩 증가시킨다.
                if (possible) {
                    for (int i = 0; i < counts.length; i++) {
                        if (counts[i] > 0)
                            middleCandidate[i]++;
                    }
                }
            }

            // 어떤 알파벳을 꼭 사용해야하는 알파벳으로 지정할 때
            // 최소 답, 최대 답이 되는지, 해당 후보들
            Queue<Character> minCandidate = new LinkedList<>();
            Queue<Character> maxCandidate = new LinkedList<>();
            // 만들 수 있는 최소, 최대 수
            int min = Integer.MAX_VALUE;
            int max = 0;
            // 후보들을 살펴본다.
            for (int i = 0; i < middleCandidate.length; i++) {
                // 만약 9개의 알파벳에 속하지 않는다면 건너뛴다.
                if (board[i] == 0)
                    continue;

                // 해당 알파벳을 꼭 사용하는 알파벳으로 지정할 때
                // 만들 수 있는 단어의 수가 최댓값을 갱신하거나 같을 때.
                char c = (char) ('A' + i);
                if (middleCandidate[i] >= max) {
                    // 갱신하는 경우라면, max 값 갱신 후, 큐 초기화
                    if (middleCandidate[i] > max) {
                        max = middleCandidate[i];
                        maxCandidate.clear();
                    }
                    // 후 해당 알파벳 추가
                    maxCandidate.offer(c);
                }
                // 마찬가지로 최솟값을 갱신하거나 같을 때.
                if (middleCandidate[i] <= min) {
                    if (middleCandidate[i] < min) {
                        min = middleCandidate[i];
                        minCandidate.clear();
                    }
                    minCandidate.offer(c);
                }
            }

            // 찾은 값을 바탕으로 답안 작성
            while (!minCandidate.isEmpty())
                sb.append(minCandidate.poll());
            sb.append(" ").append(min).append(" ");
            while (!maxCandidate.isEmpty())
                sb.append(maxCandidate.poll());
            sb.append(" ").append(max).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}