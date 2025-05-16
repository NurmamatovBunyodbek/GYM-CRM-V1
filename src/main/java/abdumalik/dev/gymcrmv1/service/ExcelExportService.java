package abdumalik.dev.gymcrmv1.service;

import abdumalik.dev.gymcrmv1.model.User;
import abdumalik.dev.gymcrmv1.repository.UserRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    UserRepo userRepo;

    public ExcelExportService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ByteArrayInputStream exportToExcel() throws IOException {
        String[] COLUMNs = {"ID", "First Name", "Last Name", "Email", "Username", "Password", "Visit Count", "Has Paid"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("GymUsers");

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            List<User> users = userRepo.findAll();
            int rowIdx = 1;
            for (User u : users) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(u.getId().toString());
                row.createCell(1).setCellValue(u.getFirstName());
                row.createCell(2).setCellValue(u.getLastName());
                row.createCell(3).setCellValue(u.getEmail());
                row.createCell(4).setCellValue(u.getUsername());
                row.createCell(5).setCellValue(u.getPassword());
                row.createCell(6).setCellValue(u.getVisitCount());
                row.createCell(7).setCellValue(u.isHasPaid());
                row.createCell(8).setCellValue(u.getCreatedAt().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
            }

        }

}