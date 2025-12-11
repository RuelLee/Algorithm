/*
 Author : Ruel
 Problem : Baekjoon 13343번 Block Game
 Problem address : https://www.acmicpc.net/problem/13343
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13343_블록게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static HashMap<Long, HashMap<Long, Boolean>> memo;

    public static void main(String[] args) throws IOException {
        // n개, m개의 돌이 쌓여있는 두 돌무더기가 있다.
        // 둘 중 더 많은 돌무더기에서, 더 적은 돌무더기의 돌의 배수만큼을 덜어낼 수 있으며
        // 두 돌무더기 중 하나를 모두 지우면 게임에서 이기게 된다.
        // 두 돌무더기의 돌의 수 n, m이 주어질 때
        // 먼저 게임을 진행하는 사람이 반드시 이길 수 있는지 여부를 출력하라
        //
        // 게임 이론
        // 두 플레이어 모두 최선으로 플레이한다 했을 때의 결과값을 도출해야한다.
        // 먼저 돌이 더 많은 돌무더기에서 더 적은 돌무더기의 배수만큼을 지울 수 있고
        // 하나의 돌 무더기를 지우면 게임에서 승리하므로, 적은 쪽이 많은 쪽의 약수인 상태가 되면
        // 해당 턴을 플레이어하는 사람이 이긴다.
        // n과 m가 최대 10^18으로 주어지므로, 배열로 메모이제이션을 할 수는 없고, 해쉬맵을 통해 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 두 돌무더기 돌의 수 n, m
        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());

        memo = new HashMap<>();
        // n과 m으로 첫 턴을 진행하는 사람이 이길 수 있는지 여부를 출력한다.
        System.out.println(canWin(Math.min(n, m), Math.max(n, m)) ? "win" : "lose");
    }

    // 더 적은 돌무더기 a, 더 많은 돌무더기 b
    static boolean canWin(long a, long b) {
        // 이미 계산할 결과가 있다면 값 참조
        if (memo.containsKey(a) && memo.get(a).containsKey(b))
            return memo.get(a).get(b);
        // 그 외의 경우, b가 a의 배수인 경우, 해당 턴의 플레이어가 이긴다.
        else if (b % a == 0)
            return true;

        boolean possible = false;
        // 값이 클 수록 많은 공간과 시간을 요구하게 되므로
        // 값이 작은 것을 우선적으로 계산한다.
        // b에서 a의 배수만큼 덜어낼 때, 가장 작은 값은 b % a이다.
        // 해당 값 부터, +a 만큼씩하며, 해당 상태로 만들어 턴을 넘길 경우
        // 상대방이 반드시 지는가를 계산한다.
        // 상대방이 반드시 진다면, 해당 경우로 진행하면 현재 턴의 플레이어는 이길 수 있다.
        for (long i = b % a; i < b; i += a) {
            if (!canWin(Math.min(a, i), Math.max(a, i))) {
                possible = true;
                break;
            }
        }

        // 결과값 메모이제이션
        if (!memo.containsKey(a))
            memo.put(a, new HashMap<>());
        memo.get(a).put(b, possible);
        // 값 반환
        return memo.get(a).get(b);
    }
}