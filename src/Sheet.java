// CS3 Spreadsheet Sheet class. Update this file with your own code.

public class Sheet {
	private static final int ROWS = 20;
	private static final int COLS = 12;

	private Cell[][] sheet;

	public Sheet() {
		sheet = new Cell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				sheet[i][j] = new Cell();
			}
		}
	}

	private boolean isValidCell(String cell) {
		cell = cell.toUpperCase();

		char col = cell.charAt(0);
		if (col < 'A' || col > 'L') {
			return false;
		}

		try {
			int row = Integer.parseInt(cell.substring(1));
			if (row < 1 || row > 20) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private SheetLocation getCellLocation(String location) {
		location = location.toUpperCase();
		char col = location.charAt(0);
		int row = Integer.parseInt(location.substring(1));
		return new SheetLocation(row - 1, col - 'A');
	}

	private boolean isNumber(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Cell getCellFromString(String value) {
		Cell cell;

		value = value.trim();

		// First check if it's value is a cell reference
		if (isValidCell(value))
			return getCell(getCellLocation(value));

		// if (value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
		// 	cell = new TextCell(value.substring(1, value.length() - 1));
		// } else if (value.contains("/")) {
		// 	cell = new DateCell(value);
		// } else if (isNumber(value)) {
		// 	cell = new RealCell(value);
		// } else {
		// 	cell = new FormulaCell(value, this);
		// }

		if (value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
			cell = new TextCell(value.substring(1, value.length() - 1));
		} else if (value.charAt(0) == '(' && value.charAt(value.length() - 1) == ')') {
			cell = new FormulaCell(value, this);
		} else if (value.contains("/")) {
			cell = new DateCell(value);
		} else if (isNumber(value)) {
			cell = new RealCell(value);
		} else {
			cell = new TextCell(value);
		}

		return cell;
	}

	private void setCell(Location location, Cell cell) {
		sheet[location.getRow()][location.getCol()] = cell;
	}

	private String processGetCellCommand(String command) {
		if (!isValidCell(command))
			return null;

		return getCell(getCellLocation(command)).fullCellText();
	}

	private String processStringAssignmentCommand(String command) {
		if (!command.contains("="))
			return null;

		// Split by the first = sign
		String[] parts = command.split("=", 2);

		String cellString = parts[0];
		String value = parts[1];

		cellString = cellString.trim();
		value = value.trim();

		if (!isValidCell(cellString))
			return null;

		Location loc = getCellLocation(cellString);
		Cell cell = getCellFromString(value);
		if (cell == null)
			return null;

		setCell(loc, cell);
		return toString();
	}

	private String processClearCellCommand(String command) {
		String[] parts = command.split(" ");
		if (parts.length != 2)
			return null;

		String cellString = parts[1];
		if (!isValidCell(cellString))
			return null;

		Location loc = getCellLocation(cellString);
		setCell(loc, new Cell());

		return toString();
	}

	private String processClearCommand(String command) {
		if (!command.toLowerCase().equals("clear"))
			return null;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				sheet[i][j] = new Cell();
			}
		}

		return toString();
	}

	public String processCommand(String command) {
		if (command.length() == 0)
			return "";

		String output;
		// cell inspection, example: A1
		if ((output = processGetCellCommand(command)) != null)
			return output;
		// assignment of string values, example: A1 = "Hello"
		if ((output = processStringAssignmentCommand(command)) != null)
			return output;
		// clearing the entire sheet, example: clear
		if ((output = processClearCommand(command)) != null)
			return output;
		// clearing a single cell, example: clear A1
		if ((output = processClearCellCommand(command)) != null)
			return output;
		// For checkpoint 3, handle these additional variations of the assignment
		// command, see project specification for
		// details, note you also need to create/implement/use the DateCell, RealCell,
		// and FormulaCell classes
		// assignment of date value, example: A1 = 1/1/2014
		// assignment of real values, example A1 = 5.2
		// assignment of real formulas, examples A1 = ( A2 * 4 + A3 ) or A1 = ( sum
		// A1-d4 )
		// Note this method should be complete in checkpoint 3.
		// For checkpoints 4 and the final project, all changes should be in the
		// FormulaCell class, except
		// for any fixes to bugs in previous checkpoints and possibly optional features.

		return toString();
	}

	public int getRows() {
		return ROWS;
	}

	public int getCols() {
		return COLS;
	}

	public Cell getCell(Location loc) {
		return sheet[loc.getRow()][loc.getCol()];
	}

	public Cell getCell(int row, int col) {
		return sheet[row][col];
	}

	public Cell getCell(String cell) {
		return getCell(getCellLocation(cell));
	}

	@Override
	public String toString() {
		String output = "   |";
		for (int i = 0; i < COLS; i++) {
			output += (char) ('A' + i) + "         |";
		}
		output += "\n";

		for (int i = 0; i < ROWS; i++) {
			output += (i + 1);
			if (i < 9)
				output += "  |";
			else
				output += " |";
			for (int j = 0; j < COLS; j++) {
				output += getCell(i, j).abbreviatedCellText() + "|";
			}
			output += "\n";
		}

		return output;
	}

}
