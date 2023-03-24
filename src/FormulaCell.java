import org.w3c.dom.ranges.Range;

// CS3 Spreadsheet FormulaCell class.  Fill in the details.

public class FormulaCell extends RealCell {
	private Sheet sheet; // needed to find values of cells in the formula
	private RangeFunction[] functions = RangeFunction.createFunctions();

	public FormulaCell(String str, Sheet sheetIn) {
		super(str);
		this.sheet = sheetIn;
	}

	// TODO: add private methods as needed
	private boolean isNumber(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private double getValue(String cell) {
		if (isNumber(cell)) {
			return Double.parseDouble(cell);
		} else {
			Cell c = sheet.getCell(cell);
			if (c instanceof RealCell) {
				return ((RealCell) c).getDoubleValue();
			} else {
				return -1;
			}
		}
	}

	private String[] parseRange(String range) {
		String[] parts = range.split("-");

		String startCell = parts[0];
		String endCell = parts[1];

		char startCol = Character.toUpperCase(startCell.charAt(0));
		char endCol = Character.toUpperCase(endCell.charAt(0));
		int startRow = Integer.parseInt(startCell.substring(1));
		int endRow = Integer.parseInt(endCell.substring(1));

		String[][] cells = new String[Math.abs(endCol - startCol) + 1][Math.abs(endRow - startRow) + 1];
		for (char col = startCol; col <= endCol; col++) {
			for (int row = startRow; row <= endRow; row++) {
				cells[col - startCol][row - startRow] = String.valueOf(col) + String.valueOf(row);
			}
		}

		String[] result = new String[cells.length * cells[0].length];
		int index = 0;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				result[index] = cells[i][j];
				index++;
			}
		}

		return result;
	}

	private double handleMethod(String[] parts) {
		if (parts.length != 2)
			return 0;

		String method = parts[0].trim().toUpperCase();
		RangeFunction function = null;
		for (RangeFunction f : functions) {
			if (f.getName().equals(method)) {
				function = f;
				break;
			}
		}
		if (function == null)
			return 0;

		String[] cells;
		if (parts[1].contains("-")) {
			cells = parseRange(parts[1]);
		} else {
			cells = new String[] { parts[1] };
		}

		return function.evaluate(sheet, cells);
	}

	// TODO: override the getDoubleValue() method
	@Override
	public double getDoubleValue() {
		String formula = fullCellText().substring(2, fullCellText().length() - 2); // Remove the parentheses

		String[] parts = formula.split(" ");

		if (parts.length == 1)
			return getValue(parts[0]);

		double result = 0;
		if ((result = handleMethod(parts)) != 0)
			return result;

		while (parts.length >= 3) {
			double left = getValue(parts[0]);
			double right = getValue(parts[2]);
			if (left == -1 || right == -1)
				return -1;

			String operator = parts[1];

			switch (operator) {
				case "+":
					result = left + right;
					break;
				case "-":
					result = left - right;
					break;
				case "*":
					result = left * right;
					break;
				case "/":
					result = left / right;
					break;
				default:
					return 0;
			}

			String[] newParts = new String[parts.length - 2];
			newParts[0] = String.valueOf(result);
			for (int i = 3; i < parts.length; i++) {
				newParts[i - 2] = parts[i];
			}

			parts = newParts;
		}

		return result;
	}

	@Override
	public String abbreviatedCellText() {
		String output = String.valueOf(getDoubleValue());
		if (output.equals("-1.0"))
			output = "#ERROR";
		if (output.length() > 10) {
			output = output.substring(0, 10);
		} else {
			while (output.length() < 10) {
				output += " ";
			}
		}
		return output;
	}

}
