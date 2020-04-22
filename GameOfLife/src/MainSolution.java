import java.util.*;
import java.lang.*;
import java.io.*;

    public class MainSolution {
	static char[][] mat;
	static char[][] tmpMat;
	static Map<Long, char[][]> nextStates;
	static int rows;
	static int cols;
	static boolean equals = false;

        public static long myHash(){
            long myhash = 0;
        	for (int i = 0; i < mat.length; i++) {
    			for (int j = 0; j < mat[0].length; j++) {
    			    myhash += (((i+1)+i)*11*((j+1)+j))*(((int)(mat[i][j]) | (i+1) * (j+1) * 3)*31) + (((int)(mat[i][j]) | (i+1) * (j+1) * 5)*37) + (((j+1)*(int)(mat[i][j])^(i+1)*41 )*7);
    			}
        	}
        	
        	//print matrix
            // for (int i = 0; i < rows; i++) {
 			// 	for (int j = 0; j < cols; j++) {
 			// 		System.out.print(mat[i][j]);
 			// 	}
 			// 	System.out.println();
 			// }
        	return myhash;
        } 

	public static void updateMatrix() {
	   long hash = myHash();
 		if (nextStates.containsKey(hash)) {
 			mat = nextStates.get(hash);
 			
// 			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"); // see the process :)
// 			System.out.println("generation matrix: ");
// 			for (int i = 0; i < rows; i++) {
// 				for (int j = 0; j < cols; j++) {
// 					System.out.print(mat[i][j]);
// 				}
// 				System.out.println();
// 			}
// 			System.out.println("hit, hash: "+ hash);
//  			System.out.println("...................................."); 
 			return;
 		} else {
// 			System.out.println("hash: "+ hash);
// 			System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
			tmpMat = new char[rows][cols];
			int res;
//		System.out.println("________________________________________________________________________________"); // see the process :)
//		System.out.println("generation matrix: ");
//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < cols; j++) {
//				System.out.print(mat[i][j]);
//			}
//			System.out.println();
//		} 
		
		
			
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
			    
				if (mat[i][j] == '-') {
    				    res = neighbors(i, j) ;
    					tmpMat[i][j] = (res == 3) ? '*' : '-';						  //to be born
				} else {
				        res = neighbors(i, j) ;
					    tmpMat[i][j] = res == 3 || res == 2 ? '*' : '-';	             //to survive
			    }
			    
			
			}
		}
        nextStates.put(hash, tmpMat);
		mat = tmpMat;
 		}
	}

	public static int neighbors(int deadRow, int deadCol) { // born if there are exactly 3 live neighbors
	int neighs = 0;
		boolean OOBRow = false;
		boolean OOBCol = false;
		boolean negCol = false;
		boolean negRow = false;
		
		for (int r = deadRow - 1; r <= (deadRow + 1); r++) {        //iterating rows
				if (r > rows-1) {
					r = 0;
					OOBRow = true;
				}
				if(r < 0) {
					r=rows-1;
					negRow = true;
				}
				for (int c = deadCol - 1; c <= (deadCol + 1); c++) {	//iterating columns
					if (c < 0) {
						c = cols-1;
						negCol = true;
					}
					if(c>cols-1) {
						c = 0;
						OOBCol=true;
					}
					if (!((r == deadRow) && (c == deadCol))) { // if not the center cell
						if (mat[r][c] == '*') {
							neighs++;
						}
					}
					if(negCol) {
						c = -1;
						negCol=false;
					}
					if(OOBCol) {
						c = cols + 1;
						OOBCol=false;
					}
				}
				if(negRow) {
					r=-1;
					negRow=false;
				}
				if(OOBRow) {
					break;
				}
		}
		return neighs;
	    }
    
	public static void main (String[] args) throws java.lang.Exception {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	  	String[] nums = br.readLine().split(" ");
		rows = Integer.parseInt(nums[0]);
		cols = Integer.parseInt(nums[1]);
		mat = new char[rows][cols];
		nextStates = new HashMap<>();
		tmpMat = new char[rows][cols];
		int iterations = Integer.parseInt(nums[2]);

		for (int i = 0; i < rows; i++) {
			char[] tempRow= br.readLine().toCharArray();
			mat[i] = tempRow;
		}

		for (int t = 0; t < iterations; t++) {
			updateMatrix();
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(mat[i][j]);
			}
			System.out.println();
		}

	}
	
}