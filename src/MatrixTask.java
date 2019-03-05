import java.lang.Math;

class MatrixTask
{
    private int[][] matrix = new int[][]{
            new int[]{1, 2, 3},
            new int[]{0, -1, 0},
            new int[]{7, 8, 9},
            new int[]{10, 11, 12},
            new int[]{13, 113, 15},
    };
    private int m = 5;
    private int n = 3;
    private int[] result = new int[m];
    
    MatrixTask() {}
    
    private int CountSum(int element)
    {
        int result = 0;
        element = Math.abs(element);
        while (element != 0)
        {
            result += element % 10;
            element = element / 10;
        }
        return result;
    }
    
    private int MaxInLine(int[] line, int size)
    {
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < size; i++)
        {
            int curr = CountSum(line[i]);
            max = Math.max(max, curr);
        }
        return max;
    }
    
    void CompleteTask()
    {
        for (int i = 0; i < m; i++)
        {
            result[i] = MaxInLine(matrix[i], n);
        }
        System.out.println(java.util.Arrays.toString(result));
    }
}
