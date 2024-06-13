/*
 Author : Ruel
 Problem : Baekjoon 3167번 기차표 검사
 Problem address : https://www.acmicpc.net/problem/3167
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3167_기차표검사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개 역을 지나는 기차가 있다.
        // 검표원이 존재하는데, 첫 역에서 두번째 역으로 갈 때 검표를 하고
        // 그 이후는 k개 역을 지날 때마다 검표를 한다. a * k + 1 ~ a * k + 2번째 역으로 갈 때 검표를 한다.
        // 각 역에서 하차객과 승차객의 수가 주어질 때
        // 기차표를 한 번도 검사받지 않는 승개의 수의 최솟값과 최댓값을 구하라
        //
        // 그리디 문제
        // 매번 검표를 진행하지 않기 때문에, 검표를 받지 않는 승객이 생길 수 있다.
        // 이 때 검표를 받지 않는 승객이 최소인 경우는
        // 검표 받은 승객이 우선적으로 하차를 하고, 검표 받지 않는 승객은 최대한 오래 기차에 머물러
        // 검표를 받는 것이다.
        // 검표를 받지 않는 승객이 최대인 경우는
        // 하차할 때 검표 받지 않은 승객이 우선적으로 하차하여
        // 그대로 검표 받지 않고 하차한 승객이 되는 경우이다.
        // 두 경우를 나누어 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 역, 매 k개의 역마다 검표
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 역의 하차, 승차객
        int[][] stations = new int[n][];
        for (int i = 0; i < n; i++)
            stations[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // passengers[0] = 검표받지 않은 인원이 최소인 경우
        // passengers[1] = 검표받지 않은 인원이 최대인 경우
        // passengers[i][0] = 아직 검표받지 않은 인원
        // passengers[i][0] = 이미 검표 받은 인원
        int[][] passengers = new int[2][2];
        // 최솟값과 최댓값
        int minCount = 0;
        int maxCount = 0;
        for (int i = 0; i < stations.length; i++) {
            // 검표 받지 않은 인원을 최소화 하고자 한다면
            // 검표 받은 인원을 우선적으로 하차시킨다.
            // 검표 받은 인원들로만 하차시키는 경우
            if (passengers[0][1] >= stations[i][0])
                passengers[0][1] -= stations[i][0];
            else {      // 검표받지 않은 인원도 하차시켜야하는 경우
                int uncheckedQuit = stations[i][0] - passengers[0][1];
                minCount += uncheckedQuit;
                passengers[0][0] -= uncheckedQuit;
                passengers[0][1] = 0;
            }
            
            // 검표 받지 않은 인원을 최대화 하고자 한다면
            // 검표 받지 않은 인원을 우선적으로 하차
            // 검표 받지 않은 인원들로만 하차시키는 경우
            if (passengers[1][0] >= stations[i][0]) {
                maxCount += stations[i][0];
                passengers[1][0] -= stations[i][0];
            } else {        // 검표 받은 인원도 하차시켜야하는 경우
                maxCount += passengers[1][0];
                passengers[1][1] -= (stations[i][0] - passengers[1][0]);
                passengers[1][0] = 0;
            }
            
            // 승차하자마자 하차하는 경우는 없으므로
            // 마지막으로 승차객들이 승차
            passengers[0][0] += stations[i][1];
            passengers[1][0] += stations[i][1];

            // 만약 k번째 역이라면
            // 현재 검표받지 않은 인원을 전부 검표 받은 인원으로 이동.
            if (i % k == 0) {
                for (int j = 0; j < passengers.length; j++) {
                    passengers[j][1] += passengers[j][0];
                    passengers[j][0] = 0;
                }
            }
        }
        // 최솟값과 최댓값 출력
        System.out.println(minCount + " " + maxCount);
    }
}