/*
 Author : Ruel
 Problem : Baekjoon 26268번 은?행 털!자 2
 Problem address : https://www.acmicpc.net/problem/26268
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26268_은행털자2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
    static long[] segmentTree;

    public static void main(String[] args) throws IOException {
        // 일직선 경로 위에 n개의 은행이 있다.
        // 은행마다 좌표 x, 문을 여는 정확한 시각 t, 은행을 털었을 때 얻는 이득 c가 주어진다.
        // 은행들은 x가 증가하는 순서로 주어진다.
        // 임의의 좌표에서 시작할 수 있고, 시간은 0에서 시작하며, 좌표가 1 증가할 때, 시간도 1 증가한다.
        // 얻을 수 있는 최대 이익은?
        //
        // 좌표 압축, 세그먼트 트리 문제
        // x와 t가 최대 10억으로 크게 주어지므로 배열을 사용하기 힘들다.
        // 문제에서 x가 증가하는 순서로 주어진다고 했으므로
        // x - t 값을 기준으로 생각하면, 일직선으로 멈추지 않고 나아갈 때, 동시에 들어갈 수 있는 은행들을 알 수 있다.
        // 따라서 x - t 값에 대해 좌표 압축을 한다.
        // 그 후, 길을 나아가다 멈추는 경우도 있을 수 있다.
        // 같은 x - t의 은행만을 털어야하는 것이 아닌, 더 큰 x - t 값으로 옮겨갈 수도 있다.
        // 따라서 순차적으로 은행을 살펴볼 때는, 현재 x - t보다 같거나 작은 값들 중 가장 큰 이득인 곳에서
        // 해당 은행을 터는 것이 최대 이득이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        // 은행들
        int[][] banks = new int[n][3];
        // 트리 셋과 해쉬 맵을 통해 좌표 압축
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < banks.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < banks[i].length; j++)
                banks[i][j] = Integer.parseInt(st.nextToken());
            treeSet.add(banks[i][1] - banks[i][0]);
        }

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        while (!treeSet.isEmpty())
            hashMap.put(treeSet.pollFirst(), hashMap.size() + 1);
        
        // 세그먼트 트리
        segmentTree = new long[hashMap.size() * 4];
        for (int[] bank : banks) {
            // 현재 은행의 t - x값을 압축한 idx로 교환
            int idx = hashMap.get(bank[1] - bank[0]);
            // 1 ~ idx들 중 최대 값을 찾아
            long max = getMax(1, idx, 1, 1, hashMap.size());
            // 그 값 + c값이 bank 은행을 마지막으로 털 때의 최대 이익
            inputValue(idx, max + bank[2], hashMap.size());
        }
        // 모든 은행을 살펴본 뒤
        // 전체 값들 중 최대 이익 값을 찾아 출력
        System.out.println(getMax(1, hashMap.size(), 1, 1, hashMap.size()));
    }

    // start ~ end 범위 내의 최대값을 찾는다.
    static long getMax(int start, int end, int loc, int seekStart, int seekEnd) {
        // 범위가 일치한다면 해당 세그먼트 트리 노드의 값 반환
        if (start == seekStart && end == seekEnd)
            return segmentTree[loc];

        int mid = (seekStart + seekEnd) / 2;
        // mid보다 end가 같거나 작은 경우 왼쪽 자식 노드
        if (end <= mid)
            return getMax(start, end, loc * 2, seekStart, mid);
        else if (start > mid)       // start가 mid 보다 큰 경우는 오른쪽 자식 노드
            return getMax(start, end, loc * 2 + 1, mid + 1, seekEnd);
        else        // 범위에 걸친 경우는 두 자식 노드 모두 참조
            return Math.max(getMax(start, mid, loc * 2, seekStart, mid),
                    getMax(mid + 1, end, loc * 2 + 1, mid + 1, seekEnd));
    }

    // idx 위치에 value값을 넣고
    // 부모 노드들의 값을 갱신한다.
    static void inputValue(int idx, long value, int size) {
        int loc = 1;
        int start = 1;
        int end = size;

        while (start < end) {
            int mid = (start + end) / 2;
            if (idx <= mid) {
                loc *= 2;
                end = mid;
            } else {
                loc = loc * 2 + 1;
                start = mid + 1;
            }
        }
        segmentTree[loc] = value;
        loc /= 2;

        while (loc > 0) {
            segmentTree[loc] = Math.max(segmentTree[loc * 2], segmentTree[loc * 2 + 1]);
            loc /= 2;
        }
    }
}