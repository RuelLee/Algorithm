/*
 Author : Ruel
 Problem : Baekjoon 23878번 Lonely Photo
 Problem address : https://www.acmicpc.net/problem/23878
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23878_LonelyPhoto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소가 줄지어 서 있다.
        // 각각 소의 종류는 Guernsey 혹은 Holstein 이다.
        // 3마리 이상의 소를 하나의 사진으로 찍을 때
        // 둘 중의 한 종이라도 한 마리만 나오는 경우는 사진을 폐기한다고 한다.
        // 폐기하는 사진의 가짓수는?
        //
        // 조합 문제
        // 한 소를 기준으로 양 옆에 다른 종의 소가 몇마리가 연이어 있는지를 세고
        // 해당 값을 기준으로 3마리 이상 소가 나오도록 사진을 찍는 가짓수를 계산하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());
        String cows = br.readLine();

        // 다음 같은 종의 소가 등장하는 번호.
        int[] nextSameChar = new int[n];
        // 이전에 등장한 같은 종의 소 번호
        int[] lastAppeared = new int[2];
        // 0 ~ n-1번까지가 할당되며
        // 다음 등장할 소가 없는 경우, n번을 할당한다.
        Arrays.fill(lastAppeared, n);
        for (int i = n - 1; i >= 0; i--) {
            // 소의 종류
            int v = cows.charAt(i) == 'G' ? 0 : 1;
            // 앞으로 등장할 같은 소의 번호.
            nextSameChar[i] = lastAppeared[v];
            // 현재 등장한 종의 idx를 남겨둠.
            lastAppeared[v] = i;
        }

        // 같은 방식으로 이전에 등장한 같은 종의 소 번호를 기록해둔다.
        int[] previousSameChar = new int[n];
        Arrays.fill(lastAppeared, -1);
        for (int i = 0; i < n; i++) {
            int v = cows.charAt(i) == 'G' ? 0 : 1;
            previousSameChar[i] = lastAppeared[v];
            lastAppeared[v] = i;
        }

        // 모든 소에 대해 차근차근 계산해나간다.
        long answer = 0;
        for (int i = 0; i < n; i++) {
            // i번 소를 기준으로
            // 좌측으로 다른 종의 소가 연이어 서있는 마릿수
            int leftDiff = i - (previousSameChar[i] + 1);
            // 우측으로 다른 종의 소가 연이어 서있는 마릿수
            int rightDiff = nextSameChar[i] - (i + 1);

            // 좌측으로 2마리 이상이 있다면
            // G....GH 꼴 혹은 H...HG 꼴이 가능하며 그 개수를 더한다.
            if (leftDiff >= 2)
                answer += leftDiff - 1;
            // 마찬가지로 우측으로 2마리 이상이 있는 경우.
            if (rightDiff >= 2)
                answer += rightDiff - 1;
            // 양 옆으로 다른 종이 한 마리 이상 있는 경우
            // G...GHG...H 꼴 혹은 H...HGH...H 꼴이 가능하다.
            // 좌측에 있는 수 * 우측에 있는 수
            // 값이 int 범위를 넘어갈 수 있다.
            if (leftDiff >= 1 && rightDiff >= 1)
                answer += (long) leftDiff * rightDiff;
        }
        // 전체 답 출력
        System.out.println(answer);
    }
}