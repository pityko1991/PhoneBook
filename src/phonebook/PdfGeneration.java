package phonebook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;

import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

class PdfGeneration {
    public void pdfGeneration(String name, ObservableList<Person> data){
        //új dokumentumot hozunk létre amely tartalmazni fogja a listánkat
        Document document = new Document();
        
        try{
           //meghívjuk a pdfWriter osztályt, majd hozzáadjuk a dokumentumunkat, és beállítjuk a kiterjesztését
            PdfWriter.getInstance(document, new FileOutputStream(name + ".pdf"));
            document.open();
            // a dokumentumhoz csatolunk egy logót, és beállítjuk a paramétereit
            Image image = Image.getInstance(getClass().getResource("/logo.jpg"));
            image.scaleToFit(400,172);
            image.setAbsolutePosition(100f, 650f);
            document.add(image);
            
            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n"));
            
            //táblázat létrehozása
            float[] columnWidth = {2,4,4,5,5};
            //
            PdfPTable table = new PdfPTable(columnWidth);
            table.setWidthPercentage(100);
            //header cella létrehozása a táblázatban, színe, elhelyezkedése, mérete beállítása, majd táblázathoz való hozzáadása
            PdfPCell cell = new PdfPCell(new Phrase("KontaktLista"));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(5);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Sorszám");
            table.addCell("Vezetéknév");
            table.addCell("Keresztnév");
            table.addCell("Email cím");
            table.addCell("Telefonszám");
            table.setHeaderRows(1);
            
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAY);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            for(int i = 1; i<data.size(); i++){
                
                Person actualPerson = data.get(i-1);
                table.addCell(""+i);
                table.addCell(actualPerson.getLastName());
                table.addCell(actualPerson.getFirstName());
                table.addCell(actualPerson.getEmail());
                table.addCell(actualPerson.getNumber());
            }
            
            document.add(table);
            
            
            
            //a dokumentumhoz csatolunk egy aláírást, amelyet programon belül állítunk be
            Chunk signature = new Chunk("\n\n Generálva a Telefonkönyv alkalmazás segítségével");
            Paragraph base = new Paragraph(signature);
            document.add(base);
            
           
           }catch(Exception e){
            e.printStackTrace();
        }
        //bezárjuk a dokumentumot
        document.close();
    
    }
}
