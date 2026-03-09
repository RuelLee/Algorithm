/*
 Author : Ruel
 Problem : Baekjoon 17215번 볼링 점수 계산
 Problem address : https://www.acmicpc.net/problem/17215
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17215_볼링점수계산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 볼링을 게임을 한다.
        // 각 기회마다 쓰러뜨린 핀의 개수가 주어질 때, 점수를 계산하라.
        // 볼링은 각 프레임마다 2회씩 기회가 주어지며, 스트라이크를 친 경우는 해당 프레임에서 두번째 기회는 사라진다.
        // 스트라이크를 친 경우, 다음 두 번의 기회 동안 쓰러뜨린 핀 만큼의 점수를 추가로 얻게 되고
        // 스페어를 한 경우, 다음 한 번의 기회 동안 쓰러뜨린 핀 만큼 점수를 추가로 얻는다.
        // 10번째 프레임에서 스트라이크를 친 경우, 두 번의 추가 기회를 얻게 되고, 추가 기회에서는 보너스가 없다.
        // 10번째 프레임에서 스페어를 한 경우, 한 번의 추가 기회를 얻게 되고, 마찬가지로 보너스는 없다.
        //
        // 시뮬레이션 문제
        // 10번째 프레임에서 스트라이크를 친 경우, 가상의 11프레임, 12프레임이 생길 수 있다고 가정하였다.
        // 먼저 주어진 입력을 각 기회마다 쓰러뜨린 핀의 개수로 환산하였다.
        // 그 후, 현재 스트라이크를 친 경우, 다음 두 번의 기회 동안 배율을 하나씩 높이는 방식을 취했다.
        // 11번째, 12번째 프레임이 존재하는 경우, 해당 프레임에서의 기본 배율을 0으로 두어, 보너스가 반영되지 않도록 했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력
        String input = br.readLine();
        // 각 기회마다 쓰러뜨린 핀의 개수로 환산
        int[] pins = new int[24];
        int idx = 0;
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case 'S' -> {
                    pins[idx] = 10;
                    idx += 2;
                }
                case 'P' -> {
                    pins[idx] = 10 - pins[idx - 1];
                    idx++;
                }
                case '-' -> idx++;
                default -> {
                    pins[idx] = input.charAt(i) - '0';
                    idx++;
                }
            }
        }

        // 배율 계산
        int[] multi = new int[24];
        // 기본 배율은 1.
        // 11, 12번 프레임의 기본 배율은 0
        Arrays.fill(multi, 0, 20, 1);
        for (int i = 0; i < 20; i += 2) {
            // 스트라이크를 친 경우
            if (pins[i] == 10) {
                // 다음 프레임 첫 구 배율 상승
                multi[i + 2]++;
                // 다음 프레임 첫 구가 스트라이크인 경우
                // 다음 다음 프레임 첫 구의 배율 상승
                if (pins[i + 2] == 10)
                    multi[i + 4]++;
                // 아닌 경우 다음 프레임의 두번째 구 배율 상승
                else
                    multi[i + 3]++;
            } else if (pins[i] + pins[i + 1] == 10)     // 스페어인 경우, 다음 프레임 첫 구만 배율 상승
                multi[i + 2]++;
        }

        // 점수 계산
        int score = 0;
        for (int i = 0; i < 24; i++)
            score += pins[i] * multi[i];
        System.out.println(score);
    }
}