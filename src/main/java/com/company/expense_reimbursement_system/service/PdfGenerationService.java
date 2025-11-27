package com.company.expense_reimbursement_system.service;

import com.company.expense_reimbursement_system.dto.ReportClaimData;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfGenerationService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public byte[] generateExpenseReport(List<ReportClaimData> claims, LocalDate startDate, LocalDate endDate) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Add title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Expense Reimbursement Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Add date range
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            Paragraph dateRange = new Paragraph(
                    "Report Period: " + startDate.format(DATE_FORMATTER) +
                            " to " + endDate.format(DATE_FORMATTER),
                    normalFont
            );
            dateRange.setSpacingAfter(20);
            document.add(dateRange);

            // Create table
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 2, 4, 2, 2, 2});

            // Add table headers
            addTableHeader(table);

            // Add data rows and calculate totals
            BigDecimal totalApprovedAmount = BigDecimal.ZERO;
            int totalClaims = claims.size();

            for (ReportClaimData claim : claims) {
                addTableRow(table, claim);

                if ("Approved".equals(claim.getStatus())) {
                    totalApprovedAmount = totalApprovedAmount.add(claim.getAmount());
                }
            }

            document.add(table);

            // Add summary section
            Paragraph summaryTitle = new Paragraph("\nSummary", titleFont);
            summaryTitle.setSpacingBefore(20);
            summaryTitle.setSpacingAfter(10);
            document.add(summaryTitle);

            Paragraph totalClaimsP = new Paragraph("Total Claims: " + totalClaims, normalFont);
            document.add(totalClaimsP);

            Paragraph totalApprovedP = new Paragraph(
                    "Total Approved Amount: $" + totalApprovedAmount.toString(),
                    normalFont
            );
            document.add(totalApprovedP);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }


    private void addTableHeader(PdfPTable table) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);

        String[] headers = {"Employee Name", "Department", "Description", "Amount", "Status", "Date"};

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }
    }

    private void addTableRow(PdfPTable table, ReportClaimData claim) {
        Font cellFont = new Font(Font.FontFamily.HELVETICA, 9);

        table.addCell(createCell(claim.getEmployeeName(), cellFont));
        table.addCell(createCell(claim.getDepartment(), cellFont));
        table.addCell(createCell(claim.getDescription(), cellFont));
        table.addCell(createCell("$" + claim.getAmount().toString(), cellFont));
        table.addCell(createCell(claim.getStatus(), cellFont));
        table.addCell(createCell(claim.getDateSubmitted().format(DATE_FORMATTER), cellFont));
    }

    private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        return cell;
    }



}
