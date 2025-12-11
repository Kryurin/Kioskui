import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
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

    /**
     * Generates a PDF card for a CustomerMembership object
     */
    public static void generateCard(CustomerMembership cm) {
        try {
            // 1. Create folder if not exists
            File folder = new File("customer_memberships");
            if (!folder.exists()) folder.mkdirs();

            // 2. PDF file path
            String pdfPath = "customer_memberships/" +
                    cm.getCustomerName().replaceAll("\\s+", "_") +
                    "_MembershipCard.pdf";

            // 3. Initialize PDF
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // 4. Card layout
            float cardWidth = 400;
            float cardHeight = 250;
            DeviceRgb bgColor = new DeviceRgb(50, 120, 200);

            Div card = new Div();
            card.setWidth(cardWidth);
            card.setHeight(cardHeight);
            card.setBackgroundColor(bgColor);
            card.setPadding(20);

            // 5. Customer Name
            Paragraph namePara = new Paragraph(cm.getCustomerName())
                    .setFontColor(ColorConstants.WHITE)
                    .setBold()
                    .setFontSize(22)
                    .setMarginBottom(10);
            card.add(namePara);

            // 6. Membership Type
            Paragraph typePara = new Paragraph("Membership: " + cm.getMembership().getName())
                    .setFontColor(ColorConstants.WHITE)
                    .setFontSize(16)
                    .setMarginBottom(10);
            card.add(typePara);

            // 7. Start & Expiration Dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            Paragraph datePara = new Paragraph(
                    "Start: " + cm.getStartDate().format(formatter) + "\n" +
                            "Expires: " + cm.getExpirationDate().format(formatter)
            ).setFontColor(ColorConstants.WHITE)
                    .setFontSize(14)
                    .setMarginBottom(10);
            card.add(datePara);

            // 8. Contact info (optional)
            if (cm.getContactInfo() != null && !cm.getContactInfo().isEmpty()) {
                Paragraph contactPara = new Paragraph("Contact: " + cm.getContactInfo())
                        .setFontColor(ColorConstants.WHITE)
                        .setFontSize(12)
                        .setMarginBottom(10);
                card.add(contactPara);
            }

            // 9. QR code for ID
            BufferedImage qrImage = generateQRCode(String.valueOf(cm.getId()), 100, 100);
            File qrFile = new File("customer_memberships/temp_qr.png");
            ImageIO.write(qrImage, "PNG", qrFile);

            Image qr = new Image(ImageDataFactory.create("customer_memberships/temp_qr.png"));
            qr.setFixedPosition(cardWidth - 120, 20); // bottom-right
            card.add(qr);

            // 10. Add card to document and close
            document.add(card);
            document.close();

            // Delete temp QR
            qrFile.delete();

            System.out.println("Customer membership PDF created: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // QR code generator
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
