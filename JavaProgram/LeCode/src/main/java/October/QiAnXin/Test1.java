package October.QiAnXin;

public class Test1 {

    public static int maxValue (int[][] matrix) {
        // write code here
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i ++) {
            for (int j = 0; j < n; j ++) {
                int left = j - 1;
                int top = i - 1;
                if ((top >= 0 && top < m) && (left >= 0 && left <= n)) {
                    matrix[i][j] = Math.max(matrix[i][j] + matrix[i][left], matrix[i][j] + matrix[top][j]);
                }
                if ((top >= 0 && top < m) && !(left >= 0 && left <= n)) {
                    matrix[i][j] = matrix[i][j] + matrix[top][j];
                }
                if (!(top >= 0 && top < m) && (left >= 0 && left <= n)) {
                    matrix[i][j] = matrix[i][j] + matrix[i][left];
                }

            }
        }
        return matrix[m - 1][n - 1];
    }

    public static void main(String[] args) {
        int[][] matrix = { {2,3,1},   {2,5,3},   {4,2,1} };
        System.out.println(maxValue(matrix));
    }
}
