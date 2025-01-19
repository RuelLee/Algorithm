/*
 Author : Ruel
 Problem : Baekjoon 27977번 킥보드로 등교하기
 Problem address : https://www.acmicpc.net/problem/27977
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27977_킥보드로등교하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int k;
    static int[] stations;

    public static void main(String[] args) throws IOException {
        // 현재 건덕이는 0 위치에 있고, l 위치에 학교가 있다.
        // 사이에는 n개의 킥보드 충전소가 존재하며, 위치가 주어진다.
        // 최대 k번 충전소를 들려, 학교에 도착하고자할 때
        // 킥보드의 최대 이용거리가 최소 얼마가 되어야 학교까지 등교가 가능한가?
        //
        // 이분 탐색 문제
        // 킥보드의 최대 이용거리에 대해 1 ~ l까지의 범위를 이분 탐색을 통해 탐색한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 학교의 위치 l, 충전소의 개수 n, 충전소에 들리는 횟수 k
        int l = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        // 충전소의 위치
        stations = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            stations[i] = Integer.parseInt(st.nextToken());
        // 마지막 위치는 학교
        stations[n] = l;
        
        // 이분 탐색
        int start = 1;
        int end = l;
        while (start < end) {
            int mid = (start + end) / 2;
            if (possible(mid))
                end = mid;
            else
                start = mid + 1;
        }
        // 답 출력
        System.out.println(start);
    }
    
    // 1회 충전으로 최대 battery만큼의 위치를 이동할 수 있다면
    static boolean possible(int battery) {
        int currentLoc = 0;
        int count = 0;
        for (int i = 0; i < stations.length; i++) {
            // k회 충전을 마쳤다면 반복문 종료
            if (count == k)
                break;

            // 만약 이번 충전소까지보다 더 멀리 갈 수 있다면 일단은 건너뛴다.
            if (currentLoc + battery >= stations[i])
                continue;
            else if (currentLoc + battery < stations[i] && i > 0 &&
                    stations[i - 1] <= currentLoc + battery) {
                // i번 충전소에는 도달하지 못하나
                // i-1번 충전소에는 닿을 수 있다면
                // 현재 위치를 i-1번째 충전소.
                // count 증가
                currentLoc = stations[i - 1];
                count++;
            } else
                break;
        }
        // 최종적으로 k회 충전을 모두 마쳤거나
        // 마지막 충전소까지 이동이 가능한 경우로 반복문을 마쳤을 것이다.
        // 현재 상태에서 battery만큼 더 이동한다면,
        // 학교에 닿을 수 있는지 여부를 반환한다.
        return currentLoc + battery >= stations[stations.length - 1];
    }
}