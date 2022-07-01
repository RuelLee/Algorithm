/*
 Author : Ruel
 Problem : Baekjoon 2513번 통학버스
 Problem address : https://www.acmicpc.net/problem/2513
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2513_통학버스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 아파트 단지와 학생 수, 버스의 탑승 인원 k, 학교의 위치 s가 주어진다
        // 버스는 s에서 출발해 탑승 인원 내의 학생들을 태우고 다시 학교로 돌아온다
        // 버스의 이동 거리를 최소로 할 때, 그 거리는?
        //
        // 그리디 문제
        // 어찌됐건, 학교에서 가장 멀리 떨어져있는 학생들 또한 모두 학교로 데려와야한다
        // 따라서 학교를 기준으로 왼쪽과 오른쪽으로 나누고
        // 거리가 먼 아파트 단지부터 학생들을 태우며 돌아오는 경우를 계산하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        int[][] apartments = new int[n][2];
        int maxLeftLoc = -1;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            apartments[i][0] = Integer.parseInt(st.nextToken());
            apartments[i][1] = Integer.parseInt(st.nextToken());
        }
        // 아파트의 위치를 오름차순으로 정렬한다.
        Arrays.sort(apartments, Comparator.comparingInt(o -> o[0]));

        // 학교의 왼쪽에 있는 아파트 단지들 중 가장 오른쪽 아파트 단지를 찾는다.
        for (int i = 0; i < n; i++) {
            if (apartments[i][0] > s)
                break;
            maxLeftLoc = Math.max(maxLeftLoc, i);
        }

        int lengthSum = 0;
        int start = 0;
        int peopleInBus = 0;
        // 0번 아파트단지부터, maxLeftLoc 아파트 단지 까지(= 학교로부터 왼쪽 단지들)
        for (int i = 0; i <= maxLeftLoc; i++) {
            // 만약 버스에 탑승 인원이 없다면, s -> 해당 아파트 단지 -> s의 경로가 된다
            // start를 apartments[i][0]으로 해주자.
            if (peopleInBus == 0)
                start = apartments[i][0];

            // 해당 아파트의 인원을 모두 태운다.
            peopleInBus += apartments[i][1];
            // 버스에 탑승한 인원이 k보다 많다면, 학교로 갔다가 돌아와야한다.
            if (peopleInBus > k) {
                // 만약 시작 지점이 현재 아파트단지가 아니라면
                // 이전 아파트 단지에서 출발해서 이번 아파트 단지를 거쳐서 학교로 돌아가는 경우.
                if (start != apartments[i][0]) {
                    // 이동 거리는 s부터 기록되어있는 start 까지. 왕복이므로 *2
                    lengthSum += (s - start) * 2;
                    // k에 해당하는 인원 한번만 제해준다.
                    peopleInBus -= k;
                    // 그리고 다음 시작 위치는 이번 아파트 단지.
                    start = apartments[i][0];
                }

                // 이동 거리 합에는 (start ~ s)까지의 거리 * 왕복 * (버스에 인원이 k보다 몇 배나 많은지)를 해준다
                // 만약 인원이 가득 차지 않았다면 peopleInBus / k는 0이 되어 길이 합이 더해지지 않을 것이다.
                lengthSum += (s - start) * 2 * (peopleInBus / k);
                // 버스 인원은 k로 모듈러 연산해준다.
                peopleInBus %= k;
            }
        }
        // 학교 왼쪽 아파트 단지를 모두 살펴보았다.
        // 혹시 버스에 아직 탑승 인원이 있다면 버스가 해당 인원을 마지막으로 학교로 돌려보낸다.
        if (peopleInBus != 0)
            lengthSum += (s - start) * 2;

        // 위와 같은 연산을 학교 오른쪽 아파트 단지들에 대해 시행한다.
        start = n - 1;
        peopleInBus = 0;
        // 가장 오른쪽 아파트 단지부터, 학교 오른쪽 단지들 중 가장 왼쪽 아파트 단지까지.
        for (int i = n - 1; i > maxLeftLoc; i--) {
            // 버스 탑승 인원이 0이라면 해당 아파트 단지가 이번 노선에서 가장 먼 곳.
            if (peopleInBus == 0)
                start = apartments[i][0];

            // 아파트 단지 인원을 모두 탑승시키고
            peopleInBus += apartments[i][1];
            // 탑승 인원을 넘었다면
            if (peopleInBus > k) {
                // start가 이번 아파트 단지인지, 저번 아파트 단지인지 확인 후, 저번 아파트 단지라면
                if (start != apartments[i][0]) {
                    // 저번 아파트 단지까지의 왕복 길이를 더하고
                    lengthSum += (start - s) * 2;
                    // k만큼만 탑승 인원을 빼준다.
                    peopleInBus -= k;
                    // 그리고 출발 지점은 현재 아파트 단지로 변경.
                    start = apartments[i][0];
                }

                // 탑승 인원 / k 만큼 버스가 이번 아파트 단지에서 탑승인원은 가득 채울 수 있다.
                // 해당 횟수만큼 곱해 왕복 길이를 더해주고
                lengthSum += (start - s) * 2 * (peopleInBus / k);
                // 모듈러 연산을 통해 남는 인원을 기록해주자.
                peopleInBus %= k;
            }
        }
        // 만약 버스에 잔여 인원이 남았다면
        // 마지막 출발 지점으로부터의 왕복 길이를 더해주자.
        if (peopleInBus > 0)
            lengthSum += (start - s) * 2;
        // 총 버스의 이동 거리 출력.
        System.out.println(lengthSum);
    }
}