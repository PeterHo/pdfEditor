package me.peterho.pdfEditor;

import com.itextpdf.kernel.pdf.*;

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
 * http://blog.51cto.com/walkerqt/1408792
 */

public class PdfEditor {

    private void removeSpecifiedTextInPDF(String src, String dest, String text) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));

        for (int i = 0; i < pdfDoc.getNumberOfPages(); i++) {
            PdfPage page = pdfDoc.getPage(i + 1);

            PdfDictionary dict = page.getPdfObject();
            PdfArray contentArray = dict.getAsArray(PdfName.Contents);
            if (contentArray != null) {
                for (int j = 0; j < contentArray.size(); j++) {
                    PdfStream stream = contentArray.getAsStream(j);
                    String content = new String(stream.getBytes());
                    if (content.contains(text)) {
                        // System.out.println(content);
                        stream.put(PdfName.Length, new PdfNumber(0));
                        stream.setData(new byte[0]);
                    }
                }
            }
        }

        pdfDoc.close();
    }

    private void removeSpecifiedXObjectInPDF(String src, String dest, String text) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));

        for (int i = 0; i < pdfDoc.getNumberOfPages(); i++) {
            PdfPage page = pdfDoc.getPage(i + 1);

            PdfDictionary dict = page.getPdfObject();
            PdfDictionary objects = dict.getAsDictionary(PdfName.XObject);
            if (objects != null) {
                for (PdfName name : objects.keySet()) {
                    PdfObject obj = objects.get(name);
                    if(obj.isIndirect()) {
                        System.out.println(name);
                    }
                }

                // for (int j = 0; j < contentArray.size(); j++) {
                //     PdfStream stream = contentArray.getAsStream(j);
                //     String content = new String(stream.getBytes());
                //     if (content.contains(text)) {
                //         System.out.println(content);
                //         stream.put(PdfName.Length, new PdfNumber(0));
                //         stream.setData(new byte[0]);
                //     }
                // }
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

        System.out.println("param: " + param);
        System.out.println("srcFile: " + srcFile);
        System.out.println("descFile: " + descFile);

        File file = new File(descFile);
        file.getParentFile().mkdirs();
        if (param.equals("-j")) {
            new PdfEditor().removeWatermarkOfJisuPDF(srcFile, descFile);
        } else if (param.startsWith("-d=")) {
            String text = param.substring(3);
            System.out.println("remove " + text + " ...");
            new PdfEditor().removeSpecifiedTextInPDF(srcFile, descFile, text);

        } else {
            System.out.println("unknown param");
        }

        System.out.println("OK");
    }
}