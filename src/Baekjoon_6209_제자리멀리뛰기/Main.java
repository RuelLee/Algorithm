/*
 Author : Ruel
 Problem : Baekjoon 6209번 제자리 멀리뛰기
 Problem address : https://www.acmicpc.net/problem/6209
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6209_제자리멀리뛰기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0의 위치로부터 징검다리를 건너 d의 위치로 가고자한다.
        // 돌은 총 n개가 있으며, 각각 0의 위치로부터 떨어진 거리가 주어진다.
        // 이 때 돌 사이의 거리들 중 최소값을 최대로 만들기 위해 m개의 돌을 치운다고 한다.
        // 이 때 만들 수 있는 돌 사이의 거리 중 최소값은?
        //
        // 이분 탐색 문제
        // 그리디 문제인 줄 알았으나, 두 돌 사이의 거리만 보고 판단하고 치우려면,
        // 앞쪽 돌을 치워야하는지, 뒤쪽 돌을 치워야하는지에 따라 코드가 복잡해지고 예외사항들이 발생할 것 같아
        // 이분탐색을 통하여 풀었다.
        // 이분 탐색을 통해, m개의 돌을 치울 때 돌 사이의 최소 거리를 최대로 하는 값을 찾아나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        StringTokenizer st = new StringTokenizer(br.readLine());
        int d = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 돌들
        int[] stones = new int[n + 1];
        for (int i = 0; i < stones.length - 1; i++)
            stones[i] = Integer.parseInt(br.readLine());
        // 마지막으로 d 위치에 도달해야하므로, d위치도 넣어준다.
        stones[n] = d;
        // 오름차순 정렬
        Arrays.sort(stones);
        
        // 이분 탐색
        int left = 1;
        int right = d;
        while (left <= right) {
            int mid = (left + right) / 2;
            // m개의 돌을 치워 최소 거리를 mid 이상으로 만들 수 있다면
            // 더 큰 값도 가능한지 살피기 위해 left 를 mid + 1 값으로 바꿔준다.
            if (isPossible(stones, mid, m))
                left = mid + 1;
            // 불가능하다면, 더 적은 값들을 탐색하기 위해
            // right 를 mid - 1로 바꿔준다.
            else
                right = mid - 1;
        }
        // 도달하는 right 값이 돌들 사이 거리들 중 최소값을 최대로 했을 때의 값.
        System.out.println(right);
    }

    // remain개의 돌을 치워, 최소거리를 minJump 이상으로 만드는 것이 가능한지 살펴본다.
    static boolean isPossible(int[] stones, int minJump, int remain) {
        // 처음 위치 0
        int preLoc = 0;
        for (int stone : stones) {
            // i번째 돌과 이전 위치와의 거리가 minJump 보다 작다면
            // i번째 돌을 치워야한다.
            if (stone - preLoc < minJump) {
                // 치우는 횟수가 남아있지 않다면 불가능
                if (remain == 0)
                    return false;
                // 남아 있다면 횟수 차감.
                remain--;
            } else      // minJump보다 같거나 크다면, 이전 위치를 현재 돌의 위치로 바꿔주고 넘어간다.
                preLoc = stone;
        }

        // false를 만나지 않고 모든 돌을 통과했다면 해당 조건이 가능한 경우.
        // true 반환.
        return true;
    }
}