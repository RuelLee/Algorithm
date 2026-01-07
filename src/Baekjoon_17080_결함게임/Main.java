/*
 Author : Ruel
 Problem : Baekjoon 17080번 결함 게임
 Problem address : https://www.acmicpc.net/problem/17080
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17080_결함게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n의 크기의 돌이 주어진다.
        // 돌탑은 큰 돌 위에 작은 돌이 쌓인 형태이다.
        // A와 B가 번갈아가며 게임을 진행한다.
        // 남은 돌을 쌓을 수 있는 돌탑이 없다면, 원하는 돌을 골라 새로운 돌탑을 쌓는다.
        // 남은 돌을 쌓을 수 있는 돌탑이 있다면 가능한 돌 중 원하는 돌을 골라 그 위에 쌓는다.
        // 최종적으로 쌓인 돌탑의 수가 홀수일 경우 A가, 짝수일 경우 B가 승리한다.
        // 둘이 최선으로 게임을 진행할 때, 승자는?
        //
        // 게임 이론, 애드혹 문제
        // 먼저 짝수개의 돌이 주어지는 경우, A가 항상 승리한다.
        // 선공의 경우, 가장 작은 돌 + 1의 돌을 쌓아, 상대방에게 가장 작은 돌을 해당 탑 위에 쌓도록 강요할 수 있기 때문
        // 그러면 홀수인 경우, B가 이기느냐?도 아니다.
        // 5인 경우, A가 2를 쌓고 1을 강요하고, 4를 쌓고 3을 강요하면, 마지막으로 5를 하나 더 쌓음으로써 홀수개의 탑을 만들 수 있기 때문
        // 이렇게 강요하는 것이 불가능한 것이, A가 B를 강요할 수 있는 횟수가 홀수 번인 경우이다.
        // 3인 경우, 2를 쌓아 1을 강요한다면, 3을 새로운 곳에 놓아 짝수개의 탑이 된다.
        // 그렇다면, A가 1을 놓는 경우, B에게 남은 돌을 짝수개로 넘겨주는거나 마찬가지가 된다.
        // 3을 먼저 놓는 경우도, B가 그 위에 1을 놓아 2를 다른 탑을 놓게 강요할 수 있게 된다.
        // 따라서 n mod 4가 3인 경우만 B가 이기게 되고, 나머지 경우는 A가 이기게 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Integer.parseInt(br.readLine()) % 4 == 3 ? 2 : 1);
    }
}