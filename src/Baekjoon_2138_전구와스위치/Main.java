/*
 Author : Ruel
 Problem : Baekjoon 2138번 전구와 스위치
 Problem address : https://www.acmicpc.net/problem/2138
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2138_전구와스위치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 스위치와 전구가 주어진다. 0은 켜진 상태, 1은 꺼진 상태.
        // i번째 스위치를 누르면, i-1, i, i+1의 전구의 상태가 바뀐다고 한다.
        // 예를 들어 001이라는 3개의 전구가 있고, 두번째 스위치를 우르면 110의 상태로 바뀐다.
        // 현재 상태와 만들고자 하는 상태 2개가 주어질 때,
        // 최소한의 스위치 누름으로 원하는 상태로 바꾸는 경우, 몇 번 누르면 되는지 계산하라.
        //
        // 그리디 문제
        // 구현 자체는 어렵지 않지만, 아이디어를 생각해내는게 어렵다.
        // n이 최대 10만이 주어지므로, 브루트 포스는 당연히 불가능하다.
        // 또한 DP로도 구현이 까다롭다.
        // 선형적인 계산을 하기 위해서 조건을 제한해 두번 계산한다.
        // 1 -> n으로 나아간다고 할 때,
        // i - 1번째 전구의 상태를 바꿀 수 있는 마지막 기회는 i번째 스위치이다.
        // 따라서 i - 1번째 전구가 목표로 하는 상태와 다른 상태일 경우, i번째 스위치를 무조건 눌러줘야한다.
        // 이와 같은 계산을 반복하여 마지막 전구의 스위치까지 눌렀을 때, 마지막 전구와 목표로 하는 상태의 마지막 전구의 상태가 다르다면
        // 위의 경우는 불가능 한 경우이다.
        // 이렇게 i - 1번째 전구의 상태에 따라서 i번째 스위치를 누르기 때문에
        // 첫번째 스위치는 0번째 전구가 없기 때문에 상태를 고려하기 힘들다.
        // 따라서 첫번째 스위치를 눌렀을 때, 누르지 않았을 때, 두가지 경우를 분리하여 각각 계산하고
        // 가능한 경우라면 스위치 누른 횟수를 비교하여 더 적은 값을 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        // 전구의 초기 상태.
        char[] originBulbs = br.readLine().toCharArray();
        // 원하는 전구들의 상태.
        String targetBulbs = br.readLine();

        // 첫번째 스위치를 누르지 않을 때.
        char[] notPushFirst = originBulbs.clone();
        int notPushFirstCount = 0;
        // 첫번째 스위치를 누를 때.
        char[] pushFirst = originBulbs.clone();
        int pushFirstCount = 1;
        pushSwitch(0, pushFirst);

        // 두번째(idx 상으로는 1) 스위치부터 누르는 걸 고려하며 선형적으로 계산한다.
        for (int i = 1; i < n; i++) {
            // i - 1번째 전구와 원하는 상태의 전구가 서로 다른 상태라면
            // 이번에 스위치를 반드시 눌러줘야한다.
            if (notPushFirst[i - 1] != targetBulbs.charAt(i - 1)) {
                pushSwitch(i, notPushFirst);
                notPushFirstCount++;
            }

            if (pushFirst[i - 1] != targetBulbs.charAt(i - 1)) {
                pushSwitch(i, pushFirst);
                pushFirstCount++;
            }
        }

        // 마지막 전구의 상태를 비교하여 원하는 상태로 만드는 것이 가능한지 살펴보고
        // 가능할 경우 두 경우의 스위치 누름 횟수를 비교하여 더 적은 값을 출력한다.
        int answer = Math.min(notPushFirst[n - 1] == targetBulbs.charAt(n - 1) ? notPushFirstCount : Integer.MAX_VALUE,
                pushFirst[n - 1] == targetBulbs.charAt(n - 1) ? pushFirstCount : Integer.MAX_VALUE);

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    // 스위치를 누를 경우, idx - 1, idx, idx + 1번째 전구의 상태가 바뀐다.
    static void pushSwitch(int idx, char[] bulbs) {
        // 마지막은 idx + 1과 전구의 마지막 전구 중 더 적은 값까지.
        int end = Math.min(idx + 2, bulbs.length);
        // 시작은 0번 전구나 idx - 1 번 중 더 큰 값부터.
        // 전구의 상태를 반대로 바꾼다.
        for (int i = Math.max(idx - 1, 0); i < end; i++)
            bulbs[i] = bulbs[i] == '0' ? '1' : '0';
    }
}