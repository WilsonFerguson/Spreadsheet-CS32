// CS3 Spreadsheet FormulaCell class.  Fill in the details.

public class FormulaCell extends RealCell {
	private Sheet sheet; // needed to find values of cells in the formula

	public FormulaCell(String str, Sheet sheetIn) {
		super(str);
		this.sheet = sheetIn;
	}

	// TODO: add private methods as needed

	// TODO: override the getDoubleValue() method

}
