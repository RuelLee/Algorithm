/*
 Author : Ruel
 Problem : Jungol 5179번 고장난 CCTV
 Problem address : https://jungol.co.kr/problem/5179
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5179_고장난CCTV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수직선 위에 cctv들이 설치되어있다.
        // 각 cctv는 설치된 위치로부터 끝방향까지에 도둑이 있는지 표시한다.
        // L x : x보다 같거나 작은 위치에 도둑이 있다.
        // G x : x보다 같거나 큰 위치에 도둑이 있다.
        // 이 중 몇몇의 카메라가 고장나 있다고 한다.
        // 고장난 카메라의 최소 개수는?
        //
        // 두 포인터, 정렬 문제
        // 먼저 L과 G에 따라 분리하여 입력받은 뒤 각각 정렬한다.
        // L 혹은 G 한 쪽으로만 카메라가 설치되어있는 경우, 고장난 최소 개수는 0개일 수밖에 없다.
        // 섞여있는 경우
        // L에 대한 카메라들을 큰 위치에서부터 살펴보며, 해당 카메라까지만 맞는 경우를 계산해본다.
        // 만약 L에 1 3 5가 있다면
        // 모두 틀리는 경우, 5 이하에 있지만 3 이하엔 없는 경우, 3 이하에 있지만 1 이하엔 없는 경우
        // 1 이하에 있는 경우 들로 나눠 해당 경우에 G에 대한 카메라가 고장난 최소 개수를 계산해
        // 고장난 카메라의 최소 개수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> before = new ArrayList<>();
        List<Integer> after = new ArrayList<>();
        // n개의 카메라
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        // L과 G에 따라 분리하여 리스트에 저장
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            if (st.nextToken().charAt(0) == 'L')
                before.add(Integer.parseInt(st.nextToken()));
            else
                after.add(Integer.parseInt(st.nextToken()));
        }
        // 정렬
        Collections.sort(before);
        Collections.sort(after);

        // 한 방향으로만 있는 경우
        // 모든 카메라가 틀리지 않을 수 있다.
        if (before.isEmpty() || after.isEmpty())
            System.out.println(0);
        else {
            // G에 대해 정리한 리스트의 idx
            int j = after.size() - 1;
            // 고장난 카메라의 최소 개수
            // 한 쪽 방향의 카메라가 모두 고장난 경우들을 생각해볼 수 있다.
            int ans = Math.min(before.size(), after.size());
            // L에 대한 카메라가 i번과 이후의 카메라들만 맞고
            // 0 ~ i번 카메라는 고장난 경우에 대해 계산한다.
            for (int i = before.size() - 1; i >= 0; i--) {
                // 그럴 때, G에 대한 카메라들은 i번 카메라가 가르키는 위치보다는 같거나 작은 위치여야한다.
                while (j >= 0 && after.get(j) > before.get(i))
                    j--;
                // 고장난 카메라의 수
                ans = Math.min(ans, i + after.size() - 1 - j);
            }
            // 답 출력
            System.out.println(ans);
        }
    }
}