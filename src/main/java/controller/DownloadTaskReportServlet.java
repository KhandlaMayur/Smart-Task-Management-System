package controller;

import dao.TaskDAO;
import dao.UserDAO;
import model.Task;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DownloadTaskReportServlet extends HttpServlet {

    private static final float PAGE_MARGIN = 36f;

    private static final float PAGE_BOTTOM_LIMIT = 70f;

    private static final float[] TABLE_WIDTHS =
            {190f, 95f, 110f, 128f};

    private static final float TABLE_ROW_PADDING = 10f;

    private TaskDAO taskDAO;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        taskDAO = new TaskDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/auth/login.jsp?error=Please Login First"
            );

            return;
        }

        int userId = Integer.parseInt(
                session.getAttribute("userId")
                        .toString()
        );

        User user =
                userDAO.getUserById(userId);

        List<Task> taskList =
                taskDAO.getAllTasks(userId);

        String userName =
                user != null
                        ? user.getName()
                        : String.valueOf(session.getAttribute("userName"));

        String generatedAt =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                );

        String fileDate =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")
                );

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=\"task-report-" + fileDate + ".pdf\""
        );

        try (PDDocument document =
                     new PDDocument()) {

            PDPage page =
                    new PDPage(PDRectangle.A4);

            document.addPage(page);

            PDPageContentStream contentStream =
                    new PDPageContentStream(document, page);

            float yPosition =
                    drawFirstPageHeader(
                            contentStream,
                            page,
                            userName,
                            generatedAt,
                            taskList.size(),
                            taskDAO.getCompletedTasks(userId),
                            taskDAO.getPendingTasks(userId),
                            taskDAO.getInProgressTasks(userId)
                    );

            yPosition -= 14f;

            yPosition =
                    drawTableHeader(
                            contentStream,
                            page,
                            yPosition
                    );

            boolean alternateRow = false;

            for (Task task : taskList) {

                if (yPosition - calculateRowHeight(task) <= PAGE_BOTTOM_LIMIT) {

                    contentStream.close();

                    page =
                            new PDPage(PDRectangle.A4);

                    document.addPage(page);

                    contentStream =
                            new PDPageContentStream(document, page);

                    yPosition =
                            drawContinuationHeader(
                                    contentStream,
                                    page,
                                    userName
                            );

                    yPosition =
                            drawTableHeader(
                                    contentStream,
                                    page,
                                    yPosition
                            );
                }

                yPosition =
                        drawTaskRow(
                                contentStream,
                                page,
                                yPosition,
                                task,
                                alternateRow
                        );

                alternateRow = !alternateRow;
            }

            if (yPosition - 52f <= PAGE_BOTTOM_LIMIT) {

                contentStream.close();

                page =
                        new PDPage(PDRectangle.A4);

                document.addPage(page);

                contentStream =
                        new PDPageContentStream(document, page);

                yPosition =
                        drawContinuationHeader(
                                contentStream,
                                page,
                                userName
                        );
            }

            drawMotivationMessage(
                    contentStream,
                    page,
                    yPosition - 24f
            );

            contentStream.close();

            document.save(response.getOutputStream());
        }
    }

    private float drawFirstPageHeader(PDPageContentStream contentStream,
                                      PDPage page,
                                      String userName,
                                      String generatedAt,
                                      int totalTasks,
                                      int completedTasks,
                                      int pendingTasks,
                                      int inProgressTasks)
            throws IOException {

        float pageWidth =
                page.getMediaBox().getWidth();

        float y =
                page.getMediaBox().getHeight() - 48f;

        drawText(
                contentStream,
                "Task Report",
                PAGE_MARGIN,
                y,
                PDType1Font.HELVETICA_BOLD,
                26,
                new Color(33, 37, 41)
        );

        drawText(
                contentStream,
                "View and download your task report receipt.",
                PAGE_MARGIN,
                y - 28f,
                PDType1Font.HELVETICA,
                13,
                new Color(108, 117, 125)
        );

        float userBoxWidth = 175f;
        float userBoxHeight = 50f;
        float userBoxX = pageWidth - PAGE_MARGIN - userBoxWidth;
        float userBoxY = y - 14f;

        contentStream.setNonStrokingColor(new Color(232, 240, 255));
        contentStream.addRect(userBoxX, userBoxY, userBoxWidth, userBoxHeight);
        contentStream.fill();

        drawText(
                contentStream,
                "User Name",
                userBoxX + 12f,
                userBoxY + 33f,
                PDType1Font.HELVETICA_BOLD,
                10,
                new Color(13, 110, 253)
        );

        float userNameFontSize =
                findBestFitFontSize(
                        safeValue(userName),
                        PDType1Font.HELVETICA_BOLD,
                        14f,
                        10f,
                        userBoxWidth - 24f
                );

        drawText(
                contentStream,
                fitText(
                        safeValue(userName),
                        PDType1Font.HELVETICA_BOLD,
                        userNameFontSize,
                        userBoxWidth - 24f
                ),
                userBoxX + 12f,
                userBoxY + 14f,
                PDType1Font.HELVETICA_BOLD,
                userNameFontSize,
                new Color(33, 37, 41)
        );

        drawText(
                contentStream,
                "Generated: " + generatedAt,
                PAGE_MARGIN,
                y - 60f,
                PDType1Font.HELVETICA,
                10,
                new Color(130, 138, 145)
        );

        float cardsY = y - 176f;
        float cardGap = 12f;
        float cardWidth = (pageWidth - (PAGE_MARGIN * 2) - (cardGap * 3)) / 4f;
        float cardHeight = 66f;

        drawSummaryCard(contentStream, PAGE_MARGIN, cardsY, cardWidth, cardHeight, "Total Tasks", String.valueOf(totalTasks), new Color(33, 37, 41));
        drawSummaryCard(contentStream, PAGE_MARGIN + cardWidth + cardGap, cardsY, cardWidth, cardHeight, "Completed", String.valueOf(completedTasks), new Color(25, 135, 84));
        drawSummaryCard(contentStream, PAGE_MARGIN + (cardWidth + cardGap) * 2, cardsY, cardWidth, cardHeight, "Pending", String.valueOf(pendingTasks), new Color(255, 193, 7));
        drawSummaryCard(contentStream, PAGE_MARGIN + (cardWidth + cardGap) * 3, cardsY, cardWidth, cardHeight, "In Progress", String.valueOf(inProgressTasks), new Color(13, 110, 253));

        return cardsY - 24f;
    }

    private float drawContinuationHeader(PDPageContentStream contentStream,
                                         PDPage page,
                                         String userName)
            throws IOException {

        float y =
                page.getMediaBox().getHeight() - 48f;

        drawText(
                contentStream,
                "Task Report",
                PAGE_MARGIN,
                y,
                PDType1Font.HELVETICA_BOLD,
                22,
                new Color(33, 37, 41)
        );

        drawText(
                contentStream,
                "User: " + safeValue(userName),
                page.getMediaBox().getWidth() - PAGE_MARGIN - 190f,
                y,
                PDType1Font.HELVETICA_BOLD,
                11,
                new Color(13, 110, 253)
        );

        return y - 36f;
    }

    private void drawSummaryCard(PDPageContentStream contentStream,
                                 float x,
                                 float y,
                                 float width,
                                 float height,
                                 String label,
                                 String value,
                                 Color valueColor)
            throws IOException {

        contentStream.setNonStrokingColor(Color.WHITE);
        contentStream.addRect(x, y, width, height);
        contentStream.fill();

        contentStream.setStrokingColor(new Color(228, 232, 237));
        contentStream.addRect(x, y, width, height);
        contentStream.stroke();

        drawText(
                contentStream,
                label,
                x + 12f,
                y + height - 20f,
                PDType1Font.HELVETICA,
                11,
                new Color(108, 117, 125)
        );

        drawText(
                contentStream,
                value,
                x + 12f,
                y + 18f,
                PDType1Font.HELVETICA_BOLD,
                23,
                valueColor
        );
    }

    private float drawTableHeader(PDPageContentStream contentStream,
                                  PDPage page,
                                  float y)
            throws IOException {

        float tableWidth =
                page.getMediaBox().getWidth() - (PAGE_MARGIN * 2);

        contentStream.setNonStrokingColor(Color.WHITE);
        contentStream.addRect(PAGE_MARGIN, y - 28f, tableWidth, 30f);
        contentStream.fill();

        float currentX = PAGE_MARGIN + 10f;

        String[] headers =
                {"Title", "Priority", "Status", "Due Date"};

        for (int i = 0; i < headers.length; i++) {

            drawText(
                    contentStream,
                    headers[i],
                    currentX,
                    y - 16f,
                    PDType1Font.HELVETICA_BOLD,
                    14,
                    Color.BLACK
            );

            currentX += TABLE_WIDTHS[i];
        }

        contentStream.setStrokingColor(new Color(223, 228, 234));
        contentStream.moveTo(PAGE_MARGIN, y - 30f);
        contentStream.lineTo(PAGE_MARGIN + tableWidth, y - 30f);
        contentStream.stroke();

        return y - 38f;
    }

    private float drawTaskRow(PDPageContentStream contentStream,
                              PDPage page,
                              float y,
                              Task task,
                              boolean alternateRow)
            throws IOException {

        List<String> titleLines =
                getWrappedTitleLines(task);

        float lineHeight = 14f;
        float rowHeight =
                calculateRowHeight(titleLines);

        float tableWidth =
                page.getMediaBox().getWidth() - (PAGE_MARGIN * 2);

        contentStream.setNonStrokingColor(
                alternateRow
                        ? new Color(246, 248, 250)
                        : Color.WHITE
        );
        contentStream.addRect(PAGE_MARGIN, y - rowHeight, tableWidth, rowHeight);
        contentStream.fill();

        contentStream.setStrokingColor(new Color(223, 228, 234));
        contentStream.moveTo(PAGE_MARGIN, y - rowHeight);
        contentStream.lineTo(PAGE_MARGIN + tableWidth, y - rowHeight);
        contentStream.stroke();

        float currentX = PAGE_MARGIN + TABLE_ROW_PADDING;
        float titleBlockHeight = titleLines.size() * lineHeight;
        float titleTextStartY =
                y - ((rowHeight - titleBlockHeight) / 2f) - 7f;
        float singleLineTextY =
                y - (rowHeight / 2f) - 3f;

        for (int i = 0; i < titleLines.size(); i++) {

            drawText(
                    contentStream,
                    titleLines.get(i),
                    currentX,
                    titleTextStartY - (i * lineHeight),
                    PDType1Font.HELVETICA,
                    11,
                    new Color(33, 37, 41)
            );
        }

        currentX += TABLE_WIDTHS[0];

        drawText(
                contentStream,
                fitText(task.getPriority(), PDType1Font.HELVETICA, 11, TABLE_WIDTHS[1] - (TABLE_ROW_PADDING * 2)),
                currentX,
                singleLineTextY,
                PDType1Font.HELVETICA,
                11,
                new Color(33, 37, 41)
        );

        currentX += TABLE_WIDTHS[1];

        drawText(
                contentStream,
                fitText(task.getStatus(), PDType1Font.HELVETICA, 11, TABLE_WIDTHS[2] - (TABLE_ROW_PADDING * 2)),
                currentX,
                singleLineTextY,
                PDType1Font.HELVETICA,
                11,
                new Color(33, 37, 41)
        );

        currentX += TABLE_WIDTHS[2];

        drawText(
                contentStream,
                fitText(String.valueOf(task.getDueDate()), PDType1Font.HELVETICA, 11, TABLE_WIDTHS[3] - (TABLE_ROW_PADDING * 2)),
                currentX,
                singleLineTextY,
                PDType1Font.HELVETICA,
                11,
                new Color(33, 37, 41)
        );

        return y - rowHeight;
    }

    private float calculateRowHeight(Task task)
            throws IOException {

        return calculateRowHeight(
                getWrappedTitleLines(task)
        );
    }

    private float calculateRowHeight(List<String> titleLines) {

        float lineHeight = 14f;

        return Math.max(
                32f,
                (titleLines.size() * lineHeight) + (TABLE_ROW_PADDING * 2)
        );
    }

    private List<String> getWrappedTitleLines(Task task)
            throws IOException {

        return wrapText(
                task.getTitle(),
                PDType1Font.HELVETICA,
                11,
                TABLE_WIDTHS[0] - (TABLE_ROW_PADDING * 2)
        );
    }

    private void drawMotivationMessage(PDPageContentStream contentStream,
                                       PDPage page,
                                       float y)
            throws IOException {

        float boxHeight = 54f;
        float boxWidth =
                page.getMediaBox().getWidth() - (PAGE_MARGIN * 2);

        contentStream.setNonStrokingColor(new Color(232, 240, 255));
        contentStream.addRect(PAGE_MARGIN, y - boxHeight, boxWidth, boxHeight);
        contentStream.fill();

        drawText(
                contentStream,
                "Keep going. Every finished task is one more step toward your goal.",
                PAGE_MARGIN + 14f,
                y - 22f,
                PDType1Font.HELVETICA_BOLD,
                12,
                new Color(13, 110, 253)
        );

        drawText(
                contentStream,
                "Stay consistent and your progress will speak for itself.",
                PAGE_MARGIN + 14f,
                y - 38f,
                PDType1Font.HELVETICA,
                10,
                new Color(73, 80, 87)
        );
    }

    private void drawText(PDPageContentStream contentStream,
                          String text,
                          float x,
                          float y,
                          PDType1Font font,
                          float fontSize,
                          Color color)
            throws IOException {

        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(safeValue(text));
        contentStream.endText();
    }

    private String fitText(String value,
                           PDType1Font font,
                           float fontSize,
                           float maxWidth)
            throws IOException {

        String safeText =
                safeValue(value);

        if (getTextWidth(safeText, font, fontSize) <= maxWidth) {

            return safeText;
        }

        String ellipsis = "...";
        StringBuilder builder =
                new StringBuilder();

        for (int i = 0; i < safeText.length(); i++) {

            String candidate =
                    builder.toString()
                            + safeText.charAt(i)
                            + ellipsis;

            if (getTextWidth(candidate, font, fontSize) > maxWidth) {

                break;
            }

            builder.append(safeText.charAt(i));
        }

        if (builder.isEmpty()) {

            return ellipsis;
        }

        return builder + ellipsis;
    }

    private float findBestFitFontSize(String value,
                                      PDType1Font font,
                                      float preferredFontSize,
                                      float minimumFontSize,
                                      float maxWidth)
            throws IOException {

        float fontSize = preferredFontSize;

        while (fontSize > minimumFontSize
                && getTextWidth(value, font, fontSize) > maxWidth) {

            fontSize -= 0.5f;
        }

        return fontSize;
    }

    private List<String> wrapText(String value,
                                  PDType1Font font,
                                  float fontSize,
                                  float maxWidth)
            throws IOException {

        List<String> lines =
                new ArrayList<>();

        String safeText =
                safeValue(value).trim();

        if (safeText.isEmpty()) {

            lines.add("");

            return lines;
        }

        String[] words =
                safeText.split("\\s+");

        StringBuilder currentLine =
                new StringBuilder();

        for (String word : words) {

            String candidate =
                    currentLine.length() == 0
                            ? word
                            : currentLine + " " + word;

            if (getTextWidth(candidate, font, fontSize) <= maxWidth) {

                currentLine.setLength(0);
                currentLine.append(candidate);

                continue;
            }

            if (currentLine.length() > 0) {

                lines.add(currentLine.toString());
                currentLine.setLength(0);
            }

            if (getTextWidth(word, font, fontSize) <= maxWidth) {

                currentLine.append(word);

            } else {

                lines.add(
                        fitText(
                                word,
                                font,
                                fontSize,
                                maxWidth
                        )
                );
            }
        }

        if (currentLine.length() > 0) {

            lines.add(currentLine.toString());
        }

        return lines;
    }

    private float getTextWidth(String text,
                               PDType1Font font,
                               float fontSize)
            throws IOException {

        return (font.getStringWidth(text) / 1000f) * fontSize;
    }

    private String safeValue(String value) {

        return value == null
                ? ""
                : value;
    }
}
