package com.clinic.vet_clinic.report;


import com.clinic.vet_clinic.veterinary.speciality.SpecialityEnum;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional; // Importe Optional

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/consultations-pdf")
    public ResponseEntity<byte[]> getConsultationsPdfReport(
            @RequestParam Optional<Long> veterinarioId,
            @RequestParam Optional<SpecialityEnum> speciality) { // Note o @RequestParam e Optional

        try {
            byte[] pdfBytes = reportService.generateConsultationReportPdf(veterinarioId, speciality);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "relatorio_consultas.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (DocumentException e) {
            System.err.println("Erro ao gerar relatório PDF de consultas: " + e.getMessage());
            // Idealmente, você logaria o erro completo para depuração:
            // e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}