/*
 Author : Ruel
 Problem : Baekjoon 17453번 두 개의 문
 Problem address : https://www.acmicpc.net/problem/17453
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17453_두개의문;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static HashMap<Integer, BitSet> hashMap;

    public static void main(String[] args) throws IOException {
        // n개의 문과 m개의 스위치가 주어진다.
        // 각 문은 앞 -> 뒤일 경우 -1년, 뒤 -> 앞일 경우 +1년의 시점으로 갈 수 있다
        // 또한 각 스위치들은 n개의 문들 중 몇 개의 문의 앞뒤를 바꿀 수 있다.
        // -n년 부터 n년까지 도달하는데 필요한 스위치와 해당 스위치를 출력하라
        // 불가능하다면 -1을 출력한다
        //
        // 브루트 포스 알고리즘
        // 스위치가 최대 20개 주어지므로
        // 2^20 약 100만가지 경우로 모두 해볼 수 있다.
        // 다만 그 때의 문 상태와 스위치 상태를 기록을 해야하는데
        // 문이 최대 100개 주어지므로 32bit인 int, 64bit인 long으로는 불가능하다.
        // 따라서 비트셋을 활용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 문과 m개의 스위치
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 원래 문의 상태
        BitSet original = stringToBitSet(br.readLine());
        // m개의 스위치가 뒤집을 수 있는 문들의 정보
        BitSet[] switches = new BitSet[m];
        for (int i = 0; i < switches.length; i++)
            switches[i] = stringToBitSet(br.readLine());

        hashMap = new HashMap<>();
        // 브루트 포스
        dfs(0, original, new BitSet(), switches);
        
        // -n년부터 n년까지 가능한 스위치 상태를 살펴본다
        StringBuilder sb = new StringBuilder();
        for (int i = -n; i < n + 1; i++) {
            // 값이 존재하지 않는다면 불가능한 경우
            // -1 기록
            if (!hashMap.containsKey(i))
                sb.append(-1).append("\n");
            else {
                // 가능하다면 누른 스위치의 개수 체크
                int count = 0;
                // 큐를 통해 누른 스위치를 순서대로 저장해준다.
                Queue<Integer> queue = new LinkedList<>();
                for (int j = 0; j < n; j++) {
                    if (hashMap.get(i).get(j)) {
                        count++;
                        queue.offer(j + 1);
                    }
                }
                // 누른 스위치의 총 개수
                sb.append(count);
                // 누른 스위치들
                while (!queue.isEmpty())
                    sb.append(" ").append(queue.poll());
                sb.append("\n");
            }
        }
        
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // 브루트 포스
    static void dfs(int idx, BitSet gates, BitSet switchState, BitSet[] switches) {
        // 모든 스위치를 살펴본 경우
        if (idx == switches.length) {
            // 변화한 시간 시점
            int year = bitSetToYear(gates);
            // 해당 시간에 도착한 기록이 없다면
            // 이번 스위치들의 상태를 기록
            if (!hashMap.containsKey(year))
                hashMap.put(year, (BitSet) switchState.clone());
            return;
        }
        
        // idx번째 스위치를 눌렀을 때
        switchState.set(idx);
        gates.xor(switches[idx]);
        dfs(idx + 1, gates, switchState, switches);
        // idx번 스위치를 누르지 않았을 때
        switchState.clear(idx);
        gates.xor(switches[idx]);
        dfs(idx + 1, gates, switchState, switches);
    }
    
    // 비트셋 기록을 토대로 시간 증감을 계산
    static int bitSetToYear(BitSet bitSet) {
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += bitSet.get(i) ? 1 : -1;
        return sum;
    }

    // 입력으로 주어지는 문과 스위치 상태를 비트셋 형태로 바꾼다.
    static BitSet stringToBitSet(String s) {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(s.length() - 1 - i) == '1')
                bitSet.set(i);
        }
        return bitSet;
    }
}