import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.TextAlignment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.format.DateTimeFormatter;

public class CustomerMembershipCardGenerator {

    public static void generateCard(CustomerMembership cm) {
        try {
            // Create folder
            File folder = new File("customer_memberships");
            if (!folder.exists()) folder.mkdirs();

            // Output PDF
            String pdfPath = "customer_memberships/" +
                    cm.getCustomerName().replaceAll("\\s+", "_") +
                    "_MembershipCard.pdf";

            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Card size and color
            float cardWidth = 400;
            float cardHeight = 180;
            DeviceRgb bgColor = new DeviceRgb(60, 80, 110);

            // CARD CONTAINER
            Div card = new Div();
            card.setWidth(cardWidth);
            card.setHeight(cardHeight);
            card.setBackgroundColor(bgColor);
            card.setPadding(20);
            card.setHorizontalAlignment(HorizontalAlignment.CENTER);

            // --- 2 COLUMN TABLE (TEXT LEFT, QR RIGHT) ---
            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 1}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setBorder(null);
            table.setMargin(0);

            // LEFT CELL: TEXT
            Cell leftCell = new Cell();
            leftCell.setBorder(null);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

            Paragraph namePara = new Paragraph(cm.getCustomerName())
                    .setFontColor(ColorConstants.WHITE)
                    .setBold()
                    .setFontSize(22)
                    .setMarginBottom(10);

            Paragraph typePara = new Paragraph("Membership: " + cm.getMembership().getName())
                    .setFontColor(ColorConstants.WHITE)
                    .setFontSize(16)
                    .setMarginBottom(10);

            Paragraph datePara = new Paragraph(
                    "Start: " + cm.getStartDate().format(formatter) + "\n" +
                            "Expires: " + cm.getExpirationDate().format(formatter)
            ).setFontColor(ColorConstants.WHITE)
                    .setFontSize(14)
                    .setMarginBottom(10);

            leftCell.add(namePara);
            leftCell.add(typePara);
            leftCell.add(datePara);

            if (cm.getContactInfo() != null && !cm.getContactInfo().isEmpty()) {
                Paragraph contactPara = new Paragraph("Contact: " + cm.getContactInfo())
                        .setFontColor(ColorConstants.WHITE)
                        .setFontSize(12);
                leftCell.add(contactPara);
            }

            table.addCell(leftCell);

            // RIGHT CELL: QR CODE
            BufferedImage qrImage = generateQRCode(String.valueOf(cm.getId()), 110, 110);
            File qrFile = new File("customer_memberships/temp_qr.png");
            ImageIO.write(qrImage, "PNG", qrFile);

            Image qr = new Image(ImageDataFactory.create(qrFile.getAbsolutePath()))
                    .setWidth(110)
                    .setHeight(110);

            Cell rightCell = new Cell();
            rightCell.setBorder(null);
            rightCell.setTextAlignment(TextAlignment.RIGHT);
            rightCell.add(qr);

            table.addCell(rightCell);

            // Add table inside card
            card.add(table);

            // Add card to PDF
            document.add(card);

            document.close();
            qrFile.delete();

            System.out.println("Customer membership PDF created: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage generateQRCode(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
