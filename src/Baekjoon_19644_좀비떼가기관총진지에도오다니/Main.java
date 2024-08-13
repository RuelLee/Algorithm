/*
 Author : Ruel
 Problem : Baekjoon 19644번 좀비 떼가 기관총 진지에도 오다니
 Problem address : https://www.acmicpc.net/problem/19644
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19644_좀비떼가기관총진지에도오다니;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 기관총 진지로 좀비들이 일렬로 다가오고 있다.
        // 진지 앞쪽 길의 거리는 lm이며, im 떨어진 좀비의 체력은 zi이다.
        // 좀비가 1m 진지 쪽으로 이동할 때마다, 기관총 혹은 수평 세열 지향성 지뢰를 1회 사용할 수 있다.
        // 기관총의 경우 유효 사거리가 ml이며, 해당 사거리에 있는 모든 좀비의 체력을 mk만큼 낮춘다.
        // 수평 세열 지향성 지뢰는 c개 주어지며, 바로 앞 1m에 있는 좀비를 남은 체력에 상관없이 제압할 수 있따.
        // 좀비들로부터 진지를 사수할 수 있는지 계산하라
        //
        // 그리디, 누적합 문제
        // 기관총을 발사하되, 해당 좀비가 1m까지 접근했을 때도 제압하지 못했다면 지뢰를 사용한다.
        // 기관총 발사 여부는 누적합으로 관리하여, 1m 앞까지 전진한 좀비의 체력이 얼마인지를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 진지 앞 거리의 길이 l
        int l = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 기관총의 사거리 ml, 피해 mk
        int ml = Integer.parseInt(st.nextToken());
        int mk = Integer.parseInt(st.nextToken());
        // 지뢰의 개수
        int c = Integer.parseInt(br.readLine());

        // 누적합을 통해, 해당 순서의 좀비에게 기관총을 발사했는지 기록한다.
        int[] gun = new int[l + 1];
        boolean possible = true;
        for (int i = 1; i <= l; i++) {
            // 누적합 처리
            gun[i] += gun[i - 1];
            // i번째 좀비
            int zombie = Integer.parseInt(br.readLine());
            // i번째 좀비가 다가올 때까지 맞은 기관총에 의한 피해
            int damage = (gun[i - 1] - gun[Math.max(i - ml, 0)] + 1) * mk;
            // 만약 피해가 좀비 체력보다 같거나 크다면
            // 이번 순서에도 기관총을 발사하고
            if (damage >= zombie)
                gun[i]++;
            else if (c > 0) {       // 그렇지 않지만 지뢰가 남았다면 지뢰 차감
                c--;
            } else {
                // 위 두 경우가 모두 아니라면 진지에 좀비가 도착한다.
                possible = false;
                break;
            }
        }
        // 진지 사수 여부 출력
        System.out.println(possible ? "YES" : "NO");
    }
}