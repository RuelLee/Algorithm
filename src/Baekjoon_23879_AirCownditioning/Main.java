/*
 Author : Ruel
 Problem : Baekjoon 23879번 Air Cownditioning
 Problem address : https://www.acmicpc.net/problem/23879
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23879_AirCownditioning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 개별 방이 주어지고, 각 방의 현재 온도와 희망 온도가 주어진다.
        // 온도는 연속한 방들의 온도를 한 번에 1씩 변경할 수 있다.
        // 모든 방을 희망 온도로 바꾸고자할 때, 필요한 온도 변경의 최소 횟수는?
        //
        // 그리디
        // 연속한 구간에 대해, 온도 변경의 방향(상승 혹은 하락)이 같다면 같이 변경하는 것이 유리하다.
        // 하지만 이미 희망온도가 됐거나, 방향이 반대라면 구간을 나눠 각각 변경해주는 것이 유리하다.
        // 따라서 모든 방을 순서대로 살펴보며
        // 1. 첫번째 방일 경우, 해당 온도만큼 온도를 변경한다.
        // 2. 다음 방이 같은 온도 방향을 갖고 있고, 이전 방보다 더 적은 양의 온도 변화가 필요하다면 해당 방은 이전 방의 온도를 조절하는 도중에
        // 자동적으로 온도가 맞춰지게 된다. 따라서 온도를 조절할 필요가 없다.
        // 3. 다음 방이 같은 온도 방향을 갖고 있고, 이전 방보다 더 많은 양의 온도 변화가 필요하다면, 해당 방까지만으로의 온도 변경만으로는 불충분하고
        // 차이만큼의 온도 변화가 더 필요하다.
        // 4. 다음 방이 다른 온도 방향을 갖고 있거나, 이미 온도가 희망 온도라면, 다시 첫번째 방인 것과 마찬가지로 이전 방과 현재 방을 분리하여 생각한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 방
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 희망 온도
        int[] p = new int[n];
        for (int i = 0; i < n; i++)
            p[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        // 현재 온도
        int[] t = new int[n];
        for (int i = 0; i < n; i++)
            t[i] = Integer.parseInt(st.nextToken());
        
        // 총 온도 변화 횟수
        int sum = 0;
        // 이전 방의 차이
        int preDiff = 0;
        for (int i = 0; i < p.length; i++) {
            // 현재 방에 필요한 온도 변화
            int diff = p[i] - t[i];
            // 이전 방과 다른 방향의 온도 변화가 필요하다면 분리하여 생각.
            // 현재 방에 필요한 만큼의 온도 변화가 필요하고, preDiff에 해당 값 저장
            if (preDiff * diff <= 0)
                sum += (Math.abs((preDiff = diff)));
            else if (diff > 0) {
                // 온도 변화 방향이 양이고
                // 현재 변화량이 이전 변화량보다 더 많은 양의 변화가 필요하다면
                // 차이만큼 추가 온도 변화가 필요
                    sum += (diff - preDiff);
                preDiff = diff;
            } else {
                // 온도 변화 방향이 음인 경우
                if (diff <= preDiff)
                    sum += (preDiff - diff);
                preDiff = diff;
            }
        }
        // 필요한 총 온도 변화의 양 출력
        System.out.println(sum);
    }
}