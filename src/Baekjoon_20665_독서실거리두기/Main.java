/*
 Author : Ruel
 Problem : Baekjoon 20665번 독서실 거리두기
 Problem address : https://www.acmicpc.net/problem/20665
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20665_독서실거리두기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class State {
    int student;
    int seat;

    public State(int student, int seat) {
        this.student = student;
        this.seat = seat;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 좌석을 일렬로 갖고 있는 독서실이 주어진다.
        // 독서실은 9시부터 21시까지 운영하며 총 t명의 사람이 예약하였다.
        // 사람들은 주위에 사람이 가장 먼 자리에 앉고자하며, 그런 자리가 여러개라면
        // 가장 번호가 작은 자리를 선호한다.
        // t명의 사람에 대해 출입시간이 주어지며
        // 같은 시간에 입장한다면 이용 시간이 적은 사람이 우선적으로 자리를 선택한다.
        // 이 중 관리자는 p번 자리가 비어있을 때만, 해당 자리를 사용한다.
        // 관리자가 p번 자리를 이용하는 시간은?
        //
        // 정렬, 스위핑 문제
        // t명의 사람들을 입장 시간이 이른 순서대로, 같다면 이용 시간이 적은 사람을 앞에 오도록 정렬한다.
        // 그 후, 사람들을 순차적으로 살펴보며, 비어있는 자리를 확인하며 자리를 배치해나간다.
        // 그러면서 p 자리가 비어있는지 확인하고 해당 자리에 관리자가 앉는 시간을 계산해나간다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n개의 자리, t명의 이용객, 관리자가 선호하는 자리 p
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken()) - 1;

        // 이용객의 출입시간을 입력 받는다
        int[][] students = new int[t][2];
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < students[i].length; j++)
                students[i][j] = stringToMin(st.nextToken());
        }
        // 입장시간에 대해 오름차순으로, 입장 시간이 같다면
        // 출입 시간이 짧은 순서대로 정렬한다.
        Arrays.sort(students, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        // 현재 자리 이용 상태
        boolean[] used = new boolean[n];
        PriorityQueue<State> current = new PriorityQueue<>(Comparator.comparing(s -> students[s.student][1]));
        // 관리자가 p 자리에 앉은 총 시간
        int sum = 0;
        // 독서실은 9시부터 운영되므로
        // p 자리에 마지막으로 앉은 시간을 9시로 설정해둔다
        int lastTimeP = 540;

        // 이용객들을 순서대로 살펴본다.
        for (int i = 0; i < students.length; i++) {
            // i번째 이용객의 입실 시간보다 퇴실 시간이 이른 이용객들은
            // 모두 퇴실 시킨다.
            while (!current.isEmpty() && students[current.peek().student][1] <= students[i][0])
                used[current.poll().seat] = false;

            // 현재 좌석 상태에 따른 i 이용객이 이용할 좌석
            int currentSeat = pickSeat(used);
            used[currentSeat] = true;
            current.offer(new State(i, currentSeat));
            // 만약 p 자리에 앉게 된다면
            // 마지막으로 p 자리가 비어있는 시점부터
            // i번째 이용객이 앉은 시간까지 관리자가 이용할 수 있다.
            if (currentSeat == p) {
                sum += (students[i][0] - lastTimeP);
                // i번째의 퇴실 시간이 p 자리의 마지막 이용 시간.
                lastTimeP = students[i][1];
            }
        }
        // 모든 이용객을 살펴본 후
        // 마지막 p 자리를 이용한 시간부터, 영업 종료 시간인 9시까지
        // 관리자가 p 자리를 사용한다.
        sum += 1260 - lastTimeP;

        // 총 이용 시간 출력
        System.out.println(sum);
    }

    // 현재 자리 상태를 살펴보고 이용객이 선호하는 자리를 반환한다.
    static int pickSeat(boolean[] used) {
        // 각 자리에서 가장 가까운 이용객까지의 거리
        int[] distances = new int[used.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        // 만약 0번 자리가 사용중이라면 거리는 0
        if (used[0])
            distances[0] = 0;
        for (int i = 1; i < distances.length; i++) {
            // 만약 i번째 자리가 사용중이라면
            if (used[i]) {
                // i번째 자리의 거리 0
                distances[i] = 0;
                // 해당 자리부터 왼쪽 좌석들의 거리에 대해
                // i 자리까지의 거리를 반영한다.
                int idx = i - 1;
                while (idx >= 0 && distances[idx] > i - idx)
                    distances[idx] = i - idx--;
            } else if (distances[i - 1] != Integer.MAX_VALUE)       // i 자리보다 왼쪽에 사용중인 자리가 있다면, 가장 가까운 왼쪽 자리부터 거리를 저장한다.
                distances[i] = distances[i - 1] + 1;
        }

        // 이번 이용객이 사용할 자리를 계산한다.
        int recommend = 0;
        for (int i = 0; i < distances.length; i++) {
            if (!used[i] && distances[recommend] < distances[i])
                recommend = i;
        }
        // 해당 자리 반환
        return recommend;
    }

    // String으로 들어온 시간을 int값의 분으로 환산한다.
    static int stringToMin(String s) {
        int time = Integer.parseInt(s);
        return time % 100 + (time / 100) * 60;
    }
}