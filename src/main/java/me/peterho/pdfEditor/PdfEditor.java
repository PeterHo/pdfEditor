package me.peterho.pdfEditor;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

// import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
// import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
// import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
// import com.itextpdf.samples.GenericTest;
// import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * https://developers.itextpdf.com/examples
 */

public class PdfEditor {

    private void removeSpecifiedTextInPDF(String src, String dest, String text) throws IOException {
        // PdfReader reader = new PdfReader(src);
        // PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        // TextExtractionStrategy strategy;
        // for (int i=1; i<=reader.getNumberOfPages(); i++) {
        // for (int i=1; i<=2; i++) {
        //
        //     // SimpleTextExtractionStrategy();
        //     // strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
        //     // System.out.println(strategy.getResultantText());
        // }
        // reader.close();



        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));

        for (int i = 0; i < pdfDoc.getNumberOfPages(); i++) {
        // for (int i = 1; i < 2; i++) {
            PdfPage page = pdfDoc.getPage(i + 1);
            PdfDictionary dict = page.getPdfObject();
            PdfObject object = dict.get(PdfName.Link);
            if (object instanceof PdfStream) {
                PdfStream stream = (PdfStream) object;
                System.out.println("~~~~~~~~~~~~~~~~ " + stream.getCompressionLevel());
                byte[] data = stream.getBytes();
                for (int j = 0; j < data.length - 8; j++) {
                    if (data[j] == 'X' &&
                            data[j + 1] == 'O' &&
                            data[j + 2] == 'b' &&
                            data[j + 3] == 'j' &&
                            data[j + 4] == 'e' &&
                            data[j + 5] == 'c' &&
                            data[j + 6] == 't') {
                        System.out.println("~~~~~~~~~~~~~~~" + j);
                        break;
                    }
                }
                System.out.println(new String(data, "UTF-8"));
            }
        }

        pdfDoc.close();
    }


    private void removeWatermarkOfJisuPDF(String src, String dest) throws Exception {

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));

        for (int i = 0; i < pdfDoc.getNumberOfPages(); i++) {
            PdfPage page = pdfDoc.getPage(i + 1);
            PdfDictionary dict = page.getPdfObject();
            PdfObject object = dict.get(PdfName.Contents);
            if (object instanceof PdfStream) {
                PdfStream stream = (PdfStream) object;
                byte[] data = stream.getBytes();
                for (int j = 0; j < data.length - 8; j++) {
                    if (data[j] == 'X' &&
                            data[j + 1] == 'O' &&
                            data[j + 2] == 'b' &&
                            data[j + 3] == 'j' &&
                            data[j + 4] == 'e' &&
                            data[j + 5] == 'c' &&
                            data[j + 6] == 't') {
                        data[j] = 'A';
                        data[j + 1] = 'A';
                        data[j + 2] = 'A';
                        data[j + 3] = 'A';
                        data[j + 4] = 'A';
                        data[j + 5] = 'A';
                        data[j + 6] = 'A';
                        break;
                    }
                }
                stream.setData(data);
            }
        }

        pdfDoc.close();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(args.length);
        System.out.println(args[0]);
        System.out.println(args[1]);
        System.out.println(args[2]);
        if (args.length != 3) {
            System.out.println("pdfEditor -j srcPdf desPdf");
            System.out.println("pdfEditor -d=\"some text\" srcPdf desPdf");
            System.out.println("-j remove watermarks of Jisu PDF Editor");
            System.out.println("-d remove specified text in pdf");
            return;
        }
        String param = args[0];
        String srcFile = args[1];
        String descFile = args[2];

        File file = new File(descFile);
        file.getParentFile().mkdirs();
        if (param.equals("-j")) {
            new PdfEditor().removeWatermarkOfJisuPDF(srcFile, descFile);
        } else if (param.startsWith("-d=")) {
            String text = param.substring(3);
            System.out.println(text);
            new PdfEditor().removeSpecifiedTextInPDF(srcFile, descFile, text);

        } else {
            System.out.println("unknown param");
        }
    }
}