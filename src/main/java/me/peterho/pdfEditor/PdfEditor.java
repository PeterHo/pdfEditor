package me.peterho.pdfEditor;

import com.itextpdf.kernel.pdf.*;
// import com.itextpdf.samples.GenericTest;
// import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

public class PdfEditor {
    // protected void removeWatermarkOfJisuPDF(String dest) throws Exception {
    //     PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
    //
    //     for (int i = 0; i < pdfDoc.getNumberOfPages(); i++) {
    //         PdfPage page = pdfDoc.getPage(i + 1);
    //         PdfDictionary dict = page.getPdfObject();
    //         PdfObject object = dict.get(PdfName.Contents);
    //         if (object instanceof PdfStream) {
    //             PdfStream stream = (PdfStream) object;
    //             byte[] data = stream.getBytes();
    //             for (int j = 0; j < data.length - 8; j++) {
    //                 if (data[j] == 'X' &&
    //                         data[j + 1] == 'O' &&
    //                         data[j + 2] == 'b' &&
    //                         data[j + 3] == 'j' &&
    //                         data[j + 4] == 'e' &&
    //                         data[j + 5] == 'c' &&
    //                         data[j + 6] == 't') {
    //                     data[j] = 'A';
    //                     data[j + 1] = 'A';
    //                     data[j + 2] = 'A';
    //                     data[j + 3] = 'A';
    //                     data[j + 4] = 'A';
    //                     data[j + 5] = 'A';
    //                     data[j + 6] = 'A';
    //                     break;
    //                 }
    //             }
    //             stream.setData(data);
    //         }
    //     }
    //
    //     pdfDoc.close();
    // }

    public static void main(String[] args) throws Exception {
        System.out.println(args.length);
        System.out.println(args[0]);
        System.out.println(args[1]);
        // File file = new File(DEST);
        // file.getParentFile().mkdirs();
        // new PdfEditor().removeWatermarkOfJisuPDF(DEST);
    }
}