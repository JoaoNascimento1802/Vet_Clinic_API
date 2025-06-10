package com.clinic.vet_clinic.report;

import com.clinic.vet_clinic.consultation.model.ConsultationModel; // Ajustado para ConsultationModel
import com.clinic.vet_clinic.consultation.repository.ConsultationRepository; // Ajustado para ConsultationRepository
import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum; // Certifique-se de importar o enum
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ConsultationRepository consultationRepository; // Ajustado para ConsultationRepository

    // Remova os @Autowireds de PetRepository e VeterinaryRepository se você já os carrega no ConsultationModel via @ManyToOne(fetch = FetchType.EAGER)
    // Se não, você precisará injetá-los e buscar os nomes dos pets/veterinários manualmente aqui.

    public byte[] generateConsultationReportPdf(
            Optional<Long> veterinarioId,
            Optional<SpecialityEnum> speciality) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            Paragraph title = new Paragraph("Relatório de Consultas", fontTitle);

            if (veterinarioId.isPresent()) {
                // Opcional: Buscar o nome do veterinário para exibir no título
                // Ex: VeterinaryModel vet = veterinaryRepository.findById(veterinarioId.get()).orElse(null);
                // if (vet != null) title.add(new Chunk("\n(Veterinário: " + vet.getName() + ")"));
                title.add(new Chunk("\n(Veterinário ID: " + veterinarioId.get() + ")"));
            }
            if (speciality.isPresent()) {
                title.add(new Chunk("\n(Especialidade: " + speciality.get().name() + ")")); // Use .name() ou .getDisplayName() se tiver
            }

            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1.5f, 1f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor headerBgColor = new BaseColor(52, 152, 219);
            addTableHeader(table, "Data", fontHeader, headerBgColor);
            addTableHeader(table, "Hora", fontHeader, headerBgColor);
            addTableHeader(table, "Paciente", fontHeader, headerBgColor);
            addTableHeader(table, "Veterinário", fontHeader, headerBgColor);
            addTableHeader(table, "Especialidade", fontHeader, headerBgColor);

            List<ConsultationModel> consultations; // <-- Variável para armazenar as consultas filtradas

            // LÓGICA DE FILTRAGEM CRUCIAL AQUI!
            if (veterinarioId.isPresent() && speciality.isPresent()) {
                consultations = consultationRepository.findByVeterinarioIdAndSpecialityEnum(veterinarioId.get(), speciality.get());
            } else if (veterinarioId.isPresent()) {
                consultations = consultationRepository.findByVeterinarioId(veterinarioId.get());
            } else if (speciality.isPresent()) {
                consultations = consultationRepository.findBySpecialityEnum(speciality.get());
            } else {
                consultations = consultationRepository.findAll(); // NENHUM FILTRO -> Retorna todas
            }

            Font fontCell = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            for (ConsultationModel consultation : consultations) { // <-- Itere sobre a lista filtrada
                // Certifique-se de que o Pet e o Veterinário sejam carregados (EAGER ou buscando manualmente)
                String petName = consultation.getPet() != null ? consultation.getPet().getName() : "N/A";
                String vetName = consultation.getVeterinario() != null ? consultation.getVeterinario().getName() : "N/A";

                addTableCell(table, consultation.getConsultationdate().toString(), fontCell);
                addTableCell(table, consultation.getConsultationtime().toString(), fontCell);
                addTableCell(table, petName, fontCell);
                addTableCell(table, vetName, fontCell);
                addTableCell(table, consultation.getSpecialityEnum().name(), fontCell);
            }
            document.add(table);

            document.close();
            return baos.toByteArray();

        } catch (DocumentException e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
            throw e;
        }
    }

    // Métodos auxiliares permanecem os mesmos
    private void addTableHeader(PdfPTable table, String headerText, Font font, BaseColor bgColor) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(bgColor);
        header.setPadding(8);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setVerticalAlignment(Element.ALIGN_MIDDLE);
        header.setBorderColor(BaseColor.WHITE);
        header.setPhrase(new Phrase(headerText, font));
        table.addCell(header);
    }

    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }
}