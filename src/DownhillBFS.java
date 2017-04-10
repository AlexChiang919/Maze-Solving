import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class DownhillBFS {

	private static final String PROBLEM = "prob18-1-in";
	private static final String EXT = " (2).txt";

	public static final int[][] d = { {-1, 0, 1, 0}, {0, -1, 0, 1} };
	
	public static void main(String[] args) {
		Scanner scan;
		try {
			scan = new Scanner(new File(PROBLEM + EXT));
		} catch (FileNotFoundException ex) {
			printF(true, "File not found: %s", ex.getMessage());
			return;
		}
		int times = Integer.parseInt(scan.nextLine());
		while (times-- > 0) {
			String[] split = scan.nextLine().split(" ");
			int rsiz = Integer.parseInt(split[0]);
			int csiz = Integer.parseInt(split[1]);
			char[][] maze = new char[rsiz][csiz];
			String[] splitt;
			for (int i = 0; i < rsiz; i++) {
				splitt = scan.nextLine().split(" ");
				for (int j = 0; j < csiz; j++)
					maze[i][j] = splitt[j].charAt(0);
			}
			int[] s = getPosition(maze, 'S');
			bfs(maze, s[0], s[1]);
			printArray(maze);
		}
		scan.close();
	}
	
	public static void bfs(char[][] maze, int sr, int sc) {
		int[][] vis = new int[maze.length][maze[0].length];
		for (int r = 0; r < vis.length; r++)
			for (int c = 0; c < vis[r].length; c++)
				if (maze[r][c] != 'X' && maze[r][c] != 'S')
					vis[r][c] = Integer.parseInt("" + maze[r][c]);
				else if (maze[r][c] == 'X')
					vis[r][c] = 0;
				else if (maze[r][c] == 'S')
					vis[r][c] = 9;
					
		Queue<Integer> q = new LinkedList<Integer>();
		int[][] dis = new int[maze.length][maze[0].length];
		fill(dis, 10000000);
		dis[sr][sc] = 0;
		q.add(sr);
		q.add(sc);
		int er = -1;
		int ec = -1;
		boolean solve = false;
		while (!q.isEmpty()) {
			int r = q.poll();
			int c = q.poll();
			if (maze[r][c] == 'X') {
				solve = true;
				er = r;
				ec = c;
			}
			for (int i = 0; i < 4; i++) {
				int rr = r + d[0][i];
				int cc = c + d[1][i];
				if (!inBounds(maze, rr, cc) || vis[rr][cc] > vis[r][c])
					continue;
				if (dis[r][c] + 1 < dis[rr][cc] && vis[rr][cc] <= vis[r][c]) {
					dis[rr][cc] = dis[r][c] + 1;
					q.add(rr);
					q.add(cc);
				}
			}
		}
		if (solve)
			draw(maze, dis, er, ec);
		else
			printLine("How mean of you to give us an unsolveable maze.");
	}
	
	public static void draw(char[][] maze, int[][] dis, int er, int ec) {
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(er);
		q.add(ec);
		List<Integer> used = new ArrayList<Integer>();
		boolean end = false;
		while (!q.isEmpty()) {
			int r = q.poll();
			int c = q.poll();
			if (end)
				break;
			for (int i = 0; i < 4; i++) {
				int rr = r + d[0][i];
				int cc = c + d[1][i];
				if (!inBounds(maze, rr, cc) || used.contains(dis[rr][cc]))
					continue;
				if (dis[rr][cc] == dis[r][c] - 1) {
					if (maze[rr][cc] == 'S') {
						end = true;
						break;
					}
					used.add(dis[rr][cc]);
					maze[rr][cc] = '.';
					q.add(rr);
					q.add(cc);
				}
				if (end)
					break;
			}
		}
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[r].length; c++) {
				if (!(maze[r][c] == 'S' || maze[r][c] == 'X' || maze[r][c] == '.'))
					;//maze[r][c] = '#';
			}
		}
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
				print(o + " ");
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
