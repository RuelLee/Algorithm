/*
 Author : Ruel
 Problem : Jungol 3142번 ID검사
 Problem address : https://jungol.co.kr/problem/3142
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3142_ID검사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static HashSet<String> members, current;

    public static void main(String[] args) throws IOException {
        // n개의 쿼리가 '명령 id' 형태로 주어질 때 쿼리들을 처리하라
        // 1 id : 가입되어있다면 1을 아니라면 0을 반환
        // 2 id : 로그인 중이라면1, 아니라면 0
        // 3 id : 새로운 id라면 등록하고 등록 중인 회원 수를 반환
        // 4 id : 이미 등록된 회원이라면 탈퇴 처리. 로그인 중이라면 로그아웃도 동시에 이루어진다.
        // 5 id : 등록된 회원이고 로그아웃 상태라면 로그인 처리하고, 현재 로그인 회원 수를 반환
        // 6 id : 로그인된 회원이라면 로그아웃 처리하고 현재 로그인 회원 수 반환
        //
        // 구현 문제
        // 서버에서 하는 쿼리 처리와 비슷하지만 단순화하여 처리한다.
        // 데이터베이스에서 데이터를 주고받지 않고 set 형태에 저장한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 쿼리
        int n = Integer.parseInt(br.readLine());

        // 가입과 로그인 여부를 판단할 set
        members = new HashSet<>();
        current = new HashSet<>();
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 명령과 id
            int o = Integer.parseInt(st.nextToken());
            String id = st.nextToken();

            switch (o) {
                // 각 쿼리 처리
                case 1 -> sb.append(members.contains(id) ? 1 : 0);
                case 2 -> sb.append(current.contains(id) ? 1 : 0);
                case 3 -> {
                    members.add(id);
                    sb.append(members.size());
                }
                case 4 -> {
                    current.remove(id);
                    members.remove(id);
                    sb.append(members.size());
                }
                case 5 -> {
                    if (members.contains(id))
                        current.add(id);
                    sb.append(current.size());
                }
                case 6 -> {
                    current.remove(id);
                    sb.append(current.size());
                }
            }
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}