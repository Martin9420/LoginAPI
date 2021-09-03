package ImportExcel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Servlet implementation class Excel
 */
@WebServlet("/Excel")
public class Excel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Excel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String filePath = "C:\\work\\hp\\workbook.xlsx";

		try (
				// Excelのワークブックの読み込み
				Workbook book = new XSSFWorkbook(filePath);) {

			// シートの読み込み
			Sheet sheet = book.getSheet("しーと");

			// 値読み込み
			for (int rowIdx = sheet.getFirstRowNum(); rowIdx <= sheet.getLastRowNum(); rowIdx++) {
				Row row = sheet.getRow(rowIdx);

				if (row == null) {
					continue;
				}

				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				for (int colIdx = row.getFirstCellNum(); colIdx < row.getLastCellNum(); colIdx++) {
					Cell cell = row.getCell(colIdx);
					String cellString = null;
					if (cell == null) {
						continue;
					}

					// cell.getCellType()はCellTypeというEnumを返すようになった
					switch (cell.getCellType()) {
					case STRING:
						cellString = cell.getStringCellValue();
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							cellString = sdf1.format(date) + " " + sdf2.format(date);
						} else {
							cellString = Double.toString(cell.getNumericCellValue());
						}
						break;
					case FORMULA:
						cellString = cell.getCellFormula();
						break;
					default:
						break;
					}

					// switch文はEnumの頭の部分を省略するが、if文は付ける必要がある
					if (cell.getCellType() == CellType.STRING) {

					}

					System.out.println(rowIdx + ":" + colIdx + ":" + cellString);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

}
