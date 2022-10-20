/*
 Author : Ruel
 Problem : Baekjoon 11509번 풍선 맞추기
 Problem address : https://www.acmicpc.net/problem/11509
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11509_풍선맞추기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 풍선이 서로 다른 높이로 일렬로 서 있다.
        // 이 때 화살을 쏴 풍선을 맞춘다.
        // 화살은 풍선을 맞출 경우, 높이가 1 감소한 상태로 계속해서 날아간다.
        // 모든 풍선을 맞추기 위해 필요한 최소 화살의 개수는?
        //
        // 그리디 문제
        // 아이디어에 비해 코딩은 너무 간단했던 문제
        // 처음에는 가장 높으면서 앞쪽에 있는 풍선부터 맞추면서 해당 풍선보다 뒤에 있는 풍선들을
        // 모두 고려해주는 방법으로 풀려했으나 시간 초과...
        // 생각해보니 굳이 높은 풍선부터 쏴맞추지 않더라도
        // 맨 앞에 있는 풍선을 맞추려면 반드시 화살을 쏴야한다.
        // 따라서 풍선을 순서대로 계산하되, 해당 풍선을 맞추기 위해 쏜 화살이 높이가 -1 된 화살로 남아있다는 가정으로 문제를 풀어나가면 된다.
        // 예를 들어
        // 3 1 2 로 풍선이 있다면
        // 3을 맞추기 위해 화살을 쏘고, 높이 2의 화살이 계속 진행한다
        // 1을 맞추기 위해 화살을 쏘고, 해당 화살은 바닥에 떨어진다.
        // 마지막으로 2를 맞추기 위해서는 이미 3을 맞추기 위해 쐈던 화살이 있으므로 더 이상 쏘지 않아도 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 풍선들
        int[] balloons = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 순차적으로 진행하며 현재 화살들이 어느 높이에 있는지 표시할 배열
        int[] arrows = new int[1_000_001];
        // 총 발사한 화살의 개수
        int arrowsSum = 0;
        for (int balloon : balloons) {
            // 이미 앞 풍선을 터뜨리고, balloon 높이에 해당하는 화살이 하나 이상 존재한다면
            // balloon 높이의 화살을 하나 줄여준다.
            if (arrows[balloon] > 0)
                arrows[balloon]--;
            // 그렇지 않다면 새로운 화살을 쏴야한다.
            else
                arrowsSum++;
            // 어쨌거나 balloon의 풍선을 터뜨리고, balloon - 1 높이의 화살을 하나 추가한다.
            arrows[balloon - 1]++;
        }
        // 총 발사한 화살의 개수를 출력한다.
        System.out.println(arrowsSum);
    }
}