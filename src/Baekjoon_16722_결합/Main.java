/*
 Author : Ruel
 Problem : Baekjoon 16722번 결! 합!
 Problem address : https://www.acmicpc.net/problem/16722
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16722_결합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 3개의 모양, 3개의 색상, 3개의 배경이 주어진다.
        // 동그라미 세모 네모 / 노란색 빨간색 파란색 / 회색 흰색 검은색
        // 따라서 총 가짓수는 27개인 그림이 있다.
        // 이 중 9개의 카드를 골라 게임을 진행한다.
        // 고른 3장의 카드의 각각의 요소들이 모두 다르거나 모두 같다면 합이다.
        // 3장의 카드를 골라 합을 외쳤을 때, 합이 맞으며 이전에 외친 조합이 아니라면 +1점, 그렇지 않다면 -1점
        // 결이라 외쳤을 때, 이전에 결로서 점수를 받은 적이 없고, 더 이상 합이 없다면 +3점, 그렇지 않다면 -1점
        // 9장의 카드와 플레이어가 한 행동이 주어질 때
        // 플레이어의 점수는?
        //
        // 브루트포스, 시뮬레이션, 비트마스크
        // 각각의 특징을 통해 그림 하나를 비트마스크 하나로 표현한 뒤
        // 3장의 그림에 대해 비트마스크들을 or 연산 후, 3개씩 끊어 살펴보아 각각의 특징들이 모두 같거나 다른지 확인한다.
        // 게임에 진행되는 그림이 9장으로 많지 않으므로 브루트포스를 통해 미리 합의 조합을 모두 구해둔다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각각의 특징
        String[] factors = new String[]{"CIRCLE", "TRIANGLE", "SQUARE", "YELLOW", "RED", "BLUE", "GRAY", "WHITE", "BLACK"};
        // idx 할당
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < factors.length; i++)
            hashMap.put(factors[i], i);

        // 게임에 사용된 9장의 그림
        int[] pictures = new int[9];
        StringTokenizer st;
        // 하나의 비트마스크로 표현
        for (int i = 0; i < pictures.length; i++) {
            st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens())
                pictures[i] |= (1 << hashMap.get(st.nextToken()));
        }

        // 합을 이루는 카드들을 비트마스크로 만들어
        // 해쉬셋에 담는다.
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < pictures.length; i++) {
            for (int j = i + 1; j < pictures.length; j++) {
                for (int k = j + 1; k < pictures.length; k++) {
                    if (isHab(pictures[i] | pictures[j] | pictures[k]))
                        hashSet.add((1 << i) | (1 << j) | (1 << k));
                }
            }
        }
        
        // 플레이어가 한 행동의 개수
        int n = Integer.parseInt(br.readLine());
        // 점수
        int score = 0;
        // 결을 외쳐 점수를 받은 적이 있는지 여부
        boolean gyeol = false;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            char input = st.nextToken().charAt(0);
            // 합을 외친 경우
            if (input == 'H') {
                // 카드들을 하나의 비트마스크로 표현한 뒤
                int bitmask = 0;
                while (st.hasMoreTokens())
                    bitmask |= (1 << (Integer.parseInt(st.nextToken()) - 1));
                
                // 해쉬셋에 포함되어있는지 확인
                if (hashSet.contains(bitmask)) {
                    // 있다면 1점 추가 후, 해쉬셋에서 해당 비트마스크 제거
                    score++;
                    hashSet.remove(bitmask);
                } else      // 없다면 -1점
                    score--;
            } else {        // 결을 외친 경우
                // 해쉬셋에 더 이상 값이 없고
                // 결을 외쳐 점수를 받은 적이 없다면 +3점
                if (hashSet.size() == 0 && !gyeol) {
                    score += 3;
                    gyeol = true;
                } else      // 그렇지 않다면 -1점
                    score--;
            }
        }
        // 최종 점수 출력
        System.out.println(score);
    }

    static boolean isHab(int bitmask) {
        // 비트마스크를 3자리씩 3번 끊어서 살펴본다.
        for (int i = 0; i < 3; i++) {
            int value = bitmask % 8;
            // 2개의 비트가 1인 경우 false 반환
            if (value == 3 || value == 5 || value == 6)
                return false;
            bitmask /= 8;
        }
        // 모두 특징들이 같거나 달랐다면 true 반환
        return true;
    }
}