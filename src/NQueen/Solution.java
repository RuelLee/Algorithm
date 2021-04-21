package NQueen;

import java.util.ArrayList;
import java.util.List;

class Cell {
    int r;
    int c;

    public Cell(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Solution {
    static List<Cell> cellList;
    static int casesCount;

    public static void main(String[] args) {
        // n * n의 체스판이 주어질 때, n개의 퀸을 배치해서 누구도 잡을 수 없는 상태의 가짓수
        // 가로 n줄, 세로 n줄에 n개의 퀸을 배치해야하므로, 각 가로줄, 세로줄에는 하나씩의 퀸만 위치해야한다.
        // 가로 세로 뿐만아니라, ↗, ↖ 방향의 대각선으로도 서로 위치가 겹치면 안된다.
        // 가로, 세로의 중복 여부는 row, col 값이 같은지 비교, 대각선은 row - col, row + col 값을 비교하면 된다.


        int n = 10;

        cellList = new ArrayList<>();
        casesCount = 0;
        nQueen(0, n);
        System.out.println(casesCount);
    }

    static void nQueen(int row, int n) {
        // 각 row에는 하나의 퀸만 위치할 수 있다.
        // 따라서 각 row마다 중복되지 않는 col, row - col, row + col 위치에 퀸을 배치하면 된다.

        if (row == n) {     // n개의 퀸이 모두 선정되었을 때 casesCount를 하나 늘려준다.
            casesCount++;
            return;
        }

        for (int col = 0; col < n; col++) {
            if (checkAvail(row, col)) {     // 현재의 col 값과 가로, 세로, 대각선 위치에 퀸이 존재하는지 확인.
                Cell cell = new Cell(row, col);
                cellList.add(cell);     // 존재 하지 않으면, row, col 위치에 Queen을 위치시키고, 다음 row + 1로 넘어간다.
                nQueen(row + 1, n);
                cellList.remove(cell);  // 이 위치에 도달하려면 row, col에 Queen을 둘 수 있는 모든 가짓수를 계산한 뒤이므로 현재 위치의 Queen을 빼준다.
            }
        }
    }

    static boolean checkAvail(int r, int c) {
        for (Cell cell : cellList) {
            if (r == cell.r || c == cell.c || cell.r + cell.c == r + c || cell.r - cell.c == r - c)
                return false;
        }
        return true;
    }
}