/*
 Author : Ruel
 Problem : Baekjoon 23309번 철도 공사
 Problem address : https://www.acmicpc.net/problem/23309
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23309_철도공사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 원형으로 이어진 지하철역이 주어진다.
        // 다음 네 종류의 공사가 가능하다
        // BN i j : i의 다음 역을 출력하고, i와 다음 역 사이에 j 역을 건설한다.
        // BP i j : i의 이전 역을 출력하고, i와 이전 역 사이에 j 역을 건설한다.
        // CN i : i의 다음 역을 폐쇄한다.
        // CP i : i의 이전 역을 폐쇄한다.
        // n개의 역, m개의 공사가 주어질 때
        // 결과 값을 출력하라
        //
        // 연결 리스트
        // 연결 리스트를 구현하는 듯했던 문제
        // 원형으로 연결되어있다는 점에 유의하며
        // 이전 역과 다음 역에 유의하며 역을 추가, 폐쇄해나가면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 역, m개의 공사
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 역들
        int[] stations = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 역의 다음 역
        int[] nextStation = new int[1_000_001];
        // 각 역의 이전 역
        int[] prevStation = new int[1_000_001];
        // 원형으로 연결되어있으므로 첫번째 역과 마지막 역이 서로 연결되게 한다.
        for (int i = 0; i < stations.length; i++) {
            nextStation[stations[i]] = stations[(i + 1) % stations.length];
            prevStation[stations[i]] = stations[(stations.length + i - 1) % stations.length];
        }

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < m; k++) {
            st = new StringTokenizer(br.readLine());
            String order = st.nextToken();
            int i = Integer.parseInt(st.nextToken());
            // i와 다음 역 사이에 j 역을 건설하는 경우
            switch (order) {
                case "BN" -> {
                    int j = Integer.parseInt(st.nextToken());
                    // 원래의 다음 역 기록
                    sb.append(nextStation[i]).append("\n");
                    // j의 다음 역과 이전 역을 기록하고
                    prevStation[j] = i;
                    nextStation[j] = nextStation[i];
                    // i의 원래 다음 역에 이전 역으로 j를 등록
                    prevStation[nextStation[i]] = j;
                    // i의 다음 역으로 j를 기록
                    nextStation[i] = j;
                }
                case "BP" -> {        // i와 이전 역 사이에 j 역을 건설하는 경우
                    int j = Integer.parseInt(st.nextToken());
                    sb.append(prevStation[i]).append("\n");
                    prevStation[j] = prevStation[i];
                    nextStation[j] = i;
                    nextStation[prevStation[i]] = j;
                    prevStation[i] = j;
                }
                case "CN" -> {         // i의 다음 역을 폐쇄하는 경우
                    sb.append(nextStation[i]).append("\n");
                    // i의 다음 다음 역에 이전 역으로 i를 등록
                    prevStation[nextStation[nextStation[i]]] = i;
                    // i의 다음 역으로 다음 다음 역을 등록
                    nextStation[i] = nextStation[nextStation[i]];
                }
                default -> {         // i의 이전 역을 폐쇄하는 경우
                    sb.append(prevStation[i]).append("\n");
                    nextStation[prevStation[prevStation[i]]] = i;
                    prevStation[i] = prevStation[prevStation[i]];
                }
            }
        }
        // 전체 결과 출력
        System.out.print(sb);
    }
}