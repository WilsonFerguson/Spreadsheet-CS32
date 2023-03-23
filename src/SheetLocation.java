// CS3 Spreadsheet SheetLocation class.  Fill in the details.

public class SheetLocation extends Location {

	public SheetLocation(int rowIn, int colIn) {
		super(rowIn, colIn);
	}

	public SheetLocation(String str) {
		this(Integer.parseInt(str.substring(1)) - 1, str.charAt(0) - 'A');
	}
}
