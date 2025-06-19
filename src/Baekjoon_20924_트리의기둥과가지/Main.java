/*
 Author : Ruel
 Problem : Baekjoon 20924번 트리의 기둥과 가지
 Problem address : https://www.acmicpc.net/problem/20924
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20924_트리의기둥과가지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Branch {
    int next;
    int d;

    public Branch(int next, int d) {
        this.next = next;
        this.d = d;
    }
}

public class Main {
    static List<List<Branch>> branches;

    public static void main(String[] args) throws IOException {
        // 실제 나무와 같은 형태로 트리가 주어진다.
        // 루트 노드로부터, 기가로드까지는 하나의 자식 노드들로만 이루어지며
        // 기가 노드는 처음으로 자식 노드가 2개 이상 생긴 노드이다.
        // 리프 노드는 더 이상 자식 노드가 없는 노드이다.
        // 루트 노드로부터 기가 노드까지를 기둥, 기가 노드로부터 리프 노드까지를 가지라고 한다.
        // 각각 노드 사이의 거리가 주어질 때, 기둥의 길이와, 가지의 최대 길이를 구하라
        //
        // BFS 문제
        // 루트 노드로부터 기가노드까지를 찾고, 다시 기가노드로부터 리프 노드까지의 거리를 재면 된다.
        // 단, 루트 노드 == 기가 노드, 기가 노드 == 리프 노드인 경우들이 있으므로
        // 이를 간단하게 해결하기 위해, 루트 노드 위에 가짜 루트 노드를 만들어준다면
        // 각각의 노드에 연결된 노드 개수로만 판별이 가능하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, 루트 노드 r
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        branches = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            branches.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            branches.get(a).add(new Branch(b, d));
            branches.get(b).add(new Branch(a, d));
        }
        // 루트 노드 r에 가짜 루트 노드 0을 추가한다.
        branches.get(0).add(new Branch(r, 0));
        branches.get(r).add(new Branch(0, 0));
        
        // BFS로 찾은 기둥과 가지의 길이를 출력
        int[] lengths = findAnswer(0, 0, 0, -1);
        System.out.println(lengths[0] + " " + lengths[1]);
    }
    
    // BFS, 백트래킹
    static int[] findAnswer(int idx, int pillarLength, int branchLength, int previous) {
        int[] max = new int[]{pillarLength, branchLength};
        
        // 가지 노드가 시작되었는지 판별
        // 이미 branchLength가 0이 아니거나, 이번 노드가 기가 노드인 경우
        boolean branchStarted = (branchLength != 0 || branches.get(idx).size() > 2);
        for (Branch b : branches.get(idx)) {
            // 부모 노드로는 가지 않는다.
            if (b.next == previous)
                continue;
            
            // b.next 노드로 나아갔을 때의, 기둥과 가지의 최대 길이 
            int[] returned = findAnswer(b.next, pillarLength + (!branchStarted ? b.d : 0), branchLength + (branchStarted ? b.d : 0), idx);
            for (int j = 0; j < max.length; j++)
                max[j] = Math.max(max[j], returned[j]);
        }
        // idx에서 얻을 수 있는 최대 기둥과 가지 길이 반환
        return max;
    }
}