import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class YetAnotherMazeSolverBFS {

	private static final String PROBLEM = "prob20-1-in";
	private static final String EXT = ".txt";
	public static final int[][] d = { { -1, -1, -1, 0, 0, 1, 1, 1 }, { -1, 0, 1, -1, 1, -1, 0, 1} };
	
	public static void main(String[] args) {
		Scanner scan;
		try {
			scan = new Scanner(new File(PROBLEM + EXT));
		} catch (FileNotFoundException ex) {
			printF(true, "File not found: %s", ex.getMessage());
			return;
		}
		while (scan.hasNextLine()) {
			String[] split = scan.nextLine().split(" ");
			int csiz = Integer.parseInt(split[0]);
			int rsiz = Integer.parseInt(split[1]);
			if (rsiz == 0 && csiz == 0)
				break;
			char[][] maze = new char[rsiz][csiz];
			for (int i = 0; i < rsiz; i++)
				maze[i] = scan.nextLine().toCharArray();
			int[][] dis = new int[rsiz][csiz];
			fill(dis, 10000000);
			int dist = bfs(maze, dis, 0, 0);
			printLine((dist == 10000000) ? 0 : dist);
		}
		scan.close();
	}
	
	public static int bfs(char[][] maze, int[][] dis, int sr, int sc) {
		Queue<Integer> q = new LinkedList<Integer>();
		dis[sr][sc] = 0;
		q.add(sr);
		q.add(sc);
		while (!q.isEmpty()) {
			int r = q.poll();
			int c = q.poll();
			for (int i = 0; i < 8; i++) {
				int rr = r + d[0][i];
				int cc = c + d[1][i];
				if (!inBounds(maze, rr, cc) || maze[rr][cc] != ' ')
					continue;
				if (dis[r][c] + 1 < dis[rr][cc]) {
					dis[rr][cc] = dis[r][c] + 1;
					q.add(rr);
					q.add(cc);
				}
			}
		}
		return dis[dis.length - 1][dis[dis.length - 1].length - 1];
	}
	
	public static void fill(int[][] array, int n) {
		for (int r = 0; r < array.length; r++)
			for (int c = 0; c < array[r].length; c++)
				array[r][c] = n;
	}
	
	public static int[] getPosition(char[][] array, char ch) {
		for (int r = 0; r < array.length; r++)
			for (int c = 0; c < array[r].length; c++)
				if (array[r][c] == ch)
					return new int[] {r, c};
		return new int[] {-1, -1};
	}
	
	public static boolean inBounds(char[][] array, int r, int c) {
		return (r >= 0 && r < array.length) && (c >= 0 && c < array[r].length);
	}
	
	public static void printArray(char[][] obj) {
		for (char[] ob : obj) {
			for (char o : ob)
				print(o);
			printLine();
		}
	}

	public static void print(Object... o) {
		for (Object obj : o) {
			System.out.print(obj);
		}
	}

	public static void printLine(Object... o) {
		if (o.length <= 0) {
			System.out.println();
			return;
		}
		for (Object obj : o) {
			System.out.println(obj);
		}
	}

	public static void printF(boolean newLine, String format, Object... o) {
		System.out.printf(format + ((newLine) ? "\n" : ""), o);
	}

}
