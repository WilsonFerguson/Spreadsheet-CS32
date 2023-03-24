import java.util.ArrayList;
import java.util.List;

public class RangeFunction {

    private String name;

    public RangeFunction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double getCellValue(Sheet sheet, String cell) {
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

    public double evaluate(Sheet sheet, String[] cells) {
        return 0;
    }

    public static RangeFunction[] createFunctions() {
        List<RangeFunction> functions = new ArrayList<>();

        functions.add(new RangeFunction("SUM") {
            @Override
            public double evaluate(Sheet sheet, String[] cells) {
                double sum = 0;
                for (String cell : cells) {
                    double value = getCellValue(sheet, cell.trim());
                    if (value == -1)
                        return -1;
                    sum += value;
                }
                return sum;
            }
        });

        functions.add(new RangeFunction("AVG") {
            @Override
            public double evaluate(Sheet sheet, String[] cells) {
                double sum = 0;
                for (String cell : cells) {
                    double value = getCellValue(sheet, cell.trim());
                    if (value == -1)
                        return -1;
                    sum += value;
                }
                return sum / cells.length;
            }
        });

        functions.add(new RangeFunction("MAX") {
            @Override
            public double evaluate(Sheet sheet, String[] cells) {
                double max = Double.MIN_VALUE;
                for (String cell : cells) {
                    double value = getCellValue(sheet, cell.trim());
                    if (value == -1)
                        return -1;
                    if (value > max)
                        max = value;
                }
                return max;
            }
        });

        functions.add(new RangeFunction("MIN") {
            @Override
            public double evaluate(Sheet sheet, String[] cells) {
                double min = Double.MAX_VALUE;
                for (String cell : cells) {
                    double value = getCellValue(sheet, cell.trim());
                    if (value == -1)
                        return -1;
                    if (value < min)
                        min = value;
                }
                return min;
            }
        });

        return functions.toArray(new RangeFunction[functions.size()]);
    }
}
