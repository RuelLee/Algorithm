/*
 Author : Ruel
 Problem : Baekjoon 9345번 디지털 비디오 디스크(DVDs)
 Problem address : https://www.acmicpc.net/problem/9345
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9345_디지털비디오디스크DVDs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] minSegmentTree;
    static int[] maxSegmentTree;
    static int n;

    public static void main(String[] args) throws IOException {
        // DVD 대여점에는 0 ~ n-1번까지의 N개 DVD로 구성된 시리즈를 구매한다
        // DVD들은 순서대로 선반에 놓여있는데
        // 진상 손님이 나타나 순서를 뒤바꿔놓는다고 한다
        // 다른 손님이 특정 구간의 DVD들을 모두 빌렸을 때, 연속된 순서라면 YES를 그렇지 않다면 NO를 출력하라
        // 2 ~ 4까지의 범위라고 한다면, 2 3 4 or 2 4 3 or 3 2 4 or 3 4 2 or 4 2 3 or 4 3 2 범위 내에 모든 DVD가 있기만 하면 된다.
        //
        // 해당 문제를 세그먼트 트리 문제라고 인식하는거 자체가 문제다.
        // 해당 구간에 대해 모든 DVD를 검색할 필요없이
        // 해당 구간에서의 최소값과 최대값이 일치하는지만 따져보면 중간에 있는 DVD들은 순서는 다를 수 있지만 연속된 DVD들이 있음 알 수 있다
        // 따라서 최소 세그먼트 트리와 최대 세그먼트 트리를 만들어, 구간 별로 최소, 최대값을 빠르게 구해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            // 세그먼트 트리의 크기
            int size = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1);
            minSegmentTree = new int[size];
            maxSegmentTree = new int[size];
            // 세그먼트 트리 초기 세팅. 0 ~ n -1까지.
            setSegmentTree(1, 0, n - 1);

            for (int i = 0; i < k; i++) {
                st = new StringTokenizer(br.readLine());
                int q = Integer.parseInt(st.nextToken());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if (q == 0)     // q가 0이라면 a, b 위치의 DVD를 서로 바꿔 넣는 경우.
                    switchDVD(a, b);
                else {
                    // q가 1이라면, a ~ b 구간에 연속된 DVD들이 있는지 확인.
                    int[] minMax = getMinMax(a, b, 1, 0, n - 1);
                    // a ~ b 구간에 최소 값이 a이고 최대값이 b라면, 연속된 DVD들로 존재하는 경우.
                    if (a == minMax[0] && b == minMax[1])
                        sb.append("YES");
                    // 그렇지 않다면 NO 출력
                    else
                        sb.append("NO");
                    sb.append("\n");
                }
            }
        }
        System.out.print(sb);
    }

    static int[] getMinMax(int targetStart, int targetEnd, int loc, int seekStart, int seekEnd) {
        if (targetStart == seekStart && targetEnd == seekEnd)
            return new int[]{minSegmentTree[loc], maxSegmentTree[loc]};

        int mid = (seekStart + seekEnd) / 2;
        if (targetEnd <= mid)
            return getMinMax(targetStart, targetEnd, loc * 2, seekStart, mid);
        else if (targetStart > mid)
            return getMinMax(targetStart, targetEnd, loc * 2 + 1, mid + 1, seekEnd);
        else {
            int[] answer = new int[2];
            int[] left = getMinMax(targetStart, mid, loc * 2, seekStart, mid);
            int[] right = getMinMax(mid + 1, targetEnd, loc * 2 + 1, mid + 1, seekEnd);
            answer[0] = Math.min(left[0], right[0]);
            answer[1] = Math.max(left[1], right[1]);
            return answer;
        }
    }
    // a위치와 b위치의 DVD들을 서로 바꿔넣는다.
    static void switchDVD(int a, int b) {
        // a와 b의 idx
        int aLoc = getLoc(a);
        int bLoc = getLoc(b);
        // 현재 a와 b에 들어있는 DVD 번호.
        int aValue = minSegmentTree[aLoc];
        int bValue = minSegmentTree[bLoc];

        // a위치에는 b값을
        updateValue(aLoc, bValue);
        // b위치에는 a값을 넣는다.
        updateValue(bLoc, aValue);
    }

    // 말단 노드의 위치 loc과 값 value가 주어질 때, 최소, 최대 세그먼트 트리를 갱신한다.
    static void updateValue(int loc, int value) {
        // 말단 노드에는 해당 값을 넣고
        minSegmentTree[loc] = maxSegmentTree[loc] = value;
        // 루트 노드로 갈 때까지
        while (loc > 1) {
            // 한 칸씩 조상 노드로 가면서
            loc /= 2;
            // 최소, 최대 세그먼트 트리를 갱신해준다.
            minSegmentTree[loc] = Math.min(minSegmentTree[loc * 2], minSegmentTree[loc * 2 + 1]);
            maxSegmentTree[loc] = Math.max(maxSegmentTree[loc * 2], maxSegmentTree[loc * 2 + 1]);
        }
    }

    // 원하는 번호의 선반의 세그먼트 트리 idx 값을 가져온다.
    static int getLoc(int target) {
        int loc = 1;
        int seekStart = 0;
        int seekEnd = n - 1;
        while (seekStart < seekEnd) {
            int mid = (seekStart + seekEnd) / 2;
            // 왼쪽 자식 노드일 경우
            if (target <= mid) {
                loc *= 2;
                seekEnd = mid;
            } else {    // 오른쪽 자식 노드 일 경우.
                loc = loc * 2 + 1;
                seekStart = mid + 1;
            }
        }
        // seekStart == seekEnd인 시점에서 반복문이 끝난다.
        // 그 시점의 loc이 원하는 선반의 세그먼트 트리 idx
        return loc;
    }

    // 세그먼트 트리 초기화 메소드
    static void setSegmentTree(int loc, int seekStart, int seekEnd) {
        if (seekStart == seekEnd) {     // 말단 노드라면
            // 최소, 최대 세그먼트 트리에 해당 값으로 초기 세팅해준다.
            minSegmentTree[loc] = maxSegmentTree[loc] = seekStart;
            return;
        }

        int mid = (seekStart + seekEnd) / 2;
        // 왼쪽 자식 노드 세팅
        setSegmentTree(loc * 2, seekStart, mid);
        // 오른쪽 자식 노드 세팅
        setSegmentTree(loc * 2 + 1, mid + 1, seekEnd);
        // 자식 노드들의 값이 채워졌으므로, 최소, 최대 세그먼트 트리에서 loc값을 세팅해준다.
        minSegmentTree[loc] = Math.min(minSegmentTree[loc * 2], minSegmentTree[loc * 2 + 1]);
        maxSegmentTree[loc] = Math.max(maxSegmentTree[loc * 2], maxSegmentTree[loc * 2 + 1]);
    }
}