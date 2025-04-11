/*
 Author : Ruel
 Problem : Baekjoon 1581번 락스타 락동호
 Problem address : https://www.acmicpc.net/problem/1581
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1581_락스타락동호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] order = {0, 1, 3, 2};

    public static void main(String[] args) throws IOException {
        // 네 종류의 노래가 있다.
        // 빠르게 시작하여 빠르게 끝나는 노래 FF, 빠르게 시작하여 느리게 끝나는 노래 FS
        // 느리게 시작하여 빠르게 끝나는 노래 FS, 느리게 시작하여 느리게 끝나는 노래 SS
        // 앨범에 노래들을 담으려 하는데, 조건이 있다.
        // 빠르게 시작하는 노래는 빠르게 끝나는 노래 뒤에 와야하고
        // 느리게 시작하는 노래는 느리게 끝나는 노래 뒤에 와야한다.
        // 첫 노래는 빠르게 시작하는 노래가 있다면, 빠르게 시작하는 노래를
        // 빠르게 시작하는 노래가 없다면 느리게 시작하는 노래를 담는다.
        // 가장 많은 노래들을 앨범에 담고자 한다.
        // 각각 노래들의 수가 주어질 때, 담을 수 있는 최대 노래의 수는?
        //
        // 조건이 많은 분기
        // 머리로 가짓수를 따져보며 조건을 나눠보면 된다.
        // 먼저 FF나 SS는 통째로 연이어서 모두 담을 수 있다.
        // 즉 한 곡이라도 선택된다면 해당하는 분류의 노래를 모두 담을 수 있다.
        // 문제는 FS와 SF
        // 현재 빠르게 시작하는 노래를 담아야한다면 FS -> SF -> FS -> SF 식으로 이어나갈 수 있다.
        // 따라서 두 노래중 더 적은 수 만큼의 사이클을 반복할 수 있고, 그러고도 FS가 남는다면 하나 더 담을 수 있다.
        // 반대의 경우도 마찬가지
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각 노래의 수
        int[] songs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 첫 노래를 선정한다.
        // 빠르게 시작하는 노래
        // FF, FS를 우선 살펴보고
        // 두 노래가 없다면 SS, SF 순으로 살펴본다.
        int count = 1;
        // 다음 노래의 시작 템포
        int nextTempo = 0;
        for (int i = 0; i < order.length; i++) {
            if (songs[order[i]] > 0) {
                songs[order[i]]--;
                nextTempo = order[i] % 2;
                break;
            }
        }
        
        // 다음 노래의 시작 템포가 빠른 템포여야한다면
        if (nextTempo == 0) {
            // 현재 FF 노래를 모두 담을 수 있다.
            count += songs[0];
            songs[0] = 0;
            // FS가 0개가 아니라면
            if (songs[1] != 0) {
                // SS 노래를 모두 담을 수 있다.
                count += songs[3];
                songs[3] = 0;
                // FS와 SF 중 더 적은 수의 노래만큼 사이클을 반복 가능.
                int min = Math.min(songs[1], songs[2]);
                // 해당하는 수만큼의 사이클 반복
                count += min * 2;
                songs[1] -= min;
                songs[2] -= min;
                // 그 후, FS 노래가 남았다면 한 곡 더 담을 수 있다.
                if (songs[1] > 0) {
                    songs[1]--;
                    count++;
                }
            }
        } else {    // 다음 노래가 느리게 시작해야하는 경우도 마찬가지.
            count += songs[3];
            songs[3] = 0;
            if (songs[2] != 0) {
                count += songs[0];
                songs[0] = 0;
                int min = Math.min(songs[1], songs[2]);
                count += min * 2;
                songs[1] -= min;
                songs[2] -= min;
                if (songs[2] > 0) {
                    songs[2]--;
                    count++;
                }
            }
        }
        // 답 출력
        System.out.println(count);
    }
}